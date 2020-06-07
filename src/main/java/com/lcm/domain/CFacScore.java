package com.lcm.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@IdClass(CFacScorePK.class)
@Table(name = "C_FAC_SCORE")
public class CFacScore {
	@Id
	@Column(nullable = true)
	private String site;
	@Id
	private String area;
	@Column(nullable = true)
	@Id
	private String constraint_item;
	@Column(nullable = true)
	private String score_level;
	@Column(nullable = true)
	private String weight;
	@Column(nullable = true)
	private String lm_user;
	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lm_time;
	
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getConstraint_item() {
		return constraint_item;
	}
	public void setConstraint_item(String constraint_item) {
		this.constraint_item = constraint_item;
	}
	public String getScore_level() {
		return score_level;
	}
	public void setScore_level(String score_level) {
		this.score_level = score_level;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getLm_user() {
		return lm_user;
	}
	public void setLm_user(String lm_user) {
		this.lm_user = lm_user;
	}
	public Date getLm_time() {
		return lm_time;
	}
	public void setLm_time(Date lm_time) {
		this.lm_time = lm_time;
	}
	
	@Override
	public String toString() {
		return "CFacScore [site=" + site + ", area=" + area + ", constraint_item=" + constraint_item + ", score_level="
				+ score_level + ", weight=" + weight + ", lm_user=" + lm_user + ", lm_time=" + lm_time + "]";
	}
}
