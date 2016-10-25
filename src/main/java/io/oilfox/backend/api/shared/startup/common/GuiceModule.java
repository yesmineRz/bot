package io.oilfox.backend.api.shared.startup.common;

import com.google.inject.AbstractModule;
import io.oilfox.backend.db.db.SessionProvider;
import io.oilfox.backend.api.shared.startup.routines.StartupDatabase;

/**
 * Created by ben on 11/01/16.
 */
public class GuiceModule extends AbstractModule {
    @Override
    protected void configure() {

        bind(SessionProvider.class).to(StartupDatabase.class);
    }
}
