package io.oilfox.backend.api.integration.controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import io.oilfox.backend.api.shared.test.helpers.TestBase;
import io.oilfox.backend.api.shared.test.helpers.TestContext;
import io.oilfox.backend.api.startup.ApiBootstrapper;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Yesmine on 27/10/16.
 */
public class GetFutureBalanceWithNumbersTest extends TestBase {

    private String URL = "http://localhost:9999/getfuturebalancewithnumbers";

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
            assertEquals("[{\"text\":\"Forecast:\\n\\nDecember: 50,100.00 EUR\\n\\nJanuary: 53,150.00 EUR\\n\\nFebruary: 57,400.00 EUR\\n\"}]", response.getBody());

        }

    }
}
