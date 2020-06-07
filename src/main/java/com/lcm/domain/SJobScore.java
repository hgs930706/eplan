package com.lcm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="S_JOB_SCORE")
public class SJobScore implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private SJobScoreId sjobscoreId;

    @Column(name="ITEM_VALUE")
    private String itemValue;

    @Column(name="LM_USER")
    private String lmUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="LM_TIME")
    private LocalDateTime lmTime;
    

    public SJobScoreId getSjobscoreId() {
        return sjobscoreId;
    }

    public void setSjobscoreId(SJobScoreId sjobscoreId) {
        this.sjobscoreId = sjobscoreId;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
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

	@Override
	public Object clone() throws CloneNotSupportedException {
		SJobScore clone = (SJobScore) super.clone();
		clone.sjobscoreId = (SJobScoreId) this.sjobscoreId.clone();
		return clone;
	}

	@Override
	public String toString() {
		return "SJobScore [sjobscoreId=" + sjobscoreId + ", itemValue=" + itemValue + ", lmUser=" + lmUser + ", lmTime="
				+ lmTime + "]";
	}
	
	
}
