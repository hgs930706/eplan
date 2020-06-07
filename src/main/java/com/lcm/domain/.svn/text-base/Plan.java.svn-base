package com.lcm.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "R_JOB_PLAN")
public class Plan implements Serializable {

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

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "shift_date")
	private LocalDate shiftDate;

	@Column(name = "model_no")
	private String modelNo;

	@Column(name = "part_no")
	private String partNo;

	@Column(name = "cell_part_no")
	private String cellPartNo;

	@Column(name = "grade")
	private String grade;

	@Column(name = "plan_qty")
	private String planQty;

	@Column(name = "job_type")
	private String jobType;

	@Column(name = "lm_user")
	private String lmUser;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "lm_time")
	private LocalDateTime lmTime;

	@Transient
	private EqpCapa capa;

	// 連續區間最大的Shift Date
	@Transient
	private LocalDate continueMaxShiftDate;

	//判斷當天的ppc plan是否<=ppc capa/2。 JoshLai@20190731+
	//若是，則當天最多只排滿白班(FullDShift)；若不是則最多可排滿整天(FullDay)
	@Transient
	private boolean isFullDShift;
	
	//COG調整後排程依照wip_hours切班後,指定readyTime JoshLai@20190820+
	@Transient
	private Double readyTime;

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

	public LocalDate getContinueMaxShiftDate() {
		return continueMaxShiftDate;
	}

	public void setContinueMaxShiftDate(LocalDate continueMaxShiftDate) {
		this.continueMaxShiftDate = continueMaxShiftDate;
	}

	public boolean isFullDShift() {
		return isFullDShift;
	}

	public void setFullDShift(boolean isFullDShift) {
		this.isFullDShift = isFullDShift;
	}

	public Double getReadyTime() {
		return readyTime;
	}

	public void setReadyTime(Double readyTime) {
		this.readyTime = readyTime;
	}

	@Override
	public String toString() {
		return "Plan [site=" + site + ", fab=" + fab + ", area=" + area + ", line=" + line + ", jobId=" + jobId
				+ ", shiftDate=" + shiftDate + ", modelNo=" + modelNo + ", partNo=" + partNo + ", cellPartNo="
				+ cellPartNo + ", grade=" + grade + ", planQty=" + planQty + ", jobType=" + jobType + ", lmUser="
				+ lmUser + ", lmTime=" + lmTime + ", capa=" + capa + ", continueMaxShiftDate=" + continueMaxShiftDate
				+ ", isFullDShift=" + isFullDShift + ", readyTime=" + readyTime + "]";
	}

}
