package com.mastery.java.task.dao;

import com.mastery.java.task.entity.Entity;

import java.util.List;

public interface CrudDao<E extends Entity> extends Dao {

    List<E> findAll();

    E findById(Long id);

    Long save(E entity);

    void update(E entity);

    void delete(Long id);
}
