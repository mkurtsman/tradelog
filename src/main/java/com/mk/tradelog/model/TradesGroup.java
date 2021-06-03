package com.mk.tradelog.model;

import com.mk.tradelog.model.orders.AbstractOrder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TradesGroup implements Domain{
    private final String id;
    private final TradeGroupingResult tradeGroupingResult;
    private final Date date;
    private final String ticker;
    private final List<AbstractOrder> abstractOrders;

}
