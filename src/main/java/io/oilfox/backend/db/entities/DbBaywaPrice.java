package io.oilfox.backend.db.entities;

/**
 * Created by Yesmine on 06/09/16.
 */

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "baywa_prices")
public class DbBaywaPrice extends BaseEntity {
    public DbBaywaPrice() {
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

    @Column(name = "regioncode", length = 2)
    public String regioncode;

    @Column(name = "regionname", length = 20)
    public String regionname;

    @Column(name = "price")
    public double price;

    @Column(name = "daytag")
    private String daytag;
}
