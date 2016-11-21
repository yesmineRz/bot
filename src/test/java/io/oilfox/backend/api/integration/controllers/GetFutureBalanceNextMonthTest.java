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
public class GetFutureBalanceNextMonthTest extends TestBase {

    private String URL = "http://localhost:9999/getfuturebalancenextmonth";

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
            assertEquals("[{\"text\":\"Based on your regular in- and outflows you will have 50,100.00 EUR\"}]\"}]", response.getBody());

        }

    }
}
