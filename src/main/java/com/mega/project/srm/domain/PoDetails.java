package com.mega.project.srm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * 采购订单单身表\n\n@author skyline
 */
@ApiModel(description = "采购订单单身表\n\n@author skyline")
@Entity
@Table(name = "po_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PoDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 订单行项目
     */
    @ApiModelProperty(value = "订单行项目")
    @Column(name = "po_detail_num")
    private Integer poDetailNum;

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
     * 数量
     */
    @ApiModelProperty(value = "数量")
    @Column(name = "material_qty")
    private Integer materialQty;

    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    /**
     * 税率
     */
    @ApiModelProperty(value = "税率")
    @Column(name = "tax")
    private Double tax;

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
    private PoHeader header;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPoDetailNum() {
        return poDetailNum;
    }

    public PoDetails poDetailNum(Integer poDetailNum) {
        this.poDetailNum = poDetailNum;
        return this;
    }

    public void setPoDetailNum(Integer poDetailNum) {
        this.poDetailNum = poDetailNum;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public PoDetails materialCode(String materialCode) {
        this.materialCode = materialCode;
        return this;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public PoDetails materialName(String materialName) {
        this.materialName = materialName;
        return this;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Integer getMaterialQty() {
        return materialQty;
    }

    public PoDetails materialQty(Integer materialQty) {
        this.materialQty = materialQty;
        return this;
    }

    public void setMaterialQty(Integer materialQty) {
        this.materialQty = materialQty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public PoDetails price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Double getTax() {
        return tax;
    }

    public PoDetails tax(Double tax) {
        this.tax = tax;
        return this;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public PoDetails createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public PoDetails createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public PoDetails lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public PoDetails lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public PoHeader getHeader() {
        return header;
    }

    public PoDetails header(PoHeader poHeader) {
        this.header = poHeader;
        return this;
    }

    public void setHeader(PoHeader poHeader) {
        this.header = poHeader;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PoDetails)) {
            return false;
        }
        return id != null && id.equals(((PoDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PoDetails{" +
            "id=" + getId() +
            ", poDetailNum=" + getPoDetailNum() +
            ", materialCode='" + getMaterialCode() + "'" +
            ", materialName='" + getMaterialName() + "'" +
            ", materialQty=" + getMaterialQty() +
            ", price=" + getPrice() +
            ", tax=" + getTax() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
