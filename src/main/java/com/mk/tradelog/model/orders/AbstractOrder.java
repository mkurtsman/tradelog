package com.mk.tradelog.model.orders;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "AORDER")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class AbstractOrder {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    private String account;
    private LocalDateTime openDate;
    private OrderType type;
}
