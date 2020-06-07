package com.lcm.domain.opta.moduleplanner.optional.realtime;

import org.optaplanner.core.impl.score.director.ScoreDirector;
import org.optaplanner.core.impl.solver.ProblemFactChange;

import com.lcm.domain.opta.moduleplanner.domain.TaskAssignmentSolution;
import com.lcm.domain.opta.moduleplanner.domain.TimeWindowedJob;

public class JobPinnedProblemFactChange implements ProblemFactChange<TaskAssignmentSolution> {

	private TimeWindowedJob job;
	
	public JobPinnedProblemFactChange(TimeWindowedJob job) {
		super();
		this.job = job;
	}

	@Override
	public void doChange(ScoreDirector<TaskAssignmentSolution> scoreDirector) {
		// A SolutionCloner clones planning entity lists (such as processList), so no need to clone the jobList here
		TimeWindowedJob workingJob = scoreDirector.lookUpWorkingObject(job);
        if (workingJob == null) {
            // The process has already been deleted (the UI asked to changed the same job twice), so do nothing
            return;
        }
        if (workingJob.getStartTime() != null) {
            scoreDirector.beforeProblemPropertyChanged(workingJob);
            workingJob.setPinned(true);
            scoreDirector.afterProblemPropertyChanged(workingJob);
        }
	}

}
