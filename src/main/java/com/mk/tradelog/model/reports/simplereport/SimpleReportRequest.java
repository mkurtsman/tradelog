package com.mk.tradelog.model.reports.simplereport;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

@Data
public class SimpleReportRequest {
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private String account;
    private String ticker;
    private Integer ignoreTicker;
    private Integer equals;

}
