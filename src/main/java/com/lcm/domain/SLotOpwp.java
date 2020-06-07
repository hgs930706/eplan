package com.lcm.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "S_LOT_OPWP")
public class SLotOpwp implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SLotOpwpId sLotOpwpId;

	@Column(name = "wip_qty")
	private int wipQty;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "lm_time")
	private LocalDateTime lmTime;

	@Column(name = "lm_user")
	private String lmUser;
	

	public SLotOpwpId getsLotOpwpId() {
		return sLotOpwpId;
	}

	public void setsLotOpwpId(SLotOpwpId sLotOpwpId) {
		this.sLotOpwpId = sLotOpwpId;
	}

	public int getWipQty() {
		return wipQty;
	}

	public void setWipQty(int wipQty) {
		this.wipQty = wipQty;
	}

	public LocalDateTime getLmTime() {
		return lmTime;
	}

	public void setLmTime(LocalDateTime lmTime) {
		this.lmTime = lmTime;
	}

	public String getLmUser() {
		return lmUser;
	}

	public void setLmUser(String lmUser) {
		this.lmUser = lmUser;
	}

	@Override
	public String toString() {
		return "SLotOpwp [sLotOpwpId=" + sLotOpwpId + ", wipQty=" + wipQty + ", lmTime=" + lmTime + ", lmUser=" + lmUser
				+ "]";
	}

	
}
