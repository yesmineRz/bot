package io.oilfox.backend.api.startup;

import com.google.inject.Injector;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

/**
 * Created by ipusic on 1/21/16.
 */
@Singleton
public class CustomContainerLifecycleListener implements ContainerLifecycleListener {

    private static final Logger logger = LoggerFactory.getLogger(CustomContainerLifecycleListener.class);

    @com.google.inject.Inject
    private Injector injector;

    @Override
    public void onStartup(Container container) {

        logger.debug("Attaching guice injector to jersey/hk2");

        ServiceLocator locator = container.getApplicationHandler().getServiceLocator();
        GuiceBridge bridge = GuiceBridge.getGuiceBridge();
        bridge.initializeGuiceBridge(locator);

        GuiceIntoHK2Bridge guiceBridge = locator.getService(GuiceIntoHK2Bridge.class);
        guiceBridge.bridgeGuiceInjector(injector);
    }

    @Override
    public void onReload(Container container) {

    }

    @Override
    public void onShutdown(Container container) {

    }
}