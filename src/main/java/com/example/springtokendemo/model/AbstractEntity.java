package com.example.springtokendemo.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity {

    @Column(name ="created_at", updatable = false)
    @CreationTimestamp
    protected ZonedDateTime createdAt;


    @Column(name = "updated_at", insertable = false)
    @UpdateTimestamp
    protected ZonedDateTime updatedAt;

    public AbstractEntity() {
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public AbstractEntity setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
