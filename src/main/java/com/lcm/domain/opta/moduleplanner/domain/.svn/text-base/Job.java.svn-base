package com.lcm.domain.opta.moduleplanner.domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.domain.variable.AnchorShadowVariable;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.core.api.domain.variable.PlanningVariableGraphType;
import org.slf4j.LoggerFactory;

import com.lcm.domain.EqpCapa;
import com.lcm.domain.opta.moduleplanner.solver.TaskDifficultyWeightFactory;
import com.lcm.domain.opta.moduleplanner.utility.ModulePlannerUtility;

@PlanningEntity(difficultyWeightFactoryClass = TaskDifficultyWeightFactory.class)
public class Job extends AbstractPersistable implements Standstill, Cloneable {
	final org.slf4j.Logger logger = LoggerFactory.getLogger(Job.class);

	// Planning variables: changes during planning, between score calculations.
	protected Standstill previousStandstill;
	private boolean locked;

	Job nextJob;

	private PlanLine line;
	private Model model;
	private PlanLine assignLine;

	private String site;
	private String fab;
	private String area; //add area JoshLai@20190722+
	private String lineNo;
	private String jobId;
	private LocalDate planStartDate;
	private LocalDate shiftDate;
	private String shiftTypeString;
	private String jobType;
	private String woId;
	private String partNo;
	private String modelNo;
	protected int planQty;
	private String changeLevel;
	private String changeKey;
	private float changeDuration;
	protected int forecastQty;
	private double pmDuration;
	private double assignStartTime;
	private int isAddTo;
	private int shiftDstart;
	private EqpCapa eqpCapa;
	private String grade;
	private List<PlanLine> possibleLineList;
	private List<TimeWindowedJob> possibleJobList;
	private Map<String, Object> mapParam;
//	private String runSeq; //adjustment轉Timewindowed需要使用 JoshLai@20190724+
//	private String remark;  //adjustment轉Timewindowed需要使用 JoshLai@20190724+
//	private String partLevel;  //adjustment轉Timewindowed需要使用 JoshLai@20190724+

	public int getPlanQty() {
		return planQty;
	}

	public void setPlanQty(int planQty) {
		this.planQty = planQty;
	}

	public int getForecastQty() {
		return forecastQty;
	}

	public void setForecastQty(int forecastQty) {
		this.forecastQty = forecastQty;
	}

	public void setModel(Model model) {
		this.model = model;
		this.modelNo = model.getModelNo();
		this.partNo = model.getPartNo();
	}
	
	@Override
	public Job getNextJob() {
		return nextJob;
	}

	@Override
	public void setNextJob(Job nextJob) {
		this.nextJob = nextJob;
	}

	public Job(){}

	public Job(int id, Model model, int demand, PlanLine line) {
		super(id);        
		this.planQty = demand;
		this.model = model;
		this.line = line;
		locked = false;
	}

	@Override
	public Model getModel() {
		return model;
	}
	

	@Override
	@AnchorShadowVariable(sourceVariableName = "previousStandstill")
	public PlanLine getLine() {
		return line;
	}
	public void setLine(PlanLine line) {
		this.line = line;
	}
	

	@PlanningVariable(valueRangeProviderRefs = {"lineRange", "jobRange"},
			graphType = PlanningVariableGraphType.CHAINED)
	public Standstill getPreviousStandstill() {
//		logger.info(" job: " + this.getJobId() + ", previousStandstill:"+previousStandstill);
		return previousStandstill;
	}

	public void setPreviousStandstill(Standstill previousStandstill) {
		this.previousStandstill = previousStandstill;
	}
	
	//設定該job能上的(指定)線別 add by avonchung 20190304
	@ValueRangeProvider(id = "lineRange")
	public List<PlanLine> getPossibleLineList() {
		return possibleLineList;
	}
	
	public void setPossibleLineList(List<PlanLine> possibleLineList) {
		this.possibleLineList = possibleLineList;
	}
	
	//設定該job允許的接前後job list add by avonchung 20190319
	@ValueRangeProvider(id = "jobRange")
    public List<TimeWindowedJob> getPossibleJobList() {
        return possibleJobList;
    }
	
