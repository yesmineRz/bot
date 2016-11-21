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
 * Created by Yesmine on 27/10/16.
 */
public class GetPreviousBalanceWithNumbersTest extends TestBase {

    private String URL = "http://localhost:9999/getpreviousbalancewithnumbers";

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
            assertEquals("[{\"text\":\"Report:\\n\\nAugust: 38,260.00 EUR\\n\\nSeptember: 40,500.00 EUR\\n\\nOctober: 45,940.00 EUR\\n\"}]", response.getBody());

        }

    }
}
