package io.oilfox.backend.db.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by Yesmine on 08/09/16.
 */
@Entity
@DiscriminatorValue("partner")
@Table(name = "partner")
public class DbPartner extends DbUser {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid2")
    @NotNull
    public UUID id;


}
