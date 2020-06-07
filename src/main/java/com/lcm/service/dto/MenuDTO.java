package com.lcm.service.dto;

import java.util.ArrayList;
import java.util.List;

public class MenuDTO {

    private String site;
    private String functionId;
    private String functionName;
    private String functionPath;
    private Integer displaySeq;
    private List<MenuDTO> childMenu = new ArrayList<>();

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionPath() {
        return functionPath;
    }

    public void setFunctionPath(String functionPath) {
        this.functionPath = functionPath;
    }

    public Integer getDisplaySeq() {
        return displaySeq;
    }

    public void setDisplaySeq(Integer displaySeq) {
        this.displaySeq = displaySeq;
    }

    public List<MenuDTO> getChildMenu() {
        return childMenu;
    }

    public void setChildMenu(List<MenuDTO> childMenu) {
        this.childMenu = childMenu;
    }
}
