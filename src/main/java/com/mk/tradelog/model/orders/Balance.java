package com.mk.tradelog.model.orders;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Balance extends AbstractOrder {

    private BigDecimal profit;
    private String comment;

}
