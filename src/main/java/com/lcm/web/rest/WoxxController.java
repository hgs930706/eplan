package com.lcm.web.rest;

import com.lcm.domain.Woxx;
import com.lcm.domain.WoxxId;
import com.lcm.repository.WoxxRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/woxx")
@RestController
public class WoxxController {

    private final WoxxRepository woxxRepository;

    public WoxxController(WoxxRepository woxxRepository) {
        this.woxxRepository = woxxRepository;
    }

    @GetMapping("")
    public ResponseEntity<Woxx> findOne(WoxxId woxxId) {
        return ResponseEntity.ok(woxxRepository.findById(woxxId).orElseGet(() ->new Woxx()));
    }

    @GetMapping("/woIds")
    public ResponseEntity<List<String>> woIds(String site, String fab, String area, String partNo) {
        return ResponseEntity.ok(woxxRepository.findDistinctWoIdBySiteAndFabAndAreaAndPartNo(site, fab, area, partNo));
    }
}
