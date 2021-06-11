package com.mastery.java.task.dao;

import com.mastery.java.task.entity.Employee;

import java.util.List;

public interface EmployeeDao extends CrudDao<Employee> {

    List<Employee> findByDepartmentId(Long id);
}
