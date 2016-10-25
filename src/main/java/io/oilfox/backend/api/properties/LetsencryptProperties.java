package io.oilfox.backend.api.properties;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.oilfox.backend.api.shared.properties.ApplicationPropertiesLoader;

/**
 * Created by ben on 07/02/16.
 */
@Singleton
public class LetsencryptProperties {

    @Inject
    private ApplicationPropertiesLoader loader;

    public boolean exists() {

        return getPath() != null
                && getPath().length() > 0
                && getValue() != null
                && getValue().length() > 0;
    }

    public String getPath() {
        return loader.getProperty("letsencrypt.path", "");
    }

    public String getValue() {
        return loader.getProperty("letsencrypt.value", "");
    }

}
