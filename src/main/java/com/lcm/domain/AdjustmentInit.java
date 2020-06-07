package com.lcm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="H_JOB_ADJUSTMENT_INIT")
public class AdjustmentInit implements Serializable {



    @EmbeddedId
    private AdjustmentInitId adjustmentInitId;

    @Column(name = "plan_qty")
    private int planQty;

    @Column(name = "lm_user")
    private String lmUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "lm_time")
    private LocalDateTime lmTime;

    @Column(name = "run_seq")
    private String runSeq;

    @Column(name = "part_no")
    private String partNo;

    @Column(name = "change_level")
    private String changeLevel;

    @Column(name = "duration")
    private String duration;

    @Column(name="REMARK")
    private String remark;

    @Column(name="PROCESS_START_TIME")
    private LocalDateTime processStartTime;

    @Column(name="PROCESS_END_TIME")
    private LocalDateTime processEndTime;

    public LocalDateTime getProcessStartTime() {
        return processStartTime;
    }

    public void setProcessStartTime(LocalDateTime processStartTime) {
        this.processStartTime = processStartTime;
    }

    public LocalDateTime getProcessEndTime() {
        return processEndTime;
    }

    public void setProcessEndTime(LocalDateTime processEndTime) {
        this.processEndTime = processEndTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public int getPlanQty() {
        return planQty;
    }

    public void setPlanQty(int planQty) {
        this.planQty = planQty;
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

    public String getRunSeq() {
        return runSeq;
    }

    public void setRunSeq(String runSeq) {
        this.runSeq = runSeq;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getChangeLevel() {
        return changeLevel;
    }

    public void setChangeLevel(String changeLevel) {
        this.changeLevel = changeLevel;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }


    public AdjustmentInitId getAdjustmentInitId() {
        return adjustmentInitId;
    }

    public void setAdjustmentInitId(AdjustmentInitId adjustmentInitId) {
        this.adjustmentInitId = adjustmentInitId;
    }


    @Override
    public String toString() {
        return "AdjustmentInit{" +
                "adjustmentInitId=" + adjustmentInitId +
                ", planQty='" + planQty + '\'' +
                ", lmUser='" + lmUser + '\'' +
                ", lmTime=" + lmTime +
                ", runSeq='" + runSeq + '\'' +
                ", partNo='" + partNo + '\'' +
                ", changeLevel='" + changeLevel + '\'' +
                ", duration='" + duration + '\'' +
                ", remark='" + remark + '\'' +
                ", processStartTime=" + processStartTime +
                ", processEndTime=" + processEndTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjustmentInit that = (AdjustmentInit) o;
        return Objects.equals(adjustmentInitId, that.adjustmentInitId) &&
                Objects.equals(planQty, that.planQty) &&
                Objects.equals(lmUser, that.lmUser) &&
                Objects.equals(lmTime, that.lmTime) &&
                Objects.equals(runSeq, that.runSeq) &&
                Objects.equals(partNo, that.partNo) &&
                Objects.equals(changeLevel, that.changeLevel) &&
                Objects.equals(duration, that.duration) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(processStartTime, that.processStartTime) &&
                Objects.equals(processEndTime, that.processEndTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjustmentInitId, planQty, lmUser, lmTime, runSeq, partNo, changeLevel, duration, remark, processStartTime, processEndTime);
    }
}
