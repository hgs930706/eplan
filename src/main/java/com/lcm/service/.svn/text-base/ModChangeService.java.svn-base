package com.lcm.service;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.lcm.domain.ModChange;
import com.lcm.repository.ModChangeRepository;
import com.lcm.util.UserUtils;
import com.lcm.web.rest.errors.ExcelDataException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.transaction.annotation.Transactional;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class ModChangeService {

    private final ModChangeRepository modChangeRepository;

    public ModChangeService(ModChangeRepository modChangeRepository) {
        this.modChangeRepository = modChangeRepository;
    }

    @Transactional
    public void parseExcel(String site, MultipartFile file) throws Exception {
        try (InputStream inputStream = file.getInputStream()) {
            List<Object> rows = EasyExcelFactory.read(inputStream, new Sheet(1, 2));
            modChangeRepository.deleteBySite(site);
            List<ModChange> list = new ArrayList<>(rows.size());
            List<ModChange> noChangeKeyList = new ArrayList<>();
            String maxChangeKey = "";
            Map<String, Character> changeKeyCountMap = new HashMap<>();
            for (int i = 0; i < rows.size(); i++) {
                List<Object> cols = (List<Object>) rows.get(i);
                //此列没有单元格 || site单元格没有 || site单元格为空 跳过
                if (cols.size() == 0 || cols.get(0) == null || "".equals(((String) cols.get(0)).trim())) {
                    continue;
                }
                if (!site.equals(cols.get(0))) {
                    throw new ExcelDataException("第"+ (i + 2) + "行site与选择site不一致");
                }
                ModChange modChange = new ModChange();
                switch (cols.size()) {
                    case 43:
                        modChange.setAffectCapaQty4((String) cols.get(42));
                    case 42:
                        modChange.setAffectCapaPercent4((String) cols.get(41));
                    case 41:
                        modChange.setAffectCapaQty3((String) cols.get(40));
                    case 40:
                        modChange.setAffectCapaPercent3((String) cols.get(39));
                    case 39:
                        modChange.setAffectCapaQty2((String) cols.get(38));
                    case 38:
                        modChange.setAffectCapaPercent2((String) cols.get(37));
                    case 37:
                        modChange.setAffectCapaQty1((String) cols.get(36));
                    case 36:
                        modChange.setAffectCapaPercent1((String) cols.get(35));
                    case 35:
                        modChange.setChangeDuration((String) cols.get(34));
                    case 34:
                        modChange.setChangeLevel((String) cols.get(33));
                    case 33:
                        modChange.setToChangeGroup((String) cols.get(32));
                    case 32:
                        modChange.setToIsOverSixMonth((String) cols.get(31));
                    case 31:
                        modChange.setToTuffyType((String) cols.get(30));
                    case 30:
                        modChange.setToIsDemura((String) cols.get(29));
                    case 29:
                        modChange.setToIsBuildPcb((String) cols.get(28));
                    case 28:
                        modChange.setToPartsGroup((String) cols.get(27));
                    case 27:
                        modChange.setToPanelSizeGroup((String) cols.get(26));
                    case 26:
                        modChange.setToBarType((String) cols.get(25));
                    case 25:
                        modChange.setToPartNo((String) cols.get(24));
                    case 24:
                        modChange.setToPartLevel((String) cols.get(23));
                    case 23:
                        modChange.setToPanelSize((String) cols.get(22));
                    case 22:
                        modChange.setToModelType((String) cols.get(21));
                    case 21:
                        modChange.setToModelSite((String) cols.get(20));
                    case 20:
                        modChange.setFromChangeGroup((String) cols.get(19));
                    case 19:
                        modChange.setFromIsOverSixMonth((String) cols.get(18));
                    case 18:
                        modChange.setFromTuffyType((String) cols.get(17));
                    case 17:
                        modChange.setFromIsDemura((String) cols.get(16));
                    case 16:
                        modChange.setFromIsBuildPcb((String) cols.get(15));
                    case 15:
                        modChange.setFromPartsGroup((String) cols.get(14));
                    case 14:
                        modChange.setFromPanelSizeGroup((String) cols.get(13));
                    case 13:
                        modChange.setFromBarType((String) cols.get(12));
                    case 12:
                        modChange.setFromPartLevel((String) cols.get(11));
                    case 11:
                        modChange.setFromPartNo((String) cols.get(10));
                    case 10:
                        modChange.setFromPanelSize((String) cols.get(9));
                    case 9:
                        modChange.setFromModelType((String) cols.get(8));
                    case 8:
                        modChange.setFromModelSite((String) cols.get(7));
                    case 7:
                        modChange.setFromToReverse((String) cols.get(6));
                    case 6:
                        modChange.setPriority((String) cols.get(5));
                    case 5:
                        String changeKey = (String) cols.get(4);
                        //不符合changeKey格式按""处理
                        if (changeKey == null || !Pattern.matches(site + "_\\d{5}", changeKey)) {
                            changeKey = "";
                        }
                        modChange.setChangeKey(changeKey);
                    case 4:
                        modChange.setLine((String) cols.get(3));
                    case 3:
                        modChange.setArea((String) cols.get(2));
                    case 2:
                        modChange.setFab((String) cols.get(1));
                    case 1:
                        modChange.setSite((String) cols.get(0));
                }
                modChange.setLmUser(UserUtils.currentUser().getEmployeeId());
                modChange.setLmTime(LocalDateTime.now());
                if (!StringUtils.isEmpty(modChange.getChangeKey())) {
                    if (changeKeyCountMap.get(modChange.getChangeKey()) == null) {
                        changeKeyCountMap.put(modChange.getChangeKey(), '1');
                    } else {
                        throw new ExcelDataException("有重复的CHANGE_KEY！新增Rule请将CHANGE_KEY置空！");
                    }
                    if (modChange.getChangeKey().compareTo(maxChangeKey) > 0) {
                        maxChangeKey = modChange.getChangeKey();
                    }
                    list.add(modChange);
                } else {
                    noChangeKeyList.add(modChange);
                }
            }
            int index = 0;
            if (!StringUtils.isEmpty(maxChangeKey)) {
                index = Integer.valueOf(maxChangeKey.split("_")[1]);
            }
            for (int i = 0; i < noChangeKeyList.size(); i++) {
                noChangeKeyList.get(i).setChangeKey(site + "_" + String.format("%05d", i + 1 + index));
            }

            modChangeRepository.saveAll(list);
            modChangeRepository.saveAll(noChangeKeyList);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void exportExcel(String site, OutputStream outputStream) {
        XSSFWorkbook excel = new XSSFWorkbook();
        XSSFSheet sheet = excel.createSheet("Sheet1");
        //sheet.setColumnWidth(0, 150);
        XSSFColor red = new XSSFColor(new Color(255, 0, 0));
        XSSFColor blue = new XSSFColor(new Color(0, 176, 240));
        XSSFColor orange = new XSSFColor(new Color(250, 191, 143));
        XSSFColor white = new XSSFColor(new Color(255, 255, 255));
        XSSFColor yellow = new XSSFColor(new Color(255, 255, 0));
        XSSFColor black = new XSSFColor(new Color(0, 0, 0));
        short fontSize = 10;
        short fontSize1 = 12;

        XSSFRow headerInfo = sheet.createRow(0);
        headerInfo.setHeightInPoints(150);

        XSSFFont fontInfo3 = excel.createFont();
        fontInfo3.setBold(true);
        fontInfo3.setFontName("微软雅黑");
        fontInfo3.setFontHeightInPoints(fontSize);
        fontInfo3.setColor(black);
        XSSFCellStyle styleInfo3= excel.createCellStyle();
        styleInfo3.setAlignment(HorizontalAlignment.CENTER);
        styleInfo3.setVerticalAlignment(VerticalAlignment.CENTER);
        styleInfo3.setFillForegroundColor(yellow);
        styleInfo3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleInfo3.setFont(fontInfo3);
        styleInfo3.setWrapText(true);
        styleInfo3.setBorderTop(BorderStyle.THIN);
        styleInfo3.setBorderRight(BorderStyle.THIN);
        styleInfo3.setBorderBottom(BorderStyle.THIN);
        styleInfo3.setBorderLeft(BorderStyle.THIN);
        XSSFCell cellInfo4= headerInfo.createCell(4);
        cellInfo4.setCellValue("系统使用");
        cellInfo4.setCellStyle(styleInfo3);

        XSSFFont fontInfoAll = excel.createFont();
        fontInfoAll.setColor(red);
        XSSFCellStyle styleAll = excel.createCellStyle();
        styleAll.setAlignment(HorizontalAlignment.CENTER);
        styleAll.setVerticalAlignment(VerticalAlignment.CENTER);
        styleAll.setFillForegroundColor(white);
        styleAll.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleAll.setFont(fontInfoAll);
        styleAll.setWrapText(true);
        styleAll.setBorderTop(BorderStyle.THIN);
        styleAll.setBorderRight(BorderStyle.THIN);
        styleAll.setBorderLeft(BorderStyle.THIN);

        XSSFCell cellInfo0= headerInfo.createCell(0);
        cellInfo0.setCellValue("厂别必填，最长20码，同PIS2");
        cellInfo0.setCellStyle(styleAll);
        XSSFCell cellInfo1= headerInfo.createCell(1);
        cellInfo1.setCellValue("车间必填，最长20码，同PIS2，不区分车间用%通配");
        cellInfo1.setCellStyle(styleAll);
        XSSFCell cellInfo3= headerInfo.createCell(3);
        cellInfo3.setCellValue("区域必填，最长20码，同PIS2，不区分区域用%通配");
        cellInfo3.setCellStyle(styleAll);
        XSSFCell cellInfo2= headerInfo.createCell(2);
        cellInfo2.setCellValue("线别必填，最长20码，同PIS2，，不区分线别用%通配");
        cellInfo2.setCellStyle(styleAll);
        XSSFCell cellInfo5= headerInfo.createCell(5);
        cellInfo5.setCellValue("优先级别，必填，数字，数字小优先");
        cellInfo5.setCellStyle(styleAll);
        XSSFCell cellInfo6= headerInfo.createCell(6);
        cellInfo6.setCellValue("反向判断（Y/N）");
        cellInfo6.setCellStyle(styleAll);
        XSSFCell cellInfo7= headerInfo.createCell(7);
        cellInfo7.setCellValue("型号厂别必填，最长20码，不区分则用%通配");
        cellInfo7.setCellStyle(styleAll);
        XSSFCell cellInfo8= headerInfo.createCell(8);
        cellInfo8.setCellValue("型号类型，必填，[DIFF]/[SAME]或自定义，最长40码，不区分则用%通配");
        cellInfo8.setCellStyle(styleAll);
        XSSFCell cellInfo9= headerInfo.createCell(9);
        cellInfo9.setCellValue("产品尺寸，[DIFF]/[SAME]，或自定义，最长20码，不区分则用%通配");
        cellInfo9.setCellStyle(styleAll);
        XSSFCell cellInfo10= headerInfo.createCell(10);
        cellInfo10.setCellValue("产品料号，必填，最长40码，不区分则用%通配");
        cellInfo10.setCellStyle(styleAll);
        XSSFCell cellInfo11= headerInfo.createCell(11);
        cellInfo11.setCellValue("产品料阶（最长20码，不区分则用%通配）");
        cellInfo11.setCellStyle(styleAll);
        XSSFCell cellInfo12= headerInfo.createCell(12);
        cellInfo12.setCellValue("产品Bar类型（最长10码，不区分则用%通配）");
        cellInfo12.setCellStyle(styleAll);
        XSSFCell cellInfo13= headerInfo.createCell(13);
        cellInfo13.setCellValue("产品尺寸群组(最长10码，不区分则用%通配，如：MIN/NB)");
        cellInfo13.setCellStyle(styleAll);
        XSSFCell cellInfo14= headerInfo.createCell(14);
        cellInfo14.setCellValue("治具群组（最长20码，，不区分则用%通配）");
        cellInfo14.setCellStyle(styleAll);
        XSSFCell cellInfo15= headerInfo.createCell(15);
        cellInfo15.setCellValue("是否带PCB板（Y/N，不区分则用%通配）");
        cellInfo15.setCellStyle(styleAll);
        XSSFCell cellInfo16= headerInfo.createCell(16);
        cellInfo16.setCellValue("产品是否需要Demura（Y/N，，不区分则用%通配）");
        cellInfo16.setCellStyle(styleAll);
        XSSFCell cellInfo17= headerInfo.createCell(17);
        cellInfo17.setCellValue("产品是否使用超粘胶（Y/N，，不区分则用%通配）");
        cellInfo17.setCellStyle(styleAll);
        XSSFCell cellInfo18= headerInfo.createCell(18);
        cellInfo18.setCellValue("产品是否大于六个月未投产（Y/N，，不区分则用%通配）");
        cellInfo18.setCellStyle(styleAll);
        XSSFCell cellInfo19= headerInfo.createCell(19);
        cellInfo19.setCellValue("换线群组（不区分则用%通配）");
        cellInfo19.setCellStyle(styleAll);
        XSSFCell cellInfo20= headerInfo.createCell(20);
        cellInfo20.setCellValue("型号厂别必填，最长20码，不区分则用%通配");
        cellInfo20.setCellStyle(styleAll);
        XSSFCell cellInfo21= headerInfo.createCell(21);
        cellInfo21.setCellValue("型号类型，必填，[DIFF]/[SAME]或自定义，最长40码，不区分则用%通配");
        cellInfo21.setCellStyle(styleAll);
        XSSFCell cellInfo22= headerInfo.createCell(22);
        cellInfo22.setCellValue("产品尺寸，[DIFF]/[SAME]，或自定义，最长20码，不区分则用%通配");
        cellInfo22.setCellStyle(styleAll);
        XSSFCell cellInfo23= headerInfo.createCell(23);
        cellInfo23.setCellValue("产品料号，必填，最长40码，不区分则用%通配");
        cellInfo23.setCellStyle(styleAll);
        XSSFCell cellInfo24= headerInfo.createCell(24);
        cellInfo24.setCellValue("产品料阶（最长20码，不区分则用%通配） ");
        cellInfo24.setCellStyle(styleAll);
        XSSFCell cellInfo25= headerInfo.createCell(25);
        cellInfo25.setCellValue("产品Bar类型（最长10码，不区分则用%通配）");
        cellInfo25.setCellStyle(styleAll);
        XSSFCell cellInfo26= headerInfo.createCell(26);
        cellInfo26.setCellValue("产品尺寸群组(最长10码，不区分则用%通配，如：MIN/NB)");
        cellInfo26.setCellStyle(styleAll);
        XSSFCell cellInfo27= headerInfo.createCell(27);
        cellInfo27.setCellValue("治具群组（最长20码，，不区分则用%通配）");
        cellInfo27.setCellStyle(styleAll);
        XSSFCell cellInfo28= headerInfo.createCell(28);
        cellInfo28.setCellValue("是否带PCB板（Y/N，不区分则用%通配）");
        cellInfo28.setCellStyle(styleAll);
        XSSFCell cellInfo29= headerInfo.createCell(29);
        cellInfo29.setCellValue("产品是否需要Demura（Y/N，，不区分则用%通配）");
        cellInfo29.setCellStyle(styleAll);
        XSSFCell cellInfo30= headerInfo.createCell(30);
        cellInfo30.setCellValue("产品是否使用超粘胶（Y/N，，不区分则用%通配）");
        cellInfo30.setCellStyle(styleAll);
        XSSFCell cellInfo31= headerInfo.createCell(31);
        cellInfo31.setCellValue("产品是否大于六个月未投产（Y/N，，不区分则用%通配）");
        cellInfo31.setCellStyle(styleAll);
        XSSFCell cellInfo32= headerInfo.createCell(32);
        cellInfo32.setCellValue("换线群组（不区分则用%通配）");
        cellInfo32.setCellStyle(styleAll);
        XSSFCell cellInfo33= headerInfo.createCell(33);
        cellInfo33.setCellValue("换线等级（必填，最长10码）");
        cellInfo33.setCellStyle(styleAll);
        XSSFCell cellInfo34= headerInfo.createCell(34);
        cellInfo34.setCellValue("换线时间（必填，数字，单位小时）");
        cellInfo34.setCellStyle(styleAll);
        XSSFCell cellInfo35= headerInfo.createCell(35);
        cellInfo35.setCellValue("换线当班摆量比例（数字0~1，默认值1）");
        cellInfo35.setCellStyle(styleAll);
        XSSFCell cellInfo36= headerInfo.createCell(36);
        cellInfo36.setCellValue("换线当班摆量片数（整数，默认0）");
        cellInfo36.setCellStyle(styleAll);
        XSSFCell cellInfo37= headerInfo.createCell(37);
        cellInfo37.setCellValue("换线第二个班摆量比例（数字0~1，默认值1）");
        cellInfo37.setCellStyle(styleAll);
        XSSFCell cellInfo38= headerInfo.createCell(38);
        cellInfo38.setCellValue("换线第二个班摆量片数（整数，默认0）");
        cellInfo38.setCellStyle(styleAll);
        XSSFCell cellInfo39= headerInfo.createCell(39);
        cellInfo39.setCellValue("换线第三个班摆量比例（数字0~1，默认值1）");
        cellInfo39.setCellStyle(styleAll);
        XSSFCell cellInfo40= headerInfo.createCell(40);
        cellInfo40.setCellValue("换线第三个班摆量片数（整数，默认0）");
        cellInfo40.setCellStyle(styleAll);
        XSSFCell cellInfo41= headerInfo.createCell(41);
        cellInfo41.setCellValue("换线第四个班摆量比例（数字0~1，默认值1）");
        cellInfo41.setCellStyle(styleAll);
        XSSFCell cellInfo42= headerInfo.createCell(42);
        cellInfo42.setCellValue("换线第四个班摆量片数（整数，默认0）");
        cellInfo42.setCellStyle(styleAll);


        XSSFRow header = sheet.createRow(1);
        header.setHeightInPoints(66);
        XSSFCell cell0 = header.createCell(0);
        cell0.setCellValue("Site");
        XSSFFont font = excel.createFont();
        font.setBold(true);
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints(fontSize);
        font.setColor(white);
        XSSFCellStyle cellStyle = excel.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFillForegroundColor(blue);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cell0.setCellStyle(cellStyle);
        XSSFCell cell1 = header.createCell(1);
        cell1.setCellValue("Fab");
        cell1.setCellStyle(cellStyle);
        XSSFCell cell2 = header.createCell(2);
        cell2.setCellValue("AREA");
        cell2.setCellStyle(cellStyle);
        XSSFCell cell3 = header.createCell(3);
        cell3.setCellValue("Line");
        cell3.setCellStyle(cellStyle);
        XSSFCell cell4 = header.createCell(4);
        XSSFCellStyle cellStyle1 = excel.createCellStyle();
        cellStyle1.setAlignment(HorizontalAlignment.CENTER);
        cellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle1.setFillForegroundColor(yellow);
        cellStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont font1 = excel.createFont();
        font1.setBold(true);
        font1.setFontName("微软雅黑");
        font1.setFontHeightInPoints(fontSize);
        font1.setColor(black);
        cellStyle1.setFont(font1);
        cellStyle1.setWrapText(true);
        cellStyle1.setBorderTop(BorderStyle.THIN);
        cellStyle1.setBorderRight(BorderStyle.THIN);
        cellStyle1.setBorderBottom(BorderStyle.THIN);
        cellStyle1.setBorderLeft(BorderStyle.THIN);
        cell4.setCellValue("CHANGE_KEY");
        cell4.setCellStyle(cellStyle1);
        XSSFCell cell5 = header.createCell(5);
        cell5.setCellValue("priority");
        cell5.setCellStyle(cellStyle);
        XSSFCell cell6 = header.createCell(6);
        cell6.setCellValue("from_to_Reverse");
        cell6.setCellStyle(cellStyle);
        XSSFCell cell7 = header.createCell(7);
        cell7.setCellValue("from_model_site");
        cell7.setCellStyle(cellStyle);
        XSSFCell cell8 = header.createCell(8);
        cell8.setCellValue("from_model_type");
        cell8.setCellStyle(cellStyle);
        XSSFCell cell9 = header.createCell(9);
        cell9.setCellValue("from_panel_Size");
        cell9.setCellStyle(cellStyle);
        XSSFCell cell10 = header.createCell(10);
        cell10.setCellValue("from_part_no");
        cell10.setCellStyle(cellStyle);
        XSSFCell cell11 = header.createCell(11);
        cell11.setCellValue("from_Part_level");
        cell11.setCellStyle(cellStyle);
        XSSFCell cell12 = header.createCell(12);
        cell12.setCellValue("from_Bar_Type");
        cell12.setCellStyle(cellStyle);
        XSSFCell cell13 = header.createCell(13);
        cell13.setCellValue("from_panel_size_group");
        cell13.setCellStyle(cellStyle);
        XSSFCell cell14 = header.createCell(14);
        cell14.setCellValue("from_parts_group");
        cell14.setCellStyle(cellStyle);
        XSSFCell cell15 = header.createCell(15);
        cell15.setCellValue("from_is_build_pcb");
        cell15.setCellStyle(cellStyle);
        XSSFCell cell16 = header.createCell(16);
        cell16.setCellValue("from_is_demura");
        cell16.setCellStyle(cellStyle);
        XSSFCell cell17 = header.createCell(17);
        cell17.setCellValue("from_tuffy_type");
        cell17.setCellStyle(cellStyle);
        XSSFCell cell18 = header.createCell(18);
        cell18.setCellValue("from_is_over_six_month");
        cell18.setCellStyle(cellStyle);
        XSSFCell cell19 = header.createCell(19);
        cell19.setCellValue("from_change_group");
        cell19.setCellStyle(cellStyle);
        XSSFCell cell20 = header.createCell(20);
        XSSFCellStyle cellStyle2 = excel.createCellStyle();
        cellStyle2.setAlignment(HorizontalAlignment.CENTER);
        cellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle2.setFillForegroundColor(orange);
        cellStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont font2 = excel.createFont();
        font2.setBold(true);
        font2.setFontName("微软雅黑");
        font2.setFontHeightInPoints(fontSize);
        font2.setColor(black);
        cellStyle2.setFont(font2);
        cellStyle2.setWrapText(true);
        cellStyle2.setBorderTop(BorderStyle.THIN);
        cellStyle2.setBorderRight(BorderStyle.THIN);
        cellStyle2.setBorderBottom(BorderStyle.THIN);
        cellStyle2.setBorderLeft(BorderStyle.THIN);
        cell20.setCellValue("to_model_site");
        cell20.setCellStyle(cellStyle2);
        XSSFCell cell21 = header.createCell(21);
        cell21.setCellValue("to_model_type");
        cell21.setCellStyle(cellStyle2);
        XSSFCell cell22 = header.createCell(22);
        cell22.setCellValue("to_panel_Size");
        cell22.setCellStyle(cellStyle2);
        XSSFCell cell23 = header.createCell(23);
        cell23.setCellValue("to_Part_level");
        cell23.setCellStyle(cellStyle2);
        XSSFCell cell24 = header.createCell(24);
        cell24.setCellValue("to_part_no");
        cell24.setCellStyle(cellStyle2);
        XSSFCell cell25 = header.createCell(25);
        cell25.setCellValue("to_Bar_Type");
        cell25.setCellStyle(cellStyle2);
        XSSFCell cell26 = header.createCell(26);
        cell26.setCellValue("to_panel_size_group");
        cell26.setCellStyle(cellStyle2);
        XSSFCell cell27 = header.createCell(27);
        cell27.setCellValue("to_parts_group");
        cell27.setCellStyle(cellStyle2);
        XSSFCell cell28 = header.createCell(28);
        cell28.setCellValue("to_is_build_pcb");
        cell28.setCellStyle(cellStyle2);
        XSSFCell cell29 = header.createCell(29);
        cell29.setCellValue("to_is_demura");
        cell29.setCellStyle(cellStyle2);
        XSSFCell cell30 = header.createCell(30);
        cell30.setCellValue("to_tuffy_type");
        cell30.setCellStyle(cellStyle2);
        XSSFCell cell31 = header.createCell(31);
        cell31.setCellValue("to_is_over_six_month");
        cell31.setCellStyle(cellStyle2);
        XSSFCell cell32 = header.createCell(32);
        cell32.setCellValue("to_change_group ");
        cell32.setCellStyle(cellStyle2);
        XSSFCell cell33 = header.createCell(33);
        XSSFCellStyle cellStyle3 = excel.createCellStyle();
        cellStyle3.setAlignment(HorizontalAlignment.CENTER);
        cellStyle3.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle3.setFillForegroundColor(white);
        cellStyle3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle3.setBorderTop(BorderStyle.THIN);
        cellStyle3.setBorderRight(BorderStyle.THIN);
        cellStyle3.setBorderBottom(BorderStyle.THIN);
        cellStyle3.setBorderLeft(BorderStyle.THIN);
        XSSFFont font3 = excel.createFont();
        font3.setBold(false);
        font3.setFontName("宋体 (正文)");
        font3.setFontHeightInPoints(fontSize1);
        font3.setColor(black);
        cellStyle3.setFont(font3);
        cellStyle3.setWrapText(true);
        cell33.setCellValue("CHANGE_LEVEL");
        cell33.setCellStyle(cellStyle3);
        XSSFCell cell34 = header.createCell(34);
        cell34.setCellValue("CHANGE_DURATION");
        cell34.setCellStyle(cellStyle3);
        XSSFCell cell35 = header.createCell(35);
        cell35.setCellValue("AFFECT_CAPA_PERCENT_1");
        cell35.setCellStyle(cellStyle3);
        XSSFCell cell36 = header.createCell(36);
        cell36.setCellValue("AFFECT_CAPA_QTY_1");
        cell36.setCellStyle(cellStyle3);
        XSSFCell cell37 = header.createCell(37);
        cell37.setCellValue("AFFECT_CAPA_PERCENT_2");
        cell37.setCellStyle(cellStyle3);
        XSSFCell cell38 = header.createCell(38);
        cell38.setCellValue("AFFECT_CAPA_QTY_2");
        cell38.setCellStyle(cellStyle3);
        XSSFCell cell39 = header.createCell(39);
        cell39.setCellValue("AFFECT_CAPA_PERCENT_3");
        cell39.setCellStyle(cellStyle3);
        XSSFCell cell40 = header.createCell(40);
        cell40.setCellValue("AFFECT_CAPA_QTY_3");
        cell40.setCellStyle(cellStyle3);
        XSSFCell cell41 = header.createCell(41);
        cell41.setCellValue("AFFECT_CAPA_PERCENT_4");
        cell41.setCellStyle(cellStyle3);
        XSSFCell cell42 = header.createCell(42);
        cell42.setCellValue("AFFECT_CAPA_QTY_4");
        cell42.setCellStyle(cellStyle3);


        List<ModChange> list = modChangeRepository.findBySiteOrderByChangeKeyAsc(site);
        for (int i = 0; i < list.size(); i++) {
            ModChange mod = list.get(i);
            XSSFRow row = sheet.createRow(i + 2);
            row.createCell(0).setCellValue(mod.getSite());
            row.createCell(1).setCellValue(mod.getFab());
            row.createCell(2).setCellValue(mod.getArea());
            row.createCell(3).setCellValue(mod.getLine());
            row.createCell(4).setCellValue(mod.getChangeKey());
            row.createCell(5).setCellValue(mod.getPriority());
            row.createCell(6).setCellValue(mod.getFromToReverse());
            row.createCell(7).setCellValue(mod.getFromModelSite());
            row.createCell(8).setCellValue(mod.getFromModelType());
            row.createCell(9).setCellValue(mod.getFromPanelSize());
            row.createCell(10).setCellValue(mod.getFromPartNo());
            row.createCell(11).setCellValue(mod.getFromPartLevel());
            row.createCell(12).setCellValue(mod.getFromBarType());
            row.createCell(13).setCellValue(mod.getFromPanelSizeGroup());
            row.createCell(14).setCellValue(mod.getFromPartsGroup());
            row.createCell(15).setCellValue(mod.getFromIsBuildPcb());
            row.createCell(16).setCellValue(mod.getFromIsDemura());
            row.createCell(17).setCellValue(mod.getFromTuffyType());
            row.createCell(18).setCellValue(mod.getFromIsOverSixMonth());
            row.createCell(19).setCellValue(mod.getFromChangeGroup());
            row.createCell(20).setCellValue(mod.getToModelSite());
            row.createCell(21).setCellValue(mod.getToModelType());
            row.createCell(22).setCellValue(mod.getToPanelSize());
            row.createCell(23).setCellValue(mod.getToPartLevel());
            row.createCell(24).setCellValue(mod.getToPartNo());
            row.createCell(25).setCellValue(mod.getToBarType());
            row.createCell(26).setCellValue(mod.getToPanelSizeGroup());
            row.createCell(27).setCellValue(mod.getToPartsGroup());
            row.createCell(28).setCellValue(mod.getToIsBuildPcb());
            row.createCell(29).setCellValue(mod.getToIsDemura());
            row.createCell(30).setCellValue(mod.getToTuffyType());
            row.createCell(31).setCellValue(mod.getToIsOverSixMonth());
            row.createCell(32).setCellValue(mod.getToChangeGroup());
            row.createCell(33).setCellValue(mod.getChangeLevel());
            row.createCell(34).setCellValue(mod.getChangeDuration());
            row.createCell(35).setCellValue(mod.getAffectCapaPercent1());
            row.createCell(36).setCellValue(mod.getAffectCapaQty1());
            row.createCell(37).setCellValue(mod.getAffectCapaPercent2());
            row.createCell(38).setCellValue(mod.getAffectCapaQty2());
            row.createCell(39).setCellValue(mod.getAffectCapaPercent3());
            row.createCell(40).setCellValue(mod.getAffectCapaQty3());
            row.createCell(41).setCellValue(mod.getAffectCapaPercent4());
            row.createCell(42).setCellValue(mod.getAffectCapaQty4());
        }
        try {
            excel.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
