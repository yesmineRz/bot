package io.oilfox.backend.api.startup;

import io.oilfox.backend.api.startup.routines.StartupDefaultFixtures;
import io.oilfox.backend.api.startup.routines.StartupHttpServer;
import io.oilfox.backend.api.shared.exception.OilfoxException;
import io.oilfox.backend.api.shared.startup.Bootstrapper;

/**
 * Created by ipusic on 1/21/16.
 */
public class ApiBootstrapper extends Bootstrapper {
    @Override
    public void executeSpecific() throws OilfoxException {
        //invoke(resolveInstance(StartupDefaultFixtures.class));
        invoke(resolveInstance(StartupHttpServer.class));
    }
}
