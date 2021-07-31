package com.MasterTweetsAnalysis.controller;

import com.MasterTweetsAnalysis.service.impl.IndexService;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @Autowired
    private IndexService indexService;


    @PostMapping(path = "search/{symbol}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE })
    @CrossOrigin(origins = "http://localhost:4200")
    public TimeSeriesResponse fetch (@PathVariable String symbol)  {
         return indexService.IndexValueReader(symbol);

    }

}