package io.oilfox.backend.api.integration.controllers;

import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import io.oilfox.backend.api.startup.ApiBootstrapper;
import io.oilfox.backend.api.shared.test.helpers.TestBase;
import io.oilfox.backend.api.shared.test.helpers.TestContext;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Yesmine on 10/26/16.
 */
public class GetChartTest extends TestBase {

    private String URL = "http://localhost:9999/getchart/oktober/200/September/100/December/400";

    @BeforeClass
    public static final void classSetup() throws Exception {
        setupUnirest();
    }

    @Test
    public void ping_returns200AndPongWithDebugHeaders() throws Exception {

        try (TestContext ctx = new TestContext(ApiBootstrapper.class)) {
            HttpResponse<String> response = Unirest.get(URL)
                    .asString();

            assertEquals(200, response.getStatus());
            //assertEquals("Pong", response.getBody());

        }

    }
}
