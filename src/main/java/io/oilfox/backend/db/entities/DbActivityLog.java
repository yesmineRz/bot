package io.oilfox.backend.db.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

/**
 * Created by ipusic on 1/27/16.
 */
@Entity
@Table(name = "activity_log")
public class DbActivityLog extends BaseEntity {

    public enum Topic {
        USER_LOGIN,
        OILFOX_DETACH,
        METERING_REQUEST
    }

    public DbActivityLog() {
        createdAt = new Date();
    }

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid2")
    @NotNull
    public UUID id;

    @Column(length = 100)
    public String origin;

    @Column(name = "http_verb", length = 10)
    public String httpVerb;

    @Column(name = "http_url", length = 1500)
    public String httpUrl;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    public Topic topic;

    @Column(columnDefinition = "text")
    public String payload;
}
