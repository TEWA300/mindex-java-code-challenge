package com.mindex.challenge.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService{
	
	private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);
	private final EmployeeRepository employeeRepository;
	
	//Constructor injection
	public ReportingStructureServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	/*
	 * This method is used for fetching the report structure for required employee
	 * It takes below parameter 
	 * id - Employee ID
	 */
	@Override
	public ReportingStructure read(String id) {
		LOG.debug("Reading the report structure for employee ID: "+id);
		
		Employee employee = this.employeeRepository.findByEmployeeId(id);
		
		if (employee == null) {
			throw new RuntimeException("Invalid Employee ID");
		}

		int numberOfReports = 0;
		ReportingStructure reportingStructure = new ReportingStructure(employee, numberOfReports);
		
		List<Employee> directReports = employee.getDirectReports();
		
		numberOfReports = directReports.size();
		
		for(Employee emp : directReports) {
			List<Employee> tmpLst = emp.getDirectReports();
			if (tmpLst != null) {
				numberOfReports += tmpLst.size();
			}
		}

		reportingStructure.setNumberOfReports(numberOfReports);
		return reportingStructure;
	}

}
