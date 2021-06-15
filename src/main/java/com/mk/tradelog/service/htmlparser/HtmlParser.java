package com.mk.tradelog.service.htmlparser;

import com.mk.tradelog.model.orders.AbstractOrder;

import java.io.IOException;

public interface HtmlParser {

    Iterable<AbstractOrder> parse() throws IOException;
}
