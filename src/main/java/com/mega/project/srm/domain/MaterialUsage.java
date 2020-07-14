package com.mega.project.srm.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * 物料用料表\n\n@author skyline
 */
@ApiModel(description = "物料用料表\n\n@author skyline")
@Entity
@Table(name = "material_usage")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MaterialUsage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 成品物料编码
     */
    @ApiModelProperty(value = "成品物料编码")
    @Column(name = "products_code")
    private String productsCode;

    /**
     * 原材料编码
     */
    @ApiModelProperty(value = "原材料编码")
    @Column(name = "material_code")
    private String materialCode;

    /**
     * 替代原材料编码
     */
    @ApiModelProperty(value = "替代原材料编码")
    @Column(name = "replace_material_code")
    private String replaceMaterialCode;

    /**
     * BOM版本
     */
    @ApiModelProperty(value = "BOM版本")
    @Column(name = "bom_version")
    private String bomVersion;

    /**
     * BOM编码
     */
    @ApiModelProperty(value = "BOM编码")
    @Column(name = "bom_num")
    private String bomNum;

    /**
     * 用料日期
     */
    @ApiModelProperty(value = "用料日期")
    @Column(name = "usage_date")
    private Instant usageDate;

    /**
     * 原材料用料数量
     */
    @ApiModelProperty(value = "原材料用料数量")
    @Column(name = "usage_qty")
    private Double usageQty;

    /**
     * 替代料用料数量
     */
    @ApiModelProperty(value = "替代料用料数量")
    @Column(name = "replace_usage_qty")
    private Double replaceUsageQty;

    /**
     * 实际用料损耗比例
     */
    @ApiModelProperty(value = "实际用料损耗比例")
    @Column(name = "scale")
    private Double scale;

    /**
     * 内部编码
     */
    @ApiModelProperty(value = "内部编码")
    @Column(name = "inter_id")
    private String interId;

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

    public String getProductsCode() {
        return productsCode;
    }

    public MaterialUsage productsCode(String productsCode) {
        this.productsCode = productsCode;
        return this;
    }

    public void setProductsCode(String productsCode) {
        this.productsCode = productsCode;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public MaterialUsage materialCode(String materialCode) {
        this.materialCode = materialCode;
        return this;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getReplaceMaterialCode() {
        return replaceMaterialCode;
    }

    public MaterialUsage replaceMaterialCode(String replaceMaterialCode) {
        this.replaceMaterialCode = replaceMaterialCode;
        return this;
    }

    public void setReplaceMaterialCode(String replaceMaterialCode) {
        this.replaceMaterialCode = replaceMaterialCode;
    }

    public String getBomVersion() {
        return bomVersion;
    }

    public MaterialUsage bomVersion(String bomVersion) {
        this.bomVersion = bomVersion;
        return this;
    }

    public void setBomVersion(String bomVersion) {
        this.bomVersion = bomVersion;
    }

    public String getBomNum() {
        return bomNum;
    }

    public MaterialUsage bomNum(String bomNum) {
        this.bomNum = bomNum;
        return this;
    }

    public void setBomNum(String bomNum) {
        this.bomNum = bomNum;
    }

    public Instant getUsageDate() {
        return usageDate;
    }

    public MaterialUsage usageDate(Instant usageDate) {
        this.usageDate = usageDate;
        return this;
    }

    public void setUsageDate(Instant usageDate) {
        this.usageDate = usageDate;
    }

    public Double getUsageQty() {
        return usageQty;
    }

    public MaterialUsage usageQty(Double usageQty) {
        this.usageQty = usageQty;
        return this;
    }

    public void setUsageQty(Double usageQty) {
        this.usageQty = usageQty;
    }

    public Double getReplaceUsageQty() {
        return replaceUsageQty;
    }

    public MaterialUsage replaceUsageQty(Double replaceUsageQty) {
        this.replaceUsageQty = replaceUsageQty;
        return this;
    }

    public void setReplaceUsageQty(Double replaceUsageQty) {
        this.replaceUsageQty = replaceUsageQty;
    }

    public Double getScale() {
        return scale;
    }

    public MaterialUsage scale(Double scale) {
        this.scale = scale;
        return this;
    }

    public void setScale(Double scale) {
        this.scale = scale;
    }

    public String getInterId() {
        return interId;
    }

    public MaterialUsage interId(String interId) {
        this.interId = interId;
        return this;
    }

    public void setInterId(String interId) {
        this.interId = interId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public MaterialUsage createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public MaterialUsage createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public MaterialUsage lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public MaterialUsage lastModifiedDate(Instant lastModifiedDate) {
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
        if (!(o instanceof MaterialUsage)) {
            return false;
        }
        return id != null && id.equals(((MaterialUsage) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MaterialUsage{" +
            "id=" + getId() +
            ", productsCode='" + getProductsCode() + "'" +
            ", materialCode='" + getMaterialCode() + "'" +
            ", replaceMaterialCode='" + getReplaceMaterialCode() + "'" +
            ", bomVersion='" + getBomVersion() + "'" +
            ", bomNum='" + getBomNum() + "'" +
            ", usageDate='" + getUsageDate() + "'" +
            ", usageQty=" + getUsageQty() +
            ", replaceUsageQty=" + getReplaceUsageQty() +
            ", scale=" + getScale() +
            ", interId='" + getInterId() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
