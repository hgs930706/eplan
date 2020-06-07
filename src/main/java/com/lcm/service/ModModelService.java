package com.lcm.service;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.lcm.domain.ModModel;
import com.lcm.repository.ModModelRepository;
import com.querydsl.core.types.Predicate;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ModModelService {
    private static final Logger LOGGER = LoggerFactory.logger(ModModelService.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final ModModelRepository modModelRepository;

    public ModModelService(ModModelRepository modModelRepository){
        this.modModelRepository = modModelRepository;
    }

    public void exportExcel(Predicate predicate, OutputStream outputStream) {
        ExcelWriter writer = EasyExcelFactory.getWriter(outputStream);
        Sheet sheet = new Sheet(1,0);
        sheet.setSheetName("ModModel");
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
        columnWidth.put(17,25*256);
        columnWidth.put(18,25*256);
        columnWidth.put(19,25*256);
        sheet.setColumnWidthMap(columnWidth);
        sheet.setAutoWidth(true);
        List<List<String>> head = new ArrayList<List<String>>();
        List<String> headColumn1 = new ArrayList<String>();
        headColumn1.add("site");
        List<String> headColumn2 = new ArrayList<String>();
        headColumn2.add("part_no");
        List<String> headColumn3 = new ArrayList<String>();
        headColumn3.add("model_no");
        List<String> headColumn4 = new ArrayList<String>();
        headColumn4.add("model_site");
        List<String> headColumn5 = new ArrayList<String>();
        headColumn5.add("model_type");
        List<String> headColumn6 = new ArrayList<String>();
        headColumn6.add("model_ext");
        List<String> headColumn7 = new ArrayList<String>();
        headColumn7.add("model_ver");
        List<String> headColumn8 = new ArrayList<String>();
        headColumn8.add("panel_size");
        List<String> headColumn9 = new ArrayList<String>();
        headColumn9.add("bar_type");
        List<String> headColumn10 = new ArrayList<String>();
        headColumn10.add("panel_size_group");
        List<String> headColumn11 = new ArrayList<String>();
        headColumn11.add("parts_group");
        List<String> headColumn12 = new ArrayList<String>();
        headColumn12.add("is_build_pcb");
        List<String> headColumn13 = new ArrayList<String>();
        headColumn13.add("is_demura");
        List<String> headColumn14 = new ArrayList<String>();
        headColumn14.add("tuffy_type");
        List<String> headColumn15 = new ArrayList<String>();
        headColumn15.add("last_trackout_time");
        List<String> headColumn16 = new ArrayList<String>();
        headColumn16.add("color");
        List<String> headColumn17 = new ArrayList<String>();
        headColumn17.add("priority");
        List<String> headColumn18 = new ArrayList<String>();
        headColumn18.add("change_group");
        List<String> headColumn19 = new ArrayList<String>();
        headColumn19.add("lm_user");
        List<String> headColumn20 = new ArrayList<String>();
        headColumn20.add("lm_time");
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
        head.add(headColumn18);
        head.add(headColumn19);
        head.add(headColumn20);
        sheet.setHead(head);
        List<ModModel> list = (List<ModModel>)modModelRepository.findAll(predicate);
        LOGGER.info("list:"+list.size());
        List<List<String>> data = new ArrayList<>(list.size());
        for(ModModel modModel : list){
            List<String> row = new ArrayList<>();
            row.add(modModel.getModelId().getSite());
            row.add(modModel.getModelId().getPartNo());
            row.add(modModel.getModelNo());
            row.add(modModel.getModelSite());
            row.add(modModel.getModelType());
            row.add(modModel.getModelExt());
            row.add(modModel.getModelVer());
            row.add(modModel.getPanelSize());
            row.add(modModel.getBarType());
            row.add(modModel.getPanelSizeGroup());
            row.add(modModel.getPartsGroup());
            row.add(modModel.getIsBuildPcb());
            row.add(modModel.getIsDemura());
            row.add(modModel.getTuffyType());
            row.add(modModel.getLastTrackoutTime()==null ? "":DATE_FORMATTER.format(modModel.getLastTrackoutTime()));
            row.add(modModel.getColor());
            row.add(modModel.getPriority());
            row.add(modModel.getChangeGroup());
            row.add(modModel.getLmUser());
            row.add(modModel.getLmTime() == null ? "" : DATE_FORMATTER.format(modModel.getLmTime()));

            data.add(row);
        }
        writer.write0(data, sheet);
        writer.finish();

    }

}
