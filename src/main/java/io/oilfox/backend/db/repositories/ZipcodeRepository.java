package io.oilfox.backend.db.repositories;

import io.oilfox.backend.db.entities.DbZipcode;
import io.oilfox.backend.db.repositories.RepositoryBase;

/**
 * Created by ipusic on 5/9/16.
 */
public class ZipcodeRepository extends RepositoryBase<DbZipcode> {
    public ZipcodeRepository() {
        super(DbZipcode.class);
    }
}
