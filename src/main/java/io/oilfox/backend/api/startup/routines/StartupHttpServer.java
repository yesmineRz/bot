package io.oilfox.backend.api.startup.routines;

import io.oilfox.backend.Router;
import io.oilfox.backend.api.properties.AppProperties;
import io.oilfox.backend.api.provider.factories.SessionFactory;
import io.oilfox.backend.api.startup.CustomContainerLifecycleListener;
import io.oilfox.backend.db.entities.DbSession;
import io.oilfox.backend.db.db.SessionProvider;
import io.oilfox.backend.api.shared.exception.OilfoxException;
import io.oilfox.backend.api.shared.routing.SimpleJerseyRouter;
import io.oilfox.backend.api.shared.startup.common.StartupRoutine;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URI;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ipusic on 1/21/16.
 */

@Singleton
public class StartupHttpServer extends StartupRoutine {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(StartupHttpServer.class);

    @Inject
    private AppProperties appProperties;

    @Inject
    private Router router;

    @Inject
    private CustomContainerLifecycleListener customContainerLifecycleListener;

    @Inject
    SessionProvider sessionProvider;

    private HttpServer server;

    private URI createUri() {

        String uri = String.format("http://%s:%d/%s",
                appProperties.getListenAddress(),
                appProperties.getPort(),
                appProperties.getPath());

        return URI.create(uri);
    }

    private void createServer() throws NoSuchMethodException, OilfoxException {

        logger.debug("Scanning for resource configurations");
        final ResourceConfig rc = new ResourceConfig()
                .packages("io.oilfox.backend.api");

        rc.register(customContainerLifecycleListener);
        rc.register(JacksonFeature.class);

        rc.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindFactory(SessionFactory.class)
                        .to(DbSession.class);
            }
        });

        URI uri = createUri();

        logger.debug("Applying routes");

        SimpleJerseyRouter simpleJerseyRouter = new SimpleJerseyRouter();
        router.init(simpleJerseyRouter);
        simpleJerseyRouter.register(rc);

        logger.info("Starting web server on " + uri);

        Logger l = Logger.getLogger("org.glassfish.grizzly.http.server.HttpHandler");
        l.setLevel(Level.FINE);
        l.setUseParentHandlers(false);
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.ALL);
        l.addHandler(ch);

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        server = GrizzlyHttpServerFactory.createHttpServer(uri, rc);

        //String staticPath = Paths.get(".", appProperties.getStaticFilesPath()).toAbsolutePath().normalize().toString();
        //server.getServerConfiguration().addHttpHandler(new StaticHttpHandler(staticPath), appProperties.getStaticFilesUrl());
    }

    @Override
    public void run() throws NoSuchMethodException, OilfoxException {
        createServer();
    }

    @Override
    public void stop() {
        server.shutdown();
    }
}
