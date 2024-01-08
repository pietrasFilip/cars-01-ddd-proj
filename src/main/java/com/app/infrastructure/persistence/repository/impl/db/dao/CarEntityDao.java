package com.app.infrastructure.persistence.repository.impl.db.dao;

import com.app.infrastructure.persistence.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarEntityDao extends JpaRepository<CarEntity, Long> {
}
