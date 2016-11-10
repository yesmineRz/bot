package io.oilfox.backend;

import io.oilfox.backend.api.controllers.*;
import io.oilfox.backend.api.properties.LetsencryptProperties;
import io.oilfox.backend.api.shared.exception.OilfoxException;
import io.oilfox.backend.api.shared.routing.SimpleRouter;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by ipusic on 1/21/16.
 */
@Singleton
public final class Router {

    @Inject
    private LetsencryptProperties letsencryptProperties;

    public void init(SimpleRouter router) throws NoSuchMethodException, OilfoxException {

        // public api
        router.get("/ping", PublicController.class, "getPing");
        router.get("/getfacebookid/{id}", PublicController.class, "getFacebookId");
        router.get("/getchart/{month1}/{value1}/{month2}/{value2}/{month3}/{value3}", ChartController.class, "getLineChart");
        router.get("/getstaticchart/{type}", ChartController.class, "getStaticChart");
        router.get("/uuu/{month1}/{value1}/{month2}/{value2}/{month3}/{value3}", ChartController.class, "uuu");
        //router.get("/json", ChartController.class, "accessJson");

        router.get("/getcurrentbalance/{id}", ChartController.class, "getCurrentBalance");
        router.get("/getpreviousbalancewithnumbers/{id}", ChartController.class, "getPreviousBalanceWithNumbers");
        router.get("/getfuturebalancewithnumbers/{id}", ChartController.class, "getFutureBalanceWithNumbers");
        router.get("/getpreviousbalancewithchart/{id}", ChartController.class, "getPreviousBalanceWithChart");
        router.get("/getfuturebalancewithchart/{id}", ChartController.class, "getFutureBalanceWithChart");
        router.get("/getlasttransactions/{id}", ChartController.class, "getLastTransactions");
        router.get("/getmorningmessage/{name}/{id}", ChartController.class, "getMorningMessage");
        router.get("/getfuturebalancenextmonth/{id}", ChartController.class, "getFutureBalanceNextMonth");
        router.get("/getreminder/{id}/{delay}/{type}", ChartController.class, "getReminder");

    }
}