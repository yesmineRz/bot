package io.oilfox.backend.db.entities;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by ipusic on 8/5/16.
 */
@Entity
@Table(name = "sensor")
public class DbSensor extends BaseEntity {
    @Id
    @NotNull
    public String hwid;

    public String notes;
}
