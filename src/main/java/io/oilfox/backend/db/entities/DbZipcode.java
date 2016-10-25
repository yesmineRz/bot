package io.oilfox.backend.db.entities;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by ipusic on 5/9/16.
 */
@Entity
@Table(name = "zipcode")
public class DbZipcode extends BaseEntity {

    public DbZipcode() {
        createdAt = new Date();
    }

    @Id
    public String code;
}
