package io.oilfox.backend.api.shared.properties;

import com.google.inject.Inject;

/**
 * Created by ipusic on 1/26/16.
 */
public class EmailProperties {
    @Inject
    private ApplicationPropertiesLoader loader;

    public String getAccessToken() {
        return loader.getProperty("email.token", "").toString();
    }

    public boolean getSendEmail() {
        return Boolean.parseBoolean(loader.getProperty("email.sendMail", "false").toString());
    }
}
