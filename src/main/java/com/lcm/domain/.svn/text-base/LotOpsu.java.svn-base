package com.lcm.domain;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name="P_LOT_OPSU")
@Entity
public class LotOpsu {

    @EmbeddedId
    private LotOpsuId lotOpsuId;

    @Column(name="ACTUAL_QTY")
    private int actualQty;

    @Column(name="LM_TIME")
    private LocalDateTime lmTime;

    @Column(name="LM_USER")
    private String lmUser;


    public LotOpsuId getLotOpsuId() {
        return lotOpsuId;
    }

    public void setLotOpsuId(LotOpsuId lotOpsuId) {
        this.lotOpsuId = lotOpsuId;
    }

    public int getActualQty() {
        return actualQty;
    }

    public void setActualQty(int actualQty) {
        this.actualQty = actualQty;
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
        return "LotOpsu{" +
                "lotOpsuId=" + lotOpsuId +
                ", actualQty=" + actualQty +
                ", lmTime=" + lmTime +
                ", lmUser='" + lmUser + '\'' +
                '}';
    }
}
