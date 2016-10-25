package io.oilfox.backend.db.entities;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by ipusic on 2/4/16.
 */
@Entity
@Table(name = "heizoel24_prices")
public class DbHeizoel24Price extends BaseEntity {
    public DbHeizoel24Price() {
        createdAt = new Date();
        refreshDayTag();
    }

    public void refreshDayTag() {
        daytag = new SimpleDateFormat("yyyy-MM-dd").format(createdAt);
    }

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid2")
    @NotNull
    public UUID id;

    @Column(name = "countrycode", length = 2)
    public String countrycode;

    @Column(name = "zipcode", length = 5)
    public String zipcode;

    @Column(name = "currency", length = 3)
    public String currency;

    @Column(name = "amount")
    public long amount;

    @Column(name = "taxrate")
    public double taxrate;

    @Column(name = "price")
    public double price;

    @Column(name = "daytag")
    private String daytag;
}
