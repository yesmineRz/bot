package io.oilfox.backend.api.shared.routing;

import io.oilfox.backend.api.shared.annotations.Authenticated;
import io.oilfox.backend.api.shared.exception.OilfoxException;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.lang.reflect.Method;
import java.util.HashSet;

/**
 * Created by ipusic on 1/21/16.
 */
public class SimpleJerseyRouter extends SimpleRouter {

    private static final Logger logger = LoggerFactory.getLogger(SimpleJerseyRouter.class);

    private HashSet<Resource> resources = new HashSet<>();

    private boolean isRegistered = false;

    public void register(ResourceConfig rc) throws OilfoxException {

        if (isRegistered) {
            throw new OilfoxException("SimpleJerseyRouter already registered its routes");
        }

        rc.registerResources(resources);

        isRegistered = true;
    }

    @Override
    public void method(String httpMethod, String path, Class<?> handlerClass, String handlerMethod) throws OilfoxException {
        Resource.Builder builder = Resource.builder();

        Method found = null;

        for(Method needle : handlerClass.getMethods()) {
            if (needle.getName() == handlerMethod) {
                found = needle;
                break;
            }
        }

        if (found == null) {

            String error = "Route %s %s should be managed by controller '%s' and method '%s', but method '%3$s.%4$s' could not be found";
            throw new OilfoxException(String.format(error, httpMethod, path, handlerClass.getSimpleName(), handlerMethod));
        }

        builder.path(path);
        ResourceMethod.Builder methodBuilder = builder.addMethod(httpMethod);

        Consumes anConsumes = found.getAnnotation(Consumes.class);
        if (anConsumes!=null) methodBuilder.consumes(anConsumes.value());

        Produces anProduces = found.getAnnotation(Produces.class);
        if (anProduces!=null) methodBuilder.produces(anProduces.value());

        Authenticated authenticated = found.getAnnotation(Authenticated.class);
        if (authenticated != null) {
            methodBuilder.nameBindings(authenticated);
        }

        methodBuilder.handledBy(handlerClass, found);

        resources.add(builder.build());
    }
}