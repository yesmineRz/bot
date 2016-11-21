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
public class GetLastTransactionsTest extends TestBase {

    private String URL = "http://localhost:9999/getlasttransactions";

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
            assertEquals("[{\"text\":\"1,560.00 EUR for communication, 96.00 EUR for lunch, 1,235.00 EUR for hardware, 12,800.00 EUR for salaries, 1,980.00 EUR for travels.\"}]", response.getBody());

        }

    }
}
