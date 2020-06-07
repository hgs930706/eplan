package com.lcm.service.dto;

import java.util.Objects;

public class ReportDTO {
    private String site;
    private String fab;
    private String area;
    private String line;
    private String modelNo;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportDTO reportDTO = (ReportDTO) o;
        return Objects.equals(site, reportDTO.site) &&
                Objects.equals(fab, reportDTO.fab) &&
                Objects.equals(area, reportDTO.area) &&
                Objects.equals(line, reportDTO.line) &&
                Objects.equals(modelNo, reportDTO.modelNo) &&
                Objects.equals(partNo, reportDTO.partNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(site, fab, area, line, modelNo, partNo);
    }
}
