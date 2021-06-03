package com.mk.tradelog.model.orders;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public enum OrderType {

    buy("buy"), sell("sell"), buystop("buy stop"), sellstop("sell stop"), buylimit("buy limit"), selllimit("sell limit"), balance("balance");

    private static final Map<Operations, List<OrderType>> operationsOrdersMap = Map.of(
            Operations.market, List.of(buy, sell),
            Operations.pending, List.of(buylimit, selllimit, buystop, sellstop),
            Operations.balance, List.of(balance)
    );

    private final String code;

    public static OrderType byCode(String code) {
        return Arrays.stream(values()).filter(orderType -> orderType.code.equals(code)).findFirst().orElse(null);
    }

    public static Operations getOperation(OrderType orderType) {
        return operationsOrdersMap.entrySet().stream().filter(e -> e.getValue().contains(orderType)).map(Map.Entry::getKey).findFirst().orElse(null);
    }


}
