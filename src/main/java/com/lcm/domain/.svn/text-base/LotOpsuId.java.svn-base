package com.lcm.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
public class LotOpsuId implements Serializable {

    @Column(name="SITE")
    private String site;

    @Column(name="FAB")
    private String fab;

    @Column(name="AREA")
    private String area;

    @Column(name="LINE")
    private String line;

    @Column(name="SHIFT_DATE")
    private LocalDate shiftDate;

    @Column(name="MODEL_NO")
    private String modelNo;

    @Column(name="PART_NO")
    private String partNo;

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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLine() {
        return line;
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

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }
}
