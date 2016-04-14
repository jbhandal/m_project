import twitter4j.Twitter;
import twitter4j.TwitterException;

public class Unit_Testing_TweetuserInfo {
	public static void main(String args[]) throws IllegalStateException, TwitterException
	{
		String filename = "Profiles.txt";
		TweetUserInfo var = new TweetUserInfo();
		var.initTweetConnectors(filename);
		Twitter t = var.getTweetConnector("/friends/ids");
		System.out.println(t.getId());
	}
}
