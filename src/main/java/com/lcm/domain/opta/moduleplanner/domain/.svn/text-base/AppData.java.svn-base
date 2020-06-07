package com.lcm.domain.opta.moduleplanner.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.lcm.domain.Adjustment;
import com.lcm.domain.CFacConstraintCapa;
import com.lcm.domain.EqpCapa;
import com.lcm.domain.EquipmentPreventiveMaintenance;
import com.lcm.domain.HJobDashboard;
import com.lcm.domain.Line;
import com.lcm.domain.ModChange;
import com.lcm.domain.ModModel;
import com.lcm.domain.Plan;
import com.lcm.domain.RJobDashboard;
import com.lcm.domain.SJobDashboard;
import com.lcm.domain.SJobScore;
import com.lcm.domain.SLotOpwp;
import com.lcm.domain.Special;
import com.lcm.domain.Woxx;

public class AppData {
	private List<Line> cFacLineList;
	private List<ModModel> cModModelList;
	private List<EqpCapa> cEqpCapaList;
	private List<Plan> rJobPlanList;
	private List<Special> rJobSpecialList;
	private List<EquipmentPreventiveMaintenance> rJobEqpmList;
	private List<CFacConstraintCapa> cFacConstraintCapaList;
	private List<Woxx> woxxList;
	private List<ModChange> cModChangeList;
	private Map<String, Object> mapParam;
	private String site;
	
	private Map<LocalDate, ShiftDate> shiftDateMap;
	private List<Shift> shiftList;
	private Setup setup = new Setup();
	private List<HJobDashboard> hJobDashboardList;
	private List<SJobScore> scoreList;
	private List<PlanLine> planLineList;
	private List<Model> modelList;
	private List<Capa> capaList;
	private List<SJobDashboard> listPlanResult;
	private LocalDate planStart;
	private int planDay;
	private Map<String, Object> mapDueTime;
	private List<LocalDate> listPlanDate;
	private int shiftDstart;
	private List<Object> historicalList;
	private String fabChangeGroup;
	private List<Adjustment> adjustmentListAfterCOG;
	private List<SLotOpwp> sLotOpwpList;
	private List<Adjustment> adjustmentAllAreaList;
	private List<RJobDashboard> rJobDashboardListBeforePlanDate;
	private List<RJobDashboard> rJobDashboardListInPlanDate;
	private List<String> areaList;
	

	/**
	 * for AppService.java use
	 * @param adjustmentListAfterCOG TODO
	 * @param sLotOpwpList TODO
	 * @param adjustmentListJI TODO
	 * @param adjustmentListMA TODO
	 * @param rJobDashboardListBeforePlanDate TODO
	 * @param rJobDashboardListInPlanDate TODO
	 * @param areaList TODO
	 * */
	public AppData(List<Line> cFacLineList, List<ModModel> cModModelList, List<EqpCapa> cEqpCapaList,
			List<Plan> rJobPlanList, List<Special> rJobSpecialList, List<EquipmentPreventiveMaintenance> rJobEqpmList,
			List<CFacConstraintCapa> cFacConstraintCapaList, List<Woxx> woxxList, List<ModChange> cModChangeList,
			Map<String, Object> mapParam, String site, List<Object> historicalList, String fabChangeGroup,
			List<Adjustment> adjustmentListAfterCOG, List<SLotOpwp> sLotOpwpList,
			List<Adjustment> adjustmentAllAreaList, List<RJobDashboard> rJobDashboardListBeforePlanDate,
			List<RJobDashboard> rJobDashboardListInPlanDate, List<String> areaList) {
		super();
		this.cFacLineList = cFacLineList;
		this.cModModelList = cModModelList;
		this.cEqpCapaList = cEqpCapaList;
		this.rJobPlanList = rJobPlanList;
		this.rJobSpecialList = rJobSpecialList;
		this.rJobEqpmList = rJobEqpmList;
		this.cModChangeList = cModChangeList;
		this.cFacConstraintCapaList = cFacConstraintCapaList;
		this.woxxList = woxxList;
		this.cModChangeList = cModChangeList;
		this.mapParam = mapParam;
		this.site = site;
		this.historicalList = historicalList;
		this.fabChangeGroup = fabChangeGroup;
		this.adjustmentListAfterCOG = adjustmentListAfterCOG;
		this.sLotOpwpList = sLotOpwpList;
		this.adjustmentAllAreaList = adjustmentAllAreaList;
		this.rJobDashboardListBeforePlanDate = rJobDashboardListBeforePlanDate;
		this.rJobDashboardListInPlanDate = rJobDashboardListInPlanDate;
		this.areaList = areaList;
	}
	
