package com.lcm.service;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.lcm.domain.*;
import com.lcm.domain.QLotOpsu;
import com.lcm.domain.dto.*;
import com.lcm.repository.*;
import com.lcm.service.dto.TJobStatisticsDTO;
import com.lcm.util.DateUtils;
import com.lcm.util.TableUtils;
import com.lcm.util.UserUtils;
import com.lcm.web.rest.errors.ExcelDataException;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class AdjustmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdjustmentService.class);
    private static final List<String> actualQtyList = new ArrayList<>();//实际数量
    private static StringBuffer bf = null;
    private final ParParameterRepository parParameterRepository;
    private final EqpCapaRepository eqpCapaRepository;

    private final AdjustmentRepository adjustmentRepository;
    private final AdjustmentInitRepository adjustmentInitRepository;
    private final ModModelRepository modModelRepository;
    private final RJobDashboardRepository rJobDashboardRepository;
    private final LineRepository lineRepository;
    private final LotOpsuRepository lotOpsuRepository;

    public AdjustmentService(AdjustmentRepository adjustmentRepository, ModModelRepository modModelRepository,
                             RJobDashboardRepository rJobDashboardRepository, ParParameterRepository parParameterRepository,
                             EqpCapaRepository eqpCapaRepository, LineRepository lineRepository,
                             AdjustmentInitRepository adjustmentInitRepository,
                             LotOpsuRepository lotOpsuRepository) {
        this.adjustmentRepository = adjustmentRepository;
        this.modModelRepository = modModelRepository;
        this.rJobDashboardRepository = rJobDashboardRepository;
        this.parParameterRepository = parParameterRepository;
        this.eqpCapaRepository = eqpCapaRepository;
        this.lineRepository = lineRepository;
        this.adjustmentInitRepository = adjustmentInitRepository;
        this.lotOpsuRepository = lotOpsuRepository;
    }

    @Transactional
    public void parseExcel(String site, MultipartFile file) throws Exception {
        Map<String, String> areaMap = new HashMap<>();
        lineRepository.findBySite(site).forEach(line -> {
            String key = line.getSite() + "-" + line.getFab() + "-" + line.getLine();
            if (areaMap.get(key) == null) {
                areaMap.put(key, line.getArea());
            }
        });

        try (InputStream inputStream = file.getInputStream()) {
            List<Object> rows = EasyExcelFactory.read(inputStream, new Sheet(1, 0));
            List<Object> header1 = (List<Object>) rows.get(0);
            List<Object> header2 = (List<Object>) rows.get(1);
            int days = (header2.size() - 2) / 12;
            List<LocalDate> headerShiftDates = new ArrayList<>(days);
            for (int i = 0; i < days; i++) {
                Date date = HSSFDateUtil.getJavaDate(Double.parseDouble((String) header1.get(12 * i + 2)));
                LocalDate shiftDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                headerShiftDates.add(shiftDate);
            }
            List<Adjustment> list = new ArrayList<>();
            String fab = null;
            String area = null;
            String line = null;
            Map<String, Integer> lineIndexMap = new HashMap<>();
            String employeeId = UserUtils.currentUser().getEmployeeId();
            Set<String> fabs = new HashSet<>();
            Set<String> areas = new HashSet<>();
            for (int k = 3; k < rows.size(); k++) {
                List<Object> cols = (List<Object>) rows.get(k);
                int colCount = cols.size();
                fab = cols.get(0) == null ? fab : (String) cols.get(0);
                fabs.add(fab);
                line = cols.get(1) == null ? line : (String) cols.get(1);
                area = areaMap.get(site + "-" + fab + "-" + line);
                areas.add(area);

                if (lineIndexMap.get(line) == null) {
                    lineIndexMap.put(line, k);
                }
                for (int i = 0; i < days; i++) {
                    if (colCount <= i * 12 + 2) {//是否越界
                        break;
                    }
                    for (int j = 0; j < 2; j++) {
                        int space = j * 6;
                        if (colCount <= i * 12 + 2 + space) {//是否越界
                            break;
                        }
                        String model = (String) cols.get(2 + space + i * 12);
                        if (model != null && !model.equals("-")) {
                            String partNo = (String) cols.get(3 + space + i * 12);
                            String partNo1 = TableUtils.getPartNo(partNo);
                            String grade1 = TableUtils.getGrade(partNo);
                            String wd = (String) cols.get(4 + space + i * 12);
                            String woId = wd == null ? "-" : wd;
                            String rek = (String) cols.get(5 + space + i * 12);
                            String remark = rek == null ? "-" : rek;
                            String seq = String.valueOf(k - lineIndexMap.get(line) + 1 + 1000 * j);
                            if (model.contains("PM") || model.contains("NON-SCHEDULE")) {
                                Adjustment adjustment = new Adjustment();
                                AdjustmentId adjustmentId = new AdjustmentId();
                                adjustmentId.setSite(site);
                                adjustmentId.setFab(fab);
                                adjustmentId.setArea(area);
                                adjustmentId.setLine(line);
                                adjustmentId.setShift((String) header2.get(2 + space + i * 12));
                                adjustmentId.setShiftDate(headerShiftDates.get(i));
                                adjustmentId.setPartLevel("0");
                                adjustmentId.setModelNo("-");
                                adjustmentId.setWoId(woId);
                                adjustmentId.setChangeKey("-");
                                adjustment.setPlanQty("0");
                                adjustment.setLmUser(employeeId);
                                adjustment.setLmTime(LocalDateTime.now());
                                adjustment.setAdjustmentId(adjustmentId);
                                //model PM:12
                                String[] modelArr = model.split(":");
                                adjustment.setDuration(modelArr[1]);
                                adjustmentId.setJobType(modelArr[0]);
                                adjustment.setPartNo(partNo1);

                                adjustmentId.setGrade(grade1);
                                adjustment.setRunSeq(seq);
                                adjustment.setRemark(remark);
                                list.add(adjustment);
                            } else if (model.contains("CHANGE")) {
                                Adjustment adjustment = new Adjustment();
                                AdjustmentId adjustmentId = new AdjustmentId();
                                adjustmentId.setSite(site);
                                adjustmentId.setFab(fab);
                                adjustmentId.setArea(area);
                                adjustmentId.setLine(line);
                                adjustmentId.setShift((String) header2.get(2 + space + i * 12));
                                adjustmentId.setJobType("CHANGE");
                                adjustmentId.setShiftDate(headerShiftDates.get(i));
                                adjustmentId.setPartLevel("0");
                                ModelId modelId = new ModelId();
                                modelId.setSite(site);
                                modelId.setPartNo(partNo);
                                Optional<ModModel> m = modModelRepository.findById(modelId);
                                adjustmentId.setModelNo(m.isPresent() ? m.get().getModelNo() : "-");
                                adjustmentId.setWoId(woId);
                                adjustment.setPlanQty("0");
                                adjustment.setLmUser(employeeId);
                                adjustment.setLmTime(LocalDateTime.now());
                                adjustment.setAdjustmentId(adjustmentId);
                                //model CHANGE:S06_00011:L2:6
                                String[] str = model.split(":");
                                adjustmentId.setChangeKey(str[1]);
                                adjustment.setChangeLevel(str[2]);
                                adjustment.setDuration(str[3]);
                                adjustment.setRunSeq(seq);
                                adjustment.setPartNo(partNo1);
                                adjustmentId.setGrade(grade1);
                                adjustment.setRemark(remark);
                                list.add(adjustment);
                            } else {
                                int index = -1;
                                String qty = (String) cols.get(6 + space + i * 12);
                                if (StringUtils.isEmpty(qty)) {
                                    throw new ExcelDataException("第" + (k + 1) + "行第" + (6 + space + i * 12 + 1) + "列数量为空。");
                                }
                                if (!"0".equals(qty)) {
                                    index = 0;
                                } else {
                                    qty = (String) cols.get(7 + space + i * 12);
                                    if (StringUtils.isEmpty(qty)) {
                                        throw new ExcelDataException("第" + (k + 1) + "行第" + (7 + space + i * 12 + 1) + "列数量为空。");
                                    }
                                    if (qty != null && !"0".equals(qty)) {
                                        index = 1;
                                    }
                                }
                                if (index != -1) {
                                    Adjustment adjustment = new Adjustment();
                                    AdjustmentId adjustmentId = new AdjustmentId();
                                    adjustmentId.setSite(site);
                                    adjustmentId.setFab(fab);
                                    adjustmentId.setArea(area);
                                    adjustmentId.setLine(line);
                                    adjustmentId.setShift((String) header2.get(2 + space + i * 12));
//                                    adjustmentId.setPartLevel((String) header2.get(6 + index + space + i * 12));
                                    adjustmentId.setPartLevel(partNo1.substring(0, 2));
                                    if (model.contains("*")) {
                                        adjustmentId.setModelNo(model.substring(1));
                                        adjustmentId.setJobType("ENG");
                                    } else if (model.contains("#")) {
                                        adjustmentId.setModelNo(model.substring(1));
                                        adjustmentId.setJobType("SPL");
                                    } else if (model.contains("%")) {
                                        adjustmentId.setModelNo(model.substring(1));
                                        adjustmentId.setJobType("REWORK");
                                    } else {
                                        adjustmentId.setModelNo(model);
                                        adjustmentId.setJobType("PROD");
                                    }
                                    adjustmentId.setChangeKey("-");
                                    adjustmentId.setShiftDate(headerShiftDates.get(i));
                                    adjustmentId.setWoId(woId);
                                    adjustment.setPlanQty(qty);
                                    adjustment.setLmUser(employeeId);
                                    adjustment.setLmTime(LocalDateTime.now());
                                    adjustment.setAdjustmentId(adjustmentId);
                                    adjustment.setPartNo(partNo1);
                                    adjustmentId.setGrade(grade1);
                                    adjustment.setRunSeq(seq);
                                    adjustment.setRemark(remark);
                                    list.add(adjustment);
                                }
                            }
                        }
                    }
                }
            }
            if (fabs.size() == 0) {
                adjustmentRepository.deleteByShiftDatesAndSite(headerShiftDates, site);
            } else {
                adjustmentRepository.deleteByShiftDatesAndSiteAndFabs(headerShiftDates, site, fabs, areas);
            }
            //更新R表
            this.getDashboard(list);
            //更新起始时间
            this.getStartAndEndTime(list, site, fabs, lineIndexMap, headerShiftDates);
            //master table
            adjustmentRepository.saveAll(list);
            //init table
            this.getInit(list, headerShiftDates, site, fabs, areas);


        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void getDashboard(List<Adjustment> list) {
        list.forEach(adjustment -> {

                    // site,fab,line,shift_date,shift,job_type,model_No,wo_id,part_no,grade,remark
                    if ("-".equals(adjustment.getAdjustmentId().getModelNo()) && "-".equals(adjustment.getPartNo())) {
                        rJobDashboardRepository.updateByKeys1(adjustment.getAdjustmentId().getSite(), adjustment.getAdjustmentId().getFab(),
                                adjustment.getAdjustmentId().getLine(), adjustment.getAdjustmentId().getShiftDate(),
                                adjustment.getAdjustmentId().getShift(), adjustment.getAdjustmentId().getJobType(),
                                adjustment.getAdjustmentId().getWoId(),
                                adjustment.getAdjustmentId().getGrade(), adjustment.getRemark());
                        if ("-".equals(adjustment.getAdjustmentId().getGrade())) {
                            rJobDashboardRepository.updateByKeys11(adjustment.getAdjustmentId().getSite(), adjustment.getAdjustmentId().getFab(),
                                    adjustment.getAdjustmentId().getLine(), adjustment.getAdjustmentId().getShiftDate(),
                                    adjustment.getAdjustmentId().getShift(), adjustment.getAdjustmentId().getJobType(),
                                    adjustment.getAdjustmentId().getWoId()
                                    , adjustment.getRemark());
                        }
                    } else {
                        rJobDashboardRepository.updateByKeys2(adjustment.getAdjustmentId().getSite(), adjustment.getAdjustmentId().getFab(),
                                adjustment.getAdjustmentId().getLine(), adjustment.getAdjustmentId().getShiftDate(),
                                adjustment.getAdjustmentId().getShift(), adjustment.getAdjustmentId().getJobType(),
                                adjustment.getAdjustmentId().getModelNo(),
                                adjustment.getAdjustmentId().getWoId(),
                                adjustment.getPartNo(),
                                adjustment.getAdjustmentId().getGrade(), adjustment.getRemark());
                        if ("-".equals(adjustment.getAdjustmentId().getGrade())) {
                            rJobDashboardRepository.updateByKeys22(adjustment.getAdjustmentId().getSite(), adjustment.getAdjustmentId().getFab(),
                                    adjustment.getAdjustmentId().getLine(), adjustment.getAdjustmentId().getShiftDate(),
                                    adjustment.getAdjustmentId().getShift(), adjustment.getAdjustmentId().getJobType(),
                                    adjustment.getAdjustmentId().getModelNo(),
                                    adjustment.getAdjustmentId().getWoId(),
                                    adjustment.getPartNo(), adjustment.getRemark());
                        }

                    }


                }
        );
    }

    public void getStartAndEndTime(List<Adjustment> list, String site, Set<String> fabs, Map<String, Integer> lineIndexMap,
                                   List<LocalDate> headerShiftDates) {
        list.sort(Comparator.comparing(a -> a.getAdjustmentId().getSite() + a.getAdjustmentId().getFab()
                + a.getAdjustmentId().getShiftDate() + a.getAdjustmentId().getShift()
                + a.getAdjustmentId().getLine() + a.getRunSeq()
        ));//排序order by site,fab,shift_date,shift,line,run_seq
        List<EqpCapa> eqpCapaList = eqpCapaRepository.findByEqpCapaIdSiteAndFabInAndEqpCapaIdLineIn(site, fabs, lineIndexMap.keySet());
        Map<String, EqpCapa> capaMap = new HashMap<>(eqpCapaList.size());
        bf = new StringBuffer();
        eqpCapaList.forEach(e -> {
            bf.append(e.getEqpCapaId().getSite()).append("-")
                    .append(e.getFab()).append("-")
                    .append(e.getEqpCapaId().getLine()).append("-")
                    .append(e.getModelNo()).append("-")
                    .append(e.getEqpCapaId().getPartNo());
            capaMap.put(bf.toString(), e);
            bf.setLength(0);
        });
        String bySiteAndItemNameD = parParameterRepository.findBySiteAndItemName(site, TableUtils.DSTART);
        String bySiteAndItemNameE = parParameterRepository.findBySiteAndItemName(site, TableUtils.ESTART);

        int byD = Integer.parseInt(bySiteAndItemNameD);
        int byE = Integer.parseInt(bySiteAndItemNameE);


        for (String fa : fabs) {
            for (int i = 0; i < headerShiftDates.size(); i++) {
                LocalDate localDate = headerShiftDates.get(i);
                for (int j = 0; j < 2; j++) {
                    String[] shifts = TableUtils.SHIFTS;
                    String shift = shifts[j];
                    for (String li : lineIndexMap.keySet()) {
                        List<Adjustment> collect = list.stream().filter(adj -> site.equals(adj.getAdjustmentId().getSite())
                                && fa.equals(adj.getAdjustmentId().getFab())
                                && localDate.isEqual(adj.getAdjustmentId().getShiftDate())
                                && shift.equals(adj.getAdjustmentId().getShift())
                                && li.equals(adj.getAdjustmentId().getLine())).collect(Collectors.toList());

                        if (collect.size() > 0) {
                            //                        String area = areaMap.get(site + "-" + fa + "-" + li);
//                        ParParameter bySiteAndItemNameD = parParameterRepository.findBySiteAndFabAndAreaAndItemName(site,fa,area,TableUtils.DSTART);
//                        ParParameter bySiteAndItemNameE = parParameterRepository.findBySiteAndFabAndAreaAndItemName(site,fa,area,TableUtils.ESTART);
//                        int byD = Integer.parseInt(bySiteAndItemNameD.getInValue1());
//                        int byE = Integer.parseInt(bySiteAndItemNameE.getInValue1());

                            for (int c = 0; c < collect.size(); c++) {
                                Adjustment adjustment = collect.get(c);
                                String jobType = adjustment.getAdjustmentId().getJobType();
                                if (c == 0) {
                                    LocalDateTime bbb = localDate.atStartOfDay();
                                    if (TableUtils.DSHIFT.equals(shift)) {
                                        adjustment.setProcessStartTime(bbb.plusSeconds(byD * 3600));
                                    } else if (TableUtils.NSHIFT.equals(shift)) {
                                        adjustment.setProcessStartTime(bbb.plusSeconds(byE * 3600));
                                    }
                                }

                                if ("CHANGE".equals(jobType) || "PM".equals(jobType) || "NON-SCHEDULE".equals(jobType)) {
                                    if (0 == c) {//从参数取初始时间
                                        double lo = Double.parseDouble(adjustment.getDuration()) * 3600;
                                        adjustment.setProcessEndTime(adjustment.getProcessStartTime().plusSeconds((long) lo));//直接取小时
                                    } else { //开始时间为get(c - 1)对象的结束时间;
                                        LocalDateTime processStartTime = collect.get(c - 1).getProcessEndTime();
                                        adjustment.setProcessStartTime(processStartTime);
                                        double lo = Double.parseDouble(adjustment.getDuration()) * 3600;
                                        adjustment.setProcessEndTime(processStartTime.plusSeconds((long) lo));
                                    }
                                } else {
                                    if (0 == c) {//从参数取初始时间
                                        adjustment.setProcessEndTime(getProcessEndTime(site, fa, li, adjustment, adjustment.getProcessStartTime(), capaMap));
                                    } else { //开始时间为get(c - 1)对象的结束时间;
                                        LocalDateTime processStartTime = collect.get(c - 1).getProcessEndTime();
                                        adjustment.setProcessStartTime(processStartTime);
                                        adjustment.setProcessEndTime(getProcessEndTime(site, fa, li, adjustment, processStartTime, capaMap));
                                    }

                                }
                            }

                        }

                    }
                }

            }
        }
    }

    public void getInit(List<Adjustment> list, List<LocalDate> headerShiftDates, String site, Set<String> fabs, Set<String> areas) {
        List<AdjustmentInit> adjustmentInitList = new ArrayList<>();
        list.forEach(obj -> {
            AdjustmentInit adjustmentInit = new AdjustmentInit();
            AdjustmentInitId adjustmentInitId = new AdjustmentInitId();
            adjustmentInitId.setSite(obj.getAdjustmentId().getSite());
            adjustmentInitId.setFab(obj.getAdjustmentId().getFab());
            adjustmentInitId.setArea(obj.getAdjustmentId().getArea());
            adjustmentInitId.setLine(obj.getAdjustmentId().getLine());
            adjustmentInitId.setShift(obj.getAdjustmentId().getShift());
            adjustmentInitId.setShiftDate(obj.getAdjustmentId().getShiftDate());
            adjustmentInitId.setJobType(obj.getAdjustmentId().getJobType());
            adjustmentInitId.setPartLevel(obj.getAdjustmentId().getPartLevel());
            adjustmentInitId.setModelNo(obj.getAdjustmentId().getModelNo());
            adjustmentInitId.setWoId(obj.getAdjustmentId().getWoId());
            adjustmentInitId.setChangeKey(obj.getAdjustmentId().getChangeKey());
            adjustmentInitId.setGrade(obj.getAdjustmentId().getGrade());
            adjustmentInit.setAdjustmentInitId(adjustmentInitId);
            adjustmentInit.setPlanQty(Integer.parseInt(obj.getPlanQty()));
            adjustmentInit.setLmUser(obj.getLmUser());
            adjustmentInit.setLmTime(obj.getLmTime());
            adjustmentInit.setRunSeq(obj.getRunSeq());
            adjustmentInit.setPartNo(obj.getPartNo());
            adjustmentInit.setChangeLevel(obj.getChangeLevel());
            adjustmentInit.setDuration(obj.getDuration());
            adjustmentInit.setRemark(obj.getRemark());
            adjustmentInit.setProcessStartTime(obj.getProcessStartTime());
            adjustmentInit.setProcessEndTime(obj.getProcessEndTime());
            adjustmentInitList.add(adjustmentInit);
        });
        List<AdjustmentInit> adjustmentInitListSave = new ArrayList<>();
        for (LocalDate localDate : headerShiftDates) {
            for (String fab : fabs) {
                for (String area : areas) {
                    List<AdjustmentInit> bySiteAndFabAndAreaAndShiftDate = adjustmentInitRepository.findByAdjustmentInitIdSiteAndAdjustmentInitIdFabAndAdjustmentInitIdAreaAndAdjustmentInitIdShiftDate(site, fab, area, localDate);
                    boolean flag = bySiteAndFabAndAreaAndShiftDate.isEmpty() ? true : false;
                    if (flag) {
                        List<AdjustmentInit> collect = adjustmentInitList.stream().filter(init -> init.getAdjustmentInitId().getSite().equals(site)
                                && init.getAdjustmentInitId().getFab().equals(fab)
                                && init.getAdjustmentInitId().getArea().equals(area)
                                && init.getAdjustmentInitId().getShiftDate().isEqual(localDate)).collect(Collectors.toList());
                        adjustmentInitListSave.addAll(collect);
                    }

                }
            }
        }
        adjustmentInitRepository.saveAll(adjustmentInitListSave);
    }

    public JobDto getData(String site, String fab, String area, String startTime, String endTime, boolean showWoId, LinkedList<String> linkedList, Map<String, String> aliasmap) {
        LocalDate startDate = LocalDate.parse(startTime, DateUtils.dateFormatter1);
        LocalDate endDate = LocalDate.parse(endTime, DateUtils.dateFormatter1);
        List<JobDashboard> tList = new ArrayList<>();
        if (showWoId) {
            List<Adjustment> aList;
            if (!StringUtils.isEmpty(fab) && !StringUtils.isEmpty(area)) {
                aList = adjustmentRepository.findAllByAdjustmentIdSiteAndAdjustmentIdFabAndAdjustmentIdAreaAndAdjustmentIdShiftDateBetween(site, fab, area, startDate, endDate);
            } else if (!StringUtils.isEmpty(fab) && StringUtils.isEmpty(area)) {
                aList = adjustmentRepository.findAllByAdjustmentIdSiteAndAdjustmentIdFabAndAdjustmentIdShiftDateBetween(site, fab, startDate, endDate);
            } else {
                aList = adjustmentRepository.findAllByAdjustmentIdSiteAndAdjustmentIdShiftDateBetween(site, startDate, endDate);
            }
            for (int i = 0; i < aList.size(); i++) {
                JobDashboard t = new JobDashboard();
                t.setJobId("a" + i);
                Adjustment adjustment = aList.get(i);
                AdjustmentId id = adjustment.getAdjustmentId();
                //adjustment.（site、fab、area、line）作为键值去遍历aliasmap，若value不为空，则将“（value）”附在id.getLine（）后面
                String adjustKey = adjustment.getAdjustmentId().getSite() + TableUtils.FLAG + adjustment.getAdjustmentId().getFab() + TableUtils.FLAG + adjustment.getAdjustmentId().getArea() + TableUtils.FLAG + adjustment.getAdjustmentId().getLine();
                String str = aliasmap.get(adjustKey);
                if (!StringUtils.isEmpty(str)) {
                    t.setLine(id.getLine() + "(" + str + ")");
                } else {
                    t.setLine(id.getLine());
                }
                t.setFab(id.getFab());
                String grade = adjustment.getAdjustmentId().getGrade();
                t.setPartNo(TableUtils.handlePartNo(adjustment.getPartNo(), grade));
                t.setRemark(adjustment.getRemark());
                t.setForecastQty(Integer.parseInt(adjustment.getPlanQty()));
                t.setShiftDate(id.getShiftDate());
                t.setShift(id.getShift());
                String jobType = id.getJobType();
                t.setJobType(jobType);
                t.setModelNo(id.getModelNo());
                t.setWoId(id.getWoId());
                t.setSeq(adjustment.getRunSeq());
                if (showWoId) {
                    if ("PM".equals(jobType) || "NON-SCHEDULE".equals(jobType)) {
                        t.setChangeDuration(adjustment.getDuration());
                    } else if ("CHANGE".equals(jobType)) {
                        t.setChangeLevel(adjustment.getChangeLevel());
                        t.setChangeDuration(adjustment.getDuration());
                        t.setChangeKey(id.getChangeKey());
                    }
                }
                tList.add(t);
            }
        } else {
            List<Object> aList = null;
            if (!StringUtils.isEmpty(fab) && !StringUtils.isEmpty(area)) {
                aList = adjustmentRepository.findBySiteAndFabAndShiftDate(site, fab, area, startDate, endDate);
                aList.addAll(adjustmentRepository.findBySiteAndFabAndShiftDateForPm(site, fab, area, startDate, endDate));
                aList.addAll(adjustmentRepository.findBySiteAndFabAndShiftDateForNonSchedule(site, area, fab, startDate, endDate));
                aList.addAll(adjustmentRepository.findBySiteAndFabAndShiftDateForChange(site, fab, area, startDate, endDate));

            } else if (!StringUtils.isEmpty(fab) && StringUtils.isEmpty(area)) {
                aList = adjustmentRepository.findBySiteAndFabAndShiftDate(site, fab, startDate, endDate);
                aList.addAll(adjustmentRepository.findBySiteAndFabAndShiftDateForPm(site, fab, startDate, endDate));
                aList.addAll(adjustmentRepository.findBySiteAndFabAndShiftDateForNonSchedule(site, fab, startDate, endDate));
                aList.addAll(adjustmentRepository.findBySiteAndFabAndShiftDateForChange(site, fab, startDate, endDate));

            } else {
                aList = adjustmentRepository.findBySiteAndShiftDate(site, startDate, endDate);
                aList.addAll(adjustmentRepository.findBySiteAndShiftDateForPm(site, startDate, endDate));
                aList.addAll(adjustmentRepository.findBySiteAndShiftDateForNonSchedule(site, startDate, endDate));
                aList.addAll(adjustmentRepository.findBySiteAndShiftDateForChange(site, startDate, endDate));
            }
            for (int i = 0; i < aList.size(); i++) {
                JobDashboard t = new JobDashboard();
                t.setJobId("a" + i);
                Object[] a = (Object[]) aList.get(i);
                String adjustKey = site + TableUtils.FLAG + a[7] + TableUtils.FLAG + a[8] + TableUtils.FLAG + a[0];
                String str = aliasmap.get(adjustKey);
                System.out.println(str);
                if (!StringUtils.isEmpty(str)) {
                    t.setLine(a[0] + "(" + str + ")");
                } else {
                    t.setLine((String) a[0]);
                }
                t.setShiftDate((LocalDate) a[1]);
                t.setShift((String) a[2]);
                String jobType = (String) a[3];
                t.setJobType(jobType);
                t.setModelNo((String) a[4]);
                String partNo = (String) a[5];
                t.setForecastQty(Integer.parseInt((String) a[6]));
                t.setFab((String) a[7]);
                if ("PM".equals(jobType) || "NON-SCHEDULE".equals(jobType)) {
                    String grade = (String) a[11];
                    t.setPartNo(TableUtils.handlePartNo(partNo, grade));
                    t.setRemark((String) a[12]);

                    t.setChangeDuration((String) a[9]);
                    t.setSeq((String) a[10]);
                } else if ("CHANGE".equals(jobType)) {
                    String grade = (String) a[13];
                    t.setPartNo(TableUtils.handlePartNo(partNo, grade));
                    t.setRemark((String) a[14]);

                    t.setChangeLevel((String) a[9]);
                    t.setChangeDuration((String) a[10]);
                    t.setChangeKey((String) a[11]);
                    t.setSeq((String) a[12]);
                } else {
                    String grade = (String) a[10];
                    t.setPartNo(TableUtils.handlePartNo(partNo, grade));
                    t.setRemark((String) a[11]);

                    t.setSeq((String) a[9]);
                }
                tList.add(t);
            }
        }
        return getListTable(showWoId, tList, linkedList);
    }

    public JobDto getListTable(boolean showWoId, List<JobDashboard> tList, LinkedList<String> linkedList) {
        Map<String, TJobStatisticsDTO> fabLineMap = TableUtils.handleTJobList(tList);
        for (String existsLine : fabLineMap.keySet()) {

            if (existsLine.indexOf("(") != -1) {

                String substring = existsLine.substring(0, existsLine.indexOf("("));

                linkedList.remove(substring);
            } else {
                linkedList.remove(existsLine);
            }
        }
        int sum = 0;
        List<JobTableDto> tableDtoList = new ArrayList<>();
        List<NoScheduleDto> noScheduleTableDtoList = new ArrayList<>();
        List<LocalDate> shiftDates = tList.stream().map(t -> t.getShiftDate()).distinct().sorted().collect(Collectors.toList()); //获取区间查询日期
        LOGGER.info("一共查询：" + shiftDates.size() + "天。");
        int dCount;
        int nCount;
        List<List<JobDashboardDto>> allListDto = new ArrayList<>();//最终返回的结果集

        for (int h = 0; h < shiftDates.size(); h++) {
            LocalDate shiftDate = shiftDates.get(h);
            List<JobDashboardDto> listDto = new ArrayList<>();

            for (String key : fabLineMap.keySet()) {
                TJobStatisticsDTO statisticsDTO = fabLineMap.get(key);
                int count = statisticsDTO.getSum();
                String[] fabLineArr = key.split(TableUtils.FLAG);
                String fab = fabLineArr[0];
                String line = fabLineArr[1];
                Map<String, List<JobDashboard>> data = statisticsDTO.getData();
                List<JobDashboard> dLineList = data.get(shiftDate + TableUtils.FLAG + TableUtils.DSHIFT);//白班生产
                List<JobDashboard> nLineList = data.get(shiftDate + TableUtils.FLAG + TableUtils.NSHIFT);//晚班生产
                if (dLineList == null) {
                    dLineList = new ArrayList<>();
                } else {
                    if (showWoId) {
                        dLineList.sort(Comparator.comparing(JobDashboard::getSeq));
                    } else {
                        dLineList.sort(Comparator.comparing(JobDashboard::getPartNo));
                    }
                }
                if (nLineList == null) {
                    nLineList = new ArrayList<>();
                } else {
                    if (showWoId) {
                        nLineList.sort(Comparator.comparing(JobDashboard::getSeq));
                    } else {
                        nLineList.sort(Comparator.comparing(JobDashboard::getPartNo));
                    }
                }
                dCount = dLineList.size();
                nCount = nLineList.size();
                if (dCount < count || nCount < count) {
                    for (int i = 0; i < count - dCount; i++) {
                        JobDashboard obj = new JobDashboard();
                        obj.setFab(fab);
                        obj.setLine(line);
                        obj.setModelNo("-");
                        obj.setPartNo("-");
                        obj.setWoId("-");
                        obj.setRemark("-");
                        dLineList.add(obj);
                    }
                    for (int i = 0; i < count - nCount; i++) {
                        JobDashboard obj = new JobDashboard();
                        obj.setFab(fab);
                        obj.setLine(line);
                        obj.setModelNo("-");
                        obj.setPartNo("-");
                        obj.setWoId("-");
                        obj.setRemark("-");
                        nLineList.add(obj);
                    }
                }

                //合并白晚班数据
                for (int i = 0; i < count; i++) {
                    JobDashboardDto dto = new JobDashboardDto();
                    dto.setFab(fab);
                    dto.setLine(line);
                    JobDashboard dObj = dLineList.get(i);
                    String dJobType = dObj.getJobType();
                    String dModelNo = dObj.getModelNo();
                    if ("ENG".equals(dJobType)) {
                        dto.setModelNo("*" + dModelNo);
                    } else if ("SPL".equals(dJobType)) {
                        dto.setModelNo("#" + dModelNo);
                    } else if ("PM".equals(dJobType)) {
                        dto.setModelNo("PM:" + dObj.getChangeDuration());
                    } else if ("NON-SCHEDULE".equals(dJobType)) {
                        dto.setModelNo("NON-SCHEDULE:" + dObj.getChangeDuration());
                    } else if ("CHANGE".equals(dJobType)) {
//                        dto.setModelNo("CHANGE" + dObj.getChangeKey() + ":" +dObj.getChangeLevel() + ":" + dObj.getChangeDuration());
                        dto.setModelNo("CHANGE:" + dObj.getChangeLevel() + ":" + dObj.getChangeDuration());
                    } else if ("REWORK".equals(dJobType)) {
                        dto.setModelNo("%" + dModelNo);
                    } else {
                        dto.setModelNo(dModelNo);
                    }
                    dto.setSeq(dObj.getSeq());

                    dto.setPartNo(dObj.getPartNo());
                    dto.setWoId(dObj.getWoId());
                    dto.setRemark(dObj.getRemark());
                    dto.setForecastQty(dObj.getForecastQty());
                    dto.setJobType(dObj.getJobType());
                    dto.setChangeDuration(dObj.getChangeDuration());
                    JobDashboard nObj = nLineList.get(i);
                    String nJobType = nObj.getJobType();
                    String nModelNo = nObj.getModelNo();
                    if ("ENG".equals(nJobType)) {
                        dto.setNmodelNo("*" + nModelNo);
                    } else if ("SPL".equals(nJobType)) {
                        dto.setNmodelNo("#" + nModelNo);
                    } else if ("PM".equals(nJobType)) {
                        dto.setNmodelNo("PM:" + nObj.getChangeDuration());
                    } else if ("NON-SCHEDULE".equals(nJobType)) {
                        dto.setNmodelNo("NON-SCHEDULE:" + nObj.getChangeDuration());
                    } else if ("CHANGE".equals(nJobType)) {
//                        dto.setNmodelNo("CHANGE:" + nObj.getChangeKey() + ":" + nObj.getChangeLevel() + ":" + nObj.getChangeDuration());
                        dto.setNmodelNo("CHANGE:" + nObj.getChangeLevel() + ":" + nObj.getChangeDuration());
                    } else if ("REWORK".equals(nJobType)) {
                        dto.setNmodelNo("%" + nModelNo);
                    } else {
                        dto.setNmodelNo(nModelNo);
                    }
                    dto.setNseq(nObj.getSeq());

                    dto.setNpartNo(nObj.getPartNo());
                    dto.setNwoId(nObj.getWoId());
                    dto.setNremark(nObj.getRemark());
                    dto.setNforecastQty(nObj.getForecastQty());
                    dto.setNjobType(nObj.getJobType());
                    dto.setNchangeDuration(nObj.getChangeDuration());
                    listDto.add(dto);
                }
            }
            if (h == 0) {
                sum = listDto.size();
            }
            allListDto.add(listDto);//所有天数的数据
        }


        //获取表格数据
        for (int i = 0; i < sum; i++) {//循环表格多少行
            JobTableDto tableDto = new JobTableDto();
            for (int j = 0; j < allListDto.size(); j++) {//这个循环区间天数
                List<JobDashboardDto> listDto = allListDto.get(j);
                JobDashboardDto dto = listDto.get(i);
                getTableData(tableDto, dto, j);
            }
            tableDtoList.add(tableDto);
        }

        for (String linked : linkedList) {
            NoScheduleDto noScheduleDto = new NoScheduleDto();
            String[] split = linked.split(TableUtils.FLAG);
            noScheduleDto.setFab(split[0]);
            noScheduleDto.setLine(split[1]);
            noScheduleTableDtoList.add(noScheduleDto);
        }

        //获取列数据
        List<String> columnList = new ArrayList<>();//所有属性，所有列
        Class clz = JobTableDto.class;
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            columnList.add(field.getName());
        }
        int a = 2;
        int b = 22;
        List<ColumnDto> columnDtoList = new ArrayList<>(); //表格所需列
        Map<String, Integer> shiftDateShiftPartNoCountMap = TableUtils.getShiftDateShiftPartNoCountMap(tList);
        for (int i = 0; i < shiftDates.size(); i++) {//查了几天循环几次//统计91、97、白晚班
            LocalDate shiftDate = shiftDates.get(i);
            List<String> list = columnList.subList(a, b);

            ColumnDto columnDto = new ColumnDto();
            columnDto.setShiftDate(shiftDate);
            columnDto = TableUtils.get91and97and90(columnDto, shiftDateShiftPartNoCountMap, shiftDate);
            columnDto.setJobType(list.get(0));
            columnDto.setPartNo(list.get(1));
            columnDto.setModelNo(list.get(2));
            columnDto.setWoId(list.get(3));
            columnDto.setForecastQty91(list.get(5));
            columnDto.setForecastQty97(list.get(6));
            columnDto.setChangeDuration(list.get(7));
            columnDto.setPartNoFull(list.get(8));
            columnDto.setRemark(list.get(9));
            columnDto.setNjobType(list.get(10));
            columnDto.setNpartNo(list.get(11));
            columnDto.setNmodelNo(list.get(12));
            columnDto.setNwoId(list.get(13));
            columnDto.setNforecastQty91(list.get(15));
            columnDto.setNforecastQty97(list.get(16));
            columnDto.setNchangeDuration(list.get(17));
            columnDto.setNpartNoFull(list.get(18));
            columnDto.setNremark(list.get(19));
            columnDtoList.add(columnDto);
            a += 20;
            b += 20;
        }
        JobDto job = new JobDto();
        job.setTableDtoList(tableDtoList);
        job.setColumnDtoList(columnDtoList);
        job.setNoScheduleTableDtoList(noScheduleTableDtoList);
        return job;
    }

    //获得结束时间
    public LocalDateTime getProcessEndTime(String site, String fa, String li, Adjustment adjustment, LocalDateTime processStartTime, Map<String, EqpCapa> capaMap) {
        //查询capa.计算。site,fab,line,part_no,model_no
        bf.setLength(0);
        bf.append(site).append("-")
                .append(fa).append("-")
                .append(li).append("-")
                .append(adjustment.getAdjustmentId().getModelNo()).append("-")
                .append(adjustment.getPartNo());
        EqpCapa eqpCapa = capaMap.get(bf.toString());

        if (eqpCapa == null) { //C_EQP_CAPA 没值，所以无法计算起始时间
            throw new NullPointerException("请维护【产能设定】，线别：" + li + ",料号：" + adjustment.getPartNo());
        }
        int fabPcCapa = eqpCapa.getFabPcCapa();

        double v = Double.parseDouble(adjustment.getPlanQty()) * 86400;//单位时间总片数需要时间
        double i1 = fabPcCapa == 0 ? 0 : v / fabPcCapa;//单位时间每片需要时间
        return processStartTime.plusSeconds((long) i1);
    }

    public List<AdjustmentDto> getAdjustChartData(String site, String fab, String area, LocalDate startDate, LocalDate endDate) {
        QAdjustment query = QAdjustment.adjustment;
        Predicate pre = query.isNotNull();
        pre = site == null ? pre : ExpressionUtils.and(pre, query.adjustmentId.site.eq(site));
        if (!StringUtils.isEmpty(fab)) {
            pre = ExpressionUtils.and(pre, query.adjustmentId.fab.eq(fab));
        }
        if (!StringUtils.isEmpty(area)) {
            pre = ExpressionUtils.and(pre, query.adjustmentId.area.eq(area));
        }
        pre = ExpressionUtils.and(pre, query.adjustmentId.shiftDate.between(startDate, endDate));
        List<Adjustment> list = (List<Adjustment>) adjustmentRepository.findAll(pre);
        List<AdjustmentDto> listDto = new ArrayList<>();
        list.forEach(adj -> {
            AdjustmentDto adjDto = new AdjustmentDto();
            adjDto.setSite(adj.getAdjustmentId().getSite());
            adjDto.setFab(adj.getAdjustmentId().getFab());
            adjDto.setLine(adj.getAdjustmentId().getLine());
            adjDto.setShift(adj.getAdjustmentId().getShift());
            adjDto.setShiftDate(adj.getAdjustmentId().getShiftDate());
            adjDto.setJobType(adj.getAdjustmentId().getJobType());
            adjDto.setProcessStartTime(adj.getProcessStartTime());
            adjDto.setProcessEndTime(adj.getProcessEndTime());
            if ("CHANGE".equals(adj.getAdjustmentId().getJobType())) {
                adjDto.setModelNo("CHANGE");
            } else if ("PM".equals(adj.getAdjustmentId().getJobType())) {
                adjDto.setModelNo("PM");
            } else if ("NON-SCHEDULE".equals(adj.getAdjustmentId().getJobType())) {
                adjDto.setModelNo("NON-SCHEDULE");
            } else {
                adjDto.setModelNo(adj.getAdjustmentId().getModelNo());
            }
            adjDto.setPlanQty(adj.getPlanQty());
            listDto.add(adjDto);
        });
        return listDto;
    }

    public List<JobAdjustChartDto> getAdjustChart2(List<AdjustmentDto> listChart,
                                                   List<AdjustmentDto> adjustChartData2,
                                                   Map<List<String>, Integer> map) {
        List<JobAdjustChartDto> chartList = new ArrayList<>();//结果集
        Map<String, List<AdjustmentDto>> lineMap = new TreeMap<>();
        Map<String, List<AdjustmentDto>> lineMap2 = new TreeMap<>();

        listChart.forEach(t -> {
            if (lineMap.get(t.getLine()) == null) {
                List<AdjustmentDto> list = new ArrayList<>();
                list.add(t);
                lineMap.put(t.getLine(), list);
            } else {
                lineMap.get(t.getLine()).add(t);
            }
        });
        adjustChartData2.forEach(t -> {
            if (lineMap2.get(t.getLine()) == null) {
                List<AdjustmentDto> list = new ArrayList<>();
                list.add(t);
                lineMap2.put(t.getLine(), list);
            } else {
                lineMap2.get(t.getLine()).add(t);
            }
        });

        for (String line : lineMap.keySet()) {
            for (int i = 0; i < 2; i++) {
                if (i == 0) {
                    JobAdjustChartDto lineJob = new JobAdjustChartDto();
                    List<AdjustmentDto> adjustments = lineMap.get(line);
                    for (int j = 0; j < adjustments.size(); j++) {
                        AdjustmentDto adjustment = adjustments.get(j);
                        String jobType = adjustment.getJobType();
                        if ("PROD".equals(jobType)) {// #ffffff
                            adjustment.setJobType("#669999");
                        } else if ("REWORK".equals(jobType)) {
                            adjustment.setJobType("#669999");
                        } else if ("ENG".equals(jobType)) {//
                            adjustment.setJobType("#00cc44");
                        } else if ("SPL".equals(jobType)) {//
                            adjustment.setJobType("#ff8000");
                        } else if ("CHANGE".equals(jobType)) {//
                            adjustment.setJobType("#ff0000");
                        } else if ("PM".equals(jobType)) {//
                            adjustment.setJobType("#0099ff");
                        } else if ("NON-SCHEDULE".equals(jobType)) {//
                            adjustment.setJobType("#808080");
                        }
                    }
                    lineJob.setAdjustmentList(adjustments);//作为一个对象
                    lineJob.setLine(line);
                    chartList.add(lineJob);
                } else {
                    JobAdjustChartDto lineJob = new JobAdjustChartDto();
                    List<AdjustmentDto> adjustments = lineMap2.get(line);
                    Iterator<AdjustmentDto> iterator = adjustments.iterator();
                    while (iterator.hasNext()) {
                        AdjustmentDto next = iterator.next();
                        if ("PM".equals(next.getJobType()) || "NON-SCHEDULE".equals(next.getJobType())
                                || "CHANGE".equals(next.getJobType())) {
                            iterator.remove();
                        } else {//主逻辑
                            Duration time1 = Duration.between(next.getProcessStartTime(), next.getProcessEndTime());
                            Duration time2 = Duration.between(next.getProcessStartTime(), LocalDateTime.now());

                            int plan;//计划数量
                            if ((double) time1.toMinutes() > 0) {
                                if(time2.toMinutes() <= 0){
                                    plan = 0;
                                }else{
                                    plan = (int) (Long.parseLong(next.getPlanQty()) / (double) time1.toMinutes()
                                            * (double) time2.toMinutes());
                                }
                            } else {
                                throw new RuntimeException("请维护计划数据");
                            }
                            next.setPlanCount(plan);
                            actualQtyList.add(next.getLine());
                            actualQtyList.add(next.getModelNo());
                            int actualQty = map.get(actualQtyList) == null ? -1 : map.get(actualQtyList).intValue();//实际数量
                            next.setActualCount(actualQty == -1 ? 0 : actualQty);
                            if (actualQty == -1) {
                                next.setJobType("#999999");
                                next.setCount(0 - plan);
                            } else {
                                int count = actualQty - plan;
                                next.setCount(count);
                                if (count < 0) {//红色
                                    next.setJobType("#ff0000");
                                } else {//绿色
                                    next.setJobType("#009933");
                                }
                            }
                        }
                        actualQtyList.clear();
                    }
                    lineJob.setAdjustmentList(adjustments);//作为一个对象
                    lineJob.setLine(line);
                    chartList.add(lineJob);
                }
            }
        }
        return chartList;
    }

    public List<JobAdjustChartDto> getAdjustChart(List<AdjustmentDto> listChart) {
        List<JobAdjustChartDto> chartList = new ArrayList<>();//结果集
        Map<String, List<AdjustmentDto>> lineMap = new TreeMap<>();
        listChart.forEach(t -> {
            if (lineMap.get(t.getLine()) == null) {
                List<AdjustmentDto> list = new ArrayList<>();
                list.add(t);
                lineMap.put(t.getLine(), list);
            } else {
                lineMap.get(t.getLine()).add(t);
            }
        });
        for (String line : lineMap.keySet()) {
            JobAdjustChartDto lineJob = new JobAdjustChartDto();
            List<AdjustmentDto> adjustments = lineMap.get(line);
            for (int j = 0; j < adjustments.size(); j++) {
                AdjustmentDto adjustment = adjustments.get(j);
                String jobType = adjustment.getJobType();
                if ("PROD".equals(jobType)) {// #ffffff
                    adjustment.setJobType("#669999");
                } else if ("REWORK".equals(jobType)) {
                    adjustment.setJobType("#669999");
                } else if ("ENG".equals(jobType)) {//
                    adjustment.setJobType("#00cc44");
                } else if ("SPL".equals(jobType)) {//
                    adjustment.setJobType("#ff8000");
                } else if ("CHANGE".equals(jobType)) {//
                    adjustment.setJobType("#ff0000");
                } else if ("PM".equals(jobType)) {//
                    adjustment.setJobType("#0099ff");
//                            adjustment.setModelNo("PM");
                } else if ("NON-SCHEDULE".equals(jobType)) {//
                    adjustment.setJobType("#808080");
//                            adjustment.setModelNo("NON-SCHEDULE");
                }
            }
            lineJob.setAdjustmentList(adjustments);//作为一个对象
            lineJob.setLine(line);
            chartList.add(lineJob);
        }
        return chartList;
    }

    public Map<List<String>, Integer> getLotOpsuList(String site, String fab, String area) {
        QLotOpsu query = QLotOpsu.lotOpsu;
        Predicate pre = query.isNotNull();

        pre = ExpressionUtils.and(pre, query.lotOpsuId.site.eq(site));

        if (!StringUtils.isEmpty(fab)) {
            pre = ExpressionUtils.and(pre, query.lotOpsuId.fab.eq(fab));
        }

        if (!StringUtils.isEmpty(area)) {
            pre = ExpressionUtils.and(pre, query.lotOpsuId.area.eq(area));
        }
        pre = ExpressionUtils.and(pre, query.lotOpsuId.shiftDate.eq(LocalDate.now()));

        List<LotOpsu> lotOpsuList = (List<LotOpsu>) lotOpsuRepository.findAll(pre);

        Map<List<String>, Integer> map = lotOpsuList.stream().collect(Collectors.groupingBy(lot -> Arrays.asList(lot.getLotOpsuId().getLine(),
                lot.getLotOpsuId().getModelNo()), Collectors.summingInt(LotOpsu::getActualQty)));

        return map;
    }

    //adjust:Table方法
    public void getTableData(JobTableDto tableDto, JobDashboardDto dto, int j) {
        if (0 == j) {
            tableDto.setFab(dto.getFab());
            tableDto.setLine(dto.getLine());
            tableDto.setChangeDuration(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty91(dto.getForecastQty());
            } else {
                tableDto.setForecastQty97(dto.getForecastQty());
            }
            tableDto.setJobType(dto.getJobType());
            tableDto.setModelNo(dto.getModelNo());
            tableDto.setPartNo(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull(dto.getPartNo());
            tableDto.setWoId(dto.getWoId());
            tableDto.setRemark(dto.getRemark());
            tableDto.setSeq(dto.getSeq());

            tableDto.setNchangeDuration(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty91(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty97(dto.getNforecastQty());
            }
            tableDto.setNjobType(dto.getNjobType());
            tableDto.setNmodelNo(dto.getNmodelNo());
            tableDto.setNpartNo(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull(dto.getNpartNo());
            tableDto.setNwoId(dto.getNwoId());
            tableDto.setNremark(dto.getNremark());
            tableDto.setNseq(dto.getNseq());
        } else if (1 == j) {
            tableDto.setChangeDuration1(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty911(dto.getForecastQty());
            } else {
                tableDto.setForecastQty971(dto.getForecastQty());
            }
            tableDto.setJobType1(dto.getJobType());
            tableDto.setModelNo1(dto.getModelNo());
            tableDto.setPartNo1(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull1(dto.getPartNo());
            tableDto.setWoId1(dto.getWoId());
            tableDto.setRemark1(dto.getRemark());

            tableDto.setSeq1(dto.getSeq());

            tableDto.setNchangeDuration1(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty911(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty971(dto.getNforecastQty());
            }
            tableDto.setNjobType1(dto.getNjobType());
            tableDto.setNmodelNo1(dto.getNmodelNo());
            tableDto.setNpartNo1(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull1(dto.getNpartNo());
            tableDto.setNwoId1(dto.getNwoId());
            tableDto.setNremark1(dto.getNremark());

            tableDto.setNseq1(dto.getNseq());
        } else if (2 == j) {
            tableDto.setChangeDuration2(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty912(dto.getForecastQty());
            } else {
                tableDto.setForecastQty972(dto.getForecastQty());
            }
            tableDto.setJobType2(dto.getJobType());
            tableDto.setModelNo2(dto.getModelNo());
            tableDto.setPartNo2(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull2(dto.getPartNo());
            tableDto.setWoId2(dto.getWoId());
            tableDto.setRemark2(dto.getRemark());

            tableDto.setSeq2(dto.getSeq());

            tableDto.setNchangeDuration2(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty912(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty972(dto.getNforecastQty());
            }
            tableDto.setNjobType2(dto.getNjobType());
            tableDto.setNmodelNo2(dto.getNmodelNo());
            tableDto.setNpartNo2(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull2(dto.getNpartNo());
            tableDto.setNwoId2(dto.getNwoId());
            tableDto.setNremark2(dto.getNremark());

            tableDto.setNseq2(dto.getNseq());
        } else if (3 == j) {
            tableDto.setChangeDuration3(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty913(dto.getForecastQty());
            } else {
                tableDto.setForecastQty973(dto.getForecastQty());
            }
            tableDto.setJobType3(dto.getJobType());
            tableDto.setModelNo3(dto.getModelNo());
            tableDto.setPartNo3(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull3(dto.getPartNo());
            tableDto.setWoId3(dto.getWoId());
            tableDto.setRemark3(dto.getRemark());

            tableDto.setSeq3(dto.getSeq());

            tableDto.setNchangeDuration3(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty913(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty973(dto.getNforecastQty());
            }
            tableDto.setNjobType3(dto.getNjobType());
            tableDto.setNmodelNo3(dto.getNmodelNo());
            tableDto.setNpartNo3(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull3(dto.getNpartNo());
            tableDto.setNwoId3(dto.getNwoId());
            tableDto.setNremark3(dto.getNremark());

            tableDto.setNseq3(dto.getNseq());
        } else if (4 == j) {
            tableDto.setChangeDuration4(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty914(dto.getForecastQty());
            } else {
                tableDto.setForecastQty974(dto.getForecastQty());
            }
            tableDto.setJobType4(dto.getJobType());
            tableDto.setModelNo4(dto.getModelNo());
            tableDto.setPartNo4(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull4(dto.getPartNo());
            tableDto.setWoId4(dto.getWoId());
            tableDto.setRemark4(dto.getRemark());

            tableDto.setSeq4(dto.getSeq());

            tableDto.setNchangeDuration4(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty914(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty974(dto.getNforecastQty());
            }
            tableDto.setNjobType4(dto.getNjobType());
            tableDto.setNmodelNo4(dto.getNmodelNo());
            tableDto.setNpartNo4(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull4(dto.getNpartNo());
            tableDto.setNwoId4(dto.getNwoId());
            tableDto.setNremark4(dto.getNremark());

            tableDto.setNseq4(dto.getNseq());
        } else if (5 == j) {
            tableDto.setChangeDuration5(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty915(dto.getForecastQty());
            } else {
                tableDto.setForecastQty975(dto.getForecastQty());
            }
            tableDto.setJobType5(dto.getJobType());
            tableDto.setModelNo5(dto.getModelNo());
            tableDto.setPartNo5(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull5(dto.getPartNo());
            tableDto.setWoId5(dto.getWoId());
            tableDto.setRemark5(dto.getRemark());

            tableDto.setSeq5(dto.getSeq());

            tableDto.setNchangeDuration5(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty915(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty975(dto.getNforecastQty());
            }
            tableDto.setNjobType5(dto.getNjobType());
            tableDto.setNmodelNo5(dto.getNmodelNo());
            tableDto.setNpartNo5(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull5(dto.getNpartNo());
            tableDto.setNwoId5(dto.getNwoId());
            tableDto.setNremark5(dto.getNremark());

            tableDto.setNseq5(dto.getNseq());
        } else if (6 == j) {
            tableDto.setChangeDuration6(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty916(dto.getForecastQty());
            } else {
                tableDto.setForecastQty976(dto.getForecastQty());
            }
            tableDto.setJobType6(dto.getJobType());
            tableDto.setModelNo6(dto.getModelNo());
            tableDto.setPartNo6(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull6(dto.getPartNo());
            tableDto.setWoId6(dto.getWoId());
            tableDto.setRemark6(dto.getRemark());

            tableDto.setSeq6(dto.getSeq());

            tableDto.setNchangeDuration6(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty916(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty976(dto.getNforecastQty());
            }
            tableDto.setNjobType6(dto.getNjobType());
            tableDto.setNmodelNo6(dto.getNmodelNo());
            tableDto.setNpartNo6(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull6(dto.getNpartNo());
            tableDto.setNwoId6(dto.getNwoId());
            tableDto.setNremark6(dto.getNremark());

            tableDto.setNseq6(dto.getNseq());
        } else if (7 == j) {
            tableDto.setChangeDuration7(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty917(dto.getForecastQty());
            } else {
                tableDto.setForecastQty977(dto.getForecastQty());
            }
            tableDto.setJobType7(dto.getJobType());
            tableDto.setModelNo7(dto.getModelNo());
            tableDto.setPartNo7(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull7(dto.getPartNo());
            tableDto.setWoId7(dto.getWoId());
            tableDto.setRemark7(dto.getRemark());

            tableDto.setSeq7(dto.getSeq());

            tableDto.setNchangeDuration7(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty917(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty977(dto.getNforecastQty());
            }
            tableDto.setNjobType7(dto.getNjobType());
            tableDto.setNmodelNo7(dto.getNmodelNo());
            tableDto.setNpartNo7(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull7(dto.getNpartNo());
            tableDto.setNwoId7(dto.getNwoId());
            tableDto.setNremark7(dto.getNremark());

            tableDto.setNseq7(dto.getNseq());
        } else if (8 == j) {
            tableDto.setChangeDuration8(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty918(dto.getForecastQty());
            } else {
                tableDto.setForecastQty978(dto.getForecastQty());
            }
            tableDto.setJobType8(dto.getJobType());
            tableDto.setModelNo8(dto.getModelNo());
            tableDto.setPartNo8(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull8(dto.getPartNo());
            tableDto.setWoId8(dto.getWoId());
            tableDto.setRemark8(dto.getRemark());

            tableDto.setSeq8(dto.getSeq());

            tableDto.setNchangeDuration8(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty918(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty978(dto.getNforecastQty());
            }
            tableDto.setNjobType8(dto.getNjobType());
            tableDto.setNmodelNo8(dto.getNmodelNo());
            tableDto.setNpartNo8(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull8(dto.getNpartNo());
            tableDto.setNwoId8(dto.getNwoId());
            tableDto.setNremark8(dto.getNremark());

            tableDto.setNseq8(dto.getNseq());
        } else if (9 == j) {
            tableDto.setChangeDuration9(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty919(dto.getForecastQty());
            } else {
                tableDto.setForecastQty979(dto.getForecastQty());
            }
            tableDto.setJobType9(dto.getJobType());
            tableDto.setModelNo9(dto.getModelNo());
            tableDto.setPartNo9(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull9(dto.getPartNo());
            tableDto.setWoId9(dto.getWoId());
            tableDto.setRemark9(dto.getRemark());

            tableDto.setSeq9(dto.getSeq());

            tableDto.setNchangeDuration9(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty919(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty979(dto.getNforecastQty());
            }
            tableDto.setNjobType9(dto.getNjobType());
            tableDto.setNmodelNo9(dto.getNmodelNo());
            tableDto.setNpartNo9(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull9(dto.getNpartNo());
            tableDto.setNwoId9(dto.getNwoId());
            tableDto.setNremark9(dto.getNremark());

            tableDto.setNseq9(dto.getNseq());
        } else if (10 == j) {
            tableDto.setChangeDuration10(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9110(dto.getForecastQty());
            } else {
                tableDto.setForecastQty9710(dto.getForecastQty());
            }
            tableDto.setJobType10(dto.getJobType());
            tableDto.setModelNo10(dto.getModelNo());
            tableDto.setPartNo10(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull10(dto.getPartNo());
            tableDto.setWoId10(dto.getWoId());
            tableDto.setRemark10(dto.getRemark());

            tableDto.setSeq10(dto.getSeq());

            tableDto.setNchangeDuration10(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9110(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty9710(dto.getNforecastQty());
            }
            tableDto.setNjobType10(dto.getNjobType());
            tableDto.setNmodelNo10(dto.getNmodelNo());
            tableDto.setNpartNo10(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull10(dto.getNpartNo());
            tableDto.setNwoId10(dto.getNwoId());
            tableDto.setNremark10(dto.getNremark());

            tableDto.setNseq10(dto.getNseq());
        } else if (11 == j) {
            tableDto.setChangeDuration11(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9111(dto.getForecastQty());
            } else {
                tableDto.setForecastQty9711(dto.getForecastQty());
            }
            tableDto.setJobType11(dto.getJobType());
            tableDto.setModelNo11(dto.getModelNo());
            tableDto.setPartNo11(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull11(dto.getPartNo());
            tableDto.setWoId11(dto.getWoId());
            tableDto.setRemark11(dto.getRemark());

            tableDto.setSeq11(dto.getSeq());

            tableDto.setNchangeDuration11(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9111(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty9711(dto.getNforecastQty());
            }
            tableDto.setNjobType11(dto.getNjobType());
            tableDto.setNmodelNo11(dto.getNmodelNo());
            tableDto.setNpartNo11(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull11(dto.getNpartNo());
            tableDto.setNwoId11(dto.getNwoId());
            tableDto.setNremark11(dto.getNremark());

            tableDto.setNseq11(dto.getNseq());
        } else if (12 == j) {
            tableDto.setChangeDuration12(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9112(dto.getForecastQty());
            } else {
                tableDto.setForecastQty9712(dto.getForecastQty());
            }
            tableDto.setJobType12(dto.getJobType());
            tableDto.setModelNo12(dto.getModelNo());
            tableDto.setPartNo12(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull12(dto.getPartNo());
            tableDto.setWoId12(dto.getWoId());
            tableDto.setRemark12(dto.getRemark());

            tableDto.setSeq12(dto.getSeq());

            tableDto.setNchangeDuration12(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9112(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty9712(dto.getNforecastQty());
            }
            tableDto.setNjobType12(dto.getNjobType());
            tableDto.setNmodelNo12(dto.getNmodelNo());
            tableDto.setNpartNo12(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull12(dto.getNpartNo());
            tableDto.setNwoId12(dto.getNwoId());
            tableDto.setNremark12(dto.getNremark());

            tableDto.setNseq12(dto.getNseq());
        } else if (13 == j) {
            tableDto.setChangeDuration13(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9113(dto.getForecastQty());
            } else {
                tableDto.setForecastQty9713(dto.getForecastQty());
            }
            tableDto.setJobType13(dto.getJobType());
            tableDto.setModelNo13(dto.getModelNo());
            tableDto.setPartNo13(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull13(dto.getPartNo());
            tableDto.setWoId13(dto.getWoId());
            tableDto.setRemark13(dto.getRemark());

            tableDto.setSeq13(dto.getSeq());

            tableDto.setNchangeDuration13(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9113(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty9713(dto.getNforecastQty());
            }
            tableDto.setNjobType13(dto.getNjobType());
            tableDto.setNmodelNo13(dto.getNmodelNo());
            tableDto.setNpartNo13(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull13(dto.getNpartNo());
            tableDto.setNwoId13(dto.getNwoId());
            tableDto.setNremark13(dto.getNremark());

            tableDto.setNseq13(dto.getNseq());
        } else if (14 == j) {
            tableDto.setChangeDuration14(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9114(dto.getForecastQty());
            } else {
                tableDto.setForecastQty9714(dto.getForecastQty());
            }
            tableDto.setJobType14(dto.getJobType());
            tableDto.setModelNo14(dto.getModelNo());
            tableDto.setPartNo14(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull14(dto.getPartNo());
            tableDto.setWoId14(dto.getWoId());
            tableDto.setRemark14(dto.getRemark());

            tableDto.setSeq14(dto.getSeq());

            tableDto.setNchangeDuration14(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9114(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty9714(dto.getNforecastQty());
            }
            tableDto.setNjobType14(dto.getNjobType());
            tableDto.setNmodelNo14(dto.getNmodelNo());
            tableDto.setNpartNo14(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull14(dto.getNpartNo());
            tableDto.setNwoId14(dto.getNwoId());
            tableDto.setNremark14(dto.getNremark());

            tableDto.setNseq14(dto.getNseq());
        } else if (15 == j) {
            tableDto.setChangeDuration15(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9115(dto.getForecastQty());
            } else {
                tableDto.setForecastQty9715(dto.getForecastQty());
            }
            tableDto.setJobType15(dto.getJobType());
            tableDto.setModelNo15(dto.getModelNo());
            tableDto.setPartNo15(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull15(dto.getPartNo());
            tableDto.setWoId15(dto.getWoId());
            tableDto.setRemark15(dto.getRemark());

            tableDto.setSeq15(dto.getSeq());

            tableDto.setNchangeDuration15(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9115(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty9715(dto.getNforecastQty());
            }
            tableDto.setNjobType15(dto.getNjobType());
            tableDto.setNmodelNo15(dto.getNmodelNo());
            tableDto.setNpartNo15(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull15(dto.getNpartNo());
            tableDto.setNwoId15(dto.getNwoId());
            tableDto.setNremark15(dto.getNremark());

            tableDto.setNseq15(dto.getNseq());
        } else if (16 == j) {
            tableDto.setChangeDuration16(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9116(dto.getForecastQty());
            } else {
                tableDto.setForecastQty9716(dto.getForecastQty());
            }
            tableDto.setJobType16(dto.getJobType());
            tableDto.setModelNo16(dto.getModelNo());
            tableDto.setPartNo16(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull16(dto.getPartNo());
            tableDto.setWoId16(dto.getWoId());
            tableDto.setRemark16(dto.getRemark());

            tableDto.setSeq16(dto.getSeq());

            tableDto.setNchangeDuration16(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9116(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty9716(dto.getNforecastQty());
            }
            tableDto.setNjobType16(dto.getNjobType());
            tableDto.setNmodelNo16(dto.getNmodelNo());
            tableDto.setNpartNo16(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull16(dto.getNpartNo());
            tableDto.setNwoId16(dto.getNwoId());
            tableDto.setNremark16(dto.getNremark());

            tableDto.setNseq16(dto.getNseq());
        } else if (17 == j) {
            tableDto.setChangeDuration17(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9117(dto.getForecastQty());
            } else {
                tableDto.setForecastQty9717(dto.getForecastQty());
            }
            tableDto.setJobType17(dto.getJobType());
            tableDto.setModelNo17(dto.getModelNo());
            tableDto.setPartNo17(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull17(dto.getPartNo());
            tableDto.setWoId17(dto.getWoId());
            tableDto.setRemark17(dto.getRemark());

            tableDto.setSeq17(dto.getSeq());

            tableDto.setNchangeDuration17(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9117(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty9717(dto.getNforecastQty());
            }
            tableDto.setNjobType17(dto.getNjobType());
            tableDto.setNmodelNo17(dto.getNmodelNo());
            tableDto.setNpartNo17(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull17(dto.getNpartNo());
            tableDto.setNwoId17(dto.getNwoId());
            tableDto.setNremark17(dto.getNremark());

            tableDto.setNseq17(dto.getNseq());
        } else if (18 == j) {
            tableDto.setChangeDuration18(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9118(dto.getForecastQty());
            } else {
                tableDto.setForecastQty9718(dto.getForecastQty());
            }
            tableDto.setJobType18(dto.getJobType());
            tableDto.setModelNo18(dto.getModelNo());
            tableDto.setPartNo18(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull18(dto.getPartNo());
            tableDto.setWoId18(dto.getWoId());
            tableDto.setRemark18(dto.getRemark());

            tableDto.setSeq18(dto.getSeq());

            tableDto.setNchangeDuration18(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9118(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty9718(dto.getNforecastQty());
            }
            tableDto.setNjobType18(dto.getNjobType());
            tableDto.setNmodelNo18(dto.getNmodelNo());
            tableDto.setNpartNo18(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull18(dto.getNpartNo());
            tableDto.setNwoId18(dto.getNwoId());
            tableDto.setNremark18(dto.getNremark());

            tableDto.setNseq18(dto.getNseq());
        } else if (19 == j) {
            tableDto.setChangeDuration19(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9119(dto.getForecastQty());
            } else {
                tableDto.setForecastQty9719(dto.getForecastQty());
            }
            tableDto.setJobType19(dto.getJobType());
            tableDto.setModelNo19(dto.getModelNo());
            tableDto.setPartNo19(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull19(dto.getPartNo());
            tableDto.setWoId19(dto.getWoId());
            tableDto.setRemark19(dto.getRemark());

            tableDto.setSeq19(dto.getSeq());

            tableDto.setNchangeDuration19(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9119(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty9719(dto.getNforecastQty());
            }
            tableDto.setNjobType19(dto.getNjobType());
            tableDto.setNmodelNo19(dto.getNmodelNo());
            tableDto.setNpartNo19(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull19(dto.getNpartNo());
            tableDto.setNwoId19(dto.getNwoId());
            tableDto.setNremark19(dto.getNremark());

            tableDto.setNseq19(dto.getNseq());
        } else if (20 == j) {
            tableDto.setChangeDuration20(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9120(dto.getForecastQty());
            } else {
                tableDto.setForecastQty9720(dto.getForecastQty());
            }
            tableDto.setJobType20(dto.getJobType());
            tableDto.setModelNo20(dto.getModelNo());
            tableDto.setPartNo20(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull20(dto.getPartNo());
            tableDto.setWoId20(dto.getWoId());
            tableDto.setRemark20(dto.getRemark());

            tableDto.setSeq20(dto.getSeq());

            tableDto.setNchangeDuration20(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9120(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty9720(dto.getNforecastQty());
            }
            tableDto.setNjobType20(dto.getNjobType());
            tableDto.setNmodelNo20(dto.getNmodelNo());
            tableDto.setNpartNo20(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull20(dto.getNpartNo());
            tableDto.setNwoId20(dto.getNwoId());
            tableDto.setNremark20(dto.getNremark());

            tableDto.setNseq20(dto.getNseq());
        } else if (21 == j) {
            tableDto.setChangeDuration21(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9121(dto.getForecastQty());
            } else {
                tableDto.setForecastQty9721(dto.getForecastQty());
            }
            tableDto.setJobType21(dto.getJobType());
            tableDto.setModelNo21(dto.getModelNo());
            tableDto.setPartNo21(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull21(dto.getPartNo());
            tableDto.setWoId21(dto.getWoId());
            tableDto.setRemark21(dto.getRemark());

            tableDto.setSeq21(dto.getSeq());

            tableDto.setNchangeDuration21(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9121(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty9721(dto.getNforecastQty());
            }
            tableDto.setNjobType21(dto.getNjobType());
            tableDto.setNmodelNo21(dto.getNmodelNo());
            tableDto.setNpartNo21(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull21(dto.getNpartNo());
            tableDto.setNwoId21(dto.getNwoId());
            tableDto.setNremark21(dto.getNremark());

            tableDto.setNseq21(dto.getNseq());
        } else if (22 == j) {
            tableDto.setChangeDuration22(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9122(dto.getForecastQty());
            } else {
                tableDto.setForecastQty9722(dto.getForecastQty());
            }
            tableDto.setJobType22(dto.getJobType());
            tableDto.setModelNo22(dto.getModelNo());
            tableDto.setPartNo22(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull22(dto.getPartNo());
            tableDto.setWoId22(dto.getWoId());
            tableDto.setRemark22(dto.getRemark());

            tableDto.setSeq22(dto.getSeq());

            tableDto.setNchangeDuration22(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9122(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty9722(dto.getNforecastQty());
            }
            tableDto.setNjobType22(dto.getNjobType());
            tableDto.setNmodelNo22(dto.getNmodelNo());
            tableDto.setNpartNo22(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull22(dto.getNpartNo());
            tableDto.setNwoId22(dto.getNwoId());
            tableDto.setNremark22(dto.getNremark());

            tableDto.setNseq22(dto.getNseq());
        } else if (23 == j) {
            tableDto.setChangeDuration23(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9123(dto.getForecastQty());
            } else {
                tableDto.setForecastQty9723(dto.getForecastQty());
            }
            tableDto.setJobType23(dto.getJobType());
            tableDto.setModelNo23(dto.getModelNo());
            tableDto.setPartNo23(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull23(dto.getPartNo());
            tableDto.setWoId23(dto.getWoId());
            tableDto.setRemark23(dto.getRemark());

            tableDto.setSeq23(dto.getSeq());

            tableDto.setNchangeDuration23(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9123(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty9723(dto.getNforecastQty());
            }
            tableDto.setNjobType23(dto.getNjobType());
            tableDto.setNmodelNo23(dto.getNmodelNo());
            tableDto.setNpartNo23(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull23(dto.getNpartNo());
            tableDto.setNwoId23(dto.getNwoId());
            tableDto.setNremark23(dto.getNremark());

            tableDto.setNseq23(dto.getNseq());
        } else if (24 == j) {
            tableDto.setChangeDuration24(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9124(dto.getForecastQty());
            } else {
                tableDto.setForecastQty9724(dto.getForecastQty());
            }
            tableDto.setJobType24(dto.getJobType());
            tableDto.setModelNo24(dto.getModelNo());
            tableDto.setPartNo24(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull24(dto.getPartNo());
            tableDto.setWoId24(dto.getWoId());
            tableDto.setRemark24(dto.getRemark());

            tableDto.setSeq24(dto.getSeq());

            tableDto.setNchangeDuration24(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9124(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty9724(dto.getNforecastQty());
            }
            tableDto.setNjobType24(dto.getNjobType());
            tableDto.setNmodelNo24(dto.getNmodelNo());
            tableDto.setNpartNo24(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull24(dto.getNpartNo());
            tableDto.setNwoId24(dto.getNwoId());
            tableDto.setNremark24(dto.getNremark());

            tableDto.setNseq24(dto.getNseq());
        } else if (25 == j) {
            tableDto.setChangeDuration25(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9125(dto.getForecastQty());
            } else {
                tableDto.setForecastQty9725(dto.getForecastQty());
            }
            tableDto.setJobType25(dto.getJobType());
            tableDto.setModelNo25(dto.getModelNo());
            tableDto.setPartNo25(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull25(dto.getPartNo());
            tableDto.setWoId25(dto.getWoId());
            tableDto.setRemark25(dto.getRemark());

            tableDto.setSeq25(dto.getSeq());

            tableDto.setNchangeDuration25(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9125(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty9725(dto.getNforecastQty());
            }
            tableDto.setNjobType25(dto.getNjobType());
            tableDto.setNmodelNo25(dto.getNmodelNo());
            tableDto.setNpartNo25(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull25(dto.getNpartNo());
            tableDto.setNwoId25(dto.getNwoId());
            tableDto.setNremark25(dto.getNremark());

            tableDto.setNseq25(dto.getNseq());
        } else if (26 == j) {
            tableDto.setChangeDuration26(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9126(dto.getForecastQty());
            } else {
                tableDto.setForecastQty9726(dto.getForecastQty());
            }
            tableDto.setJobType26(dto.getJobType());
            tableDto.setModelNo26(dto.getModelNo());
            tableDto.setPartNo26(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull26(dto.getPartNo());
            tableDto.setWoId26(dto.getWoId());
            tableDto.setRemark26(dto.getRemark());

            tableDto.setSeq26(dto.getSeq());

            tableDto.setNchangeDuration26(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9126(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty9726(dto.getNforecastQty());
            }
            tableDto.setNjobType26(dto.getNjobType());
            tableDto.setNmodelNo26(dto.getNmodelNo());
            tableDto.setNpartNo26(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull26(dto.getNpartNo());
            tableDto.setNwoId26(dto.getNwoId());
            tableDto.setNremark26(dto.getNremark());

            tableDto.setNseq26(dto.getNseq());
        } else if (27 == j) {
            tableDto.setChangeDuration27(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9127(dto.getForecastQty());
            } else {
                tableDto.setForecastQty9727(dto.getForecastQty());
            }
            tableDto.setJobType27(dto.getJobType());
            tableDto.setModelNo27(dto.getModelNo());
            tableDto.setPartNo27(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull27(dto.getPartNo());
            tableDto.setWoId27(dto.getWoId());
            tableDto.setRemark27(dto.getRemark());

            tableDto.setSeq27(dto.getSeq());

            tableDto.setNchangeDuration27(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9127(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty9727(dto.getNforecastQty());
            }
            tableDto.setNjobType27(dto.getNjobType());
            tableDto.setNmodelNo27(dto.getNmodelNo());
            tableDto.setNpartNo27(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull27(dto.getNpartNo());
            tableDto.setNwoId27(dto.getNwoId());
            tableDto.setNremark27(dto.getNremark());

            tableDto.setNseq27(dto.getNseq());
        } else if (28 == j) {
            tableDto.setChangeDuration28(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9128(dto.getForecastQty());
            } else {
                tableDto.setForecastQty9728(dto.getForecastQty());
            }
            tableDto.setJobType28(dto.getJobType());
            tableDto.setModelNo28(dto.getModelNo());
            tableDto.setPartNo28(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull28(dto.getPartNo());
            tableDto.setWoId28(dto.getWoId());
            tableDto.setRemark28(dto.getRemark());

            tableDto.setSeq28(dto.getSeq());

            tableDto.setNchangeDuration28(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9128(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty9728(dto.getNforecastQty());
            }
            tableDto.setNjobType28(dto.getNjobType());
            tableDto.setNmodelNo28(dto.getNmodelNo());
            tableDto.setNpartNo28(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull28(dto.getNpartNo());
            tableDto.setNwoId28(dto.getNwoId());
            tableDto.setNremark28(dto.getNremark());

            tableDto.setNseq28(dto.getNseq());
        } else if (29 == j) {
            tableDto.setChangeDuration29(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9129(dto.getForecastQty());
            } else {
                tableDto.setForecastQty9729(dto.getForecastQty());
            }
            tableDto.setJobType29(dto.getJobType());
            tableDto.setModelNo29(dto.getModelNo());
            tableDto.setPartNo29(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull29(dto.getPartNo());
            tableDto.setWoId29(dto.getWoId());
            tableDto.setRemark29(dto.getRemark());

            tableDto.setSeq29(dto.getSeq());

            tableDto.setNchangeDuration29(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9129(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty9729(dto.getNforecastQty());
            }
            tableDto.setNjobType29(dto.getNjobType());
            tableDto.setNmodelNo29(dto.getNmodelNo());
            tableDto.setNpartNo29(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull29(dto.getNpartNo());
            tableDto.setNwoId29(dto.getNwoId());
            tableDto.setNremark29(dto.getNremark());

            tableDto.setNseq29(dto.getNseq());
        } else if (30 == j) {
            tableDto.setChangeDuration30(dto.getChangeDuration());
            if (dto.getPartNo() != null && (dto.getPartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setForecastQty9130(dto.getForecastQty());
            } else {
                tableDto.setForecastQty9730(dto.getForecastQty());
            }
            tableDto.setJobType30(dto.getJobType());
            tableDto.setModelNo30(dto.getModelNo());
            tableDto.setPartNo30(dto.getPartNo() != null && dto.getPartNo().length() > 2 ? dto.getPartNo().substring(0, 2) : null);
            tableDto.setPartNoFull30(dto.getPartNo());
            tableDto.setWoId30(dto.getWoId());
            tableDto.setRemark30(dto.getRemark());

            tableDto.setSeq30(dto.getSeq());

            tableDto.setNchangeDuration30(dto.getNchangeDuration());
            if (dto.getNpartNo() != null && (dto.getNpartNo().indexOf("91") != -1 || dto.getPartNo().indexOf("90") != -1)) {
                tableDto.setNforecastQty9130(dto.getNforecastQty());
            } else {
                tableDto.setNforecastQty9730(dto.getNforecastQty());
            }
            tableDto.setNjobType30(dto.getNjobType());
            tableDto.setNmodelNo30(dto.getNmodelNo());
            tableDto.setNpartNo30(dto.getNpartNo() != null && dto.getNpartNo().length() > 2 ? dto.getNpartNo().substring(0, 2) : null);
            tableDto.setNpartNoFull30(dto.getNpartNo());
            tableDto.setNwoId30(dto.getNwoId());
            tableDto.setNremark30(dto.getNremark());

            tableDto.setNseq30(dto.getNseq());
        }
    }


}
