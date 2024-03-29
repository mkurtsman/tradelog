package com.mk.tradelog.model.reports.simplereport;

import com.mk.tradelog.model.db.orders.Order;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Map;

@Data
public class SimpleReportResponse {

    private String account;
    private String ticker;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private ChronoField subPeriod;
    private Map<LocalDate, List<Order>> subPeriodValues;

}
