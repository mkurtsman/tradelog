package com.mk.tradelog.service.htmlparser;

import com.mk.tradelog.model.orders.AbstractOrder;

import java.io.IOException;
import java.util.Set;

public interface HtmlParser {

    Set<AbstractOrder> parse() throws IOException;
}
