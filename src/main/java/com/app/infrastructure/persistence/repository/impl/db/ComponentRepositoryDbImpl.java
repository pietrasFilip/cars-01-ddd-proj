package com.app.infrastructure.persistence.repository.impl.db;

import com.app.domain.cars_management.model.repository.db.ComponentRepositoryDb;
import com.app.infrastructure.persistence.repository.impl.db.dao.ComponentEntityDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ComponentRepositoryDbImpl implements ComponentRepositoryDb {
    private final ComponentEntityDao componentEntityDao;
}
