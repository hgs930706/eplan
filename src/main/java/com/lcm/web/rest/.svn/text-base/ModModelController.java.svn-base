package com.lcm.web.rest;

import com.lcm.domain.ModModel;
import com.lcm.domain.ModelId;
import com.lcm.domain.QModModel;
import com.lcm.repository.ModModelRepository;
import com.lcm.service.ModModelService;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/modModel")
@RestController
public class ModModelController {
    private static final Logger LOGGER = LoggerFactory.logger(ModModelController.class);
    private final ModModelRepository modModelRepository;

    private final ModModelService modModelService;

    public ModModelController(ModModelRepository modModelRepository,ModModelService modModelService) {
        this.modModelRepository = modModelRepository;
        this.modModelService = modModelService;
    }

    @GetMapping("")
    public ResponseEntity<ModModel> findOne(ModelId modelId) {
        return ResponseEntity.ok(modModelRepository.findById(modelId).orElseGet(() ->new ModModel()));
    }

    @GetMapping("/partNos")
    public ResponseEntity<List<String>> fabs(String site) {
        return ResponseEntity.ok(modModelRepository.findDistinctPartNoBySite(site));
    }

    @GetMapping("query")
    public ResponseEntity<Page<ModModel>> query(@QuerydslPredicate(root = ModModel.class) Predicate predicate,
                                               @PageableDefault Pageable pageable,String site,String partNo,String modelNoParam) {
        QModModel query = QModModel.modModel;
        if(!StringUtils.isEmpty(site)){
            predicate = ExpressionUtils.and(predicate,query.modelId.site.eq(site));
        }

            predicate = ExpressionUtils.and(predicate,query.modelId.partNo.like("%" + partNo + "%"));
            predicate = ExpressionUtils.and(predicate,query.modelNo.like("%" + modelNoParam + "%"));

        List<Sort.Order> orders = new ArrayList<Sort.Order>();
            orders.add(Sort.Order.desc("modelId.site"));
            orders.add(Sort.Order.desc("modelId.partNo"));
            orders.add(Sort.Order.desc("modelNo"));

        return ResponseEntity.ok(modModelRepository.findAll(predicate, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders))));
    }

    @PostMapping("update")
    public ResponseEntity update(@RequestBody ModModel modModel){
        LOGGER.info("111:"+ modModel.getModelNo());
        return ResponseEntity.ok( modModelRepository.save(modModel));
    }

    @GetMapping("/export")
    public void exportExcel(@QuerydslPredicate(root = ModModel.class) Predicate predicate, HttpServletResponse response
                            ,String site,String partNo,String modelNoParam) {
       // OutputStream out = new FileOutputStream("E://a.xls");
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        }catch (IOException e) {
            e.printStackTrace();
        }
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=ModModel.xlsx");

        QModModel query = QModModel.modModel;
        if(!StringUtils.isEmpty(site)){
            predicate = ExpressionUtils.and(predicate,query.modelId.site.eq(site));
        }
        predicate = ExpressionUtils.and(predicate,query.modelId.partNo.like("%" + partNo + "%"));
        predicate = ExpressionUtils.and(predicate,query.modelNo.like("%" + modelNoParam + "%"));

        modModelService.exportExcel(predicate, out);
    }

}
