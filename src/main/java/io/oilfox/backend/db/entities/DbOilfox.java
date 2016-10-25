package io.oilfox.backend.db.entities;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "oilfox")
public class DbOilfox extends BaseEntity {

    public DbOilfox() {
        createdAt = new Date();
    }

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid2")
    @NotNull
    public UUID id;

    @NotNull
    @Column(length = 12)
    public String hwid;

    @NotNull
    public UUID token;

    @Column(length = 200)
    public String ssid;

    public Float battery;

    @Column(name = "last_metering_value")
    public Float lastMeteringValue;

    @Column(name = "last_metering_date")
    public Date lastMeteringDate;

    public String notes;

    @ManyToOne
    @JoinColumn(name = "userid")
    public DbUser user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    public Date lastseen;

    @ManyToOne
    @JoinColumn(name = "tankid")
    public DbTank tank;

    @NotNull
    public int status;

    public String name;
}
