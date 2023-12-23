package com.app.infrastructure.persistence.repository.impl.db.dao;

import com.app.infrastructure.persistence.entity.CarEntity;
import com.app.infrastructure.persistence.repository.impl.db.dao.generic.CrudRepository;

public interface CarEntityDao extends CrudRepository<CarEntity, Long> {
}
