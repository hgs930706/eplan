package com.lcm.domain.opta.moduleplanner.app;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import javax.xml.crypto.Data;

import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.constraint.ConstraintMatch;
import org.optaplanner.core.api.score.constraint.ConstraintMatchTotal;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.impl.score.director.ScoreDirector;
import org.optaplanner.core.impl.score.director.ScoreDirectorFactory;
import org.optaplanner.core.impl.solver.ProblemFactChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lcm.domain.Adjustment;
import com.lcm.domain.CFacConstraintCapa;
import com.lcm.domain.EqpCapa;
import com.lcm.domain.EquipmentPreventiveMaintenance;
import com.lcm.domain.Line;
import com.lcm.domain.Plan;
import com.lcm.domain.RJobDashboard;
import com.lcm.domain.SJobDashboard;
import com.lcm.domain.SJobScore;
import com.lcm.domain.SJobScoreId;
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
import com.lcm.domain.opta.moduleplanner.solver.score.SJobDashboardKey;
import com.lcm.domain.opta.moduleplanner.utility.ModulePlannerUtility;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

public class AppJI {  
	private static final Logger logger = LoggerFactory.getLogger(AppJI.class);
	
	private Solver<TaskAssignmentSolution> solver;
	private ScoreDirector<TaskAssignmentSolution> guiScoreDirector;
	
	public AppJI() {
		
	}
	
	public static void main(String[] args) {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		StatusPrinter.print(lc);
	}

	private AppData init(AppData data) throws Exception {
		LocalDate planStartDate = (LocalDate) ((Map<String, Object>)data.getMapParam().get("plan_start_date")).get("in_value1");
		int planDay = Integer.parseInt(String.valueOf(((Map<String, Object>)data.getMapParam().get("plan_day")).get("in_value1")));
		int shiftDstart = Integer.parseInt(String.valueOf(((Map<String, Object>)data.getMapParam().get("shift_d_start")).get("in_value1")));
	
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
		Map<String, Object> followPPCplan = (Map<String, Object>) data.getMapParam().get("follow_ppc_plan");
		if(followPPCplan!=null) {
			if("continue".equals(followPPCplan.get("in_value1"))) {
				//取得連續生產的rJobPlan,需計算連續生產Job累計數量
				//不需合併連續生產,改照原PPC Plan但考慮落後超前
				//rJobPlanList = ModulePlannerUtility.calcContinuousRJobPlan(rJobPlanList, historicalJobList, rJobSpecialList, cEqpCapaList, mapParam);
			}if("full_load".equals(followPPCplan.get("in_value1"))) {
				//根據原來的PPC Plan排產
				rJobPlanList = ModulePlannerUtility.calcFullLoadRJobPlan(rJobPlanList, data.getrJobSpecialList(), data.getcEqpCapaList(), data.getHistoricalList(), data.getMapParam());
			}
		}else
			rJobPlanList = ModulePlannerUtility.calcFullLoadRJobPlan(rJobPlanList, data.getrJobSpecialList(), data.getcEqpCapaList(), data.getHistoricalList(), data.getMapParam());
	
		logger.info(rJobPlanList.size()+ " final rJobPlanList: " + rJobPlanList);
		data.setrJobPlanList(rJobPlanList);
		//為了讓SJobDashboard也能拿取PPC&Fab pc capa JoshLai@20190704+
		Map<String, Object> mapCapa = ModulePlannerUtility.calcCapaMap(rJobPlanList);
		Map<String, Object> mapParam = data.getMapParam();
		mapParam.put("capa_map", mapCapa);
		data.setMapParam(mapParam);
		logger.info(data.getMapParam().size() + " mapParam: " + data.getMapParam());
	
		AppData appData = new AppData(data.getcFacLineList(), data.getcModModelList(), data.getcEqpCapaList(),
				data.getrJobPlanList(), data.getrJobSpecialList(), data.getrJobEqpmList(),
				data.getcFacConstraintCapaList(), data.getWoxxList(),
				data.getcModChangeList(), data.getMapParam(), data.getSite(), data.getShiftDateMap(),
				data.getShiftList(), data.getSetup(), data.gethJobDashboardList(), data.getScoreList(),
				data.getPlanLineList(), modelList, capaList, data.getListPlanResult(), planStartDate, planDay, mapDueTime,
				listPlanDate, shiftDstart, data.getHistoricalList(), data.getFabChangeGroup());
		return appData;
	}


