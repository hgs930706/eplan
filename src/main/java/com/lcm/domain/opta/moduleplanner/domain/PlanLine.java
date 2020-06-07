package com.lcm.domain.opta.moduleplanner.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lcm.domain.RJobDashboard;
import com.lcm.domain.opta.moduleplanner.domain.Standstill;



//Line=>Task.Employee
public class PlanLine extends AbstractPersistable implements Standstill{
	private static Logger logger = LoggerFactory.getLogger(PlanLine.class);
	
    private Model model;
    private String site;
    private String fab;
    private String area;
    private String lineNo;
    private String actvie_flag;
    private int cost;
    private String lineType;
    Job nextJob;
    private Map<Model, Map<String, Object>> capa = new HashMap<Model, Map<String ,Object>>();
    private RJobDashboard rJobDashboard;
    private String lineMode;
    
    public PlanLine(){}
    
    
    public PlanLine(int id, String site, String fab, String area, String lineNo, String actvie_flag, Model model, Map<Model, Map<String, Object>> capa, int cost, String lineType, RJobDashboard rJobDashboard, String lineMode) {
    	super(id);
    	this.site = site;
    	this.fab = fab;
        this.area = area;
        this.lineNo = lineNo;
        this.actvie_flag = actvie_flag;
        this.model = model;
        this.capa = capa;
        this.cost = cost;
        this.lineType = lineType;
        this.rJobDashboard = rJobDashboard;
        this.lineMode = lineMode;
    }
    
	@Override
	public Model getModel() {
		return model;
	}
	
	@Override
	public PlanLine getLine() {
		return this;
	}	
    
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getLineNo() {
		return lineNo;
	}
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	public String getActvie_flag() {
		return actvie_flag;
	}
	public void setActvie_flag(String actvie_flag) {
		this.actvie_flag = actvie_flag;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getFab() {
		return fab;
	}
	public void setFab(String fab) {
		this.fab = fab;
	}
	
	public void setModel(Model model) {
		this.model = model;
	}
	
	public String getLineType() {
		return lineType;
	}
	public void setLineType(String lineType) {
		this.lineType = lineType;
	}
	
	public Map<Model, Map<String, Object>> getCapa() {
		return capa;
	}
	
	public void setCapa(Map<Model, Map<String, Object>> capa) {
		for(Entry<Model, Map<String, Object>> entry : capa.entrySet()) {
			for(Entry<String, Object> entry2 : entry.getValue().entrySet()) {
				int value = Integer.parseInt(String.valueOf(entry2.getValue()));
				entry2.setValue(value);
			}
		}
		this.capa = capa;
	}
	
	public int getCost() {
        return cost;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }
    
	@Override
	public Job getNextJob() {
		return nextJob;
	}
	
	@Override
	public void setNextJob(Job nextJob) {
		this.nextJob = nextJob;
	}
	
	public RJobDashboard getrJobDashboard() {
		return rJobDashboard;
	}

	public void setrJobDashboard(RJobDashboard rJobDashboard) {
		this.rJobDashboard = rJobDashboard;
	}

	public String getLineMode() {
		return lineMode;
	}

	public void setLineMode(String lineMode) {
		this.lineMode = lineMode;
	}


	@Override
	public String toString() {
		return "PlanLine [model=" + model + ", lineNo=" + lineNo + ", rJobDashboard=" + rJobDashboard + ", lineMode="
				+ lineMode + "]";
	}


}