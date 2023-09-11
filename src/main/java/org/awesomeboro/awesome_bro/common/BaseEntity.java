package org.awesomeboro.awesome_bro.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Column(length = 1,nullable = false, columnDefinition = "y")
    private String useYn = "y";

    @CreatedDate
    @Column(nullable = false)
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Timestamp updatedAt;
}