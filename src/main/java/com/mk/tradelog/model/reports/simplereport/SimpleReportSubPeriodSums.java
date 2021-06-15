package com.mk.tradelog.model.reports.simplereport;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
public class SimpleReportSubPeriodSums implements SimpleReportRow{
    private Long date;
    private Integer profitCount = 0;
    private Integer lossCount = 0;
    private BigDecimal profitSum = BigDecimal.valueOf(0.0);
    private BigDecimal commissionSum = new BigDecimal(0.0);

    public SimpleReportSubPeriodSums(Long date){
        this.date = date;
    }

    @Override
    public boolean isSummary() {
        return true;
    }

    public BigDecimal getResult(){
        return profitSum.subtract(commissionSum);
    }

    public void add(SimpleReport row){
        if(row.getProfit() != null){
            if(row.getProfit().compareTo(BigDecimal.ZERO) > 0){
                profitCount = profitCount + 1;
            } else {
                lossCount = lossCount+ 1;
            }

            profitSum = profitSum.add(row.getProfit());

            if(row.getCommission() != null){
                commissionSum = commissionSum.add(row.getCommission().abs());
            }

            if(row.getOtherFee() != null){
                commissionSum = commissionSum.add(row.getOtherFee().abs());
            }
        }
    }
}
