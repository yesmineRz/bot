package io.oilfox.backend.db.entities;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "session")
public class DbSession extends BaseEntity {

    public DbSession() {
        createdAt = new Date();
    }

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid2")
    @NotNull
    public UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "userid")
    public DbUser user;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    public Date lastactive;
}
