package io.oilfox.backend.db.entities;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("user")
@Table(name = "user")
public class DbUser extends BaseEntity {

    public DbUser() {
        createdAt = new Date();
    }

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid2")
    public UUID id;

    @Column(unique = true)
    @NotNull
    public String email;

    public String mobile;

    public String passhash;

    public String firstname;

    public String lastname;

    public String country;

    @Column(name = "type", insertable = false, updatable = false)
    public String type;

    public String street;

    public String city;

    public String zipcode;

    @Column(name = "is_admin")
    public boolean isAdmin;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    public Date lastlogin;

    public UUID activationid;

    @NotNull
    public int status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    public List<DbTank> tanks = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    public List<DbOilfox> oilfoxes = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    public List<DbOrder> orders = new ArrayList<>();
}
