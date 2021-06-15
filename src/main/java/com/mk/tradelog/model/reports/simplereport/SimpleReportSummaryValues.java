package com.mk.tradelog.model.reports.simplereport;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SimpleReportSummaryValues {

    private final LocalDateTime localDateTime;
    private BigDecimal profitSum;
    private BigDecimal lossSum;
    private BigDecimal profitCount;
    private BigDecimal lossCount;
    private BigDecimal commissionsSum;
    private BigDecimal sum;


}
