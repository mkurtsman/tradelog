package com.mk.tradelog.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TradeResult implements Domain{
   private final String id;
   private final BigDecimal profit;
   private final BigDecimal volume;
}
