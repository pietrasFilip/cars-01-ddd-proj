package com.app.infrastructure.persistence.repository.impl.db.dao.impl;

import com.app.infrastructure.persistence.entity.UserEntity;
import com.app.infrastructure.persistence.repository.impl.db.dao.UserEntityDao;
import com.app.infrastructure.persistence.repository.impl.db.dao.generic.AbstractCrudRepository;
import com.app.infrastructure.persistence.repository.impl.db.dao.generic.EntityManagerWrapper;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserEntityDaoImpl extends AbstractCrudRepository<UserEntity, Long> implements UserEntityDao {
    public UserEntityDaoImpl(EntityManagerFactory emf) {
        super(emf);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        EntityTransaction tx = null;
        Optional<UserEntity> item = Optional.empty();
        try (var emw = new EntityManagerWrapper(emf)){
            var em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            item = Optional.ofNullable(em
                    .createQuery("select u from UserEntity u where u.username = :username", UserEntity.class)
                    .setParameter("username", username)
                    .getSingleResult());

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        EntityTransaction tx = null;
        Optional<UserEntity> item = Optional.empty();
        try (var emw = new EntityManagerWrapper(emf)){
            var em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            item = Optional.ofNullable(em
                    .createQuery("select u from UserEntity u where u.email = :email", UserEntity.class)
                    .setParameter("email", email)
                    .getSingleResult());

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return item;
    }
}
