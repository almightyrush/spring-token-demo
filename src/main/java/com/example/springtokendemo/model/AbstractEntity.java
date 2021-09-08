package com.example.springtokendemo.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;


@MappedSuperclass
public abstract class AbstractEntity {

    @Column(name ="created_at", updatable = false)
    @CreationTimestamp
    protected  Timestamp createdAt;


    @Column(name = "updated_at", insertable = false)
    @UpdateTimestamp
    protected Timestamp updatedAt;

    public AbstractEntity() {
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public AbstractEntity setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
