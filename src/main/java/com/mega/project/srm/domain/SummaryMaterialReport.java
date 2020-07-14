package com.mega.project.srm.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * 物料总控报表基础信息\n\n@author skyline
 */
@ApiModel(description = "物料总控报表基础信息\n\n@author skyline")
@Entity
@Table(name = "summary_material_report")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SummaryMaterialReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 成品物料号
     */
    @ApiModelProperty(value = "成品物料号")
    @Column(name = "material_code")
    private String materialCode;

    /**
     * 子物料编码
     */
    @ApiModelProperty(value = "子物料编码")
    @Column(name = "component_material_code")
    private String componentMaterialCode;

    /**
     * 子物料名称
     */
    @ApiModelProperty(value = "子物料名称")
    @Column(name = "component_material_name")
    private String componentMaterialName;

    /**
     * 客户物料编码
     */
    @ApiModelProperty(value = "客户物料编码")
    @Column(name = "customer_material_code")
    private String customerMaterialCode;

    /**
     * 供应商代码
     */
    @ApiModelProperty(value = "供应商代码")
    @Column(name = "vendor_code")
    private String vendorCode;

    /**
     * 交货周期
     */
    @ApiModelProperty(value = "交货周期")
    @Column(name = "ship_cycle")
    private String shipCycle;

    /**
     * 用量
     */
    @ApiModelProperty(value = "用量")
    @Column(name = "use_qty")
    private Double useQty;

    /**
     * 单位
     */
    @ApiModelProperty(value = "单位")
    @Column(name = "unit")
    private String unit;

    /**
     * 期初库存
     */
    @ApiModelProperty(value = "期初库存")
    @Column(name = "stock_qty")
    private Double stockQty;

    /**
     * 项目类型
     */
    @ApiModelProperty(value = "项目类型")
    @Column(name = "item_type")
    private String itemType;

    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    @Column(name = "date")
    private Instant date;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    @Column(name = "qty")
    private Double qty;

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

    public SummaryMaterialReport materialCode(String materialCode) {
        this.materialCode = materialCode;
        return this;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getComponentMaterialCode() {
        return componentMaterialCode;
    }

    public SummaryMaterialReport componentMaterialCode(String componentMaterialCode) {
        this.componentMaterialCode = componentMaterialCode;
        return this;
    }

    public void setComponentMaterialCode(String componentMaterialCode) {
        this.componentMaterialCode = componentMaterialCode;
    }

    public String getComponentMaterialName() {
        return componentMaterialName;
    }

    public SummaryMaterialReport componentMaterialName(String componentMaterialName) {
        this.componentMaterialName = componentMaterialName;
        return this;
    }

    public void setComponentMaterialName(String componentMaterialName) {
        this.componentMaterialName = componentMaterialName;
    }

    public String getCustomerMaterialCode() {
        return customerMaterialCode;
    }

    public SummaryMaterialReport customerMaterialCode(String customerMaterialCode) {
        this.customerMaterialCode = customerMaterialCode;
        return this;
    }

    public void setCustomerMaterialCode(String customerMaterialCode) {
        this.customerMaterialCode = customerMaterialCode;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public SummaryMaterialReport vendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
        return this;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getShipCycle() {
        return shipCycle;
    }

    public SummaryMaterialReport shipCycle(String shipCycle) {
        this.shipCycle = shipCycle;
        return this;
    }

    public void setShipCycle(String shipCycle) {
        this.shipCycle = shipCycle;
    }

    public Double getUseQty() {
        return useQty;
    }

    public SummaryMaterialReport useQty(Double useQty) {
        this.useQty = useQty;
        return this;
    }

    public void setUseQty(Double useQty) {
        this.useQty = useQty;
    }

    public String getUnit() {
        return unit;
    }

    public SummaryMaterialReport unit(String unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getStockQty() {
        return stockQty;
    }

    public SummaryMaterialReport stockQty(Double stockQty) {
        this.stockQty = stockQty;
        return this;
    }

    public void setStockQty(Double stockQty) {
        this.stockQty = stockQty;
    }

    public String getItemType() {
        return itemType;
    }

    public SummaryMaterialReport itemType(String itemType) {
        this.itemType = itemType;
        return this;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Instant getDate() {
        return date;
    }

    public SummaryMaterialReport date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Double getQty() {
        return qty;
    }

    public SummaryMaterialReport qty(Double qty) {
        this.qty = qty;
        return this;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public SummaryMaterialReport createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public SummaryMaterialReport createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public SummaryMaterialReport lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public SummaryMaterialReport lastModifiedDate(Instant lastModifiedDate) {
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
        if (!(o instanceof SummaryMaterialReport)) {
            return false;
        }
        return id != null && id.equals(((SummaryMaterialReport) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SummaryMaterialReport{" +
            "id=" + getId() +
            ", materialCode='" + getMaterialCode() + "'" +
            ", componentMaterialCode='" + getComponentMaterialCode() + "'" +
            ", componentMaterialName='" + getComponentMaterialName() + "'" +
            ", customerMaterialCode='" + getCustomerMaterialCode() + "'" +
            ", vendorCode='" + getVendorCode() + "'" +
            ", shipCycle='" + getShipCycle() + "'" +
            ", useQty=" + getUseQty() +
            ", unit='" + getUnit() + "'" +
            ", stockQty=" + getStockQty() +
            ", itemType='" + getItemType() + "'" +
            ", date='" + getDate() + "'" +
            ", qty=" + getQty() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
