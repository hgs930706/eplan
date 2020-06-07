package com.lcm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "C_FAC_LINE")
public class Line implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "site")
    private String site;

    @Column(name = "fab")
    private String fab;

    @Column(name = "area")
    private String area;

    @Column(name = "LINE_TYPE")
    private String lineType;

    @Column(name = "ALIAS_NAME")
    private String aliasName;

    @Column(name = "LINE_MODE")
    private String lineMode;

    @Id
    @Column(name = "line")
    private String line;

    @Column(name = "lm_user")
    private String lmUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "lm_time")
    private LocalDateTime lmTime;
    
    @Column(name = "active_flag")
    private String activeFlag;


    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getLineMode() {
        return lineMode;
    }

    public void setLineMode(String lineMode) {
        this.lineMode = lineMode;
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

	@Override
	public String toString() {
		return "Line [site=" + site + ", fab=" + fab + ", area=" + area + ", aliasName=" + aliasName +
                ", lineMode=" + lineMode + ",  lineType=" + lineType + ", line=" + line+ ", lmUser=" + lmUser +
                ", lmTime=" + lmTime + ", activeFlag=" + activeFlag + "]";
	}
    
    
}
