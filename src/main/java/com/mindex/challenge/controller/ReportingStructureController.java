package com.mindex.challenge.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;


/*Rest API is exposed to read reporting structure of employees via 
end points /reportingStructure/{id}. */

@RestController
public class ReportingStructureController {
	private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);
	private final ReportingStructureService reportingStructureService;
	
	//Constructor injection
	public ReportingStructureController(ReportingStructureService reportingStructureService) {
		this.reportingStructureService = reportingStructureService;
	}
	
	@GetMapping(path = "/reportingStructure/{id}")
	public ReportingStructure read(@PathVariable String id) {
		LOG.debug("Read report structure request for employeID: "+id);
		return this.reportingStructureService.read(id);
	}

}
