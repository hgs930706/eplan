package com.lcm.web.rest;

import auo.cim.uac.cli.jspbean.UacAuthority;
import com.lcm.domain.EqpCapa;
import com.lcm.domain.QEqpCapa;
import com.lcm.repository.EqpCapaRepository;
import com.lcm.service.EqpCapaService;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/eqpCapa")
public class EqpCapaController {

    private EqpCapaRepository  eqpCapaRepository;

    private EqpCapaService eqpCapaService;

    public EqpCapaController(EqpCapaRepository  eqpCapaRepository,EqpCapaService eqpCapaService){
        this.eqpCapaRepository = eqpCapaRepository;
        this.eqpCapaService = eqpCapaService;
    }

    @GetMapping("query")
    public ResponseEntity query(@QuerydslPredicate(root = EqpCapa.class) Predicate pre,
                                @PageableDefault Pageable pageable,
                                String site,String lineParam,String partNoParam){
        QEqpCapa query = QEqpCapa.eqpCapa;
        if(!StringUtils.isEmpty(site)) {
            pre = ExpressionUtils.and(pre, query.eqpCapaId.site.eq(site));
        }
        pre = ExpressionUtils.and(pre,query.eqpCapaId.line.like("%"+lineParam+"%"));
        pre = ExpressionUtils.and(pre,query.eqpCapaId.partNo.like("%"+partNoParam+"%"));
        Sort sort = Sort.by(Sort.Order.asc("eqpCapaId.site"), Sort.Order.asc("fab"), Sort.Order.asc("area"), Sort.Order.asc("eqpCapaId.line"));
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        return ResponseEntity.ok(eqpCapaRepository.findAll(pre, pageRequest));
    }

    @GetMapping("export")
    public ResponseEntity export(@QuerydslPredicate(root = EqpCapa.class) Predicate pre,String site,String lineParam,String partNoParam){
        QEqpCapa query = QEqpCapa.eqpCapa;
        if(!StringUtils.isEmpty(site)) {
            pre = ExpressionUtils.and(pre, query.eqpCapaId.site.eq(site));
        }
        pre = ExpressionUtils.and(pre,query.eqpCapaId.line.like("%"+lineParam+"%"));
        pre = ExpressionUtils.and(pre,query.eqpCapaId.partNo.like("%"+partNoParam+"%"));
        return ResponseEntity.ok(eqpCapaRepository.findAll(pre));
    }
    @PostMapping("add")
    public ResponseEntity add(@RequestBody EqpCapa eqpCapa, HttpSession session){
        UacAuthority UacAuthority  = (UacAuthority)session.getAttribute("user");
        eqpCapa.setLmUser(UacAuthority.getUserId());
        eqpCapa.setLmTime(LocalDateTime.now());
        return ResponseEntity.ok(eqpCapaRepository.save(eqpCapa));
    }

    @PostMapping("update")
    public ResponseEntity update(@RequestBody EqpCapa eqpCapa,HttpSession session){
        UacAuthority UacAuthority  = (UacAuthority)session.getAttribute("user");
        eqpCapa.setLmUser(UacAuthority.getUserId());
        eqpCapa.setLmTime(LocalDateTime.now());
        return ResponseEntity.ok(eqpCapaRepository.save(eqpCapa));
    }

    @PostMapping("delete")
    public ResponseEntity delete(@RequestBody List<EqpCapa> eqpCapaList){
        String str = eqpCapaService.deleteInBatch(eqpCapaList);
        return ResponseEntity.ok(str);
    }

    @PostMapping("upload")
    public ResponseEntity<String> upload( @RequestBody MultipartFile file){
        try {
            eqpCapaService.parseExcel(file);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok("success");
    }


}