    public void setPossibleJobList(List<TimeWindowedJob> possibleJobList) {
        this.possibleJobList = possibleJobList;
    }
	
	
	public PlanLine getAssignLine() {
		return assignLine;
	}

	public void setAssignLine(PlanLine assignLine) {
		this.assignLine = assignLine;
	}


	/**
	 * @return a positive number, the distance multiplied by 1000 to avoid floating point arithmetic rounding errors
	*/
	public Map<String, Object> getSetupDurationFromPreviousStandstill() {
		if (previousStandstill != null) {
			return getSetupDurationFrom(previousStandstill);
		}else {
			Map<String, Object> mapSetup = new HashMap<String, Object>();
			mapSetup.put("CHANGE_LEVEL", "L0");
			mapSetup.put("CHANGE_DURATION", 0.0);
			mapSetup.put("CHANGE_KEY", "");
			return mapSetup;
		}
	}


	/**
	 * @param standstill never null
	 * @return a positive number, the distance multiplied by 1000 to avoid floating point arithmetic rounding errors
	 */
	public Map<String, Object> getSetupDurationFrom(Standstill standstill) {
		Map<String, Object> mapSetup = new HashMap<String, Object>();
		String level = "L0";
		float setuptime = 0;
		String changeKey = "";
		mapSetup.put("CHANGE_LEVEL", level);
		mapSetup.put("CHANGE_DURATION", setuptime);
		mapSetup.put("CHANGE_KEY", changeKey);
		boolean isProcess = false;
		Model preModel = standstill.getModel();
		if("SPL".equals(jobType) || "ENG".equals(jobType)) {
			if(standstill.getModel() == null) {
				isProcess = true;
			}else if(standstill.getModel().getPartNo() != null && (!standstill.getModel().getPartNo().equals(model.getPartNo()))){
				isProcess = true;
			}
			if(isProcess) {
				mapSetup = ModulePlannerUtility.calcChangeTimeByLvl(site, fab, lineNo, changeLevel, model);
			}
		}else {
			if(standstill.getModel() != null) {
				mapSetup = standstill.getModel().getSetupDurationTo(model,line, standstill.getNextJob());
				isProcess = true;
				
				
			//mark by avonchung 20190909	
			//standstill若為PM則model為空，就要再往前找，再找PM前一個JOB的Model 
			}else {
//				if(standstill instanceof Job) {
//					preModel = ((Job) standstill).getPreviousStandstill().getModel();
//					if(((Job) standstill).getPreviousStandstill().getModel() != null) {					
//						mapSetup = ((Job) standstill).getPreviousStandstill().getModel().getSetupDurationTo(model,line, standstill.getNextJob());
//						isProcess = true;
//					}
//				}
				
				//update by avonchung 20190909 //standstill若為PM/NoN-Schedule則model為空，就要一直往前找，直到找到有run Model的job
				Standstill standstillx = standstill;
				boolean findModel = false;
				while( findModel == false && standstillx instanceof Job) {
					preModel = ((Job) standstillx).getPreviousStandstill().getModel();
					if(preModel != null) {					
						mapSetup = preModel.getSetupDurationTo(model,line, standstill.getNextJob());
						isProcess = true;
						findModel = true;
					}else {
						standstillx = ((Job) standstillx).getPreviousStandstill();
					}					
				}
			}
		}
		
		if(isProcess && model != null && preModel != null) {
			//若前一個standstill是PlanLine表示其Job為第一班,需取得昨日剩餘未換完線 JoshLai@20190529+
			if(standstill instanceof PlanLine) {
				//共用function JoshLai@20190509+
				Map<String, Object> mapRemainChange = ModulePlannerUtility.calcRemainingChangeLine(line.getrJobDashboard(), model.getcModChangeList(), preModel.getPartNo(), mapParam);
				if(mapRemainChange != null)
					mapSetup = mapRemainChange;
			}
			
			/*RJobDashboard lastChangeRJob = line.getrJobDashboard();
			if(lastChangeRJob != null) {
				int changeDurationActual = lastChangeRJob.getChangeDuration();
				int changeDurationParameter = 0;
				long remainChangeDuration = 0l;
				
				for(ModChange cModChange : model.getcModChangeList()) {
					if(cModChange.getChangeKey().equals(lastChangeRJob.getChangeKey())
							&& lastChangeRJob.getChangeLevel().equals(cModChange.getChangeLevel())) { //Add by JoshLai@20190509+
						changeDurationParameter = Integer.parseInt(cModChange.getChangeDuration());
					}
				}
				
				remainChangeDuration = changeDurationParameter - changeDurationActual;
				//未換完的換線時間且partNo相同
				if(remainChangeDuration > 0 && lastChangeRJob.getPartNo().equals(preModel.getPartNo())) {
					mapSetup.put("CHANGE_LEVEL", lastChangeRJob.getChangeLevel());
					mapSetup.put("CHANGE_DURATION", remainChangeDuration);
					mapSetup.put("CHANGE_KEY", lastChangeRJob.getChangeKey());
				}
			}*/
		}
		return mapSetup;
	}

