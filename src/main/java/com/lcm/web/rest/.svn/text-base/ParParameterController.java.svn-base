package com.lcm.web.rest;

import com.lcm.domain.ParParameter;
import com.lcm.domain.QParParameter;
import com.lcm.domain.dto.PartNoDto;
import com.lcm.repository.ParParameterRepository;
import com.lcm.service.ParParameterService;
import com.lcm.util.TableUtils;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/parameter")
@RestController
public class ParParameterController {

    private final ParParameterRepository parParameterRepository;
    private final ParParameterService parParameterService;

    public ParParameterController(ParParameterRepository parParameterRepository, ParParameterService parParameterService) {
        this.parParameterRepository = parParameterRepository;
        this.parParameterService = parParameterService;
    }

    @GetMapping("")
    public ResponseEntity<Page<ParParameter>> index(@QuerydslPredicate(root = ParParameter.class) Predicate predicate, String item,
                                            @PageableDefault Pageable pageable) {
        if (!StringUtils.isEmpty(item)) {
            predicate = ExpressionUtils.and(predicate, QParParameter.parParameter.itemName.like("%" + item + "%"));
        }
        return ResponseEntity.ok(parParameterRepository.findAll(predicate, pageable));
    }

    @PutMapping("")
    public ResponseEntity<String> update(ParParameter parParameter) {
        parParameterService.update(parParameter);
        return ResponseEntity.ok("success");
    }

    @DeleteMapping("")
    public ResponseEntity<String> delete(@RequestParam String ids) {
        List<ParParameter> pars = Arrays.asList(ids.split(",")).stream().map(id -> {
            ParParameter par = new ParParameter();
            par.setSeq(id);
            return par;
        }).collect(Collectors.toList());
        parParameterRepository.deleteInBatch(pars);
        return ResponseEntity.ok("success");
    }

    @PostMapping("")
    public ResponseEntity<String> save(@RequestBody ParParameter parParameter) {
        parParameterService.save(parParameter);
        return ResponseEntity.ok("success");
    }

    @GetMapping("partNo")
    public ResponseEntity getPartNo(String site){
        PartNoDto p = new PartNoDto();
        String bySiteAndItemName = parParameterRepository.findBySiteAndItemName(site, TableUtils.ZERONE);
        p.setZerone(bySiteAndItemName == null ? "91" : bySiteAndItemName);
        String bySiteAndItemName1 = parParameterRepository.findBySiteAndItemName(site,TableUtils.SEVEN);
        p.setSeven(bySiteAndItemName1 == null ? "97" : bySiteAndItemName1   );
        return ResponseEntity.ok(p);
    }
}
