package io.oilfox.backend.db.repositories;

import com.google.inject.Singleton;
import io.oilfox.backend.db.entities.DbOilfox;
import io.oilfox.backend.db.repositories.RepositoryBase;

@Singleton
public class OilfoxRepository extends RepositoryBase<DbOilfox> {

    public OilfoxRepository() {
        super(DbOilfox.class);
    }
}
