package io.oilfox.backend.api.shared.helpers;

import com.google.inject.Inject;
import io.oilfox.backend.db.entities.DbActivityLog;
import io.oilfox.backend.db.repositories.ActivityLogRepository;

/**
 * Created by ipusic on 1/27/16.
 */
public abstract class AbstractActivityLog {
    @Inject
    ActivityLogRepository activityLogRepository;

    public abstract String getOrigin();

    public DbActivityLog write(DbActivityLog.Topic topic, Object payload) {
        return write(topic, null, null, payload);
    }

    public DbActivityLog write(DbActivityLog.Topic topic, String url, String verb, Object payload) {
        DbActivityLog log = new DbActivityLog();
        log.httpUrl = url;
        log.httpVerb = verb;
        log.origin = getOrigin();
        log.topic = topic;
        log.payload = payload.toString();

        activityLogRepository.save(log);
        activityLogRepository.flush();

        return log;
    }
}
