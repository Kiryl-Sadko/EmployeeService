package com.mastery.java.task.dao;

import com.mastery.java.task.entity.Entity;

import java.util.List;

public interface CrudDao<T extends Entity> extends Dao<T> {

    List<T> findAll();

    T findById(Long id);

    int save(T t);

    void update(T t);

    boolean delete(Long id);
}
