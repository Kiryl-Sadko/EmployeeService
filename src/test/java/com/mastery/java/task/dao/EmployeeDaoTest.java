package com.mastery.java.task.dao;

import com.mastery.java.task.config.AppConfiguration;
import com.mastery.java.task.entity.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;

@SpringBootTest(classes = AppConfiguration.class)
@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:init.sql", "classpath:filling.sql"}),
        @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:drop.sql")
})
class EmployeeDaoTest {

    @Autowired
    EmployeeDao employeeDao;

    @Test
    void shouldFindAllEmployees() {
        List<Employee> all = employeeDao.findAll();
        Assertions.assertEquals(3, all.size());
    }

    @Test
    void shouldFindEmployeeById() {
        Employee employeeFromDb = employeeDao.findById(1L);
        Assertions.assertEquals("Test_1", employeeFromDb.getFirstName());
    }

    @Test
    void shouldCreateEmployee() {
        int sizeBeforeCreation = employeeDao.findAll().size();
        Employee employee = createNewEmployee();
        employeeDao.save(employee);
        int sizeAfterCreation = employeeDao.findAll().size();
        Assertions.assertEquals(++sizeBeforeCreation, sizeAfterCreation);
    }

    @Test
    void shouldDeleteEmployee() {
        int sizeBeforeCreation = employeeDao.findAll().size();
        employeeDao.delete(1L);
        int sizeAfterCreation = employeeDao.findAll().size();
        Assertions.assertEquals(--sizeBeforeCreation, sizeAfterCreation);
    }

    @Test
    void shouldUpdateEmployee() {
        String firstNameBeforeUpdating = employeeDao.findById(1L).getFirstName();
        Employee employee = createNewEmployee();
        employeeDao.update(employee);
        String firstNameAfterUpdating = employeeDao.findById(1L).getFirstName();
        Assertions.assertNotEquals(firstNameBeforeUpdating, firstNameAfterUpdating);
    }

    private Employee createNewEmployee() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("new Employee");
        employee.setLastName("lastname");
        employee.setDepartmentId(1L);
        employee.setJobTitle("Developer");
        employee.setGender("male");
        employee.setDateOfBirth(LocalDate.now());
        return employee;
    }
}
