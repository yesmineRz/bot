package io.oilfox.backend.db.db;

import org.hibernate.Session;

/**
 * Created by ipusic on 1/21/16.
 */
public interface SessionProvider {

    Session open();
}