package com.mastery.java.task.service;

import com.mastery.java.task.dto.Dto;

import java.util.List;

public interface CrudService<D extends Dto> extends Service {

    List<D> findAll();

    D findById(Long id);

    Long save(D dto);

    D update(D dto);

    void delete(Long id);
}
