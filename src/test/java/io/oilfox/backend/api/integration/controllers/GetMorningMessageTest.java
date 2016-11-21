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
public class GetMorningMessageTest extends TestBase {

    private String URL = "http://localhost:9999/getmorningmessage/username";

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
            assertEquals("[{\"text\":\"Everything is fine.\\nGood morning username!\\nHere is your personal financial overview for Nov 21.\\nYou have 43,567.00 EUR on your current account and an overdraft facility of 20,000.00 EUR. Today you a have a scheduled outbound payment of 2,500.00 EUR for rent. You will be fine.\\nHave a nice day.\"}]", response.getBody());

        }

    }
}
