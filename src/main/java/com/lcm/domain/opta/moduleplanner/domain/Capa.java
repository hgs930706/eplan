package com.lcm.domain.opta.moduleplanner.domain;

public class Capa {
	private String site;
	private String fab;
	private String area;
	private String line;
	private String modelNo;
	private String partNo;
	private int ppcCapa;
	private int fabPcCapa;
	private int manpowerKilo;
	
	public Capa(String site, String fab, String area, String line, String modelNo, String partNo, int ppcCapa, int fabPcCapa, int manpowerKilo) {
		this.site = site;
		this.fab = fab;
		this.area = area;
		this.line = line;
		this.modelNo = modelNo;
		this.partNo = partNo;
		this.ppcCapa = ppcCapa;
		this.fabPcCapa = fabPcCapa;
		this.manpowerKilo = manpowerKilo;
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

	@Override
	public String toString() {
		return "Capa [site=" + site + ", fab=" + fab + ", area=" + area + ", line=" + line + ", modelNo=" + modelNo
				+ ", partNo=" + partNo + ", ppcCapa=" + ppcCapa + ", fabPcCapa=" + fabPcCapa + ", manpowerKilo="
				+ manpowerKilo + "]";
	}
	
	
}
