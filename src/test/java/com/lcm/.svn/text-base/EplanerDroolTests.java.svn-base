package com.lcm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.test.impl.score.buildin.bendable.BendableScoreVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lcm.domain.CFacConstraintCapa;
import com.lcm.domain.Woxx;
import com.lcm.domain.opta.moduleplanner.domain.Model;
import com.lcm.domain.opta.moduleplanner.domain.PlanLine;
import com.lcm.domain.opta.moduleplanner.domain.Shift;
import com.lcm.domain.opta.moduleplanner.domain.TaskAssignmentSolution;
import com.lcm.domain.opta.moduleplanner.domain.TimeWindowedJob;
import com.lcm.domain.opta.moduleplanner.utility.ModulePlannerUtility;

public class EplanerDroolTests {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private BendableScoreVerifier<TaskAssignmentSolution> scoreVerifier = new BendableScoreVerifier<>(
			SolverFactory.createFromXmlResource(
					"com/lcm/opta/moduleplanner/taskassignmentConfiguration.xml"));
	
	private String site = "S06";
	private String fab = "3B";
	private String planStart = "2018/11/13";
	private String planEnd = "2018/11/14";
	
	@Test
	public void droolTestS06() throws CloneNotSupportedException {

		Map<String, Object> mapParam = getMapParam();

		List<Model> modelList = getModelList();
		List<Map<Model, Map<String, Object>>> capaList = getCapaList(modelList);
		List<Shift> shiftList = new ArrayList<>();
		List<Woxx> woxxList = getWoxxList();
		PlanLine line1 = new PlanLine(1, site, fab, "JI_3A", "S063BJI01", "N", modelList.get(0), capaList.get(0), 10, "", null, "%");
		PlanLine line2 = new PlanLine(2, site, fab, "JI_3A", "S063BJI02", "N", modelList.get(1), capaList.get(1), 20, "S", null, "%");
		
		TimeWindowedJob job1 = new TimeWindowedJob(1, modelList.get(0), 0, line1);
		TimeWindowedJob job2 = new TimeWindowedJob(2, modelList.get(1), 0, line2);
		setJob(job1);
		setJob(job2);
		job1.setAssignLine(line1);
		job2.setAssignLine(line2);
		job1.setPreviousStandstill(line1);
		line1.setNextJob(job1);
		job1.setNextJob(job2);
		job2.setNextJob(job2);
//		
		CFacConstraintCapa cFacConstraintCapa1 = new CFacConstraintCapa();
		CFacConstraintCapa cFacConstraintCapa2 = new CFacConstraintCapa();
//		
		TaskAssignmentSolution solution = new TaskAssignmentSolution(
				Arrays.asList(line1, line2), 
				Arrays.asList(job1, job2), 
				modelList, shiftList, Arrays.asList(cFacConstraintCapa1, cFacConstraintCapa2), mapParam, woxxList, null, null);
		
		//assign line
		scoreVerifier.assertHardWeight("AssignLine", 0, 0, solution);
		
		job1.setAssignLine(line2);
		job2.setAssignLine(line1);
		scoreVerifier.assertHardWeight("AssignLine", 0, -2000, solution);
		
		//end after due time
		job1.setDueTime(100d);
		job1.setStartTime(100d);
		job1.setReadyTime(300d);
		job2.setDueTime(100d);
		job2.setStartTime(100d);
		job2.setReadyTime(200d);
		scoreVerifier.assertHardWeight("EndAfterDueTime", 0, -300, solution);

		job1.setIsAddTo(1);
		scoreVerifier.assertHardWeight("EndAfterDueTime", 0, -100, solution);
		
		//spl priority
		job1.setJobType("SPL");
		job2.setJobType("SPL");
		job1.setNextJob(job2);
		job1.setAssignStartTime(100d);
		job2.setAssignStartTime(100d);
		scoreVerifier.assertSoftWeight("SPLPriority", 0, -3, solution);
		job1.setAssignStartTime(0d);
		job2.setAssignStartTime(200d);
		scoreVerifier.assertSoftWeight("SPLPriority", 0, -197, solution);
		
		//eng priority
		job1.setJobType("ENG");
		scoreVerifier.assertSoftWeight("ENGPriority", 0, -97, solution);
		job1.setAssignStartTime(100d);
		scoreVerifier.assertSoftWeight("ENGPriority", 0, 0, solution);
//		
//		//pm priority
//		job2.setJob_type("PM");
//		job1.setAssignStartTime(200d);
//		scoreVerifier.assertSoftWeight("PMPriority", 0, -100, solution);
//		job2.setAssignStartTime(100d);
//		scoreVerifier.assertSoftWeight("PMPriority", 0, 0, solution);
		
		//accumulate setup time
//		job1.setJob_type("");
//		scoreVerifier.assertHardWeight("Accumulate_Setup_Time", 0, 0, solution);
//		
//		job2.setJob_type("SPL");
//		scoreVerifier.assertHardWeight("Accumulate_Setup_Time", 0, 0, solution);
		
//		scoreVerifier.assertSoftWeight("OpenLine", 1, -2, solution);
	}

