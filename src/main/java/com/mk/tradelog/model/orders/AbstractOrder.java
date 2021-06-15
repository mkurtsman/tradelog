package com.mk.tradelog.model.orders;

import com.mk.tradelog.model.Domain;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class AbstractOrder implements Domain {
    private Long id;
    private String account;
    private LocalDateTime openDate;
    private OrderType type;
}
