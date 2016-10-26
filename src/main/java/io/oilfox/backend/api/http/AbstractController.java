package io.oilfox.backend.api.http;



import javax.ws.rs.core.Response;


/**
 * Created by ipusic on 1/21/16.
 */
public abstract class AbstractController {


    protected Response json(int status, Object entity) {
        return Response.status(status).entity(entity).build();
    }

    protected Response json(Object entity) {
        return json(200, entity);
    }
}
