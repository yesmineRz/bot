package io.oilfox.backend.api.properties;

import io.oilfox.backend.api.shared.properties.ApplicationPropertiesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by ipusic on 1/21/16.
 */
@Singleton
public class AppProperties {
    private final static Logger logger = LoggerFactory.getLogger(AppProperties.class);

    @Inject
    private ApplicationPropertiesLoader loader;


    public int getPort() {
        logger.info(String.format("Current port: %s", loader.getProperty("app.port", null)));
        return Integer.parseInt(loader.getProperty("app.port", null));
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