package com.mk.tradelog.config;

import com.mk.tradelog.model.orders.AbstractOrder;
import com.mk.tradelog.repsitory.OrdersRepository;
import com.mk.tradelog.service.htmlparser.HtmlParser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ApplicationListener {

    private final HtmlParser htmlParser;
    private final OrdersRepository ordersRepository;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            Iterable<AbstractOrder> data = htmlParser.parse();
            ordersRepository.saveAll(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
