package com.mastery.java.task.converter.impl;

import com.mastery.java.task.converter.EmployeeConverter;
import com.mastery.java.task.dto.EmployeeDto;
import com.mastery.java.task.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeConverterImpl implements EmployeeConverter {

    @Override
    public EmployeeDto convertToDto(Employee employee) {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setJobTitle(employee.getJobTitle());
        dto.setGender(employee.getGender());
        dto.setDepartmentId(employee.getDepartmentId());
        dto.setDateOfBirth(employee.getDateOfBirth());
        return dto;
    }

    @Override
    public Employee convertToEntity(EmployeeDto dto) {
        Employee entity = new Employee();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setJobTitle(dto.getJobTitle());
        entity.setGender(dto.getGender());
        entity.setDepartmentId(dto.getDepartmentId());
        entity.setDateOfBirth(dto.getDateOfBirth());
        return entity;
    }
}
