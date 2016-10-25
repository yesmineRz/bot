package io.oilfox.backend.db.entities;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "metering")
public class DbMetering extends BaseEntity {

    public DbMetering() {
        createdAt = new Date();
    }

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid2")
    @NotNull
    public UUID id;

    @NotNull
    public Float value;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    @NotNull
    public Date serverdate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    @Null
    public Date clientdate;

    @NotNull
    public UUID tankid;
}
