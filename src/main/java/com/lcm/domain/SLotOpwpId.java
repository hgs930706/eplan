package com.lcm.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonFormat;

@Embeddable
public class SLotOpwpId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "site")
	private String site;
	
	@Column(name = "fab")
	private String fab;
	
	@Column(name = "area")
	private String area;

	@Column(name = "op")
	private String op;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "shift_date")
	private LocalDate shiftDate;
	
	@Column(name = "shift")
	private String shift;
	
	@Column(name = "part_no")
	private String partNo;
	
	@Column(name = "final_grade")
	private String finalGrade;
	
	@Column(name = "lot_type")
	private String lotType;

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

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
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

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getFinalGrade() {
		return finalGrade;
	}

	public void setFinalGrade(String finalGrade) {
		this.finalGrade = finalGrade;
	}

	public String getLotType() {
		return lotType;
	}

	public void setLotType(String lotType) {
		this.lotType = lotType;
	}

	@Override
	public String toString() {
		return "SLotOpwpId [site=" + site + ", fab=" + fab + ", area=" + area + ", op=" + op + ", shiftDate="
				+ shiftDate + ", shift=" + shift + ", partNo=" + partNo + ", finalGrade=" + finalGrade + ", lotType="
				+ lotType + "]";
	}

}
