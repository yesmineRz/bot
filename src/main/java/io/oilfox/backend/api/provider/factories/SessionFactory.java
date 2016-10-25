package io.oilfox.backend.api.provider.factories;

import io.oilfox.backend.db.entities.DbSession;
import org.glassfish.hk2.api.Factory;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;

/**
 * Created by ipusic on 1/21/16.
 */

public class SessionFactory implements Factory<DbSession> {

    private final ContainerRequestContext context;

    @Inject
    public SessionFactory(ContainerRequestContext context) {
        this.context = context;
    }

    @Override
    public DbSession provide() {
        return (DbSession) context.getProperty("session");
    }

    @Override
    public void dispose(DbSession instance) {

    }
}