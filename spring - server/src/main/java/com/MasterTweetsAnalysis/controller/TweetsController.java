package com.MasterTweetsAnalysis.controller;

import com.MasterTweetsAnalysis.model.TwitterStatus;
import com.MasterTweetsAnalysis.service.impl.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import twitter4j.TwitterException;

@RestController
public class TweetsController {
    @Autowired
    private TwitterService twitterService;

    @PostMapping (path = "search/{keyword}/{size}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE })
    @CrossOrigin(origins = "http://localhost:4200")
    public Flux<TwitterStatus> fetch(@PathVariable String keyword, @PathVariable int size) throws TwitterException {
        return twitterService.fetchTweets(keyword, size);

    }

}