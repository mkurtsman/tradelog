package com.mk.tradelog.service.event;

import com.mk.tradelog.model.db.orders.AbstractOrder;
import com.mk.tradelog.repsitory.OrdersRepository;
import com.mk.tradelog.service.files.FileService;
import com.mk.tradelog.service.htmlparser.HtmlParser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdateDataServiceImpl implements UpdateDataService {

    private final HtmlParser htmlParser;
    private final OrdersRepository ordersRepository;
    private final FileService fileService;

    @Transactional
    public void update() {
        try {
            List<Path> files = fileService.getFiles();
            Iterable<AbstractOrder> data = htmlParser.parse(files);
            ordersRepository.saveAll(data);
            fileService.backupFiles(files);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        update();
    }
}
