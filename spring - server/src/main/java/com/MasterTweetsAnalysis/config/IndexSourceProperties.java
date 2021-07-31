package com.MasterTweetsAnalysis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix= IndexSourceProperties.INDEXSOURCE_PROP_PREFIX)
public class IndexSourceProperties {

    public static final String INDEXSOURCE_PROP_PREFIX = "index";

    private String alphaVantageConsumerKey;

    public String getAlphaVantageConsumerKey () {
        return alphaVantageConsumerKey;
    }

    public void setAlphaVantageConsumerKey ( String alphaVantageConsumerKey) {
        this.alphaVantageConsumerKey = alphaVantageConsumerKey;
    }


}
