package com.lcm.domain.opta.moduleplanner.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lcm.domain.ModChange;
import com.lcm.domain.opta.moduleplanner.utility.ModulePlannerUtility;



public class Model {
	private final Logger logger = LoggerFactory.getLogger(Model.class);
	
    private String site;
    private String partNo;
    private String modelNo;
    private String modelSite;
    private String modelType;
    private String modelExt;
    private String modelVer;
    private String panelSize;
    private String barType;//L/S
    private String panel_size_group;//MIN/NB
    private String parts_group;//治具组
    private String is_build_pcb;//是否带PCB
    private String is_demura;//是否Demura (Y/N)
    private String tuffy_type;//超粘&非超粘 (Y/N)
    private LocalDateTime last_trackout_time;//最後生產時間
    private String color;
    private String priority;
    private String isOver6MonthNoProduce;
    private String changeLvl;
    private float changeTime;
    private List<ModChange> cModChangeList;
    private String changeGroup;
    
    public Model(String site,String partNo,String modelNo,String model_site,String modelType,String modelExt,String modelVer,String panelSize,String barType, String panel_size_group, String parts_group, String is_build_pcb, String is_demura, String tuffy_type, LocalDateTime last_trackout_time, String color,String priority, String isOver6MonthNoProduce, List<ModChange> cModChangeList, String changeGroup){
    	this.site = site;
    	this.partNo = partNo;
    	this.modelSite = model_site;
    	this.modelNo = modelNo;
    	this.modelType = modelType;
    	this.modelExt = modelExt;
    	this.modelVer = modelVer;
    	this.panelSize = panelSize;
    	this.barType = barType;
    	this.panel_size_group = panel_size_group;
    	this.parts_group = parts_group;
    	this.is_build_pcb = is_build_pcb;
    	this.is_demura = is_demura;
    	this.tuffy_type = tuffy_type;
    	this.last_trackout_time = last_trackout_time;
    	this.color = color;
    	this.priority = priority;
    	this.isOver6MonthNoProduce = isOver6MonthNoProduce;
    	this.cModChangeList = cModChangeList;
    	this.changeGroup = changeGroup;
    }
    

	public String getSite() {
		return site;
	}


	public void setSite(String site) {
		this.site = site;
	}


