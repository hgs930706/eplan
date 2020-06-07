package com.lcm.web.rest;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lcm.service.AppService;
import com.lcm.web.rest.param.JobParam;
import com.lcm.web.rest.param.ManualRunParam;

@RequestMapping("/demo")
@RestController
public class AppController {
	private static final Logger logger = LoggerFactory.getLogger(AppController.class);
	private final AppService appService;

	public AppController(AppService appService) {
		super();
		this.appService = appService;
	}

	@RequestMapping(value = "/run", produces = "application/json")
	@ResponseBody
	public ManualRunParam run(@RequestBody ManualRunParam jsonParam) {
		long lStart = System.currentTimeMillis();
		ManualRunParam returnMsg = appService.doProcess(jsonParam);
		long lEnd = System.currentTimeMillis() - lStart;
		logger.info("Time elapsed: " + lEnd / 1000 + " seconds");

		return returnMsg;
	}
	
	@RequestMapping(value="/factChange", produces="application/json")
	@ResponseBody
	public ManualRunParam factChange(@RequestBody List<JobParam> jsonParams) {
		long lStart = System.currentTimeMillis();
		ManualRunParam returnMsg = appService.factChange(jsonParams);
		long lEnd = System.currentTimeMillis() - lStart;
		logger.info("Time elapsed: " + lEnd / 1000 + " seconds");

		return returnMsg;
	}
	
	@RequestMapping(value = "/benchmark", produces = "application/json")
	@ResponseBody
	public ManualRunParam benchmark(@RequestBody ManualRunParam jsonParam) {
		long lStart = System.currentTimeMillis();
		ManualRunParam returnMsg = appService.benchmark(jsonParam);
		long lEnd = System.currentTimeMillis() - lStart;
		logger.info("Time elapsed: " + lEnd / 1000 + " seconds");

		return returnMsg;
	}
}
