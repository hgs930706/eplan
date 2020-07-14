package com.mega.project.srm.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * 分期付款单头表\n\n@author skyline
 */
@ApiModel(description = "分期付款单头表\n\n@author skyline")
@Entity
@Table(name = "instalment_plan_header")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InstalmentPlanHeader implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 供应商代码
     */
    @ApiModelProperty(value = "供应商代码")
    @Column(name = "vendor_code")
    private String vendorCode;

    /**
     * 供应商名称
     */
    @ApiModelProperty(value = "供应商名称")
    @Column(name = "vendor_name")
    private String vendorName;

    /**
     * 采购订单号
     */
    @ApiModelProperty(value = "采购订单号")
    @Column(name = "po_num")
    private String poNum;

    /**
     * 采购人员
     */
    @ApiModelProperty(value = "采购人员")
    @Column(name = "buyer")
    private String buyer;

    /**
     * 状态:\nPAY_STAGES 分期付款中\nPAY_DONE 付款完成\nPAY_DELA 付款延期
     */
    @ApiModelProperty(value = "状态:\nPAY_STAGES 分期付款中\nPAY_DONE 付款完成\nPAY_DELA 付款延期")
    @Column(name = "state")
    private String state;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    @Column(name = "created_by")
    private String createdBy;

    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期")
    @Column(name = "created_date")
    private Instant createdDate;

    /**
     * 修改者
     */
    @ApiModelProperty(value = "修改者")
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    /**
     * 修改日期
     */
    @ApiModelProperty(value = "修改日期")
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    /**
     * 一个分期付款单头对应多个分期付款明细
     */
    @ApiModelProperty(value = "一个分期付款单头对应多个分期付款明细")
    @OneToMany(mappedBy = "header")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<InstalmentPlanDetails> details = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public InstalmentPlanHeader vendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
        return this;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public InstalmentPlanHeader vendorName(String vendorName) {
        this.vendorName = vendorName;
        return this;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getPoNum() {
        return poNum;
    }

    public InstalmentPlanHeader poNum(String poNum) {
        this.poNum = poNum;
        return this;
    }

    public void setPoNum(String poNum) {
        this.poNum = poNum;
    }

    public String getBuyer() {
        return buyer;
    }

    public InstalmentPlanHeader buyer(String buyer) {
        this.buyer = buyer;
        return this;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getState() {
        return state;
    }

    public InstalmentPlanHeader state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public InstalmentPlanHeader createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public InstalmentPlanHeader createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public InstalmentPlanHeader lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public InstalmentPlanHeader lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<InstalmentPlanDetails> getDetails() {
        return details;
    }

    public InstalmentPlanHeader details(Set<InstalmentPlanDetails> instalmentPlanDetails) {
        this.details = instalmentPlanDetails;
        return this;
    }

    public InstalmentPlanHeader addDetail(InstalmentPlanDetails instalmentPlanDetails) {
        this.details.add(instalmentPlanDetails);
        instalmentPlanDetails.setHeader(this);
        return this;
    }

    public InstalmentPlanHeader removeDetail(InstalmentPlanDetails instalmentPlanDetails) {
        this.details.remove(instalmentPlanDetails);
        instalmentPlanDetails.setHeader(null);
        return this;
    }

    public void setDetails(Set<InstalmentPlanDetails> instalmentPlanDetails) {
        this.details = instalmentPlanDetails;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InstalmentPlanHeader)) {
            return false;
        }
        return id != null && id.equals(((InstalmentPlanHeader) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InstalmentPlanHeader{" +
            "id=" + getId() +
            ", vendorCode='" + getVendorCode() + "'" +
            ", vendorName='" + getVendorName() + "'" +
            ", poNum='" + getPoNum() + "'" +
            ", buyer='" + getBuyer() + "'" +
            ", state='" + getState() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
