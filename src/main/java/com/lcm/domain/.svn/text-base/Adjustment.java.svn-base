package com.lcm.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lcm.domain.opta.moduleplanner.domain.Shift;

@Entity
@Table(name = "H_JOB_ADJUSTMENT")
public class Adjustment implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private AdjustmentId adjustmentId;

    @Column(name = "plan_qty")
    private String planQty;

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

    @Transient
    private List<Shift> shiftList;
    
    @Transient
    private int shiftIndex;
    
    public AdjustmentId getAdjustmentId() {
        return adjustmentId;
    }

    public void setAdjustmentId(AdjustmentId adjustmentId) {
        this.adjustmentId = adjustmentId;
    }

    public String getPlanQty() {
        return planQty;
    }

    public void setPlanQty(String planQty) {
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


	public List<Shift> getShiftList() {
		return shiftList;
	}

	public void setShiftList(List<Shift> shiftList) {
		this.shiftList = shiftList;
		//設定班別 JoshLai@20190415
		for(Shift shift : shiftList) {
			LocalDate shiftDate = shift.getShiftDate().getDate();
			String shiftType = shift.getShiftType().getCode();
			if(this.adjustmentId.getShiftDate().equals(shiftDate) && this.adjustmentId.getShift().equals(shiftType)) {
				shiftIndex = shift.getIndex();
			}
		}
	}

	public int getShiftIndex() {
		setShiftList(this.shiftList);
		return shiftIndex;
	}

	public void setShiftIndex(int shiftIndex) {
		this.shiftIndex = shiftIndex;
	}

    @Override
    public String toString() {
        return "Adjustment{" +
                "adjustmentId=" + adjustmentId +
                ", planQty='" + planQty + '\'' +
                ", lmUser='" + lmUser + '\'' +
                ", lmTime=" + lmTime +
                ", runSeq='" + runSeq + '\'' +
                ", partNo='" + partNo + '\'' +
                ", changeLevel='" + changeLevel + '\'' +
                ", duration='" + duration + '\'' +
                ", remark='" + remark + '\'' +
                ", shiftIndex=" + shiftIndex +
                ", processStartTime=" + processStartTime +
                ", processEndTime=" + processEndTime +
                '}';
    }
}
