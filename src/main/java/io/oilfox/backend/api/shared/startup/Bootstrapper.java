package io.oilfox.backend.api.shared.startup;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.oilfox.backend.api.shared.exception.OilfoxException;
import io.oilfox.backend.api.shared.startup.common.GuiceModule;
import io.oilfox.backend.api.shared.startup.common.StartupRoutine;
import io.oilfox.backend.api.shared.startup.routines.StartupDatabase;
import io.oilfox.backend.api.shared.startup.routines.StartupEnvironment;
import io.oilfox.backend.api.shared.startup.routines.StartupFlyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Bootstrapper implements AutoCloseable {

    private final Logger logger = LoggerFactory.getLogger(Bootstrapper.class);

    private final ArrayList<StartupRoutine> startupRoutines = new ArrayList<>();

    private boolean isStarted = false;

    private static Injector injector;

    public static  <T> T resolveInstance(Class<T> cls) {
        return injector.getInstance(cls);
    }

    public void start() throws OilfoxException {
        injector = Guice.createInjector();
        start(injector);
    }

    public void executeSpecific() throws OilfoxException {

    }

    public void start(Injector injector) throws OilfoxException {

        if (isStarted) {
            logger.warn("Bootstrapper has already started, duplicate start() call?");
            return;
        }

        isStarted = true;

        logger.info("Bootstrapper starting");

        Bootstrapper.injector = injector.createChildInjector(new GuiceModule());

        invoke(resolveInstance(StartupEnvironment.class));
        //invoke(resolveInstance(StartupFlyway.class));
        //invoke(resolveInstance(StartupDatabase.class));

        executeSpecific();

        logger.info("All set, application is ready");
    }

    protected void invoke(StartupRoutine routine) throws OilfoxException {

        startupRoutines.add(routine);

        String name = routine.getClass().getSimpleName();

        logger.info(String.format("Invoking routine '%s'", name));
        long start = System.nanoTime();

        routine.runWrapper();

        long stop = System.nanoTime() - start;

        logger.info(String.format("Routine '%s' finished in %dms", name, TimeUnit.MILLISECONDS.convert(stop, TimeUnit.NANOSECONDS)));
    }

    public void stop() {

        if (!isStarted) {
            logger.warn("Bootstrapper.stop() was called, but it has not started yet. ignoring.");
            return;
        }
        isStarted = false;


        for(int x = startupRoutines.size()-1; x >= 0; x--) {

            startupRoutines.get(x).stop();
        }
    }

    @Override
    public void close()  {
        stop();
    }
}
