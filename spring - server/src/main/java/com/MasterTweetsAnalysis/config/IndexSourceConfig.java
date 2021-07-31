package com.MasterTweetsAnalysis.config;

import com.crazzyghost.alphavantage.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IndexSourceConfig  {

    private IndexSourceProperties properties;




    @Autowired (required = true)
    public IndexSourceConfig (IndexSourceProperties properties) {

        this.properties = properties;
    }



    public Config Config () {
            Config cfg =  Config.builder()
                .key(properties.getAlphaVantageConsumerKey())
                .timeOut(10)
                .build();
            return cfg; }


}
