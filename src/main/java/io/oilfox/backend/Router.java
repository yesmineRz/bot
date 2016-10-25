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

    }
}