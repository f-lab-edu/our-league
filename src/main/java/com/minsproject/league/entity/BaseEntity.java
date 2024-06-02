package com.minsproject.league.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Getter
    @Column(updatable = false)
    @CreatedDate
    private Timestamp createdAt;

    @Getter
    @LastModifiedDate
    private Timestamp modifiedAt;

    @Getter
    private Timestamp removedAt;

    public void softDelete() {
        this.removedAt = new Timestamp(System.currentTimeMillis());
    }
}
