package io.oilfox.backend.db.repositories;

import io.oilfox.backend.db.entities.DbPartner;
import io.oilfox.backend.db.repositories.RepositoryBase;

/**
 * Created by Yesmine on 08/09/16.
 */
public class PartnerRepository extends RepositoryBase<DbPartner> {
    public PartnerRepository() {
        super(DbPartner.class);
    }
}
