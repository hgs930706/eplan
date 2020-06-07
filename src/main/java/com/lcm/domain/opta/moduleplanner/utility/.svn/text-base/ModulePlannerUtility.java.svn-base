package com.lcm.domain.opta.moduleplanner.utility;

import static java.time.temporal.ChronoUnit.DAYS;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.constraint.ConstraintMatch;
import org.optaplanner.core.api.score.constraint.ConstraintMatchTotal;
import org.optaplanner.core.impl.score.director.ScoreDirector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lcm.domain.Adjustment;
import com.lcm.domain.CFacConstraintCapa;
import com.lcm.domain.EqpCapa;
import com.lcm.domain.ModChange;
import com.lcm.domain.ParParameter;
import com.lcm.domain.Plan;
import com.lcm.domain.RJobDashboard;
import com.lcm.domain.SJobDashboard;
import com.lcm.domain.SJobScore;
import com.lcm.domain.SJobScoreId;
import com.lcm.domain.SLotOpwp;
import com.lcm.domain.SLotOpwpId;
import com.lcm.domain.Special;
import com.lcm.domain.Woxx;
import com.lcm.domain.opta.moduleplanner.domain.Model;
import com.lcm.domain.opta.moduleplanner.domain.PlanLine;
import com.lcm.domain.opta.moduleplanner.domain.Shift;
import com.lcm.domain.opta.moduleplanner.domain.ShiftDate;
import com.lcm.domain.opta.moduleplanner.domain.ShiftType;
import com.lcm.domain.opta.moduleplanner.domain.TaskAssignmentSolution;
import com.lcm.domain.opta.moduleplanner.domain.TimeWindowedJob;
import com.lcm.domain.opta.moduleplanner.solver.score.SJobDashboardKey;

public class ModulePlannerUtility {
	static Logger logger = LoggerFactory.getLogger(ModulePlannerUtility.class);
	
	public static SimpleDateFormat sdfdateNoSlash = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy/MM/dd");
	public static SimpleDateFormat sdfdatetime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	public static DateTimeFormatter formatterNoSlash = DateTimeFormatter.ofPattern("yyyyMMdd");
	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	public static DateTimeFormatter formattertime = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
	public static DateTimeFormatter formatterHHmmss = DateTimeFormatter.ofPattern("HHmmss");
	
	public static String SPECIFIC_JOB_TYPE = "PM,NON-SCHEDULE"; 
	public static final String JOB_TYPE_CHANGE = "CHANGE";
	public static final BigDecimal BIGDECIMAL_60 = new BigDecimal(60);
	public static final BigDecimal BIGDECIMAL_12 = new BigDecimal(12);
	public static final String JOB_TYPE_PROD = "PROD"; 
	public static final String AREA_JI = "JI";
	public static final String AREA_MA = "MA";
	
	public static Map<String, Object> calcStetupTime(Model fromModel, Model toModel, PlanLine line, List<ModChange> cModChangeList){
		String changeKey = "";
		String level = "L0";
		float setupTime = 0;
		
		String lineSite = line.getSite();
		String lineFab = line.getFab();
		String lineNo = line.getLineNo();
		String lineMode = line.getLineMode();
		
		String fromSite = fromModel.getModelSite();
		String fromModelType = fromModel.getModelType();
		String fromPanelSize = fromModel.getPanelSize();
		String fromPartNo = fromModel.getPartNo();
		//只取part_no前2碼
		String fromPartLevel = fromModel.getPartNo() != null ? fromModel.getPartNo().substring(0 , 2) : fromModel.getPartNo();
		String fromIsDemura = fromModel.getIs_demura();
		String fromIsOver6MonthNoProduce = fromModel.getIsOver6MonthNoProduce();
		String fromTuffy_type = fromModel.getTuffy_type();
		String fromBarType = fromModel.getBarType();
		String fromPanel_size_group = fromModel.getPanel_size_group();
		String fromParts_group = fromModel.getParts_group();
		String fromIs_build_pcb = fromModel.getIs_build_pcb();
//		//add by avonchung 20190429
		String fromChangeGroup = fromModel.getChangeGroup();
		
		String toSite = toModel.getModelSite();
		String toModelType = toModel.getModelType();
		String toPanelSize = toModel.getPanelSize();
		String toPartNo = toModel.getPartNo();
		String toPartLevel = toModel.getPartNo() != null ? toModel.getPartNo().substring(0, 2) : toModel.getPartNo();
		String toIsDemura = toModel.getIs_demura();
		String toIsOver6MonthNoProduce = toModel.getIsOver6MonthNoProduce();
		String toTuffy_type = toModel.getTuffy_type();
		String toBarType = toModel.getBarType();
		String toPanel_size_group = toModel.getPanel_size_group();
		String toParts_group = toModel.getParts_group();
		String toIs_build_pcb = toModel.getIs_build_pcb();
//		//add by avonchung 20190429
		String toChangeGroup = toModel.getChangeGroup();
		
		List<String> fromList = new ArrayList<>();
		List<String> toList = new ArrayList<>();
		fromList.add(fromSite);
		fromList.add(fromModelType);
		fromList.add(fromPanelSize);
		fromList.add(fromPartNo);
		fromList.add(fromPartLevel);
		fromList.add(fromBarType);
		fromList.add(fromPanel_size_group);
		fromList.add(fromParts_group);
		fromList.add(fromIs_build_pcb);
		fromList.add(fromIsDemura);
		fromList.add(fromTuffy_type);
		fromList.add(fromIsOver6MonthNoProduce);
		fromList.add(fromChangeGroup);//add by avonchung 20190429
		toList.add(toSite);
		toList.add(toModelType);
		toList.add(toPanelSize);
		toList.add(toPartNo);
		toList.add(toPartLevel);
		toList.add(toBarType);
		toList.add(toPanel_size_group);
		toList.add(toParts_group);
		toList.add(toIs_build_pcb);
		toList.add(toIsDemura);
		toList.add(toTuffy_type);
		toList.add(toIsOver6MonthNoProduce);
		toList.add(toChangeGroup);//add by avonchung 20190429
		
		for(ModChange cModChange : cModChangeList) {
			if(cModChange.isMatch(lineSite, lineFab, lineNo, lineMode, fromList, toList)) {
				changeKey = cModChange.getChangeKey();
				level = cModChange.getChangeLevel();
				setupTime = Float.parseFloat(cModChange.getChangeDuration());
				break;
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("CHANGE_LEVEL", level);
		map.put("CHANGE_DURATION", Float.parseFloat(String.valueOf(setupTime)));
		map.put("CHANGE_KEY", changeKey);
		return map;
	}
	
	public static String addDayReturnStr(LocalDate dStart, int add) {
		LocalDateTime dShiftEnd = addDayReturnDate(dStart, add);
		String shiftEnd = formatter.format(dShiftEnd);
		return shiftEnd;
	}
	
	public static LocalDateTime addDayReturnDate(LocalDate dStart, int add) {
		LocalDateTime datetime = dStart.atStartOfDay();
		return datetime.plusDays(add);
	}
	
	public static Date addHourReturnDate(Date dStart, float add) {
		Calendar c = Calendar.getInstance(); 
		c.setTime(dStart);

		BigDecimal bHour = new BigDecimal(add);
        bHour = bHour.setScale(2,BigDecimal.ROUND_HALF_UP);
        int seconds = bHour.intValue() * 60 * 60;
		
		c.add(Calendar.SECOND, seconds);
		Date dShiftEnd = c.getTime();
		return dShiftEnd;
	}
	
	public static LocalDateTime addHourReturnDate(LocalDateTime dStart, double add) {
		BigDecimal bHour = new BigDecimal(add);
        bHour = bHour.setScale(2,BigDecimal.ROUND_HALF_UP);
        BigDecimal seconds = bHour.multiply(new BigDecimal(60)).multiply(new BigDecimal(60));

        dStart = dStart.plusSeconds(seconds.intValue());
		return dStart;
	}
	
	
	
	public static LocalDateTime calcDateTimeByNum(Map<String, Object> mapParam, double hour) {
        // atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
		int shiftDstart = Integer.parseInt(String.valueOf(((Map<String, Object>)mapParam.get("shift_d_start")).get("in_value1")));
		LocalDate planStart = (LocalDate) ((Map<String, Object>)mapParam.get("plan_start_date")).get("in_value1");
        
		LocalDateTime localDateTime = planStart.atStartOfDay();
        int startHour = shiftDstart;
        localDateTime = localDateTime.withHour(startHour);
        
        BigDecimal bHour = new BigDecimal(hour);
        bHour = bHour.setScale(10,BigDecimal.ROUND_HALF_UP);
        BigDecimal bSeconds = bHour.multiply(BIGDECIMAL_60).multiply(BIGDECIMAL_60);
        
        long lSeconds = bSeconds.longValue();
        localDateTime = localDateTime.plusSeconds(lSeconds);
        
		return localDateTime;
	}
	
	public static LocalDateTime calcDateTimeByNum(LocalDate planStart, int shift_d_start, double hour) {
        // atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
        LocalDateTime localDateTime = planStart.atStartOfDay();
        int startHour = shift_d_start;
        localDateTime = localDateTime.withHour(startHour);
        
        
        BigDecimal bHour = new BigDecimal(hour);
        bHour = bHour.setScale(2,BigDecimal.ROUND_HALF_UP);
        BigDecimal bSeconds = bHour.multiply(new BigDecimal("60")).multiply(new BigDecimal("60"));
        
        long lSeconds = bSeconds.longValue();
        localDateTime = localDateTime.plusSeconds(lSeconds);
        
		return localDateTime;
	}
	
	public static double calcNumByDateTime(LocalDate planStart, int shift_d_start, LocalDate datetime) {
        LocalDateTime localDateTimeFrom = planStart.atStartOfDay();
        LocalDateTime localDateTimeTo = datetime.atStartOfDay();
        localDateTimeFrom.withHour(shift_d_start);
        localDateTimeTo.withHour(shift_d_start);
        Duration duration = Duration.between(localDateTimeFrom, localDateTimeTo);
        long sec = duration.getSeconds();
        double hour = sec/60/60;
		return hour;
	}
	
	public static double calcHoursFromStartDateTime(LocalDate planStart, int shiftDstart, LocalDateTime inputDateTime) {
		LocalDateTime dateTimeFrom = planStart.atStartOfDay();
		dateTimeFrom = dateTimeFrom.withHour(shiftDstart);
		Duration duration = Duration.between(dateTimeFrom, inputDateTime);
		long sec = duration.getSeconds();
		BigDecimal hour = new BigDecimal(sec).divide(BIGDECIMAL_60, 11, RoundingMode.UP).divide(BIGDECIMAL_60, 11,
				RoundingMode.UP);
		return hour.doubleValue();
	}
	
	//換算對應的班別開始時間 
	//ex:Plan Start Date is 2018/12/17
	//input 2018/12/17_N return 12
	//input 2018/12/18_D return 24
	public static double calcShiftTime(LocalDate dStart, LocalDate shiftDate, String shiftType){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Map<String, Object> map = new HashMap<String, Object>();
		double assignStartTime = 0;
		int dayEnd = 30;
		int shiftCount = dayEnd*2;
		Date dEnd = Date.from(dStart.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Calendar c = Calendar.getInstance(); 
		c.setTime(dEnd); 
		c.add(Calendar.DATE, dayEnd);
		dEnd = c.getTime();
		
		Date dShiftPlus = Date.from(dStart.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		int hour = 0;
		for(int i=1; i<=shiftCount; i++) {
			float mod = i%2;
			String sType = mod == 1 ? "D" : "N";
			
			if(i>2 && mod == 1) {
				c = Calendar.getInstance(); 
				c.setTime(dShiftPlus); 
				c.add(Calendar.DATE, 1);
				dShiftPlus = c.getTime();
			}
			map.put(sdf.format(dShiftPlus) + "_" +sType, hour);
			hour += 12;
		}
		
		Date _shiftDate = Date.from(shiftDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		assignStartTime = (int) map.get(sdf.format(_shiftDate)+"_"+shiftType);
		return assignStartTime;
	}
	
	//換算shift date by process start time
	public static Map<String, Object> calcShiftByProcStartTime(LocalDateTime procStartTime, Map<String, Object> mapParam) {
		Map<String, Object> map = new HashMap<String, Object>();
		LocalDate shiftDate = null;
		String shiftDateType = null;
		
		if(procStartTime != null) {
			LocalDateTime datetime = procStartTime;
			int hr = datetime.getHour();
			int shiftDstart = Integer.parseInt(String.valueOf(((Map)mapParam.get("shift_d_start")).get("in_value1")));
			int shiftNstart = Integer.parseInt(String.valueOf(((Map)mapParam.get("shift_e_start")).get("in_value1")));
			
			if(hr < shiftDstart) {
				datetime = datetime.minusDays(1);
				shiftDateType = "N";
			}else if(hr >=shiftDstart && hr < shiftNstart)
				shiftDateType = "D";
			else if(hr >= shiftNstart)
				shiftDateType = "N";
			
			shiftDate = datetime.toLocalDate();
		}
		map.put("SHIFT_DATE", shiftDate);
		map.put("SHIFT_TYPE", shiftDateType);
		return map;
	}
	
	//換算shift date by process and change time
	public static Map<String, Object> calcShiftByProcAndChangeTime(LocalDateTime procStartTime, LocalDateTime procEndTime, LocalDateTime changeStartTime, float changeDuration, int forecastQty, Map<String, Object> mapParam) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		int shiftDstart = Integer.parseInt(String.valueOf(((Map)mapParam.get("shift_d_start")).get("in_value1")));
		int shiftNstart = Integer.parseInt(String.valueOf(((Map)mapParam.get("shift_e_start")).get("in_value1")));
		
		LocalDate shiftDate = null;
		String shiftDateType = null;
		LocalDateTime datetime = procStartTime;
		
		//if(procStartTime.isEqual(procEndTime) && changeStartTime != null) {
		if(procStartTime.isEqual(procEndTime) && changeDuration > 0) {
			datetime = changeStartTime;
		}
		int hr = datetime.getHour();
		if(hr < shiftDstart) {
			datetime = datetime.minusDays(1);
			shiftDateType = "N";
		}else if(hr >=shiftDstart && hr < shiftNstart)
			shiftDateType = "D";
		else if(hr >= shiftNstart)
			shiftDateType = "N";
		
		// 新增例外,當pm當班只run100片且pm時長=12時,當班run的100片會接續pm的procEndTime且procDuration=0,這時需以procEndTime判斷若08:00則晚班,若20:00則早班 JoshLai@20190905+
		if (procStartTime.isEqual(procEndTime) && procEndTime.getMinute() == 0 && procEndTime.getSecond() == 0
				&& forecastQty > 0) {
			if(procEndTime.getHour()==shiftNstart) {
				shiftDateType = "D";
			}else if(procEndTime.getHour()==shiftDstart) {
				shiftDateType = "N";
			}
		}
		
		shiftDate = datetime.toLocalDate();
		LocalDateTime shiftStartBegin = (LocalDateTime) calcShiftTimeByShiftDate(shiftDate, mapParam).get("SHIFT_START_TIME");
		LocalDateTime shiftEndBegin = (LocalDateTime) calcShiftTimeByShiftDate(shiftDate, mapParam).get("SHIFT_END_TIME");
		
		map.put("SHIFT_DATE", shiftDate);
		map.put("SHIFT_TYPE", shiftDateType);
		map.put("SHIFT_START_BEGIN", shiftStartBegin);
		map.put("SHIFT_END_BEGIN", shiftEndBegin);
		return map;
	}
	
	public static boolean isCrossShiftByStartAndEndTime(LocalDateTime startTime, LocalDateTime endTime, Map<String, Object> mapParam){
		boolean isCrossShift = false;
		int shiftDstart = Integer.parseInt(String.valueOf(((Map)mapParam.get("shift_d_start")).get("in_value1")));
		double start = calcHoursFromStartDateTime(startTime.toLocalDate(), shiftDstart, startTime);
		double end = calcHoursFromStartDateTime(endTime.toLocalDate(), shiftDstart, endTime);
		
		BigDecimal decimal = new BigDecimal(start);
		while(start<=end) {
			if(start%12==0 && start!=end) {
				isCrossShift = true;
			}
			decimal = decimal.add(new BigDecimal(.1));
			start = decimal.doubleValue();
		}
		return isCrossShift;
	}
	
	public static Map<String, Object> calcShiftTimeByShiftDate(LocalDate shiftDate, Map<String, Object> mapParam){
		Map<String, Object> map = new HashMap<>();
		int shiftDstart = Integer.parseInt(String.valueOf(((Map)mapParam.get("shift_d_start")).get("in_value1")));
		int shiftNstart = Integer.parseInt(String.valueOf(((Map)mapParam.get("shift_e_start")).get("in_value1")));
		
		LocalDateTime shiftStartTime = LocalDateTime.of(shiftDate.getYear(), shiftDate.getMonthValue(), shiftDate.getDayOfMonth(), shiftDstart, 0, 0);
		LocalDateTime shiftEndTime = LocalDateTime.of(shiftDate.getYear(), shiftDate.getMonthValue(), shiftDate.getDayOfMonth(), shiftNstart, 0, 0);
		
		map.put("SHIFT_START_TIME", shiftStartTime);
		map.put("SHIFT_END_TIME", shiftEndTime);
		return map;
	}
	
	public static Map<String, Object> calcChangeTimeByLvl(String site, String fab, String lineNo, String changeLvl, Model toModel) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ModChange> cModChangeList = toModel.getcModChangeList();
		float setuptime = 0;
		String level = "L0";
		String changeKey = "";
		
		String toSite = toModel.getModelSite();
		String toModelType = toModel.getModelType();
		String toPanelSize = toModel.getPanelSize();
		String toPartNo = toModel.getPartNo();
		String toPartLevel = toModel.getPartNo() != null ? toModel.getPartNo().substring(0, 2) : toModel.getPartNo();
		String toIsDemura = toModel.getIs_demura();
		String toIsOver6MonthNoProduce = toModel.getIsOver6MonthNoProduce();
		String toTuffy_type = toModel.getTuffy_type();
		String toBarType = toModel.getBarType();
		String toPanel_size_group = toModel.getPanel_size_group();
		String toParts_group = toModel.getParts_group();
		String toIs_build_pcb = toModel.getIs_build_pcb();
		String toChangeGroup = toModel.getChangeGroup();//add by avonchung 20190429
		
		List<String> toList = new ArrayList<>();
		toList.add(toSite);
		toList.add(toModelType);
		toList.add(toPanelSize);
		toList.add(toPartNo);
		toList.add(toPartLevel);
		toList.add(toBarType);
		toList.add(toPanel_size_group);
		toList.add(toParts_group);
		toList.add(toIs_build_pcb);
		toList.add(toIsDemura);
		toList.add(toTuffy_type);
		toList.add(toIsOver6MonthNoProduce);
		toList.add(toChangeGroup);//add by avonchung 20190429
		
		for(ModChange cModChange : cModChangeList) {
			if(cModChange.isMatch(site, fab, lineNo, changeLvl, toList)) {
				changeKey = cModChange.getChangeKey();
				level = cModChange.getChangeLevel();
				setuptime = Float.parseFloat(cModChange.getChangeDuration());
				break;
			}
		}
		
		map.put("CHANGE_LEVEL", level);
		map.put("CHANGE_DURATION", Float.parseFloat(String.valueOf(setuptime)));
		map.put("CHANGE_KEY", changeKey);
		
		return map;
	}
	
	public static List<SJobDashboard> splitByShift(TimeWindowedJob job, Map<String, Object> mapParam, boolean isRun) {
		List<SJobDashboard> listMap = new ArrayList<>();
		
		LocalDate planStartDate = ((LocalDate) ((Map)(mapParam.get("plan_start_date"))).get("in_value1"));
		LocalDate planEndDate = ((LocalDate) ((Map)(mapParam.get("plan_end_date"))).get("in_value1")).plusDays(7);
		boolean isSortByDateASC = true;
		List<Shift> shiftList = generateShiftList(planStartDate, planEndDate, mapParam, isSortByDateASC);
		List<String> areaList = (List<String>) ((Map)(mapParam.get("area"))).get("in_value1");
		
//    	logger.debug("======================START==========================");
		double start = job.getStartTime() == null ? 0 : job.getStartTime();
//		double end = (double) job.getStartTime() +job.getServiceDuration(); //Marked by JoshLai@20190308
		double end = job.getEndTime();
		
		BigDecimal bEnd = new BigDecimal(String.valueOf(end));
		bEnd = bEnd.setScale(10, BigDecimal.ROUND_HALF_UP);
		end = bEnd.doubleValue();
		
		int qty = (int) job.getForecastQty(); 
		int idxStart = (int) Math.ceil(start);
		int idxEnd = (int) Math.ceil(end);
		int closeTarget = idxStart%12 == 0 ? idxStart : idxStart + 12 - (idxStart%12);
		double i=closeTarget;
		double perHourQty = qty / (end - start);
		
		int idx=1;
		boolean isValidToSplit = false;
		while(i<idxEnd+12) {
			double splitQty = perHourQty * (i-start);
			
			SJobDashboard sJob = new SJobDashboard();
			String jobId = job.getJobId();
			
			if(jobId.contains("##") && areaList.contains(AREA_JI)) {
				String[] splitJobId = job.getJobId().split("##");
				String oldJobIdSeq = splitJobId[splitJobId.length-1];
				String newJobIdSeq = String.valueOf(Integer.parseInt(oldJobIdSeq)+1);
				String newJobId = jobId.substring(0, jobId.lastIndexOf("##")+2) + newJobIdSeq;
				sJob.setJobId(newJobId);
			}else
				sJob.setJobId(job.getJobId()+"##"+idx);
			
			sJob.setSite(job.getSite());
			sJob.setFab(job.getFab());
			sJob.setArea(job.getArea());
			sJob.setLine(job.getLine().getLineNo());
			sJob.setPartNo(job.getPartNo());
			sJob.setModelNo(job.getModel_no());
			sJob.setPlanQty(job.getPlanQty());
			sJob.setGrade(job.getGrade());
			sJob.setWoId(job.getWoId());
			sJob.setIsAddTo(job.getIsAddTo());
			sJob.setJobReadyTime(calcDateTimeByNum(mapParam, job.getReadyTime())); //Add by JoshLai@20190828+

			sJob.setJobType(job.getJobType());
			String changeLvl = (String) job.getSetupDurationFromPreviousStandstill().get("CHANGE_LEVEL");
			String changeKey = (String) job.getSetupDurationFromPreviousStandstill().get("CHANGE_KEY");
			float changeDuration = Float.parseFloat(String.valueOf(job.getSetupDurationFromPreviousStandstill().get("CHANGE_DURATION")));
			BigDecimal bchangeDuration = new BigDecimal(changeDuration);
			
			sJob.setChangeLevel(changeLvl);
			sJob.setChangeDuration(bchangeDuration.floatValue());
			if(listMap.size() == 0)
				sJob.setChangeKey(changeKey);

//			logger.debug("line: " + job.getLineNo() + " jobId: " + job.getJobId() + " changeLvl: " + changeLvl + " changeDuration: " + changeDuration);

			//當startTime為0時,換線時間為startTime+changeDuration
			//當startTime不為0時,換線時間為startTime-changeDuration JoshLai@20190527+
			double dSub = (float)-changeDuration;
//			logger.debug("dSub: " + dSub);
			
			LocalDateTime procStartTime = calcDateTimeByNum(mapParam, start);
			LocalDateTime procEndTime = calcDateTimeByNum(mapParam, end);
			LocalDateTime dChangeStart = addHourReturnDate(procStartTime, dSub);
			
			sJob.setChangeStartTime(dChangeStart);
			sJob.setChangeEndTime(procStartTime);

//			logger.debug("setChangeStartTime: " + dChangeStart + " setChangeEndTime: " + procStartTime);

			LocalDate changeShiftDate = (LocalDate) calcShiftByProcStartTime(dChangeStart, mapParam).get("SHIFT_DATE");
			String changeShift = (String) calcShiftByProcStartTime(dChangeStart, mapParam).get("SHIFT_TYPE");
			sJob.setChangeShiftDate(changeShiftDate);
			sJob.setChangeShift(changeShift);

			if (idx > 1) {
				dChangeStart = procStartTime;
				sJob.setChangeLevel(null);
				sJob.setChangeDuration(0);
				sJob.setChangeStartTime(null);
				sJob.setChangeEndTime(null);
				sJob.setChangeShiftDate(null);
				sJob.setChangeShift(null);
			}

			sJob.setAssignShiftDate(job.getShift_date());
			sJob.setAssignShift(job.getShiftTypeString());

			LocalDate shiftDate = (LocalDate) calcShiftByProcStartTime(procStartTime, mapParam).get("SHIFT_DATE");
			String shiftType = (String) calcShiftByProcStartTime(procStartTime, mapParam).get("SHIFT_TYPE");
			sJob.setShiftDate(shiftDate);// 用process_start_tme換算
			sJob.setShift(shiftType);
			sJob.setForecastQty(job.getForecastQty());
			
			//設定班次 JoshLai@20190415+
			sJob.setShiftList(shiftList);

			if(i >= start && i<end && i != start) {
				isValidToSplit = true;
				
				procStartTime = calcDateTimeByNum(mapParam, start);
				procEndTime = calcDateTimeByNum(mapParam, i);
				
//				logger.debug("procStartTime: " + formattertime.format(procStartTime) + " procEndTime: " + formattertime.format(procEndTime));
				
			}else if(i != start){
				isValidToSplit = true;
				splitQty = perHourQty * (end-start);
				procStartTime = calcDateTimeByNum(mapParam, start);
				procEndTime = calcDateTimeByNum(mapParam, end);
				
//				logger.debug("procStartTime: " + formattertime.format(procStartTime) + " procEndTime: " + formattertime.format(procEndTime));
			}
			
			sJob.setProcessStartTime(procStartTime);
			sJob.setProcessEndTime(procEndTime);
			sJob.setForecastQty((int) Math.round(splitQty));
			
			if(isValidToSplit) {
				//若無跨班換線,但是換線占滿整個process而無生產,則需切割 JoshLai@20190604+
				if(sJob.getChangeShift()!=null && !sJob.getShift().equals(sJob.getChangeShift())) {
					LocalDateTime changeSTime = sJob.getChangeStartTime();
					LocalDateTime changeETime = sJob.getChangeEndTime();
					if(changeSTime!=null && changeETime!=null && !changeSTime.isEqual(changeETime) && sJob.getChangeDuration()<=12) {
						boolean isCrossShift = isCrossShiftByStartAndEndTime(changeSTime, changeETime, mapParam);
						
						if(isCrossShift==false) {
							try {
								//若切班時換線即占滿整班,需將換線切出,一個帶換線,一個不帶換線
								SJobDashboard sJobWithChange = (SJobDashboard) sJob.clone();
								sJobWithChange.setProcessStartTime(sJobWithChange.getChangeEndTime());
								sJobWithChange.setProcessEndTime(sJobWithChange.getChangeEndTime());
								sJobWithChange.setForecastQty(0);
								sJobWithChange.setShift(sJobWithChange.getChangeShift());
								
								//不帶換線
								sJob.setChangeDuration(0);
								sJob.setChangeKey(null);
								sJob.setChangeLevel(null);
								sJob.setChangeShift(null);
								sJob.setChangeShiftDate(null);
								sJob.setChangeStartTime(null);
								sJob.setChangeEndTime(null);
								
								listMap.add(sJobWithChange);
							} catch (CloneNotSupportedException e) {
								e.printStackTrace();
							}
						}
					}
				}
				sJob.setJobQty(sJob.getForecastQty());
				listMap.add(sJob);
				idx++;
			}
			start = i;
			i+=12;
		}
//		logger.debug("======================END==========================");
		return listMap;
	}
	
	public static List<SJobDashboard> changeCrossShiftAddHour(List<SJobDashboard> sJobDashboardList, Map<String, Object> mapParam) {
		Map<String, Object> mapParamChangeCrossSetting = (Map<String, Object>) mapParam.get("change_cross_setting");
		//無設定參數則不執行
		if(mapParamChangeCrossSetting == null || mapParamChangeCrossSetting.get("in_value1")==null 
				|| !mapParamChangeCrossSetting.get("in_value1").equals("change_cross_shift_add_hr")
				|| mapParamChangeCrossSetting.get("in_value2")==null ) {
			return sJobDashboardList;
		}
		
		List<SJobDashboard> listMap = null;
		List<SJobDashboard> sJobDashboardListCopy = new ArrayList<>(sJobDashboardList);
		
		Comparator<SJobDashboard> comparator = Comparator.comparing(SJobDashboard::getLine)
				.thenComparing(SJobDashboard::getShiftDate)
				.thenComparing(SJobDashboard::getProcessStartTime);
		Collections.sort(sJobDashboardListCopy, comparator);
		
		float changeCrossShiftAddHr = Float.parseFloat(String.valueOf((mapParamChangeCrossSetting).get("in_value2")));
		
		if(sJobDashboardList.size() > 0) {
			int i = 0;
			for(SJobDashboard sJob : sJobDashboardListCopy) {
				boolean isChangeCrossShift = false;
				//有換線起迄時間且換線時長>0 && <=12 JoshLai@20190904+
				if(sJob.getChangeStartTime() != null && sJob.getChangeEndTime() != null && sJob.getChangeDuration() > 0 && sJob.getChangeDuration() <=12) {
//					Map<String, Object> mapShiftStart = calcShiftByProcStartTime(sJob.getChangeStartTime(), mapParam);
//					Map<String, Object> mapShiftEnd = calcShiftByProcEndTime(sJob.getChangeEndTime(), mapParam);
//					
//					Map<String, Object> mapChange = calcShiftByProcAndChangeTime(sJob.getProcessStartTime(), sJob.getProcessEndTime(), sJob.getChangeStartTime(), mapParam);
//					
//					LocalDate shiftDateStart = (LocalDate) mapShiftStart.get("SHIFT_DATE");
//					String shiftTypeStart = (String) mapShiftStart.get("SHIFT_TYPE");
//					LocalDate shiftDateEnd = (LocalDate) mapShiftEnd.get("SHIFT_DATE");
//					String shiftTypeEnd = (String) mapShiftEnd.get("SHIFT_TYPE");
//					
//					//若shiftDate或者shiftType不同表示不同班
//					if((!shiftDateStart.isEqual(shiftDateEnd) || !shiftTypeStart.equals(shiftTypeEnd)) 
//							) {
//						isChangeCrossShift = true;
//					}
					
					//換線起訖時間
					LocalDateTime changeStartTime = sJob.getChangeStartTime();
					LocalDateTime changecEndTime = sJob.getChangeEndTime();
					
					//換算換線時長
					BigDecimal bSecBewteen = new BigDecimal(ChronoUnit.SECONDS.between(changeStartTime, changecEndTime));
					BigDecimal bHoursBewteen = bSecBewteen.divide(BIGDECIMAL_60, 11, RoundingMode.HALF_UP).divide(BIGDECIMAL_60, 10, RoundingMode.HALF_UP);
					
					int shiftDstart = Integer.parseInt((String) ((Map)mapParam.get("shift_d_start")).get("in_value1"));
					int shiftNstart = Integer.parseInt((String) ((Map)mapParam.get("shift_e_start")).get("in_value1"));
					
					//早班開始時間
					LocalDateTime shiftStartTime = changeStartTime.withHour(shiftDstart);
					shiftStartTime = shiftStartTime.withMinute(0);
					shiftStartTime = shiftStartTime.withSecond(0);
					
					//早班結束時間
					LocalDateTime shiftEndTime = changeStartTime.withHour(shiftNstart);
					shiftEndTime = shiftEndTime.withMinute(0);
					shiftEndTime = shiftEndTime.withSecond(0);
					
					//隔天早班開始時間,判斷隔日跨班換線
					LocalDateTime shiftStartTimePlus1Day = shiftStartTime.plusDays(1);
					
					//算換Unix Timestamp, 長整數型態方便計算跨班
					ZoneId zoneId = ZoneId.systemDefault();
					long lChangeStartTime = changeStartTime.atZone(zoneId).toEpochSecond();
					long lChangecEndTime = changecEndTime.atZone(zoneId).toEpochSecond();
					long lShiftStartTime = shiftStartTime.atZone(zoneId).toEpochSecond();
					long lShiftEndTime = shiftEndTime.atZone(zoneId).toEpochSecond();
					long lShiftStartTimePlus1Day = shiftStartTimePlus1Day.atZone(zoneId).toEpochSecond();
					
//					logger.info("lchangeStartTime: " + lChangeStartTime + " lchangecEndTime: " + lChangecEndTime
//							+ " lShiftStartTime: " + lShiftStartTime
//							+ " lShiftEndTime: " + lShiftEndTime
//							+ " bSecBewteen: " +bSecBewteen
//							+ " bHoursBewteen: " + bHoursBewteen);
					
					if((lChangeStartTime < lShiftStartTime && lChangecEndTime > lShiftStartTime) //若換線起迄時間介於早班開始時間(08:00)
							|| (lChangeStartTime < lShiftEndTime && lChangecEndTime > lShiftEndTime) //若換線起迄時間介於早班結束時間(20:00)
							|| lChangecEndTime > lShiftStartTimePlus1Day) //若換線結束時間超過隔日早班開始時間(08:00)
						isChangeCrossShift = true; //確定跨班
					
//					logger.info("size: " +sJobDashboardListCopy.size() + " jobid: " + sJob.getJobId() 
//								+ " isChangeCrossShift: " + isChangeCrossShift);
					
					String nextLineNo = "";
					if(i+1 < sJobDashboardListCopy.size()) {
						nextLineNo = sJobDashboardListCopy.get(i+1).getLine();
					}
					
					if(isChangeCrossShift) {
						//增加該筆changeEndTime
						float changeDuration = sJob.getChangeDuration() + changeCrossShiftAddHr;
						//換算成秒
						BigDecimal bChangeCrossShiftAddSeconds = new BigDecimal(changeCrossShiftAddHr).multiply(BIGDECIMAL_60).multiply(BIGDECIMAL_60);
						sJob.setChangeDuration(changeDuration);
						
						LocalDateTime changeEndTime = sJob.getChangeEndTime();
						LocalDateTime changeEndTimePlus = changeEndTime.plusSeconds(bChangeCrossShiftAddSeconds.longValue());
						
						doMoveJob(sJob, sJobDashboardListCopy, changeEndTimePlus, changeEndTime, mapParam, true);
						
						sJob.setChangeEndTime(changeEndTimePlus);
						LocalDateTime processStartTime = sJob.getProcessStartTime();
						processStartTime = processStartTime.plusSeconds(bChangeCrossShiftAddSeconds.longValue());
						sJob.setProcessStartTime(processStartTime);
						
						LocalDateTime processEndTime = sJob.getProcessEndTime();
						processEndTime = processEndTime.plusSeconds(bChangeCrossShiftAddSeconds.longValue());
						sJob.setProcessEndTime(processEndTime);
						
					}
				}
				i++;
			}
		}
		listMap = new ArrayList<>(sJobDashboardListCopy);
		return listMap;
	}
	
	/*
	 * Add S02 若與PM同一天還有PPC Plan才需提早排100片 JoshLai@20190701+
	 * */
	public static List<SJobDashboard> calcPMaddQty(List<SJobDashboard> sJobDashboardList, Map<String, Object> mapParam, boolean isRun) throws CloneNotSupportedException {
		if(mapParam.get("pm_add_qty") == null) {
			return sJobDashboardList;
		}
		sortSJobDashboardList(sJobDashboardList);
		
		BigDecimal bPMaddQty = new BigDecimal(String.valueOf(((Map)mapParam.get("pm_add_qty")).get("in_value1")));
		for(int i=0; i<sJobDashboardList.size(); i++) {
			SJobDashboard sJob = sJobDashboardList.get(i);
			SJobDashboard nextSJob = null;
			if(SPECIFIC_JOB_TYPE.contains(sJob.getJobType())) {
				Map<String, Object> mapAfterPm = findAfterPmSJob(sJobDashboardList, sJob); 
				nextSJob = mapAfterPm.get("job") !=null ? (SJobDashboard) mapAfterPm.get("job") : null;
				SJobDashboard pmJob = (SJobDashboard) mapAfterPm.get("pm_job");
				
				//S02 若與PM同一天還有PPC Plan才需提早排100片 JoshLai@20190701+
				if(nextSJob!=null && nextSJob.getShiftDate().isEqual(pmJob.getShiftDate())) {
					if(pmJob.equals(sJob)) {
						LocalDateTime pmEndTime = pmJob.getProcessEndTime();
						List<SJobDashboard> afterPmSJobDashboardList = findAfterPmSJobList(sJobDashboardList, pmJob, nextSJob, pmEndTime, bPMaddQty, mapParam, isRun);
					}
				}
			}
		}
		
		//PM當班只排100片會造成後面的Job time shift,需重新切班
		List<SJobDashboard> reviseListMap1 = new ArrayList<>(sJobDashboardList);
		//跨班換線拆班
		reviseListMap1 = splitJobByChangeTime(reviseListMap1, mapParam, false, false, isRun);
		logger.info(reviseListMap1.size() + " calcPMaddQty splitJobByChangeTime-1: " + reviseListMap1);
		//拆班後相同processTime合併
		reviseListMap1 = combineJobByChangeTime(reviseListMap1, mapParam, isRun);
		logger.info(reviseListMap1.size() + " calcPMaddQty combineJobByChangeTime-2: " + reviseListMap1);
		
		//處理時間跨班切班
		List<ModChange> cModChangeList = (List<ModChange>) mapParam.get("cmod_change_list");
		List<SJobDashboard> reviseListMap2 = splitJobByProcessTime(reviseListMap1, cModChangeList, mapParam, isRun);
		logger.info(reviseListMap2.size() + " calcPMaddQty splitJobByProcessTime-3: " + reviseListMap2);
		//拆班後相同processTime合併
		reviseListMap2 = combineJobByProcessTime(reviseListMap2, mapParam, isRun);
		logger.info(reviseListMap2.size() + " calcPMaddQty combineJobByProcessTime-4: " + reviseListMap2);
		
		return reviseListMap2;
	}
	
	//找出PM後的第一個plan
	private static Map<String, Object> findAfterPmSJob(List<SJobDashboard> sJobDashboardList, SJobDashboard pmJob) {
		Map<String, Object> map = new HashMap<>();
		boolean isFind = false;
		String pmJobId = pmJob.getJobId().split("##")[0];
		for(SJobDashboard sJob : sJobDashboardList) {
			String sJobId = sJob.getJobId().split("##")[0];
			if(sJob.equals(pmJob)) {
				isFind = true;
			}
			if(isFind && pmJobId.equals(sJobId)) {
				map.put("pm_job", sJob);
			}
			if(isFind && !SPECIFIC_JOB_TYPE.contains(sJob.getJobType()) 
					&& sJob.getLine().equals(pmJob.getLine())) {
				map.put("job", sJob);
				return map;
			}
		}
		return map;
	}
	
	//找出PM後的planList
	/*
	 * Add S02 提早排的量，若為同一天的ppc plan才需扣量 (pm_add_qty) JoshLai@20190701+
	 * */
	private static List<SJobDashboard> findAfterPmSJobList(List<SJobDashboard> sJobDashboardList, SJobDashboard pmJob, SJobDashboard nextSJob, LocalDateTime pmEndTime, BigDecimal bPMaddQty, Map<String, Object> mapParam, boolean isRun) throws CloneNotSupportedException {
		LocalDateTime pmShiftEndTime = null;
		for(Shift shift : pmJob.getShiftList()) {
			if(shift.getIndex()==pmJob.getShiftIndex()) {
				pmShiftEndTime = calcDateTimeByNum(mapParam, shift.getEndTimeIndex());
				break;
			}
		}
		long pmShiftRemaningTime = ChronoUnit.SECONDS.between(pmEndTime, pmShiftEndTime);
		
		List<SJobDashboard> afterPmSJobDashboardList = new ArrayList<>();
		String targetJobId = nextSJob.getJobId().split("##")[0];
		String targetWoId = nextSJob.getWoId();
		targetWoId = targetWoId == null ? "" : targetWoId;
		String targetJobType = nextSJob.getJobType();
		
		LocalDateTime jobStartTime = null;
		LocalDateTime jobEndTime = null;
		BigDecimal bSumForecastQty = new BigDecimal(0);
		BigDecimal bSencondsBetween = null;
		String previousChangeKey = null;
		
		for(SJobDashboard sJob : sJobDashboardList) {
			String sJobWoId = sJob.getWoId() == null ? "" : sJob.getWoId();
			if(targetJobId.equals(sJob.getJobId().split("##")[0])
					&& targetWoId.equals(sJobWoId)
					&& targetJobType.equals(sJob.getJobType())) {
				afterPmSJobDashboardList.add(sJob); //找出PM後的sJobList
				
				float changeDuration = 0;
				if(sJob.getChangeStartTime()!=null && sJob.getChangeEndTime()!=null) {
					changeDuration = ChronoUnit.SECONDS.between(sJob.getChangeStartTime(), sJob.getChangeEndTime());
				}
				
				//若PM後接跨班換線則不做
				if(previousChangeKey!=null && previousChangeKey.equals(sJob.getChangeKey())) {
					return sJobDashboardList;
				}
				//若將換線提早到PM當班,而當班剩餘時間不足以換線也不做
				if(changeDuration > pmShiftRemaningTime) {
					return sJobDashboardList;
				}
				
				if(jobStartTime == null)
					jobStartTime = sJob.getProcessStartTime();
				jobEndTime = sJob.getProcessEndTime();
				
				BigDecimal bForecastQty = new BigDecimal(sJob.getForecastQty());
				bSumForecastQty = bSumForecastQty.add(bForecastQty);//累加forecast數量
				previousChangeKey = sJob.getChangeKey();
			}
		}
		
		if(bSumForecastQty.compareTo(BigDecimal.ZERO) > 0) {
			//計算生產單片所需時間
			long secondsBetween = ChronoUnit.SECONDS.between(jobStartTime, jobEndTime);
			bSencondsBetween = new BigDecimal(secondsBetween);
			BigDecimal bQtyPerSeconds = bSencondsBetween.divide(bSumForecastQty, 10, RoundingMode.HALF_UP);
			
			//計算生產目標片數所需秒數
			BigDecimal pmAddSeconds = bQtyPerSeconds.multiply(bPMaddQty).setScale(0, RoundingMode.HALF_UP);
			
			//Job時間長度足夠提取的情況
			if(secondsBetween >= pmAddSeconds.longValue()) {
				BigDecimal sumExtractTime = new BigDecimal(0);//累計提取時間
				SJobDashboard newSJob = null;
				
				SJobDashboard firstCandidateSJob = afterPmSJobDashboardList.get(0);//候選SJob第一位
				SJobDashboard lastCandidateSJob = afterPmSJobDashboardList.get(afterPmSJobDashboardList.size()-1);//候選SJob最後一位
				
				//提取數量
//				for(int i=0; i<afterPmSJobDashboardList.size(); i++) {//JoshLai@20190521- 改反序從後面扣數量
				LocalDate afterPmFirstSJob = afterPmSJobDashboardList.get(0).getShiftDate();
				for(int i=afterPmSJobDashboardList.size()-1; i>=0; i--) {
					LocalDateTime afterPmFirstChangeStartTime = null;
					LocalDateTime afterPmFirstChangeEndTime = null;
					if(firstCandidateSJob.getChangeDuration()>0) {
						afterPmFirstChangeStartTime = firstCandidateSJob.getChangeStartTime();
						afterPmFirstChangeEndTime = firstCandidateSJob.getChangeEndTime();
					}
					
					SJobDashboard sJob = afterPmSJobDashboardList.get(i);
					LocalDateTime procStartTime = sJob.getProcessStartTime();
					LocalDateTime procEndTime = sJob.getProcessEndTime();
					BigDecimal bSecondsProcTime = new BigDecimal(ChronoUnit.SECONDS.between(procStartTime, procEndTime));
					
					BigDecimal actualExtractTime = new BigDecimal(0);//實際提取時間
					if(sumExtractTime.intValue() < pmAddSeconds.intValue()) {
						BigDecimal bRemainExtractTime = pmAddSeconds.subtract(sumExtractTime);//剩餘提取時間
						BigDecimal actualExtractQty = bRemainExtractTime.divide(bQtyPerSeconds, RoundingMode.HALF_UP);
						//新增SJob
						if(newSJob==null) {
							newSJob = (SJobDashboard) firstCandidateSJob.clone();
							String newSJobId = newSJob.getJobId().split("##")[0]+"##"+0;
							newSJob.setJobId(newSJobId);
							newSJob.setForecastQty(actualExtractQty.intValue());
							newSJob.setJobQty(actualExtractQty.intValue());
							newSJob.setProcessStartTime(pmEndTime);
							newSJob.setProcessEndTime(pmShiftEndTime);//結束時間直接設定PM當班結束時間
							
							//設定和PM同班
							newSJob.setShift(pmJob.getShift());
							newSJob.setShiftIndex(pmJob.getShiftIndex());
							
							//若有換線,取得換線起訖時間
							if(newSJob.getChangeStartTime()!=null && newSJob.getChangeEndTime()!=null
									&& !newSJob.getChangeStartTime().isEqual(newSJob.getChangeEndTime())) {
								afterPmFirstChangeStartTime = newSJob.getChangeStartTime();
								afterPmFirstChangeEndTime = newSJob.getChangeEndTime();
								
								firstCandidateSJob.setChangeKey(null);
								firstCandidateSJob.setChangeDuration(0);
								firstCandidateSJob.setChangeStartTime(null);
								firstCandidateSJob.setChangeEndTime(null);
								firstCandidateSJob.setChangeShift(null);
								firstCandidateSJob.setChangeShiftDate(null);
							}
						}else {
							BigDecimal bForecastQty = new BigDecimal(newSJob.getForecastQty());
							bForecastQty = bForecastQty.add(actualExtractQty);
							newSJob.setForecastQty(bForecastQty.intValue());
							newSJob.setJobQty(bForecastQty.intValue());
						}
						if(bSecondsProcTime.intValue() > bRemainExtractTime.intValue()) {//僅提取所需要的時間
							sumExtractTime = sumExtractTime.add(bRemainExtractTime);
							actualExtractTime = bRemainExtractTime;
						}else {//提取全部剩餘時間
							sumExtractTime = sumExtractTime.add(bSecondsProcTime);
							actualExtractTime = bSecondsProcTime;
							
							//S02 提早排的量，若為同一天的ppc plan才需扣量 (pm_add_qty) JoshLai@20190701+
							if(pmJob.getShiftDate().isEqual(afterPmFirstSJob)) {
								sJob.setProcessStartTime(null);
								sJob.setProcessEndTime(null);
							}
						}
						//減少數量-從最後一個SJob開始取
						BigDecimal subQty = new BigDecimal(sJob.getForecastQty()).subtract(actualExtractQty);
						
						//S02 提早排的量，若為同一天的ppc plan才需扣量 (pm_add_qty) JoshLai@20190701+
						if(pmJob.getShiftDate().isEqual(afterPmFirstSJob)) {
							sJob.setForecastQty(subQty.intValue());
						}
						
//						if(isRun)
//							logger.debug("sJob.getForecastQty(): " + sJob.getForecastQty()
//										+ " actualExtractTime: " + actualExtractTime
//										+ " actualExtractQty: " + actualExtractQty
//										+ " subQty: " + subQty
//										+ " newSJob: " + newSJob);
						
						//減少時間-從最後一個SJob開始取
						LocalDateTime lastCandidateSJobProcEndTime = sJob.getProcessEndTime();
						LocalDateTime subTime = lastCandidateSJobProcEndTime.minusSeconds(actualExtractTime.longValue());
						
//						if(isRun)
//							logger.debug("befre move firstCandidateSJob: " + firstCandidateSJob
//										+ " ,lastCandidateSJobProcEndTime: " + lastCandidateSJobProcEndTime
//										+ " ,actualExtractTime: " + actualExtractTime
//										+ " ,subTime: " + subTime
//										+" ,afterPmSJobDashboardList: " + afterPmSJobDashboardList);
						
						//數量足夠扣除的話,1.先扣除procEndTime, 2.再連同後面線上的job往前拉對齊
						//S02 提早排的量，若為同一天的ppc plan才需扣量 (pm_add_qty) JoshLai@20190701+
						if(pmJob.getShiftDate().isEqual(afterPmFirstSJob)) {
							sJob.setProcessEndTime(subTime);
						}
						
//						if(isRun){
//							logger.debug("after move: " + afterPmSJobDashboardList);
//							logger.debug("procStartTime: " + procEndTime
//										+ " actualExtractTime: " + actualExtractTime
//										+ " subTime: " + subTime
//										+ " ProcessEndTime: " + sJob.getProcessEndTime());
//						}
						
						//將換線時間提早到PM當班
						if(afterPmFirstChangeStartTime!=null && afterPmFirstChangeEndTime!=null) {
							doMoveJob(firstCandidateSJob, afterPmSJobDashboardList, afterPmFirstChangeStartTime, procStartTime, mapParam, false);
						}
						
//						if(isRun)
//							logger.debug("bSecondsProcTime: " + bSecondsProcTime + " actualExtractTime: " + actualExtractTime);
					}else 
						break; //滿足提取數量,跳出
				}
				
				//將提取的候選SJob放回sJobDashboardList
				updateSJobDashboardList(afterPmSJobDashboardList, sJobDashboardList);
				
				//候選SJob最後一位的下一班
				//若候選SJob最後一位的下一班要貼近的時間比jobReadyTime早,則不要往前拉齊時間 JoshLai@20190909+
				SJobDashboard needToMoveSJob = getNextSJob(sJobDashboardList, lastCandidateSJob);
				if(needToMoveSJob!=null) {
					LocalDateTime sJobStartTime = needToMoveSJob.getChangeStartTime()!=null ? needToMoveSJob.getChangeStartTime() : needToMoveSJob.getProcessStartTime();
					if(lastCandidateSJob.getProcessEndTime().isAfter(sJobStartTime)) {
						//從PM後的SJobList都要貼近移動後的時間
						doMoveJob(needToMoveSJob, sJobDashboardList, lastCandidateSJob.getProcessEndTime(), sJobStartTime, mapParam, true);
					}
				}
				
				//退後SJob到下一班,因為當班只run100片
				LocalDateTime nextShiftProcStartTime = null;
				LocalDateTime moveFromTime = firstCandidateSJob.getChangeStartTime()==null?firstCandidateSJob.getProcessStartTime():firstCandidateSJob.getChangeStartTime();
				for(Shift shift : newSJob.getShiftList()) {
					//if(shift.getIndex()==nextSJob.getShiftIndex()+1) {
					if(shift.getIndex()==pmJob.getShiftIndex()+1) { //PM的下一班 JoshLai@20190905+
						nextShiftProcStartTime = calcDateTimeByNum(mapParam, shift.getStartTimeIndex());
					}
				}
				doMoveJob(firstCandidateSJob, sJobDashboardList, nextShiftProcStartTime, moveFromTime, mapParam, false);
				
				//新增提前Run貨的SJob
				sJobDashboardList.add(newSJob);
				sortSJobDashboardList(sJobDashboardList);
				
			}//Job數量不足被提取的情況,ex:JobQty<100
			else {
				//PM後SJob第一位
				SJobDashboard firstCandidateSJob = afterPmSJobDashboardList.get(0);
				//PM後SJob最後一位
				SJobDashboard lastCandidateSJob = afterPmSJobDashboardList.get(afterPmSJobDashboardList.size()-1);
				
				//將PM後SJobList拉到PM當班
				doMoveJob(firstCandidateSJob, afterPmSJobDashboardList, pmEndTime, firstCandidateSJob.getChangeStartTime(), mapParam, false);
				
				//將更動後的候選SJob更新放回sJobDashboardList
				updateSJobDashboardList(afterPmSJobDashboardList, sJobDashboardList);
				
				//候選SJob最後一位的下一班
				SJobDashboard needToMoveSJob = getNextSJob(sJobDashboardList, lastCandidateSJob);
				if(needToMoveSJob!=null) {
					//候選SJob最後一位以後的SJob拉到PM下一班
					LocalDateTime moveFromTime = firstCandidateSJob.getChangeStartTime()==null?firstCandidateSJob.getProcessStartTime():firstCandidateSJob.getChangeStartTime();
					doMoveJob(needToMoveSJob, sJobDashboardList, pmShiftEndTime, moveFromTime, mapParam, false);
				}
			}
		}
		return afterPmSJobDashboardList;
	}
	
	public static List<SJobDashboard> calcNonScheduleAddQty(List<SJobDashboard> sJobDashboardList, Map<String, Object> mapParam, boolean isRun) {
		if(mapParam.get("nonschedule_add_qty") == null) {
			return sJobDashboardList;
		}
		sortSJobDashboardList(sJobDashboardList);
		
		if(sJobDashboardList.size() > 0) {
			List<SJobDashboard> extraJobList = new ArrayList<>();
			String prevoiusLineNo = sJobDashboardList.get(0).getLine();
			int previousShiftInx = 0;
			for(SJobDashboard sJob : sJobDashboardList) {
				boolean isAddExtraPlan = false;
				
				String sJobIdWithNoSharp = sJob.getJobId().substring(0, sJob.getJobId().indexOf("##"));
				int ppcCapa = 0;
				if(((Map)((Map)mapParam.get("capa_map")).get(sJobIdWithNoSharp))!=null)
					ppcCapa = (int) ((Map)((Map)mapParam.get("capa_map")).get(sJobIdWithNoSharp)).get("PPC_CAPA");
				
				//線別起始第一條,若起始班別為4第班
				if(!sJob.getLine().equals(prevoiusLineNo)) {
					if(ppcCapa>0 && sJob.getShiftIndex()==4 && sJob.getPlanQty()>=ppcCapa) {
						isAddExtraPlan = true;
					}
				}
				//同線別,若和前班別相差4個班
				else {
					if(ppcCapa>0 && sJob.getShiftIndex()-previousShiftInx >=4 && sJob.getPlanQty()>=ppcCapa) {
						isAddExtraPlan = true;
					}
				}
				
				if(isAddExtraPlan) {
					SJobDashboard extraJob = calcExtraSJobDashboard(sJob, mapParam);
					extraJobList.add(extraJob);
				}
				prevoiusLineNo = sJob.getLine();
				previousShiftInx = sJob.getShiftIndex();
			}
			sJobDashboardList.addAll(extraJobList);
			sortSJobDashboardList(sJobDashboardList);
		}
		return sJobDashboardList;
	}
	
	public static List<SJobDashboard> calcNonScheduleAndNoPPCplan(List<SJobDashboard> sJobDashboardList, Map<String, Object> mapParam, boolean isRun) {
		if(mapParam.get("nonschedule_add_qty") == null) {
			return sJobDashboardList;
		}
		
		if(sJobDashboardList.size() > 0) {
			List<SJobDashboard> extraJobList = new ArrayList<>();
			SJobDashboard previousSJob = sJobDashboardList.get(0);
			for(SJobDashboard sJob : sJobDashboardList) {
				//同條線且Job不為PM,NON-SCHEDULE才需考慮
				if(previousSJob.getLine().equals(sJob.getLine()) && !SPECIFIC_JOB_TYPE.contains(sJob.getJobType())) {
					long daysBetween = ChronoUnit.DAYS.between(previousSJob.getShiftDate(), sJob.getShiftDate());
					
					//檢查和前一個Job是否空檔沒生產
					LocalDateTime previousJobEndTime = previousSJob.getProcessEndTime(); 
					LocalDateTime currentJobStartTime = sJob.getChangeStartTime()==null ? sJob.getProcessStartTime() : sJob.getChangeStartTime();
					long secondsBetween = ChronoUnit.SECONDS.between(previousJobEndTime, currentJobStartTime);
					
					//前一天排NON_SCHEDULE&沒ppc排程，才要提早排量(不需扣量)
					if(daysBetween>=1 && previousSJob.getJobType().equals("NON-SCHEDULE") && secondsBetween>0) {
						SJobDashboard extraJob = calcExtraSJobDashboard(sJob, mapParam);
						extraJobList.add(extraJob);
					}
				}
				previousSJob = sJob;
			}
			
			sJobDashboardList.addAll(extraJobList);
			sortSJobDashboardList(sJobDashboardList);
		}
		return sJobDashboardList;
	}
	
	private static SJobDashboard calcExtraSJobDashboard(SJobDashboard sJob, Map<String, Object> mapParam) {
		int nonscheduleAddQty = Integer.parseInt((String)((Map)mapParam.get("nonschedule_add_qty")).get("in_value1"));
		
		SJobDashboard extraJob = new SJobDashboard();
		String extraJobId = sJob.getJobId().substring(0, sJob.getJobId().indexOf("##")) + "##0";
		
		long secondsBetween = ChronoUnit.SECONDS.between(sJob.getProcessStartTime(), sJob.getProcessEndTime());
		BigDecimal perQtySec = new BigDecimal(sJob.getForecastQty()).divide(new BigDecimal(secondsBetween), 10, RoundingMode.HALF_UP);
		BigDecimal bRunTime = perQtySec.multiply(new BigDecimal(nonscheduleAddQty)).setScale(0, RoundingMode.HALF_UP);
		
		String extraJobShift = "";
		float extraEndTimeIdx = 0;
		for(Shift shift : sJob.getShiftList()) {
			if(sJob.getShiftIndex()-1 == shift.getIndex()) {
				extraJobShift = shift.getShiftType().getCode();
				extraEndTimeIdx = shift.getEndTimeIndex();
			}
		}
		LocalDateTime extraJobProcEndTime = calcDateTimeByNum(mapParam, extraEndTimeIdx);
		LocalDateTime extraJobProcStartTime = extraJobProcEndTime.minusSeconds(bRunTime.longValue());
		
		LocalDate extraShiftDate = (LocalDate) calcShiftByProcStartTime(extraJobProcStartTime, mapParam).get("SHIFT_DATE");
		
		extraJob.setSite(sJob.getSite());
		extraJob.setFab(sJob.getFab());
		extraJob.setJobId(extraJobId);
		extraJob.setPlanQty(sJob.getPlanQty());
		extraJob.setForecastQty(nonscheduleAddQty);
		extraJob.setJobQty(nonscheduleAddQty);
		extraJob.setModelNo(sJob.getModelNo());
		extraJob.setPartNo(sJob.getPartNo());
		extraJob.setAssignShift(extraJobShift);
		extraJob.setShiftDate(extraShiftDate);
		extraJob.setShift(extraJobShift);
		extraJob.setLine(sJob.getLine());
		extraJob.setProcessStartTime(extraJobProcStartTime);
		extraJob.setProcessEndTime(extraJobProcEndTime);
		extraJob.setJobType(sJob.getJobType());
		return extraJob;
	}
	
	private static void updateSJobDashboardList(List<SJobDashboard> afterPmSJobDashboardList, List<SJobDashboard> sJobDashboardList) {
		for(Iterator<SJobDashboard> iter = afterPmSJobDashboardList.iterator(); iter.hasNext();) {
			SJobDashboard afterPmSJob = iter.next();
			String afterPmSJobId = afterPmSJob.getJobId();
			String afterPmSJobWoid = afterPmSJob.getWoId()==null ? "" : afterPmSJob.getWoId();
			
			for(Iterator<SJobDashboard> iter2 = sJobDashboardList.iterator(); iter2.hasNext();) {
				SJobDashboard sJob = iter2.next();
				String sJobId = sJob.getJobId();
				String sJobWoId = sJob.getWoId()==null ? "" : sJob.getWoId();
				
				if(afterPmSJobId.equals(sJobId) && afterPmSJobWoid.equals(sJobWoId)) {
					iter2.remove();
				}
			}
			if(afterPmSJob.getProcessStartTime()!=null && afterPmSJob.getProcessEndTime()!=null)
				sJobDashboardList.add(afterPmSJob);
		}
		sortSJobDashboardList(sJobDashboardList);
	}
	
	private static SJobDashboard getNextSJob(List<SJobDashboard> sJobDashboardList, SJobDashboard targetSJob) {
		SJobDashboard nextSJob = null;
		boolean isFind = false;
		for(Iterator<SJobDashboard> iter = sJobDashboardList.iterator(); iter.hasNext();) {
			SJobDashboard sJob = iter.next();
			if(targetSJob.equals(sJob))
				isFind = true;
			if(isFind && targetSJob.getLine().equals(sJob.getLine()) && !targetSJob.equals(sJob)) {
				return sJob;
			}
		}
		return nextSJob;
	}
	
	public static List<SJobDashboard> splitJobByChangeTime(List<SJobDashboard> sJobDashboardList, Map<String, Object> mapParam, boolean isSortByChangeEndTime, boolean isFirstSplit, boolean isRun) throws CloneNotSupportedException{
		List<SJobDashboard> listMap = new ArrayList<>();
		List<SJobDashboard> sJobDashboardListCopy = new ArrayList<>(sJobDashboardList);
		
		int iShiftDstart = Integer.parseInt((String) ((Map)mapParam.get("shift_d_start")).get("in_value1"));
		int iShiftEstart = Integer.parseInt((String) ((Map)mapParam.get("shift_e_start")).get("in_value1"));
		String sShiftDstart = addZeroForNum(String.valueOf(iShiftDstart),2)+"0000";//HHmmss
		String sShiftEstart = addZeroForNum(String.valueOf(iShiftEstart),2)+"0000";
		
		for(SJobDashboard sJob : sJobDashboardListCopy) {
			boolean isJobBeenSplitted = false;
			LocalDateTime changeStartTime = sJob.getChangeStartTime();
			LocalDateTime changeEndTime = sJob.getChangeEndTime();
			float changeDuration = sJob.getChangeDuration();
			BigDecimal b60 = new BigDecimal(60);
			BigDecimal bChangeDurationSec = new BigDecimal(changeDuration);
			bChangeDurationSec = bChangeDurationSec.multiply(b60).multiply(b60);//換成秒數
			
			LocalDateTime newStartTime = changeStartTime;
			LocalDateTime newEndTime = changeEndTime;
			for(int i=1; i<=bChangeDurationSec.intValue(); i++) { //換線時間改小數點 JoshLai@20190522+
				boolean isSplit = false;
				LocalDateTime tempChangeStartTime = changeStartTime.plusSeconds(i);
				String strChangeStartTime = formatterHHmmss.format(tempChangeStartTime);
				
//				if(changeStartTime.plusSeconds(i).getHour() == iShiftDstart) {
				if(strChangeStartTime.equals(sShiftDstart)) {
					isSplit = true;
					newEndTime = LocalDateTime.of(newStartTime.getYear(),
							newStartTime.getMonth(), newStartTime.getDayOfMonth(),
							iShiftDstart, 0, 0);
					if(newEndTime.isBefore(newStartTime)) {
						newEndTime = newEndTime.plusDays(1);
					}
				//新增!changeStartTime.plusHours(i).isEqual(changeEndTime) 若該Job結束時間剛好等於換班時間則不必切班 JoshLai@21090409
//				}else if(changeStartTime.plusSeconds(i).getHour() == iShiftEstart && !changeStartTime.plusSeconds(i).isEqual(changeEndTime)) {
				}else if(strChangeStartTime.equals(sShiftEstart) && !changeStartTime.plusSeconds(i).isEqual(changeEndTime)) {
					isSplit = true;
					newEndTime = LocalDateTime.of(newStartTime.getYear(),
							newStartTime.getMonth(), newStartTime.getDayOfMonth(),
							iShiftEstart, 0, 0);
				}
				
				if(isSplit) {
					SJobDashboard newSJob = (SJobDashboard) sJob.clone();
					newSJob.setProcessStartTime(newEndTime);
					newSJob.setProcessEndTime(newEndTime);
					newSJob.setChangeStartTime(newStartTime);
					newSJob.setChangeEndTime(newEndTime);
					
					if(isSortByChangeEndTime) {
						newSJob.setForecastQty(0);
						newSJob.setJobQty(0);
					}else if(newSJob.getAffectCapaQty() !=null && newSJob.getAffectCapaQty()==0 && isSortByChangeEndTime==false){
						newSJob.setForecastQty(0);
						newSJob.setJobQty(0);
					}
					float iChangeDuration = calcHoursBetweenDateTime(newStartTime, newEndTime);
					newSJob.setChangeDuration(iChangeDuration);
					
					listMap.add(newSJob);
					newStartTime = newEndTime;
					
					isJobBeenSplitted = true;
					
					//若切到最後一筆且原有的ChangeDuration%12==0,且是從AppMA第一次被切割,表示還有不需被切割的Job,需加回 JoshLai@20190905+
					if(i==bChangeDurationSec.intValue() && changeDuration>0 && changeDuration%12==0 && isFirstSplit) {
						isJobBeenSplitted = false;
						
						sJob.setChangeDuration(0);
						sJob.setChangeStartTime(null);
						sJob.setChangeEndTime(null);
						sJob.setChangeShift(null);
						sJob.setChangeKey(null);
						sJob.setChangeLevel(null);
					}
				}
				
				
				//將切完後尾段的部分加回去
				if(i==bChangeDurationSec.intValue() && isJobBeenSplitted) {
					if(newEndTime.isBefore(changeEndTime)) {
						SJobDashboard newSJob = (SJobDashboard) sJob.clone();
						newSJob.setChangeStartTime(newEndTime);
						newSJob.setChangeEndTime(changeEndTime);
						float iChangeDuration = calcHoursBetweenDateTime(newEndTime, changeEndTime);
						newSJob.setChangeDuration(iChangeDuration);
						newSJob.setJobQty(sJob.getForecastQty());
						
						listMap.add(newSJob);
					}
				}
			}
			
			//將不需被切割的Job加回去
			if(!isJobBeenSplitted) {
				listMap.add(sJob);
			}
			
			//檢查changeEndTime是否超出
			SJobDashboard lastJob = listMap.get(listMap.size()-1);
			Map<String, Object> mapChangeStart = calcShiftByProcAndChangeTime(lastJob.getProcessStartTime(), lastJob.getProcessEndTime(), lastJob.getChangeStartTime(), lastJob.getChangeDuration(), lastJob.getForecastQty(), mapParam);
			Map<String, Object> mapChangeEnd = calcShiftByProcAndChangeTime(lastJob.getProcessStartTime(), lastJob.getProcessEndTime(), lastJob.getChangeEndTime(), 0, 0, mapParam);
			String changeStartShiftType = (String) mapChangeStart.get("SHIFT_TYPE");
			String changeEndShiftType = (String) mapChangeEnd.get("SHIFT_TYPE");
			LocalDateTime shiftStartBegin = (LocalDateTime) mapChangeEnd.get("SHIFT_START_BEGIN");
			LocalDateTime shiftEndBegin = (LocalDateTime) mapChangeEnd.get("SHIFT_END_BEGIN");
			boolean isOverNextTime = false;
			if("D".equals(changeEndShiftType) && lastJob.getChangeEndTime()!=null)
				isOverNextTime = lastJob.getChangeEndTime().isAfter(shiftStartBegin);
			else if("N".equals(changeEndShiftType) && lastJob.getChangeEndTime()!=null)
				isOverNextTime = lastJob.getChangeEndTime().isAfter(shiftEndBegin);
			
			if(changeStartShiftType != null && !changeStartShiftType.equals(changeEndShiftType) && lastJob.getChangeEndTime()!=null && isOverNextTime) {
				newStartTime = lastJob.getChangeStartTime();
				newEndTime = lastJob.getChangeEndTime();
				if("D".equals(changeStartShiftType)) {
					newEndTime = LocalDateTime.of(newStartTime.getYear(),
							newStartTime.getMonth(), newStartTime.getDayOfMonth(),
							iShiftEstart, 0, 0);
				}else {
					newEndTime = LocalDateTime.of(newStartTime.getYear(),
							newStartTime.getMonth(), newStartTime.getDayOfMonth(),
							iShiftDstart, 0, 0);
					if(newEndTime.isBefore(sJob.getChangeStartTime())) {
						newEndTime = newEndTime.plusDays(1);
					}
				}
				
				SJobDashboard newSJob = (SJobDashboard) sJob.clone();
				newSJob.setProcessStartTime(newEndTime);
				newSJob.setProcessEndTime(newEndTime);
				newSJob.setChangeStartTime(newStartTime);
				newSJob.setChangeEndTime(newEndTime);
				newSJob.setForecastQty(0);
				newSJob.setJobQty(0);
				float iChangeDuration = calcHoursBetweenDateTime(newStartTime, newEndTime);
				newSJob.setChangeDuration(iChangeDuration);
				listMap.add(newSJob);
				
				//改變原有的changeInfo
				lastJob.setChangeStartTime(newEndTime);
				
				Comparator<SJobDashboard> comparator = Comparator.comparing(SJobDashboard::getProcessEndTime)
						.thenComparing(SJobDashboard::getChangeEndTime,
								Comparator.nullsFirst(Comparator.naturalOrder()));
				Collections.sort(listMap, comparator);
			}
		}
		
		sortSJobDashboardList(listMap);
		//按照順序修改JobSeq shiftDate, shiftType
		updateJobSeqAndShift(listMap, mapParam);
		
		return listMap;
	}
	
	public static List<SJobDashboard> combineJobByChangeTime(List<SJobDashboard> sJobDashboardList, Map<String, Object> mapParam, boolean isDebug){
		if(sJobDashboardList.size() > 0) {
			String prevoiusJobId = sJobDashboardList.get(0).getJobId().split("##")[0];
			String previousWoId = sJobDashboardList.get(0).getWoId() == null ? "" : sJobDashboardList.get(0).getWoId();
			int prevoiusPlanQty = sJobDashboardList.get(0).getPlanQty();
			LocalDateTime previousChangeStartTime = sJobDashboardList.get(0).getChangeStartTime()==null?sJobDashboardList.get(0).getProcessStartTime():sJobDashboardList.get(0).getChangeStartTime();
			LocalDateTime previousChangeEndTime = sJobDashboardList.get(0).getChangeEndTime()==null?sJobDashboardList.get(0).getProcessEndTime():sJobDashboardList.get(0).getChangeEndTime();
			String prevoiusChangeLvl = sJobDashboardList.get(0).getChangeLevel();
			LocalDate previousChangeShiftDate = sJobDashboardList.get(0).getChangeShiftDate();
			String previousChangeShift = sJobDashboardList.get(0).getChangeShift();
			SJobDashboard previousSJob = sJobDashboardList.get(0);
			int i = 0;
			
//			logger.debug("1st sJobDashboardList.size(): " + sJobDashboardList.size());
			
			Iterator<SJobDashboard> iter = sJobDashboardList.iterator();
			while(iter.hasNext()) {
				SJobDashboard sJob = iter.next();
				String woId = sJob.getWoId() == null ? "" : sJob.getWoId();
				LocalDateTime changeStartTime = sJob.getChangeStartTime()==null?sJob.getProcessStartTime():sJob.getChangeStartTime();
				LocalDateTime changeEndTime = sJob.getChangeEndTime()==null?sJob.getProcessEndTime():sJob.getChangeEndTime();
				
				if(previousChangeStartTime != null) {
					if(i > 0 && prevoiusJobId.equals(sJob.getJobId().split("##")[0])
							&& previousWoId.equals(woId)
							&& prevoiusPlanQty == sJob.getPlanQty() 
							&& sJob.getChangeLevel() != null && sJob.getChangeLevel().equals(prevoiusChangeLvl)
							&& sJob.getChangeDuration() > 0
							&& previousChangeEndTime.isEqual(changeStartTime)
							&& previousChangeShiftDate.isEqual(sJob.getChangeShiftDate())
							&& sJob.getChangeShift().equals(previousChangeShift)
							&& sJob.getChangeLevel().equals(prevoiusChangeLvl)) {
						
						previousSJob.setChangeEndTime(changeEndTime);
						previousSJob.setProcessStartTime(changeEndTime);
						previousSJob.setProcessEndTime(sJob.getProcessEndTime());
						LocalDateTime previousJobChangeStartTime = previousSJob.getChangeStartTime()==null?previousSJob.getProcessStartTime():previousSJob.getChangeStartTime();
						LocalDateTime previousJobChangeEndTime = previousSJob.getChangeEndTime()==null?previousSJob.getProcessEndTime():previousSJob.getChangeEndTime();
						float changeDuration = calcHoursBetweenDateTime(previousJobChangeStartTime, previousJobChangeEndTime);
						previousSJob.setChangeDuration(changeDuration);
						previousSJob.setJobQty(sJob.getJobQty());
						previousSJob.setForecastQty(sJob.getForecastQty());
						previousSJob.setShiftCountAfterChange(sJob.getShiftCountAfterChange());
						previousSJob.setAffectCapaPercent(sJob.getAffectCapaPercent());
						previousSJob.setAffectCapaQty(sJob.getAffectCapaQty());
						
						i--;
						iter.remove();
					}
				}
				prevoiusJobId = sJob.getJobId().split("##")[0];
				previousWoId = sJob.getWoId() == null ? "" : sJob.getWoId();
				prevoiusPlanQty = sJob.getPlanQty();
				previousChangeStartTime = sJob.getChangeStartTime();
				previousChangeEndTime = sJob.getChangeEndTime();
				previousChangeShiftDate = sJob.getChangeShiftDate();
				previousChangeShift = sJob.getChangeShift();
				prevoiusChangeLvl = sJob.getChangeLevel();
				previousSJob = sJobDashboardList.get(i);
				i++;
			}
			
			if(isDebug)
				logger.info("2nd sJobDashboardList.size(): " + sJobDashboardList.size() + " sJobDashboardList: " + sJobDashboardList);
		}
		return sJobDashboardList;
	}
	
	private static List<SJobDashboard> splitJobByProcessTime(List<SJobDashboard> sJobDashboardList, List<ModChange> cModChangeList, Map<String, Object> mapParam, boolean isDebug) throws CloneNotSupportedException{
		List<SJobDashboard> reviseListMap2 = new ArrayList<>(sJobDashboardList);
		List<SJobDashboard> extraSJobList = new ArrayList<>();
		
		int i=0;
		for(SJobDashboard sJob : reviseListMap2) {
			LocalDateTime processStartTime = sJob.getProcessStartTime();
			LocalDateTime processEndTime = sJob.getProcessEndTime();
			//取得該班別結束時間
			LocalDateTime shiftProcessEndTime = getShiftProcessEndTime(sJob.getShiftDate(), sJob.getShift(), sJob.getProcessEndTime(), mapParam);
			LocalDateTime newEndTime = processEndTime;
			
			if(processEndTime.isAfter(shiftProcessEndTime)) {
				newEndTime = shiftProcessEndTime;
				
				//修改原來那筆
				sJob.setProcessEndTime(newEndTime);
				
				//修改多出的那筆
				SJobDashboard newSJob = (SJobDashboard) sJob.clone();
				newSJob.setProcessStartTime(newEndTime);
				newSJob.setProcessEndTime(processEndTime);
				newSJob.setChangeDuration(0);
				newSJob.setChangeStartTime(null);
				newSJob.setChangeEndTime(null);
				newSJob.setChangeKey(null);
				newSJob.setChangeLevel(null);
				double affectCapaPercent = 1;
				int affectCapaQty = 0;
				
				double affectCapaPercentOldJob = 1;
				int affectCapaQtyOldJob = 0;
				
				if(sJob.getShiftCountAfterChange() != null 
						&& sJob.getShiftCountAfterChange() >0 && sJob.getShiftCountAfterChange() < 4) {
					newSJob.setShiftCountAfterChange(sJob.getShiftCountAfterChange()+1);
					for(ModChange cModChange : cModChangeList) {
						if(cModChange.getChangeKey().equals(sJob.getChangeKey())) {
							affectCapaPercentOldJob = Double.parseDouble(cModChange.getAffectCapaPercent1());
							affectCapaQtyOldJob = Integer.parseInt(cModChange.getAffectCapaQty1());
							
							int seq = newSJob.getShiftCountAfterChange();
							switch (seq) {
							case 2:
								affectCapaPercent = Double.parseDouble(cModChange.getAffectCapaPercent2());
								affectCapaQty = Integer.parseInt(cModChange.getAffectCapaQty2());
								break;
							case 3:
								affectCapaPercent = Double.parseDouble(cModChange.getAffectCapaPercent3());
								affectCapaQty = Integer.parseInt(cModChange.getAffectCapaQty3());
								break;
							case 4:
								affectCapaPercent = Double.parseDouble(cModChange.getAffectCapaPercent4());
								affectCapaQty = Integer.parseInt(cModChange.getAffectCapaQty4());
								break;
						}
						}
					}
				}else {
					newSJob.setAffectCapaPercent(null);
					newSJob.setAffectCapaQty(null);
				}
				newSJob.setAffectCapaPercent(affectCapaPercent);
				newSJob.setAffectCapaQty(affectCapaQty);
				
				//更新切斷數量
				if(sJob.getJobQty() > 0 && sJob.getForecastQty() > 0) {
					long secondsBetween = java.time.temporal.ChronoUnit.SECONDS.between(processStartTime, processEndTime);
					BigDecimal perQtySec = new BigDecimal(0);
					BigDecimal newQty = new BigDecimal(0);
					BigDecimal originJobQty = new BigDecimal(sJob.getJobQty());
					BigDecimal originForcastQty = new BigDecimal(sJob.getForecastQty());
					
					//修改JobQty
					perQtySec = new BigDecimal(sJob.getJobQty()).divide(new BigDecimal(secondsBetween), 10, BigDecimal.ROUND_HALF_UP);
					long jobSecondsBetween = java.time.temporal.ChronoUnit.SECONDS.between(sJob.getProcessStartTime(), sJob.getProcessEndTime());
					newQty = perQtySec.multiply(new BigDecimal(jobSecondsBetween)).setScale(0, BigDecimal.ROUND_HALF_UP);
					sJob.setJobQty(newQty.intValue());
					
					//修改ForecastQty
					perQtySec = new BigDecimal(sJob.getForecastQty()).divide(new BigDecimal(secondsBetween), 10, BigDecimal.ROUND_HALF_UP);
					newQty = perQtySec.multiply(new BigDecimal(jobSecondsBetween)).setScale(0, BigDecimal.ROUND_HALF_UP);
					sJob.setForecastQty(newQty.intValue());
					
					//若該筆Job為最後一筆且設定affectCapaQty則直接給定值 JoshLai@20190731+
					if(affectCapaQtyOldJob > 0) {
						sJob.setJobQty(affectCapaQtyOldJob);
						sJob.setForecastQty(affectCapaQtyOldJob);
						originJobQty = new BigDecimal(sJob.getJobQty());
						newSJob.setJobQty(sJob.getForecastQty());
					}else {
						BigDecimal newJobQty = originJobQty.subtract(new BigDecimal(sJob.getJobQty()));
						newSJob.setJobQty(newJobQty.intValue());
					}
					
					BigDecimal newJobForecastQty = originForcastQty.subtract(new BigDecimal(sJob.getForecastQty()));
					newSJob.setForecastQty(newJobForecastQty.intValue());
				}
				extraSJobList.add(newSJob);
			}
			i++;
		}
		
		Set<String> exChangeId = new HashSet<>();
		for(SJobDashboard sJob1 : reviseListMap2) {
			String sJob1WoId = sJob1.getWoId()==null?"":sJob1.getWoId();
			for(SJobDashboard sJob2 : extraSJobList) {
				String sJob2WoId = sJob2.getWoId()==null?"":sJob2.getWoId();
				if(sJob1.getAffectCapaPercent()!=null&&sJob1.getAffectCapaPercent()!=1
						&& sJob1.getJobId().equals(sJob2.getJobId())
						&& sJob1WoId.equals(sJob2WoId)) {
					exChangeId.add(sJob2.getJobId());
				}
			}
		}
		
		for(String id : exChangeId) {
			List<SJobDashboard> sameJobList = new ArrayList<>();
			id = id.split("##")[0];
			for(SJobDashboard job : extraSJobList) {
				String jobId = job.getJobId().split("##")[0];
				if(id.equals(jobId)) {
					sameJobList.add(job);
				}
			}
			if(sameJobList.size() > 0) {
				SJobDashboard firstJob = sameJobList.get(0);
				SJobDashboard lastJob = sameJobList.get(sameJobList.size()-1);
				int firstJobForecastQty = firstJob.getForecastQty();
				int lastJobForecastQty = lastJob.getForecastQty();
				firstJob.setForecastQty(lastJobForecastQty);
				lastJob.setForecastQty(firstJobForecastQty);
			}
		}
		if(isDebug) {
			logger.info(extraSJobList.size() + " extraSJobList-2: " + extraSJobList);
		}
		
		Comparator<SJobDashboard> comparator2 = Comparator.comparing(SJobDashboard::getLine)
				.thenComparing(SJobDashboard::getShiftDate)
				.thenComparing(SJobDashboard::getProcessStartTime);
		Collections.sort(reviseListMap2, comparator2);
		
		if(isDebug) {
			logger.info(reviseListMap2.size() +" before addAll reviseListMap2: " + reviseListMap2);
			logger.info(extraSJobList.size() +" extraSJobList: " + extraSJobList);
		}
		
		reviseListMap2.addAll(extraSJobList);
		
		//將切出來的sJob合併回去,排序後修改jobSeq
		sortSJobDashboardList(reviseListMap2);
		updateJobSeqAndShift(reviseListMap2, mapParam);
		
		if(isDebug)
			logger.info(reviseListMap2.size() +" after addAll reviseListMap2: " + reviseListMap2);
		return reviseListMap2;
	}
	
	public static List<SJobDashboard> combineJobByProcessTime(List<SJobDashboard> reviseListMap2, Map<String, Object> mapParam, boolean isDebug){
		List<SJobDashboard> removeList = new ArrayList<>();
		if(reviseListMap2.size() > 0) {
			int i=0;
			String prevoiusJobId = reviseListMap2.get(0).getJobId().split("##")[0];
			LocalDate previousShiftDate = reviseListMap2.get(0).getShiftDate();
			String previousShift = reviseListMap2.get(0).getShift();
			String previousJobType = reviseListMap2.get(0).getJobType();
			String previousWoId = reviseListMap2.get(0).getWoId() == null ? "" : reviseListMap2.get(0).getWoId();
			String previousPartNo = reviseListMap2.get(0).getPartNo() == null ? "" : reviseListMap2.get(0).getPartNo();
			String previousModelNo = reviseListMap2.get(0).getModelNo() == null ? "" : reviseListMap2.get(0).getModelNo();
			int previousPlanQty = reviseListMap2.get(0).getPlanQty();
			LocalDateTime previousProcessEndTime = reviseListMap2.get(0).getProcessEndTime();
			SJobDashboard previousSJob = reviseListMap2.get(0);
			i = 0;
//			if(isDebug)
//				logger.debug("1st reviseListMap2.size(): " + reviseListMap2.size());
			
			Iterator<SJobDashboard> iter = reviseListMap2.iterator();
			while(iter.hasNext()) {
				SJobDashboard sJob = iter.next();
				String woId = sJob.getWoId() == null ? "" : sJob.getWoId();
				String partNo = sJob.getPartNo() == null ? "" : sJob.getPartNo();
				String modelNo = sJob.getModelNo() == null ? "" : sJob.getModelNo();
				
//				logger.debug("jobId: " +sJob.getJobId()+ " prevoiusWoId: " + previousWoId + " woId: " + woId);
				
				if(i > 0 && prevoiusJobId.equals(sJob.getJobId().split("##")[0])
						&& previousShiftDate.isEqual(sJob.getShiftDate())
						&& previousShift.equals(sJob.getShift())
						&& previousJobType.equals(sJob.getJobType())
						&& previousWoId.equals(woId)
						&& previousPartNo.equals(partNo)
						&& previousModelNo.equals(modelNo)
						&& previousPlanQty == sJob.getPlanQty() 
						&& previousProcessEndTime.isEqual(sJob.getProcessStartTime())
						&& sJob.getChangeDuration() == 0 && previousSJob.getChangeDuration()==0) {
					
					previousSJob.setProcessEndTime(sJob.getProcessEndTime());
					
					//避免有換線跟沒換線的Job混在一起 JoshLai@20190605+
//					if(sJob.getChangeLevel()!=null) {
						previousSJob.setChangeLevel(sJob.getChangeLevel());
						previousSJob.setChangeDuration(sJob.getChangeDuration());
						previousSJob.setChangeStartTime(sJob.getChangeStartTime());
						previousSJob.setChangeEndTime(sJob.getChangeEndTime());
						previousSJob.setChangeShift(sJob.getChangeShift());
						previousSJob.setChangeShiftDate(sJob.getChangeShiftDate());
						previousSJob.setChangeKey(sJob.getChangeKey());
//					}
					int forecastQty = previousSJob.getForecastQty() + sJob.getForecastQty();
					previousSJob.setForecastQty(forecastQty);
					int jobQty = previousSJob.getJobQty() + sJob.getJobQty();
					previousSJob.setJobQty(jobQty);
					previousSJob.setShiftCountAfterChange(sJob.getShiftCountAfterChange());
					previousSJob.setAffectCapaPercent(sJob.getAffectCapaPercent());
					previousSJob.setAffectCapaQty(sJob.getAffectCapaQty());
					removeList.add(sJob);
					i--;
					iter.remove();
				}
				
				prevoiusJobId = sJob.getJobId().split("##")[0];
				previousShiftDate = sJob.getShiftDate();
				previousShift = sJob.getShift();
				previousJobType = sJob.getJobType();
				previousWoId = sJob.getWoId() == null ? "" : sJob.getWoId();
				previousPartNo = sJob.getPartNo() == null ? "" : sJob.getPartNo();
				previousModelNo = sJob.getModelNo() == null ? "" : sJob.getModelNo();
				previousPlanQty = sJob.getPlanQty();
				previousProcessEndTime = sJob.getProcessEndTime();
				previousSJob = reviseListMap2.get(i);
				i++;
			}
			
			if(isDebug) {
				logger.info("2nd reviseListMap2.size(): " + reviseListMap2.size() + " reviseListMap2: " + reviseListMap2);
				logger.info(removeList.size() + " removeList: " + removeList);
			}
		}
		
		sortSJobDashboardList(reviseListMap2);
		updateJobSeqAndShift(reviseListMap2, mapParam);
		
		if(isDebug)
			logger.info(reviseListMap2.size() + " before changeCrossSetting: " +  reviseListMap2);
		
		return reviseListMap2;
	}
	
	private static void updateJobSeqAndShift(List<SJobDashboard> listMap, Map<String, Object> mapParam) {
		if(listMap.size() > 0) {
			String previousJobId = listMap.get(0).getJobId();
			int previousPlanQty = listMap.get(0).getPlanQty();
			int idx = 1;
			for(SJobDashboard sJob : listMap) {
				String[] jobId = new String[2];
				jobId[0] = sJob.getJobId().substring(0, sJob.getJobId().lastIndexOf("##"));
				jobId[1] = sJob.getJobId().substring(sJob.getJobId().lastIndexOf("##")+2, sJob.getJobId().length());
				
				String[] previousJobIds = new String[2];
				previousJobIds[0] = previousJobId.substring(0, previousJobId.lastIndexOf("##"));
				previousJobIds[1] = previousJobId.substring(previousJobId.lastIndexOf("##")+2, previousJobId.length());
				
				if(previousPlanQty != sJob.getPlanQty() || !previousJobIds[0].equals(jobId[0])) {
					idx = 1;
				}
				
				if(jobId[1].length() < 6) {
					String renameJobId = jobId[0] + "##" +idx;
					sJob.setJobId(renameJobId);
				}else if(jobId[1].length() == 6){
					Random rand = new Random();
					int randNum = 100000 + rand.nextInt(900000); //取得6位數長度亂數
					String renameJobId = jobId[0] + "##" +randNum;
					sJob.setJobId(renameJobId);
				}
				
				//切完班可能造成shiftDate,shiftType更動,再一次修正
				//processStartTime
				Map<String, Object> mapShift = calcShiftByProcAndChangeTime(sJob.getProcessStartTime(), sJob.getProcessEndTime(), sJob.getChangeStartTime(), sJob.getChangeDuration(), sJob.getForecastQty(), mapParam);
				LocalDate shiftDate = (LocalDate) mapShift.get("SHIFT_DATE");
				String shiftType = (String) mapShift.get("SHIFT_TYPE");
				sJob.setShiftDate(shiftDate);
				sJob.setShift(shiftType);
				
				//changeStartTime
				mapShift = calcShiftByProcStartTime(sJob.getChangeStartTime(), mapParam);
				shiftDate = (LocalDate) mapShift.get("SHIFT_DATE");
				shiftType = (String) mapShift.get("SHIFT_TYPE");
				sJob.setChangeShiftDate(shiftDate);
				sJob.setChangeShift(shiftType);
				
				previousPlanQty = sJob.getPlanQty();
				previousJobId = sJob.getJobId();
				idx++;
			}
		}
	}
	
	public static float calcHoursBetweenDateTime(LocalDateTime start, LocalDateTime end) {
		BigDecimal b60 = new BigDecimal("60");
		long secondsBetween = java.time.temporal.ChronoUnit.SECONDS.between(start, end);
		BigDecimal bSecondsBetween = new BigDecimal(secondsBetween);
		BigDecimal bMinutesBetween = bSecondsBetween.divide(b60, 10, RoundingMode.HALF_UP);
		BigDecimal bHoursBetween = bMinutesBetween.divide(b60, 1, RoundingMode.HALF_UP);
		return bHoursBetween.floatValue();
	}
	
	public static List<Woxx> substarctWomIQty(List<Woxx> woxxList, List<Object> historicalList, Map<String, Object> mapParam){
		for(Object job : historicalList) {
			String jobWoId = null;
			BigDecimal bJobForecastQty = new BigDecimal(0);
			LocalDate jobShiftDate = null;
			
			if(job instanceof Adjustment) {
				jobWoId = ((Adjustment)job).getAdjustmentId().getWoId();
				bJobForecastQty = new BigDecimal(((Adjustment) job).getPlanQty());
				jobShiftDate = ((Adjustment) job).getAdjustmentId().getShiftDate();
			}else if(job instanceof RJobDashboard) {
				jobWoId = ((RJobDashboard) job).getWoId();
				bJobForecastQty = new BigDecimal(((RJobDashboard) job).getForecastQty());
				jobShiftDate = ((RJobDashboard) job).getShiftDate();
			}
			
			for(Woxx woxx : woxxList) {
				String woId = woxx.getWoxxId().getWoId();
				String woVersion = woxx.getWoxxId().getWoVersion();
				LocalDate woVersionDate = woVersionToLocalDate(woVersion);
				
				BigDecimal bWoQty = new BigDecimal(woxx.getWoQty());
				BigDecimal bSubstractWoQty = new BigDecimal(0);
				
				//從woVersion到最靠近planStartDate之間的工單數量需扣除實際已生產數量(historicalList)
				if((woVersionDate.isEqual(jobShiftDate) || jobShiftDate.isAfter(woVersionDate))
						&& woId.equals(jobWoId)
						) {
					bSubstractWoQty = bWoQty.subtract(bJobForecastQty);
					woxx.setWoQty(bSubstractWoQty.intValue());
				}
			}
		}
		return woxxList;
	}
	
	public static LocalDate woVersionToLocalDate(String woVersion) {
		LocalDate woVersionDate = null;
		if(woVersion != null) {
			int woVersionYear = Integer.parseInt(woVersion.substring(0, 4));
			int woVersionMonth = Integer.parseInt(woVersion.substring(4, 6));
			int woVersionDay = Integer.parseInt(woVersion.substring(6, 8));
			woVersionDate = LocalDate.of(woVersionYear, woVersionMonth, woVersionDay);
		}
		return woVersionDate;
	}
	
	//匹配工單
	public static List<SJobDashboard> splitJobByWom(List<SJobDashboard> listPlanResult, List<Object> historicalList, List<Woxx> woxxList, Map<String, Object> mapParam) throws CloneNotSupportedException {
		if(mapParam.get("is_match_wo")==null) {
			return listPlanResult;
		}
		List<SJobDashboard> listMap = new ArrayList<>();
		String isMatchWo = String.valueOf(((Map)mapParam.get("is_match_wo")).get("in_value1"));
		for(SJobDashboard job : listPlanResult) {
			if("PROD".equals(job.getJobType()) && "Y".equalsIgnoreCase(isMatchWo)) {
				SJobDashboard oldJob = (SJobDashboard) job.clone();
				String jobSite = oldJob.getSite();
				String jobFab = oldJob.getFab();
				String jobPartNo = oldJob.getPartNo();
				String jobGrade = oldJob.getGrade();
				String jobLine = oldJob.getLine();
				
				LocalDate planStart = (LocalDate) ((Map)mapParam.get("plan_start_date")).get("in_value1");
				int iShiftDstart = Integer.parseInt((String) ((Map)mapParam.get("shift_d_start")).get("in_value1"));
				
				double jobStartTime = calcHoursFromStartDateTime(planStart, iShiftDstart, job.getProcessStartTime());
				LocalDateTime jobStartDateTime = calcDateTimeByNum(mapParam, jobStartTime);
				LocalDateTime before7Days = jobStartDateTime.minusDays(7);
				
				//從歷史資料取得相同site,fab,part no, grade最後的工單, historicalList為反序排列shiftDate DESC
				String lastWoId = null;
				for(Object historyJob : historicalList) {
					String historySite = null;
					String historyFab = null;
					String historyPartNo = null;
					String historyGrade = null;
					String historyWoId = null;
					if(historyJob instanceof Adjustment) {
						historySite = ((Adjustment) historyJob).getAdjustmentId().getSite();
						historyFab = ((Adjustment) historyJob).getAdjustmentId().getFab();
						historyPartNo = ((Adjustment) historyJob).getPartNo();
						historyGrade = ((Adjustment) historyJob).getAdjustmentId().getGrade();
						historyWoId = ((Adjustment) historyJob).getAdjustmentId().getWoId();
					}else if(historyJob instanceof RJobDashboard){
						historySite = ((RJobDashboard) historyJob).getSite();
						historyFab = ((RJobDashboard) historyJob).getFab();
						historyPartNo = ((RJobDashboard) historyJob).getPartNo();
						historyGrade = ((RJobDashboard) historyJob).getGrade();
						historyWoId = ((RJobDashboard) historyJob).getWoId();
					}
					if(historySite.equals(jobSite) && jobFab.equals(historyFab) && historyPartNo.equals(jobPartNo) 
							&& historyGrade.equals(jobGrade)){
						lastWoId = historyWoId;
						break;
					}
				}
				
				//有找到七天內的woId
				Map<String, Boolean> mapFindWoId = isSplitJobByWomLoop(woxxList, oldJob, before7Days, lastWoId);
				boolean isFindLastWoId = mapFindWoId.get("IS_FIND_LAST_WOID")!=null ? mapFindWoId.get("IS_FIND_LAST_WOID") : false;
				
				//沒找到七天內的woId
				if(isFindLastWoId == false) {
					mapFindWoId = isSplitJobByWomLoop(woxxList, oldJob, before7Days, null);
					boolean isFindLastWoIdInDays = mapFindWoId.get("IS_FIND_WOID_IN_DAYS")!=null ? mapFindWoId.get("IS_FIND_WOID_IN_DAYS") : false;
					
					//沒找到歷史資料的WoId也沒找到七天內的WoId,則直接找前一班的woId JoshLai@20190531+
					if(isFindLastWoIdInDays == false) {
						for(Object historyJob : historicalList) {
							String historySite = null;
							String historyFab = null;
							String historyLine = null;
							String historyWoId = null;
							String historyPartNo = null;
							String historyGrade = null;
							LocalDate historyShiftDate = null;
							if(historyJob instanceof Adjustment) {
								historySite = ((Adjustment) historyJob).getAdjustmentId().getSite();
								historyFab = ((Adjustment) historyJob).getAdjustmentId().getFab();
								historyLine = ((Adjustment) historyJob).getAdjustmentId().getLine();
								historyWoId = ((Adjustment) historyJob).getAdjustmentId().getWoId();
								historyPartNo = ((Adjustment) historyJob).getPartNo();
								historyGrade = ((Adjustment) historyJob).getAdjustmentId().getGrade();
								historyShiftDate = ((Adjustment) historyJob).getAdjustmentId().getShiftDate();
							}else if(historyJob instanceof RJobDashboard){
								historySite = ((RJobDashboard) historyJob).getSite();
								historyFab = ((RJobDashboard) historyJob).getFab();
								historyLine = ((RJobDashboard) historyJob).getLine();
								historyWoId = ((RJobDashboard) historyJob).getWoId();
								historyPartNo = ((RJobDashboard) historyJob).getPartNo();
								historyGrade = ((RJobDashboard) historyJob).getGrade();
								historyShiftDate = ((RJobDashboard) historyJob).getShiftDate();
							}
							
							if(historyWoId != null && !"-".equals(historyWoId) && jobLine.equals(historyLine)
									&& historySite.equals(jobSite) 
									&& jobFab.equals(historyFab)
									&& historyPartNo.equals(jobPartNo) 
									&& historyGrade.equals(jobGrade)) {
								
								//工單不能 跨月使用  JoshLai@20190805+
								int iWoMonth = calcWoMoth(historyWoId);
								if(iWoMonth >= oldJob.getShiftDate().getMonthValue()){
									oldJob.setWoId(historyWoId);
									break;
								}
							}
						}
					}
				}
				
				listMap.add(oldJob);
			}else {
				listMap.add(job);
			}
		}
		return listMap;
	}
	
	private static Map<String, Boolean> isSplitJobByWomLoop(List<Woxx> woxxList, SJobDashboard oldJob, LocalDateTime before7Days, String lastWoId) {
		//mark by avonchung 20190507 匹配工單取消扣量
		//int jobForecastQty = 0;
		//int i=0;
		//Woxx in JPA order by site ASC, part_no ASC,start_date ASC //update 20190507
		Map<String, Boolean> mapIsFindWoId = new HashMap<String, Boolean>();
		boolean isFindLastWoId = false;
		String jobSite = oldJob.getSite();
		String jobFab = oldJob.getFab();
		String jobPartNo = oldJob.getPartNo();
		String jobGrade = oldJob.getGrade();
		
		for(Woxx woxx : woxxList) {
			String woxxSite = woxx.getWoxxId().getSite();
			String woxxFab = woxx.getWoxxId().getFab();
			String woxxPartNo = woxx.getPartNo();
			String woxxDefaultFinalGrade = woxx.getDefaultFinalGrade();
			String woId = woxx.getWoxxId().getWoId();
			LocalDateTime woStarDateTime = woxx.getStartDate();
//			int woxxQty = woxx.getWoQty();
//			jobForecastQty = oldJob.getForecastQty();
			int woMonth = calcWoMoth(woId);
			
			
			//若最後一筆工單亦在有效日期內，即優先匹配此工單 //update 20190507
			//要改看工單倒数第4码来判断工单的月份,A,B,C分別代表10,11,12月 JoshLai@20190806+
			if(woId.equals(lastWoId) && (woStarDateTime.isEqual(before7Days) || woStarDateTime.isAfter(before7Days))
					&& woMonth>=oldJob.getShiftDate().getMonthValue()) { //S06 3B匹配工單(7天內的工單或延用前一班的工單) 而且必須當月(工單不能 跨月使用 ) JoshLai@20190805+
				oldJob.setWoId(woId);
				isFindLastWoId = true;
				mapIsFindWoId.put("IS_FIND_LAST_WOID", isFindLastWoId);//從歷史資料找到WoId
				break;
			}
			
			//再按工單的開單日期(start_date)由早到晚順序匹配//update 20190507
			if (lastWoId==null && woxxSite.equals(jobSite) && jobFab.equals(woxxFab) && woxxPartNo.equals(jobPartNo) 
					&& jobGrade!=null && jobGrade.equals(woxxDefaultFinalGrade)
					&& (woStarDateTime.isEqual(before7Days) || woStarDateTime.isAfter(before7Days))
					&& woMonth>=oldJob.getShiftDate().getMonthValue() //工單不能 跨月使用  JoshLai@20190805+
					//&& woStarDateTime.isBefore(jobStartDateTime)) //mark by avonchung 20190417
					//&& woxxQty > 0 //mark by avonchung 20190507 匹配工單取消扣量
					) {
				
				//add by avonchung 20190507 工單在有效日期內即可匹配
				oldJob.setWoId(woId);
				mapIsFindWoId.put("IS_FIND_WOID_IN_DAYS", isFindLastWoId); //沒找到歷史WoId則找七天內匹配的WoId
				break;
				//mark by avonchung 20190507 匹配工單取消扣量
//					if((woxxQty-jobForecastQty) > 0) {
//						int	newWoxxIQty = woxxQty - jobForecastQty;
//						logger.debug("newWoxxIQty: " + newWoxxIQty + " = " + " woxxIQty: " + woxxQty + " jobForecastQty: " + jobForecastQty);
//						woxx.setWoQty(newWoxxIQty);
//						oldJob.setWoId(woId);
//						break;
//					}else {
//						int substractJobQty = jobForecastQty - woxxQty;
//						SJobDashboard newJob;
//						try {
//							newJob = (SJobDashboard) oldJob.clone();
//							newJob.setForecastQty(woxxQty);
//							newJob.setJobQty(woxxQty);
//							newJob.setWoId(woId);
//							
//							woxx.setWoQty(woxx.getWoQty() - woxxQty);
//							logger.debug("after substract woIqty: " +woxx.getWoQty()
//										+ " jobForecastQty: " + jobForecastQty
//										+ " substractJobQty: " + substractJobQty);
//							
//							logger.debug("newJobId: " +newJob.getJobId()+ "new job forecastQty: " + substractJobQty
//											+ " oldJob.getForecastQty(): " + oldJob.getForecastQty());
//							
//							double oldJobStartTime = calcHoursFromStartDateTime(planStart, iShiftDstart, oldJob.getProcessStartTime());
//							logger.debug("oldJobStartTime: " + oldJobStartTime + " oldJob.getProcessStartTime(): " + oldJob.getProcessStartTime());
//							Duration duration = Duration.between(oldJob.getProcessStartTime(), oldJob.getProcessEndTime());
//							double dOldJobDuration = duration.getSeconds()/60/60.0;
//							logger.debug("dOldJobDuration: " +dOldJobDuration);
//							BigDecimal startTime = new BigDecimal(oldJobStartTime);
//							BigDecimal endTime = new BigDecimal(oldJobStartTime).add(new BigDecimal(dOldJobDuration));
//							BigDecimal bDuration = endTime.subtract(startTime);
//							
//							BigDecimal bCapa = bDuration.divide(new BigDecimal(jobForecastQty), 5, BigDecimal.ROUND_HALF_UP);
//							BigDecimal bNewJobProcessTime = bCapa.multiply(new BigDecimal(woxxQty));
//							logger.debug("startTime: " + startTime + " endTime: " + endTime + " jobForecastQty: " + jobForecastQty + " bDuration: " + bDuration.doubleValue()
//										+ " bNewJobProcessTime: " + bNewJobProcessTime.doubleValue());
//							
//							newJob.setProcessStartTime(calcDateTimeByNum(mapParam, startTime.doubleValue()));
//							BigDecimal newJobEndTime = startTime.add(bNewJobProcessTime);
//							newJob.setProcessEndTime(calcDateTimeByNum(mapParam, newJobEndTime.doubleValue()));
//							
//							listMap.add(newJob);
//							
//							oldJob.setForecastQty(substractJobQty);
//							oldJob.setJobQty(substractJobQty);
//							oldJob.setProcessStartTime(newJob.getProcessEndTime());
//							oldJob.setChangeShift(null);
//							oldJob.setChangeShiftDate(null);
//							oldJob.setChangeLevel(null);
//							oldJob.setChangeDuration(0);
//							oldJob.setChangeStartTime(null);
//							oldJob.setChangeEndTime(null);
//							oldJob.setShiftCountAfterChange(null);
//							oldJob.setAffectCapaPercent(null);
//							oldJob.setAffectCapaQty(null);
//							oldJob.setChangeKey(null);
//							logger.debug(listMap.size() + " split womm listMap: " + listMap);
//						} catch (CloneNotSupportedException e) {
//							StringWriter errors = new StringWriter();
//			    			e.printStackTrace(new PrintWriter(errors));
//			    			logger.error(errors.toString());
//			    			e.printStackTrace();
//						}
//					}
			}
//				i++;
		}
		return mapIsFindWoId;
	}
	
	private static int calcWoMoth(String woId) {
		int iWoMonth = -1;
		String woIdAt4 = woId.substring(woId.length()-4, woId.length()-3).toUpperCase(); //倒数第4码来判断工单的月份
		if(StringUtils.isNumeric(woIdAt4)) {
			iWoMonth = Integer.parseInt(woIdAt4);
		}else {
			switch(woIdAt4) {
				case "A":
					iWoMonth = 10;
					break;
				case "B":
					iWoMonth = 11;
					break;
				case "C":
					iWoMonth = 12;
					break;
			}
		}
		return iWoMonth;
	}
	
	//提早排量到白班 JoshLai@20190801+
	public static List<SJobDashboard> calcPullToFullDShift(List<SJobDashboard> listPlanResult, List<Plan> rJobPlanList, Map<String, Object> mapParam){
		Map<String, Object> mapParamFollowPpcPlan = (Map<String, Object>) mapParam.get("follow_ppc_plan");
		if(mapParamFollowPpcPlan==null 
				|| mapParamFollowPpcPlan.get("in_value3")==null
				|| !"capabyshift".equals((String)mapParamFollowPpcPlan.get("in_value3"))) 
			return listPlanResult;
		
		Map<String, Object> capaMap = (Map<String, Object>) mapParam.get("capa_map");
		for(int i=0; i<listPlanResult.size(); i++) {
			SJobDashboard sJob = listPlanResult.get(i);
			String sJobIdWithNoSharp = sJob.getJobId().substring(0, sJob.getJobId().indexOf("##"));
			Map<String, Object> mapCapa = (Map)capaMap.get(sJobIdWithNoSharp);
			
			if(mapCapa!=null) {
				BigDecimal bForecatQty = new BigDecimal(sJob.getForecastQty());
				BigDecimal bAffectCapaQty = new BigDecimal(0);
				if(sJob.getAffectCapaQty()!=null && sJob.getAffectCapaQty() > 0) {
					bAffectCapaQty = new BigDecimal(sJob.getAffectCapaQty());
				}
				
				//尚有下一筆SJobDashboard可扣量
				if(i+1 < listPlanResult.size()) {
					boolean isProcess = false;
					
					//若為線上最後一筆則拿從ppc plan扣量
					SJobDashboard lastSJobOnLine = null;
					Map<String, Object> mapNextSJob = getNextSJobByLinePartNo(listPlanResult, sJob, i);
					SJobDashboard nextSJob = null;
					int iNextIndex = -1;
					if(mapNextSJob!=null) {
						nextSJob = (SJobDashboard) mapNextSJob.get("JOB");
						iNextIndex = (int) mapNextSJob.get("INDEX");
					}
					if(!listPlanResult.get(i+1).getLine().equals(sJob.getLine())) {
						lastSJobOnLine = sJob;
					}
					
					//若為尚有剩餘時間
					Shift shift = sJob.getShiftList().get(sJob.getShiftIndex());
					LocalDateTime shiftEndTime = calcDateTimeByNum(mapParam, shift.getEndTimeIndex());
					if((sJob.getProcessEndTime().isBefore(shiftEndTime))) {
						isProcess = true;
					}
					
					//若有換線影響by片,則計算原processEndTime
					if(bAffectCapaQty.compareTo(BigDecimal.ZERO)>0 
							&& bAffectCapaQty.compareTo(bForecatQty)>0
							&& lastSJobOnLine==null) { //後一筆為同條線SJob
						isProcess = true;
					}
					
					//最後一天shiftDate+1有ppc plan qty有量的話, min( 換線後影響qty, D capa or D+N capa)
					Plan nextShiftDatePlan = getPlanByLinePartNoGradeShiftDate(rJobPlanList, sJob);
					if(lastSJobOnLine!=null && nextShiftDatePlan!=null) {
						nextSJob = new SJobDashboard();
						nextSJob.setLine(nextShiftDatePlan.getLine());
						nextSJob.setPartNo(nextShiftDatePlan.getPartNo());
						BigDecimal bNextShiftDatePlanFabPcCapa = new BigDecimal(nextShiftDatePlan.getCapa().getFabPcCapa());
						bNextShiftDatePlanFabPcCapa = bNextShiftDatePlanFabPcCapa.divide(new BigDecimal(2), 0, RoundingMode.HALF_UP);
						nextSJob.setForecastQty(bNextShiftDatePlanFabPcCapa.intValue());
						
						int iShiftDstart = Integer.parseInt((String) ((Map)mapParam.get("shift_d_start")).get("in_value1"));
						int iShiftEstart = Integer.parseInt((String) ((Map)mapParam.get("shift_e_start")).get("in_value1"));
						LocalDateTime procTime = nextShiftDatePlan.getShiftDate().atTime(iShiftDstart, 0);
						nextSJob.setProcessStartTime(procTime);
						procTime = nextShiftDatePlan.getShiftDate().atTime(iShiftEstart, 0);
						nextSJob.setProcessEndTime(procTime);
						isProcess = true;
					}
					
					if(isProcess && nextSJob!=null) {
						//若為相同part no且後一天有量，則允許pull, 
						if(nextSJob.getLine().equals(sJob.getLine()) 
								&& nextSJob.getPartNo().equals(sJob.getPartNo())
								&& nextSJob.getForecastQty()>0) {
						
							//換算capa
							BigDecimal bNextSJobForecatQty = new BigDecimal(nextSJob.getForecastQty()); //下一班數量
							BigDecimal bSecondsBetween = new BigDecimal(ChronoUnit.SECONDS.between(nextSJob.getProcessStartTime(), nextSJob.getProcessEndTime()));
							BigDecimal bPerSecQty = bNextSJobForecatQty.divide(bSecondsBetween, 10, RoundingMode.HALF_UP);
							
							//補滿到該班所需時間
							bSecondsBetween = new BigDecimal(ChronoUnit.SECONDS.between(sJob.getProcessEndTime(), shiftEndTime));
							
							//fab pc capa
							BigDecimal bFabPcCapa = new BigDecimal((int)mapCapa.get("FAB_PC_CAPA"))
									.divide(new BigDecimal(2), 0, RoundingMode.HALF_UP); //除以2僅需要1班可run數量
							BigDecimal bSubQty = bFabPcCapa.subtract(bForecatQty); //需要補滿的數量
							
							boolean isSetAffectCapaQty = false;
							
							if(bAffectCapaQty.compareTo(BigDecimal.ZERO)>0) { //若有設定換線影響by片數
								isSetAffectCapaQty = true;
								bSubQty = bAffectCapaQty.subtract(bForecatQty);
								
								//更新補滿到該班所需時間
								bSecondsBetween = bSubQty.divide(bPerSecQty, 10, RoundingMode.HALF_UP);
							}
							
							//補滿該班所需數量
							BigDecimal bNeedRunQty = bSecondsBetween.multiply(bPerSecQty).setScale(0, RoundingMode.CEILING);
							bSubQty = bNeedRunQty.min(bSubQty);
							
							//若下一班數量足夠扣除
							if(bNextSJobForecatQty.compareTo(bSubQty)>=0) {
								//更新補滿的數量&時間
								BigDecimal bAddQty = bForecatQty.add(bSubQty);
								BigDecimal bReviseForecastQty = bAddQty;
								
								//若有設定換線後影響by片數,且forecastQty小於AffectCapaQty,則最多補到片數就好
								if(isSetAffectCapaQty && bAffectCapaQty.compareTo(bForecatQty)>0) {
									bReviseForecastQty = bAddQty.min(bAffectCapaQty);
									bSubQty = bReviseForecastQty.subtract(bForecatQty);
								}
								
								sJob.setForecastQty(bReviseForecastQty.intValue());
								BigDecimal bReviseProcEndTime = bReviseForecastQty.divide(bPerSecQty, 0, RoundingMode.HALF_UP);
								
								LocalDateTime reviseProcEndTime = sJob.getProcessEndTime();
								if(!isSetAffectCapaQty) { //沒有設定換線影響by片數才需要by capa更新procEndTime
									reviseProcEndTime = sJob.getProcessStartTime().plusSeconds(bReviseProcEndTime.longValue());
									if(reviseProcEndTime.isAfter(shiftEndTime)) {
										reviseProcEndTime = shiftEndTime;
									}
									
									sJob.setProcessEndTime(reviseProcEndTime);
								}
								
								//挖空數量後,修正下一筆SJob起始時間
								LocalDateTime nextSJobProcStartTime = nextSJob.getProcessStartTime();
								LocalDateTime nextSJobProcEndTime = nextSJob.getProcessEndTime();
								
								//若增加時長有超過下一班起始時間,則下一班從頭扣時間
								if(reviseProcEndTime.isAfter(nextSJobProcStartTime)) {
									nextSJob.setProcessStartTime(reviseProcEndTime);
								}else {
									bReviseProcEndTime = bSubQty.divide(bPerSecQty, 0, RoundingMode.HALF_UP);
									reviseProcEndTime = nextSJob.getProcessEndTime().minusSeconds(bReviseProcEndTime.longValue());
									doMoveJob(nextSJob, listPlanResult, reviseProcEndTime, nextSJobProcEndTime, mapParamFollowPpcPlan, false);
								}
								
								BigDecimal bReviseNextForecastQty = bNextSJobForecatQty.subtract(bSubQty);
								nextSJob.setForecastQty(bReviseNextForecastQty.intValue());
								
								if(nextSJob.getForecastQty()==0 && nextSJob.getJobId()!=null) {
									doMoveJob(nextSJob, listPlanResult, reviseProcEndTime, nextSJobProcEndTime, mapParamFollowPpcPlan, false);
									listPlanResult.remove(iNextIndex);
								}
							}else { //若下一班數量不足扣除,則僅拿取可扣除的部分
								//補滿的數量所需時間
								BigDecimal bNextSJobDuration = new BigDecimal(ChronoUnit.SECONDS.between(nextSJob.getProcessStartTime(), nextSJob.getProcessEndTime()));
								//更新補滿的數量&時間
								BigDecimal bReviseForecastQty = bForecatQty.add(bNextSJobForecatQty);
								sJob.setForecastQty(bReviseForecastQty.intValue());
								LocalDateTime reviseProcEndTime = sJob.getProcessEndTime().plusSeconds(bNextSJobDuration.longValue());
								
								sJob.setProcessEndTime(reviseProcEndTime);
								i--;
								listPlanResult.remove(iNextIndex);
							}
						}
					}
				}
			}
		}
		
		updateJobSeqAndShift(listPlanResult, mapParam);
		return listPlanResult;
	}
	
	private static Plan getPlanByLinePartNoGradeShiftDate(List<Plan> rJobPlanList, SJobDashboard sJob) {
		for(Plan plan : rJobPlanList) {
			if(plan.getLine().equals(sJob.getLine())
					&& plan.getShiftDate().isEqual(sJob.getShiftDate().plusDays(1))
					&& plan.getPartNo().equals(sJob.getPartNo())
					&& plan.getGrade().equals(sJob.getGrade())) {
				return plan;
			}
		}
		return null;
	}
	
	//取得同條線下一筆相同partNo的SJob
	private static Map<String, Object> getNextSJobByLinePartNo(List<SJobDashboard> sJobList, SJobDashboard targetSJob, int targetIdx) {
		//先找到shiftDate+1的SJob再從該夜班開始扣量
		Map<String, Object> map = new HashMap<>();
		
		for(int i=0; i<sJobList.size(); i++) {
			SJobDashboard sJob = sJobList.get(i);
			if(sJob.getLine().equals(targetSJob.getLine()) && sJob.getPartNo()!=null 
					&& sJob.getPartNo().equals(targetSJob.getPartNo())
					&& i>targetIdx) {
				if(!"PROD".equals(sJob.getJobType())) //只能拿PROD的量
					break;
				
				map.put("JOB", sJob);
				map.put("INDEX", i);
				//若下一班有夜班則拿夜班數量
//				if(i+1 < sJobList.size()) {
//					SJobDashboard nextSJob = sJobList.get(i+1);
//					String nextSJobIdWithNoSharp = nextSJob.getJobId().substring(0, nextSJob.getJobId().indexOf("##"));
//					String sJobIdWithNoSharp = sJob.getJobId().substring(0, sJob.getJobId().indexOf("##"));
//					if(nextSJobIdWithNoSharp.equals(sJobIdWithNoSharp) && "N".equals(nextSJob.getShift())) {
//						map.put("JOB", nextSJob);
//						map.put("INDEX", i+1);
//					}else {
//						map.put("JOB", sJob);
//						map.put("INDEX", i);
//					}
//				}
				
				return map;
			}
		}
		return null;
	}
	
	/**
	 * 1:Sunday
	 * 7:Saturday
	 * */
	public static int getDay(Date datetime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(datetime);
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	public static int calcDasyBetween(LocalDate planStart, LocalDate planEnd) {
		int days = (int) ChronoUnit.DAYS.between(planStart, planEnd);
		return days;
	}
	
	public static LocalDateTime atStartOfDay(LocalDateTime date) {
		LocalDateTime startOfDay = date.with(LocalTime.MIN);
		return startOfDay;
	}

	public static LocalDateTime atEndOfDay(LocalDateTime date) {
		LocalDateTime endOfDay = date.with(LocalTime.MAX);
		return endOfDay;
	}

	public static LocalDateTime dateToLocalDateTime(Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	public static Date localDateTimeToDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public static String getLineTypeByLineNo(List<PlanLine> line, String lineNo) {
		String lineType = null;
		for(Iterator<PlanLine> iter = line.iterator(); iter.hasNext();) {
			PlanLine obj = iter.next();
			if(lineNo.equals(obj.getLine()))
				return obj.getLineType();
		}
		return lineType;
	}
	

	public static Map<String, Map<String, Object>> calcOpenLine(List<SJobDashboard> listPlanResult, Map<String, Object> mapParam){
		Map<String, Map<String, Object>> mapOpenLine = new HashMap<>();
		
		//計算開線數
        int openQty = Integer.parseInt(String.valueOf(((Map<String, Object>)mapParam.get("open_line")).get("in_value1")));
    	String operator = (String) ((Map<String, Object>)mapParam.get("open_line")).get("in_value2");
    	
    	Set<String> nonDuplicatLineNo = new HashSet<String>();
    	for(Iterator<SJobDashboard> iter = listPlanResult.iterator(); iter.hasNext();) {
        	SJobDashboard job = iter.next();
        	if(!SPECIFIC_JOB_TYPE.contains(job.getJobType())) {
	        	int qty = job.getForecastQty();
	        	String site = job.getSite();
	        	String fab = job.getFab();
	        	String shiftDate = ModulePlannerUtility.formatterNoSlash.format(job.getShiftDate());
	        	String shift = job.getShift();
	        	String lineNo = job.getLine();
	        	nonDuplicatLineNo.add(lineNo);
	        	
	        	//計算commit量
	    		int lineQty = 0;
	    		Map<String, Object> temp = new HashMap<>();
	        	String key = site+"_"+fab+"_"+shiftDate+"_"+shift;
	        	if(mapOpenLine.get(key) != null) {
	    			if(mapOpenLine.get(key).get(lineNo) != null) {
	    				lineQty = (int) mapOpenLine.get(key).get(lineNo);
	    			}
	    			Map<String, Object> temp2 = mapOpenLine.get(key);
	    			temp.putAll(temp2);
	        	}
	    		temp.put(job.getLine(), qty+lineQty);
	    		mapOpenLine.put(key, temp);
        	}
    	}
    	
        for(Entry<String, Map<String, Object>> entry : mapOpenLine.entrySet()) {
        	Map<String, Object> value = entry.getValue();
        	
        	int iOpenLineCount = 0;
        	for(Map.Entry<String, Object> entry2 : ((Map<String, Object>) value).entrySet()) {
        		String key2 = entry2.getKey();
        		int qty = Integer.parseInt(String.valueOf(entry2.getValue()));
        		
        		if(nonDuplicatLineNo.contains(key2)) {
        			String eval = qty+operator+openQty;
        			ScriptEngineManager manager = new ScriptEngineManager();
        			ScriptEngine se = manager.getEngineByName("JavaScript");
        			Object result = null;
        			try {
        				result = se.eval(eval);
        			} catch (ScriptException e) {
        				e.printStackTrace();
        			}
        			if("true".equals(result.toString())) {
        				iOpenLineCount += 1;
        			}
        		}
        	}
        	value.put("open_line", iOpenLineCount);
        }
        mapOpenLine.put("is_open_line", new HashMap<>());
		return mapOpenLine;
	}
	
	public static int calChangeLimit(Map<String, Object> mapParam, Map<String, Map<String, Map<String, Object>>> mapChange) {
		int score = 0;
		if(mapParam.get("change_limit")==null)
			return score;
		
		//group date shift
		List<Map<String, Object>> changeLimitList = null;
		if(mapParam.get("change_limit") instanceof List) {
			changeLimitList = (List<Map<String, Object>>) mapParam.get("change_limit");
		}else {
			changeLimitList = new ArrayList<>();
			changeLimitList.add((Map<String, Object>) mapParam.get("change_limit"));
		}	

		//將mapChange中所有群組-日的資料都撈出來比對
		for(String key : mapChange.keySet()) {
			String[] keys = key.split("_");
			if(keys.length <= 3) {
				continue;
			}
			//比對此群組-日是否適用各規則(changeLimit)
			for(Map<String, Object> mapChangeLimit : changeLimitList) {
				String group = (String) mapChangeLimit.get("in_value1");
				String shift = (String) mapChangeLimit.get("in_value2");
				String limits = (String) mapChangeLimit.get("in_value3");
				String daysOfWeek = (String) mapChangeLimit.get("in_value4");
				//群組是否適用此規則 (fab_group)
				if(!"%".equals(group) && !keys[3].equals(group)) {
					continue;
				}
				//日期是否適用此規則(星期幾)
				if(!"%".equals(daysOfWeek)) {
					Integer dayOfWeek = LocalDate.parse(keys[4], formatterNoSlash).getDayOfWeek().getValue();
					if(!daysOfWeek.contains(dayOfWeek.toString())) {
						continue;
					}
				}
				Map<String, Map<String, Object>> change = mapChange.get(key);
				

				//是否需換算level (S01
				if(limits.indexOf("conversion_") >= 0) {
					/*
					 * level換算
					 * L1 = 0.5*L3
					 * L2 = 0.5*L3
					 * L3 = 1.0*L3
					 * L4 = 2.0*L3
					 */
					double lv3Limit = 0d;
					String[] limitArr = limits.substring("conversion_".length()).split(",");
					
					for(String limit : limitArr) {
						int index = limit.indexOf("L");
						double tmp = Double.parseDouble(limit.substring(0, index));
						switch(limit.substring(index)) {
						case "L1":
						case "L2":
							lv3Limit += tmp*0.5;
							break;
						case "L3":
							lv3Limit += tmp;
							break;
						case "L4":
							lv3Limit += tmp*2;
							break;
						}
					}
					
					//日夜班加總
					if(shift.equals("SUM")) {
						double cal = 0d;
						int changeDurationTot = 0;	
						for(Map<String, Object> mapCal : change.values()) {
							for(String levelKey : mapCal.keySet()) {
								if(levelKey.endsWith("_Hour")) {//日夜班換線時數加總
									changeDurationTot += (int)mapCal.get(levelKey) ;
								}else {
									int tmp = (int) mapCal.get(levelKey);
									switch(levelKey) {
									case "L1":
									case "L2":
										cal += tmp*0.5;
										break;
									case "L3":
										cal += tmp;
										break;
									case "L4":
										cal += tmp*2;
										break;
									}
								}
							}
						}
						if(cal > lv3Limit) {
//							score--;
							score -= changeDurationTot;
						}
					}else {

						if(shift.equals("D") || shift.equals("%")) {
							//日班
							Map<String, Object> mapCal = change.get("D");
							if(mapCal == null) {
								continue;
							}
							double cal = 0d;
							int changeDurationTot = 0;	
							for(String levelKey : mapCal.keySet()) {								
								if(levelKey.endsWith("_Hour")) {//日夜班換線時數加總
									changeDurationTot += (int)mapCal.get(levelKey) ;
								}else {
									int tmp = (int) mapCal.get(levelKey);
									switch(levelKey) {
									case "L1":
									case "L2":
										cal += tmp*0.5;
										break;
									case "L3":
										cal += tmp;
										break;
									case "L4":
										cal += tmp*2;
										break;
									}
								}
							}
							if(cal > lv3Limit) {
//								score--;
								score -= changeDurationTot;
							}
						}
						if(shift.equals("N") || shift.equals("%")) {
							//夜班
							Map<String, Object> mapCal = change.get("N");
							if(mapCal == null) {
								continue;
							}
							double cal = 0d;
							int changeDurationTot= 0;
							for(String levelKey : mapCal.keySet()) {
								if(levelKey.endsWith("_Hour")) {//日夜班換線時數加總
									int aa =(int)mapCal.get(levelKey);
									changeDurationTot += (int)mapCal.get(levelKey) ;
								}else {
									int tmp = (int) mapCal.get(levelKey);
									switch(levelKey) {
									case "L1":
									case "L2":
										cal += tmp*0.5;
										break;
									case "L3":
										cal += tmp;
										break;
									case "L4":
										cal += tmp*2;
										break;
									}
								}
							}
							if(cal > lv3Limit) {
//								score--;
								score -= changeDurationTot;
							}
						}
					}
				}else {
					//解析限制式 (level, bound)
					Map<String, Integer> mapLimit = new HashMap<>();
					if(limits.indexOf("L")<0) {
						mapLimit.put("TOTAL", Integer.parseInt(limits));
					}else {
						String[] limitArr = limits.split(",");
						
						for(String limit : limitArr) {
							int index = limit.indexOf("L");
							mapLimit.put(limit.substring(index), Integer.parseInt(limit.substring(0, index)));
						}	
					}
					//日夜班加總
					if(shift.equals("SUM")) {
						//將日夜班數量加總
						Map<String, Integer> mapCal = new HashMap<>();
						int changeDurationTot = 0;	
						for(Map<String, Object> map : change.values()) {							
							for(String levelKey : map.keySet()) {
								if(levelKey!=null) {
									if(levelKey.endsWith("_Hour")) {//日夜班換線時數加總
										int aa =(int)map.get(levelKey);
										changeDurationTot += (int)map.get(levelKey);
									}
									if(mapCal.containsKey(levelKey)) {
										mapCal.put(levelKey, mapCal.get(levelKey)+ (int)map.get(levelKey));
									}else {
										mapCal.put(levelKey, (int)map.get(levelKey));
									}
								}
							}							 
						}
						mapCal.put("TOTAL_Hour", changeDurationTot);
						//計算扣分
						for(String limitKey : mapLimit.keySet()) {
							Integer bound = mapLimit.get(limitKey);
							Integer cnt = 0;
							for(String levelKey : mapCal.keySet()) {
								if(!levelKey.endsWith("_Hour") && (levelKey.equals(limitKey) || limitKey.equals("TOTAL"))) {
									cnt += mapCal.get(levelKey);
								}
							}
							//只要扣分即跳出
							//後續可更改扣分規則(依違反程度扣分or多次扣分不跳出...etc)
							if(cnt > bound) {
//								score--;
								if( limitKey.equals("TOTAL")) {//若限制式不分換級等級
									score -= (int)mapCal.get("TOTAL_Hour") ;
								}else {
									score -= (int)mapCal.get(limitKey+"_Hour"); ;
								}
//								break;//多次扣分不跳出
							}
						}
					}else {
						//日班扣分
						if(shift.equals("D") || shift.equals("%")) {
							Map<String, Object> mapCal = change.get("D");
							int changeDurationTot = 0;
							if(mapCal == null) {
								continue;
							}else{								
								for(String levelKey : mapCal.keySet()) {
									if(levelKey.endsWith("_Hour")) {//日夜班換線時數加總
										changeDurationTot += (int)mapCal.get(levelKey) ;
									}												
								}
								mapCal.put("TOTAL_Hour", changeDurationTot);
						    }						
							
							for(String limitKey : mapLimit.keySet()) {
								Integer bound = mapLimit.get(limitKey);
								Integer cnt = 0;
								for(String levelKey : mapCal.keySet()) {
									if(!levelKey.endsWith("_Hour") && (levelKey.equals(limitKey) || limitKey.equals("TOTAL"))) {
										cnt += (int)mapCal.get(levelKey);
									}
								}
								//只要扣分即跳出
								//後續可更改扣分規則(依違反程度扣分or多次扣分不跳出...etc)
								if(cnt > bound) {
//									score--;
									if(limitKey.equals("TOTAL")) {
										score -= (int)mapCal.get("TOTAL_Hour");
									}else {
										score -= (int)mapCal.get(limitKey+"_Hour"); ;
									}
//									break;//多次扣分不跳出
								}
							}
						}
						//夜班扣分
						if(shift.equals("N") || shift.equals("%")) {
							Map<String, Object> mapCal = change.get("N");
							int changeDurationTot = 0;
							if(mapCal == null) {
								continue;
							}else{								
								for(String levelKey : mapCal.keySet()) {
									if(levelKey!=null) {
										if(levelKey.endsWith("_Hour")) {//日夜班換線時數加總
											changeDurationTot += (int)mapCal.get(levelKey) ;
										}
									}
								}
								mapCal.put("TOTAL_Hour", changeDurationTot);
						    }			
							for(String limitKey : mapLimit.keySet()) {
								Integer bound = mapLimit.get(limitKey);
								Integer cnt = 0;
								for(String levelKey : mapCal.keySet()) {
									if(levelKey!=null) {
										if(!levelKey.endsWith("_Hour") && (levelKey.equals(limitKey) || limitKey.equals("TOTAL"))) {
											cnt += (int)mapCal.get(levelKey);
										}
									}
								}
								//只要扣分即跳出
								//後續可更改扣分規則(依違反程度扣分or多次扣分不跳出...etc)
								if(cnt > bound) {
//									score--;
									if(limitKey.equals("TOTAL")) {
										score -= (int)mapCal.get("TOTAL_Hour");
									}else {
										score -= (int)mapCal.get(limitKey+"_Hour"); ;
									}
//									break; //多次扣分不跳出
								}
							}
						}
					}
				}
			}
		}
		
		return score;
	}
		
	public static Map<String, Map<String, Map<String, Object>>> calcChangeLimitBySite(Map<String, Object> mapParam, List<SJobDashboard> listPlanResult, String site, List<PlanLine> lineList){
		List<String> listFabs = new ArrayList<>();
		String prefix = "fab_change_g";
		for(Entry<String, Object> entry : mapParam.entrySet()) {
			if(entry.getKey().contains(prefix)) {
				listFabs.add(entry.getKey());
			}
		}

		Map<String, Map<String, Map<String, Object>>> mapChange = new HashMap<>();
		for(Iterator<SJobDashboard> iter = listPlanResult.iterator(); iter.hasNext();) {
			SJobDashboard job = iter.next();
			String site1 = job.getSite();
			if(job.getChangeShiftDate() != null) {
				String shiftDate = ModulePlannerUtility.formatterNoSlash.format(job.getChangeShiftDate());
				String shift = job.getChangeShift();
				String changeLvl = job.getChangeLevel();
				float changeDuration = job.getChangeDuration();
				String lineNo = job.getLine();
				String lineType = ModulePlannerUtility.getLineTypeByLineNo(lineList, lineNo);
				lineType = lineType == null ? "" : lineType;
				
				if(!"L0".equals(changeLvl)) {
					int count = 0;
					int changeDurationSum = 0;
					String key = "";
					for(String fabChangeGroup : listFabs) {
						boolean isContain = isContainsFab(mapParam, site1, fabChangeGroup, lineNo, lineList);
						if(isContain) {
							key = site1+"_"+fabChangeGroup+"_"+shiftDate+"_"+lineType;
							Map<String, Map<String, Object>> temp1 = new HashMap<String, Map<String, Object>>();
							Map<String, Object> temp2 = new HashMap<String, Object>();
							
							if(mapChange.get(key) != null) {
								temp1.putAll(mapChange.get(key));
								
								if(mapChange.get(key).get(shift) != null) {
									temp2.putAll(temp1.get(shift));
									
									if(mapChange.get(key).get(shift).get(changeLvl) != null) {
										count = (int) mapChange.get(key).get(shift).get(changeLvl);
										//累加換線時數
										changeDurationSum = (int) mapChange.get(key).get(shift).get(changeLvl+"_Hour");
									}
								}
							}
							count +=1;
							changeDurationSum += changeDuration;							
							temp2.put(changeLvl, count);
							temp2.put(changeLvl+"_Hour", changeDurationSum);
							
							temp1.put(shift, temp2);
							mapChange.put(key, temp1);
						}
					}
				}
			}
		}
		
		mapChange.put("is_change_limit", new HashMap<>());
		return mapChange;
	}
	
	private static boolean isContainsFab(Map<String, Object> mapParam, String site, String fabChangeGroup, String lineNo, List<PlanLine> lineList) {
		boolean isContains = false;
		List<Map<String, Object>> listValues = new ArrayList<>();
		getFabChangeGroup(mapParam, fabChangeGroup, listValues);
		
		for(Map<String, Object> map : listValues) {
			String value1 = String.valueOf(map.get("in_value1"));
			String value2 = String.valueOf(map.get("in_value2"));
			String lineFab = lineNo.substring(3, 5);
			String lineType = ModulePlannerUtility.getLineTypeByLineNo(lineList, lineNo);
			
			if(lineFab.equals(value1) && "null".equals(value2)) {
				isContains = true;
			}else if(lineFab.equals(value1) && !"null".equals(value2)) {
				if(value2.equals(lineType))
					isContains = true;
			}
			
		}
		return isContains;
	}
	
	private static void getFabChangeGroup(Map<String, Object> mapParam, String fabChangeGroup, List<Map<String, Object>> listValues) {
		if(mapParam.get(fabChangeGroup) instanceof Map) {
			listValues.add((Map<String, Object>)mapParam.get(fabChangeGroup));
		}else if(mapParam.get(fabChangeGroup) instanceof List) {
			listValues.addAll((List<Map<String, Object>>)mapParam.get(fabChangeGroup));
		}
	}
	
	public static Map<String, Object> clacChangeLimitResult(Map<String, Map<String, Map<String, Object>>> mapChangeLimit) {
		Map<String, Object> mapScore = new HashMap<>();
		int hardScore = 0;
		for(Map.Entry<String, Map<String, Map<String, Object>>> entry1 : mapChangeLimit.entrySet()) {
			String[] keys = entry1.getKey().split("_");
			if(keys.length <= 3)
				continue;
			Map<String, Map<String, Object>> value = entry1.getValue();
			String site = keys[0];
			String fab = keys[1]+"_"+keys[2]+"_"+keys[3];
			LocalDate shiftDate = LocalDate.parse(keys[4], ModulePlannerUtility.formatterNoSlash);
			String shiftD = "D";
			String shiftN = "N";
			String lineType = keys.length == 6 ? keys[5] : "";
			
			int DL1=0, DL2=0, DL3=0, DL4=0, DL5=0, DL6=0;
			int NL1=0, NL2=0, NL3=0, NL4=0, NL5=0, NL6=0;
			List<Integer> shiftDList = new ArrayList<>();
			List<Integer> shiftNList = new ArrayList<>();
			if(value.get(shiftD) != null) {
				if(value.get(shiftD).get("L1") != null)
					DL1 = (int)value.get(shiftD).get("L1");
				if(value.get(shiftD).get("L2") != null)
					DL2 = (int)value.get(shiftD).get("L2");
				if(value.get(shiftD).get("L3") != null)
					DL3 = (int)value.get(shiftD).get("L3");
				if(value.get(shiftD).get("L4") != null)
					DL4 = (int)value.get(shiftD).get("L4");
				if(value.get(shiftD).get("L5") != null)
					DL5 = (int)value.get(shiftD).get("L5");
				if(value.get(shiftD).get("L6") != null)
					DL6 = (int)value.get(shiftD).get("L6");
			}
			if(value.get(shiftN) != null) {
				if(value.get(shiftN).get("L1") != null)
					NL1 = (int)value.get(shiftN).get("L1");
				if(value.get(shiftN).get("L2") != null)
					NL2 = (int)value.get(shiftN).get("L2");
				if(value.get(shiftN).get("L3") != null)
					NL3 = (int)value.get(shiftN).get("L3");
				if(value.get(shiftN).get("L4") != null)
					NL4 = (int)value.get(shiftN).get("L4");
				if(value.get(shiftN).get("L5") != null)
					NL5 = (int)value.get(shiftN).get("L5");
				if(value.get(shiftN).get("L6") != null)
					NL6 = (int)value.get(shiftN).get("L6");
			}
			shiftDList.add(DL1);
			shiftDList.add(DL2);
			shiftDList.add(DL3);
			shiftDList.add(DL4);
			shiftDList.add(DL5);
			shiftDList.add(DL6);
			
			shiftNList.add(NL1);
			shiftNList.add(NL2);
			shiftNList.add(NL3);
			shiftNList.add(NL4);
			shiftNList.add(NL5);
			shiftNList.add(NL6);
			Map<String, Object> score = new HashMap<>();
			for(int i=1; i<=6; i++) {
				score.put("DL"+i, shiftDList.get(i-1));
			}
			for(int i=1; i<=6; i++) {
				score.put("NL"+i, shiftNList.get(i-1));
			}
			
			mapScore.put("is_map_score", new Object());
			mapScore.put(entry1.getKey(), score);
			
			
			/*int day = shiftDate.getDayOfWeek().getValue();
			if("S06".equals(site)) {
				if("3B".equals(fab) || "4A".equals(fab)) {
			    	if(NL2 > 0) {
			    		hardScore -= 1;
			    		logger.info("hardScore rule1: " + hardScore);
			    	}else {
			    		if((DL1+NL1)>2 || (DL2+NL2)>2) {
			    			hardScore -= 1;	
			    			logger.info("hardScore rule2: " + hardScore);
			    		}
			    	}
			    	logger.info("D_L1: " + DL1 + " D_L2: " + DL2 + " N_L1: " + NL1 + " N_L2: " + NL2);
				}else if("3A".equals(fab)) {
					if((DL1+NL1)>2 || (DL2+NL2)>2)
						hardScore -= 1;	
				}
			}else if("S02".equals(site)) {
				 周一到周五
				 * 白班2條/夜班1條(2E FAB 6#,7# & 3E FAB 1#,2#)
				 * 周六日不換
				if(day == 6 || day == 7) {
					hardScore -= 1;
					logger.debug("hardScore rule1: " + hardScore);
				}else {
					if((DL1+DL2+DL3+DL4+DL5+DL6) > 2 || (NL1+NL2+NL3+NL4+NL5+NL6) > 1) {
						hardScore -= 1;
						logger.debug("hardScore rule2: " + hardScore);
					}
				}
			}else if("S01".equals(site)) {
				 COG周一到周五
				 * 白班4L3+1L2/夜班3L3+1L2(L3=2L2)
				 * 周六
				 * 白班3L3+1L2/夜班3L3+1L2(L3=2L2)
				 * 周日不換
				 * COF周一到周五
				 * 白班2L3/夜班1L3
				 * 周六日不換
				 換算
				 * L1=0.5L3
				 * L2=0.5L3
				 * L4=2L3
				 
				if("COG".equals(lineType)) {
					if(day>=1 && day<=5) {
						double scoreD = DL1*0.5 + DL2*0.5 + DL3*1 + DL4*1;
						double scoreN = NL1*0.5 + NL2*0.5 + NL3*1 + NL4*1;
						if(scoreD > 4.5 || scoreN > 3.5) {
							hardScore -= 1;
						}
					}else if(day == 6) {
						double scoreD = DL1*0.5 + DL2*0.5 + DL3*1 + DL4*1;
						double scoreN = NL1*0.5 + NL2*0.5 + NL3*1 + NL4*1;
						if(scoreD > 3.5 || scoreN > 3.5) {
							hardScore -= 1;
						}
					}else if(day == 7) {
						hardScore -= 1;
					}
				}else if("COF".equals(lineType)){
					if(day>=1 && day<=5) {
						double scoreD = DL1*0.5 + DL2*0.5 + DL3*1 + DL4*1;
						double scoreN = NL1*0.5 + NL2*0.5 + NL3*1 + NL4*1;
						if(scoreD > 2 || scoreN > 1) {
							hardScore -= 1;
						}
					}else if(day == 7) {
						hardScore -= 1;
					}
				}
			}*/
        }
		return mapScore;
	}
	
	public static int calcS06L2CantInNightShift(Map<String, Object> mapScore, Map<String, Object> mapParam) {
		int iScore = 0;
		for(Map.Entry<String, Object> entry1 : mapScore.entrySet()) {
			String[] keys = entry1.getKey().split("_");
			if(keys.length <= 3)
				continue;
			Map<String, Object> value = (Map<String, Object>) entry1.getValue();
			String site = keys[0];
			String fabGroup = keys[1]+"_"+keys[2]+"_"+keys[3];
			
			List<String> listFabGroup = new ArrayList<>();
			if(mapParam.get("fab_change_g1") instanceof List) {
				listFabGroup = (List<String>) mapParam.get("fab_change_g1");
			}else {
				String fab = String.valueOf(((Map)mapParam.get(fabGroup)).get("in_value1"));
				listFabGroup.add(fab);
			}
			
			boolean isMatchFab = false;
			List<String> fabList = new ArrayList<>(Arrays.asList("3B", "4A"));
			for(String fab : fabList) {
				if(listFabGroup.contains(fab))
					isMatchFab = true;
			}
			
			if("S06".equals(site) && isMatchFab) {
				int NL2 = Integer.parseInt(String.valueOf(value.get("NL2")));
				if(NL2 > 0) {
		    		iScore += 1;
		    	}
			}
		}
		
		return iScore;
	}
	
	public static Map<String, Object> calcChangeLimitS06(Map<String, Object> mapScore, Map<String, Object> mapParam) {
		Integer iScore = 0;
		Map<String, Object> mapResult = new HashMap<>();
		for(Map.Entry<String, Object> entry1 : mapScore.entrySet()) {
			String[] keys = entry1.getKey().split("_");
			if(keys.length <= 3)
				continue;
			Map<String, Object> value = (Map<String, Object>) entry1.getValue();
			String site = keys[0];
			String fabGroup = keys[1]+"_"+keys[2]+"_"+keys[3];
			
			List<String> listFabGroup = new ArrayList<>();
			if(mapParam.get("fab_change_g1") instanceof List) {
				listFabGroup = (List<String>) mapParam.get("fab_change_g1");
			}else {
				String fab = String.valueOf(((Map)mapParam.get(fabGroup)).get("in_value1"));
				listFabGroup.add(fab);
			}
			
			boolean isMatchFab = false;
			List<String> fabList = new ArrayList<>(Arrays.asList("3A", "3B", "4A"));
			for(String fab : fabList) {
				if(listFabGroup.contains(fab))
					isMatchFab = true;
			}
			
			if("S06".equals(site) && isMatchFab) {
				int DL1 = Integer.parseInt(String.valueOf(value.get("DL1")));
				int DL2 = Integer.parseInt(String.valueOf(value.get("DL2")));
				int NL1 = Integer.parseInt(String.valueOf(value.get("NL1")));
				int NL2 = Integer.parseInt(String.valueOf(value.get("NL2")));
				if((DL1+NL1)>2 || (DL2+NL2)>2) {
	    			iScore += 1000;	
	    			String score = "DL1:" + DL1 + " NL1:" + NL1 + " DL2:" + DL2 + " NL2:" + NL2;
	    			mapResult.put(entry1.getKey(), score);
	    		}
			}
		}
		mapResult.put("score", iScore);
		mapResult.put("is_map_change_limit", "");
		return mapResult;
	}
	
	public static List<SJobDashboard> calcSetupAffect(List<SJobDashboard> sJobList, Map<String, Object> mapParam, boolean isDebug) throws Exception{
		int iShiftCnt = 0;
		boolean isShiftBeginning = false;
		String firstSetupJobChangeKey = ""; 
		
		sortSJobDashboardList(sJobList);
		
		List<ModChange> cModChangeList = (List<ModChange>) mapParam.get("cmod_change_list");
		List<Object> historicalList = (List<Object>) mapParam.get("historical_list");
		
		Map<String, Object> capaMap = (Map<String, Object>) mapParam.get("capa_map");
		Map<String, Object> mapParamFollowPpcPlan = (Map<String, Object>) mapParam.get("follow_ppc_plan");
		
		if (sJobList.size() > 0) {
//			int iCnt = 0;
			String previousLineNo = sJobList.get(0).getLine();
			String previousShift = sJobList.get(0).getShift();
			BigDecimal remainShiftPlanQty = new BigDecimal(sJobList.get(0).getPlanQty());
			int previousPlanQty = sJobList.get(0).getPlanQty();
			String previousChangeKey = sJobList.get(0).getChangeKey();
			BigDecimal extraSeconds = new BigDecimal(0);
			String previousJobId = sJobList.get(0).getJobId();
			int previousShiftIndex = sJobList.get(0).getShiftIndex();
			int previousSplitCnt = -1;
			
//			for(Iterator<SJobDashboard> iter = sJobList.iterator(); iter.hasNext();) {
//				SJobDashboard sJob = iter.next();
			for(int iCnt=0; iCnt<sJobList.size(); iCnt++) {
				SJobDashboard sJob = sJobList.get(iCnt);
				
				boolean isFindLastChangeShift = false; //找到最後換線是相鄰班別且未換完線
				boolean isFindNeighborShift = false; //找到前一班是相鄰班別
				boolean isFindSameShift = false; //找到前一班是同班別
				boolean isLastShift = false;
				boolean isSplit = false; //CPAP變慢使得不足以在一班生產完,則拆至下一班 JoshLai@20190730+
				boolean isSameIdAsPreviousPlan = false; //和前一筆是同一個Plan JoshLai@21090801+
				
				//改用班次 JoshLai@20190415+
				/*isFindNeighborShift = isFindNeighborShift(previousShiftDate, previousShift, sJob.getShiftDate(), sJob.getShift());
				if(previousShiftDate.isEqual(sJob.getShiftDate()) && previousShift.equals(sJob.getShift())) {
					isFindSameShift = true;
				}*/
				
				String sJobIdWithNoSharp = sJob.getJobId().substring(0, sJob.getJobId().indexOf("##"));
				Map<String, Object> mapCapa = (Map)capaMap.get(sJobIdWithNoSharp);
				
				if((sJob.getShiftIndex() - previousShiftIndex ) == 0 || (sJob.getShiftIndex() - previousShiftIndex) == 1) {
					isFindNeighborShift = true;
				}
				if(sJob.getShiftIndex() == previousShiftIndex)
					isFindSameShift = true;
				
				//找前一班未換完線
				if(sJob.getChangeDuration() > 0) {
					RJobDashboard lastChangeRJob = getLastChangeRJobByChangeShiftDate(historicalList, sJob.getLine(), sJob.getShiftIndex(), mapParam);
					if(lastChangeRJob != null && lastChangeRJob.getChangeDuration() > 0) {
						float changeDurationParameter = 0;
						for(ModChange cModChange : cModChangeList) {
							if(cModChange.getChangeKey().equals(lastChangeRJob.getChangeKey())) {
								changeDurationParameter = Float.parseFloat(cModChange.getChangeDuration());
							}
						}
						
						if(lastChangeRJob != null && sJob.getChangeKey()!=null 
								&& sJob.getChangeKey().equals(lastChangeRJob.getChangeKey())
								&& changeDurationParameter - sJob.getChangeDuration() > 0
								&& lastChangeRJob.getPartNo().equals(sJob.getPartNo()))
							isFindLastChangeShift = true;//找到前一個job未換完線
						else
							isFindLastChangeShift = false;
					}
				}
				
				if(!previousLineNo.equals(sJob.getLine())) {
					iShiftCnt = 0;
					isShiftBeginning = false;
					isFindSameShift = false;
					extraSeconds = new BigDecimal(0);
				}
				
				if(previousPlanQty != sJob.getPlanQty() || !previousJobId.split("##")[0].equals(sJob.getJobId().split("##")[0])) {
					remainShiftPlanQty = new BigDecimal(sJob.getPlanQty());
				}
				 
				if(sJob.getChangeStartTime()!=null && !sJob.getChangeStartTime().isEqual(sJob.getChangeEndTime())) {
					if(isFindLastChangeShift) {
						iShiftCnt = 2;
					}else {
						//前一班是相鄰班別且不同班且同changeKey且相同線別, ex:跨班換線切班
						if(isFindNeighborShift && !isFindSameShift && (!previousShift.equals(sJob.getShift()))&& previousChangeKey.equals(sJob.getChangeKey()) && previousLineNo.equals(sJob.getLine())) {
							iShiftCnt++;
						}else
							iShiftCnt = 1;
					}
					isShiftBeginning = true;
					firstSetupJobChangeKey = sJob.getChangeKey() == null ? "" : sJob.getChangeKey();
				}else {
					if(isFindNeighborShift && isShiftBeginning) {
						if(!isFindSameShift)
							iShiftCnt++;
					}else {
						if(!isFindSameShift) {
							iShiftCnt = 0;
							isShiftBeginning = false;
							
							//若沒有連續生產則重置firstSetupJobChangeKey,避免班次被繼續延用 JoshLai@20190726+
							if(Math.abs(sJob.getShiftIndex()-previousShiftIndex)>1) {
								firstSetupJobChangeKey = "";
							}
						}
					}
				}
				
				if(previousPlanQty == sJob.getPlanQty() && previousJobId.split("##")[0].equals(sJob.getJobId().split("##")[0])) {
					isSameIdAsPreviousPlan = true;
				}
				
				
//				logger.debug("sJob.getJobId(): " + sJob.getJobId()
//						+ " ,isFindLastChangeShift: " + isFindLastChangeShift
//						+ " ,isFindNeighborShift: " + isFindNeighborShift
//						+ " ,isFindSameShift: " + isFindSameShift
//						+ " ,isShiftBeginning: " + isShiftBeginning
//						+ " ,firstSetupJobChangeKey: " + firstSetupJobChangeKey
//						+ " ,previousChangeKey: " + previousChangeKey
//						+ " ,sJob.getChangeKey(): " + sJob.getChangeKey()
//						+ " ,iShiftCnt: " + iShiftCnt
//						+ " ,remainShiftPlanQty: " + remainShiftPlanQty);
				
				//當設定affectQty等於planQty時,已無剩餘數量remainShiftPlanQty可用,刪除該筆SJob不需排產 JoshLai@20190527+
				if(remainShiftPlanQty.compareTo(BigDecimal.ZERO)<=0 && !SPECIFIC_JOB_TYPE.contains(sJob.getJobType())) {
					//刪除Job會有空檔,必須先將後面的Job往前拉對齊 JoshLai@20190530+
					//若Job有換線,則不刪除,而是把後面的Job processTime往前拉 JoshLai@20190614+
					if(sJob.getChangeDuration()==0) {
						LocalDateTime moveToTime = sJob.getChangeStartTime()==null?sJob.getProcessStartTime():sJob.getChangeStartTime();
						
//						if(isDebug) {
//							logger.debug("<=0 sJobId-1: " + sJob.getJobId() + " changeDuration: " + sJob.getChangeDuration()
//										+ " getChangeStartTime: " + sJob.getChangeStartTime()
//										+ " getProcessStartTime: " + sJob.getProcessStartTime()
//										+ " moveToTime: " + moveToTime
//										+ " from: " + sJob.getProcessEndTime()
//										+ " iShiftCnt: " + iShiftCnt);
//						}
						
						doMoveJob(sJob, sJobList, moveToTime, sJob.getProcessEndTime(), mapParam, isDebug);
//						iter.remove();
						sJobList.remove(iCnt);
						if(iShiftCnt>0)
							iShiftCnt--;
						iCnt--;
						previousSplitCnt--;
						continue;
					}else {
						LocalDateTime moveToTime = sJob.getChangeEndTime();
//						if(isDebug) {
//							logger.debug("<=0 sJobId-2: " + sJob.getJobId() + " changeDuration: " + sJob.getChangeDuration()
//										+ " from: " + sJob.getProcessEndTime()
//										+ " moveToTime: " + moveToTime);
//						}
						doMoveJob(sJob, sJobList, moveToTime, sJob.getProcessEndTime(), mapParam, isDebug);
						sJob.setProcessStartTime(sJob.getChangeEndTime());
						sJob.setProcessEndTime(sJob.getChangeEndTime());
						sJob.setForecastQty(0);
						sJob.setJobQty(0);
//						if(isDebug) {
//							logger.debug("<=0 sJobId-3: " + sJob.getJobId() + " job: " + sJob);
//						}
					}
					
				}
				
				if(iCnt+1 < sJobList.size()) {
					String currentJobId = sJob.getJobId().split("##")[0];
					String nextJobId = sJobList.get(iCnt+1).getJobId().split("##")[0];
					
					if(!currentJobId.equals(nextJobId)) {
						isLastShift = true;
//					}else if(sJobList.get(iCnt+1).getJobId().equals(nextJobId)){
					}else if(!sJobList.get(iCnt+1).getJobId().split("##")[0].equals(nextJobId)){
						//if(sJobList.get(iCnt+1).getIsAddTo() == 1)
							isLastShift = true;
					}
				}else if(iCnt+1 == sJobList.size() /*|| sJob.getIsAddTo() == 1*/) {
					isLastShift = true;
				}
				
				int jobQty = sJob.getForecastQty();
				
				if(previousSplitCnt==-1 || previousSplitCnt+1 != iCnt)
					sJob.setJobQty(jobQty);
				
				String previousSetupJobChangeKey = null;
				for(ModChange cModChange : cModChangeList) {
					if(firstSetupJobChangeKey.equals(cModChange.getChangeKey())
							&& !cModChange.getChangeKey().equals(previousSetupJobChangeKey)) {
						double affectCapaPercent = 1;
						int affectCapaQty = 0;
						int affectQty = sJob.getForecastQty();
						
						boolean isProcess = false;
						switch (iShiftCnt) {
							case 1:
								affectCapaPercent = Double.parseDouble(cModChange.getAffectCapaPercent1());
								affectCapaQty = Integer.parseInt(cModChange.getAffectCapaQty1());
								isProcess = true;
								break;
							case 2:
								affectCapaPercent = Double.parseDouble(cModChange.getAffectCapaPercent2());
								affectCapaQty = Integer.parseInt(cModChange.getAffectCapaQty2());
								isProcess = true;
								break;
							case 3:
								affectCapaPercent = Double.parseDouble(cModChange.getAffectCapaPercent3());
								affectCapaQty = Integer.parseInt(cModChange.getAffectCapaQty3());
								isProcess = true;
								break;
							case 4:
								affectCapaPercent = Double.parseDouble(cModChange.getAffectCapaPercent4());
								affectCapaQty = Integer.parseInt(cModChange.getAffectCapaQty4());
								isProcess = true;
								break;
						}
						
						if(affectCapaPercent==0)
							affectCapaPercent = 1;
						if(/*isProcess &&*/ !SPECIFIC_JOB_TYPE.contains(sJob.getJobType())) {
							sJob.setShiftCountAfterChange(iShiftCnt);
							sJob.setAffectCapaPercent(affectCapaPercent);
							sJob.setAffectCapaQty(affectCapaQty);
							affectCapaQty = Math.min(sJob.getPlanQty(), affectCapaQty); //affectCapaQty不能超過PlanQty JoshLai@20190614+
							
							boolean isNoJobQty = false;
							if(sJob.getJobQty().intValue() == 0) {
								isNoJobQty = true;
							}
							
							//換線擺量直接設定Qty JoshLai@20190513+
//							if(affectCapaQty > 0) {
//								sJob.setForecastQty(affectCapaQty);
//								sJob.setJobQty(affectCapaQty);
//							}
							
							//affectQty = new BigDecimal(sJob.getJobQty()).multiply(new BigDecimal(affectCapaPercent))
							//		.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
							
							//用duration算變慢的capa JoshLai@20190730+
							//Job時間長度
							BigDecimal perSecQty = new BigDecimal(0);
							long secondsBetween = ChronoUnit.SECONDS.between(sJob.getProcessStartTime(), sJob.getProcessEndTime());
							if(secondsBetween > 0) {
								BigDecimal diffSecondsFromProcessStartToEnd = new BigDecimal(secondsBetween);
								
								BigDecimal bAffectCapaPercent = new BigDecimal(affectCapaPercent);
								Shift currentShift = sJob.getShiftList().get(sJob.getShiftIndex());
								LocalDateTime currentShiftEndTime = calcDateTimeByNum(mapParam, currentShift.getEndTimeIndex());
								long secondsBetweenShiftEnd = ChronoUnit.SECONDS.between(sJob.getProcessStartTime(), currentShiftEndTime);
								BigDecimal bSecondsBetweenShiftEnd = new BigDecimal(secondsBetweenShiftEnd);
								
								//換線後影響每秒可做
								BigDecimal bJobQty = new BigDecimal(sJob.getJobQty());
								perSecQty = bJobQty.divide(diffSecondsFromProcessStartToEnd, 10, RoundingMode.HALF_UP);
								BigDecimal affectPerSecQty = perSecQty.multiply(bAffectCapaPercent);
								
								//換線後影響需花秒數
								BigDecimal runSec = bJobQty.divide(affectPerSecQty, 10, RoundingMode.HALF_UP);
								
								//若runSec小於ShiftEndTime-ProcessStartTime,表示在該班可完全做完, 修改ProcessEndTime即可
								if(runSec.compareTo(bSecondsBetweenShiftEnd)<0) {
									BigDecimal bHour = runSec.divide(BIGDECIMAL_60, 10, RoundingMode.HALF_UP)
											.divide(BIGDECIMAL_60, 10, RoundingMode.HALF_UP);
									LocalDateTime updateProcEndTime = addHourReturnDate(sJob.getProcessStartTime(), bHour.doubleValue());
									sJob.setProcessEndTime(updateProcEndTime);
								}else {
									//換線後影響CPAP乘以該班剩餘時間,即得該班可生產數量
									affectQty = affectPerSecQty.multiply(bSecondsBetweenShiftEnd).setScale(0, RoundingMode.HALF_UP).intValue();
									sJob.setForecastQty(affectQty);
									isSplit = true;
								}
							}
							
							//換線擺量直接設定Qty JoshLai@20190513+
							if(affectCapaQty > 0) {
								sJob.setForecastQty(affectCapaQty);
								sJob.setJobQty(affectCapaQty);
								affectQty = affectCapaQty;
								
								//若為單獨的Plan且有設定affectCapaQty則需將affectCapaQty與原有的forecastQty切開 JoshLai@21090801+
								if(!isSameIdAsPreviousPlan)
									isSplit = true;
							}
							
							remainShiftPlanQty = remainShiftPlanQty.subtract(new BigDecimal(affectQty));
							
							//換線影響:不需要扣掉"換線當班"已經排的量 JoshLai@20190703+
							//若follow_ppc_plan in_value2= adjust 換線影響擺量就要扣量 JoshLai@20190801+
							boolean isAdjust = false;
							Map<String, Object> mapFollowPpcPlan = (Map<String, Object>) mapParam.get("follow_ppc_plan");
							if(mapFollowPpcPlan!=null && "adjust".equalsIgnoreCase((String) mapFollowPpcPlan.get("in_value2"))) {
								isAdjust = true;
							}
							if(!isAdjust) {
								if(sJob.getChangeDuration()!=0) {
									remainShiftPlanQty = remainShiftPlanQty.add(new BigDecimal(affectCapaQty));
								}
							}
							
							//若都在換線沒有生產數量的情況下，將affectQty加入剩餘數量 JoshLai@20190513+
							if(isNoJobQty&&affectQty==0) {
								remainShiftPlanQty = remainShiftPlanQty.add(new BigDecimal(affectQty));
							}
							
//							logger.debug("sJob: " + sJob.getJobId() + " ,isLastShift: " + isLastShift 
//								+ " ,iCnt: " + iCnt 
//								+ " ,iShiftCnt: " + iShiftCnt
//								+ " ,jobQty: " + sJob.getJobQty()
//								+ " ,planQty: " + sJob.getPlanQty()
//								+ " ,getForecastQty: " + sJob.getForecastQty()
//								+ " ,affectQty: " +affectQty
//								+ " ,remainShiftPlanQty: " + remainShiftPlanQty
//								+ " ,extraSeconds: " + extraSeconds
//								+ " ,sJob.getIsAddTo(): " + sJob.getIsAddTo()
//								+ " ,processStartTime: " + sJob.getProcessStartTime()
//								+ " ,processEndTime: " + sJob.getProcessEndTime()
//								+ " ,sJob: " + sJob);
							
							//推遲的qty加到最後一個job &&iShiftCnt > 1
							//要考慮若該筆SJob是唯一一筆也是最後一筆
							if(isLastShift) {
								if(isSplit) {
									previousSplitCnt = iCnt;
									Shift currentShift = sJob.getShiftList().get(sJob.getShiftIndex());
									BigDecimal bRunSec = remainShiftPlanQty.divide(perSecQty, 10, RoundingMode.HALF_UP);
									LocalDateTime newProcStartTime = calcDateTimeByNum(mapParam, currentShift.getEndTimeIndex());
									LocalDateTime newProcEndTime = newProcStartTime.plusSeconds(bRunSec.longValue());
									
									LocalDateTime updateProcEndTime = calcDateTimeByNum(mapParam, currentShift.getEndTimeIndex());
									sJob.setProcessEndTime(updateProcEndTime);//將processEndTime改為該班結束時間
									
									boolean isAddNewSJob = true;
									boolean isFullDShift = mapCapa==null ? false : (boolean) mapCapa.get("IS_FULLDSHIFT"); //僅能補滿白班
									//若僅能補白班則不需要增長夜班,並將剩餘的數量加到下一個相同partNo的SJob
									if(isFullDShift && (mapParamFollowPpcPlan!=null 
											|| mapParamFollowPpcPlan.get("in_value3")!=null
											|| "capabyshift".equals((String)mapParamFollowPpcPlan.get("in_value3")))
											&& sJob.getShiftIndex()%2==0) {
										addRemainShiftPlanQtyToNextShiftDateSJob(sJobList, remainShiftPlanQty, sJob, mapParam);
										isAddNewSJob = false;
										previousSplitCnt = -1;
										logger.info("sJobId===> " + sJob.getJobId());
									}
									
									if(isAddNewSJob) {
										SJobDashboard newJob = (SJobDashboard) sJob.clone();
										String[] newJobIds = newJob.getJobId().split("##");
										int newJobIdSeq = Integer.parseInt(newJobIds[1]) + 1;
										String newJobId = newJobIds[0]+"##"+newJobIdSeq;
										newJob.setJobId(newJobId);
										newJob.setProcessStartTime(newProcStartTime);
										newJob.setProcessEndTime(newProcEndTime);
										newJob.setForecastQty(remainShiftPlanQty.intValue());
										newJob.setJobQty(newJob.getForecastQty());
										newJob.setShiftIndex(sJob.getShiftIndex()+1);
										
										LocalDate shiftDate = (LocalDate) calcShiftByProcStartTime(newJob.getProcessStartTime(), mapParam).get("SHIFT_DATE");
										String shiftType = (String) calcShiftByProcStartTime(newJob.getProcessStartTime(), mapParam).get("SHIFT_TYPE");
										newJob.setShiftDate(shiftDate);// 用process_start_tme換算
										newJob.setShift(shiftType);
										
										newJob.setAffectCapaPercent(null);
										newJob.setAffectCapaQty(null);
										newJob.setShiftCountAfterChange(null);
										newJob.setChangeShift(null);
										newJob.setChangeShiftDate(null);
										newJob.setChangeKey(null);
										newJob.setChangeDuration(0);
										newJob.setChangeLevel(null);
										newJob.setChangeStartTime(null);
										newJob.setChangeEndTime(null);
										sJobList.add(newJob);
										sortSJobDashboardList(sJobList);
										break;
									}
								}else {
									affectQty = affectQty + remainShiftPlanQty.intValue();
									LocalDateTime processStartTime = sJob.getProcessStartTime();
									LocalDateTime processEndTime = sJob.getProcessEndTime();
									//long secondsBetween = java.time.temporal.ChronoUnit.SECONDS.between(processStartTime, processEndTime);
									secondsBetween = java.time.temporal.ChronoUnit.SECONDS.between(processStartTime, processEndTime);
									if(secondsBetween > 0) {
//										BigDecimal diffSecondsFromProcessStartToEnd = new BigDecimal(secondsBetween);
//										BigDecimal bJobQty = new BigDecimal(sJob.getJobQty());
//										BigDecimal perSecQty = bJobQty.divide(diffSecondsFromProcessStartToEnd, 10, BigDecimal.ROUND_HALF_UP);
										BigDecimal diffSecondsFromProcessStartToEnd = new BigDecimal(secondsBetween);
										BigDecimal bJobQty = new BigDecimal(sJob.getJobQty());
										perSecQty = bJobQty.divide(diffSecondsFromProcessStartToEnd, 10, BigDecimal.ROUND_HALF_UP);
										extraSeconds = remainShiftPlanQty.divide(perSecQty, 0, BigDecimal.ROUND_HALF_UP);
										LocalDateTime updateProcessEndTime = processEndTime.plusSeconds(extraSeconds.longValue());
										
										sJob.setProcessEndTime(updateProcessEndTime);
										
//										logger.debug("sJobId: " + sJob.getJobId()
//													+ " ,affectQty: " + affectQty
//													+ " ,remainShiftPlanQty: " + remainShiftPlanQty
//													+ " ,processStartTime: " + processStartTime
//													+ " ,processEndTime: " + processEndTime
//													+ " ,secondsBetween: " + secondsBetween
//													+ " ,bJobQty: " + bJobQty
//													+ " ,diffSecondsFromProcessStartToEnd: " + diffSecondsFromProcessStartToEnd
//													+ " ,perSecQty: " + perSecQty
//													+ " ,extraSeconds: " + extraSeconds
////													+ " ,updateProcessEndTime: " + updateProcessEndTime);
									}
								}
 							}
							
							if(affectQty > 0) {
								sJob.setForecastQty(affectQty);
								
								if(affectCapaQty>0) { //若設定換線影響by片數則該job processEndTime直接改該班結束時間
									Shift shift = sJob.getShiftList().get(sJob.getShiftIndex());
									LocalDateTime shiftProcEndTime = calcDateTimeByNum(mapParam, shift.getEndTimeIndex());
									sJob.setProcessEndTime(shiftProcEndTime);
								}
							}
							else
								sJob.setForecastQty(sJob.getJobQty());

						}
						previousSetupJobChangeKey = cModChange.getChangeKey();
					}
				}
				
//				logger.debug("iCnt: " + iCnt + " jobId: " + sJob.getJobId()+ " extraSeconds: " + extraSeconds + " jobQty: " + sJob.getJobQty()
//						+ " affectQty: " + sJob.getForecastQty()
//						+ " forecastQty: " + sJob.getForecastQty());
					
				//推遲的時間
				if(previousLineNo.equals(sJob.getLine()) && iCnt > 0) {
					LocalDateTime prevoiusProcessEndTime = sJobList.get(iCnt-1).getProcessEndTime();
					LocalDateTime currentProcessStartTime = sJob.getProcessStartTime();
					LocalDateTime currentChangeStartTime = sJob.getChangeStartTime();
					
					//當時間往後退且重疊才繼續推遲時間
					if(currentChangeStartTime != null && prevoiusProcessEndTime.isAfter(currentChangeStartTime)
							|| prevoiusProcessEndTime.isAfter(currentProcessStartTime)) {
						
						long secondsBetween = java.time.temporal.ChronoUnit.SECONDS.between(prevoiusProcessEndTime, currentProcessStartTime);
						if(sJob.getChangeDuration() > 0) {
							//取得指定換線時間 JoshLai@20190702+
							LocalDate planStartDate = (LocalDate) ((Map)(mapParam.get("plan_start_date"))).get("in_value1");
							LocalDate planEndDate = ((LocalDate) ((Map)(mapParam.get("plan_end_date"))).get("in_value1")).plusDays(1);
							List<Shift> shiftList = generateShiftList(planStartDate, planEndDate, mapParam, true);
							List<LocalDateTime> setupDateTimeList = generateSetupDateTimeList(shiftList, mapParam);
							
							//若有指定換線時間，則必須等到指定時間才換線
							if(setupDateTimeList.size() > 0) {
								for(LocalDateTime setupTime : setupDateTimeList) {
									if(setupTime.isEqual(sJob.getChangeStartTime()) || setupTime.isAfter(sJob.getChangeStartTime())) {
										secondsBetween = java.time.temporal.ChronoUnit.SECONDS.between(currentChangeStartTime, setupTime);
										break;
									}
								}
							}else
								secondsBetween = java.time.temporal.ChronoUnit.SECONDS.between(currentChangeStartTime, prevoiusProcessEndTime);
						}
						extraSeconds = new BigDecimal(secondsBetween).abs();
						
//						logger.debug("reCalc extrasSec: " + extraSeconds
//									+ " late time: " + secondsBetween 
//									+ " sJobId: " + sJob.getJobId()
//									+ " sJobPlanQty: " + sJob.getPlanQty()
//									+ " sJobQty: " + sJob.getJobQty()
//									+ " prevoiusProcessEndTime: " + prevoiusProcessEndTime
//								    + " currentProcessStartTime: " + currentProcessStartTime
//								    + " secondsBetween: " + secondsBetween
//								    + " extraSeconds: " + extraSeconds.abs());
						
						updateCurrentProcessAndChangeTime(sJob, extraSeconds.abs());
						
						//有設定affectCapaQty則該班結束時間為當班結束時間 JoshLai@20190808+
						boolean isFullDShift = mapCapa==null ? false : (boolean) mapCapa.get("IS_FULLDSHIFT"); //僅能補滿白班
						if(isFullDShift && (mapParamFollowPpcPlan!=null 
								|| mapParamFollowPpcPlan.get("in_value3")!=null
								|| "capabyshift".equals((String)mapParamFollowPpcPlan.get("in_value3")))
								&& sJob.getShiftIndex()%2==0
								&& sJob.getAffectCapaQty().intValue()>0) {
							Shift currentShift = sJob.getShiftList().get(sJob.getShiftIndex());
							LocalDateTime procEndTime = calcDateTimeByNum(mapParam, currentShift.getEndTimeIndex());
							sJob.setProcessEndTime(procEndTime);
						}
					}
				}
				
				previousLineNo = sJob.getLine();
				previousShift = sJob.getShift();
				previousPlanQty = sJob.getPlanQty();
				previousChangeKey = sJob.getChangeKey() == null ? "" : sJob.getChangeKey();
				previousJobId = sJob.getJobId();
				previousShiftIndex = sJob.getShiftIndex();
				
				//更新process & change shiftType
				LocalDate shiftDate = (LocalDate) calcShiftByProcStartTime(sJob.getProcessStartTime(), mapParam).get("SHIFT_DATE");
				String shiftType = (String) calcShiftByProcStartTime(sJob.getProcessStartTime(), mapParam).get("SHIFT_TYPE");
				sJob.setShiftDate(shiftDate);// 用process_start_tme換算
				sJob.setShift(shiftType);
				
				if(sJob.getChangeStartTime() != null) {
					LocalDate changeDate = (LocalDate) calcShiftByProcStartTime(sJob.getChangeStartTime(), mapParam).get("SHIFT_DATE");
					String changeType = (String) calcShiftByProcStartTime(sJob.getChangeStartTime(), mapParam).get("SHIFT_TYPE");
					sJob.setChangeShiftDate(changeDate);
					sJob.setChangeShift(changeType);
				}
			}
		}
		
		sortSJobDashboardList(sJobList);
		
		if(isDebug)
			logger.info(sJobList.size() + " before cross change listMap: " + sJobList);
		
		List<SJobDashboard> reviseListMap1 = new ArrayList<>();
		//跨班換線拆班
		reviseListMap1 = splitJobByChangeTime(sJobList, mapParam, false, false, false);
		if(isDebug)
			logger.info(reviseListMap1.size() + " change first split: " + reviseListMap1);
		//拆班後相同processTime合併
		reviseListMap1 = combineJobByChangeTime(reviseListMap1, mapParam, isDebug);
		
		//處理時間跨班切班
		List<SJobDashboard> reviseListMap2 = splitJobByProcessTime(reviseListMap1, cModChangeList, mapParam, isDebug);
		//拆班後相同processTime合併
		reviseListMap2 = combineJobByProcessTime(reviseListMap2, mapParam, isDebug);
		logger.info("reviseListMap2("+reviseListMap2.size()+"): " + reviseListMap2);
		
		Map<String, Object> changeCrossSetting = (Map<String, Object>) mapParam.get("change_cross_setting");
		if(changeCrossSetting!=null && (changeCrossSetting.get("in_value1").equals("change_cross_remove_next_shift") 
									|| changeCrossSetting.get("in_value1").equals("change_cross_shift_add_hr") )) {
			List<SJobDashboard> listMapTemp = new ArrayList<>();
			//S01/S02換線排量
			int idx=0;
			List<String> finishList = new ArrayList<>(); //存放已完成的JobId no seq,繞回圈時不需要再執行
			for(SJobDashboard sJob : reviseListMap2) {
				Integer affectCapaQty = sJob.getAffectCapaQty()==null? 0 : sJob.getAffectCapaQty();
				BigDecimal bAffectCapaQty = new BigDecimal(affectCapaQty);
				if(sJob.getProcessStartTime().isEqual(sJob.getProcessEndTime())
						&& sJob.getChangeDuration()>0 && sJob.getChangeDuration()<=12
						&& bAffectCapaQty.compareTo(BigDecimal.ZERO)>0) {
					
					LocalDateTime changeStartTime = sJob.getChangeStartTime();
					LocalDateTime changeEndTime = sJob.getChangeEndTime();
					long secondsBetween = ChronoUnit.SECONDS.between(changeStartTime, changeEndTime);
					BigDecimal bSecondsBetween = new BigDecimal(secondsBetween);
					bSecondsBetween = bSecondsBetween.multiply(new BigDecimal(0.1));
					LocalDateTime newSJobStartTime = changeEndTime.minusSeconds(bSecondsBetween.longValue());
					
					SJobDashboard newSJob = (SJobDashboard) sJob.clone();
					newSJob.setProcessStartTime(newSJobStartTime);
					newSJob.setChangeDuration(0);
					newSJob.setChangeLevel(null);
					newSJob.setChangeKey(null);
					newSJob.setChangeShift(null);
					newSJob.setChangeShiftDate(null);
					newSJob.setChangeStartTime(null);
					newSJob.setChangeEndTime(null);
					newSJob.setForecastQty(sJob.getAffectCapaQty());
					newSJob.setJobQty(sJob.getAffectCapaQty());
					
					calcCrossShiftSetting(reviseListMap2, sJob, bAffectCapaQty, finishList, mapParam, isDebug);
					listMapTemp.add(newSJob);
				}
				idx++;
			}
//			if(isDebug)
//				logger.debug(listMapTemp.size() + " listMapTemp: " + listMapTemp);
			reviseListMap2.addAll(listMapTemp);
		}
		
		sortSJobDashboardList(reviseListMap2);
		updateJobSeqAndShift(reviseListMap2, mapParam);
//		if(isDebug)
//			logger.debug("reviseListMap2("+reviseListMap2.size()+"): " + reviseListMap2);
		return reviseListMap2;
	}
	
	private static void addRemainShiftPlanQtyToNextShiftDateSJob(List<SJobDashboard> sJobList, BigDecimal remainShiftPlanQty, SJobDashboard targetSJob, Map<String, Object> mapParam) throws CloneNotSupportedException {
		int iFindIdx = -1;
		Map<String, Object> capaMap = (Map<String, Object>) mapParam.get("capa_map");
		for(int i=0; i<sJobList.size(); i++) {
			//當剩餘數量為0時終止迴圈
			if(remainShiftPlanQty.compareTo(BigDecimal.ZERO)==0)
				break;
			
			SJobDashboard sJob = sJobList.get(i);
			if(sJob.getJobId().equals(targetSJob.getJobId())) {
				iFindIdx = i;
			}
			
			//在targetSJob之後
			if(iFindIdx!=-1 && i>iFindIdx && sJob.getPartNo().equals(targetSJob.getPartNo())) {
				sJob.setJobQty(sJob.getForecastQty());
				
				String sJobIdWithNoSharp = sJob.getJobId().substring(0, sJob.getJobId().indexOf("##"));
				Map<String, Object> mapCapa = (Map)capaMap.get(sJobIdWithNoSharp);
				boolean isFullDShift = mapCapa==null ? false : (boolean) mapCapa.get("IS_FULLDSHIFT"); //僅能補滿白班
				BigDecimal bFabPcCapa = new BigDecimal((int)mapCapa.get("FAB_PC_CAPA"));
				BigDecimal bForecastQty = new BigDecimal(sJob.getForecastQty());
				
				bFabPcCapa = bFabPcCapa.divide(new BigDecimal(2), 0, RoundingMode.HALF_UP); //1班可跑的數量
				BigDecimal bSubQty = bFabPcCapa.subtract(bForecastQty); //可補滿到該班的數量
				bSubQty = bSubQty.min(remainShiftPlanQty);
				
				BigDecimal bReviseForecastQty = bForecastQty.add(bSubQty);
				sJob.setForecastQty(bReviseForecastQty.intValue());
				remainShiftPlanQty = remainShiftPlanQty.subtract(bSubQty);
				
				Shift currentShift = sJob.getShiftList().get(sJob.getShiftIndex());
				
				long secondsBetween = ChronoUnit.SECONDS.between(sJob.getProcessStartTime(), sJob.getProcessEndTime());
				BigDecimal diffSecondsFromProcessStartToEnd = new BigDecimal(secondsBetween);
				
				BigDecimal bJobQty = new BigDecimal(sJob.getJobQty());
				BigDecimal perSecQty = bJobQty.divide(diffSecondsFromProcessStartToEnd, 10, RoundingMode.HALF_UP);
				BigDecimal bRunSec = bReviseForecastQty.divide(perSecQty, 0, RoundingMode.CEILING);
				LocalDateTime reviseProcEndTime = sJob.getProcessStartTime().plusSeconds(bRunSec.longValue());
				if(bFabPcCapa.compareTo(bReviseForecastQty)==0) { //若已經補到滿則procEndTime設為該班結束時間
					reviseProcEndTime = calcDateTimeByNum(mapParam, currentShift.getEndTimeIndex());
				}
				sJob.setProcessEndTime(reviseProcEndTime);
				
				if(!isFullDShift) {//可補到夜班
					//尚有剩餘數量可補,則補滿繼續補夜班
					if(remainShiftPlanQty.compareTo(BigDecimal.ZERO)>0) {
						bRunSec = remainShiftPlanQty.divide(perSecQty, 0, RoundingMode.HALF_UP);
						LocalDateTime newProcStartTime = calcDateTimeByNum(mapParam, currentShift.getEndTimeIndex());
						LocalDateTime newProcEndTime = newProcStartTime.plusSeconds(bRunSec.longValue());
						
						SJobDashboard newJob = (SJobDashboard) sJob.clone();
						String[] newJobIds = newJob.getJobId().split("##");
						int newJobIdSeq = Integer.parseInt(newJobIds[1]) + 1;
						String newJobId = newJobIds[0]+"##"+newJobIdSeq;
						newJob.setJobId(newJobId);
						newJob.setProcessStartTime(newProcStartTime);
						newJob.setProcessEndTime(newProcEndTime);
						newJob.setForecastQty(remainShiftPlanQty.intValue());
						newJob.setJobQty(newJob.getForecastQty());
						newJob.setShiftIndex(sJob.getShiftIndex()+1);
						
						LocalDate shiftDate = (LocalDate) calcShiftByProcStartTime(newJob.getProcessStartTime(), mapParam).get("SHIFT_DATE");
						String shiftType = (String) calcShiftByProcStartTime(newJob.getProcessStartTime(), mapParam).get("SHIFT_TYPE");
						newJob.setShiftDate(shiftDate);// 用process_start_tme換算
						newJob.setShift(shiftType);
						
						newJob.setAffectCapaPercent(null);
						newJob.setAffectCapaQty(null);
						newJob.setShiftCountAfterChange(null);
						newJob.setChangeShift(null);
						newJob.setChangeShiftDate(null);
						newJob.setChangeKey(null);
						newJob.setChangeDuration(0);
						newJob.setChangeLevel(null);
						newJob.setChangeStartTime(null);
						newJob.setChangeEndTime(null);
						sJobList.add(newJob);
						sortSJobDashboardList(sJobList);
						
						remainShiftPlanQty = remainShiftPlanQty.subtract(remainShiftPlanQty);
					}
				}
				
				//不同條線時終止迴圈
				if(i+1 < sJobList.size()) {
					if(!sJobList.get(i+1).getLine().equals(sJob.getLine())) {
						remainShiftPlanQty = new BigDecimal(0);
						break;
					}
				}
			}
		}
	}
	
	//將AffectCapaQty搬移出來 JoshLai@20190620+
	private static void calcCrossShiftSetting(List<SJobDashboard> sJobList, SJobDashboard targetSJob, BigDecimal bAffectCapaQty, List<String> finishList, Map<String, Object> mapParam, boolean isRun) {
		String targetSJobId = targetSJob.getJobId().split("##")[0];
		for(SJobDashboard sJob : sJobList) {
			String sJobId = sJob.getJobId().split("##")[0];
			if(sJobId.equals(targetSJobId) && (sJob.getProcessStartTime().isEqual(targetSJob.getProcessStartTime())
					|| sJob.getProcessStartTime().isAfter(targetSJob.getProcessStartTime()))) {
				if(sJob.getForecastQty()>0) {
					sJob.setForecastQty(0);
					sJob.setJobQty(0);
					break;
				}
			}
		}
	}
	
	private static LocalDateTime getShiftProcessEndTime(LocalDate shiftDate, String shift, LocalDateTime processEndTime, Map<String, Object> mapParam) {
		LocalDateTime shiftProcessEndTime = null;
		int iShiftDstart = Integer.parseInt((String) ((Map)mapParam.get("shift_d_start")).get("in_value1"));
		int iShiftEstart = Integer.parseInt((String) ((Map)mapParam.get("shift_e_start")).get("in_value1"));
		
		if("D".equals(shift)) {
			shiftProcessEndTime = LocalDateTime.of(shiftDate.getYear(), shiftDate.getMonthValue(), shiftDate.getDayOfMonth(), iShiftEstart, 0);
		}else {
			shiftDate = shiftDate.plusDays(1);
			shiftProcessEndTime = LocalDateTime.of(shiftDate.getYear(), shiftDate.getMonthValue(), shiftDate.getDayOfMonth(), iShiftDstart, 0);
		}
		return shiftProcessEndTime;
	}
	
	private static void updateCurrentProcessAndChangeTime(SJobDashboard sJob, BigDecimal extraSeconds) {
		LocalDateTime currentProcessStartTime = sJob.getProcessStartTime();
		LocalDateTime currentProcessEndTIme = sJob.getProcessEndTime();
		LocalDateTime currentChangeStartTime = sJob.getChangeStartTime();
		LocalDateTime currentChangeEndTime = sJob.getChangeEndTime();
		
		LocalDateTime updateProcessStartTime = currentProcessStartTime.plusSeconds(extraSeconds.longValue());
		sJob.setProcessStartTime(updateProcessStartTime);
		LocalDateTime updateProcessEndTime = currentProcessEndTIme.plusSeconds(extraSeconds.longValue());
		sJob.setProcessEndTime(updateProcessEndTime);
		
		if(sJob.getChangeStartTime() != null) {
			LocalDateTime updateChangeStartTime = currentChangeStartTime.plusSeconds(extraSeconds.longValue());
			sJob.setChangeStartTime(updateChangeStartTime);
			LocalDateTime updateChangeEndTime = currentChangeEndTime.plusSeconds(extraSeconds.longValue());
			sJob.setChangeEndTime(updateChangeEndTime);
		}
	}
	
	//取得最後一次換線資料by線別
	public static List<RJobDashboard> getLastChangeInfoByJob(List<RJobDashboard> rJobDashboardList, SJobDashboard sJob){
		List<RJobDashboard> tempList = new ArrayList<>(rJobDashboardList);
		Comparator<RJobDashboard> comparator = Comparator.comparing(RJobDashboard::getLine)
				.thenComparing(RJobDashboard::getProcessEndTime);
		Collections.sort(tempList, comparator.reversed());
		
		List<RJobDashboard> lastChangeList = new ArrayList<>();
		Set<String> shiftDateContainer = new HashSet<>();
		
		for(RJobDashboard r : tempList) {
			String shiftDateAndShiftType = r.getShiftDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")) 
					+ r.getShift();
					
			if(sJob.getLine().equals(r.getLine()) && r.getChangeDuration()>0) {
				String rjobShift = r.getShift();
				LocalDate rjobShiftDate = r.getShiftDate();
				
				String sJobShift = sJob.getShift();
				LocalDate sJobShiftDate = sJob.getShiftDate(); 
				if(lastChangeList.size() > 0) {
					sJobShift = lastChangeList.get(lastChangeList.size()-1).getShift();
					sJobShiftDate = lastChangeList.get(lastChangeList.size()-1).getShiftDate();
					
					rjobShift = r.getShift();
					rjobShiftDate = r.getShiftDate();
				}
				//前4班資料
				if(shiftDateContainer.size() <= 4) {
					if(rjobShiftDate.isEqual(sJobShiftDate) && !rjobShift.equals(sJobShift)) {
						lastChangeList.add(r);
					}else if(rjobShiftDate.isBefore(sJobShiftDate)) {
						lastChangeList.add(r);
					}
				}
				shiftDateContainer.add(shiftDateAndShiftType);
			}
		}
//		logger.debug(shiftDateContainer.size() + " shiftDateContainer: " + shiftDateContainer);
//		logger.debug("jobId: " + sJob.getJobId() + " => "+ lastChangeList.size() + " lastChangeList: " + lastChangeList);
		return lastChangeList;
	}
	
	public static boolean isFindNeighborShift(LocalDate previousShiftDate, String previousShift, LocalDate currentShiftDate, String currentShift) {
		//now: 16D -> previous: 15N
		//now: 16N -> previous: 16D
		boolean isFindLastChangeShift = false;
		LocalDate lastShiftDate = previousShiftDate;
		String lastShift = previousShift;
		long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(lastShiftDate, currentShiftDate);
		
		if("D".equals(currentShift)) {
			if(daysBetween == 1 && "N".equals(lastShift)) {
				isFindLastChangeShift = true;
			}else if(daysBetween == 0 && "D".equals(lastShift)) {
				isFindLastChangeShift = true;
			}
		}else {
			if(daysBetween == 0) {
				isFindLastChangeShift = true;
			}
		}
		return isFindLastChangeShift;
	}
	
	//取得最後一次換線資料
	public static RJobDashboard getLastChangeRJobByChangeShiftDate(List<Object> historicalList, String line, int planStartShiftIndex, Map<String, Object> mapParam) throws CloneNotSupportedException {
		RJobDashboard rJobDashboard = null;
		List<Object> historicalListIn7Days = new ArrayList<>();
		boolean isFindLastChange = false;
		
		if(historicalList.size() > 0) {
			LocalDate planStart = (LocalDate) ((Map)mapParam.get("plan_start_date")).get("in_value1");
			
			String previousLine = null;
			LocalDate previousShiftDate = null;
			String previousShift = null;
			
			for(Object historyJob : historicalList) {
				LocalDate historyShiftDate = null;
				String historyLineNo = null;
				String hisotryShift = null;
				String jobType = null;
				float changeDuration = 0;
				if(historyJob instanceof Adjustment) {
					historyShiftDate = ((Adjustment) historyJob).getAdjustmentId().getShiftDate();
					historyLineNo =  ((Adjustment) historyJob).getAdjustmentId().getLine();
					hisotryShift = ((Adjustment) historyJob).getAdjustmentId().getShift();
					jobType = ((Adjustment) historyJob).getAdjustmentId().getJobType();
					String sChangeDurtaion = ((Adjustment) historyJob).getDuration();
					if(NumberUtils.isDigits(sChangeDurtaion))
						changeDuration = Float.parseFloat((sChangeDurtaion));
				}else if(historyJob instanceof RJobDashboard){
					historyShiftDate = ((RJobDashboard) historyJob).getShiftDate();
					historyLineNo =  ((RJobDashboard) historyJob).getLine();
					hisotryShift = ((RJobDashboard) historyJob).getShift();
					jobType = ((RJobDashboard) historyJob).getJobType();
					changeDuration = ((RJobDashboard) historyJob).getChangeDuration();
				}
				
				//只取每班最大的runSeq換線 JoshLai@20190610+
				long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(historyShiftDate, planStart);
				if(line.equals(historyLineNo) && !historyLineNo.equals(previousLine) 
						&& !historyShiftDate.equals(previousShiftDate) 
						&& !hisotryShift.equals(previousShift) && daysBetween<=7
						&& !SPECIFIC_JOB_TYPE.contains(jobType)
						&& changeDuration > 0) {
					historicalListIn7Days.add(historyJob);
				}
				
				previousLine = historyLineNo;
				previousShiftDate = historyShiftDate;
				previousShift = hisotryShift;
			}
			
	    	//把該Job的換線資料合併在一起,才知道有沒有剩餘的換線時間
			if(historicalListIn7Days.size() > 0) {
				String previousPartNo = null;
				String previousModelNo = null;
				String previousChangeKey = null;
				float previousChangeDurtaion = 0;
				RJobDashboard firstRJob = null;
				int previousShiftIndex = 0;
				
				if(historicalListIn7Days.get(0) instanceof Adjustment) {
					Adjustment adjustment = (Adjustment) historicalListIn7Days.get(0);
					String sChangeDurtaion = adjustment.getDuration();
					if(NumberUtils.isDigits(sChangeDurtaion))
						previousChangeDurtaion = Float.parseFloat((sChangeDurtaion));
					previousChangeKey = adjustment.getAdjustmentId().getChangeKey() == null ? "" : adjustment.getAdjustmentId().getChangeKey();
					previousPartNo = adjustment.getPartNo();
					previousModelNo = adjustment.getAdjustmentId().getModelNo();
					previousShiftIndex = adjustment.getShiftIndex();
					
					firstRJob = new RJobDashboard();
					firstRJob.setSite(adjustment.getAdjustmentId().getSite());
					firstRJob.setFab(adjustment.getAdjustmentId().getFab());
					firstRJob.setLine(adjustment.getAdjustmentId().getLine());
					firstRJob.setShiftDate(adjustment.getAdjustmentId().getShiftDate());
					firstRJob.setShift(adjustment.getAdjustmentId().getShift());
					firstRJob.setJobType(adjustment.getAdjustmentId().getJobType());
					firstRJob.setChangeDuration(previousChangeDurtaion);
					firstRJob.setChangeKey(previousChangeKey);
					firstRJob.setChangeLevel(adjustment.getChangeLevel());
					firstRJob.setPartNo(previousPartNo);
					firstRJob.setModelNo(previousModelNo);
					firstRJob.setAssignShiftDate(adjustment.getAdjustmentId().getShiftDate());
					
				}else if(historicalListIn7Days.get(0) instanceof RJobDashboard){
					RJobDashboard rJob = (RJobDashboard) historicalListIn7Days.get(0);
					previousChangeDurtaion = rJob.getChangeDuration();
					previousChangeKey = rJob.getChangeKey() == null ? "" : rJob.getChangeKey();
					previousPartNo = rJob.getPartNo();
					previousModelNo = rJob.getModelNo();
					previousShiftIndex = rJob.getShiftIndex();
					firstRJob = (RJobDashboard) rJob.clone();
				}
				
				for(Object hJob : historicalListIn7Days) {
					String partNo = null;
					String modelNo = null;
					String changeKey = null;
					float changeDuration = 0;
					int shiftIndex = 0;
					if(hJob instanceof Adjustment) {
						String sChangeDurtaion = ((Adjustment) hJob).getDuration();
						if(NumberUtils.isDigits(sChangeDurtaion))
							changeDuration = Float.parseFloat(sChangeDurtaion);
						changeKey = ((Adjustment) hJob).getAdjustmentId().getChangeKey();
						partNo = ((Adjustment) hJob).getPartNo();
						modelNo = ((Adjustment) hJob).getAdjustmentId().getModelNo();
						shiftIndex = ((Adjustment) hJob).getShiftIndex();
					}else if(hJob instanceof RJobDashboard){
						changeDuration = ((RJobDashboard) hJob).getChangeDuration();
						changeKey =((RJobDashboard) hJob).getChangeKey();
						partNo = ((RJobDashboard) hJob).getPartNo();
						modelNo = ((RJobDashboard) hJob).getModelNo();
						shiftIndex = ((RJobDashboard) hJob).getShiftIndex();
					}
					
					if(previousPartNo.equals(partNo)
						&& previousModelNo.equals(modelNo)
						&& previousChangeKey.equals(changeKey)
						&& ((shiftIndex - previousShiftIndex) == 0 || ((shiftIndex - previousShiftIndex) == 1))) {
						
						firstRJob.setChangeKey(changeKey);
						float chgDuration = changeDuration;
						if(isFindLastChange)
							chgDuration = firstRJob.getChangeDuration() + changeDuration;
						firstRJob.setChangeDuration(chgDuration);
						
						isFindLastChange = true;
					}else {
						if(!isFindLastChange)
							isFindLastChange = false;
						break;
					}
					
					previousChangeKey = changeKey == null ? "" : changeKey;
					previousPartNo = partNo;
					previousModelNo = modelNo;
					previousChangeDurtaion = changeDuration;
					previousShiftIndex = shiftIndex;
				}
				
				if(isFindLastChange) {
					rJobDashboard = firstRJob;
				}
			}
		}
		return rJobDashboard;
	}
	
	//同一條線上assignShiftDate必須照順序遞增排
	public static Map<String, Object> assignShiftDateMustAsc(List<SJobDashboard> sJobList) {
		Map<String, Object> mapResult = new HashMap<String, Object>();
		int hardScore = 0;
    	if(sJobList.size() > 0){
    		SJobDashboard previousSJob = (SJobDashboard)sJobList.get(0);
			LocalDate previousAssignShiftDate = previousSJob.getShiftDate();
			String previousLineNo = previousSJob.getLine();
			
			for(int i=0; i<sJobList.size(); i++){
				SJobDashboard sJob = (SJobDashboard)sJobList.get(i);
				if(previousLineNo.equals(sJob.getLine())
					&& previousAssignShiftDate.isAfter(sJob.getShiftDate())){
					hardScore -= 1;
					String key = sJob.getSite()+"|"+sJob.getLine()+"|"+sJob.getJobId()+"|"+sJob.getAssignShiftDate().toString();
					mapResult.put(key, hardScore);
				}
				previousAssignShiftDate = sJob.getShiftDate();
				previousLineNo = sJob.getLine();
			}
		}
    	mapResult.put("is_assign_shift_date", new HashMap<>());
		return mapResult;
	}
	
	public static List<Object> getLastChangeRJobUnionAdjustByShiftDate(List<Adjustment> adjustmentAllAreaList, List<RJobDashboard> rJobDashboardList, List<String> areaList, Map<String, Object> mapParam){
		List<Object> historicalList = new ArrayList<>();
		LocalDate planStartDate = (LocalDate) ((Map)mapParam.get("plan_start_date")).get("in_value1");
		planStartDate = planStartDate.minusDays(1);
		LocalDate before7days = planStartDate.minusDays(7-1);
		int daysBetween = calcDasyBetween(before7days, planStartDate);
		List<LocalDate> planDayList = new ArrayList<>();
		for(int i=0; i<=daysBetween; i++) {
			planDayList.add(before7days.plusDays(i));
		}
		Collections.sort(planDayList, Collections.reverseOrder());
		
		//只需要取得JSON.areaList相同的adjustment, JSON.area=JI則adjustmentList餵入JI,反之MA
		List<Adjustment> adjustmentList = new ArrayList<>();
		for(Adjustment adjustment : adjustmentAllAreaList) {
			if(ModulePlannerUtility.AREA_JI.equals(adjustment.getAdjustmentId().getArea())
					&& areaList.contains(ModulePlannerUtility.AREA_JI)) {
				adjustmentList.add(adjustment);
			}else if(ModulePlannerUtility.AREA_MA.equals(adjustment.getAdjustmentId().getArea())
					&& areaList.contains(ModulePlannerUtility.AREA_MA)) {
				adjustmentList.add(adjustment);
			}
		}
		//JOB_TYPE為CHANGE時只取runSeq最大的
		getMaxRunSeqFromAdjustmentList(adjustmentList);
		
		//取得班別index list
    	LocalDate historyStartTime = ((LocalDateTime) ((Map)(mapParam.get("history_start_time"))).get("in_value1")).toLocalDate();
    	LocalDate historyEndTime = ((LocalDateTime) ((Map)(mapParam.get("history_end_time"))).get("in_value1")).toLocalDate();
    	boolean isSortByDateASC = false;
        List<Shift> shiftList = generateShiftList(historyStartTime, historyEndTime, mapParam, isSortByDateASC);
		
		for(Adjustment adjustment : adjustmentList) {
			adjustment.setShiftList(shiftList);
		}
//		logger.debug(adjustmentList.size() + " adjustmentList: " + adjustmentList + " planDayList: " + planDayList);
		
		for(LocalDate planDay : planDayList) {
			//先拿手動調整
			List<Adjustment> adjustList = getAdjustByShiftDate(adjustmentList, planDay);
//			logger.debug("planDay: " + planDay + " adjustList.size(): " + adjustList.size() + " adjustList: " + adjustList);
			historicalList.addAll(adjustList);
			//無手動調整才拿RJob歷史資料
			if(adjustList.size() == 0) {
				List<RJobDashboard> rJobList = getRJobByShiftDate(rJobDashboardList, planDay, shiftList);
				historicalList.addAll(rJobList);
			}
		}
		
		Collections.sort(historicalList, new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				String lineNo1, lineNo2;
				LocalDateTime processEndTime1, processEndTime2;
				LocalDate shiftDate1, shiftDate2;
				String shift1, shift2;
				int runSeq1=0, runSeq2=0;
				if(o1 instanceof Adjustment) {
					lineNo1 = ((Adjustment) o1).getAdjustmentId().getLine();
					processEndTime1 = null;
					shiftDate1 = ((Adjustment) o1).getAdjustmentId().getShiftDate();
					shift1 = ((Adjustment) o1).getAdjustmentId().getShift();
					if(NumberUtils.isDigits(((Adjustment) o1).getRunSeq()))
						runSeq1 = Integer.parseInt(((Adjustment) o1).getRunSeq());
				}else {
					lineNo1 = ((RJobDashboard) o1).getLine();
					processEndTime1 = ((RJobDashboard) o1).getProcessEndTime();
					shiftDate1 = ((RJobDashboard) o1).getShiftDate();
					shift1 = ((RJobDashboard) o1).getShift();
				}
				
				if(o2 instanceof Adjustment) {
					lineNo2 = ((Adjustment) o2).getAdjustmentId().getLine();
					processEndTime2 = null;
					shiftDate2 = ((Adjustment) o2).getAdjustmentId().getShiftDate();
					shift2 = ((Adjustment) o2).getAdjustmentId().getShift();
					if(NumberUtils.isDigits(((Adjustment) o2).getRunSeq()))
						runSeq2 = Integer.parseInt(((Adjustment) o2).getRunSeq());
				}else {
					lineNo2 = ((RJobDashboard) o2).getLine();
					processEndTime2 = ((RJobDashboard) o2).getProcessEndTime();
					shiftDate2 = ((RJobDashboard) o2).getShiftDate();
					shift2 = ((RJobDashboard) o2).getShift();
				}
				
				int lineNoCompare = lineNo1.compareTo(lineNo2); 
				if(lineNoCompare != 0)
					return lineNoCompare;
				else {
					//回傳值: -1 前者比後者小, 0 前者與後者相同, 1 前者比後者大, 回傳-1代表把元件向前排;回傳1代表把元件向後排
					if(shiftDate1.isBefore(shiftDate2))
						return 1;
					else if(shiftDate1.isAfter(shiftDate2))
						return -1;
					else {
						if("D".equals(shift1) && "N".equals(shift2))
							return 1;
						else if("N".equals(shift1) && "D".equals(shift2))
							return -1;
						else {
							if(processEndTime1 != null && processEndTime2 != null) {
								if(processEndTime1.isBefore(processEndTime2))
									return 1;
								else if(processEndTime1.isAfter(processEndTime2))
									return -1;
								else {
									if(runSeq1 < runSeq2) //add runSeq by JoshLai@20190603+
										return 1;
									else if(runSeq1 > runSeq2)
										return -1;
									else return 0;
								}
							}else
								return 0;
						}
					}
				}
			}
		});
		return historicalList;
	}
	
	private static List<Adjustment> getAdjustByShiftDate(List<Adjustment> adjustmentList, LocalDate planDay) {
		List<Adjustment> adjustList = new ArrayList<>();
		for(Adjustment adjustJob : adjustmentList) {
			if(planDay.isEqual(adjustJob.getAdjustmentId().getShiftDate())) {
				adjustList.add(adjustJob);
			}
		}
		return adjustList;
	}
	
	private static List<RJobDashboard> getRJobByShiftDate(List<RJobDashboard> rJobDashboardList, LocalDate planDay, List<Shift> shiftList) {
		List<RJobDashboard> rJobList = new ArrayList<>();
		for(RJobDashboard rJob : rJobDashboardList) {
			if(planDay.isEqual(rJob.getShiftDate())) {
				rJob.setShiftList(shiftList);
				rJobList.add(rJob);
			}
		}
		return rJobList;
	}
	
	public static List<SJobDashboard> doPlanResultProcess(List<TimeWindowedJob> jobsInLine, Map<String, Object> mapParam, List<Woxx> woxxList, boolean isRun) throws CloneNotSupportedException {
		List<SJobDashboard> listPlanResult = new ArrayList<>();
		//先按照時間順序排再扣工單
		Comparator<TimeWindowedJob> comparator = Comparator.comparing(TimeWindowedJob::getLineNo)
				.thenComparing(TimeWindowedJob::getStartTime, Comparator.nullsFirst(Comparator.naturalOrder()));
		Collections.sort(jobsInLine, comparator);
		
		setChangeDurationByTimeWindowedJob(jobsInLine);
		
		//直接將TimedWindowedJob轉成SJobDashboard,不做重疊換線,跨班換線,換線影響等處理 JoshLai@20190627+
		boolean isJustSplitSJob = mapParam.get("just_split_sjob")!=null ? true : false;
		if(!isJustSplitSJob) {
			if(isRun) {
				logger.info(jobsInLine.size() + " jobsInLine-1: " + jobsInLine);
				//判斷重疊換線 JoshLai@20190429
				long lStartSjob = System.currentTimeMillis();
				calcOverlapping(mapParam, jobsInLine);
				logger.info(jobsInLine.size() + " jobsInLine-2: " + jobsInLine);
				long lEndSjob = System.currentTimeMillis() - lStartSjob;
				logger.debug("calcOverlapping Time elapsed: " + lEndSjob/1000 + " seconds");
//				logger.debug(jobsInLine.size() + " after calcOverlapping: " + jobsInLine);
				
				//S01換線排量 Change duration<=12 不允許跨班換線，只換到當班
				calcChangeCrossOnly1Sfhit(jobsInLine, mapParam, isRun);
				logger.info(jobsInLine.size() + " jobsInLine-3: " + jobsInLine);
				
				//不允許跨班換線,若有跨班換線則延到下一班才開始換線
				calcCrossChange(mapParam, jobsInLine);
				logger.info(jobsInLine.size() + " jobsInLine-4: " + jobsInLine);
//				logger.debug(jobsInLine.size() + " after calcCrossChange: " + jobsInLine);
				
				//重疊換線會造成startTime改變,重新再排序一次 JoshLai@20190619+
				Collections.sort(jobsInLine, comparator);
				logger.info(jobsInLine.size() + " jobsInLine-5: " + jobsInLine);
			}
		}
		List<SJobDashboard> sJobDashboardList = new ArrayList<>();
		for(TimeWindowedJob job : jobsInLine) {
			//TimeWindowedJob轉SJobDashboard
			List<SJobDashboard> tempList = splitByShift(job, mapParam, isRun);
			
			if(!isJustSplitSJob) {
				//跨班換線時程增加,增加該job時長
				tempList = changeCrossShiftAddHour(tempList, mapParam); //Markup by JoshLai@20190327
			}
			sJobDashboardList.addAll(tempList);
		}
		
		int iSeq = 0;
		for(SJobDashboard sJob : sJobDashboardList) {
			sJob.setiSeq(iSeq);
			iSeq++;
		}
		
		if(isRun)
			logger.info(sJobDashboardList.size() + " before splitJobByChangeTime: " + sJobDashboardList);
		if(!isJustSplitSJob) {
			try {
				//跨班換線拆班
				List<SJobDashboard> splittedByChangeTimeJobList = splitJobByChangeTime(sJobDashboardList, mapParam, true, true, isRun);
				sJobDashboardList = splittedByChangeTimeJobList;
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		listPlanResult.addAll(sJobDashboardList);
		
		sortSJobDashboardList(listPlanResult);
		if(isRun)
			logger.info(listPlanResult.size() + " after splitJobByChangeTime: " + listPlanResult);
		
		return listPlanResult; 
	}
	
	public static List<ShiftDate> generateShiftDateList(Map<LocalDate, ShiftDate> shiftDateMap, LocalDate planStartDate, LocalDate planEndDate){
    	int maxDayIndex = Math.toIntExact(DAYS.between(planStartDate, planEndDate));
        int shiftDateSize = maxDayIndex + 1;
        List<ShiftDate> shiftDateList = new ArrayList<>(shiftDateSize);
        long id = 0L;
        int dayIndex = 0;
        LocalDate date = planStartDate;
        
        for (int i = 0; i < shiftDateSize; i++) {
            ShiftDate shiftDate = new ShiftDate();
            shiftDate.setId(id);
            shiftDate.setDayIndex(dayIndex);
            shiftDate.setDate(date);
            shiftDate.setShiftList(new ArrayList<>());
            shiftDateList.add(shiftDate);
            shiftDateMap.put(date, shiftDate);
            id++;
            dayIndex++;
            date = date.plusDays(1);
        }
        return shiftDateList;
    }
	
	public static List<Shift> generateShiftList(LocalDate planStartDate, LocalDate planEndDate, Map<String, Object> mapParam, boolean isSortByDateASC) {
		long id = 0l;
		Map<LocalDate, ShiftDate> shiftDateMap = new HashMap<>();
		List<ShiftDate> shiftDateList = ModulePlannerUtility.generateShiftDateList(shiftDateMap, planStartDate, planEndDate);
		int shiftListSize = shiftDateMap.size() * shiftDateList.size();
		List<Shift> shiftList = new ArrayList<>(shiftListSize);
		int index = 0;
		int i = 0;
		List<ShiftType> shiftTypeList = generateShiftType(mapParam);
		
		//以planStartDate為0點，往右增加shiftDate index遞增，往左減少shiftDate，index遞增  JoshLai@20190415+
		//ex: 4/12 index:2, 4/13 index:1, 4/14:index:0, 4/15:index:1, 4/16 index:2
		Comparator<ShiftDate> comparatorDate = Comparator.comparing(ShiftDate::getDate);
		Comparator<ShiftType> comparatorType = Comparator.comparing(ShiftType::getCode);
		if(isSortByDateASC) {
			Collections.sort(shiftDateList);
		}else {
			Collections.sort(shiftDateList, comparatorDate.reversed());
			Collections.sort(shiftTypeList, comparatorType.reversed());
		}
		
		for (ShiftDate shiftDate : shiftDateList) {
			for (ShiftType shiftType : shiftTypeList) {
				Shift shift = new Shift();
				shift.setId(id);
				shift.setShiftDate(shiftDate);
				shiftDate.getShiftList().add(shift);
				shift.setShiftType(shiftType);
				shift.setIndex(index);
				if ("D".equals(shift.getShiftType().getCode())) {
					shift.setStartTimeIndex(i * 24 + 0); // 0, 24, 48, 72
				} else {
					shift.setStartTimeIndex(i * 24 + 12);// 12,36,60
				}
				shift.setEndTimeIndex(shift.getStartTimeIndex() + 12);
				shiftList.add(shift);
				id++;
				index++;
			}
			i++;
		}
		return shiftList;
	}
	
	private static List<ShiftType> generateShiftType(Map<String, Object> mapParam){
    	List<ShiftType> shiftTypeList = new ArrayList<ShiftType>();
    	ShiftType shiftType = new ShiftType();
    	
    	int shiftDstart = Integer.parseInt(String.valueOf(((Map)mapParam.get("shift_d_start")).get("in_value1")));
    	int shiftNstart = Integer.parseInt(String.valueOf(((Map)mapParam.get("shift_e_start")).get("in_value1")));
    	
        shiftType.setId(0l);
        shiftType.setCode("D");
        shiftType.setIndex(0);
        String startTimeString = String.format("%02d", shiftDstart) + ":00";
        shiftType.setStartTimeString(startTimeString);
        String endTimeString = String.format("%02d", shiftNstart) + ":00";
        shiftType.setEndTimeString(endTimeString);
        shiftType.setNight(startTimeString.compareTo(endTimeString) > 0);
        shiftTypeList.add(shiftType);
        
        shiftType = new ShiftType();
        shiftType.setId(1l);
        shiftType.setCode("N");
        shiftType.setIndex(0);
        startTimeString = String.format("%02d", shiftNstart) + ":00";
        shiftType.setStartTimeString(startTimeString);
        endTimeString = String.format("%02d", shiftDstart) + ":00";
        shiftType.setEndTimeString(endTimeString);
        shiftType.setNight(startTimeString.compareTo(endTimeString) > 0);
        shiftTypeList.add(shiftType);
        
        return shiftTypeList;
    }
	
	//相同line_id, part_no, grade且連續Shift_Date的排程加總
	// 已併入FullLoad JoshLai@20190614-
	/*public static List<Plan> calcContinuousRJobPlan(List<Plan> rJobPlanList, List<Object> historicalList, List<Special> rJobSpecialList, List<EqpCapa> cEqpCapaList, Map<String, Object> mapParam) {
		List<Plan> planList = new ArrayList<>();
		LocalDate planStartDate = (LocalDate) ((Map)mapParam.get("plan_start_date")).get("in_value1");
		
		//Step1 計算超前量/落後量
		//2. history > ppc plan --> 超前
		Map<String, Object> mapOverProduceQty = new HashMap<>();//超前量
		
		//1. history < ppc plan --> 落後
		Map<String, Object> mapUnderProduceQty = new HashMap<>();//落後量
		LocalDate planStartDateMinus1 = planStartDate.minusDays(1);
		
		logger.info(historicalList.size() + " historicalList: " + historicalList);
		
		Map<String, Object> mapSumHistoryQty = new HashMap<>();
		Map<String, Object> mapSumPlanQty = new HashMap<>();
		
		//改先各別加總再計算落後量 JoshLai@20190430
		//加總歷史資料排程前一天的量
		for(Object job : historicalList) {
			String historyLineNo = null;
			String historyPartNo = null;
			LocalDate historyShiftDate = null;
			BigDecimal historyForecastQty = null;
			String historyGrade = null;
			String historyJobType = null;
			if(job instanceof Adjustment) {
				historyLineNo = ((Adjustment) job).getAdjustmentId().getLine();
				historyPartNo = ((Adjustment) job).getPartNo();
				historyShiftDate = ((Adjustment) job).getAdjustmentId().getShiftDate();
				historyForecastQty = new BigDecimal(((Adjustment) job).getPlanQty());
				historyGrade = ((Adjustment) job).getAdjustmentId().getGrade();
				historyJobType = ((Adjustment) job).getAdjustmentId().getJobType();
			}else if(job instanceof RJobDashboard) {
				historyLineNo = ((RJobDashboard) job).getLine();
				historyShiftDate = ((RJobDashboard) job).getShiftDate();
				historyForecastQty = new BigDecimal(((RJobDashboard) job).getForecastQty());
				historyPartNo = ((RJobDashboard) job).getPartNo();
				historyGrade = ((RJobDashboard) job).getGrade();
				historyJobType = ((RJobDashboard) job).getJobType();
			}
			if(historyShiftDate.isEqual(planStartDateMinus1) && "PROD".equals(historyJobType)) {
				String strShiftDate = historyShiftDate.format(formatterNoSlash);
				String mapKey = historyLineNo+"##"+strShiftDate+"##"+historyPartNo+"##"+historyGrade;
				
				if(mapSumHistoryQty.get(mapKey) == null) {
					mapSumHistoryQty.put(mapKey, historyForecastQty);
				}else {
					//拿出數量加總後放回去
					BigDecimal bQty = (BigDecimal) mapSumHistoryQty.get(mapKey);
					BigDecimal bSumQty = bQty.add(historyForecastQty);
					mapSumHistoryQty.put(mapKey, bSumQty);
				}
			}
		}
		logger.info(mapSumHistoryQty.size() + " mapSumHistoryQty: " + mapSumHistoryQty);
		
		//加總PPC plan排程前一天的量
		for(Plan plan : rJobPlanList) {
			String planLineNo = plan.getLine();
			String planPartNo = plan.getPartNo();
			LocalDate planShiftDate = plan.getShiftDate();
			BigDecimal planQty = new BigDecimal(plan.getPlanQty());
			String planGrade = plan.getGrade();
			String planJobType = plan.getJobType();
			
			if(planShiftDate.isEqual(planStartDateMinus1) && "PROD".equals(planJobType)) {
				String strShiftDate = planShiftDate.format(formatterNoSlash);
				String mapKey = planLineNo+"##"+strShiftDate+"##"+planPartNo+"##"+planGrade;
				
				if(mapSumPlanQty.get(mapKey) == null) {
					mapSumPlanQty.put(mapKey, planQty);
				}else {
					//拿出數量加總後放回去
					BigDecimal bQty = (BigDecimal) mapSumPlanQty.get(mapKey);
					BigDecimal bSumQty = bQty.add(planQty);
					mapSumPlanQty.put(mapKey, bSumQty);
				}
			}
		}
		logger.info(mapSumPlanQty.size() + " mapSumPlanQty: " + mapSumPlanQty);
		
		//超前
		for(Map.Entry<String, Object> historyEntry : mapSumHistoryQty.entrySet()) {
			String mapKey = historyEntry.getKey();
			BigDecimal bHistoryQty = (BigDecimal) historyEntry.getValue();
			
			if(mapSumPlanQty.get(mapKey)!=null) {
				BigDecimal bPlanQty = (BigDecimal) mapSumPlanQty.get(mapKey);
				
				//PPC plan減去history,若ppc plan數量小於history則相差數量為超前量
				//6.前一天有超前，當天(plan start date)有ppc plan --> 當天ppc plan要扣掉超前
				BigDecimal bSubQty = bPlanQty.subtract(bHistoryQty);
				mapOverProduceQty.put(mapKey, bSubQty);
			}else {
				//無PPC plan數量但有history,則需計算超前量 JoshLai@20190426+
				//4. has history , no ppc plan --> 超前
				mapOverProduceQty.put(mapKey, bHistoryQty.negate());
			}
		}
		
		//落後
		for(Map.Entry<String, Object> planEntry : mapSumPlanQty.entrySet()) {
			String mapKey = planEntry.getKey();
			BigDecimal bPlanQty = (BigDecimal) planEntry.getValue();
			
			if(mapSumHistoryQty.get(mapKey)!=null) {
				BigDecimal bHistoryQty = (BigDecimal) mapSumHistoryQty.get(mapKey);
				
				//若history數量小於ppc plan則相差數量為落後量 JoshLai@20190510+
				//8.前一天有落後，當天(plan start date)有ppc plan --> 當天ppc plan要加上落後量
				BigDecimal bSubQty = bHistoryQty.subtract(bPlanQty);
				mapUnderProduceQty.put(mapKey, bSubQty);
			}else {
				//無history數量但有PPC plan,則需計算落後量 JoshLai@20190510+
				//3. no history , has ppc plan --> 落後
				mapUnderProduceQty.put(mapKey, bPlanQty.negate());
			}
		}
		
		logger.info(mapOverProduceQty.size() + " mapOverProduceQty: " + mapOverProduceQty);
		logger.info(mapUnderProduceQty.size() + " mapUnderProduceQty: " + mapUnderProduceQty);
		
		//Step2-1 扣除超前量
		List<Plan> substarctProduceList = new ArrayList<>();
		for(Plan plan : rJobPlanList) {
			plan.setContinueMaxShiftDate(plan.getShiftDate());
			//已取得超前量,移除planStartDate-1的plan
			if(plan.getShiftDate().isEqual(planStartDate) || plan.getShiftDate().isAfter(planStartDate)) {
				Plan planClone = SerializationUtils.clone(plan);
				substarctProduceList.add(planClone);
			}
		}
		
		for(Map.Entry<String, Object> entry : mapOverProduceQty.entrySet()) {
			String[] keys = entry.getKey().split("##");
			String overProduceLinNo = keys[0];
			LocalDate overProduceShiftDate = LocalDate.of(Integer.parseInt(keys[1].substring(0, 4)),
												Integer.parseInt(keys[1].substring(4, 6)),
												Integer.parseInt(keys[1].substring(6, 8)));
			String overProducePartNo = keys[2];
			BigDecimal bOverProduceQty = (BigDecimal) entry.getValue();
			String overProduceGrade = keys[3];
			
			for(Iterator<Plan> iter = substarctProduceList.iterator(); iter.hasNext();) {
				Plan plan = iter.next();
				if(plan.getLine().equals(overProduceLinNo) 
						&& (plan.getShiftDate().isEqual(overProduceShiftDate) 
								|| plan.getShiftDate().isAfter(overProduceShiftDate))
						&& plan.getPartNo().equals(overProducePartNo)
						&& plan.getGrade().equals(overProduceGrade)) {
					bOverProduceQty = new BigDecimal(String.valueOf(entry.getValue()));
					BigDecimal bPlanQty = new BigDecimal(plan.getPlanQty());
					
					//有超前量的情況下才需扣除超前量
					if(bOverProduceQty.intValue() < 0) {
						BigDecimal bSubstractQty = bPlanQty.subtract(bOverProduceQty.abs());
						
						BigDecimal bSubQty = null;
						//足夠扣除的情況
						if(bSubstractQty.intValue() >= 0) {
							plan.setPlanQty(bSubstractQty.toPlainString());
							bSubQty = ((BigDecimal) entry.getValue()).abs().subtract(bOverProduceQty.abs());
						}else {
							plan.setPlanQty("0");
							bSubQty = ((BigDecimal) entry.getValue()).abs().subtract(bPlanQty);
						}
						entry.setValue(bSubQty);
					}
					//若扣掉超前量planQty為0,則不需加入排程 JoshLai@20190508+
					if(Integer.parseInt(plan.getPlanQty()) == 0) {
						iter.remove();
					}
				}
			}
		}
		logger.info(substarctProduceList.size() + " substarctProduceList-1: " + substarctProduceList);
		
		//Step2-2 加落後量
		for(Map.Entry<String, Object> entry : mapUnderProduceQty.entrySet()) {
			String[] keys = entry.getKey().split("##");
			String underProduceLinNo = keys[0];
			LocalDate underProduceShiftDate = LocalDate.of(Integer.parseInt(keys[1].substring(0, 4)),
												Integer.parseInt(keys[1].substring(4, 6)),
												Integer.parseInt(keys[1].substring(6, 8)));
			String underProducePartNo = keys[2];
			BigDecimal bUnderProduceQty = (BigDecimal) entry.getValue();
			String underProduceGrade = keys[3];
			
			if(bUnderProduceQty.intValue() < 0) {
				String area = null;
				boolean isExsistInPlan = false;
				for(Iterator<Plan> iter = substarctProduceList.iterator(); iter.hasNext();) {
					Plan plan = iter.next();
					area = plan.getArea();
					if(plan.getLine().equals(underProduceLinNo) 
							&& (plan.getShiftDate().isEqual(underProduceShiftDate)
									|| plan.getShiftDate().isAfter(underProduceShiftDate))
							&& plan.getPartNo().equals(underProducePartNo)
							&& plan.getGrade().equals(underProduceGrade)) {
						
						bUnderProduceQty = new BigDecimal(String.valueOf(entry.getValue()));
						BigDecimal bPlanQty = new BigDecimal(plan.getPlanQty());
						BigDecimal bAddQty = bPlanQty.add(bUnderProduceQty.abs());
						plan.setPlanQty(bAddQty.toPlainString());
						isExsistInPlan = true;
					}
				}
				//7.前一天有落後，當天(plan start date)沒有ppc plan --> 要新增一筆job , plan qty=落後量
				if(isExsistInPlan == false) {
					String previousMapKey = null;
					for(Object job : historicalList) {
						String historySite = null;
						String historyFab = null;
						String historyJobId = null;
						String historyModelNo = null;
						
						String historyLineNo = null;
						String historyPartNo = null;
						LocalDate historyShiftDate = null;
						String historyGrade = null;
						String historyJobType = null;
						if(job instanceof Adjustment) {
							historySite = ((Adjustment) job).getAdjustmentId().getSite();
							historyFab = ((Adjustment) job).getAdjustmentId().getFab();
							historyModelNo = ((Adjustment) job).getAdjustmentId().getModelNo();
							
							historyLineNo = ((Adjustment) job).getAdjustmentId().getLine();
							historyPartNo = ((Adjustment) job).getPartNo();
							historyShiftDate = ((Adjustment) job).getAdjustmentId().getShiftDate();
							historyGrade = ((Adjustment) job).getAdjustmentId().getGrade();
							historyJobType = ((Adjustment) job).getAdjustmentId().getJobType();
							
							String strShiftDate = historyShiftDate.format(formatterNoSlash);
							historyJobId = historyLineNo+"-"+strShiftDate+"-"+historyPartNo+"-"+historyGrade;
						}else if(job instanceof RJobDashboard) {
							historySite = ((RJobDashboard) job).getSite();
							historyFab = ((RJobDashboard) job).getFab();
							historyJobId = ((RJobDashboard) job).getJobId();
							historyModelNo = ((RJobDashboard) job).getModelNo();
							
							historyLineNo = ((RJobDashboard) job).getLine();
							historyShiftDate = ((RJobDashboard) job).getShiftDate();
							historyPartNo = ((RJobDashboard) job).getPartNo();
							historyGrade = ((RJobDashboard) job).getGrade();
							historyJobType = ((RJobDashboard) job).getJobType();
						}
						if(historyShiftDate.isEqual(planStartDateMinus1) && "PROD".equals(historyJobType)) {
							String strShiftDate = historyShiftDate.format(formatterNoSlash);
							String mapKey = historyLineNo+"##"+strShiftDate+"##"+historyPartNo+"##"+historyGrade;
							
							//由於history runSeq有多筆,相同的key落後量只要加一次就好
							if(mapKey.equals(entry.getKey()) && !mapKey.equals(previousMapKey)) {
								Plan newPlan = new Plan();
								newPlan.setSite(historySite);
								newPlan.setFab(historyFab);
								newPlan.setArea(area);
								newPlan.setJobId(historyJobId);
								newPlan.setModelNo(historyModelNo);
								newPlan.setPlanQty(bUnderProduceQty.abs().toPlainString());
								newPlan.setLine(historyLineNo);
								
								LocalDate addShiftDate = historyShiftDate.plusDays(1);//將新的plan的shiftDate設為planDate
								newPlan.setShiftDate(addShiftDate);
								newPlan.setPartNo(historyPartNo);
								newPlan.setGrade(historyGrade);
								newPlan.setJobType(historyJobType);
								newPlan.setContinueMaxShiftDate(addShiftDate);
								substarctProduceList.add(newPlan);
								logger.info("add newPlan: " + newPlan);
								
								previousMapKey = mapKey;
							}
						}
					}
				}
			}
		}
		substarctProduceList = innerJoinPlanCapa(substarctProduceList, cEqpCapaList);
		logger.info(substarctProduceList.size() + " substarctProduceList-2: " + substarctProduceList);
		
		//Step3 合併連續shiftDate
		if(substarctProduceList.size() > 0) {
			String previousLineNo = substarctProduceList.get(0).getLine();
			LocalDate previousShiftDate = substarctProduceList.get(0).getShiftDate();
			String previousPartNo = substarctProduceList.get(0).getPartNo();
			String previousGrade = substarctProduceList.get(0).getGrade();
			
			Plan newPlan = new Plan();
			newPlan = SerializationUtils.clone(substarctProduceList.get(0));
			planList.add(newPlan);
			
			int iCounter = 0;
			for(Plan plan : substarctProduceList) {
				String lineNo = plan.getLine();
				LocalDate shiftDate = plan.getShiftDate();
				BigDecimal planQty = new BigDecimal(plan.getPlanQty());
				String partNo = plan.getPartNo();
				String grade = plan.getGrade();
				long daysBetween = DAYS.between(previousShiftDate, shiftDate);
				
				if(previousLineNo.equals(lineNo) && iCounter>0
						&& daysBetween <= 1
						&& previousPartNo.equals(partNo) && previousGrade.equals(grade)) {
						Plan lastPlan = planList.get(planList.size()-1);
						BigDecimal lastPlanQty = new BigDecimal(lastPlan.getPlanQty());
						BigDecimal plusPlanQty = lastPlanQty.add(planQty);
						lastPlan.setLine(plan.getLine());
						lastPlan.setContinueMaxShiftDate(plan.getShiftDate());
						lastPlan.setPlanQty(plusPlanQty.toPlainString());
				}else if(iCounter>0)
					planList.add(plan);
				
				previousLineNo = lineNo;
				previousShiftDate = shiftDate;
				previousPartNo = partNo;
				previousGrade = grade;
				iCounter++;
			}
		}
		logger.info(planList.size() + " combine planList: " + planList);
		logger.info(rJobSpecialList.size() + " rJobSpecialList1: " +rJobSpecialList);
		//拷貝SepcialList
		List<Special> specialListCopy = new ArrayList<>();
		for(Special special : rJobSpecialList) {
			Special specialClone = SerializationUtils.clone(special);
			specialListCopy.add(specialClone);
		}
		
		//Step4 扣掉sepcial
		for(Plan plan : planList) {
			for(Special special : specialListCopy) {
				if(plan.getSite().equals(special.getSite())
						&& plan.getLine().equals(special.getLine())
						&& plan.getPartNo().equals(special.getPartNo())
						&& plan.getShiftDate().equals(special.getShiftDate())
						&& plan.getCellPartNo().equals(special.getCellPartNo())
						&& plan.getGrade().equals(special.getGrade())
						&& (plan.getShiftDate().equals(special.getShiftDate())
							|| plan.getShiftDate().isBefore(special.getShiftDate())
								&& (plan.getShiftDate().equals(special.getShiftDate())
								|| plan.getShiftDate().isAfter(special.getShiftDate())))) {
					BigDecimal bPlanQty = new BigDecimal(plan.getPlanQty());
					BigDecimal bSpecialQty = new BigDecimal(special.getPlanQty());
					BigDecimal bSubstarctQty = bPlanQty.subtract(bSpecialQty);
					plan.setPlanQty(bSubstarctQty.toPlainString());
					special.setPlanQty("0");
				}
			}
		}
		logger.info(planList.size() + " after substarct SPL planList: " + planList);
		return planList;
	}*/
	
	public static List<Plan> calcFullLoadRJobPlan(List<Plan> rJobPlanList, List<Special> rJobSpecialList, List<EqpCapa> cEqpCapaList, List<Object> historicalList, Map<String, Object> mapParam) throws Exception {
		logger.info(rJobPlanList.size() + " rJobPlanList-0: " + rJobPlanList);
		List<Plan> planList = new ArrayList<>();
		String nextFlag = "";
		LocalDate planStartDate = (LocalDate) ((Map) mapParam.get("plan_start_date")).get("in_value1");
		LocalDate planEndDate = (LocalDate) ((Map) mapParam.get("input_plan_end_date")).get("in_value1");
		
		List<Plan> reviseRJobPlanList = new ArrayList<>(rJobPlanList);
		boolean isAdjust = false;
		//S06考慮超前/落後量
		//S06前一日超前落後，不分等級，超前落後全部計入Z級 JoshLai@20190710+
		Map<String, Object> followPPCplan = (Map<String, Object>) mapParam.get("follow_ppc_plan");
		if(followPPCplan!=null && followPPCplan.get("in_value2")!=null && followPPCplan.get("in_value2").equals("adjust")) {
			isAdjust = true;
			reviseRJobPlanList = new ArrayList<>();
			//Step1 計算超前量/落後量
			//2. history > ppc plan --> 超前
			Map<String, Object> mapOverProduceQty = new HashMap<>();//超前量
			
			//1. history < ppc plan --> 落後
			Map<String, Object> mapUnderProduceQty = new HashMap<>();//落後量
			Map<String, Object> mapSumHistoryQty = new HashMap<>();
			Map<String, Object> mapSumPlanQty = new HashMap<>();
			LocalDate planStartDateMinus1 = planStartDate.minusDays(1);
			
			//加總歷史資料排程前一天的量
			sumHistoricalQtyLastDate(mapSumHistoryQty, historicalList, planStartDateMinus1);
			logger.info(mapSumHistoryQty.size() + " mapSumHistoryQty: " + mapSumHistoryQty);
			
			//加總PPC plan排程前一天的量
			sumRJobPlanListQtyLastDate(mapSumPlanQty, rJobPlanList, planStartDateMinus1);
			logger.info(mapSumPlanQty.size() + " mapSumPlanQty: " + mapSumPlanQty);
			
			//超前
			calcOverProduceQty(mapOverProduceQty, mapSumHistoryQty, mapSumPlanQty);
			//落後
			calcUnderProduceQty(mapUnderProduceQty, mapSumHistoryQty, mapSumPlanQty);
			
			logger.info(mapOverProduceQty.size() + " mapOverProduceQty: " + mapOverProduceQty);
			logger.info(mapUnderProduceQty.size() + " mapUnderProduceQty: " + mapUnderProduceQty);
			logger.info(rJobPlanList.size() + " rJobPlanList-1: " + rJobPlanList);
			
			//Step2-1 扣除超前量,從後面開始扣量
			substarctOverProduceQty(rJobPlanList, reviseRJobPlanList, mapOverProduceQty, planStartDate, planEndDate);
			logger.info(reviseRJobPlanList.size() + " reviseRJobPlanList-1: " + reviseRJobPlanList);
			
			//Step2-2 加落後量,從前面日期往前加,只補到滿載數量為只
			try {
				addUnderProduceQty(reviseRJobPlanList, mapUnderProduceQty, historicalList, cEqpCapaList, planEndDate, planStartDateMinus1);
			}catch(Exception e) {
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logger.error(errors.toString());
				e.printStackTrace();
			}
			
		}
		logger.info(reviseRJobPlanList.size() + " reviseRJobPlanList-2: " + reviseRJobPlanList);
		
		//不分等級加總planQty by line,partNo
		//FabPcCapa-PcCapa多出的量計入Z級 JoshLai@20190712+
		Map<String, Object> mapSumNoGradePlanQty = new TreeMap<>();
		for(Plan plan : reviseRJobPlanList) {
			String strShiftDate = plan.getShiftDate().format(formatterNoSlash);
			String mapKey = plan.getLine()+"##"+strShiftDate+"##"+plan.getPartNo();
			BigDecimal bPlanQty = new BigDecimal(plan.getPlanQty());
			if(mapSumNoGradePlanQty.get(mapKey) == null) {
				mapSumNoGradePlanQty.put(mapKey, bPlanQty);
			}else {
				BigDecimal bMapPlanQty = (BigDecimal) mapSumNoGradePlanQty.get(mapKey);
				bPlanQty = bMapPlanQty.add(bPlanQty);
				mapSumNoGradePlanQty.put(mapKey, bPlanQty);
			}
		}
		logger.info("mapSumNoGradePlanQty: " + mapSumNoGradePlanQty);
		
		for(Map.Entry<String, Object> entry : mapSumNoGradePlanQty.entrySet()) {
			String keys[] = entry.getKey().split("##");
			String entryLineNo = keys[0];
			String strShiftDate = keys[1];
			LocalDate entryShiftDate = LocalDate.parse(strShiftDate, formatterNoSlash);
			String entryPartNo = keys[2];
			int entryPlanQty = ((BigDecimal) entry.getValue()).intValue();
			int nextPlanQty = 0;
			
			//若后一天有量：
			//当天满载(plan qty >= ppc_capa)：看當天+後一天若>=Fab pc capa則排 Fab pc capa；若<=Fab pc capa則排當天+後一天即可；
			//当天不满载(plan qty < ppc_capa)：直接排plan量不加量
			//若后一天没有量： 直接排plan量（不论是否满载）
			LocalDate nextDate = entryShiftDate.plusDays(1);
			String strNextDate = nextDate.format(formatterNoSlash);
			nextFlag = entryLineNo+"##"+strNextDate+"##"+entryPartNo;
			
			//ppc plan排量:若后一天有量: (***若后一天 (沒有PPC Plan and  (PM or NON_SCHEDULE)) 則再往後一天***) JoshLai@20190718+
			BigDecimal bNextPlanQty = getNextShiftDatePlanQty(nextFlag, mapSumNoGradePlanQty, reviseRJobPlanList);
			if(bNextPlanQty!=null)
				nextPlanQty = bNextPlanQty.intValue();
			
			for(int i=0; i<reviseRJobPlanList.size(); i++) {
				Plan plan = reviseRJobPlanList.get(i);
				BigDecimal bPlanQty = new BigDecimal(plan.getPlanQty());
				
				if(nextPlanQty>0) {
					if(plan.getLine().equals(entryLineNo) && plan.getShiftDate().isEqual(entryShiftDate) 
							&& plan.getPartNo().equals(entryPartNo)) {
//						if(entryPlanQty >= plan.getCapa().getPpcCapa()) {
//							if ((entryPlanQty + nextPlanQty) >= plan.getCapa().getFabPcCapa()) {
						if(entryPlanQty >= plan.getCapa().getAdjustPpcCapa()) {
							if ((entryPlanQty + nextPlanQty) >= plan.getCapa().getAdjustFabPcCapa()) {
								if("Z".equals(plan.getGrade())) { //不分等級,多出來的量計入Z級
//									BigDecimal bFabPcCapa = new BigDecimal(plan.getCapa().getFabPcCapa());
//									BigDecimal bPcCapa = new BigDecimal(plan.getCapa().getPpcCapa());
									BigDecimal bFabPcCapa = new BigDecimal(plan.getCapa().getAdjustFabPcCapa());
									BigDecimal bPcCapa = new BigDecimal(plan.getCapa().getAdjustPpcCapa());
									BigDecimal subQty = bFabPcCapa.subtract(bPcCapa);
									bPlanQty = bPlanQty.add(subQty);
								}
							}else {
								bPlanQty = bPlanQty.add(new BigDecimal(nextPlanQty));
							}
						}
					}
				}
				
				if(isAdjust) {
					//補滿到FabPcCapa的部分從後面開始扣
					//只能從planDate區間扣 20190716+
					BigDecimal bSubQty = bPlanQty.subtract(new BigDecimal(plan.getPlanQty()));
					getLastSameJobPlanQty(reviseRJobPlanList, plan, bSubQty, planStartDate, planEndDate);
				}
				
				//扣除specail job qty
				BigDecimal bSplQty = new BigDecimal(getQtyFromSPLJob(plan, rJobSpecialList));
				bPlanQty = bPlanQty.subtract(bSplQty);
				plan.setPlanQty(bPlanQty.toPlainString());
				
				if(!planList.contains(plan))
					planList.add(plan);
			}
		}
		
		//過濾只要planDate in N day JoshLai@20190613+
		for(Iterator<Plan> iter = planList.iterator(); iter.hasNext();) {
			Plan plan = iter.next();
			plan.setContinueMaxShiftDate(plan.getShiftDate());
//			if(plan.getShiftDate().isBefore(planStartDate) || plan.getShiftDate().isAfter(planEndDate)) {
			//為了後處理能夠pull補滿D/D+N的數量,plan多取planEndDate+1
			if(plan.getShiftDate().isBefore(planStartDate) || plan.getShiftDate().isAfter(planEndDate.plusDays(1))) {
				iter.remove();
			}
		}
		return planList;
	}
	
	private static void sumHistoricalQtyLastDate(Map<String, Object> mapSumHistoryQty, List<Object> historicalList, LocalDate planStartDateMinus1) {
		//改先各別加總再計算落後量 JoshLai@20190430
		//加總歷史資料排程前一天的量
		for(Object job : historicalList) {
			String historyLineNo = null;
			String historyPartNo = null;
			LocalDate historyShiftDate = null;
			BigDecimal historyForecastQty = null;
//			String historyGrade = null;
			String historyJobType = null;
			if(job instanceof Adjustment) {
				historyLineNo = ((Adjustment) job).getAdjustmentId().getLine();
				historyPartNo = ((Adjustment) job).getPartNo();
				historyShiftDate = ((Adjustment) job).getAdjustmentId().getShiftDate();
				historyForecastQty = new BigDecimal(((Adjustment) job).getPlanQty());
//				historyGrade = ((Adjustment) job).getAdjustmentId().getGrade();
				historyJobType = ((Adjustment) job).getAdjustmentId().getJobType();
			}else if(job instanceof RJobDashboard) {
				historyLineNo = ((RJobDashboard) job).getLine();
				historyShiftDate = ((RJobDashboard) job).getShiftDate();
				historyForecastQty = new BigDecimal(((RJobDashboard) job).getForecastQty());
				historyPartNo = ((RJobDashboard) job).getPartNo();
//				historyGrade = ((RJobDashboard) job).getGrade();
				historyJobType = ((RJobDashboard) job).getJobType();
			}
			if(historyShiftDate.isEqual(planStartDateMinus1) && "PROD".equals(historyJobType)) {
				String strShiftDate = historyShiftDate.format(formatterNoSlash);
				String mapKey = historyLineNo+"##"+strShiftDate+"##"+historyPartNo;
				if(mapSumHistoryQty.get(mapKey) == null) {
					mapSumHistoryQty.put(mapKey, historyForecastQty);
				}else {
					//拿出數量加總後放回去
					BigDecimal bQty = (BigDecimal) mapSumHistoryQty.get(mapKey);
					BigDecimal bSumQty = bQty.add(historyForecastQty);
					mapSumHistoryQty.put(mapKey, bSumQty);
				}
			}
		}
	}
	
	private static void sumRJobPlanListQtyLastDate(Map<String, Object> mapSumPlanQty, List<Plan> rJobPlanList, LocalDate planStartDateMinus1) {
		for(Plan plan : rJobPlanList) {
			String planLineNo = plan.getLine();
			String planPartNo = plan.getPartNo();
			LocalDate planShiftDate = plan.getShiftDate();
			BigDecimal planQty = new BigDecimal(plan.getPlanQty());
			String planGrade = plan.getGrade();
			String planJobType = plan.getJobType();
			
			if(planShiftDate.isEqual(planStartDateMinus1) && "PROD".equals(planJobType)) {
				String strShiftDate = planShiftDate.format(formatterNoSlash);
				String mapKey = planLineNo+"##"+strShiftDate+"##"+planPartNo;
				if(mapSumPlanQty.get(mapKey) == null) {
					mapSumPlanQty.put(mapKey, planQty);
				}else {
					//拿出數量加總後放回去
					BigDecimal bQty = (BigDecimal) mapSumPlanQty.get(mapKey);
					BigDecimal bSumQty = bQty.add(planQty);
					mapSumPlanQty.put(mapKey, bSumQty);
				}
			}
		}
	}
	
	private static void calcOverProduceQty(Map<String, Object> mapOverProduceQty, Map<String, Object> mapSumHistoryQty, Map<String, Object> mapSumPlanQty) {
		for(Map.Entry<String, Object> historyEntry : mapSumHistoryQty.entrySet()) {
			String mapKey = historyEntry.getKey();
			BigDecimal bHistoryQty = (BigDecimal) historyEntry.getValue();
			
			if(mapSumPlanQty.get(mapKey)!=null) {
				BigDecimal bPlanQty = (BigDecimal) mapSumPlanQty.get(mapKey);
				
				//PPC plan減去history,若ppc plan數量小於history則相差數量為超前量
				//6.前一天有超前，當天(plan start date)有ppc plan --> 當天ppc plan要扣掉超前
				BigDecimal bSubQty = bPlanQty.subtract(bHistoryQty);
				mapOverProduceQty.put(mapKey, bSubQty);
			}else {
				//無PPC plan數量但有history,則需計算超前量 JoshLai@20190426+
				//4. has history , no ppc plan --> 超前
				mapOverProduceQty.put(mapKey, bHistoryQty.negate());
			}
		}
	}
	
	private static void calcUnderProduceQty(Map<String, Object> mapUnderProduceQty, Map<String, Object> mapSumHistoryQty, Map<String, Object> mapSumPlanQty) {
		for(Map.Entry<String, Object> planEntry : mapSumPlanQty.entrySet()) {
			String mapKey = planEntry.getKey();
			BigDecimal bPlanQty = (BigDecimal) planEntry.getValue();
			
			if(mapSumHistoryQty.get(mapKey)!=null) {
				BigDecimal bHistoryQty = (BigDecimal) mapSumHistoryQty.get(mapKey);
				
				//若history數量小於ppc plan則相差數量為落後量 JoshLai@20190510+
				//8.前一天有落後，當天(plan start date)有ppc plan --> 當天ppc plan要加上落後量
				BigDecimal bSubQty = bHistoryQty.subtract(bPlanQty);
				mapUnderProduceQty.put(mapKey, bSubQty);
			}else {
				//無history數量但有PPC plan,則需計算落後量 JoshLai@20190510+
				//3. no history , has ppc plan --> 落後
				mapUnderProduceQty.put(mapKey, bPlanQty.negate());
			}
		}
	}
	
	private static void substarctOverProduceQty(List<Plan> rJobPlanList, List<Plan> reviseRJobPlanList, Map<String, Object> mapOverProduceQty, LocalDate planStartDate, LocalDate planEndDate) {
		for(Plan plan : rJobPlanList) {
			plan.setContinueMaxShiftDate(plan.getShiftDate());
			//已取得超前量,移除planStartDate-1的plan
			if(plan.getShiftDate().isEqual(planStartDate) || plan.getShiftDate().isAfter(planStartDate)) {
				Plan planClone = SerializationUtils.clone(plan);
				reviseRJobPlanList.add(planClone);
			}
		}
		logger.info(reviseRJobPlanList.size() + " reviseRJobPlanList: " + reviseRJobPlanList);
		
		for(Map.Entry<String, Object> entry : mapOverProduceQty.entrySet()) {
			String[] keys = entry.getKey().split("##");
			String overProduceLinNo = keys[0];
			LocalDate overProduceShiftDate = LocalDate.of(Integer.parseInt(keys[1].substring(0, 4)),
												Integer.parseInt(keys[1].substring(4, 6)),
												Integer.parseInt(keys[1].substring(6, 8)));
			String overProducePartNo = keys[2];
			BigDecimal bOverProduceQty = (BigDecimal) entry.getValue();
			String overProduceGrade = null;
			if(keys.length > 3)
				overProduceGrade = keys[3];
			
			for(int i=reviseRJobPlanList.size()-1; i>=0; i--) {
				Plan plan = reviseRJobPlanList.get(i);
				if(plan.getLine().equals(overProduceLinNo)
						&& ((plan.getShiftDate().isEqual(overProduceShiftDate) 
								|| plan.getShiftDate().isAfter(overProduceShiftDate))
									&& (plan.getShiftDate().isEqual(planEndDate.plusDays(1))
											|| plan.getShiftDate().isBefore(planEndDate.plusDays(1))))
						&& plan.getPartNo().equals(overProducePartNo)
						/*&& plan.getGrade().equals(overProduceGrade)*/
						&& plan.getGrade().equals("Z")) {
					
					bOverProduceQty = new BigDecimal(String.valueOf(entry.getValue()));
					BigDecimal bPlanQty = new BigDecimal(plan.getPlanQty());
					
					//有超前量的情況下才需扣除超前量
					if(bOverProduceQty.intValue() < 0) {
						BigDecimal bSubstractQty = bPlanQty.subtract(bOverProduceQty.abs());
						
						BigDecimal bSubQty = null;
						//足夠扣除的情況
						if(bSubstractQty.intValue() >= 0) {
							plan.setPlanQty(bSubstractQty.toPlainString());
							bSubQty = ((BigDecimal) entry.getValue()).abs().subtract(bOverProduceQty.abs());
						}else {
							plan.setPlanQty("0");
							bSubQty = ((BigDecimal) entry.getValue()).abs().subtract(bPlanQty);
							
						}
						entry.setValue(bSubQty);
					}
					//若扣掉超前量planQty為0,則不需加入排程 JoshLai@20190508+
					if(Integer.parseInt(plan.getPlanQty()) == 0) {
						reviseRJobPlanList.remove(i);
					}
				}
			}
		}
	}
	
	private static void addUnderProduceQty(List<Plan> reviseRJobPlanList, Map<String, Object> mapUnderProduceQty, List<Object> historicalList, List<EqpCapa> cEqpCapaList, LocalDate planEndDate, LocalDate planStartDateMinus1) throws Exception {
		if(reviseRJobPlanList.size() > 0) {
			for(Map.Entry<String, Object> entry : mapUnderProduceQty.entrySet()) {
				String[] keys = entry.getKey().split("##");
				String underProduceLinNo = keys[0];
				LocalDate underProduceShiftDate = LocalDate.of(Integer.parseInt(keys[1].substring(0, 4)),
													Integer.parseInt(keys[1].substring(4, 6)),
													Integer.parseInt(keys[1].substring(6, 8)));
				String underProducePartNo = keys[2];
				BigDecimal bUnderProduceQty = (BigDecimal) entry.getValue();
				String underProduceGrade = null;
				if(keys.length > 3)
					underProduceGrade = keys[3];
				
				if(bUnderProduceQty.intValue() < 0) {
					String area = null;
					boolean isExsistInPlan = false;
					BigDecimal remainQty = new BigDecimal(0);
					Plan previousPlan = reviseRJobPlanList.iterator().next();
					String previousLine = reviseRJobPlanList.iterator().next().getLine();
					for(int i=0; i<reviseRJobPlanList.size(); i++) {
						Plan plan = reviseRJobPlanList.get(i);
						area = plan.getArea();
						if(plan.getLine().equals(underProduceLinNo) 
								&& (plan.getShiftDate().isEqual(underProduceShiftDate)
										|| plan.getShiftDate().isAfter(underProduceShiftDate)
										&& (plan.getShiftDate().isEqual(planEndDate.plusDays(1))
												|| plan.getShiftDate().isBefore(planEndDate.plusDays(1))))
								&& plan.getPartNo().equals(underProducePartNo)
								/*&& plan.getGrade().equals(underProduceGrade)*/
								&& plan.getGrade().equals("Z") //只補Z 20190716+
								/*&& plan.getCapa().getPpcCapa() > Integer.parseInt(plan.getPlanQty())*/  //未滿載才需加落後量 20190617+
								//若ppc plan是否=ppc capa/2 則為日班滿載，則需加量到fab pc capa/2 JoshLai@20190801+
								//若ppc plan是否>=ppc capa  則為整天滿載，則需加量到fab pc capa
								&& plan.getCapa().getAdjustPpcCapa() > Integer.parseInt(plan.getPlanQty())) { 
							
							bUnderProduceQty = new BigDecimal(String.valueOf(entry.getValue()));
							BigDecimal bPlanQty = new BigDecimal(plan.getPlanQty());
							//BigDecimal bPpcCapa = new BigDecimal(plan.getCapa().getPpcCapa());
							BigDecimal bPpcCapa = new BigDecimal(plan.getCapa().getAdjustPpcCapa());
							
							BigDecimal bAddPlanQty = new BigDecimal("0");
							BigDecimal bDiffQty = bPpcCapa.subtract(bPlanQty);
							if(bDiffQty.compareTo(bUnderProduceQty.abs())>=0) { //若落後量足夠補足到ppc_capa,則計入後歸零 JoshLai@20190717+
								bAddPlanQty = bPlanQty.add(bUnderProduceQty.abs());
								bUnderProduceQty = new BigDecimal("0");
							}else { //若落後量超出ppc_capa,則扣除需補足的落後量後,繼續下一輪 JoshLai@20190717+
								bAddPlanQty = bPlanQty.add(bDiffQty);
								bUnderProduceQty = bUnderProduceQty.add(bDiffQty);
							}
							
							logger.info("adjustCapa: " + bPpcCapa + " | " + plan.getCapa().getAdjustPpcCapa());
							
							plan.setPlanQty(bAddPlanQty.toPlainString());
							entry.setValue(bUnderProduceQty);
							
							//若加上落後量<=ppc capa
							if(bAddPlanQty.compareTo(bPpcCapa)<=0) {
								//若已加上落後量且沒超出PPC_Capa,則繼續下一筆落後 JoshLai@20190711+
								break;
							}
							//若加上落後量>ppc capa,則往後遞補
							else if(bAddPlanQty.compareTo(bPpcCapa)>0){
								if(bPpcCapa.compareTo(bAddPlanQty)>=0) {
									remainQty = bPpcCapa.subtract(bAddPlanQty);
									BigDecimal bExtraQty = bAddPlanQty.subtract(remainQty);
									plan.setPlanQty(bExtraQty.toPlainString());
									remainQty = remainQty.subtract(bExtraQty);
								}else {
									remainQty = bUnderProduceQty.abs();
								}
							}
							
							
//							if(!previousLine.equals(plan.getLine()) && previousLine.compareTo(plan.getLine())>=0){
							if(!previousLine.equals(plan.getLine())){ //JoshLai@20190711+
								if(remainQty.compareTo(BigDecimal.ZERO)>0) {
									//若還有剩餘落後量未補上且已不同條線,則新增Plan補上
//										Plan newPlan = SerializationUtils.clone(previousPlan);
									Plan newPlan = SerializationUtils.clone(plan);
									newPlan.setShiftDate(newPlan.getShiftDate().plusDays(1));
									newPlan.setPlanQty(remainQty.toPlainString());
									reviseRJobPlanList.add(newPlan);

									logger.info("addUnder-3 line: " + newPlan.getLine()
									+ " shiftDate: " + newPlan.getShiftDate()
									+ " partNo: " + newPlan.getPartNo()
									+ " bPlanQty: " + newPlan.getPlanQty()
									+ " newPlan: " + newPlan);
								}
								
								remainQty = new BigDecimal(0);
							}
							isExsistInPlan = true;
						}
						
						previousLine = plan.getLine();
						previousPlan = plan;
					}
					//7.前一天有落後，當天(plan start date)沒有ppc plan --> 要新增一筆job , plan qty=落後量
					if(isExsistInPlan == false) {
						String previousMapKey = null;
						String previousUnderProduceJobId = null;
						for(Object job : historicalList) {
							String historySite = null;
							String historyFab = null;
							String historyModelNo = null;
							
							String historyLineNo = null;
							String historyPartNo = null;
							LocalDate historyShiftDate = null;
							String historyGrade = null;
							String historyJobType = null;
							if(job instanceof Adjustment) {
								historySite = ((Adjustment) job).getAdjustmentId().getSite();
								historyFab = ((Adjustment) job).getAdjustmentId().getFab();
								historyModelNo = ((Adjustment) job).getAdjustmentId().getModelNo();
								historyLineNo = ((Adjustment) job).getAdjustmentId().getLine();
								historyPartNo = ((Adjustment) job).getPartNo();
								historyShiftDate = ((Adjustment) job).getAdjustmentId().getShiftDate();
								historyGrade = ((Adjustment) job).getAdjustmentId().getGrade();
								historyJobType = ((Adjustment) job).getAdjustmentId().getJobType();
							}else if(job instanceof RJobDashboard) {
								historySite = ((RJobDashboard) job).getSite();
								historyFab = ((RJobDashboard) job).getFab();
								historyModelNo = ((RJobDashboard) job).getModelNo();
								historyLineNo = ((RJobDashboard) job).getLine();
								historyShiftDate = ((RJobDashboard) job).getShiftDate();
								historyPartNo = ((RJobDashboard) job).getPartNo();
								historyGrade = ((RJobDashboard) job).getGrade();
								historyJobType = ((RJobDashboard) job).getJobType();
							}
							if(historyShiftDate.isEqual(planStartDateMinus1) && "PROD".equals(historyJobType)) {
								String strShiftDate = historyShiftDate.format(formatterNoSlash);
								String historyKey = historyLineNo+"##"+strShiftDate+"##"+historyPartNo+"##"+historyGrade;
								
								//由於history runSeq有多筆,相同的key落後量只要加一次就好
								String underProduceJobId = entry.getKey().replaceAll("##", "-")+"-Z";
								if(historyLineNo.equals(underProduceLinNo) && !historyKey.equals(previousMapKey)
										&& !underProduceJobId.equals(previousUnderProduceJobId)) { //同一個落後量只要加一次 JoshLai@20190701+
									Plan newPlan = new Plan();
									newPlan.setSite(historySite);
									newPlan.setFab(historyFab);
									newPlan.setArea(area);
									newPlan.setJobId(underProduceJobId);
									newPlan.setModelNo(historyModelNo);
									newPlan.setPlanQty(bUnderProduceQty.abs().toPlainString());
									newPlan.setLine(historyLineNo);
									
									LocalDate addShiftDate = historyShiftDate.plusDays(1);//將新的plan的shiftDate設為planDate
									newPlan.setShiftDate(addShiftDate);
									newPlan.setPartNo(underProducePartNo);
									newPlan.setGrade("Z");
									newPlan.setJobType(historyJobType);
									newPlan.setContinueMaxShiftDate(addShiftDate);
									innerJoinPlanCapa(newPlan, cEqpCapaList); //新增Job需給定Capa JoshLai@20190614+
									if(newPlan.getCapa()!=null)
										reviseRJobPlanList.add(newPlan);
									logger.info("add newPlan: " + newPlan);
									previousMapKey = historyKey;
									previousUnderProduceJobId = underProduceJobId;
								}
							}
						}
					}
				}
			}
		}else {
			throw new Exception("[WARNING] No PPC Plans, reviseRJobPlanList.size() is: " + reviseRJobPlanList.size());
		}
	}
	
	private static BigDecimal getNextShiftDatePlanQty(String nextFlag, Map<String, Object> mapSumNoGradePlanQty, List<Plan> reviseRJobPlanList) {
		BigDecimal bNoGradePlanQty = (BigDecimal) mapSumNoGradePlanQty.get(nextFlag);
		if(bNoGradePlanQty!=null) //找到隔一天planQty
			return bNoGradePlanQty;
		else { //沒找到隔一天planQty,查看是否PM,若為PM則繼續找下一天
			String nextFlags[] = nextFlag.split("##");
			String nextLineNo = nextFlags[0];
			String strNextShiftDate = nextFlags[1];
			LocalDate nextShiftDate = LocalDate.parse(strNextShiftDate, formatterNoSlash);
			String nextPartNo = nextFlags[2];
			
			for(Plan plan : reviseRJobPlanList) {
				if(nextLineNo.equals(plan.getLine()) && nextShiftDate.isEqual(plan.getShiftDate())
						&& SPECIFIC_JOB_TYPE.contains(plan.getJobType())) {
					nextShiftDate = nextShiftDate.plusDays(1); //若找到PM就加一天
					continue;
				}
				
				//找到PM後一天相同line,partNo的planQty
				if(nextLineNo.equals(plan.getLine()) && nextShiftDate.isEqual(plan.getShiftDate())
						&& nextPartNo.equals(plan.getPartNo())) {
					return new BigDecimal(plan.getPlanQty());
				}
			}
		}
		return null;
	}
	
	//從後面開始找相同LineNo, partNo, grade的PlanQty
	private static void getLastSameJobPlanQty(List<Plan> reviseRJobPlanList, Plan inputPlan, BigDecimal bExtraQty, LocalDate planStartDate, LocalDate planEndDate) {
		LocalDate planEndDatePlus = planEndDate.plusDays(1); 
		BigDecimal bRemainingQty = new BigDecimal(0);
		String previousLine = reviseRJobPlanList.get(0).getLine();
		for(int i=reviseRJobPlanList.size()-1; i>=0; i--) {
			Plan plan = reviseRJobPlanList.get(i);
			if(inputPlan.getLine().equals(plan.getLine()) && inputPlan.getPartNo().equals(plan.getPartNo())
					/*&& inputPlan.getGrade().equals(plan.getGrade())*/
					&& "Z".equals(plan.getGrade()) //先只扣Z 20190715+
					&& plan.getShiftDate().isAfter(inputPlan.getShiftDate())
					&& (plan.getShiftDate().isEqual(planStartDate) || plan.getShiftDate().isAfter(planStartDate)
					&& plan.getShiftDate().isEqual(planEndDatePlus) || plan.getShiftDate().isBefore(planEndDatePlus))) {
				BigDecimal planQty = new BigDecimal(plan.getPlanQty());
				
				if(bRemainingQty.compareTo(BigDecimal.ZERO)>0) {
					bExtraQty = bRemainingQty;
				}
				//若planQty足夠扣除
				if(planQty.subtract(bExtraQty).compareTo(BigDecimal.ZERO)>=0) {
					planQty = planQty.subtract(bExtraQty);
					plan.setPlanQty(planQty.toPlainString());
					break;
				}
				//不足扣除則繼續往前扣
				else {
					bRemainingQty = bExtraQty.subtract(planQty);
					plan.setPlanQty("0");
				}
			}
			
			//不同線別歸零
			if(previousLine!=null && plan.getLine().compareTo(previousLine)>=1) {
				bRemainingQty = new BigDecimal(0);
			}
			
			previousLine = plan.getLine();
		}
	}
	
	/* 判斷換線時間是否重疊 */
	public static void calcOverlapping(Map<String, Object> mapParam, List<TimeWindowedJob> jobsInLine) {
		int shiftDstart = Integer.parseInt(String.valueOf(((Map)mapParam.get("shift_d_start")).get("in_value1")));
		int shiftNstart = Integer.parseInt(String.valueOf(((Map)mapParam.get("shift_e_start")).get("in_value1")));
		LocalDate planStart = (LocalDate) ((Map)mapParam.get("plan_start_date")).get("in_value1");
		LocalDateTime planStartTime = planStart.atTime(shiftDstart, 0, 0);
		LocalDate planEnd = (LocalDate) ((Map)mapParam.get("plan_end_date")).get("in_value1");
		LocalDateTime planEndTime = planEnd.atTime(shiftNstart, 0, 0);
		
		List<TimeWindowedJob> movedJobList = doSolveOverlap(planStartTime, planEndTime, jobsInLine, mapParam, null, -1);
//		logger.debug(movedJobList.size() + " movedJobList: " + movedJobList);
	}
	
	private static List<TimeWindowedJob> doSolveOverlap(LocalDateTime planStart, LocalDateTime planEnd, List<TimeWindowedJob> jobList, Map<String, Object> mapParam, List<TimeWindowedJob> inputCandidateList, int earlyIndex) {
		//無設定參數時,不跑重疊換線 JoshLai@20190507+
		if(mapParam.get("change_interlock") == null || mapParam.get("change_interlock_prodtype_priority") == null) {
			return jobList;
		}
		String paramChangeLevel = (String) ((Map) mapParam.get("change_interlock")).get("in_value1");
		int paramLimitNum = Integer.parseInt(String.valueOf(((Map) mapParam.get("change_interlock")).get("in_value2")));
		
		LocalDateTime timer = planStart;
		List<TimeWindowedJob> candidateList = new ArrayList<>();
		boolean isRun = true;
		boolean isReturn = false;
		boolean isEnter = false;
		int getIndex = paramLimitNum;
		while(timer.isBefore(planEnd) || timer.isEqual(planEnd)) {
			isEnter = true;
			if(inputCandidateList != null && inputCandidateList.size() > 0) {
				candidateList = new ArrayList<>(inputCandidateList);
				getIndex = earlyIndex;
			}else {
				for(Iterator<TimeWindowedJob> iter = jobList.iterator(); iter.hasNext();) {
					TimeWindowedJob job = iter.next();
					String changeKey = (String) job.getChangeKey();
					BigDecimal bChangeDuration = new BigDecimal(String.valueOf(job.getChangeDuration()));
					LocalDateTime changeStartTime = calcChangeStartTimeByTimeWindowedJob(mapParam, job);
					String level = String.valueOf(job.getChangeLevel());
					
					if(timer.isEqual(changeStartTime) && changeKey!=null && bChangeDuration.compareTo(BigDecimal.ZERO)>0
							&& ("%".equals(paramChangeLevel) || paramChangeLevel.equals(level))) {
//						logger.debug("timer: " + timer + " changeStartTime: " + changeStartTime
//									+ " changeKey: " + changeKey + " bChangeDuration: " + bChangeDuration
//									+ " => " + (bChangeDuration.compareTo(BigDecimal.ZERO)>0));
						candidateList.add(job);
						getIndex = paramLimitNum;
					}
					
				}
				//當換線結束時沒有碰到其他重疊JOB,則自口袋中移除
				//logger.info("candidateList===> " + candidateList.size() + " | " + candidateList);
				for(Iterator<TimeWindowedJob> iter = candidateList.iterator(); iter.hasNext();) {
					TimeWindowedJob job = iter.next();
					LocalDateTime changeStartTime = calcChangeStartTimeByTimeWindowedJob(mapParam, job);
					double dChangeEndTime = job.getStartTime() == null ? 0 : job.getStartTime();
					LocalDateTime changeEndTime = calcDateTimeByNum(mapParam, dChangeEndTime);
					
//					logger.debug("timer==> " + timer + " start: " + changeStartTime + " endTime: " + changeEndTime);
					
					if(timer.isEqual(changeEndTime) || timer.isAfter(changeEndTime)) {
						iter.remove();
//						logger.debug("remove()... ");
					}
				}
			}
			
			//超過限制數量時
			while(candidateList.size() > paramLimitNum) {
				List<TimeWindowedJob> candidateSortList = calcMoveByPriority(mapParam, candidateList);
//				logger.debug(candidateSortList.size() + " candidateSortList: " + candidateSortList);
				
				//最先要換的Job
				TimeWindowedJob firstPriority = candidateSortList.get(0);
//				logger.debug("firstPriority: " + firstPriority);
				
				//最早換線結束時間, 換線結束時間為process start time
				double dEarlyEndTime = 0;
				Double dProcStartTime =  candidateSortList.get(candidateSortList.size()-getIndex).getStartTime();
				if(dProcStartTime != null)
					dEarlyEndTime = dProcStartTime;
				
				//需等到指定換線時間才能做 JoshLai@20190523+
				dEarlyEndTime = calcWaitForNextChangeTime(firstPriority, dEarlyEndTime);
				
				LocalDateTime earlyEndTime = calcDateTimeByNum(mapParam, dEarlyEndTime);
				LocalDateTime firstPriorityJobChangeStartTime = calcChangeStartTimeByTimeWindowedJob(mapParam, firstPriority);
				
//				logger.debug("earlyEndTime: " + earlyEndTime);
				doMoveJobForce(firstPriority, jobList, earlyEndTime, firstPriorityJobChangeStartTime, mapParam);
//				logger.debug("after move: ");
//				for(TimeWindowedJob tjob : jobList) {
//					if(tjob.getChangeDuration()>0)
//						logger.debug(""+tjob);
//				}
				
				//將搬移完成的job自候選列拿掉
				if(candidateList.indexOf(firstPriority) != -1) {
					candidateList.remove(firstPriority);
//					logger.debug("remove... ");
					if(getIndex-1 <= candidateSortList.size() && getIndex-1 != 0)
						getIndex-=1;//最早結束時間的index每次往後遞減1
				}
				/*if(candidateList.size() == paramLimitNum) {
					logger.debug("======= 1 ========");
					logger.debug("candidateList.size(): " +candidateList.size()
								+ " paramLimitNum: " + paramLimitNum
								+ " timer: " + timer
								+ " earlyEndTime: " + earlyEndTime);
					timer = earlyEndTime;
					//當口袋還有候選換線JOB時，跳到下一個時間點,並且清空口袋
					return doSolveOverlap(timer, planEnd, jobList, mapParam, null, -1);
				}else */if(candidateList.size() < paramLimitNum) {
//					logger.debug("======= 2 ========");
//					logger.debug("candidateList.size(): " +candidateList.size()
//								+ " paramLimitNum: " + paramLimitNum
//								+ " timer: " + timer
//								+ " earlyEndTime: " + earlyEndTime);
					timer = earlyEndTime;
					return doSolveOverlap(timer, planEnd, jobList, mapParam, candidateList, getIndex);
				}
			}
			
			
			//取得所有換線JOB的起始與結束時間,使timer直接跳到那個時間點,而不是每次累加一秒鐘去掃描,加速迴圈
			List<LocalDateTime> changeTimeList = calcOverlapTimerList(jobList, mapParam);
//			logger.debug(changeTimeList.size() + " changeTimeList: " + changeTimeList);
			int indexOfTimer = -1;
			for(int i=0; i<changeTimeList.size(); i++) {
				if(changeTimeList.get(i).isAfter(timer)){
					indexOfTimer = i;
					break;
				}
				if(i==changeTimeList.size()-1) {
					indexOfTimer = i;
					break;
				}
			}
			//int indexOfTimer = changeTimeList.indexOf(timer);
			//口袋候選JOB未超過限制數量，跳至下一個時間點(changeStarTime or changeEndTime)
			if(indexOfTimer!=-1 && indexOfTimer < changeTimeList.size()) {
				//logger.info("changeTimeList("+changeTimeList.size()+"): " + changeTimeList
				//			+ " indexOfTimer: " + indexOfTimer);
				timer = changeTimeList.get(indexOfTimer);
			}
			//時間來到最後一個changeEndTime結束迴圈
			if(indexOfTimer == (changeTimeList.size()-1)) {
				isReturn = true;
				break;
			}
		}
		
		
		if(isReturn)
			return jobList;
		
		if(!isEnter)
			return jobList;
		
//		logger.debug("return...");
		return doSolveOverlap(timer, planEnd, jobList, mapParam, candidateList, -1);
	}
	
	//找出首先要延後的job
	private static List<TimeWindowedJob> calcMoveByPriority(Map<String, Object> mapParam, List<TimeWindowedJob> candidateList) {
		String sProdTypePriority = (String) ((Map) mapParam.get("change_interlock_prodtype_priority")).get("in_value1");
		String[] aryProdTypePriority = sProdTypePriority.split(",");
		
		Collections.sort(candidateList, new Comparator<TimeWindowedJob>() {
			@Override
			public int compare(TimeWindowedJob o1, TimeWindowedJob o2) {
				float duration1 = (float) o1.getSetupDurationFromPreviousStandstill().get("CHANGE_DURATION");
				float duration2 = (float) o2.getSetupDurationFromPreviousStandstill().get("CHANGE_DURATION");
				double o1ProcStartTime = o1.getStartTime() == null ? 0 : o1.getStartTime();
				double o2ProcStartTime = o2.getStartTime() == null ? 0 : o2.getStartTime();
				LocalDateTime changeStartTime1 = calcChangeStartTimeByTimeWindowedJob(mapParam, o1);
				LocalDateTime changeStartTime2 = calcChangeStartTimeByTimeWindowedJob(mapParam, o2);
				LocalDateTime changeEndTime1 = calcDateTimeByNum(mapParam, o1ProcStartTime);
				LocalDateTime changeEndTime2 = calcDateTimeByNum(mapParam, o2ProcStartTime);
				
				String prodType1 = (String) o1.getJobType();
				String prodType2 = (String) o2.getJobType();
				int prodTypePriority1 = calcPriority(aryProdTypePriority, prodType1, o1.getPartNo());
				int prodTypePriority2 = calcPriority(aryProdTypePriority, prodType2, o2.getPartNo());
				
				if(duration1 > duration2)
					return 1;
				else if(duration1 < duration2)
					return -1;
				else {
					if(changeStartTime1.isAfter(changeStartTime2))
						return -1;
					else if(changeStartTime1.isBefore(changeStartTime2))
						return 1;
					else {
						if(changeEndTime1.isAfter(changeEndTime2))
							return -1;
						else if(changeEndTime1.isBefore(changeEndTime2))
							return 1;
						else {
							if(prodTypePriority1 > prodTypePriority2)
								return 1;
							else if(prodTypePriority1 < prodTypePriority2)
								return -1;
							else {
								return 0;
							}
						}
					}
				}
			}
		});
		return candidateList;
	}
	
	private static LocalDateTime calcChangeStartTimeByTimeWindowedJob(Map<String, Object> mapParam, TimeWindowedJob job) {
		double dProcStartTime = job.getStartTime() == null ? 0 : job.getStartTime();
		BigDecimal bChangeDuration = new BigDecimal(String.valueOf(job.getChangeDuration()));
		BigDecimal bSub = bChangeDuration.negate();
		LocalDateTime procStartTime = calcDateTimeByNum(mapParam, dProcStartTime);
		LocalDateTime changeStartTime = addHourReturnDate(procStartTime, bSub.doubleValue());
		return changeStartTime;
	}
	
	//移動線上的job往後
	private static void doMoveJobForce(TimeWindowedJob firstPriorityJob, List<TimeWindowedJob> jobList, LocalDateTime earlyEndTime, LocalDateTime moveFromTime, Map<String, Object> mapParam) {
		doMoveJob(firstPriorityJob, jobList, earlyEndTime, moveFromTime, mapParam, true);
	}
	
	private static void doMoveJob(TimeWindowedJob firstPriorityJob, List<TimeWindowedJob> jobList, LocalDateTime earlyEndTime, LocalDateTime moveFromTime, Map<String, Object> mapParam, boolean isForceMove) {
		//long hoursDiff = ChronoUnit.HOURS.between(moveFromTime, earlyEndTime);//這個方法換算的時間不夠精確 JoshLai@20190517+
		long secondsBetween = ChronoUnit.SECONDS.between(moveFromTime, earlyEndTime);//改用秒數在換乘小時
		BigDecimal bSecondsDiff = new BigDecimal(secondsBetween);
		BigDecimal b60 = new BigDecimal(60);
		BigDecimal bMinDiff = bSecondsDiff.divide(b60, 10, RoundingMode.HALF_UP);
		BigDecimal bHoursDiff = bMinDiff.divide(b60, 10, RoundingMode.HALF_UP);
		
		Double previousEndTime = null;
		String previousLineNo = null;
		for(Iterator<TimeWindowedJob> iter = jobList.iterator(); iter.hasNext();) {
			TimeWindowedJob job = iter.next();
			String jobLineNo = (String) job.getLineNo();
			
			//不同條線時重置previousEndTime JoshLai@20190620+
			if(!jobLineNo.equals(previousLineNo))
				previousEndTime = null;
			double dJobStartTime = job.getStartTime() == null ? 0 : job.getStartTime();
			BigDecimal bJobStartTime = new BigDecimal(dJobStartTime);
			BigDecimal bJobEndTime = new BigDecimal(job.getEndTime()); 
//			BigDecimal bJobEndTime = new BigDecimal(job.endTime); //由於Adjustment轉TimeWindowed有指定結束時間,因此直接取endTime JoshLai@20190726+
			LocalDateTime jobChangeStartTime = calcChangeStartTimeByTimeWindowedJob(mapParam, job);
					
			//此線上moveFromTime(包含)以後的連續job
			boolean isRun = jobLineNo.equals(firstPriorityJob.getLineNo()) && (jobChangeStartTime.isEqual(moveFromTime)
					|| jobChangeStartTime.isAfter(moveFromTime));
			
			//若在同一線上且排在moveFromTime後面,但是時間沒有接續ex:該Job站在指定時間上(08:00)才run,則不更動時間 JoshLai@20190610+
			if(isRun && previousEndTime!=null && isForceMove==false) {
				BigDecimal bJobStartTimeWithoutChange = new BigDecimal(job.getStartTime());
				BigDecimal bChangeDuration = new BigDecimal(job.getChangeDuration());
				bJobStartTimeWithoutChange = bJobStartTimeWithoutChange.subtract(bChangeDuration);
				if(bJobStartTimeWithoutChange.compareTo(new BigDecimal(previousEndTime)) != 0) {
					isRun = false;
				}
			}
			
			previousEndTime = job.getEndTime();
//			previousEndTime = job.endTime;
			if(isRun) {
				BigDecimal bAddStartTime = bJobStartTime.add(bHoursDiff);
				job.setStartTime(bAddStartTime.doubleValue());
				BigDecimal bAddEndTime = bJobEndTime.add(bHoursDiff);
				job.setEndTime(bAddEndTime.doubleValue());
			}
			previousLineNo = jobLineNo;
		}
	}
	
	private static void doMoveJob(SJobDashboard targetSJob, List<SJobDashboard> jobList, LocalDateTime earlyEndTime, LocalDateTime moveFromTime, Map<String, Object> mapParam, boolean isDebug) {
		if(moveFromTime!=null && earlyEndTime!=null) {
			long secondsDiff = ChronoUnit.SECONDS.between(moveFromTime, earlyEndTime);
			
			LocalDateTime previousEndTime = null;
			String previousLineNo = null;
			int i=0;
			for(Iterator<SJobDashboard> iter = jobList.iterator(); iter.hasNext();i++) {
				SJobDashboard sJob = iter.next();
				String jobLineNo = (String) sJob.getLine();
				if(!jobLineNo.equals(previousLineNo))
					previousEndTime = null;
				LocalDateTime jobChangeStartTime = sJob.getChangeStartTime()==null ? sJob.getProcessStartTime(): sJob.getChangeStartTime();
				LocalDateTime jobChangeEndTime = sJob.getChangeEndTime()==null ? sJob.getProcessEndTime() : sJob.getChangeEndTime();
				LocalDateTime jobProcStartTime = sJob.getProcessStartTime();
				LocalDateTime jobProcEndTime = sJob.getProcessEndTime();
				
				boolean isSetAffectCapaQty = sJob.getAffectCapaQty()!=null && sJob.getAffectCapaQty().intValue()>0;
				
				//此線上moveFromTime(包含)以後的連續job
				boolean isRun = jobLineNo.equals(targetSJob.getLine()) && ((jobChangeStartTime.isEqual(moveFromTime)
						|| jobChangeStartTime.isAfter(moveFromTime)) && jobProcStartTime!=null && jobProcEndTime!=null);
				
				//若在同一線上且排在moveFromTime後面,但是時間沒有接續ex:該Job站在指定時間上(08:00)才run,則不更動時間 JoshLai@20190606+
				if(isRun && previousEndTime!=null) {
					BigDecimal bSecondsDiff = new BigDecimal(ChronoUnit.SECONDS.between(jobChangeStartTime, previousEndTime));
					if(bSecondsDiff.abs().longValue() != Math.abs(secondsDiff) && bSecondsDiff.compareTo(BigDecimal.ZERO)!=0) {
						isRun = false;
					}
				}
				
				previousEndTime = sJob.getProcessEndTime();
				if(isRun) {
					jobProcStartTime = jobProcStartTime.plusSeconds(secondsDiff);
					sJob.setProcessStartTime(jobProcStartTime);
					
					jobProcEndTime = jobProcEndTime.plusSeconds(secondsDiff);
					if(!isSetAffectCapaQty) //若有設定affectCapaQty則該procEndTime不更動,維持原本班別結束時間 JoshLai@20190809+
						sJob.setProcessEndTime(jobProcEndTime);
					
					if(sJob.getChangeStartTime()!=null) {
						jobChangeStartTime = jobChangeStartTime.plusSeconds(secondsDiff);
						sJob.setChangeStartTime(jobChangeStartTime);
					}else {
						sJob.setChangeStartTime(null);
					}
					
					if(sJob.getChangeEndTime()!=null) {
						jobChangeEndTime = jobChangeEndTime.plusSeconds(secondsDiff);
						sJob.setChangeEndTime(jobChangeEndTime);
					}else {
						sJob.setChangeEndTime(null);
					}
				}
				previousLineNo = jobLineNo;
			}
		}
	}
	
	private static List<LocalDateTime> calcOverlapTimerList(List<TimeWindowedJob> jobList, Map<String, Object> mapParam) {
		List<LocalDateTime> changeTimeList = new ArrayList<>();
		for(Iterator<TimeWindowedJob> iter = jobList.iterator(); iter.hasNext();) {
			TimeWindowedJob job = iter.next();
			BigDecimal bChangeDuration = new BigDecimal(String.valueOf(job.getChangeDuration()));
			if(bChangeDuration.compareTo(BigDecimal.ZERO)>0) {
				LocalDateTime changeStartTime = calcChangeStartTimeByTimeWindowedJob(mapParam, job);
				LocalDateTime changeEndTime = calcDateTimeByNum(mapParam, job.getStartTime());
				if(!changeTimeList.contains(changeStartTime))
					changeTimeList.add(changeStartTime);
				if(!changeTimeList.contains(changeEndTime))
					changeTimeList.add(changeEndTime);
			}
		}
		Collections.sort(changeTimeList);
		return changeTimeList;
	}
	
	private static int calcPriority(String[] aryPriority, String prodType, String partNo) {
		int priority = -1;
		int i = aryPriority.length;
		String partNoPrefix = partNo != null ? partNo.substring(0, 2) : "";
		for(String str : aryPriority) {
			if(!"PROD".equals(prodType) && str.contains(prodType)) {
				return i;
			}else if(str.contains(partNoPrefix)){
				return i;
			}
			i--;
		}
		return priority;
	}
	
	//等到指定換線時間才能做
	private static double calcWaitForNextChangeTime(TimeWindowedJob job, double dEarlyEndTime) {
		//取得指定換線時間
		if(job.getSetup()!=null) {
			List<Double> changeTimeList = job.getSetup().getAssignSetuptimeList();
			//若指定換線時間在預計要退後的換線時間之後,則回傳指定換線時間
			for(double changTime : changeTimeList) {
				if(changTime>=dEarlyEndTime) {
					return changTime;
				}
			}
		}
		return dEarlyEndTime;
	}
	
	//若跨班需延到下一班才換
	private static List<TimeWindowedJob> calcCrossChange(Map<String, Object> mapParam, List<TimeWindowedJob> jobList) {
		Map<String, Object> mapParamChangeCrossSetting = (Map<String, Object>) mapParam.get("change_cross_setting");
		//無設定參數或參數==false則不執行
		if(mapParamChangeCrossSetting == null || mapParamChangeCrossSetting.get("in_value1")==null 
				|| mapParamChangeCrossSetting.get("in_value2")==null) {
			return jobList;
		}
		String crossChangeLevel = (String) mapParamChangeCrossSetting.get("in_value2");
		LocalDate planStartDate = ((LocalDate) ((Map)(mapParam.get("plan_start_date"))).get("in_value1"));
		LocalDate planEndDate = ((LocalDate) ((Map)(mapParam.get("plan_end_date"))).get("in_value1")).plusDays(1);
		List<Shift> shiftList = generateShiftList(planStartDate, planEndDate, mapParam, true);
		
		for(Iterator<TimeWindowedJob> iter = jobList.iterator(); iter.hasNext();) {
			TimeWindowedJob job = iter.next();
			String changeLevel = (String) job.getChangeLevel();
			String changeKey = (String) job.getChangeKey();
			BigDecimal bChangeDuration = new BigDecimal(String.valueOf(job.getChangeDuration()));
			double startTime = job.getStartTime() == null ? 0 : job.getStartTime();
			BigDecimal bProcStartTime = new BigDecimal(startTime);
			BigDecimal bChangeStartTime = bProcStartTime.subtract(bChangeDuration);
			
			//判斷是否跨班換線
			Map<String, Object> mapCrossChange = getCrossChange(mapParam, job);
			String crossShift = (String) mapCrossChange.get("cross");
			
			if(changeKey!=null && bChangeDuration.compareTo(BigDecimal.ZERO)>0 
					&& (changeLevel.equals(crossChangeLevel) || "%".equals(crossChangeLevel))) {
				//若有跨班換線發生
				if(crossShift != null) {
					double dNextStartTime = -1;
					int iCount = 0;
					for(Shift shift : shiftList) {
						double dShiftStartTime = shift.getStartTimeIndex();
						double dShiftEndTime = shift.getEndTimeIndex();
						//換線開始時間介於當班別之間,推至下一班
						if(bChangeStartTime.doubleValue()>=dShiftStartTime
								&& bChangeStartTime.doubleValue()<=dShiftEndTime) {
							if(iCount+1 < shiftList.size()) {
								dNextStartTime = shiftList.get(iCount+1).getStartTimeIndex();
								break;
							}
						}
						iCount++;
					}
					if(dNextStartTime!=-1) {
						LocalDateTime nextStartTime = calcDateTimeByNum(mapParam, dNextStartTime);
						LocalDateTime firstPriorityJobChangeStartTime = calcChangeStartTimeByTimeWindowedJob(mapParam, job);
						doMoveJob(job, jobList, nextStartTime, firstPriorityJobChangeStartTime, mapParam, false);
					}
				}
			}
		}
		return jobList;
	}
	
	private static Map<String, Object> getCrossChange(Map<String, Object> mapParam, TimeWindowedJob job) {
		LocalDateTime changeStartTime = calcChangeStartTimeByTimeWindowedJob(mapParam, job);
		double startTime = job.getStartTime()==null ? 0 : job.getStartTime();
		LocalDateTime changeEndTime = calcDateTimeByNum(mapParam, startTime);
		return getCrossChange(mapParam, changeStartTime, changeEndTime);
	}
	
	private static Map<String, Object> getCrossChange(Map<String, Object> mapParam, LocalDateTime changeStartTime, LocalDateTime changeEndTime) {
		Map<String, Object> mapCrossChange = new HashMap<>();
		int iShiftDstart = Integer.parseInt((String) ((Map)mapParam.get("shift_d_start")).get("in_value1"));
		int iShiftNstart = Integer.parseInt((String) ((Map)mapParam.get("shift_e_start")).get("in_value1"));
		
		LocalDateTime shiftDtime = LocalDateTime.of(changeStartTime.getYear(), changeStartTime.getMonth()
				, changeStartTime.getDayOfMonth(), iShiftDstart, 0, 0);
		LocalDateTime shiftNtime = LocalDateTime.of(changeStartTime.getYear(), changeStartTime.getMonth()
				, changeStartTime.getDayOfMonth(), iShiftNstart, 0, 0);
		
		if(changeStartTime.isBefore(shiftDtime) && changeEndTime.isAfter(shiftDtime)) {
			mapCrossChange.put("cross", "D"); //跨白班
		}else if(changeStartTime.isBefore(shiftNtime) && changeEndTime.isAfter(shiftNtime)) {
			mapCrossChange.put("cross", "N"); //跨晚班
		}else {
			mapCrossChange.put("cross", null); //無跨班
		}
		
		return mapCrossChange;
	}
	
	public static Map<String, Object> calcRemainingChangeLine(RJobDashboard rJob, List<ModChange> cModChangeList, String intputPartNo, Map<String, Object> mapParam){
		Map<String, Object> mapSetup = null;
		RJobDashboard lastChangeRJob = rJob;
		if(lastChangeRJob != null) {
			float changeDurationActual = lastChangeRJob.getChangeDuration();
			float changeDurationParameter = 0;
			float remainChangeDuration = 0;
			
			for(ModChange cModChange : cModChangeList) {
				if(cModChange.getChangeKey().equals(lastChangeRJob.getChangeKey()) 
						&& lastChangeRJob.getChangeLevel().equals(cModChange.getChangeLevel())) { //Add by JoshLai@20190509+
					changeDurationParameter = Float.parseFloat(cModChange.getChangeDuration());
				}
			}
			
			remainChangeDuration = changeDurationParameter - changeDurationActual;
			//未換完的換線時間且partNo相同
			if(remainChangeDuration > 0 && lastChangeRJob.getPartNo().equals(intputPartNo)) {
				mapSetup = new HashMap<String, Object>();
				mapSetup.put("CHANGE_LEVEL", lastChangeRJob.getChangeLevel());
				mapSetup.put("CHANGE_DURATION", remainChangeDuration);
				mapSetup.put("CHANGE_KEY", lastChangeRJob.getChangeKey());
				mapSetup.put("IS_REMAINING", true);
			}
			
			//S01換線排量 Change duration<=12 不允許跨班換線，只換到當班 JoshLai@20190610+
			Map<String, Object> changeCrossSetting = (Map<String, Object>) mapParam.get("change_cross_setting");
			if(changeCrossSetting!=null && changeCrossSetting.get("in_value1").equals("change_cross_remove_next_shift")) {
				if(changeDurationActual + remainChangeDuration <=12) {
					mapSetup = null;
				}
			}
		}
		return mapSetup;
	}
	
	public static List<Plan> innerJoinPlanCapa(List<Plan> list, List<EqpCapa> cEqpCapaJPA, List<String> areaList, Map<String, Object> mapParam){
		List<Plan> planList = new ArrayList<>();
		for(Plan plan : list) {
			Plan planCopy = SerializationUtils.clone(plan);
			planList.add(planCopy);
		}
		for(Plan plan : planList) {
			if(areaList.contains(AREA_JI))
				innerJoinPlanCapa(plan, cEqpCapaJPA);
			else if(areaList.contains(AREA_MA))
				innerJoinPlanCapaByPartNo(plan, cEqpCapaJPA);
		}
		logger.info("plan list before remove null capa: " + planList.size() + " : " + planList);
		Iterator<Plan> iter = planList.iterator();
		while(iter.hasNext()) {
			Plan plan = iter.next();
			if(plan.getCapa() == null)
				iter.remove();
		}
		logger.info("plan list after remove null capa: " + planList.size() + " : " + planList);
		
		//設定AdjustPpcCapa/AdjustFabPcCapa JoshLai@20190731+
		setAdjustCapa(planList, mapParam);
		logger.info("plan list after setAdjustCapa: " + planList.size() + " : " + planList);
		return planList;
	}
	
	public static void setAdjustCapa(List<Plan> rJobPlanList, Map<String, Object> mapParam) {
		for(Plan plan : rJobPlanList) {
			EqpCapa capa = SerializationUtils.clone(plan.getCapa());
			capa.setAdjustFabPcCapa(capa.getFabPcCapa());
			capa.setAdjustPpcCapa(capa.getPpcCapa());
			
			Map<String, Object> mapParamFollowPpcPlan = (Map<String, Object>) mapParam.get("follow_ppc_plan");
			if(mapParamFollowPpcPlan!=null 
					&& mapParamFollowPpcPlan.get("in_value3")!=null) {
				String invalue3 = (String) mapParamFollowPpcPlan.get("in_value3");
				
				//當capabyshift日班滿載才需要做以下調整 JoshLai@20190801+
				if("capabyshift".equalsIgnoreCase(invalue3)){
					int iPlanQty = Integer.parseInt(plan.getPlanQty());
					if(iPlanQty <= capa.getPpcCapa()/2) {
						plan.setFullDShift(true);
					}
					if(iPlanQty == capa.getPpcCapa()/2) {
						capa.setAdjustFabPcCapa(capa.getFabPcCapa()/2);
						capa.setAdjustPpcCapa(capa.getPpcCapa()/2);
					}
				}
			}
			plan.setCapa(capa);
		}
	}
	
	public static void innerJoinPlanCapa(Plan plan, List<EqpCapa> cEqpCapaJPA){
		for(EqpCapa capa : cEqpCapaJPA) {
			if(plan.getSite().equals(capa.getEqpCapaId().getSite())
					&& plan.getLine().equals(capa.getEqpCapaId().getLine())
					&& plan.getPartNo().equals(capa.getEqpCapaId().getPartNo())) {
				plan.setCapa(capa);
			}
		}
	}
	
	public static void innerJoinPlanCapaByPartNo(Plan plan, List<EqpCapa> cEqpCapaJPA){
		for(EqpCapa capa : cEqpCapaJPA) {
			if(plan.getPartNo().equals(capa.getEqpCapaId().getPartNo())) {
				plan.setCapa(capa);
			}
		}
	}
	
	public static void sortSJobDashboardList(List<SJobDashboard> sJobDashboardList) {
		//修改自定排序 JoshLai@20190619-
		/*Comparator<SJobDashboard> comparator = Comparator.comparing(SJobDashboard::getFab)
				.thenComparing(SJobDashboard::getLine)
				.thenComparing(SJobDashboard::getJobId) //修改排序,增加JobId JoshLai@20190614+
				.thenComparing(SJobDashboard::getProcessEndTime)
				.thenComparing(SJobDashboard::getChangeEndTime,
						Comparator.nullsFirst(Comparator.naturalOrder()));*/
//		Collections.sort(sJobDashboardList, comparator);
		
		Collections.sort(sJobDashboardList, new Comparator<SJobDashboard>() {
			@Override
			public int compare(SJobDashboard o1, SJobDashboard o2) {
				int iSeq1 = o1.getiSeq();
				int iSeq2 = o2.getiSeq();
				String fab1 = (String) o1.getFab();
				String fab2 = (String) o2.getFab();
				String line1 = (String) o1.getLine();
				String line2 = (String) o2.getLine();
				LocalDate shiftDate1 = o1.getShiftDate();
				LocalDate shiftDate2 = o2.getShiftDate();
				String shift1 = (String) o1.getShift();
				String shift2 = (String) o2.getShift();
				LocalDateTime processStartTime1 = (LocalDateTime) o1.getProcessStartTime();
				LocalDateTime processStartTime2 = (LocalDateTime) o2.getProcessStartTime();
				LocalDateTime processEndTime1 = (LocalDateTime) o1.getProcessEndTime();
				LocalDateTime processEndTime2 = (LocalDateTime) o2.getProcessEndTime();
				LocalDateTime changeStartTime1 = o1.getChangeStartTime()==null?(LocalDateTime) o1.getProcessStartTime():(LocalDateTime) o1.getChangeStartTime();
				LocalDateTime changeStartTime2 = o2.getChangeStartTime()==null?(LocalDateTime) o2.getProcessStartTime():(LocalDateTime) o2.getChangeStartTime();
				LocalDateTime changeEndTime1 = o1.getChangeEndTime()==null?(LocalDateTime) o1.getProcessEndTime():(LocalDateTime) o1.getChangeEndTime();
				LocalDateTime changeEndTime2 = o2.getChangeEndTime()==null?(LocalDateTime) o2.getProcessEndTime():(LocalDateTime) o2.getChangeEndTime();

				int fabCompare = fab1.compareTo(fab2);
				int lineCompare = line1.compareTo(line2);
				int shiftCompare = shift1.compareTo(shift2);
				if(fabCompare<0)
					return -1;
				else if(fabCompare>0) {
					return 1;
				}else {
					if(lineCompare<0)
						return -1;
					else if(lineCompare>0)
						return 1;
					else {
						if(iSeq1 < iSeq2)
							return -1;
						else if(iSeq1 > iSeq2)
							return 1;
						else {
							if(shiftDate1.isBefore(shiftDate2)) {
								return -1;
							}else if(shiftDate1.isAfter(shiftDate2)) {
								return 1;
							}else {
								if(shiftCompare<0) {
									return -1;
								}else if(shiftCompare>0)
									return 1;
								else {
									if(changeStartTime1.isBefore(changeStartTime2)) {
										return -1;
									}else if(changeStartTime1.isAfter(changeStartTime2)) {
										return 1;
									}else {
										if(changeEndTime1.isBefore(changeEndTime2)) {
											return -1;
										}else if(changeEndTime1.isAfter(changeEndTime2)) {
											return 1;
										}else {
											if(processStartTime1.isBefore(processStartTime2)) {
												return -1;
											}else if(processStartTime1.isAfter(processStartTime2)) {
												return 1;
											}else {
												if(processEndTime1.isBefore(processEndTime2)) {
													return -1;
												}else if(processEndTime1.isAfter(processEndTime2)) {
													return 1;
												}else
													 return 0;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		});
	}
	
	//更新job seq以便調整後COG能唯一ID
	public static void updateJobIdSeq(List<SJobDashboard> sJobDashboardList) {
		Map<String, Object> mapJobId = new HashMap<>();
		for(SJobDashboard sJob : sJobDashboardList) {
			String jobId = sJob.getJobId();
			String pureJobId = jobId.substring(0, jobId.lastIndexOf("##"));
			int seq = Integer.parseInt(jobId.substring(jobId.lastIndexOf("##")+2, jobId.length()));
			String woId = sJob.getWoId() == null ? "" : sJob.getWoId();
			String uniqueId = pureJobId+woId;
			if(mapJobId.containsKey(uniqueId)) {
				seq = (int) mapJobId.get(uniqueId);
				seq += 1;
				String newJobId = pureJobId + "##" + seq;
//				logger.info("jobId: " + jobId+ " pureJobId: " + pureJobId + " seq: " +seq + " newJobId: " + newJobId);
				sJob.setJobId(newJobId);
			}
			mapJobId.put(uniqueId, seq);
		}
	}
	
	private static String addZeroForNum(String str, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(str);// 左补0
//		    sb.append(str).append("0");//右补0  
				str = sb.toString();
				strLen = str.length();
			}
		}
		return str;
	}
	
	private static int getQtyFromSPLJob(Plan plan, List<Special> rJobSpecialList) {
		for(Special obj : rJobSpecialList) {
			if(obj.getJobType().equals("SPL")
					&& plan.getSite().equals(obj.getSite())
					&& plan.getLine().equals(obj.getLine())
					&& plan.getPartNo().equals(obj.getPartNo())
					&& plan.getShiftDate().equals(obj.getShiftDate())
					&& obj.getCellPartNo().equals(plan.getCellPartNo())
					&& plan.getGrade().equals(obj.getGrade())) {
				return Integer.parseInt(obj.getPlanQty());
			}
		}
		return 0;
	}
	
	private static int getQtyFromMASPLJob(String partNo, List<Special> rJobSpecialList) {
		for(Special obj : rJobSpecialList) {
			if(obj.getJobType().equals("SPL") && partNo.equals(obj.getPartNo())) {
				return Integer.parseInt(obj.getPlanQty());
			}
		}
		return 0;
	}
	
	/*
     * 2 days : 10/31 , 11/1
     * 4 time : 08:30, 13:30 , 20:30 , 01:30
     * startday = localdate 08:00
     * 10/31 01:30
     * 10/31 08:30 --> 0.5
     * 10/31 13:30 --> 5.5
     * 10/31 20:30 --> 12.5
     * 11/01 01:30 --> 18.5
     * 11/01 08:30
     * 11/01 13:30
     * 11/01 20:30
     * 
     * filter >= 10/31 08:00 and <= 11/1 08:00
     * 
     * */
	public static List<Double> generateSetupTimeList(List<Shift> shiftList, Map<String, Object> mapParam) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Set<String> nonDuplicateDate = new LinkedHashSet<String>();
		for (Shift shift : shiftList) {
			nonDuplicateDate.add(String.valueOf(shift.getShiftDate()));
		}
		int shiftDstart = Integer.parseInt(String.valueOf(((Map<String, Object>) mapParam.get("shift_d_start")).get("in_value1")));
		List<Double> assignSetuptimeList = new LinkedList<Double>();
		if (mapParam.get("assign_change_time") != null) {
			List<Map<String, Object>> listValues = (List<Map<String, Object>>) mapParam.get("assign_change_time");
			String time1 = (String) listValues.get(0).get("in_value1");
			String time2 = (String) listValues.get(1).get("in_value1");
			String time3 = (String) listValues.get(2).get("in_value1");
			String time4 = (String) listValues.get(3).get("in_value1");

			String startDate = shiftList.get(0).getShiftDate() + " " + shiftDstart + ":00";
			Date dStartDate;
			try {
				dStartDate = sdf.parse(startDate);
				List<String> tempList = new LinkedList<String>();
				tempList.add(time1);
				tempList.add(time2);
				tempList.add(time3);
				tempList.add(time4);

				List<Date> tempList2 = new LinkedList<Date>();
				for (String str : nonDuplicateDate) {
					for (String time : tempList) {
						String sTime = str + " " + time;
						Date dTime = sdf.parse(sTime);
						if (dTime.after(dStartDate)) // 只要擋dTime.after(dStartDate)，不用管結束時間 20190325
							tempList2.add(dTime);
					}
				}

				for (Date dTime : tempList2) {
					double hour = (dTime.getTime() - dStartDate.getTime()) / 60000 / 60.0;
					assignSetuptimeList.add(hour);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		assignSetuptimeList.sort(Comparator.comparingDouble(Double::doubleValue));
		return assignSetuptimeList;
	}
	
	public static List<LocalDateTime> generateSetupDateTimeList(List<Shift> shiftList, Map<String, Object> mapParam) {
		List<LocalDateTime> assignSetupDateTimeList = new LinkedList<>();
		List<Double> assignSetuptimeList = generateSetupTimeList(shiftList, mapParam);
		for(Double time : assignSetuptimeList) {
			LocalDateTime dateTime = calcDateTimeByNum(mapParam, time);
			assignSetupDateTimeList.add(dateTime);
		}
		return assignSetupDateTimeList;
	}
	
	public static void calcChangeCrossOnly1Sfhit(List<TimeWindowedJob> jobList, Map<String, Object> mapParam, boolean isDebug) {
		Map<String, Object> changeCrossSetting = (Map<String, Object>) mapParam.get("change_cross_setting");
		if(changeCrossSetting!=null && changeCrossSetting.get("in_value1").equals("change_cross_remove_next_shift")) {
			LocalDate planStartDate = ((LocalDate) ((Map)(mapParam.get("plan_start_date"))).get("in_value1"));
			LocalDate planEndDate = ((LocalDate) ((Map)(mapParam.get("plan_end_date"))).get("in_value1")).plusDays(1);
			List<Shift> shiftList = generateShiftList(planStartDate, planEndDate, mapParam, true);
			
			for(Iterator<TimeWindowedJob> iter = jobList.iterator(); iter.hasNext();) {
				TimeWindowedJob job = iter.next();
				String changeKey = (String) job.getChangeKey();
				BigDecimal bChangeDuration = new BigDecimal(String.valueOf(job.getChangeDuration()));
				double startTime = job.getStartTime()==null ? 0 : job.getStartTime();
				BigDecimal bProcStartTime = new BigDecimal(startTime);
				BigDecimal bChangeStartTime = bProcStartTime.subtract(bChangeDuration);
				

				//判斷是否跨班換線
				Map<String, Object> mapCrossChange = getCrossChange(mapParam, job);
				String crossShift = (String) mapCrossChange.get("cross");
				
				//若有跨班換線且換線時間<=12小時
				if(changeKey!=null && bChangeDuration.doubleValue()<=12 && crossShift != null) {
					double dNextStartTime = -1;
					int iCount = 0;
					for(Shift shift : shiftList) {
						double dShiftStartTime = shift.getStartTimeIndex();
						double dShiftEndTime = shift.getEndTimeIndex();
						//換線開始時間介於當班別之間,推至下一班
						if(bChangeStartTime.doubleValue()>=dShiftStartTime
								&& bChangeStartTime.doubleValue()<=dShiftEndTime) {
							if(iCount+1 < shiftList.size()) {
								dNextStartTime = shiftList.get(iCount+1).getStartTimeIndex();
								break;
							}
						}
						iCount++;
					}
					
//					if(isDebug) {
//						logger.debug("dNextStartTime: " + dNextStartTime
//									+ " bChangeStartTime: " + bChangeStartTime
//									+ " changeEndTime: " + job.getStartTime());
//					}
					if(dNextStartTime!=-1) {
						//捨去job.getStartTime()~dNextStartTime換線第二班
						BigDecimal bJobStarTime = new BigDecimal(job.getStartTime());
						BigDecimal bNewChangeDuration = new BigDecimal(dNextStartTime).subtract(bChangeStartTime);
						BigDecimal bSubTime = bJobStarTime.subtract(new BigDecimal(dNextStartTime));
						BigDecimal bNewJobStartTime = bJobStarTime.subtract(bSubTime);
						BigDecimal bNewJobEndTime = new BigDecimal(job.getEndTime()).subtract(bSubTime);
						LocalDateTime changeEndTime = calcDateTimeByNum(mapParam, job.getStartTime());
						LocalDateTime newChangeEndTime = calcDateTimeByNum(mapParam, dNextStartTime);
						
//						if(isDebug) {
//							logger.debug("sJobId: " + job.getJobId()
//										+ " changeEndTime: " + changeEndTime
//										+ " newChangeEndTime: " + newChangeEndTime
//										+ " bNewChangeDuration: " + bNewChangeDuration
//										+ " bSubTime: " + bSubTime
//										+ " bNewJobStartTime: " + bNewJobStartTime
//										+ " bNewJobEndTime: " + bNewJobEndTime
//										+ " oldChangeDuration: " + job.getSetupDurationFromPreviousStandstill().get("CHANGE_DURATION")
//										+ " jobList: " + jobList);
//						}
						doMoveJob(job, jobList, newChangeEndTime, changeEndTime, mapParam, false);
						job.setChangeDuration(bNewChangeDuration.floatValue());
						job.setStartTime(bNewJobStartTime.doubleValue());
						job.setEndTime(bNewJobEndTime.doubleValue());
						
//						if(isDebug) {
//							logger.debug("sJobId: " + job.getJobId()
//										+ " newChangeDuration: " + job.getSetupDurationFromPreviousStandstill().get("CHANGE_DURATION")
//										+ " changeDuration: " + job.getChangeDuration()
//										+ " jobList: " + jobList);
//						}
					}
				}
			}
		}
	}
	
	public static void setChangeDurationByTimeWindowedJob(List<TimeWindowedJob> jobList) {
		for(TimeWindowedJob job : jobList) {
			float changeDuration = Float.parseFloat(String.valueOf(job.getSetupDurationFromPreviousStandstill().get("CHANGE_DURATION")));
			job.setChangeDuration(changeDuration);
			String changeLvl = (String) job.getSetupDurationFromPreviousStandstill().get("CHANGE_LEVEL");
			job.setChangeLevel(changeLvl);
			String changeKey = (String) job.getSetupDurationFromPreviousStandstill().get("CHANGE_KEY");
			job.setChangeKey(changeKey);
		}
	}
	
	public static Map<String, Object> calcCapaMap(List<Plan> rJobPlanList){
		Map<String, Object> mapJobIdCapa = new HashMap<>();
		for(Plan plan : rJobPlanList) {
			Map<String, Object> mapCapa = new HashMap<>();
			mapCapa.put("PPC_CAPA", plan.getCapa().getPpcCapa());
			mapCapa.put("FAB_PC_CAPA", plan.getCapa().getFabPcCapa());
			mapCapa.put("ADJUST_PPC_CAPA", plan.getCapa().getAdjustPpcCapa());
			mapCapa.put("ADJUST_FAB_PC_CAPA", plan.getCapa().getAdjustFabPcCapa());
			mapCapa.put("IS_FULLDSHIFT", plan.isFullDShift());
			mapJobIdCapa.put(plan.getJobId(), mapCapa);
		}
		return mapJobIdCapa;
	}
	
	public static Map<String, Object> listParamToMapParam(List<ParParameter> listParam, LocalDate planStartDate, LocalDate planEndDate){
		Map<String, Object> mapParam = new HashMap<>();
		for(ParParameter obj : listParam) {
			Map<String, Object> mapTemp = new HashMap<>();
			mapTemp.put("in_value1", String.valueOf(obj.getInValue1()));
			mapTemp.put("in_value2", String.valueOf(obj.getInValue2()));
			mapTemp.put("in_value3", String.valueOf(obj.getInValue3()));
			mapTemp.put("in_value4", String.valueOf(obj.getInValue4()));
			
			if(mapParam.containsKey(obj.getItemName())) {
				List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
				if(mapParam.get(obj.getItemName()) instanceof Map) {
					listMap.add((Map<String, Object>) mapParam.get(obj.getItemName()));
					listMap.add(mapTemp);
				}else {
					listMap = (List<Map<String, Object>>) mapParam.get(obj.getItemName());
					listMap.add(mapTemp);
				}
				mapParam.put(obj.getItemName(), listMap);
			}else {
				
				mapParam.put(obj.getItemName(), mapTemp);
			}
		}
		Map<String, Object> mapTemp = new HashMap<>();
		mapTemp.put("in_value1", planStartDate);
		mapParam.put("plan_start_date", mapTemp);
		
		mapTemp = new HashMap<>();
		mapTemp.put("in_value1", planEndDate);
		mapParam.put("plan_end_date", mapTemp);
		return mapParam;
	}
	
	public static void getMaxRunSeqFromAdjustmentList(List<Adjustment> adjustmentList) {
		if(adjustmentList.size() > 0) {
			String previousLineNo = adjustmentList.iterator().next().getAdjustmentId().getLine();
			LocalDate previousShiftDate = adjustmentList.iterator().next().getAdjustmentId().getShiftDate();
			String previousShift = adjustmentList.iterator().next().getAdjustmentId().getShift();
			String previousJobType = adjustmentList.iterator().next().getAdjustmentId().getJobType();
			int preivousRunSeq = Integer.parseInt(adjustmentList.iterator().next().getRunSeq());
			Iterator<Adjustment> iter = adjustmentList.iterator();
			while(iter.hasNext()) {
				Adjustment adjustment = iter.next();
				if(adjustment==null)
					continue;
				int runSeq = Integer.parseInt(adjustment.getRunSeq());
				if("CHANGE".equals(adjustment.getAdjustmentId().getJobType())
						&& previousLineNo.equals(adjustment.getAdjustmentId().getLine())
						&& previousShiftDate.isEqual(adjustment.getAdjustmentId().getShiftDate())
						&& previousShift.equals(adjustment.getAdjustmentId().getShift())
						&& previousJobType.equals(adjustment.getAdjustmentId().getJobType())
						&& runSeq < preivousRunSeq) {
					iter.remove();
				}
				previousLineNo = adjustment.getAdjustmentId().getLine();
				previousShiftDate = adjustment.getAdjustmentId().getShiftDate();
				previousShift = adjustment.getAdjustmentId().getShift();
				previousJobType = adjustment.getAdjustmentId().getJobType();
			}
		}
	}
	
	//排6/2-6/5 MA: 
	//推估6/2 8:00 MA剩餘WIP = 6/1 08:00 MA WIP(5/31 N) + 6/1 COG調整後排程 – 6/1 MA調整後排程  -MA SPL
	//+ 6/2~6/5 COG調整後排程 (只抓PROD)(量產會扣掉MA SPL) + MA PM + MA SPECIAL
	public static List<Plan> calcRemainingWIP(LocalDate planStartDateMinus1, List<SLotOpwp> sLotOpwpList,
			List<Adjustment> adjustmentAllAreaList, List<RJobDashboard> rJobDashboardListBeforePlanDate,
			List<RJobDashboard> rJobDashboardListInPlanDate, List<Adjustment> adjustmentListAfterCOG,
			List<Special> rJobSpecialList, List<EqpCapa> cEqpCapaList, List<String> areaList,
			Map<String, Object> mapParam) {
		
		List<Plan> rJobPlanList = new ArrayList<Plan>();
		boolean isRemaningWIP = false;
		List<Adjustment> remainingAdjustmentList = new ArrayList<>();
		List<RJobDashboard> remainingRJobList = new ArrayList<>();
		
		//取得剩餘MA WIP
		List<SLotOpwp> remainingWIPList = new ArrayList<>();
		for(SLotOpwp wip : sLotOpwpList) {
			SLotOpwp wipCopy = SerializationUtils.clone(wip);
			//將wipQty依照相同partNo加總
			SLotOpwp samePartNoWIP = getWIPbyPartNo(remainingWIPList, wipCopy.getsLotOpwpId().getPartNo());
			if(samePartNoWIP==null) {
				remainingWIPList.add(wipCopy);
			}else {
				BigDecimal bWipQty = new BigDecimal(wipCopy.getWipQty());
				BigDecimal samePartNoWipQty = new BigDecimal(samePartNoWIP.getWipQty());
				BigDecimal bAddWipQty = bWipQty.add(samePartNoWipQty);
				samePartNoWIP.setWipQty(bAddWipQty.intValue());
			}
		}
		
		logger.info("before substarct("+remainingWIPList.size()+"): " + remainingWIPList);
		
		//若有MA WIP,則繼續抓6/1 COG調整後排程-6/1 MA調整後排程
		if(!remainingWIPList.isEmpty()) {
			List<String> partNoInCOGList = new ArrayList<>();
			isRemaningWIP = true;
			
			logger.info(adjustmentAllAreaList.size() + " adjustmentAllAreaList-0: " + adjustmentAllAreaList);
			
			List<Adjustment> adjustmentListJI = new ArrayList<>();
			List<Adjustment> adjustmentListMA = new ArrayList<>();
			for(Adjustment adjustment : adjustmentAllAreaList) {
				Adjustment adjustmentCopy = SerializationUtils.clone(adjustment);
				if(adjustmentCopy.getAdjustmentId().getArea().equals(AREA_JI)) {
					adjustmentListJI.add(adjustmentCopy);
					
					//將不重複的partNoJI加入
					if(!partNoInCOGList.contains(adjustmentCopy.getPartNo()))
						partNoInCOGList.add(adjustmentCopy.getPartNo());
					
				}else if(adjustmentCopy.getAdjustmentId().getArea().equals(AREA_MA)) {
					adjustmentListMA.add(adjustmentCopy);
				}
			}
			
			logger.info(adjustmentListJI.size() + " adjustmentListJI-0: " + adjustmentListJI);
			logger.info(adjustmentListMA.size() + " adjustmentListMA-0: " + adjustmentListMA);
			
			//COG調整後排程
			adjustmentListJI = adjustmentListJI.stream()
					.filter(adjustment -> adjustment.getAdjustmentId().getShiftDate().isEqual(planStartDateMinus1)
							&& adjustment.getAdjustmentId().getJobType().equals(JOB_TYPE_PROD))
					.collect(Collectors.toList());
			
			//MA調整後排程
			adjustmentListMA = adjustmentListMA.stream()
					.filter(adjustment -> adjustment.getAdjustmentId().getShiftDate().isEqual(planStartDateMinus1)
							&& adjustment.getAdjustmentId().getJobType().equals(JOB_TYPE_PROD))
					.collect(Collectors.toList());
			
			logger.info("planStartDateMinus1: " + planStartDateMinus1);
			logger.info(adjustmentListJI.size() + " adjustmentListJI-1: " + adjustmentListJI);
			logger.info(adjustmentListMA.size() + " adjustmentListMA-1: " + adjustmentListMA);
			
			//沒有調整就抓RJob
			List<RJobDashboard> rJobListJI = new ArrayList<>();
			List<RJobDashboard> rJobListMA = new ArrayList<>();
			if(adjustmentListJI.isEmpty()) {
				for(RJobDashboard rJob : rJobDashboardListBeforePlanDate) {
					RJobDashboard rJobCopy = SerializationUtils.clone(rJob);
					if(rJobCopy.getShiftDate().isEqual(planStartDateMinus1)) {
						String area = rJobCopy.getArea();
						if(AREA_JI.equals(area)) {
							rJobListJI.add(rJobCopy);
							
							//將不重複的partNoJI加入
							if(!partNoInCOGList.contains(rJobCopy.getPartNo()))
								partNoInCOGList.add(rJobCopy.getPartNo());
							
						}else if(AREA_MA.equals(area))
							rJobListMA.add(rJobCopy);
					}
				}
			}
			
			if((adjustmentListJI.isEmpty() && rJobListJI.isEmpty())
					|| (adjustmentListMA.isEmpty() && rJobListMA.isEmpty())) {
				isRemaningWIP = false;
			}
			
			logger.info("isRemaningWIP: " + isRemaningWIP
					+ " adjustmentListJI: " + adjustmentListJI.size()
					+ " rJobListJI: " + rJobListJI.size()
					+ " adjustmentListMA: " + adjustmentListMA.size()
					+ " rJobListMA: " + rJobListMA.size()
					+ " \r\n adjustmentListJI: " + adjustmentListJI
					+ " \r\n rJobListJI: " + rJobListJI
					+ " \r\n adjustmentListMA: " + adjustmentListMA
					+ System.lineSeparator()+ " rJobListMA: " + rJobListMA);
			
			logger.info(remainingWIPList.size() + " remainingWIPList-0: " + remainingWIPList);
			
			//若三者皆有資料才計算剩餘MA WIP
			if(isRemaningWIP) {
				//JI先減去MA
				for(Adjustment adjustJI : adjustmentListJI) {
					String partNoJI = adjustJI.getPartNo();
					BigDecimal bAdjustQtyJI = new BigDecimal(adjustJI.getPlanQty());
					
					//-MA調整後排程 for 和adjustmentListJI同料號
					for(Adjustment adjustMA : adjustmentListMA) {
						String partNoMA = adjustMA.getPartNo();
						if(partNoJI.equals(partNoMA)) {
							BigDecimal bAdjustQtyMA = new BigDecimal(adjustMA.getPlanQty());
							BigDecimal subQty = bAdjustQtyJI.min(bAdjustQtyMA);
							bAdjustQtyJI = bAdjustQtyJI.subtract(subQty);
							adjustJI.setPlanQty(bAdjustQtyJI.toPlainString());
							bAdjustQtyMA = bAdjustQtyMA.subtract(subQty);
							adjustMA.setPlanQty(bAdjustQtyMA.toPlainString()); //加到WIP後adjust歸零
						}
					}
				}
				logger.info("adjust after COG substrct MA: " + adjustmentListJI);
				
				for(RJobDashboard rJobJI : rJobListJI) {
					String partNoJI = rJobJI.getPartNo();
					BigDecimal bRJobForecastQtyJI = new BigDecimal(rJobJI.getForecastQty());
					
					//-若無MA COG調整後排程則抓RJob for 和rJobListJI同料號
					for(RJobDashboard rJobMA : rJobListMA) {
						String partNoMA = rJobMA.getPartNo();
						if(partNoJI.equals(partNoMA)) {
							BigDecimal bRJobForecastQtyMA = new BigDecimal(rJobMA.getForecastQty());
							BigDecimal subQty = bRJobForecastQtyJI.min(bRJobForecastQtyMA);
							bRJobForecastQtyJI = bRJobForecastQtyJI.subtract(subQty);
							rJobJI.setForecastQty(bRJobForecastQtyJI.intValue());
							bRJobForecastQtyMA = bRJobForecastQtyMA.subtract(subQty);
							rJobMA.setForecastQty(bRJobForecastQtyMA.intValue()); //加到WIP後forecastQty歸零
						}
					}
				}
				logger.info("rJob after COG substrct MA: " + rJobListJI);
				
				List<SLotOpwp> extraSLotOpwpList = new ArrayList<>(); //partNo不存在於remainingWIPList,需要新增的部分 JoshLai@20190903+
				List<String> partNoInWIPList = new ArrayList<>();
				for(SLotOpwp wip : remainingWIPList) {
					String wipPartNo = wip.getsLotOpwpId().getPartNo();
					BigDecimal bWipQty = new BigDecimal(wip.getWipQty());
					
					if(!partNoInWIPList.contains(wipPartNo))
						partNoInWIPList.add(wipPartNo);
					
					//+COG調整後排程
					for(Adjustment adjustJI : adjustmentListJI) {
						String partNoJI = adjustJI.getPartNo();
						if(wipPartNo.equals(partNoJI)) {
							BigDecimal bAdjustQtyJI = new BigDecimal(adjustJI.getPlanQty());
							bWipQty = bWipQty.add(bAdjustQtyJI);
							adjustJI.setPlanQty("0"); //加到WIP後adjust歸零
						}
					}
					//+若無JI COG調整後排程則抓RJob
					for(RJobDashboard rJobJI : rJobListJI) {
						String partNoJI = rJobJI.getPartNo();
						if(wipPartNo.equals(partNoJI)) {
							BigDecimal bRJobForecastQty = new BigDecimal(rJobJI.getForecastQty());
							bWipQty = bWipQty.add(bRJobForecastQty);
							rJobJI.setForecastQty(0);
						}
					}
					//-MA調整後排程 for 和remainingWIPList同料號
					for(Adjustment adjustMA : adjustmentListMA) {
						String partNoMA = adjustMA.getPartNo();
						if(wipPartNo.equals(partNoMA)) {
							BigDecimal bAdjustQtyMA = new BigDecimal(adjustMA.getPlanQty());
							BigDecimal subQty = bWipQty.min(bAdjustQtyMA);
							bWipQty = bWipQty.subtract(subQty);
							bAdjustQtyMA = bAdjustQtyMA.subtract(subQty);
							adjustMA.setPlanQty(bAdjustQtyMA.toPlainString()); //加到WIP後adjust歸零
						}
					}
					//-若無MA COG調整後排程則抓RJob
					for(RJobDashboard rJob : rJobListMA) {
						String partNoMA = rJob.getPartNo();
						if(wipPartNo.equals(partNoMA)) {
							BigDecimal bRJobForecastQty = new BigDecimal(rJob.getForecastQty());
							BigDecimal subQty = bWipQty.min(bRJobForecastQty);
							bWipQty = bWipQty.subtract(subQty);
							bRJobForecastQty = bRJobForecastQty.subtract(subQty);
							rJob.setForecastQty(bRJobForecastQty.intValue()); //加到WIP後forecastQty歸零
						}
					}
					wip.setWipQty(bWipQty.intValue());
				}
				logger.info(remainingWIPList.size()+" remainingWIPList-1: " +remainingWIPList);
				logger.info("--substract-- " + " adjustmentListMA: " + adjustmentListMA);
				
				//求差集,即JI的partNo不存在於WIPList
				partNoInCOGList.removeAll(partNoInWIPList);
				for(String partNoJi : partNoInCOGList) {
					for(Adjustment adjustJI : adjustmentListJI) {
						if(partNoJi.equals(adjustJI.getPartNo())) {
							SLotOpwp sLotOpwp = new SLotOpwp();
							SLotOpwpId sLotOpwpId = new SLotOpwpId();
							sLotOpwpId.setSite(adjustJI.getAdjustmentId().getSite());
							sLotOpwpId.setFab(adjustJI.getAdjustmentId().getFab());
							sLotOpwpId.setArea(AREA_MA);
							sLotOpwpId.setPartNo(partNoJi);
							sLotOpwp.setsLotOpwpId(sLotOpwpId);
							sLotOpwp.setWipQty(Integer.parseInt(adjustJI.getPlanQty()));
							extraSLotOpwpList.add(sLotOpwp);
						}
					}
					
					for(RJobDashboard rJobJI : rJobListJI) {
						if(partNoJi.equals(rJobJI.getPartNo())) {
							SLotOpwp sLotOpwp = new SLotOpwp();
							SLotOpwpId sLotOpwpId = new SLotOpwpId();
							sLotOpwpId.setSite(rJobJI.getSite());
							sLotOpwpId.setFab(rJobJI.getFab());
							sLotOpwpId.setArea(AREA_MA);
							sLotOpwpId.setPartNo(partNoJi);
							sLotOpwp.setsLotOpwpId(sLotOpwpId);
							sLotOpwp.setWipQty(rJobJI.getForecastQty());
							extraSLotOpwpList.add(sLotOpwp);
						}
					}
				}
				
				//將不存在於WIPList的partNo加上 JoshLai@20190903+
				remainingWIPList.addAll(extraSLotOpwpList);
				
				logger.info(extraSLotOpwpList.size() + " extraSLotOpwpList: " + extraSLotOpwpList);
				logger.info(remainingWIPList.size()+" remainingWIPList-2: " +remainingWIPList);
			}
		}
		
		//+planStartDate~planEndDate COG調整後排程 (PROD) (COG.PROD需扣掉MA的SPECIAL)+ MA PM + MA SPECIAL
		//MA剩餘WIP轉Plan
		for(SLotOpwp wip : remainingWIPList) {
			Plan plan = new Plan();
			plan.setSite(wip.getsLotOpwpId().getSite());
			plan.setFab(wip.getsLotOpwpId().getFab());
			plan.setArea(wip.getsLotOpwpId().getArea());
			plan.setLine(null);
			LocalDate planStartDate = (LocalDate) ((Map)mapParam.get("plan_start_date")).get("in_value1");
			String strShiftDate = planStartDate.format(formatterNoSlash);
			String jobId = strShiftDate + "-" + wip.getsLotOpwpId().getPartNo();
			plan.setJobId(jobId);
			plan.setShiftDate(planStartDate);
			plan.setModelNo(null);
			plan.setPartNo(wip.getsLotOpwpId().getPartNo());
			plan.setCellPartNo(null);
			plan.setPlanQty(String.valueOf(wip.getWipQty()));
			plan.setJobType(JOB_TYPE_PROD);
			rJobPlanList.add(plan);
		}
		logger.info(rJobPlanList.size() + " rJobPlanList==> " + rJobPlanList);
		
		//+COG調整後排程 (PROD) by param.wip_hours切班
		//依照當天shiftDate有adjust抓adjust,沒adjust抓RJob
		LocalDate planStartDate = (LocalDate) ((Map)mapParam.get("plan_start_date")).get("in_value1");
		LocalDate planEndDate = (LocalDate) ((Map)mapParam.get("plan_end_date")).get("in_value1");
		long planDaysBewteen = ChronoUnit.DAYS.between(planStartDate, planEndDate);
		
		List<Object> adjustAndRJobList = new ArrayList<>();
		LocalDate lDate = planStartDate;
		for(int i=0; i<planDaysBewteen; i++) {
			boolean isAddAdjust = false;
			for(Adjustment adj : adjustmentListAfterCOG) {
				Adjustment adjCopy = SerializationUtils.clone(adj);
				if(lDate.isEqual(adjCopy.getAdjustmentId().getShiftDate())) {
					adjustAndRJobList.add(adjCopy);
					isAddAdjust = true; //該sfhitDate有調整
				}
			}
			logger.info("lDate: " + lDate+ " adjustAndRJobList-0: " + adjustAndRJobList.size());
			//沒有調整就抓RJob
			if (!isAddAdjust) {
				for(RJobDashboard rJob : rJobDashboardListInPlanDate) {
					RJobDashboard rJobCopy = SerializationUtils.clone(rJob);
					if(lDate.isEqual(rJobCopy.getShiftDate()) && AREA_JI.equals(rJobCopy.getArea()))
						adjustAndRJobList.add(rJobCopy);
				}
				
				logger.info("lDate: " + lDate+ " adjustAndRJobList-1: " + adjustAndRJobList.size());
			}
			lDate = lDate.plusDays(1);
		}
		
		logger.info(adjustAndRJobList.size() + " adjustAndRJobList: " + adjustAndRJobList);
		
		//COG調整後排程 (PROD)
		//依照每4小時切開
		List<Plan> splitRJobPlanList = splitAdjListByWIPhour(adjustAndRJobList, mapParam);
		logger.info(splitRJobPlanList.size() + " splitRJobPlanList: " + splitRJobPlanList);
		logger.info(rJobPlanList.size() + " rJobPlanList-0: " + rJobPlanList);
		
		 //MA剩餘WIP + COG調整後排程 (PROD) 
		rJobPlanList.addAll(splitRJobPlanList);
		logger.info(rJobPlanList.size() + " rJobPlanList-1: " + rJobPlanList);
		
		//濾掉90!91! & 清線
		filterOutRJobPlanList(rJobPlanList, mapParam);
		logger.info(rJobPlanList.size() + " rJobPlanList-2: --keep 97!-- " +rJobPlanList);
		
		//-MA Sepcial Qty
		for(Plan plan : rJobPlanList) {
			BigDecimal bPlanQty = new BigDecimal(plan.getPlanQty());
			BigDecimal bSplQty = new BigDecimal(getQtyFromMASPLJob(plan.getPartNo(), rJobSpecialList));
			BigDecimal bSubQty = bPlanQty.min(bSplQty);
			bPlanQty = bPlanQty.subtract(bSubQty);
			plan.setPlanQty(bPlanQty.toPlainString());
		}
		logger.info(rJobPlanList.size() + " rJobPlanList-3: --substract MA SPL-- " +rJobPlanList);
		
		for(Plan plan : rJobPlanList) {
			plan.setContinueMaxShiftDate(plan.getShiftDate());
		}
		
		//join capa
		rJobPlanList = innerJoinPlanCapa(rJobPlanList, cEqpCapaList, areaList, mapParam);
		logger.info(rJobPlanList.size() + " rJobPlanList-4: --innerjoin CAPA-- " +rJobPlanList);
		
		return rJobPlanList;
	}
	
	private static void filterOutRJobPlanList(List<Plan> rJobPlanList, Map<String, Object> mapParam) {
		boolean isClearLine = false;
		int clearLineUnderQty = -1;
		Map<String, Object> mapClearLine = (Map<String, Object>) mapParam.get("clear_line");
		if(mapClearLine!=null && ((Map)mapClearLine).get("in_value1")!=null
				&& !"null".equals(((String)((Map)mapClearLine).get("in_value1")))) {
			isClearLine = true;
			clearLineUnderQty = Integer.parseInt((String)((Map)mapClearLine).get("in_value1"));
		}
		for(Iterator<Plan> iter = rJobPlanList.iterator(); iter.hasNext();) {
			Plan plan = iter.next();
			
			//濾掉90!91!
			//<100片為清線，可不排
			int wipQty = Integer.parseInt(plan.getPlanQty());
			String partNo = plan.getPartNo();
			//update by avonchung 20190903 檢查所有條件，其中有符合再刪除
			if( (partNo.startsWith("90") || partNo.startsWith("91") ) || (isClearLine && wipQty<=clearLineUnderQty)) {
				iter.remove();
			}		
			
		}
	}
	
	private static List<Plan> splitAdjListByWIPhour(List<Object> adjustAndRJobList, Map<String, Object> mapParam){
		List<Plan> rJobPlanList = new ArrayList<>();
		
		if(!mapParam.containsKey("wip_hours") || !((Map)mapParam.get("wip_hours")).containsKey("in_value1"))
			return rJobPlanList;
		
		LocalDate planStartDate = (LocalDate) ((Map)mapParam.get("plan_start_date")).get("in_value1");
		int shiftDStart = Integer.parseInt((String) ((Map)mapParam.get("shift_d_start")).get("in_value1"));
		
		BigDecimal bWipHour = new BigDecimal(String.valueOf(((Map)mapParam.get("wip_hours")).get("in_value1")));
		for(Object obj : adjustAndRJobList) {
			String site = null;
			String fab = null;
			String line = null;
			String jobId = null;
			LocalDate shiftDate = null;
			String modelNo = null;
			String partNo = null;
			String grade = null;
			LocalDateTime procStartTime = null;
			LocalDateTime procEndTime = null;
			BigDecimal bPlanQty = null;
			String jobType = null;
			if(obj instanceof Adjustment) {
				site = ((Adjustment) obj).getAdjustmentId().getSite();
				fab = ((Adjustment) obj).getAdjustmentId().getFab();
				line = ((Adjustment) obj).getAdjustmentId().getLine();
				shiftDate = ((Adjustment) obj).getAdjustmentId().getShiftDate();
				modelNo = ((Adjustment) obj).getAdjustmentId().getModelNo();
				partNo = ((Adjustment) obj).getPartNo();
				grade = ((Adjustment) obj).getAdjustmentId().getGrade();
				procStartTime = ((Adjustment) obj).getProcessStartTime();
				procEndTime = ((Adjustment) obj).getProcessEndTime();
				bPlanQty = new BigDecimal(((Adjustment) obj).getPlanQty());
				jobType = ((Adjustment) obj).getAdjustmentId().getJobType();
				String seq = ((Adjustment) obj).getRunSeq();
				String strShiftDate = shiftDate.format(formatterNoSlash);
				jobId = line + "-" + strShiftDate + "-" + partNo + "-" + grade + "##" + seq;
			}else if(obj instanceof RJobDashboard) {
				site = ((RJobDashboard) obj).getSite();
				fab = ((RJobDashboard) obj).getFab();
				line = ((RJobDashboard) obj).getLine();
				jobId = ((RJobDashboard) obj).getJobId();
				shiftDate = ((RJobDashboard) obj).getShiftDate();
				modelNo = ((RJobDashboard) obj).getModelNo();
				partNo = ((RJobDashboard) obj).getPartNo();
				grade = ((RJobDashboard) obj).getGrade();
				procStartTime = ((RJobDashboard) obj).getProcessStartTime();
				procEndTime = ((RJobDashboard) obj).getProcessEndTime();
				bPlanQty = new BigDecimal(((RJobDashboard) obj).getForecastQty());
				jobType = ((RJobDashboard) obj).getJobType();
			}
			
			BigDecimal bSecondsBetween = new BigDecimal(ChronoUnit.SECONDS.between(procStartTime, procEndTime));
			BigDecimal bDurationHour = bSecondsBetween.divide(BIGDECIMAL_60, 10, RoundingMode.HALF_UP)
										.divide(BIGDECIMAL_60, 10, RoundingMode.HALF_UP);
			BigDecimal bDivide = bDurationHour.divide(bWipHour, 0, RoundingMode.CEILING);
			
//			logger.info("bDivide====> " + bDivide + " procStartTime: " + procStartTime + " procEndTime: " + procEndTime
//						+ " bSecondsBetween: " + bSecondsBetween
//						+ " bDurationHour: " + bDurationHour
//						+ " bPlanQty: " + bPlanQty
//						+ " adjustAndRJobList("+adjustAndRJobList.size()+"): " + adjustAndRJobList);
			
			BigDecimal bDeltaPlanQty = bPlanQty;
			LocalDateTime previousProcStartTime = procStartTime;
			for(int i=0; i<bDivide.intValue(); i++) {
				//每4小時切開後,抓取最小結束時間當作readyTime,例如該RJob處理時間僅3小時,則抓取3小時當作readyTime
				//若切班僅有1班且不滿4小時,則readTime使用procStartTime+4 JoshLai@20190830+
				LocalDateTime procEndTimePlus = previousProcStartTime.plusHours(bWipHour.longValue());
				LocalDateTime minProcEndTime = procEndTime;
				
				if(bDivide.compareTo(BigDecimal.ONE)==0) {
					minProcEndTime = procEndTimePlus;
				}else {
					if(procEndTimePlus.isBefore(procEndTime))
						minProcEndTime = procEndTimePlus;
				}
				
				//依照時長換算qty
				BigDecimal bPerSecQty = bPlanQty.divide(bSecondsBetween, 10, RoundingMode.CEILING);
				BigDecimal bProcDurationSec = new BigDecimal(ChronoUnit.SECONDS.between(previousProcStartTime, minProcEndTime));
				BigDecimal bCalcPlanQty = bPerSecQty.multiply(bProcDurationSec).setScale(0, RoundingMode.HALF_UP);
				
				//和原始planQty相差的部分,在最後一筆qty加減
				bDeltaPlanQty = bDeltaPlanQty.subtract(bCalcPlanQty);
				if(i+1==bDivide.intValue()) {
					bCalcPlanQty = bCalcPlanQty.add(bDeltaPlanQty);
				}
				
				Plan plan = new Plan();
				plan.setSite(site);
				plan.setFab(fab);
				plan.setArea(AREA_MA);
				plan.setLine(line);
				plan.setJobId(jobId);
				plan.setShiftDate(shiftDate);
				plan.setModelNo(modelNo);
				plan.setPartNo(partNo);
				plan.setCellPartNo(null);
				plan.setGrade(grade);
				plan.setPlanQty(bCalcPlanQty.toPlainString());
				plan.setJobType(jobType);
				
				//用planStartDate + shiftDStart換算readyTime
				double readyTime = calcHoursFromStartDateTime(planStartDate, shiftDStart, minProcEndTime);
				plan.setReadyTime(readyTime);
				
//				logger.info("split=> " + " from: " +previousProcStartTime + "  to: " + minProcEndTime 
//						+ " readyTime: " + readyTime
//						+ " bCalcPlanQty: " +bCalcPlanQty
//						+ " bDeltaPlanQty: " + bDeltaPlanQty);
				
				previousProcStartTime = minProcEndTime; //這筆的結束時間=下一筆開始時間
				rJobPlanList.add(plan);
			}
		}
		logger.info(rJobPlanList.size() + " rJobPlanList: " + rJobPlanList);
		return rJobPlanList;
	}
	
	private static SLotOpwp getWIPbyPartNo(List<SLotOpwp> sLotOpwpList, String partNo) {
		for(SLotOpwp wip : sLotOpwpList) {
			if(partNo.equals(wip.getsLotOpwpId().getPartNo()))
				return wip;
		}
		return null;
	}
	
	public static List<SJobScore> genConstraintMatch(ScoreDirector<TaskAssignmentSolution> guiScoreDirector) throws CloneNotSupportedException {
		List<SJobScore> scoreList = new ArrayList<SJobScore>();
    	Score scoreTotal = guiScoreDirector.calculateScore();
    	logger.debug("--- scoreTotal : " + scoreTotal.toString() + "----");
    	
		for (ConstraintMatchTotal constraintMatchTotal : guiScoreDirector.getConstraintMatchTotals()) {

    	    String constraintName = constraintMatchTotal.getConstraintName();
    	    // The score impact of that constraint
    	    Score scoreSubTotal = constraintMatchTotal.getScore();
    	    logger.debug("constraintName: " + constraintName + " : " + constraintMatchTotal.toString());
    	    logger.debug("--- scoreSubTotal : " + scoreSubTotal.toShortString() + "----");
    	    
    	    SJobScore jobScore = new SJobScore();
    	    jobScore.setSjobscoreId(new SJobScoreId());
    	    jobScore.getSjobscoreId().setTotalScore(scoreTotal.toShortString());
    		jobScore.getSjobscoreId().setConstraintItem(constraintName);
    		Integer seq = 1;
    	    for (ConstraintMatch constraintMatch : constraintMatchTotal.getConstraintMatchSet()) {
    	        Score score = constraintMatch.getScore();
    	        logger.debug(constraintMatch.getIdentificationString());
    	        logger.debug("score : " + score.toShortString());
    	        
    	        jobScore = (SJobScore) jobScore.clone();
    	        jobScore.getSjobscoreId().setItemSeq(seq.toString());
    	        jobScore.getSjobscoreId().setScore(score.toShortString());
    	        seq++;
    	        
    	        logger.debug("constraintMatch.getJustificationList().size() " + constraintMatch.getJustificationList().size() + " list: " + constraintMatch.getJustificationList());
    	        for (Object item : constraintMatch.getJustificationList()) {
    	        	if(item instanceof TimeWindowedJob) {
    	        		logger.debug("instanceof TimeWindowedJob, constraintName: " + constraintName);
    	        		TimeWindowedJob job = (TimeWindowedJob) item;
    	        		jobScore.getSjobscoreId().setSite(job.getSite());
    	        		jobScore.getSjobscoreId().setScoreItem("job_id");
    	        		jobScore.setItemValue(job.getJobId());
    	        		scoreList.add(jobScore);
    	        		
    	        		jobScore = (SJobScore) jobScore.clone();
    	        		jobScore.getSjobscoreId().setScoreItem("job_type");
    	        		jobScore.setItemValue(job.getJobType());
    	        		scoreList.add(jobScore);
    	        		
    	        		jobScore = (SJobScore) jobScore.clone();
		        	    switch(constraintName) {
		        	    case "AssignLine":
		        	    	jobScore.getSjobscoreId().setScoreItem("assign_line");
        	        		jobScore.setItemValue(job.getAssignLine().toString());
        	        		scoreList.add(jobScore);
        	        		
        	        		jobScore = (SJobScore) jobScore.clone();
        	        		jobScore.getSjobscoreId().setScoreItem("line");
        	        		jobScore.setItemValue(job.getLine().toString());
        	        		scoreList.add(jobScore);
		        	    	break;
		        	    case "EndAfterDueTime":
		        	    	jobScore.getSjobscoreId().setScoreItem("end_time");
        	        		jobScore.setItemValue(Double.toString(job.getEndTime()));
        	        		scoreList.add(jobScore);
        	        		
        	        		jobScore = (SJobScore) jobScore.clone();
        	        		jobScore.getSjobscoreId().setScoreItem("due_time");
        	        		jobScore.setItemValue(Double.toString(job.getDueTime()));
        	        		scoreList.add(jobScore);
		        	    	break;
		        	    case "PMPriority":
		        	    	jobScore.getSjobscoreId().setScoreItem("assign_start_time");
        	        		jobScore.setItemValue(Double.toString(job.getAssignStartTime()));
        	        		scoreList.add(jobScore);
        	        		
        	        		jobScore = (SJobScore) jobScore.clone();
        	        		jobScore.getSjobscoreId().setScoreItem("start_time");
        	        		jobScore.setItemValue(job.getStartTime().toString());
        	        		scoreList.add(jobScore);
        	        		
        	        		jobScore = (SJobScore) jobScore.clone();
        	        		jobScore.getSjobscoreId().setScoreItem("change_duration");
//        	        		jobScore.setItemValue(((Float)job.getSetupDurationFromPreviousStandstill().get("CHANGE_DURATION")).toString());
        	        		jobScore.setItemValue(String.valueOf(job.getChangeDuration())); //JoshLai@20190610+
        	        		scoreList.add(jobScore);
		        	    	break;
		        	    case "AccumulateSetupTime":
		        	    	jobScore.getSjobscoreId().setScoreItem("change_duration");
        	        		jobScore.setItemValue(((Float)job.getSetupDurationFromPreviousStandstill().get("CHANGE_DURATION")).toString());
        	        		scoreList.add(jobScore);
        	        		
        	        		jobScore = (SJobScore) jobScore.clone();
        	        		jobScore.getSjobscoreId().setScoreItem("change_level");
//        	        		jobScore.setItemValue(job.getChange_level());
        	        		jobScore.setItemValue((String)job.getSetupDurationFromPreviousStandstill().get("CHANGE_LEVEL"));
        	        		scoreList.add(jobScore);    
        	        		
        	        		jobScore = (SJobScore) jobScore.clone();
        	        		jobScore.getSjobscoreId().setScoreItem("start_time");
        	        		jobScore.setItemValue(job.getStartTime().toString());
        	        		scoreList.add(jobScore);   
        	        		
        	        		jobScore = (SJobScore) jobScore.clone();
        	        		jobScore.getSjobscoreId().setScoreItem("change_key");
//        	        		jobScore.setItemValue(job.getChange_level());
        	        		jobScore.setItemValue((String)job.getSetupDurationFromPreviousStandstill().get("CHANGE_KEY"));
        	        		scoreList.add(jobScore);  
        	        		break;
		        	    case "LineCost":
		        	    	jobScore = (SJobScore) jobScore.clone();
        	        		jobScore.getSjobscoreId().setScoreItem("line");
//        	        		jobScore.setItemValue(job.getLineNo());
        	        		jobScore.setItemValue(job.getLine().getLineNo());//update by avonchung 2019/08/29
        	        		scoreList.add(jobScore);
		        	    	break;
		        	    case "MinMakespan"://add by avonchung 20190415
		        	    	jobScore.getSjobscoreId().setScoreItem("end_time");
        	        		jobScore.setItemValue(Double.toString(job.getEndTime()));
        	        		scoreList.add(jobScore);
        	        		
        	        		jobScore = (SJobScore) jobScore.clone(); //add by avonchung 2019/08/29
        	        		jobScore.getSjobscoreId().setScoreItem("line");
        	        		jobScore.setItemValue(job.getLine().getLineNo());
        	        		scoreList.add(jobScore);
        	        		
        	        		jobScore = (SJobScore) jobScore.clone();
        	        		jobScore.getSjobscoreId().setScoreItem("due_time");
        	        		jobScore.setItemValue(Double.toString(job.getDueTime()));
        	        		scoreList.add(jobScore);
		        	    	break;	
		        	    case "DiscontinuousProduction"://add by avonchung 20190415
		        	    	jobScore.getSjobscoreId().setScoreItem("part_no");
        	        		jobScore.setItemValue(job.getPartNo());
        	        		scoreList.add(jobScore);
        	        		
        	        		jobScore = (SJobScore) jobScore.clone();
        	        		jobScore.getSjobscoreId().setScoreItem("line");
//        	        		jobScore.setItemValue(job.getLineNo());
        	        		jobScore.setItemValue(job.getLine().getLineNo());//update by avonchung 2019/08/29
        	        		scoreList.add(jobScore);
		        	    	break;
		        	    case "CrossChange"://add by avonchung 20190415
		        	    	jobScore.getSjobscoreId().setScoreItem("change_duration");
        	        		jobScore.setItemValue(((Float)job.getSetupDurationFromPreviousStandstill().get("CHANGE_DURATION")).toString());
        	        		scoreList.add(jobScore);
        	        		
        	        		jobScore = (SJobScore) jobScore.clone();
        	        		jobScore.getSjobscoreId().setScoreItem("start_time");
        	        		jobScore.setItemValue(job.getStartTime().toString());
        	        		scoreList.add(jobScore);
        	        		break;
		        	    case "AccumulateWaittoLogon":
		        	    	jobScore.getSjobscoreId().setScoreItem("start_time");
        	        		jobScore.setItemValue(job.getStartTime().toString());
        	        		scoreList.add(jobScore);
        	        		
        	        		jobScore = (SJobScore) jobScore.clone();
        	        		jobScore.getSjobscoreId().setScoreItem("ready_time");
        	        		jobScore.setItemValue(String.valueOf(job.getReadyTime()));
        	        		scoreList.add(jobScore);
        	        		break;
		        	    case "AccumulateManpowerKilo":
		        	    	jobScore.getSjobscoreId().setScoreItem("manpower");
        	        		jobScore.setItemValue(String.valueOf(job.getManpowerKilo()));
        	        		scoreList.add(jobScore);
        	        		break;
		        	    }
    	        	}else if(item instanceof CFacConstraintCapa) {
    	        		logger.debug("instanceof CFacConstraintCapa, constraintName: " + constraintName);
    	        		CFacConstraintCapa cCapa = (CFacConstraintCapa) item;
    	        		jobScore.getSjobscoreId().setSite(cCapa.getSite());
    	        		jobScore.getSjobscoreId().setScoreItem("fab");
    	        		jobScore.setItemValue(cCapa.getFab());
    	        		scoreList.add(jobScore);
    	        		
    	        		jobScore = (SJobScore) jobScore.clone();
    	        		jobScore.getSjobscoreId().setScoreItem("shift_date");
    	        		jobScore.setItemValue(cCapa.getShift_date().toString());
    	        		scoreList.add(jobScore);
    	        		
    	        		jobScore = (SJobScore) jobScore.clone();
    	        		jobScore.getSjobscoreId().setScoreItem("shift");
    	        		jobScore.setItemValue(cCapa.getShift());
    	        		scoreList.add(jobScore);
    	        		
    	        		jobScore = (SJobScore) jobScore.clone();
    	        		jobScore.getSjobscoreId().setScoreItem("limit");
    	        		jobScore.setItemValue(Integer.toString(cCapa.getItem_value()));
    	        		scoreList.add(jobScore);
    	        		
    	        	}else if(item instanceof SJobDashboard) {
    	        		logger.debug("instanceof SJobDashboard, constraintName: " + constraintName);
    	        		SJobDashboard job = (SJobDashboard) item;
    	        		jobScore.getSjobscoreId().setSite(job.getSite());
    	        		jobScore.getSjobscoreId().setScoreItem("job_id");
    	        		jobScore.setItemValue(job.getJobId());
    	        		scoreList.add(jobScore);
    	        		
    	        		jobScore = (SJobScore) jobScore.clone();
    	        		jobScore.getSjobscoreId().setScoreItem("job_type");
    	        		jobScore.setItemValue(job.getJobType());
    	        		scoreList.add(jobScore);
    	        		
    	        		switch(constraintName) {
		        	    case "CrossChange":
        	        		jobScore = (SJobScore) jobScore.clone();
        	        		jobScore.getSjobscoreId().setScoreItem("change_start_time");
        	        		jobScore.setItemValue(job.getChangeStartTime().toString());
        	        		scoreList.add(jobScore);
        	        		
        	        		jobScore = (SJobScore) jobScore.clone();
        	        		jobScore.getSjobscoreId().setScoreItem("change_end_time");
        	        		jobScore.setItemValue(job.getChangeEndTime().toString());
        	        		scoreList.add(jobScore);
		        	    	break;
		        	    case "SPLPriority":
		        	    case "ENGPriority":
		        	    	jobScore = (SJobScore) jobScore.clone();
        	        		jobScore.getSjobscoreId().setScoreItem("assign_shift");
        	        		jobScore.setItemValue(job.getAssignShift());
        	        		scoreList.add(jobScore);
        	        		
		        	    	if(job.getChangeDuration() > 0) {
		        	    		jobScore = (SJobScore) jobScore.clone();
	        	        		jobScore.getSjobscoreId().setScoreItem("change_shift");
	        	        		jobScore.setItemValue(job.getChangeShift());
	        	        		scoreList.add(jobScore);
	        	        		
	        	        		jobScore = (SJobScore) jobScore.clone();
	        	        		jobScore.getSjobscoreId().setScoreItem("change_start_time");
	        	        		jobScore.setItemValue(job.getChangeStartTime().toString());
	        	        		scoreList.add(jobScore);
		        	    	}else {
		        	    		jobScore = (SJobScore) jobScore.clone();
	        	        		jobScore.getSjobscoreId().setScoreItem("shift");
	        	        		jobScore.setItemValue(job.getShift());
	        	        		scoreList.add(jobScore);
	        	        		
	        	        		jobScore = (SJobScore) jobScore.clone();
	        	        		jobScore.getSjobscoreId().setScoreItem("process_start_time");
	        	        		jobScore.setItemValue(job.getProcessStartTime().toString());
	        	        		scoreList.add(jobScore);
		        	    	}
    	        		}
    	        	}else if(item instanceof SJobDashboardKey) {
    	        		logger.debug("instanceof SJobDashboardKey, constraintName: " + constraintName);
    	        		SJobDashboardKey job = (SJobDashboardKey) item;
    	        		jobScore.getSjobscoreId().setSite(job.getSite());
    	        		
    	        		switch(constraintName) {
		        	    case "SameDayChange":
		        	    	jobScore = (SJobScore) jobScore.clone();
        	        		jobScore.getSjobscoreId().setScoreItem("change_shift_date");
        	        		jobScore.setItemValue(job.getChangeShiftDate().toString());
        	        		scoreList.add(jobScore);
        	        		
        	        		jobScore = (SJobScore) jobScore.clone();
        	        		jobScore.getSjobscoreId().setScoreItem("change_shift");
        	        		jobScore.setItemValue(job.getChangeShift());
        	        		scoreList.add(jobScore);
		        	    	break;
    	        		}
    	        	}else if(item instanceof Map) {
    	        		logger.debug("instanceof Map, constraintName: " + constraintName + " Map=> " + item);
    	        		Map<String, Object> map = (Map<String, Object>) item;
    	        		if(map.get("is_map_score") != null) {
    	        			for(Map.Entry<String, Object> entry1 : map.entrySet()) {
    	        				String[] keys = entry1.getKey().split("_");
    	        				if(keys.length <= 3)
    	        					continue;
    	        				jobScore = (SJobScore) jobScore.clone();
    	        				jobScore.getSjobscoreId().setSite(keys[0]);
            	        		jobScore.getSjobscoreId().setScoreItem(entry1.getKey());
            	        		
								for (Iterator<Map.Entry<String, Object>> it = ((Map<String, Object>) entry1.getValue()).entrySet().iterator(); it.hasNext();) {
									Map.Entry<String, ?> entry2 = it.next();
									if ("0".equals(String.valueOf(entry2.getValue()))) {
										it.remove();
									}
								}
								jobScore.setItemValue(entry1.getValue().toString());
								scoreList.add(jobScore);
    	        			}
    	        		}else if(map.get("is_change_limit") != null) {
    	        			for(Map.Entry<String, Object> entry1 : map.entrySet()) {
    	        				String[] keys = entry1.getKey().split("_");
    	        				if(keys.length <= 3)
    	        					continue;
    	        				jobScore = (SJobScore) jobScore.clone();
    	        				jobScore.getSjobscoreId().setSite(keys[0]);
            	        		jobScore.getSjobscoreId().setScoreItem(entry1.getKey());
								jobScore.setItemValue(entry1.getValue().toString());
								scoreList.add(jobScore);
    	        			}
    	        		}else if(map.get("is_map_param") != null) {
    	        			if(constraintName.equals("ChangeLimit")) {
    	        				Object obj = map.get("change_limit");
    	        				if(obj != null) {
	        						jobScore = (SJobScore) jobScore.clone();
	    	        				jobScore.getSjobscoreId().setSite( ((Map<String, String>)map.get("site")).get("in_value1") );
	    	        				jobScore.getSjobscoreId().setScoreItem("change_limit");
    	        					if(obj instanceof Map) {
    	        						Map<String, String> mapPar = (Map<String, String>) obj;
    	        						String str = mapPar.get("in_value1") + "_" + mapPar.get("in_value2") + "_" + mapPar.get("in_value3") + "_" + mapPar.get("in_value4");
    	        						jobScore = (SJobScore) jobScore.clone();
    									jobScore.setItemValue(str);
    									scoreList.add(jobScore);
    	        					}else if(obj instanceof List) {
    	        						List<Map<String, String>> list = (List<Map<String, String>>) obj;
    	        						int i = 1;
    	        						for(Map<String, String> mapPar : list) {
    	        							String str = mapPar.get("in_value1") + "_" + mapPar.get("in_value2") + "_" + mapPar.get("in_value3") + "_" + mapPar.get("in_value4");
        	        						jobScore = (SJobScore) jobScore.clone();
        	        						jobScore.getSjobscoreId().setScoreItem("change_limit"+i++);
        									jobScore.setItemValue(str);
        									scoreList.add(jobScore);
    	        						}
    	        					}
    	        				}
    	        			}
    	        		}else if(map.get("is_assign_shift_date") != null) {
    	        			int idx=1;
    	        			for(Map.Entry<String, Object> entry : map.entrySet()) {
    	        				if(!"is_assign_shift_date".equals(entry.getKey())) {
	    	        				String[] keys = entry.getKey().split("\\|");
	    	        				
	    	        				for(int i=2; i<=3; i++) {
	    	        					String scoreItem = "";
	    	        					if(i==2)
	    	        						scoreItem = "job_id";
	    	        					else if(i==3)
	    	        						scoreItem = "assign_shift_date";
	    	        					jobScore = (SJobScore) jobScore.clone();
		    	        				jobScore.getSjobscoreId().setSite(keys[0]);
    	        						jobScore.getSjobscoreId().setScoreItem(scoreItem);
										jobScore.setItemValue(keys[i]);
										jobScore.getSjobscoreId().setItemSeq(""+idx);
										scoreList.add(jobScore);
										
	    	        				}
    	        				}
    	        				idx++;
    	        			}
    	        		}
    	        	}else if(item instanceof List) {
    	        		logger.debug("instanceof List, constraintName: " + constraintName + " List=> " + item);
    	        	}else {
    	        		logger.debug("not belongs to any type, constraintName: " + constraintName + " item: " + item);
    	        	}
    	        	logger.debug("Justified by {}", item);
                }
    	    }
		}
		return scoreList;
	}
}
