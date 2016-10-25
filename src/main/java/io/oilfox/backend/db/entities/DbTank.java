package io.oilfox.backend.db.entities;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tank")
public class DbTank extends BaseEntity {

    public DbTank() {
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
    public int shape;

    public Integer volume;

    public Integer height;

    public Integer length;

    public String name;

    public String street;

    public String country;

    public String city;

    public String zipcode;

    @Column(name = "distance_from_tank_to_oilfox")
    public int distanceFromTankToOilFox;

    @Column(name = "max_volume")
    public int maxVolume;

    @OneToMany(mappedBy = "tank")
    public List<DbOilfox> oilfoxes = new ArrayList<>();
}
