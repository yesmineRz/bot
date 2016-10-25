package io.oilfox.backend.db.repositories;

import com.google.inject.Singleton;
import io.oilfox.backend.db.entities.DbTank;
import io.oilfox.backend.db.repositories.RepositoryBase;

@Singleton
public class TankRepository extends RepositoryBase<DbTank> {

    public TankRepository() {
        super(DbTank.class);
    }
}
