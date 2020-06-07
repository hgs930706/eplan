package com.lcm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "C_MOD_MODEL")
public class ModModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ModelId modelId;

    @Column(name = "model_no")
    private String modelNo;

    @Column(name = "model_site")
    private String modelSite;

    @Column(name = "model_type")
    private String modelType;

    @Column(name = "model_ext")
    private String modelExt;

    @Column(name = "model_ver")
    private String modelVer;

    @Column(name = "panel_size")
    private String panelSize;

    @Column(name = "bar_type")
    private String barType;

    @Column(name = "panel_size_group")
    private String panelSizeGroup;

    @Column(name = "parts_group")
    private String partsGroup;

    @Column(name = "is_build_pcb")
    private String isBuildPcb;

    @Column(name = "is_demura")
    private String isDemura;

    @Column(name = "tuffy_type")
    private String tuffyType;

    @Column(name = "last_trackout_time")
    private LocalDateTime lastTrackoutTime;

    @Column(name = "color")
    private String color;

    @Column(name = "priority")
    private String priority;

    @Column(name = "lm_user")
    private String lmUser;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "lm_time")
    private LocalDateTime lmTime;

    @Column(name="CHANGE_GROUP")
    private String changeGroup;

    @Column(name="IS_OVERSIXMONTH")
    private String isOversixmonth;


    public String getIsOversixmonth() {
        return isOversixmonth;
    }

    public void setIsOversixmonth(String isOversixmonth) {
        this.isOversixmonth = isOversixmonth;
    }

    public String getChangeGroup() {
        return changeGroup;
    }

    public void setChangeGroup(String changeGroup) {
        this.changeGroup = changeGroup;
    }

    public ModelId getModelId() {
        return modelId;
    }

    public void setModelId(ModelId modelId) {
        this.modelId = modelId;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getModelSite() {
        return modelSite;
    }

    public void setModelSite(String modelSite) {
        this.modelSite = modelSite;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public String getModelExt() {
        return modelExt;
    }

    public void setModelExt(String modelExt) {
        this.modelExt = modelExt;
    }

    public String getModelVer() {
        return modelVer;
    }

    public void setModelVer(String modelVer) {
        this.modelVer = modelVer;
    }

    public String getPanelSize() {
        return panelSize;
    }

    public void setPanelSize(String panelSize) {
        this.panelSize = panelSize;
    }

    public String getBarType() {
        return barType;
    }

    public void setBarType(String barType) {
        this.barType = barType;
    }

    public String getPanelSizeGroup() {
        return panelSizeGroup;
    }

    public void setPanelSizeGroup(String panelSizeGroup) {
        this.panelSizeGroup = panelSizeGroup;
    }

    public String getPartsGroup() {
        return partsGroup;
    }

    public void setPartsGroup(String partsGroup) {
        this.partsGroup = partsGroup;
    }

    public String getIsBuildPcb() {
        return isBuildPcb;
    }

    public void setIsBuildPcb(String isBuildPcb) {
        this.isBuildPcb = isBuildPcb;
    }

    public String getIsDemura() {
        return isDemura;
    }

    public void setIsDemura(String isDemura) {
        this.isDemura = isDemura;
    }

    public String getTuffyType() {
        return tuffyType;
    }

    public void setTuffyType(String tuffyType) {
        this.tuffyType = tuffyType;
    }

    public LocalDateTime getLastTrackoutTime() {
        return lastTrackoutTime;
    }

    public void setLastTrackoutTime(LocalDateTime lastTrackoutTime) {
        this.lastTrackoutTime = lastTrackoutTime;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
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
}
