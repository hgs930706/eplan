package com.lcm.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "R_WOM_WOXX")
public class Woxx implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private WoxxId woxxId;

    @Column(name = "part_no")
    private String partNo;

    @Column(name = "valid_cell_grade")
    private String validCellGrade;

    @Column(name = "wo_type")
    private String woType;

    @Column(name = "wo_qty_total")
    private String woQtyTotal;

    @Column(name = "wo_iqty")
    private String woIqty;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "is_closed")
    private String isClosed;

    @Column(name = "status")
    private String status;

    @Column(name = "material_category")
    private String materialCategory;

    @Column(name = "lm_user")
    private String lmUser;

    @Column(name = "lm_time")
    private LocalDateTime lmTime;
    
    @Column(name = "default_final_grade")
    private String defaultFinalGrade;
    
    @Transient
    private int woQty;

    public WoxxId getWoxxId() {
        return woxxId;
    }

    public void setWoxxId(WoxxId woxxId) {
        this.woxxId = woxxId;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getValidCellGrade() {
        return validCellGrade;
    }

    public void setValidCellGrade(String validCellGrade) {
        this.validCellGrade = validCellGrade;
    }

    public String getWoType() {
        return woType;
    }

    public void setWoType(String woType) {
        this.woType = woType;
    }

    public String getWoQtyTotal() {
        return woQtyTotal;
    }

    public void setWoQtyTotal(String woQtyTotal) {
        this.woQtyTotal = woQtyTotal;
    }

    public String getWoIqty() {
        return woIqty;
    }

    public void setWoIqty(String woIqty) {
        this.woIqty = woIqty;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public String getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(String isClosed) {
        this.isClosed = isClosed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMaterialCategory() {
        return materialCategory;
    }

    public void setMaterialCategory(String materialCategory) {
        this.materialCategory = materialCategory;
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

	public int getWoQty() {
		return woQty;
	}

	public void setWoQty(int woQty) {
		this.woQty = woQty;
	}

	public String getDefaultFinalGrade() {
		return defaultFinalGrade;
	}

	public void setDefaultFinalGrade(String defaultFinalGrade) {
		this.defaultFinalGrade = defaultFinalGrade;
	}

	@Override
	public String toString() {
		return "Woxx [woxxId=" + woxxId + ", partNo=" + partNo + ", validCellGrade=" + validCellGrade + ", woType="
				+ woType + ", woQtyTotal=" + woQtyTotal + ", woIqty=" + woIqty + ", startDate=" + startDate
				+ ", isClosed=" + isClosed + ", status=" + status + ", materialCategory=" + materialCategory
				+ ", lmUser=" + lmUser + ", lmTime=" + lmTime + ", defaultFinalGrade=" + defaultFinalGrade + ", woQty="
				+ woQty + "]";
	}
    
}
