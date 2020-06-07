package com.lcm.domain;

import java.io.Serializable;
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
@IdClass(RJobDashboardPk.class)
@Table(name="R_JOB_DASHBOARD")
public class RJobDashboard implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    @Column(name="SITE")
    private String site;

    @Column(name="FAB")
    private String fab;

    @Column(name="AREA")
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

    @Column(name="JOB_STATUS")
    private String jobStatus;

    @Column(name="CHANGE_SHIFT_DATE")
    private LocalDate changeShiftDate;

    @Column(name="CHANGE_SHIFT")
    private String changeShift;
    
    @Column(name="CHANGE_KEY")
    private String changeKey;
    
    @Column(name="grade")
	private String grade;

    @Column(name="REMARK")
    private String remark;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Transient
    private List<Shift> shiftList;
    
    @Transient
    private int shiftIndex;
    
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

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
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

	public String getChangeKey() {
		return changeKey;
	}

	public void setChangeKey(String changeKey) {
		this.changeKey = changeKey;
	}
	
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
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

	@Override
	public Object clone() throws CloneNotSupportedException {
		RJobDashboard clone = (RJobDashboard) super.clone();
		return clone;
	}

	@Override
	public String toString() {
		return "RJobDashboard [site=" + site + ", fab=" + fab + ", line=" + line + ", jobId=" + jobId + ", shiftDate="
				+ shiftDate + ", shift=" + shift + ", jobType=" + jobType + ", woId=" + woId + ", partNo=" + partNo
				+ ", modelNo=" + modelNo + ", planQty=" + planQty + ", processStartTime=" + processStartTime
				+ ", processEndTime=" + processEndTime + ", changeLevel=" + changeLevel + ", changeDuration="
				+ changeDuration + ", changeStartTime=" + changeStartTime + ", changeEndTime=" + changeEndTime
				+ ", lmUser=" + lmUser + ", lmTime=" + lmTime + ", assignShiftDate=" + assignShiftDate
				+ ", assignShift=" + assignShift + ", forecastQty=" + forecastQty + ", jobStatus=" + jobStatus
				+ ", changeShiftDate=" + changeShiftDate + ", changeShift=" + changeShift + ", changeKey=" + changeKey
				+ ", grade=" + grade + ", shiftIndex=" + shiftIndex + "]";
	}
    
}
