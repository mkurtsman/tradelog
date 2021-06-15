package com.mk.tradelog.service.dataprovider;

import com.mk.tradelog.model.orders.AbstractOrder;
import com.mk.tradelog.service.htmlparser.HtmlParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class HtmlDataProvider implements DataProvider {

    private final HtmlParser parser;

    @Override
    public Iterable<AbstractOrder> getData() {
        try {
            return parser.parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
