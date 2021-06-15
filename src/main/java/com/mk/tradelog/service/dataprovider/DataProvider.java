package com.mk.tradelog.service.dataprovider;

import com.mk.tradelog.model.orders.AbstractOrder;

public interface DataProvider {

    Iterable<AbstractOrder> getData();
}
