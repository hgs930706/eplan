package com.lcm.domain.opta.moduleplanner.app;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.impl.score.director.ScoreDirector;
import org.optaplanner.core.impl.score.director.ScoreDirectorFactory;
import org.optaplanner.core.impl.solver.ProblemFactChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lcm.domain.Adjustment;
import com.lcm.domain.EqpCapa;
import com.lcm.domain.EquipmentPreventiveMaintenance;
import com.lcm.domain.Line;
import com.lcm.domain.Plan;
import com.lcm.domain.RJobDashboard;
import com.lcm.domain.SJobDashboard;
import com.lcm.domain.SJobScore;
import com.lcm.domain.Special;
import com.lcm.domain.Woxx;
import com.lcm.domain.opta.moduleplanner.domain.AppData;
import com.lcm.domain.opta.moduleplanner.domain.Capa;
import com.lcm.domain.opta.moduleplanner.domain.Job;
import com.lcm.domain.opta.moduleplanner.domain.Model;
import com.lcm.domain.opta.moduleplanner.domain.PlanLine;
import com.lcm.domain.opta.moduleplanner.domain.Setup;
import com.lcm.domain.opta.moduleplanner.domain.Shift;
import com.lcm.domain.opta.moduleplanner.domain.TaskAssignmentSolution;
import com.lcm.domain.opta.moduleplanner.domain.TimeWindowedJob;
import com.lcm.domain.opta.moduleplanner.utility.ModulePlannerUtility;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

public class AppMA {
	private static final Logger logger = LoggerFactory.getLogger(AppMA.class);
	
	private Solver<TaskAssignmentSolution> solver;
	private ScoreDirector<TaskAssignmentSolution> guiScoreDirector;
	
	public AppMA() {
		
	}
	
	public static void main(String[] args) {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		StatusPrinter.print(lc);
	}
	
