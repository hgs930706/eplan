package com.lcm.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "C_EQP_CAPA")
public class EqpCapa implements Serializable {

	@EmbeddedId
	private EqpCapaId eqpCapaId;

	@Column(name = "fab")
	private String fab;

	@Column(name = "area")
	private String area;

	@Column(name = "model_no")
	private String modelNo;

	@Column(name = "ppc_capa")
	private int ppcCapa;

	@Column(name = "fab_pc_capa")
	private int fabPcCapa;

	@Column(name = "manpower_kilo")
	private int manpowerKilo;

	@Column(name = "lm_user")
	private String lmUser;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "lm_time")
	private LocalDateTime lmTime;

	// 若ppc plan是否=ppc capa/2 則為日班滿載，則需加量到fab pc capa/2  JoshLai@20190731+
	// 若ppc plan是否>=ppc capa 則為整天滿載，則需加量到fab pc capa
	@Transient
	private int adjustPpcCapa;
	
	@Transient
	private int adjustFabPcCapa;

	public EqpCapaId getEqpCapaId() {
		return eqpCapaId;
	}

	public void setEqpCapaId(EqpCapaId eqpCapaId) {
		this.eqpCapaId = eqpCapaId;
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

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public int getPpcCapa() {
		return ppcCapa;
	}

	public void setPpcCapa(int ppcCapa) {
		this.ppcCapa = ppcCapa;
	}

	public int getFabPcCapa() {
		return fabPcCapa;
	}

	public void setFabPcCapa(int fabPcCapa) {
		this.fabPcCapa = fabPcCapa;
	}

	public int getManpowerKilo() {
		return manpowerKilo;
	}

	public void setManpowerKilo(int manpowerKilo) {
		this.manpowerKilo = manpowerKilo;
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

	public int getAdjustPpcCapa() {
		return adjustPpcCapa;
	}

	public void setAdjustPpcCapa(int adjustPpcCapa) {
		this.adjustPpcCapa = adjustPpcCapa;
	}

	public int getAdjustFabPcCapa() {
		return adjustFabPcCapa;
	}

	public void setAdjustFabPcCapa(int adjustFabPcCapa) {
		this.adjustFabPcCapa = adjustFabPcCapa;
	}

	@Override
	public String toString() {
		return "EqpCapa [eqpCapaId=" + eqpCapaId + ", fab=" + fab + ", area=" + area + ", modelNo=" + modelNo
				+ ", ppcCapa=" + ppcCapa + ", fabPcCapa=" + fabPcCapa + ", manpowerKilo=" + manpowerKilo + ", lmUser="
				+ lmUser + ", lmTime=" + lmTime + ", adjustPpcCapa=" + adjustPpcCapa + ", adjustFabPcCapa="
				+ adjustFabPcCapa + "]";
	}
}
