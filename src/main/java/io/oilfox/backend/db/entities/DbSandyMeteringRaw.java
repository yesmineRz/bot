package io.oilfox.backend.db.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "sandy_metering_raw")
public class DbSandyMeteringRaw {

    public DbSandyMeteringRaw() {
        createdAt = new Date();
    }

    @Id
    @NotNull
    public long id;

    @NotNull
    public UUID guid;

    @NotNull
    public Float value;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    @NotNull
    public Date serverdate;

    @NotNull
    public UUID dataowner;

    @NotNull
    public UUID oilfoxid;

    @Column(name = "created_at")
    public Date createdAt;

    @Column(name = "updated_at")
    public Date updatedAt;
}
