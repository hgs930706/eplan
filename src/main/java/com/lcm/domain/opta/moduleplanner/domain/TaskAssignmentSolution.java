package com.lcm.domain.opta.moduleplanner.domain;

import java.util.List;
import java.util.Map;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactProperty;
import org.optaplanner.core.api.score.buildin.bendable.BendableScore;
import org.slf4j.LoggerFactory;

import com.lcm.domain.CFacConstraintCapa;
import com.lcm.domain.ModChange;
import com.lcm.domain.Woxx;

@PlanningSolution
public class TaskAssignmentSolution extends AbstractPersistable{
	final static org.slf4j.Logger logger = LoggerFactory.getLogger(TaskAssignmentSolution.class);
    private BendableScore score;
    
    private List<PlanLine> lineList;
    private List<TimeWindowedJob> jobList;
    private List<Model> modelList;
    private List<Shift> shiftList;
    private List<Woxx> woxxList;
    private List<ModChange> cModChangeList;
    private List<Object> historicalList;
    
    List<CFacConstraintCapa> cFacConstraintCapaList;
    Map<String, Object> mapParam;
    
    @PlanningScore(bendableHardLevelsSize = 1, bendableSoftLevelsSize = 2)
    public BendableScore getScore() {
        return score;
    }

    public void setScore(BendableScore score) {
        this.score = score;
    }
    
    //mark by avonchung 20190304 @ValueRangeProvider(id = "lineRange")
    @PlanningEntityCollectionProperty
    public List<PlanLine> getLineList() {
        return lineList;
    }
    
    public void setLineList(List<PlanLine> lineList) {
        this.lineList = lineList;
    }
        
    //mark by avonchung 20190319 @ValueRangeProvider(id = "jobRange")
    @PlanningEntityCollectionProperty
    public List<TimeWindowedJob> getJobList() {
        return jobList;
    }
    public void setJobList(List<TimeWindowedJob> jobList) {
        this.jobList = jobList;
    }
    
    @ProblemFactCollectionProperty
    public List<Model> getModelList() {
        return modelList;
    }
    public void setModelList(List<Model> modelList) {
        this.modelList = modelList;
    }
    
    @ProblemFactCollectionProperty
    public List<Shift> getShiftList() {
        return shiftList;
    }
    public void setShiftList(List<Shift> shiftList) {
        this.shiftList = shiftList;
    }
    
    public TaskAssignmentSolution(List<PlanLine> lineList, List<TimeWindowedJob> jobList, List<Model> modelList,
			List<Shift> shiftList, List<CFacConstraintCapa> cFacConstraintCapaList,
			Map<String, Object> mapParam, List<Woxx> woxxList, List<ModChange> cModChangeList,
			List<Object> historicalList) {
		super();
		this.lineList = lineList;
		this.jobList = jobList;
		this.modelList = modelList;
		this.shiftList = shiftList;
		this.cFacConstraintCapaList = cFacConstraintCapaList;
		this.mapParam = mapParam;
		this.woxxList = woxxList;
		this.cModChangeList = cModChangeList;
		this.historicalList = historicalList;
	}

	public TaskAssignmentSolution(){}

	@ProblemFactCollectionProperty
	public List<CFacConstraintCapa> getcFacConstraintCapaList() {
		return cFacConstraintCapaList;
	}

	public void setcFacConstraintCapaList(List<CFacConstraintCapa> cFacConstraintCapaList) {
		this.cFacConstraintCapaList = cFacConstraintCapaList;
	}

    @ProblemFactProperty
    public Map<String, Object> getMapParam() {
		return mapParam;
	}

	public void setMapParam(Map<String, Object> mapParam) {
		this.mapParam = mapParam;
	}

	public List<Woxx> getWoxxList() {
		return woxxList;
	}

	public void setWoxxList(List<Woxx> woxxList) {
		this.woxxList = woxxList;
	}

	public List<ModChange> getcModChangeList() {
		return cModChangeList;
	}

	public void setcModChangeList(List<ModChange> cModChangeList) {
		this.cModChangeList = cModChangeList;
	}

	public List<Object> getHistoricalList() {
		return historicalList;
	}

	public void setHistoricalList(List<Object> historicalList) {
		this.historicalList = historicalList;
	}
	
}