package io.oilfox.backend.db.entities;

/**
 * Created by Yesmine on 12/07/16.
 */


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "order")
public class DbOrder extends BaseEntity {

    public DbOrder() {
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

    public double  volume;

    public double  price;

    public String notes;

    public String status;
}