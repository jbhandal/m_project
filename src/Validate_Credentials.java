import java.util.ArrayList;
import java.util.Map;

import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Validate_Credentials {
public static void main(String args[]) throws TwitterException
{
TweetUserInfo var = new TweetUserInfo();
var.initTweetConnectors("Profiles.txt");
Twitter tc = var.getTweetConnector("/users/lookup")     ;
System.out.println(tc.getScreenName());
//	ConfigurationBuilder cb = new ConfigurationBuilder();
//    cb.setDebugEnabled(true)
//	 .setOAuthConsumerKey("Dq5tQ4r33Ou1ZrlZKEiODMpPD")
//	 .setOAuthConsumerSecret("NSAZFLtNkCmpw6yup6dLWQYGGswbzp2ooeT3y9hm7oHVHruYPY")
//	 .setOAuthAccessToken("707640162470080513-1IO8Z8wyCpYaGV90DkFSpEkSC4lgEmh")
//	 .setOAuthAccessTokenSecret("TlgBkrvPrYgaiAg4PBFBkf2ihRDL3ACwoxXhmfMcagSjH")    		;
//	TwitterFactory tf = new TwitterFactory(cb.build());		    		
//	Twitter tc = tf.getInstance();
//	Map<String, RateLimitStatus> rateLimitStatus;
//	rateLimitStatus = tc.getRateLimitStatus();
//	RateLimitStatus st = rateLimitStatus.get("user/show");
//	System.out.println(tc.showUser(381327034).getName());
//	for(Map.Entry<String, RateLimitStatus> entry:rateLimitStatus.entrySet())		
//	System.out.println(entry);
//ArrayList<Twitter> list = var.twitterConnectors;
//for(Twitter tc:list)
//{
//	Map<String, RateLimitStatus> rateLimitStatus;
//	rateLimitStatus = tc.getRateLimitStatus();
//	RateLimitStatus st = (RateLimitStatus) rateLimitStatus.get("statuses/user_timeline");
//	if(st!=null)
//	System.out.println(st.getRemaining());
//	else
//		System.out.println(tc);
////	for(Map.Entry<String, RateLimitStatus> entry:rateLimitStatus.entrySet())		
////	System.out.println(entry);
//
//}

}
}
