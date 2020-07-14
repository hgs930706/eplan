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
 * 分期付款单身表，实际付款\n\n@author skyline
 */
@ApiModel(description = "分期付款单身表，实际付款\n\n@author skyline")
@Entity
@Table(name = "instalment_plan_detail_actual")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InstalmentPlanDetailActual implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 实际付款时间
     */
    @ApiModelProperty(value = "实际付款时间")
    @Column(name = "actual_date")
    private Instant actualDate;

    /**
     * 实际付款金额
     */
    @ApiModelProperty(value = "实际付款金额")
    @Column(name = "actual_amount", precision = 21, scale = 2)
    private BigDecimal actualAmount;

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
    @JsonIgnoreProperties(value = "actuals", allowSetters = true)
    private InstalmentPlanDetails detail;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getActualDate() {
        return actualDate;
    }

    public InstalmentPlanDetailActual actualDate(Instant actualDate) {
        this.actualDate = actualDate;
        return this;
    }

    public void setActualDate(Instant actualDate) {
        this.actualDate = actualDate;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public InstalmentPlanDetailActual actualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
        return this;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public InstalmentPlanDetailActual createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public InstalmentPlanDetailActual createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public InstalmentPlanDetailActual lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public InstalmentPlanDetailActual lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public InstalmentPlanDetails getDetail() {
        return detail;
    }

    public InstalmentPlanDetailActual detail(InstalmentPlanDetails instalmentPlanDetails) {
        this.detail = instalmentPlanDetails;
        return this;
    }

    public void setDetail(InstalmentPlanDetails instalmentPlanDetails) {
        this.detail = instalmentPlanDetails;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InstalmentPlanDetailActual)) {
            return false;
        }
        return id != null && id.equals(((InstalmentPlanDetailActual) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InstalmentPlanDetailActual{" +
            "id=" + getId() +
            ", actualDate='" + getActualDate() + "'" +
            ", actualAmount=" + getActualAmount() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
