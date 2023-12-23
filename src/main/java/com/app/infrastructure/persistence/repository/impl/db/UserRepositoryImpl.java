package com.app.infrastructure.persistence.repository.impl.db;

import com.app.domain.users_management.model.User;
import com.app.domain.users_management.model.repository.UserRepository;
import com.app.infrastructure.persistence.entity.UserEntity;
import com.app.infrastructure.persistence.repository.impl.db.dao.UserEntityDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserEntityDao userEntityDao;
    @Override
    public Optional<User> findByUsername(String username) {
        return userEntityDao.findByUsername(username).map(UserEntity::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userEntityDao.findByEmail(email).map(UserEntity::toDomain);
    }

    @Override
    public Optional<User> save(User user) {
        return Optional.of(userEntityDao
                .save(user.toEntity())
                .toDomain());
    }

    @Override
    public Optional<User> findById(Long id) {
        return userEntityDao
                .findById(id)
                .map(UserEntity::toDomain);
    }
}
