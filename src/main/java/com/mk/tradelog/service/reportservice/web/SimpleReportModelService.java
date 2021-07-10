package com.mk.tradelog.service.reportservice.web;

import com.mk.tradelog.model.reports.simplereport.Account;
import com.mk.tradelog.model.reports.simplereport.Strategy;

import java.time.LocalDate;
import java.util.Map;

public interface SimpleReportModelService {

    Map<String, Object> getReportModel(LocalDate dateFrom, LocalDate dateTo, Strategy strategy, Account account);

}
