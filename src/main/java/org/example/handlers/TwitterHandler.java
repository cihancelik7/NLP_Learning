package org.example.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterHandler implements ResponseHandler {
    private static final Logger logger = LoggerFactory.getLogger(TwitterHandler.class);
    private boolean awaitingTweetContent = false;
    private Twitter twitter;

    public TwitterHandler() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("********************")
                .setOAuthConsumerSecret("********************")
                .setOAuthAccessToken("********************-********************")
                .setOAuthAccessTokenSecret("********************");

        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    @Override
    public String handleResponse(String input) {
        if ("I want to tweet".equalsIgnoreCase(input) && !awaitingTweetContent) {
            awaitingTweetContent = true;
            return "Please enter your tweet: ";
        } else if (awaitingTweetContent) {
            try {
                twitter.updateStatus(input);
                logger.info("Tweet successfully posted.");
                awaitingTweetContent = false;
                return "Your tweet has been posted!";
            } catch (TwitterException e) {
                logger.error("Failed to post tweet", e);
                awaitingTweetContent = false;
                return "Failed to post your tweet. Please try again.";
            }
        }
        return "To tweet, start by typing 'I want to tweet'";
    }
}
