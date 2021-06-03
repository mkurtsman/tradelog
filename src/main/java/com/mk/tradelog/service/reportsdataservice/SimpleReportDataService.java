package com.mk.tradelog.service.reportsdataservice;

import com.mk.tradelog.model.reports.simplereport.SimpleReportRequest;
import com.mk.tradelog.model.reports.simplereport.SimpleReportResponse;

public interface SimpleReportDataService {
    SimpleReportResponse createReportModel(SimpleReportRequest reportRequest);
}
