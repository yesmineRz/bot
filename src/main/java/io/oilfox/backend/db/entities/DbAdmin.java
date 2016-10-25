package io.oilfox.backend.db.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by Yesmine on 18/10/16.
 */

@Entity
@DiscriminatorValue("admin")
@Table(name = "admin")
public class DbAdmin extends DbUser {


    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid2")
    @NotNull
    public UUID id;


}
