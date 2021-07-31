package com.MasterTweetsAnalysis.service.impl;

import com.MasterTweetsAnalysis.config.TwitterConfig;
import com.MasterTweetsAnalysis.model.TwitterStatus;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import twitter4j.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

@Service
public class TwitterService {
    private static org.slf4j.Logger log = LoggerFactory.getLogger(TwitterService.class);

    TwitterConfig config;
    SentimentAnalyzerService analyzerService;

    @Autowired
    public TwitterService(TwitterConfig config, SentimentAnalyzerService analyzerService) {
        this.config = config;
        this.analyzerService = analyzerService;
    }


    public Flux<TwitterStatus> fetchTweets(String keyword, int count) throws TwitterException {
        Twitter twitter = this.config.twitter(this.config.twitterFactory());
        Query query = new Query(keyword.concat(" -filter:retweets -filter:replies"));
        query.setCount(count);
        query.setLocale("en");
        query.setLang("en");
        query.setCount(5);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime Day = LocalDateTime.now();
        int numberOfTweets = 128;
        long lastID = Long.MAX_VALUE;
        List<Status> tweets = new ArrayList<Status>();
        for (int i = 0; i < 8; i++) {
            query.setUntil(dtf.format(Day));
            Day = Day.minusDays(1);
            query.setSince(dtf.format(Day));

            try {
                QueryResult result = twitter.search(query);
                tweets.addAll(result.getTweets());


            }

            catch (TwitterException te) {

            };
        }
//        while (tweets.size () < numberOfTweets) {
//            if (numberOfTweets - tweets.size() > 100)
//                query.setCount(100);
//            else
//                query.setCount(numberOfTweets - tweets.size());
//            try {
//                QueryResult result = twitter.search(query);
//                tweets.addAll(result.getTweets());
//
//                for (Status t: tweets)
//                    if(t.getId() < lastID) lastID = t.getId();
//
//            }
//
//            catch (TwitterException te) {
//
//            };
//            query.setMaxId(lastID-1);
//        }

            return  Flux.fromStream(tweets.stream().map(status -> this.cleanTweets(status)));

//                 return Flux.fromStream( twitter.search(query).getTweets().stream()).map(status -> this.cleanTweets(status));
//                 return Flux.fromStream( twitter.search(query).getTweets().stream()).map(status -> this.cleanTweets(status));
//   TIMELINE:     return Flux.fromStream( twitter.getUserTimeline("elonmusk").stream()).map(status -> this.cleanTweets(status));

    }


// S T R E A M TWEETS:
//    public Flux<TwitterStatus> streamTweets(String keyword){
//        TwitterStream stream = config.twitterStream();
//        FilterQuery tweetFilterQuery = new FilterQuery();
//        tweetFilterQuery.track(new String[]{keyword});
//        tweetFilterQuery.language(new String[]{"en"});
//        return Flux.create(sink -> {
//            stream.onStatus(status -> sink.next(this.cleanTweets(status)));
//            stream.onException(sink::error);
//            stream.filter(tweetFilterQuery);
//            sink.onCancel(stream::shutdown);
//        });
//    }

    private TwitterStatus cleanTweets(Status status){
        TwitterStatus twitterStatus = new TwitterStatus(status.getCreatedAt(), status.getId(), status.getText(), null, status.getUser().getName(), status.getUser().getScreenName(), status.getUser().getProfileImageURL());
        // Clean up tweets
        String text = status.getText().trim()
                // remove links
                .replaceAll("http.*?[\\S]+", "")
                // remove usernames
                .replaceAll("@[\\S]+", "")
                // replace hashtags by just words
                .replaceAll("#", "")
                // correct all multiple white spaces to a single white space
                .replaceAll("[\\s]+", " ");
        twitterStatus.setText(text);
        twitterStatus.setSentimentType(analyzerService.analyse(text));
        return twitterStatus;
    }


}