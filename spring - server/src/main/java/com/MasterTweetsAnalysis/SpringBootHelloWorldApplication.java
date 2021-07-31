package com.MasterTweetsAnalysis;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootHelloWorldApplication {


	public static void main(String[] args) {

		SpringApplication.run(SpringBootHelloWorldApplication.class, args);}




	}


//	@SpringBootApplication
//	public class SpringBootHelloWorldApplication {

//		public static void main(String[] args) throws TwitterException {
//			Config cfg = Config.builder()
//					.key("473c4607abfa41dcb470d3194665bdad")
//					.timeOut(10)
//					.build();
//
//			AlphaVantage.api().init(cfg);
//			AlphaVantage.api()
//					.timeSeries()
//					.intraday()
//					.forSymbol("IBM")
//					.interval(Interval.FIVE_MIN)
//					.outputSize(OutputSize.FULL)
//					.fetch();
//			SpringApplication.run(SpringBootHelloWorldApplication.class, args);
//		}
//
//	}



//	public void init() {
//		//initializer
//		Config cfg = Config.builder()
//				.key("473c4607abfa41dcb470d3194665bdad")
//				.timeOut(50)
//				.build();
//
//		AlphaVantage.api().init(cfg);
//
//		AlphaVantage.api()
//				.timeSeries()
//				.intraday()
//				.forSymbol("TSLA")
//				.interval(Interval.SIXTY_MIN)
//				.outputSize(OutputSize.FULL)
//				.dataType(DataType.JSON)
//				.onSuccess(e -> handleSuccess((TimeSeriesResponse) e))
//				.onFailure(e -> handleFailure((e)))
//				.fetch();
//
//
//	}
//
//	public void handleSuccess(TimeSeriesResponse response) {
//		System.out.println("Function");
//		System.out.println(response.toString());
//	}
//
//
//	public void handleFailure(AlphaVantageException error) {
//
//		System.out.println("Doesn't function");
//		System.out.println(error.getMessage());
//	}

//	public static void main(String[] args) {
//		SpringBootHelloWorldApplication main = new SpringBootHelloWorldApplication();
//		main.init();
//	}
//}




//Tweeter API Check:
//		ConfigurationBuilder cb = new ConfigurationBuilder();
//		cb.setDebugEnabled(true)
//				.setOAuthConsumerKey("Z1ACAkih7ToUEq95MT6JQcB0Q")
//				.setOAuthConsumerSecret("AchZduLX4mtY5jlhNI51UhBmEBzm8rFmuyvjljZJmFsLBkEn3W")
//				.setOAuthAccessToken("304381187-idD7GbBg4BAw8X55IyI9gOzFIXULMkeO1vp0tt5O")
//				.setOAuthAccessTokenSecret("1hTLwmcmsq8jeuIDldfVJyTQIdx5vXCKvxb1WPugcSgzC");
//		TwitterFactory tf = new TwitterFactory(cb.build());
//		Twitter twitter = tf.getInstance();
//		try {
//			Query query = new Query("Musk");
//			QueryResult result;
//			result = twitter.search(query);
//			List<Status> tweets = result.getTweets();
//			for (Status tweet : tweets) {
//				System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
//			}
//
//			System.exit(0);
//		} catch (TwitterException te) {
//			te.printStackTrace();
//			System.out.println("Failed to search tweets: " + te.getMessage());
//			System.exit(-1);
//		}
