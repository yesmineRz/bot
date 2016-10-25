package io.oilfox.backend.api.shared.startup.routines;

import io.oilfox.backend.api.shared.properties.DatabaseProperties;
import io.oilfox.backend.api.shared.properties.FlywayProperties;
import io.oilfox.backend.api.shared.startup.common.StartupRoutine;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class StartupFlyway extends StartupRoutine {

    private static final Logger logger = LoggerFactory.getLogger(StartupFlyway.class);

    @Inject
    private DatabaseProperties databaseProperties;

    @Inject
    private FlywayProperties flywayProperties;

    @Override
    public void run() {

        if (!flywayProperties.getEnabled()) {
            logger.warn("Flyway has been disabled by configuration; skipping database migrations");
            return;
        }

        Flyway flyway = new Flyway();

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName(databaseProperties.getHostname());
        dataSource.setPortNumber(databaseProperties.getPort());
        dataSource.setDatabaseName(databaseProperties.getDatabase());
        dataSource.setUser(databaseProperties.getUsername());
        dataSource.setPassword(databaseProperties.getPassword());

        flyway.setDataSource(dataSource);

        flyway.migrate();
    }
}
