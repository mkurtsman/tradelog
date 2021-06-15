package com.mk.tradelog.service.reportservice.web;

import com.mk.tradelog.model.orders.Order;
import com.mk.tradelog.model.reports.simplereport.SimpleReport;
import com.mk.tradelog.model.reports.simplereport.SimpleReportResponse;
import com.mk.tradelog.model.reports.simplereport.SimpleReportRow;
import com.mk.tradelog.model.reports.simplereport.SimpleReportSubPeriodSums;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class SimpleReportModelServiceImpl implements SimpleReportModelService {


    @Override
    public Map<String, Object> getReportModel(SimpleReportResponse data) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("account", data.getAccount());
        attributes.put("ticker", data.getTicker());
        attributes.put("dateFrom", data.getDateFrom());
        attributes.put("dateTo", data.getDateTo());
        List<SimpleReportRow> rows = new LinkedList<>();
        attributes.put("reportData", rows);
        attributes.put("reportHeaders", new String[]{"date", "sum", "commissions", "result", "profit, count", "loss, count", "ticker"});
        attributes.put("summaryHeaders", new String[]{"date", "result", "profit, count", "loss, count", "count"});

        List<SimpleReportSubPeriodSums> sumRows = new LinkedList<>();
        for( Map.Entry<Long, List< Order>> entry : data.getSubPeriodValues().entrySet()){
            SimpleReportSubPeriodSums sum = new SimpleReportSubPeriodSums(entry.getKey());
            sumRows.add(sum);
            rows.add(sum);
            for(Order  order : entry.getValue()){
                SimpleReport reportRow = mapOrder(order);
                rows.add(reportRow);
                sum.add(reportRow);
            }
        }

        BigDecimal sumResult = sumRows.stream().map(SimpleReportSubPeriodSums::getResult).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal avgResult = sumRows.size() > 0 ? sumResult.divide(new BigDecimal(sumRows.size()), RoundingMode.HALF_UP) : BigDecimal.ZERO;

        Long profits = sumRows.stream().filter(r -> r.getResult().compareTo(BigDecimal.ZERO) > 0).count();
        Long losses = sumRows.stream().filter(r -> r.getResult().compareTo(BigDecimal.ZERO) < 0).count();

        attributes.put("sumResult", sumResult);
        attributes.put("avgResult", avgResult);
        attributes.put("profits", profits);
        attributes.put("losses", losses);


        Double avgCount = sumRows.stream().mapToInt(r -> r.getLossCount() +r.getProfitCount()).average().orElse(0.0);
        Double profitAvgCount = sumRows.stream().mapToInt(SimpleReportSubPeriodSums::getProfitCount).average().orElse(0.0);
        Double lossAvgCount = sumRows.stream().mapToInt(SimpleReportSubPeriodSums::getLossCount).average().orElse(0.0);
        Double plCoef = lossAvgCount.compareTo(0.0) != 0 ? profitAvgCount / lossAvgCount : 0.0;

        attributes.put("avgCount", avgCount);
        attributes.put("profitAvgCount", profitAvgCount);
        attributes.put("lossAvgCount", lossAvgCount);
        attributes.put("plCoef", plCoef);
        return attributes;
    }

    private SimpleReport mapOrder(Order order) {
        SimpleReport reportRow = new SimpleReport();
        reportRow.setProfit(order.getProfit());
        reportRow.setCommission(order.getCommission());
        reportRow.setVolume(order.getVolume());
        reportRow.setOtherFee(order.getSwap());
        reportRow.setTicker(order.getTicker());
        return reportRow;
    }
}
