/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
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

package com.lcm.domain.opta.moduleplanner.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.entity.PlanningPin;
import org.optaplanner.core.api.domain.variable.CustomShadowVariable;
import org.optaplanner.core.api.domain.variable.PlanningVariableReference;
import org.slf4j.LoggerFactory;

import com.lcm.domain.opta.moduleplanner.solver.ArrivalTimeUpdatingVariableListener;
import com.lcm.domain.opta.moduleplanner.utility.ModulePlannerUtility;

@PlanningEntity
public class TimeWindowedJob extends Job {
	final static org.slf4j.Logger logger = LoggerFactory.getLogger(TimeWindowedJob.class);
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Times are multiplied by 1000 to avoid floating point arithmetic rounding errors
    private double readyTime;
    private double dueTime;
    private double serviceDuration;
    private double endTime;
    private Shift shift;
    private Setup setup;
    // Shadow variable
    private Double startTime;
    @PlanningPin
    private boolean pinned;
    
    public TimeWindowedJob() {
    }
    
    public TimeWindowedJob(int id, Model model, int demand, PlanLine line) {
        super(id, model, demand, line);
        pinned = false;
    }

	/**
     * @return a positive number, the time multiplied by 1000 to avoid floating point arithmetic rounding errors
     */
    public double getReadyTime() {
        return readyTime;
    }

    public void setReadyTime(double readyTime) {
        this.readyTime = readyTime;
    }

    /**
     * @return a positive number, the time multiplied by 1000 to avoid floating point arithmetic rounding errors
     */
    public double getDueTime() {
        return dueTime;
    }

    public void setDueTime(double dueTime) {
        this.dueTime = dueTime;
    }

    /**
     * @return a positive number, the time multiplied by 1000 to avoid floating point arithmetic rounding errors
     */
//    public double getServiceDuration() {
//        return serviceDuration;
//    }

    public void setServiceDuration(double serviceDuration) {
        this.serviceDuration = serviceDuration;
    }
    

    public Setup getSetup() {
		return setup;
	}

	public void setSetup(Setup setup) {
		this.setup = setup;
	}

	/**
     * @return a positive number, the time multiplied by 1000 to avoid floating point arithmetic rounding errors
     */
    @CustomShadowVariable(variableListenerClass = ArrivalTimeUpdatingVariableListener.class,            
            sources = {@PlanningVariableReference(variableName = "previousStandstill")})
    public Double getStartTime() {
        return startTime;
    }

    public void setStartTime(Double startTime) {
        this.startTime = startTime;
//        List<Shift> shiftList = App.shiftList;
//        if(this.startTime != null){
//	        int interval = (int) (this.startTime/12);
//	//        shiftList = 0:12/26 D , 1:12/26 N, 2:12/27 D, 12/27 N
////	        System.out.println("getShift==========> " + shiftList.get(interval));
////	        this.shift = shiftList.get(interval);
//	        setShift(shiftList.get(interval));
//        }
    }
    
