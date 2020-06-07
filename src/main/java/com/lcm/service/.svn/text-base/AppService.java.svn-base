package com.lcm.service;

import com.lcm.domain.*;
import com.lcm.domain.opta.moduleplanner.app.AppJI;
import com.lcm.domain.opta.moduleplanner.app.AppMA;
import com.lcm.domain.opta.moduleplanner.app.BenchmarkAppJI;
import com.lcm.domain.opta.moduleplanner.app.BenchmarkAppMA;
import com.lcm.domain.opta.moduleplanner.domain.AppData;
import com.lcm.domain.opta.moduleplanner.domain.TaskAssignmentSolution;
import com.lcm.domain.opta.moduleplanner.domain.TimeWindowedJob;
import com.lcm.domain.opta.moduleplanner.utility.ModulePlannerUtility;
import com.lcm.repository.*;
import com.lcm.web.rest.param.JobParam;
import com.lcm.web.rest.param.ManualRunParam;
import org.optaplanner.core.impl.solver.ProblemFactChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppService {
	private static final Logger logger = LoggerFactory.getLogger(AppService.class);
	
	private final ModModelRepository modModelRepository;
	private final LineRepository lineRepository;
	private final EqpCapaRepository eqpCapaRepository;
	private final RJobDashboardRepository rJobDashboardRepository;
	private final PlanRepository planRepository;
	private final ParParameterRepository parParameterRepository;
	private final SJobDashboardRepository sJobDashboardRepository;
	private final SpecialRepository specialRepository;
	private final EquipmentPreventiveMaintenanceRepository equipmentPreventiveMaintenanceRepository;
	private final CFacConstraintCapaRepository cFacConstraintCapaRepository;
	private final CFacScoreReposity cFacScoreReposity;
	private final SJobScoreRepository sJobScoreRepository;
	private final WoxxRepository woxxRepository;
	private final ModChangeRepository cModChangeRepository;
	private final AdjustmentRepository adjustmentRepository;
	private final SLotOpwpRepository sLotOpwpRepository;
	
	private AppJI appS01G1JI, appS02G1JI, appS02G2JI, appS06G1JI, appS06G2JI, appS06G3JI, appS11G1JI, appS11G2JI;
	private AppMA appS01G1MA, appS02G1MA, appS02G2MA, appS06G1MA, appS06G2MA, appS06G3MA, appS11G1MA, appS11G2MA;
	
	public AppService(ModModelRepository modModelRepository, LineRepository lineRepository,
			EqpCapaRepository eqpCapaRepository, RJobDashboardRepository rJobDashboardRepository,
			PlanRepository planRepository, ParParameterRepository parParameterRepository,
			SJobDashboardRepository sJobDashboardRepository, SpecialRepository specialRepository,
			EquipmentPreventiveMaintenanceRepository equipmentPreventiveMaintenanceRepository, CFacConstraintCapaRepository cFacConstraintCapaRepository,
			CFacScoreReposity cFacScoreReposity,
			SJobScoreRepository sJobScoreRepository, WoxxRepository woxxRepository,
			ModChangeRepository cModChangeRepository, AdjustmentRepository adjustmentRepository, SLotOpwpRepository sLotOpwpRepository) {
		super();
		this.modModelRepository = modModelRepository;
		this.lineRepository = lineRepository;
		this.eqpCapaRepository = eqpCapaRepository;
		this.rJobDashboardRepository = rJobDashboardRepository;
		this.planRepository = planRepository;
		this.parParameterRepository = parParameterRepository;
		this.sJobDashboardRepository = sJobDashboardRepository;
		this.specialRepository = specialRepository;
		this.equipmentPreventiveMaintenanceRepository = equipmentPreventiveMaintenanceRepository;
		this.cFacConstraintCapaRepository = cFacConstraintCapaRepository;
		this.cFacScoreReposity = cFacScoreReposity;
		this.sJobScoreRepository = sJobScoreRepository;
		this.woxxRepository = woxxRepository;
		this.cModChangeRepository = cModChangeRepository;
		this.adjustmentRepository = adjustmentRepository;
		this.sLotOpwpRepository = sLotOpwpRepository;
		
		appS01G1JI = new AppJI();
		appS02G1JI = new AppJI();
		appS02G2JI = new AppJI();
		appS06G1JI = new AppJI();
		appS06G2JI = new AppJI();
		appS06G3JI = new AppJI();
		appS11G1JI = new AppJI();
		appS11G2JI = new AppJI();
		
		appS01G1MA = new AppMA();
		appS02G1MA = new AppMA();
		appS02G2MA = new AppMA();
		appS06G1MA = new AppMA();
		appS06G2MA = new AppMA();
		appS06G3MA = new AppMA();
		appS11G1MA = new AppMA();
		appS11G2MA = new AppMA();
	}

	public ManualRunParam doProcess(ManualRunParam jsonParam) {
		String site = "";
		List<String> fabList = new ArrayList<>();
		List<String> areaList; //add area for JI/MA JoshLai@20190719+
		String planStart = "";
		String planEnd = "";
		String trxId = "";
		
		site = jsonParam.getSite();
		fabList = jsonParam.getFab();
		areaList = jsonParam.getArea();
		planStart = jsonParam.getPlanStart();
		planEnd = jsonParam.getPlanEnd();
		trxId = jsonParam.getTrxId();
		
		//若排產沒有帶area則預設JI JoshLai@20190719+
		if(areaList==null || areaList.isEmpty()) {
			areaList = new ArrayList<>();
			areaList.add("JI");
		}
		
		String status = "COMPLETE";
		String message = "";
		try {
			LocalDate planStartDate = LocalDate.parse(planStart, ModulePlannerUtility.formatter);
			LocalDate planEndDate = LocalDate.parse(planEnd, ModulePlannerUtility.formatter);
			
			AppJI appJI = null;
			AppMA appMA = null;
			String fabChangeGroup = null;
			
			switch(site) {
			case "S01":
				if(areaList.size()==1 && areaList.contains("JI")) //JI在area只能單獨一個選項
					appJI = appS01G1JI;
				else if(areaList.contains("MA")) //MA可能會和FD一起
					appMA = appS01G1MA;
				fabChangeGroup = "fab_change_g1";
				break;
			case "S02":
				if(fabList.contains("1E") || fabList.contains("2E") || fabList.contains("3E")) {
					if(areaList.size()==1 && areaList.contains("JI"))
						appJI = appS02G1JI;
					else if(areaList.contains("MA"))
						appMA = appS02G1MA;
					fabChangeGroup = "fab_change_g1";
				}else if(fabList.contains("3EDT")) {
					if(areaList.size()==1 && areaList.contains("JI"))
						appJI = appS02G2JI;
					else if(areaList.contains("MA"))
						appMA = appS02G2MA;
					fabChangeGroup = "fab_change_g2";
				}
				break;
			case "S06":
				if(fabList.contains("3A")) {
					if(areaList.size()==1 && areaList.contains("JI"))
						appJI = appS06G1JI;
					else if(areaList.contains("MA"))
						appMA = appS06G1MA;
					fabChangeGroup = "fab_change_g1";
				}else if(fabList.contains("4A")) {
					if(areaList.size()==1 && areaList.contains("JI"))
						appJI = appS06G2JI;
					else if(areaList.contains("MA"))
						appMA = appS06G2MA;
					fabChangeGroup = "fab_change_g2";
				}else if(fabList.contains("3B")) {
					if(areaList.size()==1 && areaList.contains("JI"))
						appJI = appS06G3JI;
					else if(areaList.contains("MA"))
						appMA = appS06G3MA;
					fabChangeGroup = "fab_change_g3";
				}
				break;
			case "S11":
				if(fabList.contains("2A")) {
					if(areaList.size()==1 && areaList.contains("JI"))
						appJI = appS11G1JI; //加S11 JoshLai@20190425+
					else if(areaList.contains("MA"))
						appMA = appS11G1MA;
					fabChangeGroup = "fab_change_g1";
				}else if(fabList.contains("2B")) {
					if(areaList.size()==1 && areaList.contains("JI"))
						appJI = appS11G2JI;
					else if(areaList.contains("MA"))
						appMA = appS11G2MA;
					fabChangeGroup = "fab_change_g2";
				}
				break;
			}
			
			long lStartPlan = System.currentTimeMillis();
			logger.info("planStartDate: " + planStartDate + " planEndDate: " + planEndDate);
			AppData data = init(site, fabList, areaList, trxId, planStart, planEnd, fabChangeGroup);
			
			Map<String, Object> mapPlanResult = null;
			if(areaList.size()==1 && areaList.contains("JI")) {
				//logger.info("appJI: " + appJI + " data: " + data);
				mapPlanResult = appJI.startPlan(data);
			}else if(areaList.contains("MA"))
				mapPlanResult = appMA.startPlan(data);
			
			if(mapPlanResult!=null && mapPlanResult.containsKey("LIST_PLAN_RESULT") && mapPlanResult.containsKey("SCORE_LIST")) {
				List<SJobDashboard> listPlanResult = (List<SJobDashboard>) mapPlanResult.get("LIST_PLAN_RESULT");
				List<SJobScore> scoreList = (List<SJobScore>) mapPlanResult.get("SCORE_LIST");
				long lEndPlan = System.currentTimeMillis() - lStartPlan;
				logger.info("AppService startPlan Time elapsed: " + lEndPlan / 1000 + " seconds");
				logger.info("@Transactional listPlanResult("+listPlanResult.size()+") " +listPlanResult);
				
				long lStart = System.currentTimeMillis();
				delete(listPlanResult, site, fabList, areaList, trxId);
				long lEnd = System.currentTimeMillis() - lStart;
				logger.info("AppService Delete Time elapsed: " + lEnd / 1000 + " seconds");
				
				lStart = System.currentTimeMillis();
				save(listPlanResult, scoreList, site, areaList, trxId, planStartDate, planEndDate);
				lEnd = System.currentTimeMillis() - lStart;
				logger.info("AppService Save Time elapsed: " + lEnd / 1000 + " seconds");
			}
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
			e.printStackTrace();
			status = "FAIL";
			message = errors.toString();
		}
		
		ManualRunParam jsonObj = new ManualRunParam();
		jsonObj.setSite(site);
		jsonObj.setFab(fabList);
		jsonObj.setPlanStart(planStart);
		jsonObj.setPlanEnd(planEnd);
		jsonObj.setTrxId(trxId);
		jsonObj.setStatus(status);
		jsonObj.setMessage(message);
		
		logger.info("jsonObj: " + jsonObj.toString());
		return jsonObj;
	}
	
	public ManualRunParam factChange(List<JobParam> jsonParams) {
		AppJI appJI = null;
		AppMA appMA = null;
		String site = jsonParams.get(0).getSite();
		List<String> fabList = jsonParams.get(0).getFab();
		List<String> areaList = jsonParams.get(0).getArea(); //add area for JI/MA JoshLai@20190719+
		String trxId = jsonParams.get(0).getTrxId();
		
		if(areaList==null || areaList.isEmpty()) {
			areaList = new ArrayList<>();
			areaList.add("JI");
		}
		
		switch(site) {
		case "S01":
			if(areaList.size()==1 && areaList.contains("JI"))
				appJI = appS01G1JI;
			else if(areaList.contains("MA"))
				appMA = appS01G1MA;
			break;
		case "S02":
			if(fabList.contains("1E") || fabList.contains("2E") || fabList.contains("3E"))
				if(areaList.size()==1 && areaList.contains("JI"))
					appJI = appS02G1JI;
				else if(areaList.contains("MA"))
					appMA = appS02G1MA;
			else if(fabList.contains("3EDT"))
				appJI = appS02G2JI;
			break;
		case "S06":
			if(fabList.contains("3A"))
				if(areaList.size()==1 && areaList.contains("JI"))
					appJI = appS06G1JI;
				else if(areaList.contains("MA"))
					appMA = appS06G1MA;
			else if(fabList.contains("4A"))
				if(areaList.size()==1 && areaList.contains("JI"))
					appJI = appS06G2JI;
				else if(areaList.contains("MA"))
					appMA = appS06G2MA;
			else if(fabList.contains("3B"))
				if(areaList.size()==1 && areaList.contains("JI"))
					appJI = appS06G3JI;
				else if(areaList.contains("MA"))
					appMA = appS06G3MA;
			break;
		case "S11":
			if(areaList.size()==1 && areaList.contains("JI"))
				appJI = appS11G1JI; //加S11 JoshLai@20190425+
			else if(areaList.contains("MA"))
				appMA = appS11G1MA;
			break;
		}
		
		List<ProblemFactChange<TaskAssignmentSolution>> problemFactChanges = jsonParams.stream().map(temp -> {
			String className = "com.lcm.domain.opta.moduleplanner.optional.realtime." + temp.getFactChange();
			ProblemFactChange<TaskAssignmentSolution> instance = null;
			
			try {
				Class<?> clazz = Class.forName(className);
				switch(clazz.getSimpleName()) {
				case "JobPinnedProblemFactChange":
				case "JobDeleteProblemFactChange":
					TimeWindowedJob job = new TimeWindowedJob();
					job.setId(temp.getId());
					Constructor<?> constructor = clazz.getConstructor(TimeWindowedJob.class);
					instance = (ProblemFactChange<TaskAssignmentSolution>) constructor.newInstance(job);
					break;
				}
			} catch (Exception e) {
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logger.error(errors.toString());
				e.printStackTrace();
			}
			return instance;
		}).collect(Collectors.toList());
		
		String status = "OK";
		String message = "Fact Change";
		try {
			if(areaList.size()==1 && areaList.contains("JI"))
				appJI.doProblemFactChanges(problemFactChanges);
			else if(areaList.contains("MA"))
				appMA.doProblemFactChanges(problemFactChanges);
			
		}catch(Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
			e.printStackTrace();
			status = "FAIL";
			message = errors.toString();
		}
		ManualRunParam jsonObj = new ManualRunParam();
		jsonObj.setSite(site);
		jsonObj.setFab(fabList);
		jsonObj.setTrxId(trxId);
		jsonObj.setStatus(status);
		jsonObj.setMessage(message);
		return jsonObj;
	}
	
	public ManualRunParam benchmark(ManualRunParam jsonParam) {		
		String site = "";
		List<String> fabList = new ArrayList<>();
		List<String> areaList = new ArrayList<>();
		String trxId = "";
		String planStart = "";
		String planEnd = "";
		
		logger.debug("jsonParam: " + jsonParam);
		site = jsonParam.getSite();
		fabList = jsonParam.getFab();
		areaList = jsonParam.getArea();
		trxId = jsonParam.getTrxId();
		planStart = jsonParam.getPlanStart();
		planEnd = jsonParam.getPlanEnd();
		
		if(areaList==null || areaList.isEmpty()) {
			areaList = new ArrayList<>();
			areaList.add("JI");
		}
		
		String status = "COMPLETE";
		String message = "";
		
		try {
			BenchmarkAppJI appJI = null;
			BenchmarkAppMA appMA = null;
			AppData data = init(site, fabList, areaList, trxId, planStart, planEnd, null);
			
			if(areaList.size()==1 && areaList.contains("JI")) {
				appJI = new BenchmarkAppJI();
				appJI.startPlan(data);
			}else if(areaList.contains("MA")) {
				appMA = new BenchmarkAppMA();
				appMA.startPlan(data);
			}
			
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logger.error(errors.toString());
			e.printStackTrace();
			status = "FAIL";
			message = errors.toString();
		}
		
		ManualRunParam jsonObj = new ManualRunParam();
		jsonObj.setSite(site);
		jsonObj.setPlanStart(planStart);
		jsonObj.setPlanEnd(planEnd);
		jsonObj.setTrxId(trxId);
		jsonObj.setStatus(status);
		jsonObj.setMessage(message);
		
		logger.info("jsonObj: " + jsonObj.toString());
		return jsonObj;
	}
	
	@Transactional
	private void delete(List<SJobDashboard> listPlanResult, String site, List<String> fabList, List<String> areaList, String trx_id) {
		logger.info("site: " + site + " trx_id: " + trx_id);
		//sJobDashboardRepository.deleteBySiteAndFabInAndTrxId(site, fabList, trx_id); //記得要多增加assignShiftDate
		sJobDashboardRepository.deleteBySiteAndFabInAndAreaInAndTrxId(site, fabList, areaList, trx_id);
		
		List<String> siteList = new ArrayList<>();
		List<LocalDate> shiftDateList = new ArrayList<>();
		for(SJobDashboard obj : listPlanResult) {
			siteList.add(obj.getSite());
			shiftDateList.add(obj.getShiftDate());
		}
		
		long lStart = System.currentTimeMillis();
		rJobDashboardRepository.deleteBySiteInAndFabInAndAreaInAndShiftDateIn(siteList, fabList, areaList, shiftDateList);
		long lEnd = System.currentTimeMillis() - lStart;
		logger.info("AppService rJobDashboardRepository delete Time elapsed: " + lEnd / 1000 + " seconds");
		
		long lStartSjob = System.currentTimeMillis();
		sJobScoreRepository.deleteBySjobscoreIdSiteAndSjobscoreIdTrxId(site, trx_id);
		long lEndSjob = System.currentTimeMillis() - lStartSjob;
		logger.info("AppService sJobScoreRepository delete Time elapsed: " + lEndSjob / 1000 + " seconds");
	}
	
	@Transactional
	private void save(List<SJobDashboard> listPlanResult, List<SJobScore> scoreList, String site, List<String> areaList, String trx_id, LocalDate plan_start_date, LocalDate plan_end_date) throws UnknownHostException {
		//排程結果寫入s_job_dashboard ( & r_job_dashboard)
		List<RJobDashboard> rJobDashboardList = new ArrayList<>();
		for(SJobDashboard obj : listPlanResult) {
			boolean isAddTo = false;
			if(obj.getJobId().split("##").length > 2) {
				isAddTo = true;
			}
			if(obj.getWoId()==null)
				obj.setWoId("-");
			if("L0".equals(obj.getChangeLevel())) {
				obj.setChangeLevel(null);
				obj.setChangeShiftDate(null);
				obj.setChangeShift(null);
			}
			if(ModulePlannerUtility.SPECIFIC_JOB_TYPE.contains(obj.getJobType())) {
				if(obj.getPartNo()==null)
					obj.setPartNo("-");
				if(obj.getGrade()==null)
					obj.setGrade("-");
			}
			obj.setLmTime(LocalDateTime.now());
			String computername = InetAddress.getLocalHost().getHostName();
			obj.setLmUser(computername);
			obj.setTrxId(trx_id);
			obj.setPlanStartDate(plan_start_date);
			obj.setPlanEndDate(plan_end_date);
			
			RJobDashboard rJob = new RJobDashboard();
			rJob.setSite(obj.getSite());
			rJob.setFab(obj.getFab());
			rJob.setArea(obj.getArea());
			rJob.setLine(obj.getLine());
			rJob.setShiftDate(obj.getShiftDate());
			rJob.setShift(obj.getShift());
			rJob.setJobId(obj.getJobId());
			rJob.setJobType(obj.getJobType());
			rJob.setWoId(obj.getWoId());
			rJob.setPartNo(obj.getPartNo());
			rJob.setModelNo(obj.getModelNo());
			rJob.setPlanQty(obj.getPlanQty());
			rJob.setProcessStartTime(obj.getProcessStartTime());
			rJob.setProcessEndTime(obj.getProcessEndTime());
			rJob.setChangeLevel(obj.getChangeLevel());
			rJob.setChangeDuration(obj.getChangeDuration());
			rJob.setChangeStartTime(obj.getChangeStartTime());
			rJob.setChangeEndTime(obj.getChangeEndTime());
			rJob.setLmUser(obj.getLmUser());
			rJob.setLmTime(obj.getLmTime());
			rJob.setAssignShiftDate(obj.getAssignShiftDate());
			rJob.setAssignShift(obj.getAssignShift());
			rJob.setForecastQty(obj.getForecastQty());
			rJob.setChangeShiftDate(obj.getChangeShiftDate());
			rJob.setChangeShift(obj.getChangeShift());
			rJob.setChangeKey(obj.getChangeKey());
			rJob.setGrade(obj.getGrade());
			rJobDashboardList.add(rJob);
		}
		
		long lStart = System.currentTimeMillis();
		rJobDashboardRepository.saveAll(rJobDashboardList);
		long lEnd = System.currentTimeMillis() - lStart;
		logger.info("AppService rJobDashboardRepository.saveAll("+rJobDashboardList.size()+") Time elapsed: " + lEnd / 1000 + " seconds");
		
		long lStartSJob = System.currentTimeMillis();
		sJobDashboardRepository.saveAll(listPlanResult);
		long lEndSJob = System.currentTimeMillis() - lStartSJob;
		logger.info("AppService sJobDashboardRepository.saveAll("+listPlanResult.size()+") Time elapsed: " + lEndSJob / 1000 + " seconds");
		
		if(scoreList != null) {
//			logger.debug("scoreList.size(): " + scoreList.size());
			for(SJobScore jobScore : scoreList) {
				jobScore.setLmTime(LocalDateTime.now());
				String computername = InetAddress.getLocalHost().getHostName();
				jobScore.setLmUser(computername);
				jobScore.getSjobscoreId().setTrxId(trx_id);
				jobScore.getSjobscoreId().setPlanStartDate(plan_start_date);
				jobScore.getSjobscoreId().setPlanEndDate(plan_end_date);
			}
			long lStartScore = System.currentTimeMillis();
			sJobScoreRepository.saveAll(scoreList);
			long lEndScore = System.currentTimeMillis() - lStartScore;
			logger.info("AppService sJobScoreRepository.saveAll("+scoreList.size()+") Time elapsed: " + lEndScore / 1000 + " seconds");
		}
	}
	
	private List<Special> innerJoinSplCapa(List<Special> inputList, List<EqpCapa> cEqpCapaJPA){
		List<Special> list = new ArrayList<>();
		list.addAll(inputList);
		for(Special plan : list) {
			for(EqpCapa capa : cEqpCapaJPA) {
				if(plan.getSite().equals(capa.getEqpCapaId().getSite())
						&& plan.getLine().equals(capa.getEqpCapaId().getLine())
						&& plan.getPartNo().equals(capa.getEqpCapaId().getPartNo())) {
					plan.setCapa(capa);
				}
			}
		}
		logger.info("spl list before remove null capa: " + list.size() + " : " + list);
		Iterator<Special> iter = list.iterator();
		while(iter.hasNext()) {
			Special plan = iter.next();
			if(plan.getCapa() == null)
				iter.remove();
		}
		logger.info("spl list after remove null capa: " + list.size() + " : " + list);
		return list;
	}

	private AppData init(String site, List<String> fabList, List<String> areaList, String trxId, String planStart, String planEnd, String fabChangeGroup) throws Exception {
		long lStart = System.currentTimeMillis();
		
		Map<String, Object> mapParam = new HashMap<>();
		
		LocalDate dPlanStart = LocalDate.parse(planStart, ModulePlannerUtility.formatter);
		LocalDate dPlanEnd = LocalDate.parse(planEnd, ModulePlannerUtility.formatter);
		LocalDate inputPlanEnd = dPlanEnd;
		LocalDate planStartDate = dPlanStart;
		LocalDate planEndDate = dPlanEnd;
		LocalDate planStartDateMinus1 = planStartDate.minusDays(1);
		LocalDate planStartDateMinus2 = planStartDate.minusDays(2);
		LocalDate planEndDatePlus7 = planEndDate.plusDays(7);
		dPlanEnd = ModulePlannerUtility.addDayReturnDate(dPlanEnd, 1).toLocalDate();//手動跑結束日期要+1
		
		int plan_day = 0;
		List<ParParameter> listParam = parParameterRepository.findBySiteAndFabInAndAreaInOrderBySiteAscItemNameAscSeqAsc(site, fabList, areaList);
		logger.info(listParam.size() + " listParam: " + listParam);
		mapParam = ModulePlannerUtility.listParamToMapParam(listParam, planStartDate, planEndDate); //搬到utility JoshLai@20190724+
		logger.info("mapParam: " + mapParam);
		
		//score level/weight by site
		Map<String, Object> mapScore = new HashMap<>();
		Map<String, Object> mapWeight = new HashMap<>(); 
		for(CFacScore obj : cFacScoreReposity.findBySiteAndAreaIn(site, areaList)) {
			mapScore.put(obj.getConstraint_item(), Integer.valueOf(obj.getScore_level()));
			mapWeight.put(obj.getConstraint_item(), Integer.valueOf(obj.getWeight()));
		}
		mapParam.put("score_level", mapScore);
		mapParam.put("score_weight", mapWeight);
		
		Map<String, Object> mapTemp = new HashMap<>();
		mapTemp.put("in_value1", inputPlanEnd);
		mapParam.put("input_plan_end_date", mapTemp);
		
		mapTemp = new HashMap<>();
		plan_day = ModulePlannerUtility.calcDasyBetween(dPlanStart, dPlanEnd);
		mapTemp.put("in_value1", plan_day);
		mapParam.put("plan_day", mapTemp);
		
		mapTemp = new HashMap<>();
		mapTemp.put("in_value1", trxId);
		mapParam.put("trx_id", mapTemp);
		
		mapTemp = new HashMap<>();
		mapTemp.put("in_value1", dPlanEnd);
		mapParam.put("plan_end_date", mapTemp);
		
		mapTemp = new HashMap<>();
		mapTemp.put("in_value1", site);
		mapParam.put("site", mapTemp);
		
		mapTemp = new HashMap<>();
		mapTemp.put("in_value1", trxId);
		mapParam.put("trx_id", mapTemp);
		
		mapTemp = new HashMap<>();
		mapTemp.put("in_value1", areaList);
		mapParam.put("area", mapTemp);
		
		mapParam.put("is_map_param", new HashMap<>());
		
		//抓最近7天
		LocalDate historyShiftEndDay = planStartDate;
		LocalDate hisotryShiftStartDay = planStartDate.minusDays(7);
		
		int shiftDstart = Integer.parseInt(String.valueOf(((Map)mapParam.get("shift_d_start")).get("in_value1")));
		LocalDateTime historyStartTime = LocalDateTime.of(hisotryShiftStartDay.getYear(),
				hisotryShiftStartDay.getMonthValue(), hisotryShiftStartDay.getDayOfMonth(),
				shiftDstart, 0, 0);
		LocalDateTime historyEndTime = LocalDateTime.of(historyShiftEndDay.getYear(),
				historyShiftEndDay.getMonthValue(), historyShiftEndDay.getDayOfMonth(),
				shiftDstart, 0, 0);
		
		mapTemp = new HashMap<>();
		mapTemp.put("in_value1", historyStartTime);
		mapParam.put("history_start_time", mapTemp);
		
		mapTemp = new HashMap<>();
		mapTemp.put("in_value1", historyEndTime);
		mapParam.put("history_end_time", mapTemp);
		
		String shiftEnd = ModulePlannerUtility.addDayReturnStr(planStartDate, plan_day);
		
		logger.info("site: " + site
					+ "\r\n fabList: " + fabList
					+ "\r\n areaList: " + areaList
					+ "\r\n shiftStart: " + planStart
					+ "\r\n shiftEnd: " + shiftEnd
					+ "\r\n plan_day: " + plan_day
					+ "\r\n historyStartTime: " + historyStartTime
					+ "\r\n historyEndTime: " + historyEndTime);
		
		List<RJobDashboard> rJobDashboardList = rJobDashboardRepository.findBySiteAndFabInAndAreaInAndProcessEndTimeBetweenOrderByLineAscProcessEndTimeDescLmTimeDesc(site, fabList, areaList, historyStartTime, historyEndTime);
		logger.info("rJobDashboardList size:"+rJobDashboardList.size() + " rJobDashboardList: " + rJobDashboardList);
		mapParam.put("rjobdashboard_list", rJobDashboardList);
		
		//ShiftDate = PlanStartDate-1
		List<RJobDashboard> rJobDashboardListBeforePlanDate = rJobDashboardRepository.findBySiteAndFabInAndShiftDateBetweenOrderByLineAscProcessEndTimeAscLmTimeAsc(site, fabList, planStartDateMinus1, planStartDateMinus1);
		logger.info("rJobDashboardListBeforePlanDate size:"+rJobDashboardListBeforePlanDate.size() + " rJobDashboardListBeforePlanDate: " + rJobDashboardListBeforePlanDate);
		
		//ShiftDate between PlanStartDate ~ PlanEndDate
		List<RJobDashboard> rJobDashboardListInPlanDate = rJobDashboardRepository.findBySiteAndFabInAndShiftDateBetweenOrderByLineAscProcessEndTimeAscLmTimeAsc(site, fabList, planStartDate, planEndDate);
		logger.info("rJobDashboardListInPlanDate size:"+rJobDashboardListInPlanDate.size() + " rJobDashboardListInPlanDate: " + rJobDashboardListInPlanDate);
		
		List<Line> cFacLineList = lineRepository.findBySiteAndFabInAndAreaInAndActiveFlag(site, fabList, areaList, "Y");
		logger.info("cFacLineList size:"+cFacLineList.size() + " cFacLineList: " + cFacLineList);
		
		List<ModModel> cModModelList = modModelRepository.findByModelIdSite(site);
		//logger.info("cModModelList size:"+cModModelList.size() + " cModModelList: " + cModModelList);
		
		List<EqpCapa> cEqpCapaList = eqpCapaRepository.findByEqpCapaIdSiteAndFabInAndAreaIn(site, fabList, areaList);
		logger.info("cEqpCapaList size:"+cEqpCapaList.size() + " cEqpCapaJPA: " + cEqpCapaList);
		
		logger.info("site: " + site + " planStartDateMinus1: " + planStartDateMinus1 + " planStartDateMinus2: " + planStartDateMinus2 + " planEndDatePlus7: " + planEndDatePlus7);
		List<Plan> rJobPlanTemp =  planRepository.find(site, fabList, areaList, planStartDateMinus1, planEndDatePlus7);
		logger.info(rJobPlanTemp.size() + " rJobPlanTemp: " + rJobPlanTemp);

		List<Plan> rJobPlanList = ModulePlannerUtility.innerJoinPlanCapa(rJobPlanTemp, cEqpCapaList, areaList, mapParam);
		logger.info(rJobPlanList.size() + " rJobPlanList: " + rJobPlanList);
		
		List<Special> rJobSpecialList = specialRepository.find(site, fabList, areaList, planStartDate, planEndDate);
		logger.info(rJobSpecialList.size() + " rJobSpecialList-0: " + rJobSpecialList);
		logger.info(cEqpCapaList.size() + " cEqpCapaList: " + cEqpCapaList);
		rJobSpecialList = innerJoinSplCapa(rJobSpecialList, cEqpCapaList);
		logger.info(rJobSpecialList.size() + " rJobSpecialList-1: " + rJobSpecialList);
		
		List<EquipmentPreventiveMaintenance> rJobEqpmList = equipmentPreventiveMaintenanceRepository.find(site, fabList, areaList, planStartDate, planEndDate);
		logger.info(rJobEqpmList.size() + " rJobEqpmList: " + rJobEqpmList);
		
		List<CFacConstraintCapa> cFacConstraintCapaList = cFacConstraintCapaRepository.find(site, fabList, planStartDate);
		logger.info(cFacConstraintCapaList.size() + " cFacConstraintCapaList: " + cFacConstraintCapaList);
		
		LocalDate maxWoStartDate = null;
		List<Woxx> woxxList = woxxRepository.findBySite(site, fabList, areaList);
		//先取得最靠近planStartDate的woVersion
		if(woxxList.size() > 0) {
			String previousWoVersion = woxxList.get(0).getWoxxId().getWoVersion();
			maxWoStartDate = ModulePlannerUtility.woVersionToLocalDate(previousWoVersion);
			if(previousWoVersion != null) {
				int iCounter=0;
				for(Woxx woxx : woxxList) {
					String woVersion = woxx.getWoxxId().getWoVersion();
					LocalDate woVersionDate = ModulePlannerUtility.woVersionToLocalDate(woVersion);
					
					//若不幸第一筆woVersion比planStartDate大,則取下一個woVersion直到小於planStartDate
					if(maxWoStartDate.isEqual(planStartDate) || maxWoStartDate.isAfter(planStartDate)) {
						if(iCounter+1 < rJobPlanList.size() && iCounter+1 < woxxList.size()) {
							String nextWoVersion = woxxList.get(iCounter+1).getWoxxId().getWoVersion();
							maxWoStartDate = ModulePlannerUtility.woVersionToLocalDate(nextWoVersion);
						}
					}
					if(woVersionDate.isAfter(maxWoStartDate) && (woVersionDate.isBefore(planStartDate) 
							|| woVersionDate.isEqual(planStartDate))) //從planStartDate開始匹配wo_version JoshLai@20190701+
						maxWoStartDate = woVersionDate;
					iCounter++;
				}
			}
		}
		
		//過濾只要woVersion = 最靠近planStartDate的版本 JoshLai@20190410
		//不要使用工單倒數第三碼=J的 JoshLai@20190412 先取消20190416
		for(Iterator<Woxx> iter = woxxList.iterator(); iter.hasNext();) {
			Woxx woxx = iter.next();
			String strWoVersion = null;
			//String woId = woxx.getWoxxId().getWoId();
			//String woIdLast3 = woId.substring(woId.length()-3, woId.length()-2); //倒數第三碼
			if(maxWoStartDate != null) {
				strWoVersion = maxWoStartDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			}
			if(!woxx.getWoxxId().getWoVersion().equals(strWoVersion) /*|| woIdLast3.equalsIgnoreCase("J")*/)
				iter.remove();
			
			BigDecimal bWoQtyTotal = new BigDecimal(woxx.getWoQtyTotal());
			//BigDecimal bWoIqty = new BigDecimal(woxx.getWoIqty());
			//BigDecimal bWoQty = bWoQtyTotal.subtract(bWoIqty); //mark by avonchung 20190426 不卡工單剩餘量
			BigDecimal bWoQty = bWoQtyTotal;
			woxx.setWoQty(bWoQty.intValue());
		}
		logger.info("maxWoStartDate: " + maxWoStartDate);
		logger.info(woxxList.size() + " woxxList: " + woxxList);
		mapParam.put("woxx_list", woxxList);
		
		//update by avonchung 20190508 取消fab條件
//		List<ModChange> cModChangeList = cModChangeRepository.findBySiteAndFabInOrderByPriority(site, fabList);
		List<ModChange> cModChangeList = cModChangeRepository.findBySiteAndAreaInOrderByPriority(site, areaList);
		//add by avonchung 20190426
		//update by avonchung 20190508 取消fab條件
//		List<Object> cModChangeReverseList = cModChangeRepository.findReverseBySiteAndFabInOrderByPriority(site, fabList);
		List<Object> cModChangeReverseList = cModChangeRepository.findReverseBySiteAndAreaInOrderByPriority(site, areaList);
		for (int i = 0; i < cModChangeReverseList.size(); i++) {
			ModChange m = new ModChange();
            Object[] mcr = (Object[]) cModChangeReverseList.get(i);
            m.setSite((String) mcr[0]); 
            m.setFab((String) mcr[1]); 
            m.setLine((String) mcr[2]);
            m.setFromModelSite((String) mcr[3]); 
            m.setFromModelType((String) mcr[4]); 
            m.setFromPanelSize((String) mcr[5]); 
            m.setFromPartLevel((String) mcr[6]); 
            m.setFromBarType((String) mcr[7]); 
            m.setFromPanelSizeGroup((String) mcr[8]); 
            m.setFromIsBuildPcb((String) mcr[9]); 
            m.setFromIsDemura((String) mcr[10]); 
            m.setFromTuffyType((String) mcr[11]); 
            m.setFromIsOverSixMonth((String) mcr[12]); 
            m.setFromPartNo((String) mcr[13]); 
            m.setFromChangeGroup((String) mcr[14]); 
            m.setFromPartsGroup((String)mcr[40]);
            m.setToModelSite((String) mcr[15]); 
            m.setToModelType((String) mcr[16]); 
            m.setToPanelSize((String) mcr[17]); 
            m.setToPartLevel((String) mcr[18]); 
            m.setToBarType((String) mcr[19]); 
            m.setToPanelSizeGroup((String) mcr[20]);
            m.setToIsBuildPcb((String) mcr[21]); 
            m.setToIsDemura((String) mcr[22]); 
            m.setToTuffyType((String) mcr[23]); 
            m.setToIsOverSixMonth((String) mcr[24]); 
            m.setToPartNo((String) mcr[25]); 
            m.setToChangeGroup((String) mcr[26]); 
            m.setChangeKey((String) mcr[27]); 
            m.setChangeLevel((String) mcr[28]); 
            m.setChangeDuration((String) mcr[29]);
            m.setFromToReverse((String) mcr[30]); 
            m.setPriority((String) mcr[31]); 
            m.setAffectCapaPercent1((String) mcr[32]); 
            m.setAffectCapaQty1((String) mcr[33]); 
            m.setAffectCapaPercent2((String) mcr[34]); 
            m.setAffectCapaQty2((String) mcr[35]); 
            m.setAffectCapaPercent3((String) mcr[36]); 
            m.setAffectCapaQty3((String) mcr[37]); 
            m.setAffectCapaPercent4((String) mcr[38]); 
            m.setAffectCapaQty4((String) mcr[39]);
            m.setToPartsGroup((String) mcr[41]);
            //m.setLineMode((String) mcr[42]); //10月中的版本再上,先拿掉 JoshLai@20190927+
            cModChangeList.add(m);
		}
		
		for(ModChange cModChange : cModChangeList) {
			if(cModChange.getAffectCapaPercent1()==null)
				cModChange.setAffectCapaPercent1("1");
			if(cModChange.getAffectCapaQty1() == null)
				cModChange.setAffectCapaQty1("0");
			if(cModChange.getAffectCapaPercent2()==null)
				cModChange.setAffectCapaPercent2("1");
			if(cModChange.getAffectCapaQty2() == null)
				cModChange.setAffectCapaQty2("0");
			if(cModChange.getAffectCapaPercent3()==null)
				cModChange.setAffectCapaPercent3("1");
			if(cModChange.getAffectCapaQty3() == null)
				cModChange.setAffectCapaQty3("0");
			if(cModChange.getAffectCapaPercent4()==null)
				cModChange.setAffectCapaPercent4("1");
			if(cModChange.getAffectCapaQty4() == null)
				cModChange.setAffectCapaQty4("0");
		}
		
		cModChangeList.sort(Comparator.comparing(m -> m.getPriority()
        ));//排序order by priority 重新校對priority順序 JoshLai@20190725+
		logger.info(cModChangeList.size() + " cModChangeList: " + cModChangeList);
		mapParam.put("cmod_change_list", cModChangeList);
		
		List<Adjustment> adjustmentAllAreaList = adjustmentRepository.findBySiteAndFabAndShiftDateForAllType(site, fabList, historyStartTime.minusDays(1).toLocalDate(), historyEndTime.toLocalDate());
		List<Adjustment> adjustmentListAfterCOG = adjustmentRepository.findBySiteAndFabAndShiftDateForAllType(site, fabList, planStartDate, planEndDate);
		List<Adjustment> adjustmentList = new ArrayList<>();
		logger.info(adjustmentAllAreaList.size() + " adjustmentAllAreaList: " + adjustmentAllAreaList);
		
		
		//COG調整後排程只需要PROD
		adjustmentListAfterCOG = adjustmentListAfterCOG.stream().filter(
				adjustment -> adjustment.getAdjustmentId().getJobType().equals(ModulePlannerUtility.JOB_TYPE_PROD)
				&& adjustment.getAdjustmentId().getArea().equals(ModulePlannerUtility.AREA_JI))
				.collect(Collectors.toList());
		
		//JOB_TYPE為CHANGE時只取runSeq最大的
		ModulePlannerUtility.getMaxRunSeqFromAdjustmentList(adjustmentListAfterCOG);
		logger.info(adjustmentListAfterCOG.size() + " adjustmentListAfterCOG: " + adjustmentListAfterCOG);
		
		
		logger.info(adjustmentList.size() + " after keep max runSeq adjustmentList: " + adjustmentList);
		mapParam.put("adjustment_list", adjustmentList);
		
		//組合rJobDashboardList,adjustmentList
		List<Object> historicalJobList = ModulePlannerUtility.getLastChangeRJobUnionAdjustByShiftDate(adjustmentAllAreaList, rJobDashboardList, areaList, mapParam);
		mapParam.put("historical_list", historicalJobList);
		logger.info(historicalJobList.size() + " historicalJobList: " + historicalJobList);
		
		//update for avonchung 20190903 S_LOT_OPWP記錄的shift_date是前一天的夜班，所以要捉plan start -2
		List<SLotOpwp> sLotOpwpList = sLotOpwpRepository.findBySiteAndFabInAndAreaInAndShiftDateOrderbyShiftDatePartNo(site, fabList, areaList, planStartDateMinus2);
		logger.info(sLotOpwpList.size() + " sLotOpwpList: " + sLotOpwpList);
	
		AppData data = new AppData(cFacLineList, cModModelList, cEqpCapaList, rJobPlanList, rJobSpecialList, rJobEqpmList,
				cFacConstraintCapaList, woxxList, cModChangeList, mapParam, site, historicalJobList, fabChangeGroup,
				adjustmentListAfterCOG, sLotOpwpList, adjustmentAllAreaList, 
				rJobDashboardListBeforePlanDate, rJobDashboardListInPlanDate, areaList);
		
		long lEnd = System.currentTimeMillis() - lStart;
		logger.info("AppService.init() Time elapsed: " + lEnd / 1000 + " seconds");
		return data;
	}
}
