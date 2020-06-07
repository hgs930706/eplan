/*
 * Copyright 2014 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lcm.domain.opta.moduleplanner.solver;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionSorterWeightFactory;
import org.slf4j.LoggerFactory;

import com.lcm.domain.opta.moduleplanner.domain.Job;
import com.lcm.domain.opta.moduleplanner.domain.TaskAssignmentSolution;


/**
 * On large datasets, the constructed solution looks like pizza slices.
 */
public class TaskDifficultyWeightFactory
        implements SelectionSorterWeightFactory<TaskAssignmentSolution, Job> {
	final static org.slf4j.Logger logger = LoggerFactory.getLogger(TaskDifficultyWeightFactory.class);

    @Override
    public TaskDifficultyWeight createSorterWeight(TaskAssignmentSolution taskAssignmentSolution, Job job) {
    	float setupDuration = 0;
    	if(job.getLine() != null) {
	    	if(job.getLine().getModel() != null) {
	    		setupDuration = (float) job.getLine().getModel().getSetupDurationTo(job.getModel(),job.getLine(), job).get("CHANGE_DURATION");
	    	}
    	}
    	return new TaskDifficultyWeight(job, setupDuration);
    }

	public static class TaskDifficultyWeight implements
			Comparable<TaskDifficultyWeight> {

		private final Job job;
		private final float setupDuration;

		public TaskDifficultyWeight(Job job2,
				float setupDuration) {
			this.job = job2;
			this.setupDuration = setupDuration;
		}

		@Override
		public int compareTo(TaskDifficultyWeight other) {
			return new CompareToBuilder()					
					.append(job.getShift_date(), other.job.getShift_date())
//					.append(job.getIs_add_to(), other.job.getIs_add_to())
					.append(job.getForecastQty(), other.job.getForecastQty())
					.append(job.getId(), other.job.getId())
					.toComparison();
		}

	}

}
