/*
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
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

package com.lcm.domain.opta.moduleplanner.solver.score;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.optaplanner.core.api.score.buildin.bendable.BendableScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lcm.domain.CFacConstraintCapa;
import com.lcm.domain.ModChange;
import com.lcm.domain.RJobDashboard;
import com.lcm.domain.SJobDashboard;
import com.lcm.domain.opta.moduleplanner.domain.PlanLine;
import com.lcm.domain.opta.moduleplanner.domain.Shift;
import com.lcm.domain.opta.moduleplanner.domain.Standstill;
import com.lcm.domain.opta.moduleplanner.domain.TaskAssignmentSolution;
import com.lcm.domain.opta.moduleplanner.domain.TimeWindowedJob;
import com.lcm.domain.opta.moduleplanner.utility.ModulePlannerUtility;

public class TaskEasyScoreCalculator_S06 implements EasyScoreCalculator<TaskAssignmentSolution> {
	Logger logger = LoggerFactory.getLogger(TaskEasyScoreCalculator_S06.class);
	
	private int hardScore;
    private int soft0Score;
    private int soft1Score;
    
    
    private List<CFacConstraintCapa> cFacConstraintCapaList;
    private Map<String, Object> mapParam;
    private List<PlanLine> lineList;
	
	@Override
	public BendableScore calculateScore(TaskAssignmentSolution solution) {
		List<SJobDashboard> sJobList;
		DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyyMMdd");
		List<Shift> shiftList = solution.getShiftList();
		List<TimeWindowedJob> jobList = solution.getJobList();
		lineList = solution.getLineList();
		List<ModChange> cModChangeList = solution.getcModChangeList();
		List<Object> historicalList = solution.getHistoricalList();
		
		Map<PlanLine, Long> machineDurationMap = new HashMap<>(lineList.size());
		Map<String, Map> dailyShiftMachine = new HashMap<String, Map>();
		for (Shift shift : shiftList) {
			for (PlanLine line : lineList) {
				String key = shift.getShiftDate().getDate().format(sdf) + shift.getShiftType().getCode();
				machineDurationMap.put(line, 0l);
				dailyShiftMachine.put(key, machineDurationMap);
			}
		}
		Map<String, Map> dailyShiftMachineClone = new HashMap<String, Map>(dailyShiftMachine);
		
		sJobList = new ArrayList<>();
		cFacConstraintCapaList = solution.getcFacConstraintCapaList();
		mapParam = solution.getMapParam();

		hardScore = 0;
		soft0Score = 0;
		soft1Score = 0;
		Map<PlanLine, Integer> sumMachine = new HashMap<PlanLine, Integer>();
		logger.debug(jobList.size() + " calculateScore jobList: " + jobList);
		for (TimeWindowedJob job : jobList) {
			logger.debug("job:" + job + "  ServiceDuration-->" + job.getServiceDuration());
			logger.debug("job.getStartTime-->" + job.getStartTime());
			Standstill previousStandstill = job.getPreviousStandstill();
			logger.debug("previousStandstill==> " + previousStandstill);
			if (previousStandstill != null) {
				long setupDuration = (long) job.getSetupDurationFromPreviousStandstill().get("CHANGE_DURATION");
				String changeKey = (String) job.getSetupDurationFromPreviousStandstill().get("CHANGE_KEY");
				double serviceDuration = job.getServiceDuration();
				double taskStarttime = job.getStartTime();
				double taskEndtime = job.getEndTime();
//	        	
				//assignLine
				PlanLine assignLine = job.getAssignLine();
				if (!job.getLine().equals(assignLine)) {
					hardScore -= 1000;
				}
				
				//EndAfterDueTime
				double dueTime = job.getDueTime();
				Double departureTime = job.getEndTime();
				
				//把多加的量，拆成另一個Job (is_add_to =Y) , 超過due time 且is_add_to = N才扣分
				if (departureTime > dueTime && 0 == job.getIsAddTo()) {
					hardScore -= departureTime - dueTime  ;
				}
				
				//capa
				int demand = job.getForecastQty();
				PlanLine taskLine = job.getLine();
				if (sumMachine.get(taskLine) != null) {
					int sumDemand = sumMachine.get(taskLine) + demand;
					sumMachine.put(taskLine, sumDemand);
				} else {
					sumMachine.put(taskLine, demand);
				}
				
				//accumulateSetupTime
				soft1Score -= ((Long)job.getSetupDurationFromPreviousStandstill().get("CHANGE_DURATION")).intValue();
				
				//special job priority
				long changeDuration = Long.parseLong(String.valueOf(job.getSetupDurationFromPreviousStandstill().get("CHANGE_DURATION")));
				if(job.getLine() != null && "SPL".equals(job.getJobType())) {
					double startTime = job.getStartTime();
					double assignStartTime = job.getAssignStartTime();
					double score = Math.abs(assignStartTime - (startTime-changeDuration));
					soft0Score -= score;
				}
				
				//eng job priority
				if(job.getLine() != null && "ENG".equals(job.getJobType())) {
					double startTime = job.getStartTime();
					double assignStartTime = job.getAssignStartTime();
					double score = Math.abs(assignStartTime - (startTime-changeDuration));
					soft0Score -= score;
				}
				
				//pm job priority
				if(job.getLine() != null && ModulePlannerUtility.SPECIFIC_JOB_TYPE.contains(job.getJobType())) {
					double startTime = job.getStartTime();
					double assignStartTime = job.getAssignStartTime();
					double score = Math.abs(assignStartTime - (startTime-changeDuration));
					soft0Score -= score;
				}
				
				
				//開線數&Commit量
				List<SJobDashboard> tempList = ModulePlannerUtility.splitByShift(job, mapParam, false);
				logger.debug(tempList.size() + " tempList==> " + tempList);
				sJobList.addAll(tempList);
			}
		}
		
		
		logger.info(sJobList.size() + " sJobList=> " + sJobList);
		if(sJobList.size() > 0) {
			logger.debug("=================================");
			Map<String, Map<String, Object>> mapCommit91 = calcCommitQtyByPartLevel(sJobList, mapParam, "91");
			logger.debug("mapCommit91: " + mapCommit91);
			
			Map<String, Map<String, Object>> mapCommit97 = calcCommitQtyByPartLevel(sJobList, mapParam, "97");
			logger.debug("mapCommit97: " + mapCommit97);
			
			Map<String, Map<String, Object>> mapOpenLine = ModulePlannerUtility.calcOpenLine(sJobList, mapParam);
			logger.debug("mapOpenLine2: " + mapOpenLine);
			
			//計算91階commit量 Map.Key = is_commit_91
			calcOpenLineLimit(mapCommit91);
			//計算97階commit量 Map.Key = is_commit_97
			calcOpenLineLimit(mapCommit97);
			//計算開線數 Map.Key = is_open_line
			calcOpenLineLimit(mapOpenLine);
	        
			//計算換線次數 Map.Key = is_change_limit
			logger.debug(mapParam.size() + " mapParam: " + mapParam);
			String planSite = (String) ((Map<String, Object>)mapParam.get("site")).get("in_value1");
	        Map<String, Map<String, Map<String, Object>>> mapChangeLimit = ModulePlannerUtility.calcChangeLimitBySite(mapParam, sJobList, planSite, lineList);
	        logger.debug(mapChangeLimit.size() + " mapChangeLimit: " + mapChangeLimit);
	        Map<String, Object> mapScore = ModulePlannerUtility.clacChangeLimitResult(mapChangeLimit);
	        logger.debug(mapScore.size() + " mapScore: " + mapScore);
	        
	        hardScore -= ModulePlannerUtility.calcS06L2CantInNightShift(mapScore, mapParam);
	        
	        Map<String, Object> mapChangeResult = ModulePlannerUtility.calcChangeLimitS06(mapScore, mapParam); 
	        hardScore -= (Integer) mapChangeResult.get("score");
	        
	        //不允許同時換線
	        calcDisallowedSameDayChange(sJobList);
	        //不允許跨班換線
	        calcDisallowedCrossChange(sJobList);
	        
	        //換線影響
			try {
				ModulePlannerUtility.calcSetupAffect(sJobList, mapParam, false);
			} catch (Exception e) {
				StringWriter errors = new StringWriter();
    			e.printStackTrace(new PrintWriter(errors));
    			logger.error(errors.toString());
    			e.printStackTrace();
			}
	        
			logger.debug("=================================");
		}
		return BendableScore.of(new int[] {hardScore}, new int[] {soft0Score, soft1Score});
	}
	
	private Map<String, Map<String, Object>> calcCommitQtyByPartLevel(List<SJobDashboard> listPlanResult, Map<String, Object> mapParam, String partLevel){
		Map<String, Map<String, Object>> mapOpenLine = new HashMap<>();
		mapOpenLine = calcCommitOpenLimit(listPlanResult, mapParam, partLevel);
		return mapOpenLine;
	}
	
	private Map<String, Map<String, Object>> calcCommitOpenLimit(List<SJobDashboard> listPlanResult, Map<String, Object> mapParam, String partLevel){
		//計算commit量
        Map<String, Map<String, Object>> mapOpenLine = new HashMap<>();
        for(Iterator<SJobDashboard> iter = listPlanResult.iterator(); iter.hasNext();) {
        	SJobDashboard job = iter.next();
        	
        	if(!ModulePlannerUtility.SPECIFIC_JOB_TYPE.contains(job.getJobType())) {
	        	int qty = job.getForecastQty();
	        	String site = job.getSite();
	        	String fab = job.getFab();
	        	String shiftDate = ModulePlannerUtility.formatterNoSlash.format(job.getShiftDate());
	        	String shift = job.getShift();
	        	String partNo = job.getPartNo();
	        	
	        	//計算commit量
	    		int partQty = 0;
	        	
	    		Map<String, Object> temp = new HashMap<>();
	        	String key = site+"_"+fab+"_"+shiftDate+"_"+shift;
	        	if(mapOpenLine.get(key) != null) {
	    			int commit_qty = (int) mapOpenLine.get(key).get("commit_qty" + partLevel);
	    			partQty = commit_qty;
	    			
	    			Map<String, Object> temp2 = mapOpenLine.get(key);
	    			temp.putAll(temp2);
	        	}
	    		if(partNo.startsWith(partLevel)) {
	    			partQty += qty;
	    		}
	    		temp.put("commit_qty" + partLevel, partQty);
	    		mapOpenLine.put(key, temp);
        	}
        }
        mapOpenLine.put("is_commit_"+partLevel, new HashMap<>());
        return mapOpenLine;
	}
		
	private void calcOpenLineLimit(Map<String, Map<String, Object>> map) {
		for(Entry<String, Map<String, Object>> entry : map.entrySet()) {
        	int commit_qty97 = 0;
	        int commit_qty91 = 0;
	        int open_line = 0;
	        
	        int openLineLimit = 0;
			int limit91 = 0;
			int limit97 = 0;
			
        	String key = entry.getKey();
        	logger.debug("key: " + key);
    		for(CFacConstraintCapa obj : cFacConstraintCapaList) {
    			String site = obj.getSite();
				String fab = obj.getFab();
				String shiftDate = ModulePlannerUtility.formatterNoSlash.format(obj.getShift_date());
				String shiftTypeStr = obj.getShift();
				
				if((site+"_"+fab+"_"+shiftDate+"_"+shiftTypeStr).equals(key)) {
					if("open_line".equals(obj.getScore_item())) {
						openLineLimit = obj.getItem_value();
					}else if("91_limit".equals(obj.getScore_item())) {
						limit91 = obj.getItem_value();
					}else if("97_limit".equals(obj.getScore_item())) {
						limit97 = obj.getItem_value();
					}
				}
			}
    		
    		if(entry.getValue().containsKey("commit_qty97"))
    			commit_qty97 = (int) entry.getValue().get("commit_qty97");
    		
    		if(entry.getValue().containsKey("commit_qty91"))
    			commit_qty91 = (int) entry.getValue().get("commit_qty91");
    		
    		if(entry.getValue().containsKey("open_line"))
    			open_line = (int) entry.getValue().get("open_line");
    		
    		logger.debug("open_line: " + open_line + " openLineLimit: " + openLineLimit);
    		logger.debug("commit_qty91: " + commit_qty91 + " limit91: " + limit91);
    		logger.debug("commit_qty97: " + commit_qty97 + " limit97: " + limit97);
    		
    		if(openLineLimit != 0 && open_line > openLineLimit) {
    			soft1Score -= open_line - openLineLimit;
    			logger.debug("[open_line]2" + "soft1Score: " + soft1Score + "open_line: " + open_line + " openLineLimit: " + openLineLimit);
    		}
    		
    		if(limit91 != 0 && commit_qty91 > limit91) {
    			soft1Score -= commit_qty91 - limit91;
    			logger.debug("[commit_qty91]2" + "soft1Score: " + soft1Score + "commit_qty91: " + commit_qty91 + " limit91: " + limit91);
    		}
    		
    		if(limit97 != 0 && commit_qty97 > limit97) {
    			soft1Score -= commit_qty97 - limit97;
    			logger.debug("[commit_qty97]2" + "soft1Score: " + soft1Score +  "commit_qty97: " + commit_qty97 + " limit97: " + limit97);
    		}
        }
	}
	
	private void calcDisallowedSameDayChange(List<SJobDashboard> sJobList) {
		List<SJobDashboard> sJobListCopy = removeNullElement(sJobList);
		logger.debug(sJobListCopy.size() + " sJobListCopy: " + sJobListCopy);
		
		sJobListCopy.sort(Comparator.comparing(SJobDashboard::getChangeShiftDate)
				.thenComparing(SJobDashboard::getChangeShift));
		logger.debug("after sJobList sort: " + sJobListCopy);
		
		for(int i=0; i<sJobListCopy.size(); i++) {
			logger.debug("sJob==> " + sJobListCopy.get(i));
			SJobDashboard currentJob = sJobListCopy.get(i);
			SJobDashboard nextJob = null;
			if(("S02".equals(currentJob.getSite()) && "3E".equals(currentJob.getFab()) 
				&& ("S023EJI01".equals(currentJob.getLine()) || "S023EJI02".equals(currentJob.getLine())))
					|| ("S02".equals(currentJob.getSite()) && "2E".equals(currentJob.getFab()) 
					&& ("S022EJI06".equals(currentJob.getLine()) || "S022EJI07".equals(currentJob.getLine())))){
				if(i+1 < sJobListCopy.size()) {
					nextJob = sJobListCopy.get(i+1);
				}
				if(nextJob != null) {
					LocalDate changeShiftDate = currentJob.getChangeShiftDate();
					String changeShift = currentJob.getChangeShift();
					
					LocalDate nextChangeShiftDate = nextJob.getChangeShiftDate();
					String nextChangeShift = nextJob.getChangeShift();
					
					if(changeShiftDate.isEqual(nextChangeShiftDate) && changeShift.equals(nextChangeShift)) {
						hardScore -= 1;
						logger.info("here...................");
					}
				}
			}
		}
	}
	
	private void calcDisallowedCrossChange(List<SJobDashboard> sJobList) {
		for(SJobDashboard sJob : sJobList) {
			if(!ModulePlannerUtility.SPECIFIC_JOB_TYPE.contains(sJob.getJobType())) {
				if(sJob.getChangeStartTime() != null) {
					String startShiftType = (String) ModulePlannerUtility.calcShiftByProcStartTime(sJob.getChangeStartTime(), mapParam).get("SHIFT_TYPE");
					String endShiftType = (String) ModulePlannerUtility.calcShiftByProcStartTime(sJob.getChangeEndTime(), mapParam).get("SHIFT_TYPE");
					if(!startShiftType.equals(endShiftType)) {
						hardScore -= 1;
					}
				}
			}
		}
	}
	
	private List<SJobDashboard> removeNullElement(List<SJobDashboard> sJobList){
		//remove change shift date is null
		List<SJobDashboard> sJobListCopy = new ArrayList<>(sJobList);
		Iterator<SJobDashboard> iter = sJobListCopy.iterator();
		while(iter.hasNext()) {
			SJobDashboard job = iter.next();
			if(job.getChangeShiftDate() == null)
				iter.remove();
		}
		return sJobListCopy;
	}
}
