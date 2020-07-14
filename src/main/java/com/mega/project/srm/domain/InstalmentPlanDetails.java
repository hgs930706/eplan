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
import java.util.HashSet;
import java.util.Set;

/**
 * 分期付款单身表\n\n@author skyline
 */
@ApiModel(description = "分期付款单身表\n\n@author skyline")
@Entity
@Table(name = "instalment_plan_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InstalmentPlanDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 付款方式
     */
    @ApiModelProperty(value = "付款方式")
    @Column(name = "payment_term")
    private String paymentTerm;

    /**
     * 预计付款日期
     */
    @ApiModelProperty(value = "预计付款日期")
    @Column(name = "payment_date")
    private Instant paymentDate;

    /**
     * 付款比例
     */
    @ApiModelProperty(value = "付款比例")
    @Column(name = "payment_scale")
    private Double paymentScale;

    /**
     * 预计付款金额,6位小数
     */
    @ApiModelProperty(value = "预计付款金额,6位小数")
    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    /**
     * BPM流水号
     */
    @ApiModelProperty(value = "BPM流水号")
    @Column(name = "bpm_num")
    private String bpmNum;

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

    @OneToMany(mappedBy = "detail")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<InstalmentPlanDetailActual> actuals = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "details", allowSetters = true)
    private InstalmentPlanHeader header;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentTerm() {
        return paymentTerm;
    }

    public InstalmentPlanDetails paymentTerm(String paymentTerm) {
        this.paymentTerm = paymentTerm;
        return this;
    }

    public void setPaymentTerm(String paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public Instant getPaymentDate() {
        return paymentDate;
    }

    public InstalmentPlanDetails paymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public void setPaymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getPaymentScale() {
        return paymentScale;
    }

    public InstalmentPlanDetails paymentScale(Double paymentScale) {
        this.paymentScale = paymentScale;
        return this;
    }

    public void setPaymentScale(Double paymentScale) {
        this.paymentScale = paymentScale;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public InstalmentPlanDetails amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBpmNum() {
        return bpmNum;
    }

    public InstalmentPlanDetails bpmNum(String bpmNum) {
        this.bpmNum = bpmNum;
        return this;
    }

    public void setBpmNum(String bpmNum) {
        this.bpmNum = bpmNum;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public InstalmentPlanDetails createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public InstalmentPlanDetails createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public InstalmentPlanDetails lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public InstalmentPlanDetails lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<InstalmentPlanDetailActual> getActuals() {
        return actuals;
    }

    public InstalmentPlanDetails actuals(Set<InstalmentPlanDetailActual> instalmentPlanDetailActuals) {
        this.actuals = instalmentPlanDetailActuals;
        return this;
    }

    public InstalmentPlanDetails addActual(InstalmentPlanDetailActual instalmentPlanDetailActual) {
        this.actuals.add(instalmentPlanDetailActual);
        instalmentPlanDetailActual.setDetail(this);
        return this;
    }

    public InstalmentPlanDetails removeActual(InstalmentPlanDetailActual instalmentPlanDetailActual) {
        this.actuals.remove(instalmentPlanDetailActual);
        instalmentPlanDetailActual.setDetail(null);
        return this;
    }

    public void setActuals(Set<InstalmentPlanDetailActual> instalmentPlanDetailActuals) {
        this.actuals = instalmentPlanDetailActuals;
    }

    public InstalmentPlanHeader getHeader() {
        return header;
    }

    public InstalmentPlanDetails header(InstalmentPlanHeader instalmentPlanHeader) {
        this.header = instalmentPlanHeader;
        return this;
    }

    public void setHeader(InstalmentPlanHeader instalmentPlanHeader) {
        this.header = instalmentPlanHeader;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InstalmentPlanDetails)) {
            return false;
        }
        return id != null && id.equals(((InstalmentPlanDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InstalmentPlanDetails{" +
            "id=" + getId() +
            ", paymentTerm='" + getPaymentTerm() + "'" +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", paymentScale=" + getPaymentScale() +
            ", amount=" + getAmount() +
            ", bpmNum='" + getBpmNum() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
