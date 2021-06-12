package com.mastery.java.task.converter;

public interface Converter<D, E> {

    D convertToDto(E entity);

    E convertToEntity(D dto);
}
