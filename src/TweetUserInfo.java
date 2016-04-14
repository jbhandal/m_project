import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TweetUserInfo {
private Set<Twitter> mTwitterConnectorsSet;
private BufferedReader mUserFileReader;
public ArrayList<Twitter> twitterConnectors =  new ArrayList<>();

TweetUserInfo(){
    mTwitterConnectorsSet = new HashSet<Twitter>();

}
//initialize tweet connectors
public void initTweetConnectors(String inFile) {
    BufferedReader br = null;
    try {
        String line = null;
        String[] lines = new String[4];
        int linesIndex = 0;
        br = new BufferedReader(new FileReader(inFile));

        while ((line = br.readLine()) != null) {
            if (linesIndex == 4) {
                createAndAddTwitterConnector(lines);
                linesIndex = 0;
            }
            lines[linesIndex] = line;
            ++linesIndex;
        }
        if (linesIndex == 4) {
            createAndAddTwitterConnector(lines);
        }

    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
            if (br != null)br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

public void createAndAddTwitterConnector(String[] lines) {
    ConfigurationBuilder twitterConfigBuilder = new ConfigurationBuilder();
    twitterConfigBuilder.setDebugEnabled(true);

    for (int i = 0; i < lines.length; ++i) {
        String[] input = lines[i].split("=");

        if (input[0].equalsIgnoreCase("consumerkey")) {
//        	System.out.println(input[1]);
            twitterConfigBuilder.setOAuthConsumerKey(input[1]);
        }
        if (input[0].equalsIgnoreCase("consumersecret")) {
//        	System.out.println(input[1]);
            twitterConfigBuilder.setOAuthConsumerSecret(input[1]);
        }
        if (input[0].equalsIgnoreCase("accesstoken")) {
//        	System.out.println(input[1]);
            twitterConfigBuilder.setOAuthAccessToken(input[1]);
        }
        if (input[0].equalsIgnoreCase("accesstokensecret")) {
//        	System.out.println(input[1]);
            twitterConfigBuilder.setOAuthAccessTokenSecret(input[1]);
        }
    }
    TwitterFactory tf = new TwitterFactory(twitterConfigBuilder.build());		    		
	Twitter twitter = tf.getInstance();
    mTwitterConnectorsSet.add(twitter);
    twitterConnectors.add(twitter);
}

public Twitter getTweetConnector(String resource) {
        for (Twitter tc : mTwitterConnectorsSet) {
            try {
                if (tc.getRateLimitStatus() != null) {
                    if (tc.getRateLimitStatus().containsKey(resource)) {
                        if (tc.getRateLimitStatus().get(resource) != null) {
                            System.out.println("tc - "+tc);
                            System.out.println("tc rate - "+tc.getRateLimitStatus().get(resource).getRemaining());
                            if (tc.getRateLimitStatus().get(resource).getRemaining() > 2) {
                                return tc;
                            }
                        }
                    }
                }
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }
    return null;
}
}