package com.lcm.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class WoxxId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "site")
	private String site;

	@Column(name = "fab")
	private String fab;

	@Column(name = "area")
	private String area;

	@Column(name = "wo_id")
	private String woId;

	@Column(name = "material")
	private String material;
	
	@Column(name = "wo_version")
    private String woVersion;

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

	public String getWoId() {
		return woId;
	}

	public void setWoId(String woId) {
		this.woId = woId;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
	
	public String getWoVersion() {
		return woVersion;
	}

	public void setWoVersion(String woVersion) {
		this.woVersion = woVersion;
	}

	@Override
	public String toString() {
		return "WoxxId [site=" + site + ", fab=" + fab + ", area=" + area + ", woId=" + woId + ", material=" + material
				+ ", woVersion=" + woVersion + "]";
	}

}
