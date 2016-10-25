package io.oilfox.backend.db.repositories;

import io.oilfox.backend.db.db.UnitOfWork;
import io.oilfox.backend.db.entities.BaseEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public abstract class RepositoryBase<T extends BaseEntity> {

    private Class<T> cls;

    public RepositoryBase(Class<T> cls) {
        this.cls = cls;
    }

    @Inject
    protected UnitOfWork unitOfWork;

    /**
     * Convenience method to shield unchecked problems into one method
     */
    @SuppressWarnings("unchecked")
    public List<T> findListByCriteria(Criterion... criterion) {

        Criteria criteria = unitOfWork.db().createCriteria(cls);
        for (Criterion c : criterion) {
            criteria.add(c);
        }
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public T findSingleByCriteria(Criterion... criterion) {

        Criteria criteria = unitOfWork.db().createCriteria(cls);
        for (Criterion c : criterion) {
            criteria.add(c);
        }

        return (T) criteria.uniqueResult();
    }

    public T findSingleBy(String key, Object value) {

        return findSingleByCriteria(Restrictions.eq(key, value));
    }

    public List<T> findListBy(String key, Object value) {

        return findListByCriteria(Restrictions.eq(key, value));
    }

    public T findSingleById(UUID id) {
        return findSingleByCriteria(Restrictions.eq("id", id));
    }

    public T findSingleById(String id) {
        if (id == null) {
            return null;
        }

        return findSingleById(UUID.fromString(id));
    }

    public Criteria query() {
        return unitOfWork.db().createCriteria(cls);
    }

    @SuppressWarnings("unchecked")
    public List<T> all() {

        Criteria criteria = unitOfWork.db().createCriteria(cls);
        //criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    public Session db() {
        return unitOfWork.db();
    }

    public T save(T object) {
        if (object.createdAt == null) {
            object.createdAt = new Date();
        }
        db().save(object);

        return object;
    }

    public T update(T object) {
        object.updatedAt = new Date();
        db().update(object);

        return object;
    }

    public void delete(T object) {
        db().delete(object);
    }

    public void commit() {
        unitOfWork.commit();
    }

    public void flush() {
        unitOfWork.db().flush();
    }
}