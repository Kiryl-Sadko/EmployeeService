package com.mastery.java.task.converter;

import com.mastery.java.task.dto.Dto;
import com.mastery.java.task.entity.Entity;

public interface Converter<D extends Dto, E extends Entity> {

    D convertToDto(E entity);

    E convertToEntity(D dto);
}
