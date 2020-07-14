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

/**
 * 生产排程单头表(历史记录)\n\n@author skyline
 */
@ApiModel(description = "生产排程单头表(历史记录)\n\n@author skyline")
@Entity
@Table(name = "production_plan_header_his")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductionPlanHeaderHis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 生产排程编码
     */
    @ApiModelProperty(value = "生产排程编码")
    @Column(name = "pp_code")
    private String ppCode;

    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    @Column(name = "pp_version")
    private String ppVersion;

    /**
     * 客户代码
     */
    @ApiModelProperty(value = "客户代码")
    @Column(name = "pp_cust_code")
    private String ppCustCode;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Column(name = "pp_cust_name")
    private String ppCustName;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @Column(name = "pp_state")
    private String ppState;

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
     * 一个历史生产排程头信息对应多个历史生产排成明细
     */
    @ApiModelProperty(value = "一个历史生产排程头信息对应多个历史生产排成明细")
    @OneToMany(mappedBy = "header")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ProductionPlanDetailHis> details = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPpCode() {
        return ppCode;
    }

    public ProductionPlanHeaderHis ppCode(String ppCode) {
        this.ppCode = ppCode;
        return this;
    }

    public void setPpCode(String ppCode) {
        this.ppCode = ppCode;
    }

    public String getPpVersion() {
        return ppVersion;
    }

    public ProductionPlanHeaderHis ppVersion(String ppVersion) {
        this.ppVersion = ppVersion;
        return this;
    }

    public void setPpVersion(String ppVersion) {
        this.ppVersion = ppVersion;
    }

    public String getPpCustCode() {
        return ppCustCode;
    }

    public ProductionPlanHeaderHis ppCustCode(String ppCustCode) {
        this.ppCustCode = ppCustCode;
        return this;
    }

    public void setPpCustCode(String ppCustCode) {
        this.ppCustCode = ppCustCode;
    }

    public String getPpCustName() {
        return ppCustName;
    }

    public ProductionPlanHeaderHis ppCustName(String ppCustName) {
        this.ppCustName = ppCustName;
        return this;
    }

    public void setPpCustName(String ppCustName) {
        this.ppCustName = ppCustName;
    }

    public String getPpState() {
        return ppState;
    }

    public ProductionPlanHeaderHis ppState(String ppState) {
        this.ppState = ppState;
        return this;
    }

    public void setPpState(String ppState) {
        this.ppState = ppState;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ProductionPlanHeaderHis createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public ProductionPlanHeaderHis createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public ProductionPlanHeaderHis lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public ProductionPlanHeaderHis lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<ProductionPlanDetailHis> getDetails() {
        return details;
    }

    public ProductionPlanHeaderHis details(Set<ProductionPlanDetailHis> productionPlanDetailHis) {
        this.details = productionPlanDetailHis;
        return this;
    }

    public ProductionPlanHeaderHis addDetail(ProductionPlanDetailHis productionPlanDetailHis) {
        this.details.add(productionPlanDetailHis);
        productionPlanDetailHis.setHeader(this);
        return this;
    }

    public ProductionPlanHeaderHis removeDetail(ProductionPlanDetailHis productionPlanDetailHis) {
        this.details.remove(productionPlanDetailHis);
        productionPlanDetailHis.setHeader(null);
        return this;
    }

    public void setDetails(Set<ProductionPlanDetailHis> productionPlanDetailHis) {
        this.details = productionPlanDetailHis;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductionPlanHeaderHis)) {
            return false;
        }
        return id != null && id.equals(((ProductionPlanHeaderHis) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductionPlanHeaderHis{" +
            "id=" + getId() +
            ", ppCode='" + getPpCode() + "'" +
            ", ppVersion='" + getPpVersion() + "'" +
            ", ppCustCode='" + getPpCustCode() + "'" +
            ", ppCustName='" + getPpCustName() + "'" +
            ", ppState='" + getPpState() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
