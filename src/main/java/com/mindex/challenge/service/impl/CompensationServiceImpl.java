package com.mindex.challenge.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;

@Service
public class CompensationServiceImpl implements CompensationService{
	private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);
	private final CompensationRepository compensationRepository;
	private final EmployeeRepository employeeRepository;
	
	//Repository/Constructor injection
	public CompensationServiceImpl(CompensationRepository compensationRepository, EmployeeRepository employeeRepository) {
		this.compensationRepository = compensationRepository;
		this.employeeRepository = employeeRepository;
	}

	/*
	 * This method is used to create the new compensation 
	 * It accepts below parameters 
	 * compensation - object of type Compensation
	 */
	@Override
	public Compensation create(Compensation compensation) {
		LOG.debug("Creating compensation for employeeID: "+compensation.getEmployee().getEmployeeId());
		Employee employee = this.employeeRepository.findByEmployeeId(compensation.getEmployee().getEmployeeId());
		if(employee == null) {
			throw new RuntimeException("Employee:"
					+ " with employeeId: "+compensation.getEmployee().getEmployeeId()+ 
					" is not the system to create the compensation");
		}
		compensation.setEmployee(employee);
		this.compensationRepository.insert(compensation);
		return compensation;
	}

	/*
	 * This method is for getting the compensation for employee 
	 * It accepts below parameters 
	 * employId - employeeId if requried compensation
	 */
	@Override
	public Compensation read(String employeeId) {
		LOG.debug("Reading the compensation for employee: "+employeeId);
		Employee employee = this.employeeRepository.findByEmployeeId(employeeId);
		if(employee == null) {
			throw new RuntimeException("Employee not found with employee ID: "+employeeId);
		}
		
		Compensation compensation = this.compensationRepository.findByEmployee(employee);
		
		if(compensation == null) {
			throw new RuntimeException("No compensation found for employee:" +employee.getFirstName()+ " "+employee.getLastName()+" with employeeId: "+employeeId);
		}
		return compensation;
	}

}
