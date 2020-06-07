package com.lcm.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
public class FacCapaId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "site")
    private String site;

    @Column(name = "fab")
    private String fab;

    @Column(name = "area")
    private String area;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "shift_date")
    private LocalDate shiftDate;

    @Column(name = "shift")
    private String shift;

    @Column(name = "score_item")
    private String scoreItem;

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

    public LocalDate getShiftDate() {
        return shiftDate;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return area;
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

    public String getScoreItem() {
        return scoreItem;
    }

    public void setScoreItem(String scoreItem) {
        this.scoreItem = scoreItem;
    }
}
