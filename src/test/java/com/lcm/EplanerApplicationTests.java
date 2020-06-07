package com.lcm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lcm.repository.LineRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EplanerApplicationTests {

	@Autowired
	private  LineRepository lineRepository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void dataSouce(){
		lineRepository.findDistinctFabBySite("S01").forEach(System.out::println);

	}


}
