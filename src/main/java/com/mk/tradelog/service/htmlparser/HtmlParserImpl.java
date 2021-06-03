package com.mk.tradelog.service.htmlparser;

import com.mk.tradelog.config.AppProperties;
import com.mk.tradelog.model.orders.AbstractOrder;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
public class HtmlParserImpl implements HtmlParser {

    private final AppProperties properties;
    private final HtmlMapper mapper;

    @Override
    public Set<AbstractOrder> parse() throws IOException {


        Stream<Path> files = Files.list(Paths.get(URI.create(properties.htmlParser.filespath)));

        return files.flatMap(p -> parseFile(p).stream()).collect(Collectors.toSet());
    }

    private List<AbstractOrder> parseFile(Path path){
        try {
            Document doc = Jsoup.parse(path.toFile(), Charset.defaultCharset().toString());
            return  mapper.mapDocument(doc);
        }catch (IOException ex) {
          throw  new RuntimeException(ex);
        }
    }

}
