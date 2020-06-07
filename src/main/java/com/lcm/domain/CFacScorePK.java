package com.lcm.domain;

import java.io.Serializable;

public class CFacScorePK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5659380564054941978L;
	private String site;
    private String area;
	private String constraint_item;
	
	
	public CFacScorePK() {
	}
	public CFacScorePK(String site, String constraint_item) {
		super();
		this.site = site;
		this.constraint_item = constraint_item;
	}
	
	public CFacScorePK(String site, String area, String constraint_item) {
		super();
		this.site = site;
		this.area = area;
		this.constraint_item = constraint_item;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getConstraint_item() {
		return constraint_item;
	}
	
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public void setConstraint_item(String constraint_item) {
		this.constraint_item = constraint_item;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((constraint_item == null) ? 0 : constraint_item.hashCode());
		result = prime * result + ((site == null) ? 0 : site.hashCode());
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CFacScorePK other = (CFacScorePK) obj;
		if (constraint_item == null) {
			if (other.constraint_item != null)
				return false;
		} else if (!constraint_item.equals(other.constraint_item))
			return false;
		if (site == null) {
			if (other.site != null)
				return false;
		} else if (!site.equals(other.site))
			return false;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CFacScorePK [site=" + site + ", area=" + area + ", constraint_item=" + constraint_item + "]";
	}
}