	private AppData init(AppData data) throws Exception {
		LocalDate planStartDate = (LocalDate) ((Map)data.getMapParam().get("plan_start_date")).get("in_value1");
		int planDay = Integer.parseInt(String.valueOf(((Map)data.getMapParam().get("plan_day")).get("in_value1")));
		int shiftDstart = Integer.parseInt(String.valueOf(((Map)data.getMapParam().get("shift_d_start")).get("in_value1")));
	
		//修改Plan區間使用planStartDate+planDay天數,迴圈內planDate+1是為了要滿載而多計算一天dueTime,但實際上listPlan不會加進去 JoshLai@20190419
		List<LocalDate> listPlanDate = new ArrayList<>();
		Map<String, Object> mapDueTime = new HashMap<>();
		for(int i=0; i<planDay+1; i++) {
			LocalDate date = planStartDate.plusDays(i);
			if(listPlanDate.size() < planDay) {
				listPlanDate.add(date);
			}
			mapDueTime.put(ModulePlannerUtility.formatterNoSlash.format(date), (i+1)*24);
		}
	
		logger.info("planDay: " + planDay + " listPlanDate: " + listPlanDate);
		logger.info("mapDueTime: " + mapDueTime);
	
		List<Model> modelList = new ArrayList<Model>();
		for(int i=0; i<data.getcModModelList().size(); i++) {
			Model model =  new Model(data.getcModModelList().get(i).getModelId().getSite(), data.getcModModelList().get(i).getModelId().getPartNo(),data.getcModModelList().get(i).getModelNo(),data.getcModModelList().get(i).getModelSite(),data.getcModModelList().get(i).getModelType(),
					data.getcModModelList().get(i).getModelExt(),data.getcModModelList().get(i).getModelVer(),data.getcModModelList().get(i).getPanelSize(),data.getcModModelList().get(i).getBarType(),data.getcModModelList().get(i).getPanelSizeGroup(),
					data.getcModModelList().get(i).getPartsGroup(),data.getcModModelList().get(i).getIsBuildPcb(),data.getcModModelList().get(i).getIsDemura(),data.getcModModelList().get(i).getTuffyType(),data.getcModModelList().get(i).getLastTrackoutTime(),
					data.getcModModelList().get(i).getColor(),data.getcModModelList().get(i).getPriority(),"", data.getcModChangeList(),data.getcModModelList().get(i).getChangeGroup());
			modelList.add(model);
		}

		List<Capa> capaList = new ArrayList<Capa>();
		for(EqpCapa capa : data.getcEqpCapaList()) {
			Capa cp = new Capa(capa.getEqpCapaId().getSite(), capa.getFab(), capa.getArea(), capa.getEqpCapaId().getLine(), capa.getModelNo(), capa.getEqpCapaId().getPartNo(), capa.getPpcCapa(), capa.getFabPcCapa(), capa.getManpowerKilo());
			capaList.add(cp);
		}
	
		//滿載調整planQty從AppService搬移到這 JoshLai@20190723+
		List<Plan> rJobPlanList = data.getrJobPlanList();
		logger.info(rJobPlanList.size()+ " final rJobPlanList: " + rJobPlanList);
		data.setrJobPlanList(rJobPlanList);
		//為了讓SJobDashboard也能拿取PPC&Fab pc capa JoshLai@20190704+
		Map<String, Object> mapCapa = ModulePlannerUtility.calcCapaMap(rJobPlanList);
		Map<String, Object> mapParam = data.getMapParam();
		mapParam.put("capa_map", mapCapa);
		data.setMapParam(mapParam);
		logger.info(data.getMapParam().size() + " mapParam: " + data.getMapParam());
		
		//可排產Plan = 前一日MA剩餘WIP + 排產日COG調整後排程
		List<Plan> planList = ModulePlannerUtility.calcRemainingWIP(planStartDate.minusDays(1), data.getsLotOpwpList(),
				data.getAdjustmentAllAreaList(), data.getrJobDashboardListBeforePlanDate(),
				data.getrJobDashboardListInPlanDate(), data.getAdjustmentListAfterCOG(), data.getrJobSpecialList(),
				data.getcEqpCapaList(), data.getAreaList(), mapParam);
		data.setrJobPlanList(planList);
	
		AppData appData = new AppData(data.getcFacLineList(), data.getcModModelList(), data.getcEqpCapaList(),
				data.getrJobPlanList(), data.getrJobSpecialList(), data.getrJobEqpmList(),
				data.getcFacConstraintCapaList(), data.getWoxxList(),
				data.getcModChangeList(), data.getMapParam(), data.getSite(), data.getShiftDateMap(),
				data.getShiftList(), data.getSetup(), data.gethJobDashboardList(), data.getScoreList(),
				data.getPlanLineList(), modelList, capaList, data.getListPlanResult(), planStartDate, planDay, mapDueTime,
				listPlanDate, shiftDstart, data.getHistoricalList(), data.getFabChangeGroup());
		return appData;
	}
	
	private List<PlanLine> getLines(List<Line> cFacLineList, List<Object> historicalList, List<PlanLine> planLineList, List<Model> modelList, List<Capa> capaList, Map<String, Object> mapParam) throws CloneNotSupportedException {
		planLineList = new ArrayList<PlanLine>();
	
		logger.info(historicalList.size() + " historicalList: " + historicalList);
		int i=0;
		for(Line line : cFacLineList) {
			Map<Model, Map<String, Object>> capa = new HashMap<Model, Map<String ,Object>>();
			for(Capa obj : getCapaByLine(line.getLine(), capaList)) {
				Map<String, Object> capaTmp = new HashMap<String, Object>();
//				capaTmp.put("PPC_CAPA", obj.getPpcCapa());
				capaTmp.put("FAB_PC_CAPA", obj.getFabPcCapa());
				capaTmp.put("MANPOWER", obj.getManpowerKilo());
			
				Model model = getModelByPartNo(obj.getPartNo(), modelList);
				capa.put(model, capaTmp);
			}
		
			String lastPartNo = getLastPartNoByProcessEndTime(historicalList, line.getLine());
			logger.info("lineNo: " + line.getLine() + " lastPartNo: " + lastPartNo + " lastModel: " + getModelByPartNo(lastPartNo, modelList));
			RJobDashboard lastChangeRJob = ModulePlannerUtility.getLastChangeRJobByChangeShiftDate(historicalList, line.getLine(), 0, mapParam);
			logger.info("lineNo: " + line.getLine() + " lastChangeRJob: " + lastChangeRJob + " line: " + line);
			PlanLine ln = new PlanLine(i, line.getSite(),line.getFab(), line.getArea(), line.getLine(), line.getActiveFlag(), getModelByPartNo(lastPartNo, modelList), capa, 1, line.getLineType(), lastChangeRJob, line.getLineMode());
			ln.setCapa(capa);
			planLineList.add(ln);
			i++;
		}
	
		logger.info(planLineList.size() + " lineList: " + planLineList);
		return planLineList;
	}
	
