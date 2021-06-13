package com.mastery.java.task.service.impl;

import com.mastery.java.task.converter.EmployeeConverter;
import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.Dto;
import com.mastery.java.task.dto.EmployeeDto;
import com.mastery.java.task.entity.Employee;
import com.mastery.java.task.exception.InvalidDtoException;
import com.mastery.java.task.exception.SavingEntityException;
import com.mastery.java.task.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.text.MessageFormat;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER = LogManager.getLogger(EmployeeServiceImpl.class);

    private final EmployeeDao employeeDao;
    private final EmployeeConverter converter;
    private final Validator validator;

    public EmployeeServiceImpl(EmployeeDao employeeDao, EmployeeConverter converter, Validator validator) {
        this.employeeDao = employeeDao;
        this.converter = converter;
        this.validator = validator;
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
    public Long save(EmployeeDto dto) {
        validate(dto);

        Employee employee = converter.convertToEntity(dto);
        try {
            return employeeDao.save(employee);
        } catch (Exception exception) {
            String message = "Bad request, check input";
            LOGGER.error(message);
            throw new SavingEntityException(message);
        }
    }

    @Override
    public EmployeeDto update(EmployeeDto dto) {
        checkEmployeeForExistence(dto);
        validate(dto);
        Employee employee = converter.convertToEntity(dto);
        employeeDao.update(employee);
        return converter.convertToDto(employee);
    }

    private void checkEmployeeForExistence(EmployeeDto dto) {
        Long id = dto.getId();
        try {
            this.findById(id);
        } catch (Exception e) {
            String message = MessageFormat.format("Such employee id={0} not found", id);
            LOGGER.error(message);
            throw new NoSuchElementException(message);
        }
    }

    @Override
    public void delete(Long id) {
        employeeDao.delete(id);
    }

    private void validate(EmployeeDto dto) {
        Set<ConstraintViolation<Dto>> violations = validator.validate(dto);
        if (!CollectionUtils.isEmpty(violations)) {
            StringBuilder causes = new StringBuilder();
            violations.stream().iterator().forEachRemaining(violation -> causes
                    .append("'")
                    .append(violation.getPropertyPath().toString())
                    .append("' ")
                    .append(violation.getMessage())
                    .append(";"));
            String message = MessageFormat.format("The dto is invalid, check causes: {0}", causes);
            LOGGER.error(message);
            throw new InvalidDtoException(message);
        }
    }
}
