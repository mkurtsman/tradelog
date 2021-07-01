package com.mk.tradelog.model.orders;

import com.mk.tradelog.model.Domain;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "AORDER")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class AbstractOrder implements Domain {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    private String account;
    private LocalDateTime openDate;
    private OrderType type;
}
