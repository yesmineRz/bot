package io.oilfox.backend.api.controllers;


import io.oilfox.backend.api.http.AbstractController;
import io.oilfox.backend.api.shared.annotations.*;

import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


/**
 * Created by ipusic on 1/22/16.
 */
@ApiDocController
public class PublicController extends AbstractController{



    @Produces("text/plain")
    @ApiDocDescription("Returns Pong")
    public Response getPing() {
        return Response.ok("Pong").status(200).build();
    }

    @Authenticated
    @Produces("application/json")
    @ApiDocDescription("Returns facebook id")
    public Response getFacebookId(@PathParam("id") String id) throws Exception {


        String message = "[{\"text\":\"your Facebook ID is "+id+"\"}]";


        return json(message);
    }
}
