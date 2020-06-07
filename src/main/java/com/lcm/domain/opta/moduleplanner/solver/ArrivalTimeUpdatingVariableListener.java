/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.optaplanner.core.impl.domain.variable.listener.VariableListener;
import org.optaplanner.core.impl.score.director.ScoreDirector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lcm.domain.opta.moduleplanner.domain.Job;
import com.lcm.domain.opta.moduleplanner.domain.Setup;
import com.lcm.domain.opta.moduleplanner.domain.Standstill;
import com.lcm.domain.opta.moduleplanner.domain.TimeWindowedJob;


// TODO When this class is added only for TimeWindowedCustomer, use TimeWindowedCustomer instead of Customer
public class ArrivalTimeUpdatingVariableListener implements VariableListener<Job> {
	private final Logger logger = LoggerFactory.getLogger(ArrivalTimeUpdatingVariableListener.class);
	
    @Override
    public void beforeEntityAdded(ScoreDirector scoreDirector, Job job) {
        // Do nothing
    }

    @Override
    public void afterEntityAdded(ScoreDirector scoreDirector, Job job) {
        if (job instanceof TimeWindowedJob) {
            updateStartTime(scoreDirector, (TimeWindowedJob) job);
        }
    }

    @Override
    public void beforeVariableChanged(ScoreDirector scoreDirector, Job job) {
        // Do nothing
    }

    @Override
    public void afterVariableChanged(ScoreDirector scoreDirector, Job job) {
        if (job != null && job instanceof TimeWindowedJob) {
            updateStartTime(scoreDirector, (TimeWindowedJob) job);
        }
    }

    @Override
    public void beforeEntityRemoved(ScoreDirector scoreDirector, Job job) {
        // Do nothing
    }

    @Override
    public void afterEntityRemoved(ScoreDirector scoreDirector, Job job) {
        // Do nothing
    }

    
	/*固定換線時間
	白斑8:30/13:30
	夜班20:30/1:30*/
     
    protected void updateStartTime(ScoreDirector scoreDirector, TimeWindowedJob sourceTask) {
        Standstill previousStandstill = sourceTask.getPreviousStandstill();
        Double endTime = previousStandstill == null ? null
                : (previousStandstill instanceof TimeWindowedJob)
                ? ((TimeWindowedJob) previousStandstill).getEndTime()
                : 0;
        TimeWindowedJob shadowTask = sourceTask;
        Double startTime = calculateStartTime(shadowTask, endTime, previousStandstill);
        
        while (shadowTask != null && !Objects.equals(shadowTask.getStartTime(), startTime)) {
            scoreDirector.beforeVariableChanged(shadowTask, "startTime");
            shadowTask.setStartTime(startTime);
            scoreDirector.afterVariableChanged(shadowTask, "startTime");
            endTime = shadowTask.getEndTime();
            shadowTask = shadowTask.getNextJob();
            startTime = calculateStartTime(shadowTask, endTime, previousStandstill);
        }
    }
    
    private Double calculateStartTime(TimeWindowedJob job, Double previousDepartureTime, Standstill previousStandstill) {
    	if(job == null) {
    		return null;
    	}
    	
    	double jobReadyTime = job.getReadyTime();
    	//add by avonchung 20190524 相同料號量產是否要提早投產
    	String arriveAction = "";
    	if((Map)job.getMapParam().get("arrive_time_action") == null) {
    		arriveAction ="OnTime";
    	}else {    	
	    	arriveAction = (String) ((Map)job.getMapParam().get("arrive_time_action")).get("in_value1");
    	}
        
        if (job.getPreviousStandstill() == null) {
            return null;
        }else {        
          //前後model相同，則可接續後一天的排程，不用等到job ready time
          //只有PROD的相同model才可以接下去跑，ENG/SPL就按指定時間排 update by avonchung 20190508
          //根據系統參數arrive_time_action=Earyly才能提早投產-->jobReayTime設為0 //add by avonchung 20190524
          if (job.getPreviousStandstill().getModel() == job.getModel() &&  "PROD".equals(job.getJobType()) 
        		  && "Early".equals(arriveAction)) {
        		  //&& "ENG".equals(job.getJobType()) &&  "SPL".equals(job.getJobType()) ) {
        	  jobReadyTime = 0;
          }
        }
                
    	Setup setup = job.getSetup();
    	List<Double> assignSetupTimeList = setup.getAssignSetuptimeList();
        // PreviousStandstill is the Vehicle, so we leave from the Depot at the best suitable time
    	
    	double startTime = 0;
    	float changeDuration = Float.parseFloat(String.valueOf(job.getSetupDurationFromPreviousStandstill().get("CHANGE_DURATION")));
    	double maxChangeTime = 0;
    	   	
    	List<Double> listCandidate = new ArrayList<Double>();
    	for(Double setuptime : assignSetupTimeList){
//    		if(setuptime >= Math.max(previousDepartureTime,job.getReadyTime())){
//    			listCandidate.add(setuptime);
//    		}
    		
    		//jobReadyTime指的是job的plan date,允許提早換線以因應此job開始排程
    		if(setuptime >= Math.max(previousDepartureTime, jobReadyTime)){
    			listCandidate.add(setuptime);
    		}
    	}
    	if (listCandidate.size() > 0 && changeDuration > 0) {//need to setup
    		maxChangeTime = Double.parseDouble(String.valueOf(listCandidate.get(0)));
    	}else{
//    		maxChangeTime = Math.max(Double.parseDouble(String.valueOf(previousDepartureTime))
//    				,job.getReadyTime());
    		
    		//jobReadyTime指的是job的plan date。推算最早可以換線時間要用jobReadyTime往前考慮換線時長，
    		maxChangeTime = Math.max(Double.parseDouble(String.valueOf(previousDepartureTime))
    				,jobReadyTime);       
    	}
    	startTime = maxChangeTime + changeDuration;
    	
		return startTime;
    }
}
