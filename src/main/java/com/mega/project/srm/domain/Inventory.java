package com.mega.project.srm.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * 库存上传表\n\n@author skyline
 */
@ApiModel(description = "库存上传表\n\n@author skyline")
@Entity
@Table(name = "inventory")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Inventory implements Serializable {

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
     * 库存数量
     */
    @ApiModelProperty(value = "库存数量")
    @Column(name = "inv_qty")
    private Double invQty;

    /**
     * 库存单位
     */
    @ApiModelProperty(value = "库存单位")
    @Column(name = "inv_unit")
    private String invUnit;

    /**
     * 月份
     */
    @ApiModelProperty(value = "月份")
    @Column(name = "month")
    private String month;

    /**
     * 上传时间
     */
    @ApiModelProperty(value = "上传时间")
    @Column(name = "upload_date")
    private Instant uploadDate;

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

    public Inventory materialCode(String materialCode) {
        this.materialCode = materialCode;
        return this;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public Double getInvQty() {
        return invQty;
    }

    public Inventory invQty(Double invQty) {
        this.invQty = invQty;
        return this;
    }

    public void setInvQty(Double invQty) {
        this.invQty = invQty;
    }

    public String getInvUnit() {
        return invUnit;
    }

    public Inventory invUnit(String invUnit) {
        this.invUnit = invUnit;
        return this;
    }

    public void setInvUnit(String invUnit) {
        this.invUnit = invUnit;
    }

    public String getMonth() {
        return month;
    }

    public Inventory month(String month) {
        this.month = month;
        return this;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Instant getUploadDate() {
        return uploadDate;
    }

    public Inventory uploadDate(Instant uploadDate) {
        this.uploadDate = uploadDate;
        return this;
    }

    public void setUploadDate(Instant uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Inventory createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Inventory createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Inventory lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Inventory lastModifiedDate(Instant lastModifiedDate) {
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
        if (!(o instanceof Inventory)) {
            return false;
        }
        return id != null && id.equals(((Inventory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Inventory{" +
            "id=" + getId() +
            ", materialCode='" + getMaterialCode() + "'" +
            ", invQty=" + getInvQty() +
            ", invUnit='" + getInvUnit() + "'" +
            ", month='" + getMonth() + "'" +
            ", uploadDate='" + getUploadDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