	public Map<String, Object> startPlan(AppData data) throws Exception{
		Map<String, Object> mapPlanResult = new HashMap<>();
		
		AppData appData = init(data);
		List<PlanLine> planLineList = getLines(appData.getcFacLineList(), appData.getHistoricalList(), appData.getPlanLineList(), appData.getModelList(), appData.getCapaList(), appData.getMapParam());
		appData.setPlanLineList(planLineList);
	
		Map<String, Object> maps = getJobs(appData.getSetup(), appData.getrJobPlanList(), appData.getrJobSpecialList(), appData.getrJobEqpmList(), appData.getPlanLineList(), appData.getModelList(), appData.getPlanStart(), appData.getMapDueTime(), appData.getListPlanDate(), appData.getShiftDstart(), appData.getMapParam());
		List<TimeWindowedJob> jobs = (List<TimeWindowedJob>) maps.get("JOBS");
		Map<String, Object> mapPossible = (Map<String, Object>) maps.get("JOBPOSSIBLEMAP");

		//設定該job的前後possible job add by avonchung 20190320
		for(TimeWindowedJob tJob : jobs) {
			if(mapPossible.containsKey(tJob.getLineNo())){
				List possibleJobList = (List) mapPossible.get(tJob.getLineNo());
				tJob.setPossibleJobList(possibleJobList);
				logger.info("possibleJobList.size(): " + possibleJobList.size() + " possibleJobList: " + possibleJobList);
			}
		}

		if(jobs.size() > 0) {
			//負載平衡
			doBalanceJob(jobs, appData.getPlanStart(), appData.getMapParam());
	
			//取得班別index list
			LocalDate planStartDate = (LocalDate) ((Map)(appData.getMapParam().get("plan_start_date"))).get("in_value1");
			LocalDate planEndDate = ((LocalDate) ((Map)(appData.getMapParam().get("plan_end_date"))).get("in_value1")).plusDays(1);
			boolean isSortByDateASC = true;
			List<Shift> shiftList = ModulePlannerUtility.generateShiftList(planStartDate, planEndDate, appData.getMapParam(), isSortByDateASC);

			//指定換線
			List<Double> assignSetuptimeList = ModulePlannerUtility.generateSetupTimeList(shiftList, appData.getMapParam());
			appData.getSetup().setAssignSetuptimeList(assignSetuptimeList);
			logger.debug(assignSetuptimeList.size() + " assignSetuptimeList: " + assignSetuptimeList);

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

		String drlName = "com/lcm/opta/moduleplanner/taskAssignmentDoolsJI.drl";
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
//        	logger.debug("============ lineNo:" + line.getLineNo() + " ============");
			List<TimeWindowedJob> jobsInLine = assigned.getJobList().stream().filter(x -> x.getLine().equals(line)).collect(Collectors.toList());
			timeWindowedJobList.addAll(jobsInLine);
		}

		//整合到function裡面 JoshLai@20190326
		List<SJobDashboard> tempList = ModulePlannerUtility.doPlanResultProcess(timeWindowedJobList, appData.getMapParam(), woxxList, true);
		listPlanResult.addAll(tempList);
			
		
		//直接將TimedWindowedJob轉成SJobDashboard,不做重疊換線,跨班換線,換線影響等處理 JoshLai@20190627+
		boolean isJustSplitSJob = appData.getMapParam().get("just_split_sjob")!=null ? true : false;
		if(!isJustSplitSJob) {
			logger.info(listPlanResult.size()+ " before calcSetupAffect: " + listPlanResult);
			//換線影響
			listPlanResult = ModulePlannerUtility.calcSetupAffect(listPlanResult, appData.getMapParam(), true);
			logger.info(listPlanResult.size() + " after calcSetupAffect: " + listPlanResult);
			
			//PM當班只排量 100片(生產下一班的model)
			listPlanResult = ModulePlannerUtility.calcPMaddQty(listPlanResult, appData.getMapParam(), true);
			logger.info(listPlanResult.size() + " after calcPMaddQty: " + listPlanResult);
			
			//S01 Non-Schedule --> 連續停三個班且第四班滿載，第三班要先排100片  JoshLai@20190704+
			listPlanResult = ModulePlannerUtility.calcNonScheduleAddQty(listPlanResult, appData.getMapParam(), true);
			logger.info(listPlanResult.size() + " after calcNonScheduleAddQty: " + listPlanResult);
			
			//S01前一天排NON_SCHEDULE & 沒ppc 排程，才要提早排量(不需扣量)。不需要扣提早排的量 JoshLai@20190705+
			listPlanResult = ModulePlannerUtility.calcNonScheduleAndNoPPCplan(listPlanResult, appData.getMapParam(), true);
			logger.info(listPlanResult.size() + " after calcNonScheduleAndNoPPCplan: " + listPlanResult);
			
			//將工單數量扣除,才是實際要生產的數量 JoshLai@20190410+
			//暫時不須扣需量,JoshLai@20190507-
			//woxxList = ModulePlannerUtility.substarctWomIQty(woxxList, appData.getHistoricalList(), appData.getMapParam());
			
			//換線影響完再匹配工單 JoshLai@20190329
			logger.info(woxxList.size() + " splitWoom woxxList: " + woxxList);
			listPlanResult = ModulePlannerUtility.splitJobByWom(listPlanResult, appData.getHistoricalList(), woxxList, appData.getMapParam());
			
			logger.info(listPlanResult.size() + " before calcPullToFullDShift: " + listPlanResult);
			//若為相同part no且後一天有量，則允許pull。再根據” FullDShift“僅加量到白班(D)為止 JoshLai@20190801+
			listPlanResult = ModulePlannerUtility.calcPullToFullDShift(listPlanResult, appData.getrJobPlanList(), appData.getMapParam());

			logger.info(listPlanResult.size() + " after calcPullToFullDShift: " + listPlanResult);
		}
		mapPlanResult.put("LIST_PLAN_RESULT", listPlanResult);
		lEnd = System.currentTimeMillis() - lStart;
		logger.info("App doPlanResultProcess Time elapsed: " + lEnd / 1000 + " seconds");
	}


