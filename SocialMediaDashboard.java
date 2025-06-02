
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;
import java.util.Scanner;

public class SocialMediaDashboard {

    public static void main(String[] args) {
        // Twitter API credentials - replace with your own keys
        String consumerKey = "YOUR_CONSUMER_KEY";
        String consumerSecret = "YOUR_CONSUMER_SECRET";
        String accessToken = "YOUR_ACCESS_TOKEN";
        String accessTokenSecret = "YOUR_ACCESS_TOKEN_SECRET";

        // Configure Twitter4J
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
            .setOAuthConsumerKey(consumerKey)
            .setOAuthConsumerSecret(consumerSecret)
            .setOAuthAccessToken(accessToken)
            .setOAuthAccessTokenSecret(accessTokenSecret);

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        Scanner scanner = new Scanner(System.in);
        System.out.println("== Social Media Dashboard - Twitter Analytics ==");

        System.out.print("Enter Twitter username (without @): ");
        String username = scanner.nextLine();

        try {
            // Fetch User info
            User user = twitter.showUser(username);

            if (user == null) {
                System.out.println("User not found.");
                System.exit(1);
            }

            System.out.println("\nUser Profile:");
            System.out.println("Name: " + user.getName());
            System.out.println("Username: @" + user.getScreenName());
            System.out.println("Description: " + user.getDescription());
            System.out.println("Followers: " + user.getFollowersCount());
            System.out.println("Following: " + user.getFriendsCount());
            System.out.println("Tweets: " + user.getStatusesCount());
            System.out.println("Profile Image URL: " + user.getProfileImageURLHttps());

            // Fetch recent tweets (last 20)
            System.out.println("\nRecent Tweets Engagement:");
            Paging paging = new Paging(1, 20);
            List&lt;Status&gt; statuses = twitter.getUserTimeline(username, paging);

            int totalLikes = 0;
            int totalRetweets = 0;

            for (Status status : statuses) {
                int likes = status.getFavoriteCount();
                int retweets = status.getRetweetCount();
                totalLikes += likes;
                totalRetweets += retweets;
                System.out.println("\nTweet: " + status.getText());
                System.out.println("Likes: " + likes + " | Retweets: " + retweets + 
                    " | Date: " + status.getCreatedAt());
            }

            System.out.println("\nSummary of last " + statuses.size() + " tweets:");
            System.out.println("Total Likes: " + totalLikes);
            System.out.println("Total Retweets: " + totalRetweets);

        } catch (TwitterException e) {
            System.out.println("Failed to get data: " + e.getMessage());
        }

        scanner.close();
    }
}