	private Map<String, Object> getMapParam() {

		Map<String, Object> mapParam = new HashMap<>();
		Map<String, Object> mapTemp;
		
		mapParam.put("is_map_param", new HashMap<>());
		
		try {
			LocalDate dPlanStart = LocalDate.parse(planStart, ModulePlannerUtility.formatter);
			LocalDate dPlanEnd = LocalDate.parse(planEnd, ModulePlannerUtility.formatter);
			dPlanEnd = ModulePlannerUtility.addDayReturnDate(dPlanEnd, 1).toLocalDate();//手動跑結束日期要+1
			
			mapTemp = new HashMap<>();
			mapTemp.put("in_value1", dPlanStart);
			mapParam.put("plan_start_date", mapTemp);
			
			mapTemp = new HashMap<>();
			mapTemp.put("in_value1", dPlanEnd);
			mapParam.put("plan_end_date", mapTemp);
		}catch(Exception e) {
			e.printStackTrace();
		}
		mapTemp = new HashMap<>();
		mapTemp.put("in_value1", site);
		mapParam.put("site", mapTemp);
		
		mapTemp = new HashMap<>();
		mapTemp.put("in_value1", "3A");
		mapParam.put("fab_change_g1", mapTemp);
		
		mapTemp = new HashMap<>();
		mapTemp.put("in_value1", "4A");
		mapParam.put("fab_change_g2", mapTemp);
		
		mapTemp = new HashMap<>();
		mapTemp.put("in_value1", "3B");
		mapParam.put("fab_change_g3", mapTemp);
		
		mapTemp = new HashMap<>();
		mapTemp.put("in_value1", "6:4");
		mapParam.put("job_balance", mapTemp);
		
		mapTemp = new HashMap<>();
		mapTemp.put("in_value1", "0");
		mapParam.put("open_line", mapTemp);
		
		mapTemp = new HashMap<>();
		mapTemp.put("in_value1", "2");
		mapParam.put("plan_day", mapTemp);
		
		mapTemp = new HashMap<>();
		mapTemp.put("in_value1", "7");
		mapParam.put("query_history_day", mapTemp);
		
		mapTemp = new HashMap<>();
		mapTemp.put("in_value1", "08");
		mapParam.put("shift_d_start", mapTemp);
		
		mapTemp = new HashMap<>();
		mapTemp.put("in_value1", "20");
		mapParam.put("shift_e_start", mapTemp);
		
		mapTemp = new HashMap<>();
		mapTemp.put("PMPriority", 0);
		mapTemp.put("ENGPriority", 0);
		mapTemp.put("SPLPriority", 0);
		mapTemp.put("EndAfterDueTime", 0);
		mapTemp.put("AssignLine", 0);
		mapTemp.put("AccumulateSetupTime", 1);
		mapTemp.put("OpenLine", 1);
		mapTemp.put("Limit97", 1);
		mapTemp.put("Limit91", 1);
		mapTemp.put("CrossChange", 0);
		mapTemp.put("SameDayChange", 0);
		mapParam.put("score_level", mapTemp);
		
		mapTemp = new HashMap<>();
		mapTemp.put("PMPriority", 500);
		mapTemp.put("ENGPriority", 1);
		mapTemp.put("SPLPriority", 1);
		mapTemp.put("EndAfterDueTime", 1);
		mapTemp.put("AssignLine", 1000);
		mapTemp.put("AccumulateSetupTime", 1);
		mapTemp.put("OpenLine", 1);
		mapTemp.put("Limit97", 1);
		mapTemp.put("Limit91", 1);
		mapTemp.put("CrossChange", 1);
		mapTemp.put("SameDayChange", 1);
		mapParam.put("score_weight", mapTemp);
		
		return mapParam;
	}

	private List<Model> getModelList(){
		List<Model> modelList = new ArrayList<>();
//		Model model1 = new Model(site, "97.13B42.C01", "K06", "B133HAN05-C/01", "B133HAN05", "", "", "", "", "", "", "", "", "", LocalDateTime.now(), "", "", "");
//		Model model2 = new Model(site, "97.14B84.000", "K06", "B140QAN02-0/00", "B140QAN02", "", "", "", "", "", "", "", "", "", LocalDateTime.now(), "", "", "");
//		model1.setMapChangeLvl(getChangeLvl());
//		model2.setMapChangeLvl(getChangeLvl());
//		modelList.add(model1);
//		modelList.add(model2);
		return modelList;
	}
	
	private List<Map<Model, Map<String, Object>>> getCapaList(List<Model> modelList) {
		List<Map<Model, Map<String, Object>>> capaList = new ArrayList<>();
		Map<Model, Map<String, Object>> capa = new HashMap<>();
		Map<String, Object> mapTemp = new HashMap<>();
		mapTemp.put("PPC_CAPA", 6000);
		mapTemp.put("FAB_PC_CAPA", 6600);
		capa.put(modelList.get(0), mapTemp);
		capaList.add(capa);
		
		capa = new HashMap<>();
		mapTemp = new HashMap<>();
		mapTemp.put("PPC_CAPA", 5000);
		mapTemp.put("FAB_PC_CAPA", 5000);
		capa.put(modelList.get(1), mapTemp);
		capaList.add(capa);
		
		return capaList;
	}
	
	private Map<String, Object> getChangeLvl(){
		Map<String, Object> mapChangeLvl = new HashMap<>();
		
		
		String key = site+"_"+fab+"_"+"%"+"_"+"L1";
		mapChangeLvl.put(key, 3);
		
		key = site+"_"+fab+"_"+"%"+"_"+"L2";
		mapChangeLvl.put(key, 8);
		
		return mapChangeLvl;
	}
	
	private void setJob(TimeWindowedJob job) {
		job.setSite(site);
		job.setFab(fab);
		job.setLineNo("S063BJI01");
		job.setChangeLevel("L1");
	}
	
	private List<Woxx> getWoxxList(){
		List<Woxx> wooList = new ArrayList<>();
		return wooList;
	}
}
