package io.oilfox.backend.api.shared.test.helpers;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.oilfox.backend.api.shared.properties.ApplicationPropertiesLoader;

/**
 * Created by ipusic on 1/21/16.
 */
public class InjectorHelper {
    public static final Injector createInjector() {

        Injector injector = Guice.createInjector();
        ApplicationPropertiesLoader loader = injector.getInstance(ApplicationPropertiesLoader.class);

        loader.setProperty("db.database", "oilfox_test");
        loader.setProperty("app.port", "9999");
        loader.setProperty("hibernate.hbm2ddl.auto", "none");
        loader.setProperty("email.sendMail", "false");

        return injector;
    }
}
