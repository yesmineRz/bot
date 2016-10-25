package io.oilfox.backend.db.entities;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "offer")
public class DbOffer extends BaseEntity {

    public DbOffer() {
        createdAt = new Date();
    }

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid2")
    @NotNull
    public UUID id;

    @NotNull
    public UUID tankid;

    @NotNull
    public UUID userid;

    @Temporal(TemporalType.TIMESTAMP)
    @Null
    public Date created;

    @Null
    public int status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    @Null
    public Date lastdispatch;
}
