package io.oilfox.backend.api.common;

import com.google.inject.Inject;
import io.oilfox.backend.db.entities.DbSession;
import io.oilfox.backend.db.entities.DbUser;
import io.oilfox.backend.db.repositories.SessionRepository;

import java.util.Date;
import java.util.UUID;

/**
 * Created by ipusic on 1/22/16.
 */
public class SessionManager {
    @Inject
    private SessionRepository sessionRepository;

    public UUID generateToken(DbUser user) {
        DbSession db = new DbSession();
        db.lastactive = new Date();
        db.user = user;

        sessionRepository.save(db);
        sessionRepository.flush();

        return db.id;
    }
}
