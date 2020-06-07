package com.lcm.service;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.lcm.domain.*;
import com.lcm.repository.LineRepository;
import com.lcm.repository.ModModelRepository;
import com.lcm.repository.SpecialRepository;
import com.lcm.repository.WoxxRepository;
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
public class SpecialService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmService.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final SpecialRepository specialRepository;
    private final LineRepository lineRepository;
    private final ModModelRepository modModelRepository;
    private final WoxxRepository woxxRepository;

    public SpecialService(SpecialRepository specialRepository, LineRepository lineRepository, ModModelRepository modModelRepository,
                          WoxxRepository woxxRepository) {
        this.specialRepository = specialRepository;
        this.lineRepository = lineRepository;
        this.modModelRepository = modModelRepository;
        this.woxxRepository = woxxRepository;
    }

    @Transactional
    public void parseExcel(MultipartFile file) throws Exception {
        try (InputStream inputStream = file.getInputStream()) {
            List<Object> rows = EasyExcelFactory.read(inputStream, new Sheet(1,2));
            Map<String, Map<String, String>> siteLineMap = new HashMap<>();
            String employeeId = UserUtils.currentUser().getEmployeeId();
            List<Special> list = IntStream.range(0, rows.size()).mapToObj(i -> {
                Special special = new Special();
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
                special.setSite(site);
                String fab = (String) row.get(1);
                if (StringUtils.isEmpty(fab)) {
                    throw new ExcelDataException("第" + (i + 3) + "行fab不能为空");
                }
                special.setFab(fab);
                String area = (String) row.get(2);
                if (StringUtils.isEmpty(area)) {
                    throw new ExcelDataException("第" + (i + 3) + "行area不能为空");
                }
                special.setArea(area);
                String line = (String) row.get(3);
                if (siteLineMap.get(site).get(line) == null) {
                    LOGGER.warn("第" + (i + 3) + "行line错误");
                    throw new ExcelDataException("第" + (i + 3) + "行line错误");
                }
                special.setLine(line);
                String dateDouStr = (String) row.get(4);
                if (StringUtils.isEmpty(dateDouStr)) {
                    throw new ExcelDataException("第" + (i + 3) + "行shift_date不能为空");
                }
                Date date = HSSFDateUtil.getJavaDate(Double.parseDouble(dateDouStr));
                LocalDate shiftDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                special.setShiftDate(shiftDate);
                String dateStr = DATE_FORMATTER.format(shiftDate);
                String shift = (String) row.get(5);
                if (StringUtils.isEmpty(shift)) {
                    throw new ExcelDataException("第" + (i + 3) + "行shift不能为空");
                }
                special.setShift(shift);

                String partNo = (String) row.get(6);
                if (StringUtils.isEmpty(partNo)) {
                    throw new ExcelDataException("第" + (i + 3) + "行part_no不能为空");
                }
                special.setPartNo(partNo);
                ModelId modelId = new ModelId();
                modelId.setSite(site);
                modelId.setPartNo(partNo);
                Optional<ModModel> model = modModelRepository.findById(modelId);
                if(!model.isPresent()) {
                    LOGGER.warn("第" + (i + 3) + "行错误,找不到对应的PART_NO");
                    throw new ExcelDataException("第" + (i + 3) + "行错误,找不到对应的PART_NO");
                }
                special.setModelNo(model.get().getModelNo());

                /*String woId = (String) row.get(7);
                special.setWoId(woId);
                WoxxId woxxId = new WoxxId();
                woxxId.setSite(site);
                woxxId.setFab(fab);
                woxxId.setArea(area);
                woxxId.setWoId(woId);
                Optional<Woxx> byId1 = woxxRepository.findById(woxxId);
                special.setCellPartNo(byId1.get().getCellPartNo());


                special.setGrade((String) row.get(8));
                special.setChangeLevel((String) row.get(9));
                special.setPlanQty((String) row.get(10));
                special.setJobType((String) row.get(11));
                special.setRemark((String) row.get(12));
                special.setLmTime(LocalDateTime.now());*/

                String woId = (String) row.get(7);
                if (StringUtils.isEmpty(woId)) {
                    throw new ExcelDataException("第" + (i + 3) + "行wo_id不能为空");
                }
                special.setWoId(woId);
                special.setCellPartNo((String) row.get(8));
                String grade= (String) row.get(9);
                if (StringUtils.isEmpty(grade)) {
                    throw new ExcelDataException("第" + (i + 3) + "行grade不能为空");
                }
                special.setGrade(grade);
                special.setJobId(line + "-" + dateStr + "-" + partNo + "-" + grade + "-" + woId + "-" + shift);
                String changeLevel = (String) row.get(10);
                /*if (StringUtils.isEmpty(changeLevel)) {
                    throw new ExcelDataException("第" + (i + 3) + "行change_level不能为空");
                }*/
                special.setChangeLevel(changeLevel);
                special.setPlanQty((String) row.get(11));
                String jobType= (String) row.get(12);
                if (StringUtils.isEmpty(jobType)) {
                    throw new ExcelDataException("第" + (i + 3) + "行job_type不能为空");
                }
                special.setJobType(jobType);
                special.setRemark((String) row.get(13));
                special.setLmUser(employeeId);
                special.setLmTime(LocalDateTime.now());
                return special;
            }).collect(Collectors.toList());
            List<Special> collect = list.stream().filter(l -> l != null).collect(Collectors.toList());
            specialRepository.saveAll(collect);
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
        columnWidth.put(5,15*256);
        columnWidth.put(6,25*256);
        columnWidth.put(7,25*256);
        columnWidth.put(8,25*256);
        columnWidth.put(9,25*256);
        columnWidth.put(10,15*256);
        columnWidth.put(11,15*256);
        columnWidth.put(12,15*256);
        columnWidth.put(13,15*256);
        columnWidth.put(14,30*256);
        columnWidth.put(15,20*256);
        columnWidth.put(16,25*256);
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
        headColumn7.add("model_no");
        List<String> headColumn8 = new ArrayList<String>();
        headColumn8.add("part_no");
        List<String> headColumn9 = new ArrayList<String>();
        headColumn9.add("wo_id");
        List<String> headColumn10 = new ArrayList<String>();
        headColumn10.add("cell_part_no");
        List<String> headColumn11 = new ArrayList<String>();
        headColumn11.add("grade");
        List<String> headColumn12 = new ArrayList<String>();
        headColumn12.add("change_level");
        List<String> headColumn13 = new ArrayList<String>();
        headColumn13.add("plan_qty");
        List<String> headColumn14 = new ArrayList<String>();
        headColumn14.add("job_type");
        List<String> headColumn15 = new ArrayList<String>();
        headColumn15.add("remark");
        List<String> headColumn16 = new ArrayList<String>();
        headColumn16.add("lm_user");
        List<String> headColumn17 = new ArrayList<String>();
        headColumn17.add("lm_time");
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
        head.add(headColumn13);
        head.add(headColumn14);
        head.add(headColumn15);
        head.add(headColumn16);
        head.add(headColumn17);
        sheet.setHead(head);
        List<Special> result = (List<Special>) specialRepository.findAll(predicate);
        List<List<String>> data = new ArrayList<>(result.size());
        result.forEach(special -> {
            List<String> row = new ArrayList<>();
            row.add(special.getSite());
            row.add(special.getFab());
            row.add(special.getArea());
            row.add(special.getLine());
            row.add(special.getShiftDate().toString());
            row.add(special.getSite());
            row.add(special.getModelNo());
            row.add(special.getPartNo());
            row.add(special.getWoId());
            row.add(special.getCellPartNo());
            row.add(special.getGrade());
            row.add(special.getChangeLevel());
            row.add(special.getPlanQty());
            row.add(special.getJobType());
            row.add(special.getRemark());
            row.add(special.getLmUser());
            row.add(special.getLmTime() == null ? "" : special.getLmTime().toString());
            data.add(row);
        });
        excelWriter.write0(data, sheet);
        excelWriter.finish();
    }

    public void update(String changeLevel, String planQty, String jobType, String remark, String jobId) {
        Optional<Special> eqpm = specialRepository.findById(jobId);
        eqpm.ifPresent(e -> {
            e.setChangeLevel(changeLevel);
            e.setPlanQty(planQty);
            e.setJobType(jobType);
            e.setRemark(remark);
            e.setLmUser(UserUtils.currentUser().getEmployeeId());
            e.setLmTime(LocalDateTime.now());
            specialRepository.save(e);
        });
    }

    public void save(Special sp) {
        String line = sp.getLine();
        String dateStr = DATE_FORMATTER.format(sp.getShiftDate());
        sp.setJobId(line + "-" + dateStr + "-" + sp.getPartNo() + "-" + sp.getGrade() + "-" + sp.getWoId() + "-" + sp.getShift());
        sp.setJobType("PM");
        sp.setLmUser(UserUtils.currentUser().getEmployeeId());
        sp.setLmTime(LocalDateTime.now());
        specialRepository.save(sp);
    }
}
