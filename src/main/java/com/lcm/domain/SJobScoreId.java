package com.lcm.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable//可嵌入的
public class SJobScoreId implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    @Column(name="SITE")
    private String site;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="PLAN_START_DATE")
    private LocalDate planStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="PLAN_END_DATE")
    private LocalDate planEndDate;

    @Column(name="TRX_ID")
    private String trxId;

    @Column(name="TOTAL_SCORE")
    private String totalScore;

    @Column(name="CONSTRAINT_ITEM")
    private String constraintItem;

    @Column(name="SCORE_ITEM")
    private String scoreItem;

    @Column(name="SCORE")
    private String score;
    
    @Column(name="ITEM_SEQ")
    private String itemSeq;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public LocalDate getPlanStartDate() {
        return planStartDate;
    }

    public void setPlanStartDate(LocalDate planStartDate) {
        this.planStartDate = planStartDate;
    }

    public LocalDate getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(LocalDate planEndDate) {
        this.planEndDate = planEndDate;
    }

    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getConstraintItem() {
        return constraintItem;
    }

    public void setConstraintItem(String constraintItem) {
        this.constraintItem = constraintItem;
    }

    public String getScoreItem() {
        return scoreItem;
    }

    public void setScoreItem(String scoreItem) {
        this.scoreItem = scoreItem;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
    
    public String getItemSeq() {
		return itemSeq;
	}

	public void setItemSeq(String itemSeq) {
		this.itemSeq = itemSeq;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((constraintItem == null) ? 0 : constraintItem.hashCode());
		result = prime * result + ((itemSeq == null) ? 0 : itemSeq.hashCode());
		result = prime * result + ((scoreItem == null) ? 0 : scoreItem.hashCode());
		result = prime * result + ((trxId == null) ? 0 : trxId.hashCode());
		return result;
	}
    
    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SJobScoreId))
			return false;
		SJobScoreId other = (SJobScoreId) obj;
		if (constraintItem == null) {
			if (other.constraintItem != null)
				return false;
		} else if (!constraintItem.equals(other.constraintItem))
			return false;
		if (itemSeq == null) {
			if (other.itemSeq != null)
				return false;
		} else if (!itemSeq.equals(other.itemSeq))
			return false;
		if (scoreItem == null) {
			if (other.scoreItem != null)
				return false;
		} else if (!scoreItem.equals(other.scoreItem))
			return false;
		if (trxId == null) {
			if (other.trxId != null)
				return false;
		} else if (!trxId.equals(other.trxId))
			return false;
		return true;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String toString() {
		return "SJobScoreId [site=" + site + ", planStartDate=" + planStartDate + ", planEndDate=" + planEndDate
				+ ", trxId=" + trxId + ", totalScore=" + totalScore + ", constraintItem=" + constraintItem
				+ ", scoreItem=" + scoreItem + ", score=" + score + ", itemSeq=" + itemSeq + "]";
	}
	
}