	private List<PlanLine> getLines(List<Line> cFacLineList, List<Object> historicalList, List<PlanLine> planLineList, List<Model> modelList, List<Capa> capaList, Map<String, Object> mapParam) throws CloneNotSupportedException {
		planLineList = new ArrayList<PlanLine>();
	
		logger.info(historicalList.size() + " historicalList: " + historicalList);
		int i=0;
		for(Line line : cFacLineList) {
			Map<Model, Map<String, Object>> capa = new HashMap<Model, Map<String ,Object>>();
			for(Capa obj : getCapaByLine(line.getLine(), capaList)) {
				Map<String, Object> capaTmp = new HashMap<String, Object>();
				capaTmp.put("PPC_CAPA", obj.getPpcCapa());
				capaTmp.put("FAB_PC_CAPA", obj.getFabPcCapa());
			
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


	private Map getJobs(Setup setup, List<Plan> rJobPlanList, List<Special> rJobSpecialList, List<EquipmentPreventiveMaintenance> rJobEqpmList, List<PlanLine> planLineList, List<Model> modelList, LocalDate planStart, Map<String, Object> mapDueTime, List<LocalDate> listPlanDate, int shiftDstart, Map<String, Object> mapParam){
		Map<String, Object> maps = new HashMap<>();
		List<TimeWindowedJob> jobList = new ArrayList<TimeWindowedJob>();
	
		int idx = 0;
		int listIdxCounter = 0;
		String firstFlag = "";
		String nextFlag = "";
	
		//add by avonchung 20190304
		ArrayList possibleLineList = new ArrayList<PlanLine>();
	
		//by line收集相同指定線別的job add by avonchung 20190320
		Map<String, Object> mapPossible = new HashMap<>();
		for(PlanLine line : planLineList) {
			String lineNo = line.getLineNo();
			List<TimeWindowedJob> possibleJobList = new ArrayList<>();
			mapPossible.put(lineNo, possibleJobList);
		}
	
		for(Plan obj : rJobPlanList) {
			//只取planDate區間的plan
			if(!listPlanDate.contains(obj.getShiftDate())) {
				continue;
			}
			int advQty = 0;
			try {
				TimeWindowedJob job = new TimeWindowedJob();
			
				//add by avonchung 20190304
				possibleLineList = new ArrayList<PlanLine>();
				possibleLineList.add(getLineByLineNo(obj.getLine(), planLineList));
			
				double readyTime = ModulePlannerUtility.calcNumByDateTime(planStart, shiftDstart, obj.getShiftDate());
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

				//將PPC Plan改為連續天數加總後，已不再計算後一天有量的規則 mark by JoshLai@20190419
				/*若后一天有量：
                	当天满载(plan qty >= ppc_capa)：看當天+後一天若>=Fab pc capa則排 Fab pc capa；若<=Fab pc capa則排當天+後一天即可；
                	当天不满载(plan qty < ppc_capa)：直接排plan量不加量
            	若后一天没有量：
            		直接排plan量（不论是否满载）*/
                /*int planQty = Integer.parseInt(obj.getPlanQty());
                firstFlag = obj.getLine()+obj.getPartNo()+obj.getGrade();
                int nextPlanQty = 0;
                if(listIdxCounter+1 < rJobPlanList.size()) {
                	nextFlag = rJobPlanList.get(listIdxCounter+1).getLine()+rJobPlanList.get(listIdxCounter+1).getPartNo()+ rJobPlanList.get(listIdxCounter+1).getGrade();
                	nextPlanQty = Integer.parseInt(rJobPlanList.get(listIdxCounter+1).getPlanQty());
                }
                if(firstFlag.equals(nextFlag)) {
                	if(planQty >= obj.getCapa().getPpcCapa()) {
                		if((planQty + nextPlanQty) >= obj.getCapa().getFabPcCapa()) {
                			planQty = obj.getCapa().getFabPcCapa();
                		}else
                			planQty = planQty + nextPlanQty;
                	}
                }
                job.setPlanQty(planQty);
                if(planQty > Integer.parseInt(obj.getPlanQty())) {
                	advQty = planQty - Integer.parseInt(obj.getPlanQty());
                }
                
                //扣除specail job qty
                int splQty = getQtyFromSPLJob(obj, rJobSpecialList);
                int forecastQty = planQty - splQty - advQty;
                logger.debug("line: " + obj.getLine()+ " splQty: " + splQty + " planQty: " + job.getPlanQty()
                				+ " planQty: " + planQty + " forecastQty: " + forecastQty + " advQty: " + advQty);*/
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
				job.setIsAddTo(0);
				job.setPlanStartDate(planStart);
				job.setShiftDstart(shiftDstart);
				job.setEqpCapa(obj.getCapa());
				job.setGrade(obj.getGrade());
				job.setPossibleLineList(possibleLineList);// add by avonchung 20190304
				job.setMapParam(mapParam);

				//by line收集相同指定線別的job add by avonchung 20190320
				if (mapPossible.get(obj.getLine()) != null && listPlanDate.contains(obj.getShiftDate())) {
					List<TimeWindowedJob> list = (List<TimeWindowedJob>) mapPossible.get(obj.getLine());
					list.add(job);
					mapPossible.put(obj.getLine(), list);
				}

				//只取planDate
				//if(listPlanDate.contains(obj.getShiftDate())) {
					/*//把多加的量，拆成另一個Job(is_add_to =Y)
					if(advQty > 0) {
						logger.debug("adv qty job: " +job + " ");
						jobList.addAll(splitAdvQtyJob(job, splQty, advQty));
					}else {
						jobList.add(job);
					}
					idx++;
				}*/
				jobList.add(job);
				idx++;
				listIdxCounter++;
			}catch(Exception e) {
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logger.error(errors.toString());
				e.printStackTrace();
			}
		}
	
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
				job.setIsAddTo(0);
				job.setPlanStartDate(planStart);
				job.setShiftDstart(shiftDstart);
				job.setMapParam(mapParam);
	            
	            //by line收集相同指定線別的job add by avonchung 20190320
	            if(mapPossible.get(obj.getLine()) != null) {
                	List<TimeWindowedJob> list = (List<TimeWindowedJob>) mapPossible.get(obj.getLine());
                	list.add(job);
                	mapPossible.put(obj.getLine(), list);
                }
	            
	            jobList.add(job);
	    		idx++;
    		}catch(Exception e) {
    			StringWriter errors = new StringWriter();
    			e.printStackTrace(new PrintWriter(errors));
    			logger.error(e.toString());
    		}
    	}
    	
    	//Special Job
    	for(Special obj : rJobSpecialList) {
    		try {
	    		TimeWindowedJob job = new TimeWindowedJob();
	    		
	    		//add by avonchung 20190304
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
	            job.setIsAddTo(0);
	            job.setPlanStartDate(planStart);
	            job.setShiftDstart(shiftDstart);
	            job.setEqpCapa(obj.getCapa());
	            job.setGrade(obj.getGrade());
	            job.setWoId(obj.getWoId());
	            job.setMapParam(mapParam);
	            
//	            logger.debug("planStart: " + planStart + " getShiftDate: " + obj.getShiftDate() + " getShift: " + obj.getShift());
	            
	            
	            //by line收集相同指定線別的job add by avonchung 20190320
	            if(mapPossible.get(obj.getLine()) != null) {
                	List<TimeWindowedJob> list = (List<TimeWindowedJob>) mapPossible.get(obj.getLine());
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
	
	
	private List<Capa> getCapaByLine(String line, List<Capa> capaList){
		List<Capa> newCapaList = new ArrayList<>();
		for(Capa capa : capaList) {
			if(line.equals(capa.getLine()))
				newCapaList.add(capa);
		}
		return newCapaList;
	}
	
	private PlanLine getLineByLineNo(String lineNo, List<PlanLine> planLineList) {
		for(PlanLine line : planLineList) {
			if(lineNo.equals(line.getLineNo()))
				return line;
		}
		return null;
	}
	
	private void doBalanceJob(List<TimeWindowedJob> jobList, LocalDate planStart, Map<String, Object> mapParam) {
		Map<String, Object> mapJobBalance = (Map<String, Object>) mapParam.get("job_balance");
		if(mapJobBalance !=null) {
			String param = (String) (mapJobBalance).get("in_value1");
			if(param != null) {
				String[] params = param.split(":");
				if(param.replace(":", "").matches("\\d{2}")
						&& Integer.parseInt(params[0]) + Integer.parseInt(params[1]) == 10) {
					
					String[] balanceStandard = param.split(":");
					List<Long> jobIndexList = new ArrayList<>();
					
					for(TimeWindowedJob job : jobList) {
						if(job.getEqpCapa() != null && "PROD".equals(job.getJobType())) {
							int ppcCapa = job.getEqpCapa().getPpcCapa();
							int fabPcCapa = job.getEqpCapa().getFabPcCapa()/2;
							
							if(job.getPlanQty() != ppcCapa && job.getPlanQty() <= fabPcCapa/2) {
								jobIndexList.add(job.getId());
							}
						}
					}
					logger.info(jobIndexList.size() + " jobIndexList: " + jobIndexList);
					
					List<Long> shiftListD = new ArrayList<>();
					List<Long> shiftListN = new ArrayList<>();
					BigDecimal ratioD = new BigDecimal(balanceStandard[0]).divide(new BigDecimal(10), 1, BigDecimal.ROUND_HALF_UP);
					BigDecimal ratioN = new BigDecimal(balanceStandard[1]).divide(new BigDecimal(10), 1, BigDecimal.ROUND_HALF_UP);
					int shiftDsize = 0;
					int shiftNsize = 0;
					BigDecimal bCnt = ratioD.multiply(new BigDecimal(jobIndexList.size())).setScale(0, BigDecimal.ROUND_HALF_UP);
					shiftDsize = bCnt.intValue();
					shiftNsize = jobIndexList.size() - shiftDsize;
					
					logger.info("ratioD: " + ratioD.doubleValue() + " ratioN: " + ratioN.doubleValue() 
									+ " shiftDsize: " + shiftDsize + " shiftNsize: " + shiftNsize);
					
					//挑選不重複的List index,再從這些index取TimeWindowedJob Id
					int[] randomNums = ThreadLocalRandom.current().ints(0, jobIndexList.size()).distinct().limit(shiftDsize).toArray();
					for(int id : randomNums) {
						shiftListD.add(jobIndexList.get(id));
					}
					for(Long id : jobIndexList) {
						if(!shiftListD.contains(id)) {
							shiftListN.add(id);
						}
					}
					logger.info("randomNums len: " + randomNums.length 
								+ " shiftListD("+shiftListD.size()+"): " + shiftListD
								+ " shiftListN("+shiftListN.size()+"): " + shiftListN);
					
					//放在該ShiftDate夜班
					for(Long id : shiftListN) {
						for(TimeWindowedJob tJob : jobList) {
							if(tJob.getId() == id) {
								double readyTime = ModulePlannerUtility.calcShiftTime(planStart, tJob.getShift_date(), "N");
								logger.info("Let TimeWindowedJob id: " + tJob.getId() 
											+ " readyTime: " + tJob.getReadyTime() + " changeTo: " + readyTime);
								tJob.setReadyTime(readyTime);
							}
						}
					}
				}else {
					logger.info("job_balance value("+param+") invalid!");
				}
			}
			logger.info(jobList.size() + " job_balance list: " + jobList);
		}
	}
	
	private List<SJobScore> genConstraintMatch(ScoreDirector<TaskAssignmentSolution> guiScoreDirector) throws CloneNotSupportedException {
		return ModulePlannerUtility.genConstraintMatch(guiScoreDirector);
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
}
