package com.lcm.web.rest.param;

import java.util.List;

public class JobParam {
	private String site;
	private List<String> fab;
	private List<String> area;
	private String trxId;
	private String factChange;
	private String id;
	
	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}
	
	public List<String> getFab() {
		return fab;
	}

	public void setFab(List<String> fab) {
		this.fab = fab;
	}

	public List<String> getArea() {
		return area;
	}

	public void setArea(List<String> area) {
		this.area = area;
	}

	public String getTrxId() {
		return trxId;
	}
	
	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}
	
	public String getFactChange() {
		return factChange;
	}

	public void setFactChange(String factChange) {
		this.factChange = factChange;
	}

	public long getId() {
		return Long.parseLong(id);
	}

	public void setId(String id) {
		this.id = id;
	}
	
//	public List<TempData> getDataList() {
//		return dataList;
//	}
//
//	public void setDataList(List<TempData> dataList) {
//		this.dataList = dataList;
//	}
	
//	public class TempData{
//		public TempData() {
//			
//		}
//		public TempData(String id) {
//			super();
//			this.id = id;
//		}
//
//		private String id;
//
//		public long getId() {
//			return Long.parseLong(id);
//		}
//
//		public void setId(String id) {
//			this.id = id;
//		}
//		
//	}
}


