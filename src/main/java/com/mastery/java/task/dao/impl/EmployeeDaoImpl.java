package com.mastery.java.task.dao.impl;

import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.entity.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    private static final Logger LOGGER = LogManager.getLogger(EmployeeDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Employee> rowMapper;

    public EmployeeDaoImpl(JdbcTemplate jdbcTemplate) {
        this.rowMapper = (resultSet, i) -> {
            Employee employee = new Employee();
            employee.setId(resultSet.getLong("id"));
            employee.setFirstName(resultSet.getString("first_name"));
            employee.setLastName(resultSet.getString("last_name"));
            employee.setDepartmentId(resultSet.getLong("department_id"));
            employee.setJobTitle(resultSet.getString("job_title"));
            employee.setGender(resultSet.getString("gender"));
            employee.setDateOfBirth(resultSet.getDate("date_of_birth").toLocalDate());
            return employee;
        };
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Employee> findAll() {
        String query = "SELECT * FROM employee;";
        return jdbcTemplate.query(query, rowMapper);
    }

    @Override
    public Employee findById(Long id) {
        String query = MessageFormat.format("SELECT * FROM employee WHERE id={0}", id);
        try {
            return jdbcTemplate.queryForObject(query, rowMapper);
        } catch (Exception exception) {
            LOGGER.error("Such entity not found id={}", id);
            throw new NoSuchElementException();
        }
    }

    @Override
    public Long save(Employee employee) {
        String query = "INSERT INTO employee (first_name, last_name, department_id, job_title, gender, date_of_birth) VALUES(?, ?, ?, ?, ?, ?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection ->
                {
                    PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, employee.getFirstName());
                    ps.setString(2, employee.getLastName());
                    ps.setLong(3, employee.getDepartmentId());
                    ps.setString(4, employee.getJobTitle());
                    ps.setString(5, employee.getGender());
                    ps.setDate(6, Date.valueOf(employee.getDateOfBirth()));
                    return ps;
                },
                keyHolder);
        Long generatedId = (Long) Objects.requireNonNull(keyHolder.getKeys(), "Generated key hasn't found").get("id");
        employee.setId(generatedId);
        LOGGER.info("Employee has created in database, generated id='{}'", generatedId);
        return generatedId;
    }

    @Override
    public void update(Employee employee) {
        String query = "UPDATE employee SET(first_name, last_name, department_id, job_title, gender, date_of_birth) = (?, ?, ?, ?, ?, ?)WHERE id =?";
        jdbcTemplate.update(query,
                employee.getFirstName(),
                employee.getLastName(),
                employee.getDepartmentId(),
                employee.getJobTitle(),
                employee.getGender(),
                Date.valueOf(employee.getDateOfBirth()),
                employee.getId());
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM employee WHERE id=?";
        int isDeleted = jdbcTemplate.update(query, id);
        if (isDeleted == 0) {
            String message = MessageFormat.format("Employee with id={0} not found", id);
            LOGGER.error(message);
            throw new NoSuchElementException(message);
        }
    }

    @Override
    public List<Employee> findByDepartmentId(Long id) {
        String query = "SELECT * FROM employee WHERE department_id=?";
        return jdbcTemplate.query(query, rowMapper, id);
    }
}
