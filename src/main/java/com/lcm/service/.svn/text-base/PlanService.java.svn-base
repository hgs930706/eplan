package com.lcm.service;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.lcm.domain.Line;
import com.lcm.domain.ModModel;
import com.lcm.domain.ModelId;
import com.lcm.domain.Plan;
import com.lcm.repository.LineRepository;
import com.lcm.repository.ModModelRepository;
import com.lcm.repository.PlanRepository;
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

@Service
public class PlanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmService.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final PlanRepository planRepository;
    private final LineRepository lineRepository;
    private final ModModelRepository modModelRepository;

    public PlanService(PlanRepository planRepository, LineRepository lineRepository, ModModelRepository modModelRepository) {
        this.planRepository = planRepository;
        this.lineRepository = lineRepository;
        this.modModelRepository = modModelRepository;
    }

    @Transactional
    public void parseExcel(MultipartFile file) throws Exception {
        try (InputStream inputStream = file.getInputStream()) {
            List<Object> rows = EasyExcelFactory.read(inputStream, new Sheet(1,1));
            List<Object> header = (List<Object>) rows.get(0);
            List<LocalDate> headerShiftDates = new ArrayList<>();
            header.stream().skip(9).forEach(dateDouStr -> {
                if (dateDouStr != null) {
                    Date date = HSSFDateUtil.getJavaDate(Double.parseDouble((String) dateDouStr));
                    LocalDate shiftDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    headerShiftDates.add(shiftDate);
                }
            });
            Map<String, Map<String, String>> siteLineMap = new HashMap<>();
            List<Plan> list = new ArrayList<>();
            String employeeId = UserUtils.currentUser().getEmployeeId();
            for (int i = 1; i < rows.size(); i++) {
                List<Object> cols = (List<Object>) rows.get(i);
                String site = (String) cols.get(0);
                if(org.apache.commons.lang3.StringUtils.isBlank(site)){
                    break;
                }
                if (siteLineMap.get(site) == null) {
                    List<Line> lines = lineRepository.findBySite(site);
                    if (lines.size() == 0) {
                        LOGGER.warn("第" + (i + 2) + "行site错误");
                        throw new ExcelDataException("第" + (i + 2) + "行site错误");
                    }
                    Map<String, String> lineMap = lines.stream().collect(Collectors.toMap(Line::getLine, line -> ""));
//                    Map<String, String> lineMap = lines.stream().collect(Collectors.toMap(Line::getLine, line -> "",(key1,key2) -> key1 + "," + key2 ));
                    siteLineMap.put(site, lineMap);
                }
                String fab = (String) cols.get(1);
                if (StringUtils.isEmpty(fab)) {
                    throw new ExcelDataException("第" + (i + 2) + "行fab不能为空");
                }
                String area = (String) cols.get(2);
                if (StringUtils.isEmpty(area)) {
                    throw new ExcelDataException("第" + (i + 2) + "行area不能为空");
                }
                String line = (String) cols.get(3);
                if (siteLineMap.get(site).get(line) == null) {
                    LOGGER.warn("第" + (i + 2) + "行line错误");
                    throw new ExcelDataException("第" + (i + 2) + "行line错误");
                }
                String partNo = (String) cols.get(5);
                if (StringUtils.isEmpty(partNo)) {
                    throw new ExcelDataException("第" + (i + 2) + "行part_no不能为空");
                }
                ModelId modelId = new ModelId();
                modelId.setSite(site);
                modelId.setPartNo(partNo);
                Optional<ModModel> model = modModelRepository.findById(modelId);
                if(!model.isPresent()) {
                    LOGGER.warn("第" + (i + 2) + "行错误,找不到对应的PART_NO");
                    throw new ExcelDataException("第" + (i + 2) + "行错误,找不到对应的PART_NO");
                }
                String cellPartNo = (String) cols.get(6);
//                if (StringUtils.isEmpty(cellPartNo)) {
//                    throw new ExcelDataException("第" + (i + 2) + "行cell_part_no不能为空");
//                }
                String grade = (String) cols.get(7);
                if (StringUtils.isEmpty(grade)) {
                    throw new ExcelDataException("第" + (i + 2) + "行grade不能为空");
                }
                for (int j = 0; j < headerShiftDates.size(); j++) {
                    if(j + 9 >= cols.size()){
                        throw new ExcelDataException("格式错误！！！");
                    }
                    String qty = (String) cols.get(j + 9);
                    LocalDate date = headerShiftDates.get(j);
                    planRepository.deleteBySiteAndFabAndAreaAndShiftDate(site, fab, area, date);
                    if (qty != null && !"".equals(qty.trim())) {
                        try {
                            Integer.parseInt(qty);
                        } catch (Exception e) {
                            throw new ExcelDataException("第" + (i + 2) + "行," + "第" + (j + 9 + 1) + "列无效数字");
                        }
                        Plan plan = new Plan();
                        plan.setSite(site);
                        plan.setFab(fab);
                        plan.setArea(area);
                        plan.setLine(line);
                        plan.setPartNo(partNo);
                        plan.setModelNo(model.get().getModelNo());
                        plan.setCellPartNo(cellPartNo);
                        plan.setGrade(grade);
                        plan.setShiftDate(date);
                        String dateStr = DATE_FORMATTER.format(date);
                        plan.setJobId(line + "-" + dateStr + "-" + partNo + "-" + grade);
                        plan.setPlanQty(qty);
                        plan.setJobType("PROD");
                        plan.setLmUser(employeeId);
                        plan.setLmTime(LocalDateTime.now());
                        list.add(plan);
                    }
                }
            }
            planRepository.saveAll(list);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void exportExcel(Predicate predicate, OutputStream outputStream) {
        ExcelWriter excelWriter = EasyExcelFactory.getWriter(outputStream);
        Sheet sheet = new Sheet(1, 0);
        sheet.setSheetName("Plan");
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
        headColumn6.add("model_no");
        List<String> headColumn7 = new ArrayList<String>();
        headColumn7.add("part_no");
        List<String> headColumn8 = new ArrayList<String>();
        headColumn8.add("cell_part_no");
        List<String> headColumn9 = new ArrayList<String>();
        headColumn9.add("grade");
        List<String> headColumn10 = new ArrayList<String>();
        headColumn10.add("plan_qty");
        List<String> headColumn11 = new ArrayList<String>();
        headColumn11.add("lm_user");
        List<String> headColumn12 = new ArrayList<String>();
        headColumn12.add("lm_time");
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
        head.add(headColumn12);
        sheet.setHead(head);
        List<Plan> result = (List<Plan>) planRepository.findAll(predicate);
        List<List<String>> data = new ArrayList<>(result.size());
        result.forEach(plan -> {
            List<String> row = new ArrayList<>();
            row.add(plan.getSite());
            row.add(plan.getFab());
            row.add(plan.getArea());
            row.add(plan.getLine());
            row.add(plan.getShiftDate().toString());
            row.add(plan.getModelNo());
            row.add(plan.getPartNo());
            row.add(plan.getCellPartNo());
            row.add(plan.getGrade());
            row.add(plan.getPlanQty());
            row.add(plan.getLmUser());
            row.add(plan.getLmTime() == null ? "" : plan.getLmTime().toString());
            data.add(row);
        });
        excelWriter.write0(data, sheet);
        excelWriter.finish();
    }

    public void update(String cellPartNo, String grade, String planQty, String jobId) {
        Optional<Plan> plan = planRepository.findById(jobId);
        plan.ifPresent(p -> {
            p.setCellPartNo(cellPartNo);
            p.setGrade(grade);
            p.setPlanQty(planQty);
            p.setLmUser(UserUtils.currentUser().getEmployeeId());
            p.setLmTime(LocalDateTime.now());
            planRepository.save(p);
        });
    }

    public void save(Plan plan) {
        String line = plan.getLine();
        String dateStr = DATE_FORMATTER.format(plan.getShiftDate());
        plan.setJobId(line + "-" + dateStr + "-" + plan.getPartNo() + "-" + plan.getGrade());
        plan.setJobType("PROD");
        plan.setLmUser(UserUtils.currentUser().getEmployeeId());
        plan.setLmTime(LocalDateTime.now());
        planRepository.save(plan);
    }
}
