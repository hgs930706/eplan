package com.lcm.service;

import com.lcm.domain.ParParameter;
import com.lcm.repository.ParParameterRepository;
import com.lcm.util.SnowFlake;
import com.lcm.util.UserUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ParParameterService {

    private final ParParameterRepository parParameterRepository;
    private final SnowFlake snowFlake;

    public ParParameterService(ParParameterRepository parParameterRepository, SnowFlake snowFlake) {
        this.parParameterRepository = parParameterRepository;
        this.snowFlake = snowFlake;
    }

    public void update(ParParameter parParameter) {
        Optional<ParParameter> par = parParameterRepository.findById(parParameter.getSeq());
        par.ifPresent(p -> {
            p.setInValue1(parParameter.getInValue1());
            p.setInValue2(parParameter.getInValue2());
            p.setInValue3(parParameter.getInValue3());
            p.setInValue4(parParameter.getInValue4());
            p.setRemark(parParameter.getRemark());
            p.setLmUser(UserUtils.currentUser().getEmployeeId());
            p.setLmTime(LocalDateTime.now());
            parParameterRepository.save(p);
        });
    }

    public void save(ParParameter parParameter) {
        parParameter.setSeq(snowFlake.nextId()+"");
        parParameter.setLmUser(UserUtils.currentUser().getEmployeeId());
        parParameter.setLmTime(LocalDateTime.now());
        parParameterRepository.save(parParameter);
    }
}
