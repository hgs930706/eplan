package com.lcm.service;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.lcm.domain.EqpCapa;
import com.lcm.domain.EqpCapaId;
import com.lcm.domain.ModModel;
import com.lcm.domain.ModelId;
import com.lcm.repository.EqpCapaRepository;
import com.lcm.repository.ModModelRepository;
import com.lcm.util.UserUtils;
import com.lcm.web.rest.errors.ExcelDataException;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.transaction.annotation.Transactional;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EqpCapaService {
    private static final Logger LOGGER = LoggerFactory.logger(EqpCapaService.class);
    private final EqpCapaRepository eqpCapaRepository;
    private final ModModelRepository modModelRepository;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:dd");

    public EqpCapaService(EqpCapaRepository eqpCapaRepository,ModModelRepository modModelRepository){
        this.modModelRepository = modModelRepository;
        this.eqpCapaRepository = eqpCapaRepository;
    }

    @Transactional
    public String deleteInBatch(List<EqpCapa> eqpCapaList){
        eqpCapaRepository.deleteInBatch(eqpCapaList);
        return "success";

    }

    @Transactional
    public void parseExcel(MultipartFile file) throws Exception{
        try(InputStream inputStream = file.getInputStream()) {

//           String name = file.getOriginalFilename();
//           if("CAPA template.xlsx".equals(name)){
            //InputStream inputStream = file.getInputStream();
                List<Object> rows = EasyExcelFactory.read(inputStream, new Sheet(1, 0));
                List<EqpCapa> list =new ArrayList<EqpCapa>();
                String employeeId = UserUtils.currentUser().getEmployeeId();
                for (int i= 2; i< rows.size();i++){
                    EqpCapa capa = new EqpCapa();
                    EqpCapaId eqpCapaId  = new EqpCapaId();

                    List<Object> row = (List<Object>)rows.get(i);
                    LOGGER.info("row:"+row.toString());//[S06, 3B, JI, S063BJI01, B125HAN02.2, 91.07B04.602, 2000, 2200]
                    String site = (String)row.get(0);
                    if(org.apache.commons.lang3.StringUtils.isBlank(site)){
                        break;
                    }
                    String fab = (String)row.get(1);
                    String area = (String)row.get(2);
                    String line = (String)row.get(3);
                    String partNo = (String)row.get(5);
                   // String modelNo = (String)row.get(4);
                    String ppcCapa = (String)row.get(6);
                    String fabPcCapa = (String)row.get(7);
                    if(StringUtils.isEmpty(site) || StringUtils.isEmpty(fab) || StringUtils.isEmpty(area) || StringUtils.isEmpty(line)
                            || StringUtils.isEmpty(partNo) || StringUtils.isEmpty(ppcCapa) || StringUtils.isEmpty(fabPcCapa)){
                        throw new ExcelDataException("第" + (i+1) + "行必填，实际为空。");
                    }

                    ModelId modelId = new ModelId();
                        modelId.setSite(site);
                        modelId.setPartNo(partNo);
                    Optional<ModModel> m = modModelRepository.findById(modelId);
                    if(!m.isPresent()) {
                        LOGGER.warn("第" + (i + 1) + "行错误,找不到对应的PART_NO");
                        throw new ExcelDataException("第" + (i + 1) + "行错误,找不到对应的PART_NO");
                    }
                    capa.setModelNo(m.get().getModelNo());
                    capa.setFab(fab);
                    capa.setArea(area);
                    eqpCapaId.setLine(line);
                    eqpCapaId.setPartNo(partNo);
                    eqpCapaId.setSite(site);
                    capa.setEqpCapaId(eqpCapaId);
                    try {
                        capa.setPpcCapa(Integer.parseInt(ppcCapa));
                        capa.setFabPcCapa(Integer.parseInt(fabPcCapa));
                    }catch (NumberFormatException e){
                        throw new ExcelDataException("第" + (i+1) + "行、第7列或第8列不是数字。");
                    }
                    capa.setLmUser(employeeId);
                    capa.setLmTime(LocalDateTime.now());
                    list.add(capa);
                }
                eqpCapaRepository.saveAll(list);
//            }else{
//               throw new ExcelDataException("错误模板。");
//           }
        } catch (Exception e) {
            throw e;
        }
    }


}