	/**
	 * for App.java use
	 * */
	public AppData(List<Line> cFacLineList, List<ModModel> cModModelList, List<EqpCapa> cEqpCapaList,
			List<Plan> rJobPlanList, List<Special> rJobSpecialList,
			List<EquipmentPreventiveMaintenance> rJobEqpmList, List<CFacConstraintCapa> cFacConstraintCapaList,
			List<Woxx> woxxList, List<ModChange> cModChangeList,
			Map<String, Object> mapParam, String site, Map<LocalDate, ShiftDate> shiftDateMap, List<Shift> shiftList,
			Setup setup, List<HJobDashboard> hJobDashboardList, List<SJobScore> scoreList, List<PlanLine> planLineList,
			List<Model> modelList, List<Capa> capaList, List<SJobDashboard> listPlanResult, LocalDate planStart,
			int planDay, Map<String, Object> mapDueTime, List<LocalDate> listPlanDate, int shiftDstart,
			List<Object> historicalList, String fabChangeGroup) {
		super();
		this.cFacLineList = cFacLineList;
		this.cModModelList = cModModelList;
		this.cEqpCapaList = cEqpCapaList;
		this.rJobPlanList = rJobPlanList;
		this.rJobSpecialList = rJobSpecialList;
		this.rJobEqpmList = rJobEqpmList;
		this.cFacConstraintCapaList = cFacConstraintCapaList;
		this.woxxList = woxxList;
		this.cModChangeList = cModChangeList;
		this.mapParam = mapParam;
		this.site = site;
		this.shiftDateMap = shiftDateMap;
		this.shiftList = shiftList;
		this.setup = setup;
		this.hJobDashboardList = hJobDashboardList;
		this.scoreList = scoreList;
		this.planLineList = planLineList;
		this.modelList = modelList;
		this.capaList = capaList;
		this.listPlanResult = listPlanResult;
		this.planStart = planStart;
		this.planDay = planDay;
		this.mapDueTime = mapDueTime;
		this.listPlanDate = listPlanDate;
		this.shiftDstart = shiftDstart;
		this.historicalList = historicalList;
		this.fabChangeGroup = fabChangeGroup;
	}

	public AppData() {
	}

	public List<Line> getcFacLineList() {
		return cFacLineList;
	}

	public void setcFacLineList(List<Line> cFacLineList) {
		this.cFacLineList = cFacLineList;
	}

	public List<ModModel> getcModModelList() {
		return cModModelList;
	}

	public void setcModModelList(List<ModModel> cModModelList) {
		this.cModModelList = cModModelList;
	}

	public List<EqpCapa> getcEqpCapaList() {
		return cEqpCapaList;
	}

	public void setcEqpCapaList(List<EqpCapa> cEqpCapaList) {
		this.cEqpCapaList = cEqpCapaList;
	}

	public List<Plan> getrJobPlanList() {
		return rJobPlanList;
	}

	public void setrJobPlanList(List<Plan> rJobPlanList) {
		this.rJobPlanList = rJobPlanList;
	}

	public List<Special> getrJobSpecialList() {
		return rJobSpecialList;
	}

	public void setrJobSpecialList(List<Special> rJobSpecialList) {
		this.rJobSpecialList = rJobSpecialList;
	}

	public List<EquipmentPreventiveMaintenance> getrJobEqpmList() {
		return rJobEqpmList;
	}

	public void setrJobEqpmList(List<EquipmentPreventiveMaintenance> rJobEqpmList) {
		this.rJobEqpmList = rJobEqpmList;
	}

	public List<CFacConstraintCapa> getcFacConstraintCapaList() {
		return cFacConstraintCapaList;
	}

	public void setcFacConstraintCapaList(List<CFacConstraintCapa> cFacConstraintCapaList) {
		this.cFacConstraintCapaList = cFacConstraintCapaList;
	}

	public List<Woxx> getWoxxList() {
		return woxxList;
	}

	public void setWoxxList(List<Woxx> woxxList) {
		this.woxxList = woxxList;
	}

	public List<ModChange> getcModChangeList() {
		return cModChangeList;
	}

	public void setcModChangeList(List<ModChange> cModChangeList) {
		this.cModChangeList = cModChangeList;
	}

	public Map<String, Object> getMapParam() {
		return mapParam;
	}

