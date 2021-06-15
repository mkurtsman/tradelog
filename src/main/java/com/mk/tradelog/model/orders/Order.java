package com.mk.tradelog.model.orders;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Order extends AbstractOrder {

    private BigDecimal volume;
    private String ticker;
    private BigDecimal openPrice;
    private BigDecimal closePrice;
    private BigDecimal stopLoss;
    private BigDecimal takeProfit;
    private LocalDateTime closeDate;
    private BigDecimal commission;
    private BigDecimal tax;
    private BigDecimal swap;
    private BigDecimal profit;

}
