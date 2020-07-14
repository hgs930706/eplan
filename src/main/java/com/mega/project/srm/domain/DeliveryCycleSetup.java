package com.mega.project.srm.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * 物料送货周期设置\n\n@author skyline
 */
@ApiModel(description = "物料送货周期设置\n\n@author skyline")
@Entity
@Table(name = "delivery_cycle_setup")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeliveryCycleSetup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 物料类别
     */
    @ApiModelProperty(value = "物料类别")
    @Column(name = "material_class")
    private String materialClass;

    /**
     * 送货周期;一，二，三，四，五
     */
    @ApiModelProperty(value = "送货周期;一，二，三，四，五")
    @Column(name = "delivery_cycle")
    private String deliveryCycle;

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

    public String getMaterialClass() {
        return materialClass;
    }

    public DeliveryCycleSetup materialClass(String materialClass) {
        this.materialClass = materialClass;
        return this;
    }

    public void setMaterialClass(String materialClass) {
        this.materialClass = materialClass;
    }

    public String getDeliveryCycle() {
        return deliveryCycle;
    }

    public DeliveryCycleSetup deliveryCycle(String deliveryCycle) {
        this.deliveryCycle = deliveryCycle;
        return this;
    }

    public void setDeliveryCycle(String deliveryCycle) {
        this.deliveryCycle = deliveryCycle;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public DeliveryCycleSetup createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public DeliveryCycleSetup createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public DeliveryCycleSetup lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public DeliveryCycleSetup lastModifiedDate(Instant lastModifiedDate) {
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
        if (!(o instanceof DeliveryCycleSetup)) {
            return false;
        }
        return id != null && id.equals(((DeliveryCycleSetup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeliveryCycleSetup{" +
            "id=" + getId() +
            ", materialClass='" + getMaterialClass() + "'" +
            ", deliveryCycle='" + getDeliveryCycle() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
