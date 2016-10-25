package io.oilfox.backend.db.repositories;

import io.oilfox.backend.db.entities.DbBaywaPrice;


/**
 * Created by Yesmine on 06/09/16.
 */
public class BaywaPriceRepository extends RepositoryBase<DbBaywaPrice> {
    public BaywaPriceRepository() {
        super(DbBaywaPrice.class);
    }
}
