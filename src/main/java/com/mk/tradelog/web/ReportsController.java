package com.mk.tradelog.web;

import com.mk.tradelog.model.reports.simplereport.SimpleReportRequest;
import com.mk.tradelog.service.reportsdataservice.SimpleReportDataService;
import com.mk.tradelog.service.reportservice.web.SimpleReportModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

@Controller
@RequiredArgsConstructor
public class ReportsController {

    private final SimpleReportDataService dataService;
    private final SimpleReportModelService modelService;

    @GetMapping("/simplereport")
    public String simpleReport(Model model) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        fillModel(model, true);
        return "simplereport";
    }

    @GetMapping("/simplereportother")
    public String simpleReportOther(Model model) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        fillModel(model, false);
        return "simplereport";
    }

    private void fillModel(Model model, boolean includeTicker) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        SimpleReportRequest request = new SimpleReportRequest();
        request.setAccount("730044154");
        LocalDateTime now = LocalDateTime.now();
        request.setDateFrom(now.with(firstDayOfMonth()).with(ChronoField.NANO_OF_DAY, LocalTime.MIN.toNanoOfDay()));
        request.setDateTo(now.with(lastDayOfMonth()).with(ChronoField.NANO_OF_DAY, LocalTime.MAX.toNanoOfDay()));
        request.setTicker("AUDUSD");
        request.setIncludeTicker(includeTicker);
        request.setSubPeriod(ChronoField.DAY_OF_MONTH);
        model.addAllAttributes(modelService.getReportModel(dataService.createReportModel(request)));
    }

}
