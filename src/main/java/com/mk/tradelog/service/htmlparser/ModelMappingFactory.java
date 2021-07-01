package com.mk.tradelog.service.htmlparser;

import com.mk.tradelog.model.orders.*;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ModelMappingFactory {

    //2021.06.01 11:18:35
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:s");

    public AbstractOrder mapRow(Element element, String accountString) {

        OrderType orderType = OrderType.byCode(element.select("td").get(2).text());
        Operations operation = OrderType.getOperation(orderType);

        switch (operation) {
            case market:
                return fillOrder(accountString, element);
            case pending:
                return fillCanceledOrder(accountString, element);
            case balance:
                return fillBalance(accountString, element);
            default:
                throw new RuntimeException("Couldn't parse model");
        }

    }

    private CancelledOrder fillCanceledOrder(String accountString, Element element) {
        CancelledOrder cancelledOrder = new CancelledOrder();
        fillCommonFields(cancelledOrder, accountString, element);
        Elements cells = element.select("td");
        BigDecimal size = new BigDecimal(cells.get(3).text());
        cancelledOrder.setVolume(size);
        cancelledOrder.setTicker(cells.get(4).text().toLowerCase());
        BigDecimal openPrice = new BigDecimal(cells.get(5).text());
        cancelledOrder.setOpenPrice(openPrice);
        BigDecimal stopLoss = new BigDecimal(cells.get(6).text());
        cancelledOrder.setStopLoss(stopLoss);
        BigDecimal takeProfit = new BigDecimal(cells.get(7).text());
        cancelledOrder.setTakeProfit(takeProfit);
        LocalDateTime closeDateTime = LocalDateTime.parse(cells.get(8).text(), formatter);
        cancelledOrder.setCloseTime(closeDateTime);
        BigDecimal closePrice = new BigDecimal(cells.get(9).text());
        cancelledOrder.setClosePrice(closePrice);
        String comment = cells.get(10).text();
        cancelledOrder.setComment(comment);
        return cancelledOrder;

    }

    private Balance fillBalance(String accountString, Element element) {
        Balance balance = new Balance();
        fillCommonFields(balance, accountString, element);
        Elements cells = element.select("td");
        BigDecimal profit = new BigDecimal(cells.get(4).text());
        balance.setProfit(profit);
        String comment = cells.get(3).text();
        balance.setComment(comment);
        return balance;
    }

    private Order fillOrder(String accountString, Element element) {
        Order order = new Order();
        fillCommonFields(order, accountString, element);
        Elements cells = element.select("td");
        BigDecimal size = new BigDecimal(cells.get(3).text());
        order.setVolume(size);
        order.setTicker(cells.get(4).text().toLowerCase());
        BigDecimal openPrice = new BigDecimal(cells.get(5).text());
        order.setOpenPrice(openPrice);
        BigDecimal stopLoss = new BigDecimal(cells.get(6).text());
        order.setStopLoss(stopLoss);
        BigDecimal takeProfit = new BigDecimal(cells.get(7).text());
        order.setTakeProfit(takeProfit);
        LocalDateTime closeDateTime = LocalDateTime.parse(cells.get(8).text(), formatter);
        order.setCloseDate(closeDateTime);
        BigDecimal closePrice = new BigDecimal(cells.get(9).text());
        order.setClosePrice(closePrice);
        BigDecimal commission = new BigDecimal(cells.get(10).text());
        order.setCommission(commission);
        BigDecimal tax = new BigDecimal(cells.get(11).text());
        order.setTax(tax);
        BigDecimal swap = new BigDecimal(cells.get(12).text());
        order.setSwap(swap);
        BigDecimal profit = new BigDecimal(cells.get(13).text());
        order.setProfit(profit);

        return order;
    }

    private void fillCommonFields(AbstractOrder model, String accountString, Element element) {

        Long id = Long.valueOf(element.select("td").get(0).text());
        LocalDateTime openDate = LocalDateTime.parse(element.select("td").get(1).text(), formatter);
        OrderType orderType = OrderType.byCode(element.select("td").get(2).text());

        model.setAccount(accountString);
        model.setId(id);
        model.setOpenDate(openDate);
        model.setType(orderType);

    }


}
