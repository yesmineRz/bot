package io.oilfox.backend.db.repositories;

import com.google.inject.Singleton;
import io.oilfox.backend.db.entities.DbUser;
import io.oilfox.backend.db.repositories.RepositoryBase;

@Singleton
public class UserRepository extends RepositoryBase<DbUser> {

    public UserRepository() {
        super(DbUser.class);
    }
}
