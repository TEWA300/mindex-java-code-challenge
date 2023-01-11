package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {
    private String compensationUrl;
    private String compensationIdUrl;

    @Autowired
    private EmployeeRepository employeeRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation";
        compensationIdUrl = "http://localhost:" + port + "/compensation/{employeeId}";
    }

    @Test
    public void testCreateRead() {
        Compensation testCompensation = new Compensation();
        Employee employee = this.employeeRepository.findByEmployeeId("b7839309-3348-463b-a7e3-5de1c168beb3");
        testCompensation.setEmployee(employee);
        testCompensation.setSalary("120000");
        testCompensation.setEffectiveDate(new Date(1256238663));

        Compensation creatCompensation = restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();
        assertEmployeeEquivalence(testCompensation, creatCompensation);

        Compensation readCompensation = restTemplate.getForEntity(compensationIdUrl, Compensation.class, creatCompensation.getEmployee().getEmployeeId()).getBody();
        assertEmployeeEquivalence(creatCompensation, readCompensation);
    }

    private static void assertEmployeeEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getEmployee().getEmployeeId(), actual.getEmployee().getEmployeeId());
        assertEquals(expected.getSalary(), actual.getSalary());
        assertEquals(expected.getEffectiveDate().compareTo(actual.getEffectiveDate()), 0);
    }
}