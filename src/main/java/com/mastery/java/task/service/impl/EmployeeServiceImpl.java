package com.mastery.java.task.service.impl;

import com.mastery.java.task.converter.EmployeeConverter;
import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.EmployeeDto;
import com.mastery.java.task.entity.Employee;
import com.mastery.java.task.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;
    private final EmployeeConverter converter;

    public EmployeeServiceImpl(EmployeeDao employeeDao, EmployeeConverter converter) {
        this.employeeDao = employeeDao;
        this.converter = converter;
    }

    @Override
    public List<EmployeeDto> findAll() {
        List<Employee> employees = employeeDao.findAll();
        return employees.stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto findById(Long id) {
        Employee employee = employeeDao.findById(id);
        return converter.convertToDto(employee);
    }

    @Override
    public int save(EmployeeDto dto) {
        Employee employee = converter.convertToEntity(dto);
        return employeeDao.save(employee);
    }

    @Override
    public EmployeeDto update(EmployeeDto dto) {
        Employee employee = converter.convertToEntity(dto);
        employeeDao.update(employee);
        return converter.convertToDto(employee);
    }

    @Override
    public void delete(Long id) {
        employeeDao.delete(id);
    }
}
