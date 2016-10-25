package io.oilfox.backend.db.entities;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by ipusic on 8/1/16.
 */
@Table(name = "remaining_liters")
public class DbRemainingLiters extends BaseEntity {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid2")
    @NotNull
    public UUID id;

    public int liters;
}
