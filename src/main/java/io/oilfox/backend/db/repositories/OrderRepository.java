package io.oilfox.backend.db.repositories;

import io.oilfox.backend.db.entities.DbOrder;
import io.oilfox.backend.db.repositories.RepositoryBase;

/**
 * Created by Yesmine on 15/07/16.
 */
public class OrderRepository extends RepositoryBase<DbOrder> {

    public OrderRepository() {
        super(DbOrder.class);
    }
}
