package io.oilfox.backend.api.shared.startup.routines;

import io.oilfox.backend.api.shared.startup.common.StartupRoutine;

import java.util.TimeZone;

/**
 * Created by ben on 13/01/16.
 */
public class StartupEnvironment extends StartupRoutine {
    @Override
    public void run() {
        // this mechanism is used twice:
        //   first in the Main.java (to make all logging calls behave consistently
        //   secondly in the Bootstrapper, to make all tests behave consistently

        System.setProperty("user.timezone", "GMT");
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
    }
}
