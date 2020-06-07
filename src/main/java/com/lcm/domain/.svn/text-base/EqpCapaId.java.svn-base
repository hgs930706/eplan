package com.lcm.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EqpCapaId implements Serializable {

    @Column(name = "site")
	private String site;
    @Column(name = "line")
    private String line;
    @Column(name = "part_no")
    private String partNo;

    public String getLine() {
        return line;
    }
    

    public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public void setLine(String line) {
        this.line = line;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }


	@Override
	public String toString() {
		return "EqpCapaId [site=" + site + ", line=" + line + ", partNo=" + partNo + "]";
	}
    
    
}
