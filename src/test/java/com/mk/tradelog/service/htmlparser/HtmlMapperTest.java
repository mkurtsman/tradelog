package com.mk.tradelog.service.htmlparser;

import com.mk.tradelog.model.db.orders.AbstractOrder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class HtmlMapperTest {

    private static Document doc;
    private final HtmlMapper mapper = new HtmlMapper(new ModelMappingFactory());

    @BeforeAll
    public static void init() throws IOException {
        InputStream stream = ClassLoader.getSystemResource("DetailedStatement.htm").openStream();
        doc = Jsoup.parse(stream, "windows-1251", "");
    }

    @Test
    public void testMapDocument() {
        List<AbstractOrder> abstractOrders = mapper.mapDocument(doc);
        assertEquals("730044154", abstractOrders.get(0).getAccount());
    }


}