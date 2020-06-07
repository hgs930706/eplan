package com.lcm.service;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.lcm.domain.FacCapa;
import com.lcm.domain.FacCapaId;
import com.lcm.repository.FacCapaRepository;
import com.lcm.util.UserUtils;
import com.querydsl.core.types.Predicate;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.transaction.annotation.Transactional;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class FacCapaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FacCapaService.class);

    private final FacCapaRepository facCapaRepository;

    public FacCapaService(FacCapaRepository facCapaRepository) {
        this.facCapaRepository = facCapaRepository;
    }

    @Transactional
    public void parseExcel(MultipartFile file) throws Exception {
        try (InputStream inputStream = file.getInputStream()) {
            List<Object> rows = EasyExcelFactory.read(inputStream, new Sheet(1, 0));
            List<Object> header = (List<Object>) rows.get(0);
            List<String> valueItems = new ArrayList<>();
            String employeeId = UserUtils.currentUser().getEmployeeId();
            header.stream().skip(5).forEach( value -> valueItems.add((String) value));
            List<FacCapa> list = new ArrayList<>();
            rows.stream().skip(2).forEach(row -> {
                List<Object> cols = (List<Object>) row;
                for (int i = 0; i < valueItems.size(); i++) {
                    String value = (String) cols.get(5 + i);
                    if (value != null && !"0".equals(value)) {
                        FacCapa facCapa = new FacCapa();
                        FacCapaId facCapaId = new FacCapaId();
                        facCapaId.setSite((String) cols.get(0));
                        facCapaId.setFab((String) cols.get(1));
                        facCapaId.setArea((String) cols.get(2));
                        String dateDouStr = (String) cols.get(3);
                        Date date = HSSFDateUtil.getJavaDate(Double.parseDouble(dateDouStr));
                        LocalDate shiftDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        facCapaId.setShiftDate(shiftDate);
                        facCapaId.setShift((String) cols.get(4));
                        facCapaId.setScoreItem(valueItems.get(i));
                        facCapa.setItemValue(value);
                        facCapa.setLmUser(employeeId);
                        facCapa.setLmTime(LocalDateTime.now());
                        facCapa.setFacCapaId(facCapaId);
                        list.add(facCapa);
                    }
                }
            });
            facCapaRepository.saveAll(list);
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
        columnWidth.put(4,15*256);
        columnWidth.put(5,20*256);
        columnWidth.put(6,20*256);
        columnWidth.put(7,20*256);
        columnWidth.put(8,25*256);
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
        headColumn4.add("shift_date");
        List<String> headColumn5 = new ArrayList<String>();
        headColumn5.add("shift");
        List<String> headColumn6 = new ArrayList<String>();
        headColumn6.add("score_item");
        List<String> headColumn7 = new ArrayList<String>();
        headColumn7.add("item_value");
        List<String> headColumn8 = new ArrayList<String>();
        headColumn8.add("lm_user");
        List<String> headColumn9 = new ArrayList<String>();
        headColumn9.add("lm_time");
        head.add(headColumn1);
        head.add(headColumn2);
        head.add(headColumn3);
        head.add(headColumn4);
        head.add(headColumn5);
        head.add(headColumn6);
        head.add(headColumn7);
        head.add(headColumn8);
        head.add(headColumn9);
        sheet.setHead(head);
        List<FacCapa> result = (List<FacCapa>) facCapaRepository.findAll(predicate);
        List<List<String>> data = new ArrayList<>(result.size());
        result.forEach(facCapa -> {
            List<String> row = new ArrayList<>();
            row.add(facCapa.getFacCapaId().getSite());
            row.add(facCapa.getFacCapaId().getFab());
            row.add(facCapa.getFacCapaId().getArea());
            row.add(facCapa.getFacCapaId().getShiftDate().toString());
            row.add(facCapa.getFacCapaId().getShift());
            row.add(facCapa.getFacCapaId().getScoreItem());
            row.add(facCapa.getItemValue());
            row.add(facCapa.getLmUser());
            row.add(facCapa.getLmTime() == null ? "" : facCapa.getLmTime().toString());
            data.add(row);
        });
        excelWriter.write0(data, sheet);
        excelWriter.finish();
    }

    public void update(FacCapa facCapa) {
        Optional<FacCapa> capa = facCapaRepository.findById(facCapa.getFacCapaId());
        capa.ifPresent(c -> {
            c.setItemValue(facCapa.getItemValue());
            c.setLmUser(UserUtils.currentUser().getEmployeeId());
            c.setLmTime(LocalDateTime.now());
            facCapaRepository.save(c);
        });
    }

    public void save(FacCapa facCapa) {
        facCapa.setLmUser(UserUtils.currentUser().getEmployeeId());
        facCapa.setLmTime(LocalDateTime.now());
        facCapaRepository.save(facCapa);
    }
}
