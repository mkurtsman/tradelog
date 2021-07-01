package com.mk.tradelog.service.reportservice.web;

import com.mk.tradelog.model.reports.simplereport.SimpleReportResponse;

import java.util.Map;

public interface SimpleReportModelService {
    Map<String, Object> getReportModel(SimpleReportResponse data);

}
