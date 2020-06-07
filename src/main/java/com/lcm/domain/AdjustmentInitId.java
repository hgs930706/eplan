package com.lcm.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
public class AdjustmentInitId implements Serializable {



    @Column(name = "site")
    private String site;

    @Column(name = "fab")
    private String fab;

    @Column(name="area")
    private String area;


    @Column(name = "line")
    private String line;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "shift_date")
    private LocalDate shiftDate;

    @Column(name = "shift")
    private String shift;

    @Column(name = "job_type")
    private String jobType;

    @Column(name = "part_level")
    private String partLevel;

    @Column(name = "model_no")
    private String modelNo;

    @Column(name = "wo_id")
    private String woId;

    @Column(name = "change_key")
    private String changeKey;

    @Column(name="grade")
    private String grade;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getLine() {
        return line;
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

    public void setLine(String line) {
        this.line = line;
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

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getPartLevel() {
        return partLevel;
    }

    public void setPartLevel(String partLevel) {
        this.partLevel = partLevel;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getWoId() {
        return woId;
    }

    public void setWoId(String woId) {
        this.woId = woId;
    }

    public String getChangeKey() {
        return changeKey;
    }

    public void setChangeKey(String changeKey) {
        this.changeKey = changeKey;
    }

    @Override
    public String toString() {
        return "AdjustmentInitId{" +
                "site='" + site + '\'' +
                ", fab='" + fab + '\'' +
                ", area='" + area + '\'' +
                ", line='" + line + '\'' +
                ", shiftDate=" + shiftDate +
                ", shift='" + shift + '\'' +
                ", jobType='" + jobType + '\'' +
                ", partLevel='" + partLevel + '\'' +
                ", modelNo='" + modelNo + '\'' +
                ", woId='" + woId + '\'' +
                ", changeKey='" + changeKey + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}
