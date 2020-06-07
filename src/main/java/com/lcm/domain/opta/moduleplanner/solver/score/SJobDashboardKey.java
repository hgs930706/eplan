package com.lcm.domain.opta.moduleplanner.solver.score;

import java.time.LocalDate;

public class SJobDashboardKey{
	private String site;
	private String fab;
	private LocalDate shiftDate;
	private String shift;
	
	private LocalDate changeShiftDate;
	private String changeShift;
	
	public SJobDashboardKey(String site, String fab, LocalDate shiftDate, String shift) {
		super();
		this.site = site;
		this.fab = fab;
		this.shiftDate = shiftDate;
		this.shift = shift;
	}

	public SJobDashboardKey(String site, String fab, String changeShift, LocalDate changeShiftDate) {
		super();
		this.site = site;
		this.fab = fab;
		this.changeShiftDate = changeShiftDate;
		this.changeShift = changeShift;
	}

	@Override
	public String toString() {
		return "SJobDashboardKey [site=" + site + ", fab=" + fab + ", shiftDate=" + shiftDate + ", shift=" + shift
				+ ", changeShiftDate=" + changeShiftDate + ", changeShift=" + changeShift + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((changeShift == null) ? 0 : changeShift.hashCode());
		result = prime * result + ((changeShiftDate == null) ? 0 : changeShiftDate.hashCode());
		result = prime * result + ((fab == null) ? 0 : fab.hashCode());
		result = prime * result + ((shift == null) ? 0 : shift.hashCode());
		result = prime * result + ((shiftDate == null) ? 0 : shiftDate.hashCode());
		result = prime * result + ((site == null) ? 0 : site.hashCode());
		return result;
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SJobDashboardKey))
			return false;
		SJobDashboardKey other = (SJobDashboardKey) obj;
		if (changeShift == null) {
			if (other.changeShift != null)
				return false;
		} else if (!changeShift.equals(other.changeShift))
			return false;
		if (changeShiftDate == null) {
			if (other.changeShiftDate != null)
				return false;
		} else if (!changeShiftDate.equals(other.changeShiftDate))
			return false;
		if (fab == null) {
			if (other.fab != null)
				return false;
		} else if (!fab.equals(other.fab))
			return false;
		if (shift == null) {
			if (other.shift != null)
				return false;
		} else if (!shift.equals(other.shift))
			return false;
		if (shiftDate == null) {
			if (other.shiftDate != null)
				return false;
		} else if (!shiftDate.equals(other.shiftDate))
			return false;
		if (site == null) {
			if (other.site != null)
				return false;
		} else if (!site.equals(other.site))
			return false;
		return true;
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

	public LocalDate getChangeShiftDate() {
		return changeShiftDate;
	}

	public void setChangeShiftDate(LocalDate changeShiftDate) {
		this.changeShiftDate = changeShiftDate;
	}

	public String getChangeShift() {
		return changeShift;
	}

	public void setChangeShift(String changeShift) {
		this.changeShift = changeShift;
	}
}
