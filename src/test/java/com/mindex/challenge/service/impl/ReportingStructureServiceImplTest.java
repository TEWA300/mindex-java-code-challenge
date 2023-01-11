package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {
	private String readReportingStructureUrl;

	@Autowired
	private EmployeeRepository employeeRepository;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	private Employee testEmployee;

	@Before
	public void setup() {
		readReportingStructureUrl = "http://localhost:" + port + "/reportingStructure/{id}";

		testEmployee = employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
	}

	@Test
	public void testCreateReadUpdate() {
		ReportingStructure testReportingStructure = new ReportingStructure(testEmployee, 2);

		// Read checks
		ResponseEntity<ReportingStructure> readReportingStructureResponse = testRestTemplate
				.getForEntity(readReportingStructureUrl, ReportingStructure.class, testEmployee.getEmployeeId());
		
		ReportingStructure reportingStructure = readReportingStructureResponse.getBody();

		assertEquals(HttpStatus.OK, readReportingStructureResponse.getStatusCode());
		assertEmployeeEquivalence(reportingStructure, testReportingStructure);

	}
	
    private static void assertEmployeeEquivalence(ReportingStructure expected, ReportingStructure actual) {
    	assertNotNull(actual);
		assertEquals(actual.getEmployee().getEmployeeId(), expected.getEmployee().getEmployeeId());
		assertEquals(actual.getNumberOfReports(), expected.getNumberOfReports());
    }

}
