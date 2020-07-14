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

import com.mega.project.srm.domain.enumeration.PoStates;

/**
 * 采购订单单头表\n\n@author skyline
 */
@ApiModel(description = "采购订单单头表\n\n@author skyline")
@Entity
@Table(name = "po_header")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PoHeader implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 采购订单
     */
    @ApiModelProperty(value = "采购订单")
    @Column(name = "po_num")
    private String poNum;

    /**
     * 采购订单版本
     */
    @ApiModelProperty(value = "采购订单版本")
    @Column(name = "po_version")
    private String poVersion;

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
     * 厂区
     */
    @ApiModelProperty(value = "厂区")
    @Column(name = "plant")
    private String plant;

    /**
     * 收货地点
     */
    @ApiModelProperty(value = "收货地点")
    @Column(name = "ship_to")
    private String shipTo;

    /**
     * 采购订单状态
     */
    @ApiModelProperty(value = "采购订单状态")
    @Enumerated(EnumType.STRING)
    @Column(name = "po_status")
    private PoStates poStatus;

    /**
     * 采购员
     */
    @ApiModelProperty(value = "采购员")
    @Column(name = "buyer")
    private String buyer;

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
     * 一个采购订单头信息对应多个采购订单明细
     */
    @ApiModelProperty(value = "一个采购订单头信息对应多个采购订单明细")
    @OneToMany(mappedBy = "header")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PoDetails> details = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPoNum() {
        return poNum;
    }

    public PoHeader poNum(String poNum) {
        this.poNum = poNum;
        return this;
    }

    public void setPoNum(String poNum) {
        this.poNum = poNum;
    }

    public String getPoVersion() {
        return poVersion;
    }

    public PoHeader poVersion(String poVersion) {
        this.poVersion = poVersion;
        return this;
    }

    public void setPoVersion(String poVersion) {
        this.poVersion = poVersion;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public PoHeader vendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
        return this;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public PoHeader vendorName(String vendorName) {
        this.vendorName = vendorName;
        return this;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getPlant() {
        return plant;
    }

    public PoHeader plant(String plant) {
        this.plant = plant;
        return this;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getShipTo() {
        return shipTo;
    }

    public PoHeader shipTo(String shipTo) {
        this.shipTo = shipTo;
        return this;
    }

    public void setShipTo(String shipTo) {
        this.shipTo = shipTo;
    }

    public PoStates getPoStatus() {
        return poStatus;
    }

    public PoHeader poStatus(PoStates poStatus) {
        this.poStatus = poStatus;
        return this;
    }

    public void setPoStatus(PoStates poStatus) {
        this.poStatus = poStatus;
    }

    public String getBuyer() {
        return buyer;
    }

    public PoHeader buyer(String buyer) {
        this.buyer = buyer;
        return this;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public PoHeader createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public PoHeader createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public PoHeader lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public PoHeader lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<PoDetails> getDetails() {
        return details;
    }

    public PoHeader details(Set<PoDetails> poDetails) {
        this.details = poDetails;
        return this;
    }

    public PoHeader addDetail(PoDetails poDetails) {
        this.details.add(poDetails);
        poDetails.setHeader(this);
        return this;
    }

    public PoHeader removeDetail(PoDetails poDetails) {
        this.details.remove(poDetails);
        poDetails.setHeader(null);
        return this;
    }

    public void setDetails(Set<PoDetails> poDetails) {
        this.details = poDetails;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PoHeader)) {
            return false;
        }
        return id != null && id.equals(((PoHeader) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PoHeader{" +
            "id=" + getId() +
            ", poNum='" + getPoNum() + "'" +
            ", poVersion='" + getPoVersion() + "'" +
            ", vendorCode='" + getVendorCode() + "'" +
            ", vendorName='" + getVendorName() + "'" +
            ", plant='" + getPlant() + "'" +
            ", shipTo='" + getShipTo() + "'" +
            ", poStatus='" + getPoStatus() + "'" +
            ", buyer='" + getBuyer() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
