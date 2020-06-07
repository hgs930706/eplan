package com.lcm.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "C_FAC_CONSTRAINT_CAPA")
@IdClass(CFacConstraintCapaPk.class)
public class CFacConstraintCapa {
	@Id
	private String site;
	@Id
	private String fab;
	@Id
	private LocalDate shift_date;
	@Id
	private String shift;
	@Id
	private String score_item;
	private int item_value;
	private String lm_user;
	private LocalDateTime lm_time;
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
	public int getItem_value() {
		return item_value;
	}
	public void setItem_value(int item_value) {
		this.item_value = item_value;
	}
	public String getLm_user() {
		return lm_user;
	}
	public void setLm_user(String lm_user) {
		this.lm_user = lm_user;
	}
	
	public LocalDateTime getLm_time() {
		return lm_time;
	}
	public void setLm_time(LocalDateTime lm_time) {
		this.lm_time = lm_time;
	}
	@Override
	public String toString() {
		return "CFacConstraintCapa [site=" + site + ", fab=" + fab + ", shift_date=" + shift_date + ", shift=" + shift
				+ ", score_item=" + score_item + ", item_value=" + item_value + ", lm_user=" + lm_user + ", lm_time="
				+ lm_time + "]";
	}
	
	
}
