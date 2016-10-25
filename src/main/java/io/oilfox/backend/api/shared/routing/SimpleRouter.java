package io.oilfox.backend.api.shared.routing;

import io.oilfox.backend.api.shared.exception.OilfoxException;

/**
 * Created by ben on 26/01/16.
 */
public abstract class SimpleRouter {

    public abstract void method(String httpMethod, String path, Class<?> handlerClass, String handlerMethod) throws OilfoxException;

    public void get(String path, Class<?> handlerClass, String method) throws OilfoxException {
        method("GET", path, handlerClass, method);
    }

    public void post(String path, Class<?> handlerClass, String method) throws OilfoxException {
        method("POST", path, handlerClass, method);
    }

    public void put(String path, Class<?> handlerClass, String method) throws OilfoxException {
        method("PUT", path, handlerClass, method);
    }

    public void delete(String path, Class<?> handlerClass, String method) throws OilfoxException {
        method("DELETE", path, handlerClass, method);
    }

    public void patch(String path, Class<?> handlerClass, String method) throws OilfoxException {
        method("PATCH", path, handlerClass, method);
    }

    public void options(String path, Class<?> handlerClass, String method) throws OilfoxException {
        method("OPTIONS", path, handlerClass, method);
    }
}
