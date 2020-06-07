package com.lcm.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class CFacConstraintCapaPk implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4510035868042668680L;
	
	private String site;
	private String fab;
	private LocalDate shift_date;
	private String shift;
	private String score_item;
	
	public CFacConstraintCapaPk() {
	}

	public CFacConstraintCapaPk(String site, String fab, LocalDate shift_date, String shift, String score_item) {
		super();
		this.site = site;
		this.fab = fab;
		this.shift_date = shift_date;
		this.shift = shift;
		this.score_item = score_item;
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

	public LocalDate getShift_date() {
		return shift_date;
	}

	public void setShift_date(LocalDate shift_date) {
		this.shift_date = shift_date;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}
	
	

	public String getScore_item() {
		return score_item;
	}

	public void setScore_item(String score_item) {
		this.score_item = score_item;
	}

	@Override
	public boolean equals(Object arg0) {
		if(this == arg0) return true;
		if(!(arg0 instanceof CFacConstraintCapaPk)) return false;
		CFacConstraintCapaPk that = (CFacConstraintCapaPk)arg0;
		return (getSite() == that.getSite() &&
				getFab() == that.getFab() &&
				getShift_date() == that.getShift_date() &&
				getShift() == that.getShift() &&
				getScore_item() == that.getScore_item());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getSite(),getFab(), getShift_date(), getShift(), getScore_item());
	}
	
	
}
