package com.mk.tradelog.service.htmlparser;

import com.mk.tradelog.model.common.Account;
import com.mk.tradelog.model.common.Strategy;
import com.mk.tradelog.model.db.info.OrderInfo;
import com.mk.tradelog.model.db.orders.*;
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
        BigDecimal size = parceBigDecimal(cells, 3);
        cancelledOrder.setVolume(size);
        cancelledOrder.setTicker(cells.get(4).text().toLowerCase());
        BigDecimal openPrice = parceBigDecimal(cells, 5);
        cancelledOrder.setOpenPrice(openPrice);
        BigDecimal stopLoss = parceBigDecimal(cells, 6);
        cancelledOrder.setStopLoss(stopLoss);
        BigDecimal takeProfit = parceBigDecimal(cells, 7);
        cancelledOrder.setTakeProfit(takeProfit);
        LocalDateTime closeDateTime = LocalDateTime.parse(cells.get(8).text(), formatter);
        cancelledOrder.setCloseTime(closeDateTime);
        BigDecimal closePrice = parceBigDecimal(cells, 9);
        cancelledOrder.setClosePrice(closePrice);
        String comment = cells.get(10).text();
        cancelledOrder.setComment(comment);
        return cancelledOrder;

    }

    private Balance fillBalance(String accountString, Element element) {
        Balance balance = new Balance();
        fillCommonFields(balance, accountString, element);
        Elements cells = element.select("td");
        BigDecimal profit = parceBigDecimal(cells, 4);
        balance.setProfit(profit);
        String comment = cells.get(3).text();
        balance.setComment(comment);
        return balance;
    }

    private Order fillOrder(String accountString, Element element) {
        Order order = new Order();
        fillCommonFields(order, accountString, element);
        Elements cells = element.select("td");
        BigDecimal size = parceBigDecimal(cells, 3);
        order.setVolume(size);
        order.setTicker(cells.get(4).text().toLowerCase());
        BigDecimal openPrice = parceBigDecimal(cells, 5);
        order.setOpenPrice(openPrice);
        BigDecimal stopLoss = parceBigDecimal(cells, 6);
        order.setStopLoss(stopLoss);
        BigDecimal takeProfit = parceBigDecimal(cells, 7);
        order.setTakeProfit(takeProfit);
        LocalDateTime closeDateTime = LocalDateTime.parse(cells.get(8).text(), formatter);
        order.setCloseDate(closeDateTime);
        BigDecimal closePrice = parceBigDecimal(cells, 9);
        order.setClosePrice(closePrice);
        BigDecimal commission = parceBigDecimal(cells, 10);
        order.setCommission(commission);
        BigDecimal tax = parceBigDecimal(cells, 11);
        order.setTax(tax);
        BigDecimal swap = parceBigDecimal(cells, 12);
        order.setSwap(swap);
        BigDecimal profit = parceBigDecimal(cells, 13);
        order.setProfit(profit);

        OrderInfo info = new OrderInfo();
        order.setInfo(info);

        if(Account.DAILY.getValue().equals(accountString)){
            info.setStrategy(Strategy.D1);
        } else if(Account.INRADAY.getValue().equals(accountString)){
            if(order.getVolume().doubleValue() <= 0.02) {
                info.setStrategy(Strategy.M5);
            } else {
                info.setStrategy(Strategy.SCALPING);
            }
        } else {
            info.setStrategy(Strategy.CUSTOM);
        }


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

    private BigDecimal parceBigDecimal(Elements cells, Integer idx) {
        var text = cells.get(idx).text();
        System.out.println(text);
        return text == null ? null : new BigDecimal(text.replaceAll("\\s", ""));
    }


}