    public Shift getShift() {
        return shift;
    }
    public void setShift(Shift shift) {
    	this.shift = shift;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    /**
     * @return a positive number, the time multiplied by 1000 to avoid floating point arithmetic rounding errors
     */
    public double getEndTime() {
        if (startTime == null) {
            return 0;
        }else{
//        	endTime = Math.max(startTime, readyTime) + getServiceDuration(); //Marked up by JoshLai@20190311 直接拿getStartTime()
        	endTime = getStartTime() + getServiceDuration();
        }
        
        return endTime;
    }
    
    public void setEndTime(double endTime) {
		this.endTime = endTime;
	}
    
	public boolean isArrivalBeforeReadyTime() {
        return startTime != null
                && startTime < readyTime;
    }

    public boolean isArrivalAfterDueTime() {
        return startTime != null
                && dueTime < startTime;
    }
    
    public ShiftDate getShiftDate() {
        return shift.getShiftDate();
    }

    public ShiftType getShiftType() {
        return shift.getShiftType();
    }

    public int getShiftDateDayIndex() {
        return shift.getShiftDate().getDayIndex();
    }

    public DayOfWeek getShiftDateDayOfWeek() {
        return shift.getShiftDate().getDayOfWeek();
    }
    
    @Override
    public TimeWindowedJob getNextJob() {
        return (TimeWindowedJob) super.getNextJob();
    }

    
    /**
     * @return a positive number, the time multiplied by 1000 to avoid floating point arithmetic rounding errors
     */
    public double getTimeWindowGapTo(TimeWindowedJob other) {
        // dueTime doesn't account for serviceDuration
    	double latestDepartureTime = dueTime + getServiceDuration();
    	double otherLatestDepartureTime = other.getDueTime() + other.getServiceDuration();
        if (latestDepartureTime < other.getReadyTime()) {
            return other.getReadyTime() - latestDepartureTime;
        }
        if (otherLatestDepartureTime < readyTime) {
            return readyTime - otherLatestDepartureTime;
        }
        return 0;
    }
 
       
	public double getServiceDuration() {// capa
		if (ModulePlannerUtility.SPECIFIC_JOB_TYPE.contains(getJobType())) {
			return getPmDuration();
		} else {
			double perHrQty = getCapa() / 24.0;
//			logger.debug("getCapa(): " + getCapa() + " perHrQty: " + perHrQty + " lineNo: " + this.getLineNo() + " model: " + this.getModel_no());
			if (perHrQty == 0)
				return 0;
			double value = forecastQty / perHrQty;
//			logger.debug("forecastQty: " + forecastQty + " perHrQty: " + perHrQty + " value= " + value);
			return value;
		}
	}
	
	//每千片人力
	public double getManpowerKilo() {
		double perKiloManpower = getManpower() / 1000.0;
		if (perKiloManpower == 0)
			return 0;
		double value = forecastQty * perKiloManpower;
		return value;
	}

    public int getCapa() {
    	if(this.getModel() == null || this.getLine().getCapa().get(this.getModel()) == null)
    		return 0;
    	else
    		return Integer.parseInt(String.valueOf(this.getLine().getCapa().get(this.getModel()).get("FAB_PC_CAPA")));
    }
    
    
    public int getManpower() {
    	if(this ==null || this.getModel() == null || this.getLine()==null || this.getLine().getCapa()==null || this.getLine().getCapa().get(this.getModel()) == null)
    		return 0;
    	else
    		return Integer.parseInt(String.valueOf(this.getLine().getCapa().get(this.getModel()).get("MANPOWER")));
    }
    

    public boolean isPinned() {
		return pinned;
	}

	public void setPinned(boolean pinned) {
		this.pinned = pinned;
	}
	
	//add by avonchung 20190410
	public String changeStartShiftType() {
		//changeStartTime = processStartTime - changeDuration
		LocalDateTime procStartTime = ModulePlannerUtility.calcDateTimeByNum(this.getPlanStartDate(),this.getShiftDstart(), startTime);
		double dSub = (float)-(float)this.getSetupDurationFromPreviousStandstill().get("CHANGE_DURATION");
		LocalDateTime dChangeStart = ModulePlannerUtility.addHourReturnDate(procStartTime, dSub);
		int hr = dChangeStart.getHour();
		int minute = dChangeStart.getMinute();
		if(hr < 8) {
			return "N";
		}else if(hr >=8 && (hr < 20 || (hr == 20 && minute == 0)))
			return "D";
		else
			return "N";
	}
	
	//add by avonchung 20190410
	public String changeEndShiftType() {
		//changeEndTime = processStartTime
		LocalDateTime changeEndTime = ModulePlannerUtility.calcDateTimeByNum(this.getPlanStartDate(),this.getShiftDstart(), startTime);
		int hr = changeEndTime.getHour();
		int minute = changeEndTime.getMinute();
		if(hr < 8) {
			return "N";
		}else if(hr >=8 && (hr < 20 || (hr == 20 && minute == 0)))
			return "D";
		else
			return "N";
	}
	
	
	
	@Override
	public String toString() {
		return "TimeWindowedJob [jobId="+getJobId()+",planQty=" + planQty + ", forecastQty=" + forecastQty + ", id=" + id + ", getDueTime()="
				+ getDueTime() + ", getStartTime()=" + getStartTime()+ ""
				+" ,endTime: " + endTime
//				+" ,getEndTime() " + getEndTime() //在此不能call getEndTime()因為會endTime會被重置
				+ ", isIs_add_to()="+ getIsAddTo()
				+ ", getLine()="+ getLine()
				+ ", getJob_type()="+ getJobType()
				+ ", getModel_no()="+ getModel_no()
				+ ", getModel()="+ getModel()
				+ ", getAssignStartTime()="+ getAssignStartTime()
				+ ", getShift_date()="+ getShift_date()
				+ ", getWoId()="+ getWoId()
				+ ", readyTime()="+ getReadyTime()
				+ ", serviceDuration="+ serviceDuration
				+ ", getPossibleLineList()="+ getPossibleLineList()
				+ ", getStandstillChangeKey()="+ getSetupDurationFromPreviousStandstill().get("CHANGE_KEY")
				+ ", getStandstillChangeLevel()="+ getSetupDurationFromPreviousStandstill().get("CHANGE_LEVEL")
				+ ", getStandstillChangeDuration()="+ getSetupDurationFromPreviousStandstill().get("CHANGE_DURATION")
				+ ", getChangeKey()="+ getChangeKey()
				+ ", getChangeLevel()="+ getChangeLevel()
				+ ", getChangeDuration()="+ getChangeDuration()
				+ ", pinned="+ pinned+ "]";
	}
}
