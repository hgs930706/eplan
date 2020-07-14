package com.mega.project.srm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import com.mega.project.srm.domain.enumeration.PurchasePlanNumClass;

/**
 * 采购排程信息单身表\n\n@author skyline
 */
@ApiModel(description = "采购排程信息单身表\n\n@author skyline")
@Entity
@Table(name = "purchase_plan_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PurchasePlanDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 物料代码
     */
    @ApiModelProperty(value = "物料代码")
    @Column(name = "material_code")
    private String materialCode;

    /**
     * 物料名称
     */
    @ApiModelProperty(value = "物料名称")
    @Column(name = "material_name")
    private String materialName;

    /**
     * 出货地址
     */
    @ApiModelProperty(value = "出货地址")
    @Column(name = "shipto")
    private String shipto;

    /**
     * 数量类型 ,PLAN, CONFIRM, ASN, BALANCE
     */
    @ApiModelProperty(value = "数量类型 ,PLAN, CONFIRM, ASN, BALANCE")
    @Enumerated(EnumType.STRING)
    @Column(name = "num_class")
    private PurchasePlanNumClass numClass;

    /**
     * 采购日期
     */
    @ApiModelProperty(value = "采购日期")
    @Column(name = "pur_date")
    private Instant purDate;

    /**
     * 采购数量
     */
    @ApiModelProperty(value = "采购数量")
    @Column(name = "pur_qty")
    private Double purQty;

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

    @ManyToOne
    @JsonIgnoreProperties(value = "details", allowSetters = true)
    private PurchasePlanHeader header;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public PurchasePlanDetails materialCode(String materialCode) {
        this.materialCode = materialCode;
        return this;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public PurchasePlanDetails materialName(String materialName) {
        this.materialName = materialName;
        return this;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getShipto() {
        return shipto;
    }

    public PurchasePlanDetails shipto(String shipto) {
        this.shipto = shipto;
        return this;
    }

    public void setShipto(String shipto) {
        this.shipto = shipto;
    }

    public PurchasePlanNumClass getNumClass() {
        return numClass;
    }

    public PurchasePlanDetails numClass(PurchasePlanNumClass numClass) {
        this.numClass = numClass;
        return this;
    }

    public void setNumClass(PurchasePlanNumClass numClass) {
        this.numClass = numClass;
    }

    public Instant getPurDate() {
        return purDate;
    }

    public PurchasePlanDetails purDate(Instant purDate) {
        this.purDate = purDate;
        return this;
    }

    public void setPurDate(Instant purDate) {
        this.purDate = purDate;
    }

    public Double getPurQty() {
        return purQty;
    }

    public PurchasePlanDetails purQty(Double purQty) {
        this.purQty = purQty;
        return this;
    }

    public void setPurQty(Double purQty) {
        this.purQty = purQty;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public PurchasePlanDetails createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public PurchasePlanDetails createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public PurchasePlanDetails lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public PurchasePlanDetails lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public PurchasePlanHeader getHeader() {
        return header;
    }

    public PurchasePlanDetails header(PurchasePlanHeader purchasePlanHeader) {
        this.header = purchasePlanHeader;
        return this;
    }

    public void setHeader(PurchasePlanHeader purchasePlanHeader) {
        this.header = purchasePlanHeader;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PurchasePlanDetails)) {
            return false;
        }
        return id != null && id.equals(((PurchasePlanDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PurchasePlanDetails{" +
            "id=" + getId() +
            ", materialCode='" + getMaterialCode() + "'" +
            ", materialName='" + getMaterialName() + "'" +
            ", shipto='" + getShipto() + "'" +
            ", numClass='" + getNumClass() + "'" +
            ", purDate='" + getPurDate() + "'" +
            ", purQty=" + getPurQty() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
