package com.mk.tradelog.service.htmlparser;

import com.mk.tradelog.model.orders.AbstractOrder;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface HtmlParser {
    Iterable<AbstractOrder> parse(List<Path> files) throws IOException;
}
