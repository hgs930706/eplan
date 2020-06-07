package com.lcm.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.lcm.domain.opta.moduleplanner.domain.Shift;


@Entity
@IdClass(SJobDashboardPk.class)
@Table(name = "S_JOB_DASHBOARD")
public class SJobDashboard implements Comparable<SJobDashboard>, Cloneable{
    private static final long serialVersionUID = 1L;

    @Column(name="SITE")
    private String site;

    @Column(name="FAB")
    private String fab;
    
    @Column(name="area")
    private String area;

    @Column(name="LINE")
    private String line;

    @Id
    @Column(name="JOB_ID")
    private String jobId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "SHIFT_DATE")
    private LocalDate shiftDate;

    @Column(name="SHIFT")
    private String shift;

    @Id
    @Column(name="JOB_TYPE")
    private String jobType;
    
    @Id
    @Column(name="WO_ID")
    private String woId;

    @Column(name="PART_NO")
    private String partNo;

    @Column(name="MODEL_NO")
    private String modelNo;

    @Column(name="PLAN_QTY")
    private int planQty;

    @Column(name="PROCESS_START_TIME")
    private LocalDateTime processStartTime;

    @Column(name="PROCESS_END_TIME")
    private LocalDateTime processEndTime;

    @Column(name="CHANGE_LEVEL")
    private String changeLevel;

    @Column(name="CHANGE_DURATION")
    private float changeDuration;

    @Column(name="CHANGE_START_TIME")
    private LocalDateTime changeStartTime;

    @Column(name="CHANGE_END_TIME")
    private LocalDateTime changeEndTime;

    @Column(name="LM_USER")
    private String lmUser;

    @Column(name="LM_TIME")
    private LocalDateTime lmTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "ASSIGN_SHIFT_DATE")
    private LocalDate assignShiftDate;

    @Column(name="ASSIGN_SHIFT")
    private String assignShift;

    @Column(name="FORECAST_QTY")
    private int forecastQty;

    @Column(name="CHANGE_SHIFT_DATE")
    private LocalDate changeShiftDate;

    @Column(name="CHANGE_SHIFT")
    private String changeShift;

    @Id
    @Column(name="TRX_ID")
    private String trxId;

    @Column(name="PLAN_START_DATE")
    private LocalDate planStartDate;

    @Column(name="PLAN_END_DATE")
    private LocalDate planEndDate;
    
    @Transient
	private SJobDashboard nextJob = null;
    
    @Transient
	private boolean isCalled = false;
    
    @Column(name="grade")
	private String grade;
    
    @Transient
    private int isAddTo;
    
    @Column(name="CHANGE_KEY")
    private String changeKey;
    
    @Column(name="JOB_QTY")
    private Integer jobQty;
    
    @Column(name="SHIFT_COUNT_AFTER_CHANGE")
    private Integer shiftCountAfterChange;
    
    @Column(name="AFFECT_CAPA_PERCENT")
    private Double affectCapaPercent;
    
    @Column(name="AFFECT_CAPA_QTY")
    private Integer affectCapaQty;
    
    @Transient
    private List<Shift> shiftList;
    
    @Transient
    private int shiftIndex;
    
    @Transient
    private int iSeq;
    
    public String getTrxId() {
        return trxId;
    }
    
	// Add by JoshLai@20190828+
	@Column(name = "job_ready_time")
	private LocalDateTime jobReadyTime;

    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }

    public LocalDate getPlanStartDate() {
        return planStartDate;
    }

    public void setPlanStartDate(LocalDate planStartDate) {
        this.planStartDate = planStartDate;
    }

    public LocalDate getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(LocalDate planEndDate) {
        this.planEndDate = planEndDate;
    }

    public LocalDate getAssignShiftDate() {
        return assignShiftDate;
    }

    public void setAssignShiftDate(LocalDate assignShiftDate) {
        this.assignShiftDate = assignShiftDate;
    }

    public String getAssignShift() {
        return assignShift;
    }

    public void setAssignShift(String assignShift) {
        this.assignShift = assignShift;
    }

    public LocalDate getChangeShiftDate() {
        return changeShiftDate;
    }

    public void setChangeShiftDate(LocalDate changeShiftDate) {
        this.changeShiftDate = changeShiftDate;
    }

    public String getChangeShift() {
        return changeShift;
    }

    public void setChangeShift(String changeShift) {
        this.changeShift = changeShift;
    }

    public int getForecastQty() {
        return forecastQty;
    }

    public void setForecastQty(int forecastQty) {
        this.forecastQty = forecastQty;
    }

    public float getChangeDuration() {
        return changeDuration;
    }

    public void setChangeDuration(float changeDuration) {
        this.changeDuration = changeDuration;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getFab() {
        return fab;
    }

    public void setFab(String fab) {
        this.fab = fab;
    }

    public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public LocalDate getShiftDate() {
        return shiftDate;
    }

    public void setShiftDate(LocalDate shiftDate) {
        this.shiftDate = shiftDate;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getWoId() {
        return woId;
    }

    public void setWoId(String woId) {
        this.woId = woId;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public int getPlanQty() {
        return planQty;
    }

    public void setPlanQty(int planQty) {
        this.planQty = planQty;
    }

    public LocalDateTime getProcessStartTime() {
        return processStartTime;
    }

    public void setProcessStartTime(LocalDateTime processStartTime) {
        this.processStartTime = processStartTime;
    }

    public LocalDateTime getProcessEndTime() {
        return processEndTime;
    }

    public void setProcessEndTime(LocalDateTime processEndTime) {
        this.processEndTime = processEndTime;
    }

    public String getChangeLevel() {
        return changeLevel;
    }

    public void setChangeLevel(String changeLevel) {
        this.changeLevel = changeLevel;
    }

    public LocalDateTime getChangeStartTime() {
        return changeStartTime;
    }

    public void setChangeStartTime(LocalDateTime changeStartTime) {
        this.changeStartTime = changeStartTime;
    }

    public LocalDateTime getChangeEndTime() {
        return changeEndTime;
    }

    public void setChangeEndTime(LocalDateTime changeEndTime) {
        this.changeEndTime = changeEndTime;
    }

    public String getLmUser() {
        return lmUser;
    }

    public void setLmUser(String lmUser) {
        this.lmUser = lmUser;
    }

    public LocalDateTime getLmTime() {
        return lmTime;
    }

    public void setLmTime(LocalDateTime lmTime) {
        this.lmTime = lmTime;
    }
    
    public SJobDashboard getNextJob() {
		return nextJob;
	}
	public void setNextJob(SJobDashboard nextJob) {
		this.nextJob = nextJob;
	}
	
	public boolean isCalled() {
		return isCalled;
	}

	public void setCalled(boolean isCalled) {
		this.isCalled = isCalled;
	}
	
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public String getChangeKey() {
		return changeKey;
	}

	public void setChangeKey(String changeKey) {
		this.changeKey = changeKey;
	}
	
	public Integer getJobQty() {
		return jobQty;
	}

	public void setJobQty(Integer jobQty) {
		this.jobQty = jobQty;
	}

	public Integer getShiftCountAfterChange() {
		return shiftCountAfterChange;
	}

	public void setShiftCountAfterChange(Integer shiftCountAfterChange) {
		this.shiftCountAfterChange = shiftCountAfterChange;
	}

	public Double getAffectCapaPercent() {
		return affectCapaPercent;
	}

	public void setAffectCapaPercent(Double affectCapaPercent) {
		this.affectCapaPercent = affectCapaPercent;
	}

	public Integer getAffectCapaQty() {
		return affectCapaQty;
	}

	public void setAffectCapaQty(Integer affectCapaQty) {
		this.affectCapaQty = affectCapaQty;
	}
	
	public int getIsAddTo() {
		return isAddTo;
	}

	public void setIsAddTo(int isAddTo) {
		this.isAddTo = isAddTo;
	}
	
	/* --move to timewindowjob -- mark by avonchung 20190415
    public String changeStartShiftType() {
		LocalDateTime datetime = changeStartTime;
		int hr = datetime.getHour();
		int minute = datetime.getMinute();
		if(hr < 8) {
			return "N";
		}else if(hr >=8 && (hr < 20 || (hr <= 20 && minute == 0)))
			return "D";
		else
			return "N";
	}*/
	
    /* --move to timewindowjob -- mark by avonchung 20190415
	public String changeEndShiftType() {
		int hr = changeEndTime.getHour();
		int minute = changeEndTime.getMinute();
		if(hr < 8) {
			return "N";
		}else if(hr >=8 && (hr < 20 || (hr <= 20 && minute == 0)))
			return "D";
		else
			return "N";
	}*/
	
	
	public LocalDateTime getJobReadyTime() {
		return jobReadyTime;
	}

	public void setJobReadyTime(LocalDateTime jobReadyTime) {
		this.jobReadyTime = jobReadyTime;
	}

	public List<Shift> getShiftList() {
		return shiftList;
	}

	public void setShiftList(List<Shift> shiftList) {
		this.shiftList = shiftList;
		//設定班別 JoshLai@20190415
		for(Shift shift : shiftList) {
			LocalDate shiftDate = shift.getShiftDate().getDate();
			String shiftType = shift.getShiftType().getCode();
			if(this.shiftDate.equals(shiftDate) && this.shift.equals(shiftType)) {
				shiftIndex = shift.getIndex();
			}
		}
	}

	public int getShiftIndex() {
		setShiftList(this.shiftList);
		return shiftIndex;
	}
	
	public void setShiftIndex(int shiftIndex) {
		this.shiftIndex = shiftIndex;
	}

	public LocalDateTime getAssignStartTime() {
		int hr = 8;
		if("N".equals(assignShift)) {
			hr = 20;
		}
		LocalDateTime assignStartTime = assignShiftDate.atTime(hr, 0);
		
		return assignStartTime;
	}
	
    public int getiSeq() {
		return iSeq;
	}

	public void setiSeq(int iSeq) {
		this.iSeq = iSeq;
	}

	@Override
	public int compareTo(SJobDashboard o) {
		int result = this.changeShiftDate.compareTo(o.getChangeShiftDate());
		if(result == 0) {
			result = this.changeShift.compareTo(o.getChangeShift());
		}
		return result;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		SJobDashboard clone = (SJobDashboard) super.clone();
		return clone;
	}
	

	@Override
	public String toString() {
		return "SJobDashboard [site=" + site + ", fab=" + fab + ", area=" + area + ", line=" + line + ", jobId=" + jobId
				+ ", shiftDate=" + shiftDate + ", shift=" + shift + ", jobType=" + jobType + ", woId=" + woId
				+ ", partNo=" + partNo + ", modelNo=" + modelNo + ", planQty=" + planQty + ", processStartTime="
				+ processStartTime + ", processEndTime=" + processEndTime + ", changeLevel=" + changeLevel
				+ ", changeDuration=" + changeDuration + ", changeStartTime=" + changeStartTime + ", changeEndTime="
				+ changeEndTime + ", lmUser=" + lmUser + ", lmTime=" + lmTime + ", assignShiftDate=" + assignShiftDate
				+ ", assignShift=" + assignShift + ", forecastQty=" + forecastQty + ", changeShiftDate="
				+ changeShiftDate + ", changeShift=" + changeShift + ", trxId=" + trxId + ", planStartDate="
				+ planStartDate + ", planEndDate=" + planEndDate + ", nextJob=" + nextJob + ", isCalled=" + isCalled
				+ ", grade=" + grade + ", isAddTo=" + isAddTo + ", changeKey=" + changeKey + ", jobQty=" + jobQty
				+ ", shiftCountAfterChange=" + shiftCountAfterChange + ", affectCapaPercent=" + affectCapaPercent
				+ ", affectCapaQty=" + affectCapaQty + ", shiftIndex=" + shiftIndex
				+ ", iSeq=" + iSeq + ", jobReadyTime=" + jobReadyTime + "]";
	}
}
