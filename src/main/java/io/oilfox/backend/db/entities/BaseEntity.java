package io.oilfox.backend.db.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by ipusic on 1/25/16.
 */
@MappedSuperclass
public class BaseEntity {
    @NotNull
    @Column(name = "created_at")
    public Date createdAt;

    @Column(name = "updated_at")
    public Date updatedAt;

    @Column(name = "deleted_at")
    public Date deletedAt;
}
