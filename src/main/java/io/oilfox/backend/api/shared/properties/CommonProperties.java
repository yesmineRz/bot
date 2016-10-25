package io.oilfox.backend.api.shared.properties;

import com.google.inject.Inject;

/**
 * Created by ipusic on 1/26/16.
 */
public class CommonProperties {
    @Inject
    private ApplicationPropertiesLoader loader;

    public String getServerUrl() {
        return loader.getProperty("server.url", "");
    }
}
