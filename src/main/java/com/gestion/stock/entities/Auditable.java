package com.gestion.stock.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * Class Auditable
 * SuperClass des entités à auditer
 *
 * @param <U>
 * @author Mouhamed NDOYE
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public class Auditable<U> implements Serializable {

    @JsonIgnore
    @CreatedBy
    protected U createdBy;

    @CreatedDate
    protected Timestamp createdDate;

    @JsonIgnore
    @LastModifiedBy
    protected U lastModifiedBy;

    @LastModifiedDate
    protected Timestamp lastModifiedDate;

    // getters and setter here
}
