package com.mk.tradelog.model.reports.simplereport;

import lombok.Data;

import java.math.BigDecimal;
import java.math.MathContext;

@Data
public class SimpleReport implements SimpleReportRow {
    private BigDecimal profit;
    private BigDecimal volume;
    private BigDecimal commission;
    private BigDecimal otherFee;
    private String ticker;
    private Long id;

    @Override
    public boolean isSummary() {
        return false;
    }

    public BigDecimal getResult(){
        return profit!=null && commission != null ? profit.subtract(commission.abs()) :
                (profit!=null ? profit : commission) ;
    }

    public BigDecimal getPoints(){
        return profit.divide(volume, MathContext.DECIMAL32);
    }

}
