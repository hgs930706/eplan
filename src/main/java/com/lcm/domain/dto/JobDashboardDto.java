package com.lcm.domain.dto;

/**
 * 合并白晚班数据
 */
public class JobDashboardDto {

    private String fab;

    private String line;

    private String partNo;

    private String woId;

    private String seq;

    private String modelNo;

    private int forecastQty;

    private String changeDuration;

    private String jobType;

    private String remark;

    private String npartNo;

    private String nwoId;

    private String nseq;

    private String nmodelNo;

    private int nforecastQty;

    private String nchangeDuration;

    private String njobType;

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

    public int getForecastQty() {
        return forecastQty;
    }

    public void setForecastQty(int forecastQty) {
        this.forecastQty = forecastQty;
    }

    public int getNforecastQty() {
        return nforecastQty;
    }

    public void setNforecastQty(int nforecastQty) {
        this.nforecastQty = nforecastQty;
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

    public String getFab() {
        return fab;
    }

    public void setFab(String fab) {
        this.fab = fab;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
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
