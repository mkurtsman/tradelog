package com.mk.tradelog.model.reports.simplereport;

import com.mk.tradelog.model.common.Strategy;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

@Data
public class SimpleReportRequest {
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private String account;
    private String ticker;
    private Strategy strategy;

}
