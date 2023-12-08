package com.yapp.memeserver.global.config;

import com.p6spy.engine.spy.P6SpyOptions;
import com.yapp.memeserver.global.formatter.P6SpyFormatter;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class P6SpyConfiguration {
    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(P6SpyFormatter.class.getName());
    }
}