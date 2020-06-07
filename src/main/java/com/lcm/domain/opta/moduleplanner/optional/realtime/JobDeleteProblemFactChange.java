package com.lcm.domain.opta.moduleplanner.optional.realtime;

import org.optaplanner.core.impl.score.director.ScoreDirector;
import org.optaplanner.core.impl.solver.ProblemFactChange;

import com.lcm.domain.opta.moduleplanner.domain.Standstill;
import com.lcm.domain.opta.moduleplanner.domain.TaskAssignmentSolution;
import com.lcm.domain.opta.moduleplanner.domain.TimeWindowedJob;

public class JobDeleteProblemFactChange implements ProblemFactChange<TaskAssignmentSolution> {

	private final TimeWindowedJob job;
	
	public JobDeleteProblemFactChange(TimeWindowedJob job) {
		super();
		this.job = job;
	}
	
	@Override
	public void doChange(ScoreDirector<TaskAssignmentSolution> scoreDirector) {
		TaskAssignmentSolution workingSolution = scoreDirector.getWorkingSolution();
        // A SolutionCloner clones planning entity lists (such as processList), so no need to clone the jobList here
		TimeWindowedJob workingJob = scoreDirector.lookUpWorkingObject(job);
        if (workingJob == null) {
            // The process has already been deleted (the UI asked to changed the same job twice), so do nothing
            return;
        }

        // Handle the chained entity
        TimeWindowedJob nextJob = scoreDirector.lookUpWorkingObject(workingJob.getNextJob());
        Standstill standstill = workingJob.getPreviousStandstill();
        
        // Remove the planning entity itself
        scoreDirector.beforeEntityRemoved(workingJob);
        workingSolution.getJobList().remove(workingJob);
        if(nextJob != null)
        	nextJob.setPreviousStandstill(standstill);
        if(standstill != null)
        	standstill.setNextJob(nextJob); 
        scoreDirector.afterEntityRemoved(workingJob);
        scoreDirector.triggerVariableListeners();
	}
}