	public String getPartNo() {
		return partNo;
	}


	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}


	public String getModelNo() {
		return modelNo;
	}


	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}


	public String getModelSite() {
		return modelSite;
	}


	public void setModelSite(String modelSite) {
		this.modelSite = modelSite;
	}


	public Logger getLogger() {
		return logger;
	}


	public String getModelType() {
		return modelType;
	}


	public void setModelType(String modelType) {
		this.modelType = modelType;
	}


	public String getModelExt() {
		return modelExt;
	}


	public void setModelExt(String modelExt) {
		this.modelExt = modelExt;
	}


	public String getModelVer() {
		return modelVer;
	}


	public void setModelVer(String modelVer) {
		this.modelVer = modelVer;
	}


	public String getPanelSize() {
		return panelSize;
	}


	public void setPanelSize(String panelSize) {
		this.panelSize = panelSize;
	}


	public String getBarType() {
		return barType;
	}


	public void setBarType(String barType) {
		this.barType = barType;
	}


	public String getPanel_size_group() {
		return panel_size_group;
	}


	public void setPanel_size_group(String panel_size_group) {
		this.panel_size_group = panel_size_group;
	}


	public String getParts_group() {
		return parts_group;
	}


	public void setParts_group(String parts_group) {
		this.parts_group = parts_group;
	}


	public String getIs_build_pcb() {
		return is_build_pcb;
	}


	public void setIs_build_pcb(String is_build_pcb) {
		this.is_build_pcb = is_build_pcb;
	}


	public String getIs_demura() {
		return is_demura;
	}


	public void setIs_demura(String is_demura) {
		this.is_demura = is_demura;
	}


	public String getTuffy_type() {
		return tuffy_type;
	}


	public void setTuffy_type(String tuffy_type) {
		this.tuffy_type = tuffy_type;
	}


	public LocalDateTime getLast_trackout_time() {
		return last_trackout_time;
	}


	public void setLast_trackout_time(LocalDateTime last_trackout_time) {
		this.last_trackout_time = last_trackout_time;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public String getPriority() {
		return priority;
	}


	public void setPriority(String priority) {
		this.priority = priority;
	}
	

	public String getIsOver6MonthNoProduce() {
		return isOver6MonthNoProduce;
	}

	public void setIsOver6MonthNoProduce(String isOver6MonthNoProduce) {
		this.isOver6MonthNoProduce = isOver6MonthNoProduce;
	}
	
	

	public String getChangeLvl() {
		return changeLvl;
	}


	public void setChangeLvl(String changeLvl) {
		this.changeLvl = changeLvl;
	}


	public float getChangeTime() {
		return changeTime;
	}


	public void setChangeTime(int changeTime) {
		this.changeTime = changeTime;
	}

	public List<ModChange> getcModChangeList() {
		return cModChangeList;
	}


	public void setcModChangeList(List<ModChange> cModChangeList) {
		this.cModChangeList = cModChangeList;
	}
	

	public String getChangeGroup() {
		return changeGroup;
	}


	public void setChangeGroup(String changeGroup) {
		this.changeGroup = changeGroup;
	}


	@Override
	public String toString() {
		return "Model [partNo=" + partNo + ", modelNo=" + modelNo + ", changeLvl=" + changeLvl + ", changeTime="
				+ changeTime + "]";
	}

	
	public Map<String, Object> getSetupDurationTo(Model toModel, PlanLine line, Job job){
		Map<String, Object> mapSetup = null;
		
		changeTime = 0;
		changeLvl = "L0";
		
		boolean isProcess = false;
		if(ModulePlannerUtility.SPECIFIC_JOB_TYPE.contains(job.getJobType())) {
//			changeTime = (int) job.getChangeDuration(); //JoshLai@20190508-
			changeTime = 0; //JoshLai@20190508+
		}else if("SPL".equals(job.getJobType()) || "ENG".equals(job.getJobType())){
			if(this.getPartNo() != null && (!this.getPartNo().equals(toModel.getPartNo()))) {
				mapSetup =  ModulePlannerUtility.calcChangeTimeByLvl(line.getSite(), line.getFab(), line.getLineNo(), job.getChangeLevel(), toModel);
				isProcess = true;
			}
		} else {
			if(this.getPartNo() != null && !this.partNo.equals(toModel.getPartNo())) {
				mapSetup = ModulePlannerUtility.calcStetupTime(this, toModel, line, cModChangeList);
				isProcess = true;
			}
		}
		if(mapSetup == null) {
			mapSetup = new HashMap<String, Object>();
			mapSetup.put("CHANGE_LEVEL", changeLvl);
			mapSetup.put("CHANGE_DURATION", changeTime);
			mapSetup.put("CHANGE_KEY", null);
		}
		
		if(isProcess) {
			//共用function JoshLai@20190509+
			Map<String, Object> mapRemainChange = ModulePlannerUtility.calcRemainingChangeLine(line.getrJobDashboard(), cModChangeList, job.getPartNo(), job.getMapParam());
			if(mapRemainChange != null)
				mapSetup = mapRemainChange;
			
			/*RJobDashboard lastChangeRJob = line.getrJobDashboard();
			if(lastChangeRJob != null) {
				
				int changeDurationActual = lastChangeRJob.getChangeDuration();
				int changeDurationParameter = 0;
				long remainChangeDuration = 0l;
				
				for(ModChange cModChange : cModChangeList) {
					if(cModChange.getChangeKey().equals(lastChangeRJob.getChangeKey()) 
							&& lastChangeRJob.getChangeLevel().equals(cModChange.getChangeLevel())) { //Add by JoshLai@20190509+
						changeDurationParameter = Integer.parseInt(cModChange.getChangeDuration());
					}
				}
				
				remainChangeDuration = changeDurationParameter - changeDurationActual;
				//未換完的換線時間且partNo相同
				if(remainChangeDuration > 0 && lastChangeRJob.getPartNo().equals(job.getPartNo())) {
					mapSetup.put("CHANGE_LEVEL", lastChangeRJob.getChangeLevel());
					mapSetup.put("CHANGE_DURATION", remainChangeDuration);
					mapSetup.put("CHANGE_KEY", lastChangeRJob.getChangeKey());
				}
			}*/
		}
		return mapSetup;
	}

}
