package io.oilfox.backend.api.shared.properties;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DatabaseProperties {

    @Inject
    private ApplicationPropertiesLoader loader;

    public String getDatabase() {

        return loader.getProperty("db.database", "").toString();
    }

    public String getUsername() {

        return loader.getProperty("db.username", "").toString();
    }

    public String getPassword() {

        return loader.getProperty("db.password", "").toString();
    }

    public String getHostname() {

        return loader.getProperty("db.hostname", "").toString();
    }

    public int getPort() {

        return Integer.parseInt(loader.getProperty("db.port", "5432").toString());
    }

    public boolean getLoadMigrations() {

        return Boolean.parseBoolean(loader.getProperty("db.loadMigrations", "true").toString());
    }
}
