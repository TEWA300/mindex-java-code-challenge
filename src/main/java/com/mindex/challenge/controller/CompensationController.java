package com.mindex.challenge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.impl.CompensationServiceImpl;

/*Two Rest APIs exposed to read and create the compensation via 
end points /compensation/{employeeId} and /compensation respectfully. */

@RestController
public class CompensationController {
	private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

	private final CompensationServiceImpl compensationService;

	//Constructor injection
	public CompensationController(CompensationServiceImpl compensationService) {
		this.compensationService = compensationService;
	}
	
	@GetMapping(path = "/compensation/{employeeId}")
	public Compensation read(@PathVariable String employeeId){
		LOG.debug("Read compensation request for employeeID: "+employeeId);
		return this.compensationService.read(employeeId);
	}
	
	@PostMapping(path = "/compensation")
	public Compensation create(@RequestBody Compensation compensation) {
		LOG.debug("Create compensation request for employeeID: "+compensation.getEmployee().getEmployeeId());
		return this.compensationService.create(compensation);
	}
}
