package com.mk.tradelog.service.htmlparser;

import com.mk.tradelog.model.orders.AbstractOrder;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class HtmlMapper {

    public static final int START_ROW = 3;

    private final ModelPappingFactory modelPappingFactory;

    public List<AbstractOrder> mapDocument(Document doc) {
        List<AbstractOrder> abstractOrders = new ArrayList<>();
        Element table = doc.selectFirst("table");
        Elements rows = table.select("tr");

        String accountString = rows.get(0).select("td").get(0).text().substring("Account: ".length());

        int row = START_ROW;
        Predicate<Element> rowsCond = el -> "Closed P/L:".equals(el.select("td").get(0).text());

        while (rowsCond.negate().test(rows.get(row + 2))) {
            abstractOrders.add(modelPappingFactory.mapRow(rows.get(row), accountString));
            row++;
        }
        if (row != START_ROW) {
            abstractOrders.add(modelPappingFactory.mapRow(rows.get(row), accountString));
        }

        return abstractOrders;
    }

}
