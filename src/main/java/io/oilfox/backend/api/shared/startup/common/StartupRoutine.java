package io.oilfox.backend.api.shared.startup.common;

import io.oilfox.backend.api.shared.exception.OilfoxException;

public abstract class StartupRoutine implements StartupRunnable {

    public void runWrapper() throws OilfoxException {

        try {
            this.run();
        }
        catch(Exception ex) {
            throw new OilfoxException("Error executing startup routine " + this.getClass().getSimpleName(), ex);
        }
    }

    public void stop() {

    }
}
