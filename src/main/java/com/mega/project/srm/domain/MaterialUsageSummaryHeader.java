package com.mega.project.srm.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * 物料用料表汇总单头表\n\n@author skyline
 */
@ApiModel(description = "物料用料表汇总单头表\n\n@author skyline")
@Entity
@Table(name = "material_usage_summary_header")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MaterialUsageSummaryHeader implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 供应商代码
     */
    @ApiModelProperty(value = "供应商代码")
    @Column(name = "vendor_id")
    private String vendorId;

    /**
     * 原材料物料编码
     */
    @ApiModelProperty(value = "原材料物料编码")
    @Column(name = "material_code")
    private String materialCode;

    /**
     * 汇总用料日期
     */
    @ApiModelProperty(value = "汇总用料日期")
    @Column(name = "usage_date")
    private Instant usageDate;

    /**
     * 汇总用料数量
     */
    @ApiModelProperty(value = "汇总用料数量")
    @Column(name = "usage_qty")
    private Integer usageQty;

    /**
     * 汇总编码,逗号分割
     */
    @ApiModelProperty(value = "汇总编码,逗号分割")
    @Column(name = "sum_id")
    private String sumId;

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

    public String getVendorId() {
        return vendorId;
    }

    public MaterialUsageSummaryHeader vendorId(String vendorId) {
        this.vendorId = vendorId;
        return this;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public MaterialUsageSummaryHeader materialCode(String materialCode) {
        this.materialCode = materialCode;
        return this;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public Instant getUsageDate() {
        return usageDate;
    }

    public MaterialUsageSummaryHeader usageDate(Instant usageDate) {
        this.usageDate = usageDate;
        return this;
    }

    public void setUsageDate(Instant usageDate) {
        this.usageDate = usageDate;
    }

    public Integer getUsageQty() {
        return usageQty;
    }

    public MaterialUsageSummaryHeader usageQty(Integer usageQty) {
        this.usageQty = usageQty;
        return this;
    }

    public void setUsageQty(Integer usageQty) {
        this.usageQty = usageQty;
    }

    public String getSumId() {
        return sumId;
    }

    public MaterialUsageSummaryHeader sumId(String sumId) {
        this.sumId = sumId;
        return this;
    }

    public void setSumId(String sumId) {
        this.sumId = sumId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public MaterialUsageSummaryHeader createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public MaterialUsageSummaryHeader createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public MaterialUsageSummaryHeader lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public MaterialUsageSummaryHeader lastModifiedDate(Instant lastModifiedDate) {
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
        if (!(o instanceof MaterialUsageSummaryHeader)) {
            return false;
        }
        return id != null && id.equals(((MaterialUsageSummaryHeader) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MaterialUsageSummaryHeader{" +
            "id=" + getId() +
            ", vendorId='" + getVendorId() + "'" +
            ", materialCode='" + getMaterialCode() + "'" +
            ", usageDate='" + getUsageDate() + "'" +
            ", usageQty=" + getUsageQty() +
            ", sumId='" + getSumId() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