	private Map getJobs(Setup setup, List<Plan> rJobPlanList, List<Special> rJobSpecialList, List<EquipmentPreventiveMaintenance> rJobEqpmList, List<PlanLine> planLineList, List<Model> modelList, LocalDate planStart, Map<String, Object> mapDueTime, List<LocalDate> listPlanDate, int shiftDstart, List<EqpCapa> cEqpCapaList, Map<String, Object> mapParam){
		Map<String, Object> maps = new HashMap<>();
		List<TimeWindowedJob> jobList = new ArrayList<TimeWindowedJob>();
	
		int idx = 0;
		//add by avonchung 20190304
		ArrayList possibleLineList = new ArrayList<PlanLine>();
	
		//by line收集相同指定線別的job add by avonchung 20190320
		Map<String, Object> mapPossible = new HashMap<>();
		for(PlanLine line : planLineList) {
			String lineNo = line.getLineNo();
			List<TimeWindowedJob> possibleJobList = new ArrayList<>();
			mapPossible.put(lineNo, possibleJobList);
		}
		
		logger.info(rJobPlanList.size() + " rJobPlanList: " + rJobPlanList);
		logger.info(planLineList.size() + " planLineList: " + planLineList);
		logger.info(cEqpCapaList.size() + " cEqpCapaList: " + cEqpCapaList);
		for(Plan obj : rJobPlanList) {
			//只取planDate區間的plan
			if(!listPlanDate.contains(obj.getShiftDate())) {
				continue;
			}
			try {
				TimeWindowedJob job = new TimeWindowedJob();
				possibleLineList = new ArrayList<PlanLine>();
				//possibleLineList.add(getLineByLineNo(obj.getLine(), planLineList));
				possibleLineList.addAll(getLineByPartNo(obj.getPartNo(), cEqpCapaList, planLineList));
				
				double readyTime = ModulePlannerUtility.calcNumByDateTime(planStart, shiftDstart, obj.getShiftDate());
				if(obj.getReadyTime()!=null) {
					readyTime = obj.getReadyTime();
				}
				job.setReadyTime(readyTime);
				String sDueTime = String.valueOf(mapDueTime.get(ModulePlannerUtility.formatterNoSlash.format(obj.getContinueMaxShiftDate())));
				if(sDueTime != null) {
					job.setDueTime(Double.parseDouble(sDueTime));
//					logger.debug("lineNo: " + obj.getLine() + " shiftDate: " + obj.getShiftDate() +" getContinueMaxShiftDate()==> " + obj.getContinueMaxShiftDate() + " sDueTime: " + sDueTime);
				}
				job.setId(Long.valueOf(idx));
				job.setJobId(obj.getJobId());
//				job.setLine(getLineByLineNo(obj.getLine(), planLineList));
				job.setModel(getModelByPartNo(obj.getPartNo(), modelList));

				int planQty = Integer.parseInt(obj.getPlanQty());
				job.setPlanQty(planQty);
				job.setForecastQty(planQty);
				job.setJobType("PROD");
				job.setSetup(setup);
				job.setAssignLine(getLineByLineNo(obj.getLine(), planLineList));
				job.setSite(obj.getSite());
				job.setFab(obj.getFab());
				job.setArea(obj.getArea());
				job.setLineNo(obj.getLine());
				job.setShift_date(obj.getShiftDate());
				job.setPlanStartDate(planStart);
				job.setShiftDstart(shiftDstart);
				job.setEqpCapa(obj.getCapa());
				job.setGrade(obj.getGrade());
				job.setPossibleLineList(possibleLineList);// add by avonchung 20190304
				job.setMapParam(mapParam);

				//by line收集相同指定線別的job add by avonchung 20190320
				if (/*mapPossible.get(obj.getLine()) != null && */listPlanDate.contains(obj.getShiftDate())) {
					//List<TimeWindowedJob> list = (List<TimeWindowedJob>) mapPossible.get(obj.getLine());
					List<TimeWindowedJob> list = new ArrayList<>();
					list.add(job);
					mapPossible.put(obj.getLine(), list);
				}
				
				jobList.add(job);
				idx++;
			}catch(Exception e) {
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logger.error(errors.toString());
				e.printStackTrace();
			}
		}
		
		logger.info(mapPossible.size() + " mapPossible: " + mapPossible);
		logger.info(jobList.size() + " jobList: " + jobList);
	
		//PM
		for(EquipmentPreventiveMaintenance obj : rJobEqpmList ) {
			try {
				TimeWindowedJob job = new TimeWindowedJob();
		
				//add by avonchung 20190304
				possibleLineList = new ArrayList<PlanLine>();
				possibleLineList.add(getLineByLineNo(obj.getLine(), planLineList));
				
				job.setDueTime(Double.parseDouble(String.valueOf(mapDueTime.get(ModulePlannerUtility.formatterNoSlash.format(obj.getShiftDate())))));
				job.setId(Long.valueOf(idx));
				job.setJobId(obj.getJobId());
//	    		job.setLine(getLineByLineNo(obj.getLine(), planLineList));
				job.setPlanQty(0);
				job.setForecastQty(0);
				job.setJobType(obj.getJobType());
				job.setSetup(setup);
				job.setAssignLine(getLineByLineNo(obj.getLine(), planLineList));
				job.setSite(obj.getSite());
				job.setFab(obj.getFab());
				job.setArea(obj.getArea());
				job.setLineNo(obj.getLine());
				job.setShift_date(obj.getShiftDate());
				job.setPmDuration(Double.parseDouble(obj.getPmDuration()));
				job.setShiftTypeString(obj.getShift());
				job.setPossibleLineList(possibleLineList);//add by avonchung 20190304

				double assignStartTime = ModulePlannerUtility.calcShiftTime(planStart, obj.getShiftDate(), obj.getShift());
				job.setAssignStartTime(assignStartTime);
				job.setReadyTime(assignStartTime);
				job.setPlanStartDate(planStart);
				job.setShiftDstart(shiftDstart);
				job.setMapParam(mapParam);

				// by line收集相同指定線別的job add by avonchung 20190320
				if(mapPossible.get(obj.getLine()) != null) {
					List<TimeWindowedJob> list = (List<TimeWindowedJob>) mapPossible.get(obj.getLine());
					list.add(job);
					mapPossible.put(obj.getLine(), list);
				}

				jobList.add(job);
				idx++;
			} catch (Exception e) {
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logger.error(e.toString());
			}
		}

		//Special Job
		for (Special obj : rJobSpecialList) {
			try {
				TimeWindowedJob job = new TimeWindowedJob();

				// add by avonchung 20190304
				possibleLineList = new ArrayList<PlanLine>();
				possibleLineList.add(getLineByLineNo(obj.getLine(), planLineList));
		
				job.setDueTime(Double.parseDouble(String.valueOf(mapDueTime.get(ModulePlannerUtility.formatterNoSlash.format(obj.getShiftDate())))));
				job.setId(Long.valueOf(idx));
				job.setJobId(obj.getJobId());
//	    		job.setLine(getLineByLineNo(obj.getLine(), planLineList));
				job.setModel(getModelByPartNo(obj.getPartNo(), modelList));
				job.setPlanQty(Integer.parseInt(obj.getPlanQty()));
				job.setForecastQty(Integer.parseInt(obj.getPlanQty()));
				job.setJobType(obj.getJobType());
				job.setSetup(setup);
				job.setAssignLine(getLineByLineNo(obj.getLine(), planLineList));
				job.setSite(obj.getSite());
				job.setFab(obj.getFab());
				job.setArea(obj.getArea());
				job.setLineNo(obj.getLine());
				job.setShift_date(obj.getShiftDate());
				job.setShiftTypeString(obj.getShift());
				job.setChangeLevel(obj.getChangeLevel());
				job.setPossibleLineList(possibleLineList);//add by avonchung 20190304

				double assignStartTime = ModulePlannerUtility.calcShiftTime(planStart, obj.getShiftDate(), obj.getShift());
				job.setAssignStartTime(assignStartTime);
				job.setReadyTime(assignStartTime);
				job.setPlanStartDate(planStart);
				job.setShiftDstart(shiftDstart);
				job.setEqpCapa(obj.getCapa());
				job.setGrade(obj.getGrade());
				job.setWoId(obj.getWoId());
				job.setMapParam(mapParam);

				//by line收集相同指定線別的job add by avonchung 20190320
				if (mapPossible.get(obj.getLine()) != null) {
					List<TimeWindowedJob> list = (List<TimeWindowedJob>) mapPossible.get(obj.getLine());
					list.add(job);
					mapPossible.put(obj.getLine(), list);
				}

				jobList.add(job);
				idx++;
			} catch (Exception e) {
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logger.error(errors.toString());
				e.printStackTrace();
			}
		}
		maps.put("JOBS", jobList);
		maps.put("JOBPOSSIBLEMAP", mapPossible);

		logger.info("jobList.size(): " + jobList.size() + " jobList: " + jobList);
		return maps;
	}
	
