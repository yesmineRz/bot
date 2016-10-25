package io.oilfox.backend;

import io.oilfox.backend.api.startup.ApiBootstrapper;
import io.oilfox.backend.api.shared.startup.Bootstrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimeZone;

/**
 * Created by ipusic on 1/22/16.
 */
public class Main {

    private final static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws NoSuchMethodException {

        // this mechanism is used twice:
        //   first in the Main.java (to make all logging calls behave consistently
        //   secondly in the Bootstrapper, to make all tests behave consistently
        System.setProperty("user.timezone", "GMT");
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));

        logger.info("Application is starting");

        final Bootstrapper bootstrapper = new ApiBootstrapper();

        try {
            bootstrapper.start();
        }
        catch(Exception ex) {

            logger.error("Bootstrap failed, bailing", ex);
            System.exit(1);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                logger.warn("External shutdown detect, cleaning up");
                bootstrapper.stop();
            }
        }, "shutdownHook"));

        try {
            Thread.currentThread().join();
        } catch (Exception e) {
        }
    }
}