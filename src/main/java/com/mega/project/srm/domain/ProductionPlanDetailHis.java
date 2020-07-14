package com.mega.project.srm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * 生产排程单身表(历史记录)\n\n@author skyline
 */
@ApiModel(description = "生产排程单身表(历史记录)\n\n@author skyline")
@Entity
@Table(name = "production_plan_detail_his")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductionPlanDetailHis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 成品物料String机种
     */
    @ApiModelProperty(value = "成品物料String机种")
    @Column(name = "products_code")
    private String productsCode;

    /**
     * 半成品物料String组件
     */
    @ApiModelProperty(value = "半成品物料String组件")
    @Column(name = "semi_products_code")
    private String semiProductsCode;

    /**
     * 半成品数量
     */
    @ApiModelProperty(value = "半成品数量")
    @Column(name = "semi_pro_qty")
    private Integer semiProQty;

    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    @Column(name = "semi_pro_date")
    private Instant semiProDate;

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
    private ProductionPlanHeaderHis header;

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

    public ProductionPlanDetailHis productsCode(String productsCode) {
        this.productsCode = productsCode;
        return this;
    }

    public void setProductsCode(String productsCode) {
        this.productsCode = productsCode;
    }

    public String getSemiProductsCode() {
        return semiProductsCode;
    }

    public ProductionPlanDetailHis semiProductsCode(String semiProductsCode) {
        this.semiProductsCode = semiProductsCode;
        return this;
    }

    public void setSemiProductsCode(String semiProductsCode) {
        this.semiProductsCode = semiProductsCode;
    }

    public Integer getSemiProQty() {
        return semiProQty;
    }

    public ProductionPlanDetailHis semiProQty(Integer semiProQty) {
        this.semiProQty = semiProQty;
        return this;
    }

    public void setSemiProQty(Integer semiProQty) {
        this.semiProQty = semiProQty;
    }

    public Instant getSemiProDate() {
        return semiProDate;
    }

    public ProductionPlanDetailHis semiProDate(Instant semiProDate) {
        this.semiProDate = semiProDate;
        return this;
    }

    public void setSemiProDate(Instant semiProDate) {
        this.semiProDate = semiProDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ProductionPlanDetailHis createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public ProductionPlanDetailHis createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public ProductionPlanDetailHis lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public ProductionPlanDetailHis lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public ProductionPlanHeaderHis getHeader() {
        return header;
    }

    public ProductionPlanDetailHis header(ProductionPlanHeaderHis productionPlanHeaderHis) {
        this.header = productionPlanHeaderHis;
        return this;
    }

    public void setHeader(ProductionPlanHeaderHis productionPlanHeaderHis) {
        this.header = productionPlanHeaderHis;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductionPlanDetailHis)) {
            return false;
        }
        return id != null && id.equals(((ProductionPlanDetailHis) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductionPlanDetailHis{" +
            "id=" + getId() +
            ", productsCode='" + getProductsCode() + "'" +
            ", semiProductsCode='" + getSemiProductsCode() + "'" +
            ", semiProQty=" + getSemiProQty() +
            ", semiProDate='" + getSemiProDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