	/**
	 * @param standstill never null
	 * @return a positive number, the distance multiplied by 1000 to avoid floating point arithmetic rounding errors
	 */
	public float getSetupDurationTo(Standstill standstill) {
		return (float) model.getSetupDurationTo(standstill.getModel(),line, standstill.getNextJob()).get("CHANGE_DURATION");
	}

	/**
	* @return 建立並傳回此物件的一個副本。
	* @throws CloneNotSupportedException
	*/
	public Object clone() throws CloneNotSupportedException {
		// 直接使用父類別的clone()方法,傳回複製副本
		return super.clone();
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public LocalDate getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(LocalDate planStartDate) {
		this.planStartDate = planStartDate;
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
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

	public LocalDate getShift_date() {
		return shiftDate;
	}

	public void setShift_date(LocalDate shift_date) {
		this.shiftDate = shift_date;
	}

	public String getShiftTypeString() {
		return shiftTypeString;
	}

	public void setShiftTypeString(String shiftTypeString) {
		this.shiftTypeString = shiftTypeString;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getWoId() {
		return woId;
	}

	public void setWoId(String woId) {
		this.woId = woId;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getModel_no() {
		return modelNo;
	}

	public void setModel_no(String model_no) {
		this.modelNo = model_no;
	}


	public String getChangeLevel() {
		return changeLevel;
	}

	public void setChangeLevel(String changeLevel) {
		this.changeLevel = changeLevel;
	}

	public float getChangeDuration() {
		return changeDuration;
	}

	public void setChangeDuration(float changeDuration) {
		this.changeDuration = changeDuration;
	}

	public double getPmDuration() {
		return pmDuration;
	}

	public void setPmDuration(double pmDuration) {
		this.pmDuration = pmDuration;
	}

	public double getAssignStartTime() {
		return assignStartTime;
	}

	public void setAssignStartTime(double assignStartTime) {
		this.assignStartTime = assignStartTime;
	}

	public int getIsAddTo() {
		return isAddTo;
	}

	public void setIsAddTo(int isAddTo) {
		this.isAddTo = isAddTo;
	}

	public int getShiftDstart() {
		return shiftDstart;
	}

	public void setShiftDstart(int shiftDstart) {
		this.shiftDstart = shiftDstart;
	}

	public EqpCapa getEqpCapa() {
		return eqpCapa;
	}

	public void setEqpCapa(EqpCapa eqpCapa) {
		this.eqpCapa = eqpCapa;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Map<String, Object> getMapParam() {
		return mapParam;
	}

	public void setMapParam(Map<String, Object> mapParam) {
		this.mapParam = mapParam;
	}

	public String getChangeKey() {
		return changeKey;
	}

	public void setChangeKey(String changeKey) {
		this.changeKey = changeKey;
	}

//	public String getRunSeq() {
//		return runSeq;
//	}
//
//	public void setRunSeq(String runSeq) {
//		this.runSeq = runSeq;
//	}
//
//	public String getRemark() {
//		return remark;
//	}
//
//	public void setRemark(String remark) {
//		this.remark = remark;
//	}
//
//	public String getPartLevel() {
//		return partLevel;
//	}
//
//	public void setPartLevel(String partLevel) {
//		this.partLevel = partLevel;
//	}
}