	private Model getModelByPartNo(String partNo, List<Model> modelList) {
		for(Model model : modelList) {
			if (model.getPartNo().equals(partNo))
				return model;
		}
		return null;
	}
	
	private PlanLine getLineByLineNo(String lineNo, List<PlanLine> planLineList) {
		for(PlanLine line : planLineList) {
			if(line.getLineNo().equals(lineNo))
				return line;
		}
		return null;
	}
	
	private List<PlanLine> getLineByPartNo(String partNo, List<EqpCapa> cEqpCapaList, List<PlanLine> planLineList) {
		List<PlanLine> acceptPlanListList = new ArrayList<>();
		
		for(EqpCapa capa : cEqpCapaList) {
			if(partNo.equals(capa.getEqpCapaId().getPartNo())) {
				String lineNo = capa.getEqpCapaId().getLine();
				
				for(PlanLine plan : planLineList) {
					if(lineNo.equals(plan.getLineNo())) {
						if(!acceptPlanListList.contains(plan)) {
							acceptPlanListList.add(plan);
						}
					}
				}
			}
		}
		return acceptPlanListList;
	}
	
	private List<Capa> getCapaByLine(String line, List<Capa> capaList){
		List<Capa> newCapaList = new ArrayList<>();
		for(Capa capa : capaList) {
			if(line.equals(capa.getLine()))
				newCapaList.add(capa);
		}
		return newCapaList;
	}
	
