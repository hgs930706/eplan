package com.lcm.service;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.lcm.domain.EquipmentPreventiveMaintenance;
import com.lcm.domain.Line;
import com.lcm.repository.EquipmentPreventiveMaintenanceRepository;
import com.lcm.repository.LineRepository;
import com.lcm.util.UserUtils;
import com.lcm.web.rest.errors.ExcelDataException;
import com.querydsl.core.types.Predicate;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.transaction.annotation.Transactional;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PmService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmService.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final EquipmentPreventiveMaintenanceRepository eqpmRepository;
    private final LineRepository lineRepository;

    public PmService(EquipmentPreventiveMaintenanceRepository eqpmRepository, LineRepository lineRepository) {
        this.eqpmRepository = eqpmRepository;
        this.lineRepository = lineRepository;
    }

    @Transactional
    public void parseExcel(MultipartFile file) throws Exception {
        try (InputStream inputStream = file.getInputStream()) {
            List<Object> rows = EasyExcelFactory.read(inputStream, new Sheet(1,2));
            Map<String, Map<String, String>> siteLineMap = new HashMap<>();
            String employeeId = UserUtils.currentUser().getEmployeeId();
            List<EquipmentPreventiveMaintenance> list = IntStream.range(0, rows.size()).mapToObj(i -> {
                EquipmentPreventiveMaintenance eqpm = new EquipmentPreventiveMaintenance();
                List<Object> row = (List<Object>) rows.get(i);
                String site = (String) row.get(0);
                if(org.apache.commons.lang3.StringUtils.isBlank(site)){
                    return null;
                }
                if (siteLineMap.get(site) == null) {
                    List<Line> lines = lineRepository.findBySite(site);
                    if (lines.size() == 0) {
                        LOGGER.warn("第" + (i + 3) + "行site错误");
                        throw new ExcelDataException("第" + (i + 3) + "行site错误");
                    }
                    Map<String, String> lineMap = lines.stream().collect(Collectors.toMap(Line::getLine, line -> ""));
                    siteLineMap.put(site, lineMap);
                }
                eqpm.setSite(site);
                String fab = (String) row.get(1);
                if (StringUtils.isEmpty(fab)) {
                    throw new ExcelDataException("第" + (i + 3) + "行fab不能为空");
                }
                eqpm.setFab(fab);
                String area = (String) row.get(2);
                if (StringUtils.isEmpty(area)) {
                    throw new ExcelDataException("第" + (i + 3) + "行area不能为空");
                }
                eqpm.setArea(area);
                String line = (String) row.get(3);
                if (siteLineMap.get(site).get(line) == null) {
                    LOGGER.warn("第" + (i + 3) + "行line错误");
                    throw new ExcelDataException("第" + (i + 3) + "行line错误");
                }
                eqpm.setLine(line);
                String dateDouStr = (String) row.get(4);
                if (StringUtils.isEmpty(dateDouStr)) {
                    throw new ExcelDataException("第" + (i + 3) + "行shift_date不能为空");
                }
                Date date = HSSFDateUtil.getJavaDate(Double.parseDouble(dateDouStr));
                LocalDate shiftDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                eqpm.setShiftDate(shiftDate);
                String dateStr = DATE_FORMATTER.format(shiftDate);
                String shift = (String) row.get(5);
                if (StringUtils.isEmpty(shift)) {
                    throw new ExcelDataException("第" + (i + 3) + "行shift不能为空");
                }
                eqpm.setShift(shift);
                eqpm.setJobId(line + "-" + dateStr + "-" + shift);
                String jobType = (String) row.get(6);
                if (StringUtils.isEmpty(jobType)) {
                    throw new ExcelDataException("第" + (i + 3) + "行job_type不能为空");
                }
                eqpm.setJobType(jobType);
                eqpm.setPmDuration((String) row.get(7));
                eqpm.setRemark((String) row.get(8));
                eqpm.setLmUser(employeeId);
                eqpm.setLmTime(LocalDateTime.now());
                return eqpm;
            }).collect(Collectors.toList());
            List<EquipmentPreventiveMaintenance> collect = list.stream().filter(l -> l != null).collect(Collectors.toList());
            eqpmRepository.saveAll(collect);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void exportExcel(Predicate predicate, OutputStream outputStream) {
        ExcelWriter excelWriter = EasyExcelFactory.getWriter(outputStream);
        Sheet sheet = new Sheet(1, 0);
        sheet.setSheetName("Pm");
        Map columnWidth = new HashMap();
        columnWidth.put(0,15*256);
        columnWidth.put(1,15*256);
        columnWidth.put(2,15*256);
        columnWidth.put(3,20*256);
        columnWidth.put(4,20*256);
        columnWidth.put(5,25*256);
        columnWidth.put(6,25*256);
        columnWidth.put(7,25*256);
        columnWidth.put(8,15*256);
        columnWidth.put(9,15*256);
        columnWidth.put(10,20*256);
        columnWidth.put(11,25*256);
        sheet.setColumnWidthMap(columnWidth);
        sheet.setAutoWidth(true);
        List<List<String>> head = new ArrayList<List<String>>();
        List<String> headColumn1 = new ArrayList<String>();
        headColumn1.add("site");
        List<String> headColumn2 = new ArrayList<String>();
        headColumn2.add("fab");
        List<String> headColumn3 = new ArrayList<String>();
        headColumn3.add("area");
        List<String> headColumn4 = new ArrayList<String>();
        headColumn4.add("line");
        List<String> headColumn5 = new ArrayList<String>();
        headColumn5.add("shift_date");
        List<String> headColumn6 = new ArrayList<String>();
        headColumn6.add("shift");
        List<String> headColumn7 = new ArrayList<String>();
        headColumn7.add("pm_duration");
        List<String> headColumn8 = new ArrayList<String>();
        headColumn8.add("job_type");
        List<String> headColumn9 = new ArrayList<String>();
        headColumn9.add("remark");
        List<String> headColumn10 = new ArrayList<String>();
        headColumn10.add("lm_user");
        List<String> headColumn11 = new ArrayList<String>();
        headColumn11.add("lm_time");
        head.add(headColumn1);
        head.add(headColumn2);
        head.add(headColumn3);
        head.add(headColumn4);
        head.add(headColumn5);
        head.add(headColumn6);
        head.add(headColumn7);
        head.add(headColumn8);
        head.add(headColumn9);
        head.add(headColumn10);
        head.add(headColumn11);
        sheet.setHead(head);
        List<EquipmentPreventiveMaintenance> result = (List<EquipmentPreventiveMaintenance>) eqpmRepository.findAll(predicate);
        List<List<String>> data = new ArrayList<>(result.size());
        result.forEach(eqpm -> {
            List<String> row = new ArrayList<>();
            row.add(eqpm.getSite());
            row.add(eqpm.getFab());
            row.add(eqpm.getArea());
            row.add(eqpm.getLine());
            row.add(eqpm.getShiftDate().toString());
            row.add(eqpm.getShift());
            row.add(eqpm.getPmDuration());
            row.add(eqpm.getJobType());
            row.add(eqpm.getRemark());
            row.add(eqpm.getLmUser());
            row.add(eqpm.getLmTime() == null ? "" : eqpm.getLmTime().toString());
            data.add(row);
        });
        excelWriter.write0(data, sheet);
        excelWriter.finish();
    }

    public void update(String pmDuration, String remark, String jobId) {
        Optional<EquipmentPreventiveMaintenance> eqpm = eqpmRepository.findById(jobId);
        eqpm.ifPresent(e -> {
            e.setPmDuration(pmDuration);
            e.setRemark(remark);
            e.setLmUser(UserUtils.currentUser().getEmployeeId());
            e.setLmTime(LocalDateTime.now());
            eqpmRepository.save(e);
        });
    }

    public void save(EquipmentPreventiveMaintenance eqpm) {
        String line = eqpm.getLine();
        String dateStr = DATE_FORMATTER.format(eqpm.getShiftDate());
        String shift = eqpm.getShift();
        eqpm.setJobId(line + "-" + dateStr + "-" + shift);
        //eqpm.setJobType("PM");
        eqpm.setLmUser(UserUtils.currentUser().getEmployeeId());
        eqpm.setLmTime(LocalDateTime.now());
        eqpmRepository.save(eqpm);
    }
}
