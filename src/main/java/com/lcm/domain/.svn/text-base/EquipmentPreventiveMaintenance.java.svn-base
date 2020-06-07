package com.lcm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "R_JOB_EQPM")
public class EquipmentPreventiveMaintenance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "job_id")
    private String jobId;

    @Column(name = "site")
    private String site;

    @Column(name = "fab")
    private String fab;

    @Column(name = "area")
    private String area;

    @Column(name = "line")
    private String line;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "shift_date")
    private LocalDate shiftDate;

    @Column(name = "shift")
    private String shift;

    @Column(name = "pm_duration")
    private String pmDuration;

    @Column(name = "job_type")
    private String jobType;

    @Column(name = "remark")
    private String remark;

    @Column(name = "lm_user")
    private String lmUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "lm_time")
    private LocalDateTime lmTime;
    
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

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
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

    public String getPmDuration() {
        return pmDuration;
    }

    public void setPmDuration(String pmDuration) {
        this.pmDuration = pmDuration;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

	@Override
	public String toString() {
		return "EquipmentPreventiveMaintenance [jobId=" + jobId + ", site=" + site + ", fab=" + fab + ", area=" + area
				+ ", line=" + line + ", shiftDate=" + shiftDate + ", shift=" + shift + ", pmDuration=" + pmDuration
				+ ", jobType=" + jobType + ", remark=" + remark + ", lmUser=" + lmUser + ", lmTime=" + lmTime + "]";
	}
    
    
}