	public void setMapParam(Map<String, Object> mapParam) {
		this.mapParam = mapParam;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public Map<LocalDate, ShiftDate> getShiftDateMap() {
		return shiftDateMap;
	}

	public void setShiftDateMap(Map<LocalDate, ShiftDate> shiftDateMap) {
		this.shiftDateMap = shiftDateMap;
	}

	public List<Shift> getShiftList() {
		return shiftList;
	}

	public void setShiftList(List<Shift> shiftList) {
		this.shiftList = shiftList;
	}

	public Setup getSetup() {
		return setup;
	}
	
	public void setSetup(Setup setup) {
		this.setup = setup;
	}

	public List<HJobDashboard> gethJobDashboardList() {
		return hJobDashboardList;
	}

	public void sethJobDashboardList(List<HJobDashboard> hJobDashboardList) {
		this.hJobDashboardList = hJobDashboardList;
	}

	public List<SJobScore> getScoreList() {
		return scoreList;
	}

	public void setScoreList(List<SJobScore> scoreList) {
		this.scoreList = scoreList;
	}

	public List<PlanLine> getPlanLineList() {
		return planLineList;
	}

	public void setPlanLineList(List<PlanLine> planLineList) {
		this.planLineList = planLineList;
	}

	public List<Model> getModelList() {
		return modelList;
	}

	public void setModelList(List<Model> modelList) {
		this.modelList = modelList;
	}

	public List<Capa> getCapaList() {
		return capaList;
	}

	public void setCapaList(List<Capa> capaList) {
		this.capaList = capaList;
	}

	public List<SJobDashboard> getListPlanResult() {
		return listPlanResult;
	}

	public void setListPlanResult(List<SJobDashboard> listPlanResult) {
		this.listPlanResult = listPlanResult;
	}

	public LocalDate getPlanStart() {
		return planStart;
	}

	public void setPlanStart(LocalDate planStart) {
		this.planStart = planStart;
	}

	public int getPlanDay() {
		return planDay;
	}

	public void setPlanDay(int planDay) {
		this.planDay = planDay;
	}

	public Map<String, Object> getMapDueTime() {
		return mapDueTime;
	}

	public void setMapDueTime(Map<String, Object> mapDueTime) {
		this.mapDueTime = mapDueTime;
	}

	public List<LocalDate> getListPlanDate() {
		return listPlanDate;
	}

	public void setListPlanDate(List<LocalDate> listPlanDate) {
		this.listPlanDate = listPlanDate;
	}

	public int getShiftDstart() {
		return shiftDstart;
	}

	public void setShiftDstart(int shiftDstart) {
		this.shiftDstart = shiftDstart;
	}

	public List<Object> getHistoricalList() {
		return historicalList;
	}

	public void setHistoricalList(List<Object> historicalList) {
		this.historicalList = historicalList;
	}

	public String getFabChangeGroup() {
		return fabChangeGroup;
	}

	public void setFabChangeGroup(String fabChangeGroup) {
		this.fabChangeGroup = fabChangeGroup;
	}

	public List<Adjustment> getAdjustmentListAfterCOG() {
		return adjustmentListAfterCOG;
	}

	public void setAdjustmentListAfterCOG(List<Adjustment> adjustmentListAfterCOG) {
		this.adjustmentListAfterCOG = adjustmentListAfterCOG;
	}

	public List<SLotOpwp> getsLotOpwpList() {
		return sLotOpwpList;
	}

	public void setsLotOpwpList(List<SLotOpwp> sLotOpwpList) {
		this.sLotOpwpList = sLotOpwpList;
	}

	public List<Adjustment> getAdjustmentAllAreaList() {
		return adjustmentAllAreaList;
	}

	public void setAdjustmentAllAreaList(List<Adjustment> adjustmentAllAreaList) {
		this.adjustmentAllAreaList = adjustmentAllAreaList;
	}

	public List<RJobDashboard> getrJobDashboardListBeforePlanDate() {
		return rJobDashboardListBeforePlanDate;
	}

	public void setrJobDashboardListBeforePlanDate(List<RJobDashboard> rJobDashboardListBeforePlanDate) {
		this.rJobDashboardListBeforePlanDate = rJobDashboardListBeforePlanDate;
	}

	public List<RJobDashboard> getrJobDashboardListInPlanDate() {
		return rJobDashboardListInPlanDate;
	}

	public void setrJobDashboardListInPlanDate(List<RJobDashboard> rJobDashboardListInPlanDate) {
		this.rJobDashboardListInPlanDate = rJobDashboardListInPlanDate;
	}

	public List<String> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<String> areaList) {
		this.areaList = areaList;
	}
}
