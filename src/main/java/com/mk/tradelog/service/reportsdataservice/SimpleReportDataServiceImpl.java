package com.mk.tradelog.service.reportsdataservice;

import com.mk.tradelog.model.orders.AbstractOrder;
import com.mk.tradelog.model.orders.Operations;
import com.mk.tradelog.model.orders.Order;
import com.mk.tradelog.model.orders.OrderType;
import com.mk.tradelog.model.reports.simplereport.SimpleReportRequest;
import com.mk.tradelog.model.reports.simplereport.SimpleReportResponse;
import com.mk.tradelog.service.dataprovider.DataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


@Component
@RequiredArgsConstructor
public class SimpleReportDataServiceImpl implements SimpleReportDataService {

    private final DataProvider dataProvider;

    @Override
    public SimpleReportResponse createReportModel(SimpleReportRequest reportRequest) {
        Stream<AbstractOrder> orderStream = StreamSupport.stream(dataProvider.getData().spliterator(), false);
        Stream<Order> orderList = orderStream.filter(new FilterPredicate(reportRequest)).map(e -> (Order) e);
        Map<Long, List<Order>> map = orderList.collect(Collectors.groupingBy(order -> reportRequest.getSubPeriod().getFrom(order.getCloseDate()), Collectors.toList()));

        SimpleReportResponse reportResponse = new SimpleReportResponse();
        reportResponse.setSubPeriodValues(map);
        reportResponse.setAccount(reportRequest.getAccount());
        reportResponse.setTicker(reportRequest.getTicker());
        reportResponse.setDateFrom(reportRequest.getDateFrom());
        reportResponse.setDateTo(reportRequest.getDateTo());
        return reportResponse;
    }

    private class FilterPredicate implements Predicate<AbstractOrder> {

        private final SimpleReportRequest request;

        public FilterPredicate(SimpleReportRequest request) {
            this.request = request;
        }

        @Override
        public boolean test(AbstractOrder abstractOrder) {
            if (OrderType.getOperation(abstractOrder.getType()) != Operations.market) {
                return false;
            }
            Order order = (Order) abstractOrder;

            if (!(request.getAccount() == null || request.getAccount().equals(abstractOrder.getAccount()))) {
                return false;
            } else if (OrderType.getOperation(abstractOrder.getType()) != Operations.market) {
                return false;
            } else if (request.getTicker() != null && (request.isIncludeTicker() ^ request.getTicker().equalsIgnoreCase(order.getTicker()))) {
                return false;
            } else if (request.getDateFrom() == null && request.getDateTo() == null) {
                return true;
            } else if (request.getDateFrom() == null) {
                return request.getDateTo().isAfter(order.getCloseDate());
            } else if (request.getDateTo() == null) {
                return request.getDateFrom().isBefore(order.getCloseDate());
            } else {
                return request.getDateFrom().isBefore(order.getCloseDate()) && request.getDateTo().isAfter(order.getCloseDate());
            }
        }
    }


}
