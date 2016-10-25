package io.oilfox.backend.api.properties;

import io.oilfox.backend.api.shared.properties.ApplicationPropertiesLoader;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by ipusic on 1/21/16.
 */
@Singleton
public class AppProperties {

    @Inject
    private ApplicationPropertiesLoader loader;

    public int getPort() {
        String envPort = System.getenv("PORT");
        return Integer.parseInt(loader.getProperty("app.port", envPort));
    }

    public String getListenAddress() {
        return loader.getProperty("app.listenAddress", "127.0.0.1").toString();
    }

    public String getPath() {
        return loader.getProperty("app.path", "").toString();
    }

    public String getVersion() {
        return loader.getProperty("app.version", "").toString();
    }

    public String getName() {
        return loader.getProperty("app.name", "").toString();
    }

    public String getSha1() {
        return loader.getProperty("app.sha1", "").toString();
    }
}