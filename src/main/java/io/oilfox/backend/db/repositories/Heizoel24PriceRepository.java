package io.oilfox.backend.db.repositories;

import io.oilfox.backend.db.entities.DbHeizoel24Price;

/**
 * Created by ipusic on 2/4/16.
 */
public class Heizoel24PriceRepository extends RepositoryBase<DbHeizoel24Price> {
    public Heizoel24PriceRepository() {
        super(DbHeizoel24Price.class);
    }
}
