package com.mastery.java.task.service;

import com.mastery.java.task.config.AppConfiguration;
import com.mastery.java.task.dto.EmployeeDto;
import com.mastery.java.task.dto.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;

@SpringBootTest(classes = AppConfiguration.class)
@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:init.sql", "classpath:filling.sql"}),
        @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:drop.sql")
})
class EmployeeServiceTest {

    @Autowired
    private EmployeeService service;

    @Test
    void shouldFindAllEmployees() {
        List<EmployeeDto> all = service.findAll();
        assertEquals(3, all.size());
    }

    @Test
    void shouldFindEmployeeById() {
        EmployeeDto employeeDto = service.findById(1L);
        assertEquals("Test_1", employeeDto.getFirstName());
    }

    @Test
    void shouldCreateEmployee() {
        int sizeBeforeCreation = service.findAll().size();
        EmployeeDto employeeDto = createNewEmployeeDto();
        service.save(employeeDto);
        int sizeAfterCreation = service.findAll().size();
        assertEquals(++sizeBeforeCreation, sizeAfterCreation);
    }

    @Test
    void shouldDeleteEmployee() {
        int sizeBeforeCreation = service.findAll().size();
        service.delete(1L);
        int sizeAfterCreation = service.findAll().size();
        assertEquals(--sizeBeforeCreation, sizeAfterCreation);
    }

    @Test
    void shouldUpdateEmployee() {
        String firstNameBeforeUpdating = service.findById(1L).getFirstName();
        EmployeeDto employeeDto = createNewEmployeeDto();
        EmployeeDto updatedDto = service.update(employeeDto);
        String firstNameAfterUpdating = updatedDto.getFirstName();
        assertNotEquals(firstNameBeforeUpdating, firstNameAfterUpdating);
    }

    private EmployeeDto createNewEmployeeDto() {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(1L);
        dto.setFirstName("new Employee");
        dto.setLastName("lastname");
        dto.setDepartmentId(1L);
        dto.setJobTitle("Developer");
        dto.setGender(Gender.MALE.name());
        dto.setDateOfBirth(LocalDate.now());
        return dto;
    }
}
