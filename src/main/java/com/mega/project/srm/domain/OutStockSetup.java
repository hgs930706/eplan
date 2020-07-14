package com.mega.project.srm.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * 物料外部库存设置表\n\n@author skyline
 */
@ApiModel(description = "物料外部库存设置表\n\n@author skyline")
@Entity
@Table(name = "out_stock_setup")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OutStockSetup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 物料编码
     */
    @ApiModelProperty(value = "物料编码")
    @Column(name = "material_code")
    private String materialCode;

    /**
     * 仓库
     */
    @ApiModelProperty(value = "仓库")
    @Column(name = "stock_code")
    private String stockCode;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    @Column(name = "stock_qty")
    private Double stockQty;

    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    @Column(name = "setup_date")
    private Instant setupDate;

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

    public OutStockSetup materialCode(String materialCode) {
        this.materialCode = materialCode;
        return this;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getStockCode() {
        return stockCode;
    }

    public OutStockSetup stockCode(String stockCode) {
        this.stockCode = stockCode;
        return this;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public Double getStockQty() {
        return stockQty;
    }

    public OutStockSetup stockQty(Double stockQty) {
        this.stockQty = stockQty;
        return this;
    }

    public void setStockQty(Double stockQty) {
        this.stockQty = stockQty;
    }

    public Instant getSetupDate() {
        return setupDate;
    }

    public OutStockSetup setupDate(Instant setupDate) {
        this.setupDate = setupDate;
        return this;
    }

    public void setSetupDate(Instant setupDate) {
        this.setupDate = setupDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public OutStockSetup createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public OutStockSetup createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public OutStockSetup lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public OutStockSetup lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OutStockSetup)) {
            return false;
        }
        return id != null && id.equals(((OutStockSetup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OutStockSetup{" +
            "id=" + getId() +
            ", materialCode='" + getMaterialCode() + "'" +
            ", stockCode='" + getStockCode() + "'" +
            ", stockQty=" + getStockQty() +
            ", setupDate='" + getSetupDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
