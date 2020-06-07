package com.lcm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "R_JOB_SPECIAL")
public class Special implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "site")
    private String site;

    @Column(name = "fab")
    private String fab;

    @Column(name = "area")
    private String area;

    @Column(name = "line")
    private String line;

    @Id
    @Column(name = "job_id")
    private String jobId;

    @Column(name = "model_no")
    private String modelNo;

    @Column(name = "part_no")
    private String partNo;

    @Column(name = "wo_id")
    private String woId;

    @Column(name = "cell_part_no")
    private String cellPartNo;

    @Column(name = "grade")
    private String grade;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "shift_date")
    private LocalDate shiftDate;

    @Column(name = "shift")
    private String shift;

    @Column(name = "change_level")
    private String changeLevel;

    @Column(name = "plan_qty")
    private String planQty;

    @Column(name = "job_type")
    private String jobType;

    @Column(name = "remark")
    private String remark;

    @Column(name = "lm_user")
    private String lmUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "lm_time")
    private LocalDateTime lmTime;
    
    @Transient
	private EqpCapa capa;

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

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getWoId() {
        return woId;
    }

    public void setWoId(String woId) {
        this.woId = woId;
    }

    public String getCellPartNo() {
        return cellPartNo;
    }

    public void setCellPartNo(String cellPartNo) {
        this.cellPartNo = cellPartNo;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
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

    public String getChangeLevel() {
        return changeLevel;
    }

    public void setChangeLevel(String changeLevel) {
        this.changeLevel = changeLevel;
    }

    public String getPlanQty() {
        return planQty;
    }

    public void setPlanQty(String planQty) {
        this.planQty = planQty;
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
    
	public EqpCapa getCapa() {
		return capa;
	}

	public void setCapa(EqpCapa capa) {
		this.capa = capa;
	}

	@Override
	public String toString() {
		return "Special [site=" + site + ", fab=" + fab + ", area=" + area + ", line=" + line + ", jobId=" + jobId
				+ ", modelNo=" + modelNo + ", partNo=" + partNo + ", woId=" + woId + ", cellPartNo=" + cellPartNo
				+ ", grade=" + grade + ", shiftDate=" + shiftDate + ", shift=" + shift + ", changeLevel=" + changeLevel
				+ ", planQty=" + planQty + ", jobType=" + jobType + ", remark=" + remark + ", lmUser=" + lmUser
				+ ", lmTime=" + lmTime + ", capa=" + capa + "]";
	}
    
    
}