	private String getLastPartNoByProcessEndTime(List<Object> historicalList, String line) {
		if(historicalList.size() > 0) {
			for(Object historyJob : historicalList) {
				String lineNo = "";
				String jobType = "";
				String partNo = "";
				if(historyJob instanceof Adjustment) {
					lineNo = ((Adjustment)historyJob).getAdjustmentId().getLine();
					jobType = ((Adjustment)historyJob).getAdjustmentId().getJobType();
					partNo = ((Adjustment)historyJob).getPartNo();
				}else if(historyJob instanceof RJobDashboard) {
					lineNo = ((RJobDashboard)historyJob).getLine();
					jobType = ((RJobDashboard)historyJob).getJobType();
					partNo = ((RJobDashboard)historyJob).getPartNo();
				}
				if(line.equals(lineNo) && !ModulePlannerUtility.SPECIFIC_JOB_TYPE.contains(jobType)) {
					return partNo;
				}
			}
		}
		return null;
	}
	
	

	public Map<String, Object> startPlan(AppData data) throws Exception{
		Map<String, Object> mapPlanResult = new HashMap<>();
		
		AppData appData = init(data);
		List<PlanLine> planLineList = getLines(appData.getcFacLineList(), appData.getHistoricalList(), appData.getPlanLineList(), appData.getModelList(), appData.getCapaList(), appData.getMapParam());
		appData.setPlanLineList(planLineList);
		
		Map<String, Object> maps = getJobs(appData.getSetup(), appData.getrJobPlanList(), appData.getrJobSpecialList(), appData.getrJobEqpmList(), appData.getPlanLineList(), appData.getModelList(), appData.getPlanStart(), appData.getMapDueTime(), appData.getListPlanDate(), appData.getShiftDstart(), appData.getcEqpCapaList(), appData.getMapParam());
		List<TimeWindowedJob> jobs = (List<TimeWindowedJob>) maps.get("JOBS");
		Map<String, Object> mapPossible = (Map<String, Object>) maps.get("JOBPOSSIBLEMAP");

		logger.info(jobs.size() + " jobs===> " + jobs);
		logger.info(mapPossible.size() + " mapPossible===> " + mapPossible);
		//設定該job的前後possible job add by avonchung 20190320
		for(TimeWindowedJob tJob : jobs) {
			if(mapPossible.containsKey(tJob.getLineNo())){
				List possibleJobList = (List) mapPossible.get(tJob.getLineNo());
				tJob.setPossibleJobList(possibleJobList);
//				logger.info("posc" + possibleJobList.size() + " possibleJobList: " + possibleJobList);
			}
		}

		if(jobs.size() > 0) {
			//取得班別index list
			LocalDate planStartDate = (LocalDate) ((Map)(appData.getMapParam().get("plan_start_date"))).get("in_value1");
			LocalDate planEndDate = ((LocalDate) ((Map)(appData.getMapParam().get("plan_end_date"))).get("in_value1")).plusDays(1);
			boolean isSortByDateASC = true;
			List<Shift> shiftList = ModulePlannerUtility.generateShiftList(planStartDate, planEndDate, appData.getMapParam(), isSortByDateASC);
			
			doSchedule(mapPlanResult, appData, planLineList, jobs, shiftList);
		}else {
			throw new Exception("No Jobs, jobs.size() is 0");
		}
		return mapPlanResult;
	}
	
