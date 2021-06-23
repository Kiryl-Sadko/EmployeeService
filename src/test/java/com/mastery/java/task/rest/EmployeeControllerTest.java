package com.mastery.java.task.rest;

import com.mastery.java.task.config.AppConfiguration;
import com.mastery.java.task.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AppConfiguration.class)
@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:init.sql", "classpath:filling.sql"}),
        @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:drop.sql")
})
@AutoConfigureMockMvc(addFilters = false)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeService service;

    @Test
    void responseShouldHaveOkStatus() throws Exception {
        mockMvc.perform(get("/employees/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void responseShouldHaveBadRequestStatus() throws Exception {
        mockMvc.perform(get("/employees/badRequest")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldCreateEmployee() throws Exception {
        int before = service.findAll().size();
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content("{\n" +
                        "    \"id\": 37,\n" +
                        "    \"firstName\": \"Kiryl\",\n" +
                        "    \"lastName\": \"Postman\",\n" +
                        "    \"departmentId\": 1,\n" +
                        "    \"jobTitle\": \"developer_1\",\n" +
                        "    \"gender\": \"MALE\",\n" +
                        "    \"dateOfBirth\": [\n" +
                        "        1995,\n" +
                        "        8,\n" +
                        "        3\n" +
                        "    ]\n" +
                        "}"))
                .andExpect(status().isCreated());
        int after = service.findAll().size();
        assertEquals(++before, after);
    }

    @Test
    void shouldDeleteEntityFromDB() throws Exception {
        int before = service.findAll().size();
        mockMvc.perform(delete("/employees/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
        int after = service.findAll().size();
        assertEquals(--before, after);
    }
}
