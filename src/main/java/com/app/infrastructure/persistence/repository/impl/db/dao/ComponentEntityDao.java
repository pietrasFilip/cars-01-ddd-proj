package com.app.infrastructure.persistence.repository.impl.db.dao;

import com.app.infrastructure.persistence.entity.ComponentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponentEntityDao extends JpaRepository<ComponentEntity, Long> {
}
