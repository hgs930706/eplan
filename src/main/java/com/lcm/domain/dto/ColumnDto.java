package com.lcm.domain.dto;

import java.time.LocalDate;

public class ColumnDto {

    private LocalDate shiftDate;
    private String dforecastQtySum91;
    private String dforecastQtySum97;
    private String nforecastQtySum91;
    private String nforecastQtySum97;

    private String jobType;
    private String partNo;
    private String woId;
    private String seq;
    private String partNoFull;
    private String modelNo;
    private String forecastQty91;
    private String forecastQty97;
    private String changeDuration;
    private String remark;

    private String njobType;
    private String npartNo;
    private String nwoId;
    private String nseq;
    private String npartNoFull;
    private String nmodelNo;
    private String nforecastQty91;
    private String nforecastQty97;
    private String nchangeDuration;
    private String nremark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNremark() {
        return nremark;
    }

    public void setNremark(String nremark) {
        this.nremark = nremark;
    }

    public String getForecastQty91() {
        return forecastQty91;
    }

    public void setForecastQty91(String forecastQty91) {
        this.forecastQty91 = forecastQty91;
    }

    public String getForecastQty97() {
        return forecastQty97;
    }

    public void setForecastQty97(String forecastQty97) {
        this.forecastQty97 = forecastQty97;
    }

    public String getNforecastQty91() {
        return nforecastQty91;
    }

    public void setNforecastQty91(String nforecastQty91) {
        this.nforecastQty91 = nforecastQty91;
    }

    public String getNforecastQty97() {
        return nforecastQty97;
    }

    public void setNforecastQty97(String nforecastQty97) {
        this.nforecastQty97 = nforecastQty97;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }


    public String getChangeDuration() {
        return changeDuration;
    }

    public void setChangeDuration(String changeDuration) {
        this.changeDuration = changeDuration;
    }

    public String getNpartNo() {
        return npartNo;
    }

    public void setNpartNo(String npartNo) {
        this.npartNo = npartNo;
    }

    public String getNmodelNo() {
        return nmodelNo;
    }

    public void setNmodelNo(String nmodelNo) {
        this.nmodelNo = nmodelNo;
    }


    public String getNjobType() {
        return njobType;
    }

    public void setNjobType(String njobType) {
        this.njobType = njobType;
    }

    public String getNchangeDuration() {
        return nchangeDuration;
    }

    public void setNchangeDuration(String nchangeDuration) {
        this.nchangeDuration = nchangeDuration;
    }
    public LocalDate getShiftDate() {
        return shiftDate;
    }

    public void setShiftDate(LocalDate shiftDate) {
        this.shiftDate = shiftDate;
    }

    public String getDforecastQtySum91() {
        return dforecastQtySum91;
    }

    public void setDforecastQtySum91(String dforecastQtySum91) {
        this.dforecastQtySum91 = dforecastQtySum91;
    }

    public String getDforecastQtySum97() {
        return dforecastQtySum97;
    }

    public void setDforecastQtySum97(String dforecastQtySum97) {
        this.dforecastQtySum97 = dforecastQtySum97;
    }

    public String getNforecastQtySum91() {
        return nforecastQtySum91;
    }

    public void setNforecastQtySum91(String nforecastQtySum91) {
        this.nforecastQtySum91 = nforecastQtySum91;
    }

    public String getNforecastQtySum97() {
        return nforecastQtySum97;
    }

    public void setNforecastQtySum97(String nforecastQtySum97) {
        this.nforecastQtySum97 = nforecastQtySum97;
    }

    public String getPartNoFull() {
        return partNoFull;
    }

    public void setPartNoFull(String partNoFull) {
        this.partNoFull = partNoFull;
    }

    public String getNpartNoFull() {
        return npartNoFull;
    }

    public void setNpartNoFull(String npartNoFull) {
        this.npartNoFull = npartNoFull;
    }

    public String getWoId() {
        return woId;
    }

    public void setWoId(String woId) {
        this.woId = woId;
    }

    public String getNwoId() {
        return nwoId;
    }

    public void setNwoId(String nwoId) {
        this.nwoId = nwoId;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getNseq() {
        return nseq;
    }

    public void setNseq(String nseq) {
        this.nseq = nseq;
    }
}
