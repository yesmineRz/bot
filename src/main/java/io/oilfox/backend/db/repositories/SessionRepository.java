package io.oilfox.backend.db.repositories;

import io.oilfox.backend.db.entities.DbSession;
import io.oilfox.backend.db.repositories.RepositoryBase;

public class SessionRepository extends RepositoryBase<DbSession> {

    public SessionRepository() {
        super(DbSession.class);
    }

    public void deleteByCriteria(String name, Object value) {

        String hql = "delete from DbSession where " + name + "= :value";
        unitOfWork.db().createQuery(hql).setParameter("value", value).executeUpdate();
    }
}
