package io.oilfox.backend.db.repositories;

import io.oilfox.backend.db.entities.DbAdmin;


/**
 * Created by Yesmine on 18/10/16.
 */
public class AdminRepository extends RepositoryBase<DbAdmin> {
    public AdminRepository() {
        super(DbAdmin.class);
    }
}