	protected void doSchedule(Map<String, Object> mapPlanResult, AppData appData, List<PlanLine> planLineList, List<TimeWindowedJob> jobs, List<Shift> shiftList) throws Exception {
		InputStream ins = AppJI.class.getResourceAsStream("/com/lcm/opta/moduleplanner/taskassignmentConfiguration.xml");
		SolverFactory<TaskAssignmentSolution> solverFactory = SolverFactory.createFromXmlInputStream(ins);

//		String planSite = (String) ((Map) appData.getMapParam().get("site")).get("in_value1");
//		String className = "com.lcm.domain.opta.moduleplanner.solver.score.TaskEasyScoreCalculator_" + planSite;
//		Class<?> easyScoreClassName = Class.forName(className);
//		solverFactory.getSolverConfig().getScoreDirectorFactoryConfig().setEasyScoreCalculatorClass((Class<? extends EasyScoreCalculator>) easyScoreClassName);

		String drlName = "com/lcm/opta/moduleplanner/taskAssignmentDoolsMA.drl";
		List<String> listDrl = new ArrayList<>();
		listDrl.add(drlName);
		solverFactory.getSolverConfig().getScoreDirectorFactoryConfig().setScoreDrlList(listDrl);
		long lSencondsSpentLimit = solverFactory.getSolverConfig().getTerminationConfig().getSecondsSpentLimit();

		//getSecondsSpentLimit吃系統參數 JoshLai@20190614+
		try {
			Map<String, Object> mapParam = appData.getMapParam();
			String fabChangeGroup = appData.getFabChangeGroup();
			String inValue3 = null;
			if(mapParam.get(fabChangeGroup) instanceof Map)
				inValue3 = String.valueOf(((Map) mapParam.get(fabChangeGroup)).get("in_value3"));
			else if(mapParam.get(fabChangeGroup) instanceof List)
				inValue3 = String.valueOf(((Map)((List) mapParam.get(fabChangeGroup)).get(0)).get("in_value3"));
			if (fabChangeGroup != null && mapParam.get(fabChangeGroup) != null && inValue3 != null) {
				lSencondsSpentLimit = Long.parseLong(inValue3);
			}
		}catch(Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
			e.printStackTrace();
		}

		logger.info("lSencondsSpentLimit: " + lSencondsSpentLimit);
		logger.info("appDate getPlanDay==> " + appData.getPlanDay());
		BigDecimal bSpentLimit = new BigDecimal(appData.getPlanDay()).multiply(new BigDecimal(lSencondsSpentLimit));
		solverFactory.getSolverConfig().getTerminationConfig().setSecondsSpentLimit(bSpentLimit.longValue());

		long lStart = System.currentTimeMillis();
		solver = solverFactory.buildSolver();
		ScoreDirectorFactory<TaskAssignmentSolution> scoreDirectorFactory = solver.getScoreDirectorFactory();
		guiScoreDirector = scoreDirectorFactory.buildScoreDirector();
	
		TaskAssignmentSolution unassignment = new TaskAssignmentSolution(planLineList, jobs, appData.getModelList(), shiftList, appData.getcFacConstraintCapaList(), appData.getMapParam(), appData.getWoxxList(), appData.getcModChangeList(), appData.getHistoricalList());
		guiScoreDirector.setWorkingSolution(unassignment);

		TaskAssignmentSolution assigned = solver.solve(unassignment);
		long lEnd = System.currentTimeMillis() - lStart;
		logger.info("App solver Time elapsed: " + lEnd / 1000 + " seconds");

		//規則結果輸出
//		logger.debug("Drools Solve Result.");
		guiScoreDirector.setWorkingSolution(assigned);
//		logger.debug(solver.explainBestScore());
		List<SJobScore> scoreList = genConstraintMatch(guiScoreDirector);
		mapPlanResult.put("SCORE_LIST", scoreList);
	
		List<SJobDashboard> listPlanResult = new ArrayList<>();
		List<PlanLine> machinesAssigned = assigned.getJobList().stream().map(Job::getLine).distinct().collect(Collectors.toList());
		logger.info(machinesAssigned.size() + " machinesAssigned: " + machinesAssigned);

		List<Woxx> mapParamWoxxList = (List<Woxx>) appData.getMapParam().get("woxx_list");
		List<Woxx> woxxList = new ArrayList<>(mapParamWoxxList);
		
		lStart = System.currentTimeMillis();
		//把全部的Job放到List裡面 JoshLai@20190430+
		List<TimeWindowedJob> timeWindowedJobList = new ArrayList<>();
		for (PlanLine line : machinesAssigned) {
			List<TimeWindowedJob> jobsInLine = assigned.getJobList().stream().filter(x -> x.getLine().equals(line)).collect(Collectors.toList());
			timeWindowedJobList.addAll(jobsInLine);
		}
		
		logger.info(timeWindowedJobList.size() + " before sort: " + timeWindowedJobList);
		
		//排序
		Comparator<TimeWindowedJob> comparator = Comparator.comparing(TimeWindowedJob::getLineNo, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(TimeWindowedJob::getStartTime, Comparator.nullsFirst(Comparator.naturalOrder()));
		Collections.sort(timeWindowedJobList, comparator);
		
		ModulePlannerUtility.setChangeDurationByTimeWindowedJob(timeWindowedJobList);
		logger.info(timeWindowedJobList.size() + " timeWindowedJobList: " + timeWindowedJobList);
		List<SJobDashboard> sJobDashboardList = new ArrayList<>();
		for(TimeWindowedJob job : timeWindowedJobList) {
			//TimeWindowedJob轉SJobDashboard
			List<SJobDashboard> tempList = ModulePlannerUtility.splitByShift(job, appData.getMapParam(), true);
			sJobDashboardList.addAll(tempList);
		}
		logger.info(sJobDashboardList.size() + " after splitByShift: " + sJobDashboardList);
		
		//跨班換線拆班
		List<SJobDashboard> splittedByChangeTimeJobList = ModulePlannerUtility.splitJobByChangeTime(sJobDashboardList, appData.getMapParam(), true, false, true);
		logger.info(splittedByChangeTimeJobList.size() + " after splitJobByChangeTime: " + splittedByChangeTimeJobList);
		
		listPlanResult.addAll(splittedByChangeTimeJobList);
		ModulePlannerUtility.sortSJobDashboardList(listPlanResult);
		ModulePlannerUtility.updateJobIdSeq(listPlanResult);
		logger.info(listPlanResult.size() + " after sortSJobDashboardList: " + listPlanResult);
		
		mapPlanResult.put("LIST_PLAN_RESULT", listPlanResult);
		lEnd = System.currentTimeMillis() - lStart;
		logger.info("App doPlanResultProcess Time elapsed: " + lEnd / 1000 + " seconds");
	}
	
	public void doProblemFactChanges(List<ProblemFactChange<TaskAssignmentSolution>> problemFactChanges) {
		if (solver.isSolving()) {
			solver.addProblemFactChanges(problemFactChanges);
		} else {
			for(ProblemFactChange<TaskAssignmentSolution> problemFactChange : problemFactChanges) {
				problemFactChange.doChange(guiScoreDirector);
			}
			guiScoreDirector.calculateScore();
		}
	}
	
	private List<SJobScore> genConstraintMatch(ScoreDirector<TaskAssignmentSolution> guiScoreDirector) throws CloneNotSupportedException {
		return ModulePlannerUtility.genConstraintMatch(guiScoreDirector);
	}
}
