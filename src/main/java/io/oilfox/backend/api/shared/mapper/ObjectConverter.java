package io.oilfox.backend.api.shared.mapper;

public interface ObjectConverter<T> {
    Object convert(T obj);
}
