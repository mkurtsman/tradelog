package com.mk.tradelog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "application")
@Component
public class AppProperties {

    private HtmlParser htmlParser = new HtmlParser();

    @Data
    public static class HtmlParser {
        private String filespath;
    }
}
