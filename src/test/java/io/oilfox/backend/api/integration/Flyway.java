package io.oilfox.backend.api.integration;

import io.oilfox.backend.api.startup.ApiBootstrapper;
import io.oilfox.backend.api.shared.properties.ApplicationPropertiesLoader;
import io.oilfox.backend.api.shared.test.helpers.TestBase;
import io.oilfox.backend.api.shared.test.helpers.TestContext;
import org.junit.Test;

public class Flyway extends TestBase {

    @Test
    public void get_validRequest_expectConfiguration() throws Exception {
        try (TestContext ctx = new TestContext(ApiBootstrapper.class, ((injector) -> {
            ApplicationPropertiesLoader loader = injector.getInstance(ApplicationPropertiesLoader.class);
            loader.setProperty("flyway.enabled", "true");
            loader.setProperty("hibernate.hbm2ddl.auto", "validate");
        }))) {

            // intentionally left blank
            // this test will both execute the migrations of flyway
            // AND the schema validation of Hibernate

        }
    }
}

