package com.lcm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "C_SYS_MENU")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "site")
    private String site;

    @Id
    @Column(name = "function_id")
    private String functionId;

    @Column(name = "function_name")
    private String functionName;

    @Column(name = "function_path")
    private String functionPath;

    @Column(name = "display_seq")
    private Integer displaySeq;

    @Column(name = "lm_user")
    private String lmUser;

    @Column(name = "lm_time")
    private LocalDateTime lmTime;

    @Column(name = "active_flag")
    private String activeFlag;

    @Column(name = "LANGUAGE_TYPE")
    private String language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "parent_id")
    private Menu parentMenu;

    @OneToMany(mappedBy = "parentMenu")
    private List<Menu> childMenu = new ArrayList<>();

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

    public String getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(String activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Menu getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    public List<Menu> getChildMenu() {
        return childMenu;
    }

    public void setChildMenu(List<Menu> childMenu) {
        this.childMenu = childMenu;
    }
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
