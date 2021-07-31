package com.MasterTweetsAnalysis.service.impl;

import com.MasterTweetsAnalysis.config.IndexSourceProperties;
import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.DataType;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexService {
//    public void init() {
//        //initializer
//        Config cfg = Config.builder()
//                .key("473c4607abfa41dcb470d3194665bdad")
//                .timeOut(50)
//                .build();
private IndexSourceProperties properties;





    @Autowired (required = true)
    public IndexService (IndexSourceProperties properties) {

        this.properties = properties;
    }


    public TimeSeriesResponse IndexValueReader (String symbol) {
        Config cfg = Config.builder()
					.key(properties.getAlphaVantageConsumerKey())
				    .timeOut(10)
					.build();

        AlphaVantage.api().init(cfg);

        TimeSeriesResponse response = AlphaVantage.api()
                .timeSeries()
                .daily()
//               .intraday()
                .forSymbol(symbol)
//                .interval(Interval.FIVE_MIN)
                .outputSize(OutputSize.FULL)
                .dataType(DataType.JSON)
                .fetchSync();

        return response;

    }



}
