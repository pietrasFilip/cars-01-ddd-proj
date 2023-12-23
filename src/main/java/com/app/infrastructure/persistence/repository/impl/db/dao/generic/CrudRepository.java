package com.app.infrastructure.persistence.repository.impl.db.dao.generic;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {
    T save(T t);
    List<T> saveAll(List<T> items);
    Optional<T> findById(ID id);
    List<T> findAllById(List<ID> ids);
    List<T> findAll();
    void deleteById(ID id);
    void deleteAll();
}
