package com.app.infrastructure.persistence.repository.impl.db.dao;

import com.app.infrastructure.persistence.entity.UserEntity;
import com.app.infrastructure.persistence.repository.impl.db.dao.generic.CrudRepository;

import java.util.Optional;

public interface UserEntityDao extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
}
