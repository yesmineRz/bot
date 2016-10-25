package io.oilfox.backend.api.shared.properties;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Created by ben on 13/07/16.
 */
@Singleton
public class FlywayProperties {
    @Inject
    private ApplicationPropertiesLoader loader;

    public boolean getEnabled() {
        return Boolean.parseBoolean(loader.getProperty("flyway.enabled", "true"));
    }
}
