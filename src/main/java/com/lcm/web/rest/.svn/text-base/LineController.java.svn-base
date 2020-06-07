package com.lcm.web.rest;

import com.lcm.domain.Line;
import com.lcm.domain.QLine;
import com.lcm.repository.LineRepository;
import com.lcm.util.UserUtils;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/line")
@RestController
public class LineController {

    private final LineRepository lineRepository;

    public LineController(LineRepository lineRepository) {
        this.lineRepository = lineRepository;
    }

    @GetMapping("query")
    public ResponseEntity<Page<Line>> query(@QuerydslPredicate(root = Line.class) Predicate pre, @PageableDefault Pageable pageable, String lineParam) {
        QLine query = QLine.line1;
        pre = ExpressionUtils.and(pre, query.line.like("%" + lineParam + "%"));
        return ResponseEntity.ok(lineRepository.findAll(pre, pageable));
    }

    @GetMapping("/export")
    public ResponseEntity query(@QuerydslPredicate(root = Line.class) Predicate pre, String lineParam) {
        QLine query = QLine.line1;
        pre = ExpressionUtils.and(pre, query.line.like("%" + lineParam + "%"));
        return ResponseEntity.ok(lineRepository.findAll(pre));
    }

    @GetMapping("/sites")
    public ResponseEntity<List<String>> sites(HttpSession session) {
        String[] locations = (String[]) session.getAttribute("sites");
        if (locations != null) {
            if (!"*".equals(locations[0])) {
                List<String> strings = Arrays.asList(locations);
                return ResponseEntity.ok(strings);
            } else {
                return ResponseEntity.ok(lineRepository.findDistinctSite());
            }
        }
        return null;
    }

    @GetMapping("/fabs")
    public ResponseEntity<List<String>> fabs(String site) {
        return ResponseEntity.ok(lineRepository.findDistinctFabBySite(site));
    }

    @GetMapping("/areas")
    public ResponseEntity<List<String>> areas(String site, String fab) {
        System.out.println(fab);
        if (StringUtils.isEmpty(fab)) {
            return ResponseEntity.ok(null);
        }else if (fab.equals("all")){
            return ResponseEntity.ok(lineRepository.findDistinctAreaBySite(site));
        }else{
            return ResponseEntity.ok(lineRepository.findDistinctAreaBySiteAndFab(site, fab));
        }
    }

    @GetMapping("/lines")
    public ResponseEntity<List<String>> lines(String site, String fab, String area) {
        return ResponseEntity.ok(lineRepository.findDistinctLineBySiteAndFabAndArea(site, fab, area));
    }

    @PostMapping("update")
    public ResponseEntity update(@RequestBody Line line) {
        line.setLmTime(LocalDateTime.now());
        line.setLmUser(UserUtils.currentUser().getEmployeeId());

        return ResponseEntity.ok(lineRepository.save(line));
    }

    @GetMapping("/queryAreas")
    public ResponseEntity<List<String>> queryAreas(@RequestParam String site, @RequestParam String[] fab) {
        if (fab.length > 0) {
            return ResponseEntity.ok(lineRepository.findAreas(site, fab));
        }
        return ResponseEntity.ok(null);
    }

}



