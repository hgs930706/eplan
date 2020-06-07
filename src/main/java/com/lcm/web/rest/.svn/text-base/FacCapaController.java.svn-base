package com.lcm.web.rest;

import com.lcm.domain.FacCapa;
import com.lcm.repository.FacCapaRepository;
import com.lcm.service.FacCapaService;
import com.lcm.web.rest.param.FacCapaParam;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/capa")
@RestController
public class FacCapaController {

    private final FacCapaService facCapaService;
    private final FacCapaRepository facCapaRepository;

    public FacCapaController(FacCapaService facCapaService, FacCapaRepository facCapaRepository) {
        this.facCapaService = facCapaService;
        this.facCapaRepository = facCapaRepository;
    }

    @GetMapping("")
    public ResponseEntity<Page<FacCapa>> index(@QuerydslPredicate(root = FacCapa.class) Predicate predicate,
                                                                      @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(facCapaRepository.findAll(predicate, pageable));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestBody MultipartFile file) {
        try {
            facCapaService.parseExcel(file);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok("success");
    }

    @PutMapping("")
    public ResponseEntity<String> update(FacCapa facCapa) {
        facCapaService.update(facCapa);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody FacCapaParam facCapaParam) {
        List<FacCapa> facCapas = facCapaParam.getFacCapaIds().stream().map(id -> {
            FacCapa facCapa = new FacCapa();
            facCapa.setFacCapaId(id);
            return facCapa;
        }).collect(Collectors.toList());
        facCapaRepository.deleteInBatch(facCapas);
        return ResponseEntity.ok("success");
    }

    @PostMapping("")
    public ResponseEntity<String> save(@RequestBody FacCapa facCapa) {
        facCapaService.save(facCapa);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/export")
    public void exportExcel(@QuerydslPredicate(root = FacCapa.class) Predicate predicate, HttpServletResponse response) {
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileName = "Special";
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+fileName+".xlsx");
        facCapaService.exportExcel(predicate, out);
    }
}
