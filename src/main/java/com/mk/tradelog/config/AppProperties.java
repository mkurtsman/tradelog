package com.mk.tradelog.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@AllArgsConstructor
public class AppProperties {

    public final Database database;
    public final HtmlParser htmlParser;

    public class HtmlParser {
       public String filespath;
    }

    public class Database {
        public String url;
        public String username;
        public String password;
    }
}
