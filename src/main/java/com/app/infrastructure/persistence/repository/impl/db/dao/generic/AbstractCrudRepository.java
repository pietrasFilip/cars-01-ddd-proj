package com.app.infrastructure.persistence.repository.impl.db.dao.generic;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public abstract class AbstractCrudRepository<T, ID> implements CrudRepository<T, ID> {
    protected final EntityManagerFactory emf;

    @SuppressWarnings("unchecked")
    private final Class<T> entityType
            = (Class<T>) ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    @SuppressWarnings("unchecked")
    private final Class<ID> idType
            = (Class<ID>) ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];

    @Override
    public T save(T t) {
        EntityTransaction tx = null;
        T item = null;
        try (var emw = new EntityManagerWrapper(emf)){
            var em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();

            item = em.merge(t);
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
    public List<T> saveAll(List<T> items) {
        EntityTransaction tx = null;
        List<T> insertedItems = new ArrayList<>();
        try (var emw = new EntityManagerWrapper(emf)){
            var em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();

            items.forEach(item -> insertedItems.add(em.merge(item)));
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return insertedItems;
    }

    @Override
    public Optional<T> findById(ID id) {
        EntityTransaction tx = null;
        Optional<T> item = Optional.empty();
        try (var emw = new EntityManagerWrapper(emf)){
            var em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();

            item = Optional.ofNullable(em.find(entityType, id));
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
    public List<T> findAllById(List<ID> ids) {
        EntityTransaction tx = null;
        List<T> items = List.of();
        try (var emw = new EntityManagerWrapper(emf)){
            var em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();

            items = em
                    .createQuery(
                            "select t from %s t where t.id in :ids".formatted(entityType.getSimpleName()),
                                    entityType)
                            .setParameter("ids", ids)
                            .getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public List<T> findAll() {
        EntityTransaction tx = null;
        List<T> items = List.of();
        try (var emw = new EntityManagerWrapper(emf)){
            var em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();

            items = em
                    .createQuery(
                            "select t from %s t".formatted(entityType.getSimpleName()),
                            entityType)
                            .getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public void deleteById(ID id) {
        EntityTransaction tx = null;
        try (var emw = new EntityManagerWrapper(emf)){
            var em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();

            var item = em.find(entityType, id);
            em.remove(item);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        EntityTransaction tx = null;
        try (var emw = new EntityManagerWrapper(emf)){
            var em = emw.getEntityManager();
            tx = em.getTransaction();
            tx.begin();

            var items = em
                    .createQuery(
                            "select t from %s t".formatted(entityType.getSimpleName()),
                            entityType)
                            .getResultList();
            items.forEach(em::remove);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }
}
