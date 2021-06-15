package com.mk.tradelog.service.htmlparser;

import com.mk.tradelog.config.AppProperties;
import com.mk.tradelog.model.orders.AbstractOrder;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class HtmlParserImpl implements HtmlParser {

    private final AppProperties properties;
    private final HtmlMapper mapper;

    @Override
    public Iterable<AbstractOrder> parse() throws IOException {
        Stream<Path> files = Files.list(Paths.get(properties.getHtmlParser().getFilespath())).filter(p -> p.toString().endsWith(".htm") || p.toString().endsWith(".html"));
        return files.flatMap(p -> parseFile(p).stream()).collect(Collectors.toSet());
    }

    private List<AbstractOrder> parseFile(Path path) {
        try {
            Document doc = Jsoup.parse(path.toFile(), Charset.defaultCharset().toString());
            return mapper.mapDocument(doc);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
