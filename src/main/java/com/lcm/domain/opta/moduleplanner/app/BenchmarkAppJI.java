package com.lcm.domain.opta.moduleplanner.app;

import java.util.List;
import java.util.Map;

import org.optaplanner.benchmark.api.PlannerBenchmark;
import org.optaplanner.benchmark.api.PlannerBenchmarkFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lcm.domain.opta.moduleplanner.domain.AppData;
import com.lcm.domain.opta.moduleplanner.domain.PlanLine;
import com.lcm.domain.opta.moduleplanner.domain.Shift;
import com.lcm.domain.opta.moduleplanner.domain.TaskAssignmentSolution;
import com.lcm.domain.opta.moduleplanner.domain.TimeWindowedJob;

public class BenchmarkAppJI extends AppJI{  
	private static final Logger logger = LoggerFactory.getLogger(BenchmarkAppJI.class);
	
	public BenchmarkAppJI() {
		
	}

	@Override
	protected void doSchedule(Map<String, Object> mapPlanResult, AppData appData, List<PlanLine> planLineList,
			List<TimeWindowedJob> jobs, List<Shift> shiftList) throws Exception {
		
		PlannerBenchmarkFactory plannerBenchmarkFactory = PlannerBenchmarkFactory.createFromXmlResource(
				"com/lcm/opta/moduleplanner/taskassignmentBenchmarkConfiguration.xml");
		TaskAssignmentSolution dataset1 = new TaskAssignmentSolution(planLineList, jobs, appData.getModelList(), shiftList,
				appData.getcFacConstraintCapaList(), appData.getMapParam(), appData.getWoxxList(),
				appData.getcModChangeList(), appData.getHistoricalList());
		PlannerBenchmark plannerBenchmark = plannerBenchmarkFactory.buildPlannerBenchmark(dataset1);
		plannerBenchmark.benchmarkAndShowReportInBrowser();
	}
}
