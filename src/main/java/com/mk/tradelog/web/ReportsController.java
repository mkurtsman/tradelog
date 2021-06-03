package com.mk.tradelog.web;

import com.mk.tradelog.model.reports.simplereport.SimpleReportRequest;
import com.mk.tradelog.model.web.Account;
import com.mk.tradelog.model.web.Strategy;
import com.mk.tradelog.service.reportsdataservice.SimpleReportDataService;
import com.mk.tradelog.service.reportservice.web.SimpleReportModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoField;

@Controller
@RequiredArgsConstructor
public class ReportsController {

    private final SimpleReportDataService dataService;
    private final SimpleReportModelService modelService;

    @GetMapping("/simplereport")
    public String simpleReport(Model model,
                               @RequestParam
                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                       LocalDate dateFrom,
                               @RequestParam
                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                       LocalDate dateTo,
                               @RequestParam
                                        Strategy strategy,
                               @RequestParam
                                       Account account) {
        SimpleReportRequest request = new SimpleReportRequest();
        request.setAccount(account == null ? null : account.getValue());
        request.setDateFrom(dateFrom.atStartOfDay());
        request.setDateTo(dateTo.atStartOfDay().with(ChronoField.NANO_OF_DAY, LocalTime.MAX.toNanoOfDay()));
        if( strategy == Strategy.ALL) {
            request.setIgnoreTicker(1);
        } else if (strategy == Strategy.H1) {
            request.setIgnoreTicker(0);
            request.setEquals(0);
            request.setTicker("audusd");
        } else if(strategy == Strategy.AUDUSDM5){
            request.setIgnoreTicker(0);
            request.setEquals(1);
            request.setTicker("audusd");
        } else {
            throw new RuntimeException("incorrect strategy type");
        }
        model.addAllAttributes(modelService.getReportModel(dataService.createReportModel(request)));
        return "simplereport";
    }

}
