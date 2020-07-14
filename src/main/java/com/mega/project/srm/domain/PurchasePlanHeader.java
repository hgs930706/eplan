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

import com.mega.project.srm.domain.enumeration.PurchasePlanStatus;

/**
 * 采购排程信息单头表\n\n@author skyline
 */
@ApiModel(description = "采购排程信息单头表\n\n@author skyline")
@Entity
@Table(name = "purchase_plan_header")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PurchasePlanHeader implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 采购排程编码
     */
    @ApiModelProperty(value = "采购排程编码")
    @Column(name = "pur_sche_code")
    private String purScheCode;

    /**
     * 采购排程版本
     */
    @ApiModelProperty(value = "采购排程版本")
    @Column(name = "pur_sche_version")
    private String purScheVersion;

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
     * 采购员
     */
    @ApiModelProperty(value = "采购员")
    @Column(name = "buyer")
    private String buyer;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    @Column(name = "pur_class")
    private String purClass;

    /**
     * 送货次数
     */
    @ApiModelProperty(value = "送货次数")
    @Column(name = "delivery_times")
    private Integer deliveryTimes;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PurchasePlanStatus status;

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
     * 一个采购排程头信息对应多个采购排成明细
     */
    @ApiModelProperty(value = "一个采购排程头信息对应多个采购排成明细")
    @OneToMany(mappedBy = "header")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PurchasePlanDetails> details = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPurScheCode() {
        return purScheCode;
    }

    public PurchasePlanHeader purScheCode(String purScheCode) {
        this.purScheCode = purScheCode;
        return this;
    }

    public void setPurScheCode(String purScheCode) {
        this.purScheCode = purScheCode;
    }

    public String getPurScheVersion() {
        return purScheVersion;
    }

    public PurchasePlanHeader purScheVersion(String purScheVersion) {
        this.purScheVersion = purScheVersion;
        return this;
    }

    public void setPurScheVersion(String purScheVersion) {
        this.purScheVersion = purScheVersion;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public PurchasePlanHeader vendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
        return this;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public PurchasePlanHeader vendorName(String vendorName) {
        this.vendorName = vendorName;
        return this;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getBuyer() {
        return buyer;
    }

    public PurchasePlanHeader buyer(String buyer) {
        this.buyer = buyer;
        return this;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getPurClass() {
        return purClass;
    }

    public PurchasePlanHeader purClass(String purClass) {
        this.purClass = purClass;
        return this;
    }

    public void setPurClass(String purClass) {
        this.purClass = purClass;
    }

    public Integer getDeliveryTimes() {
        return deliveryTimes;
    }

    public PurchasePlanHeader deliveryTimes(Integer deliveryTimes) {
        this.deliveryTimes = deliveryTimes;
        return this;
    }

    public void setDeliveryTimes(Integer deliveryTimes) {
        this.deliveryTimes = deliveryTimes;
    }

    public PurchasePlanStatus getStatus() {
        return status;
    }

    public PurchasePlanHeader status(PurchasePlanStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(PurchasePlanStatus status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public PurchasePlanHeader createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public PurchasePlanHeader createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public PurchasePlanHeader lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public PurchasePlanHeader lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<PurchasePlanDetails> getDetails() {
        return details;
    }

    public PurchasePlanHeader details(Set<PurchasePlanDetails> purchasePlanDetails) {
        this.details = purchasePlanDetails;
        return this;
    }

    public PurchasePlanHeader addDetail(PurchasePlanDetails purchasePlanDetails) {
        this.details.add(purchasePlanDetails);
        purchasePlanDetails.setHeader(this);
        return this;
    }

    public PurchasePlanHeader removeDetail(PurchasePlanDetails purchasePlanDetails) {
        this.details.remove(purchasePlanDetails);
        purchasePlanDetails.setHeader(null);
        return this;
    }

    public void setDetails(Set<PurchasePlanDetails> purchasePlanDetails) {
        this.details = purchasePlanDetails;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PurchasePlanHeader)) {
            return false;
        }
        return id != null && id.equals(((PurchasePlanHeader) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PurchasePlanHeader{" +
            "id=" + getId() +
            ", purScheCode='" + getPurScheCode() + "'" +
            ", purScheVersion='" + getPurScheVersion() + "'" +
            ", vendorCode='" + getVendorCode() + "'" +
            ", vendorName='" + getVendorName() + "'" +
            ", buyer='" + getBuyer() + "'" +
            ", purClass='" + getPurClass() + "'" +
            ", deliveryTimes=" + getDeliveryTimes() +
            ", status='" + getStatus() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
