package io.oilfox.backend.db.repositories;

import com.google.inject.Singleton;
import io.oilfox.backend.db.entities.DbMetering;


@Singleton
public class MeteringRepository extends RepositoryBase<DbMetering> {

    public MeteringRepository() {
        super(DbMetering.class);
    }
}
