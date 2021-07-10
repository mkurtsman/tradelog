package com.mk.tradelog.service.reportsdataservice;

import com.mk.tradelog.model.orders.Order;
import com.mk.tradelog.model.reports.simplereport.SimpleReportRequest;
import com.mk.tradelog.model.reports.simplereport.SimpleReportResponse;
import com.mk.tradelog.repsitory.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


@Component
@RequiredArgsConstructor
public class SimpleReportDataServiceImpl implements SimpleReportDataService {

    private final MarketRepository repository;

    @Override
    @Transactional
    public SimpleReportResponse createReportModel(SimpleReportRequest reportRequest) {

        Iterable<Order> orders = repository.findByParams(reportRequest.getAccount(),
                reportRequest.getDateFrom(),
                reportRequest.getDateTo(),
                reportRequest.getIgnoreTicker(),
                reportRequest.getEquals(),
                reportRequest.getTicker());
        Stream<Order> orderStream = StreamSupport.stream(orders.spliterator(), false);
        Map<LocalDate, List<Order>> map = orderStream.collect(Collectors.groupingBy(order -> order.getCloseDate().toLocalDate(), Collectors.toList()));

        SimpleReportResponse reportResponse = new SimpleReportResponse();
        reportResponse.setSubPeriodValues(map);
        reportResponse.setAccount(reportRequest.getAccount());
        reportResponse.setTicker(reportRequest.getTicker());
        reportResponse.setDateFrom(reportRequest.getDateFrom());
        reportResponse.setDateTo(reportRequest.getDateTo());
        return reportResponse;
    }


}
