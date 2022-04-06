package com.vivacon.mapper;

@FunctionalInterface
public interface ResponseConverter {

    Object toResponse(Object entity);
}
