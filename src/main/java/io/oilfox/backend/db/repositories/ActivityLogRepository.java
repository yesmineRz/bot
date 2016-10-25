package io.oilfox.backend.db.repositories;

import io.oilfox.backend.db.entities.DbActivityLog;



/**
 * Created by ipusic on 1/27/16.
 */
public class ActivityLogRepository extends RepositoryBase<DbActivityLog> {
    public ActivityLogRepository() {
        super(DbActivityLog.class);
    }
}
