package com.lcm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.StringJoiner;

@Entity
@Table(name = "P_PAR_PARAMETER")
public class ParParameter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "site")
    private String site;

    @Column(name = "fab")
    private String fab;
    
    @Column(name = "area")
    private String area;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "in_value1")
    private String inValue1;

    @Column(name = "in_value2")
    private String inValue2;

    @Column(name = "in_value3")
    private String inValue3;

    @Column(name = "in_value4")
    private String inValue4;

    @Column(name = "remark")
    private String remark;

    @Column(name = "lm_user")
    private String lmUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "lm_time")
    private LocalDateTime lmTime;

    @Id
    @Column(name = "item_seq")
    private String seq;

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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getInValue1() {
        return inValue1;
    }

    public void setInValue1(String inValue1) {
        this.inValue1 = inValue1;
    }

    public String getInValue2() {
        return inValue2;
    }

    public void setInValue2(String inValue2) {
        this.inValue2 = inValue2;
    }

    public String getInValue3() {
        return inValue3;
    }

    public void setInValue3(String inValue3) {
        this.inValue3 = inValue3;
    }

    public String getInValue4() {
        return inValue4;
    }

    public void setInValue4(String inValue4) {
        this.inValue4 = inValue4;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ParParameter.class.getSimpleName() + "[", "]")
                .add("site='" + site + "'")
                .add("fab='" + fab + "'")
                .add("area='" + area +"'")
                .add("itemName='" + itemName + "'")
                .add("inValue1='" + inValue1 + "'")
                .add("inValue2='" + inValue2 + "'")
                .add("inValue3='" + inValue3 + "'")
                .add("inValue4='" + inValue4 + "'")
                .add("remark='" + remark + "'")
                .add("lmUser='" + lmUser + "'")
                .add("lmTime=" + lmTime)
                .add("seq=" + seq)
                .toString();
    }
}
