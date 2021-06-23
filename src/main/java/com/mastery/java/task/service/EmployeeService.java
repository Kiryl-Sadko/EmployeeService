package com.mastery.java.task.service;

import com.mastery.java.task.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService extends CRUDService<EmployeeDto> {

    List<EmployeeDto> findByDepartmentId(Long id);
}
