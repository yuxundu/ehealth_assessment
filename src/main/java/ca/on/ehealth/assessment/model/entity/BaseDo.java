package ca.on.ehealth.assessment.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Basic Data Object - share common attributes
 * @author yuxundu
 */
@MappedSuperclass
@Getter
@Setter
@ToString
public abstract class BaseDo {
    @Id
    @GeneratedValue
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @Version
    private Long version;
    @Transient
    private String message;
    @PrePersist
    protected void onCreate() {
        Date now = new Date();
        createTime = now;
        updateTime = now;

    }

    @PreUpdate
    protected void onUpdate() {
        updateTime = new Date();
    }


}
