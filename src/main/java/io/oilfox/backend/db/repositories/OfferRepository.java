package io.oilfox.backend.db.repositories;

import com.google.inject.Singleton;
import io.oilfox.backend.db.entities.DbOffer;
import io.oilfox.backend.db.repositories.RepositoryBase;

@Singleton
public class OfferRepository extends RepositoryBase<DbOffer> {

    public OfferRepository() {
        super(DbOffer.class);
    }
}
