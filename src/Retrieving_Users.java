import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import twitter4j.IDs;
import twitter4j.Paging;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.User;

public class Retrieving_Users {
	static TweetUserInfo var = new TweetUserInfo();
	static String list_profile = "Profiles.txt";
	public static void main(String args[])
	{
		var.initTweetConnectors(list_profile);
    	//create a map of friends and their tweets for zia
    	HashMap<User,List<Status>> friend_list = new HashMap<User,List<Status>>();

//    	31239408		        	


    	Map<String, RateLimitStatus> rateLimitStatus;    	
    	
    	try
    	{
    		ArrayList<Long> users = getUsers(46086849);
    		Paging page = new Paging();
    		page.setCount(2200);
        	List<Status> statuses = null;
        	IDs friend_ids,followers_id;

        	ArrayList<Long> friends = null;    		

    	        
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	
	}
	private static ArrayList<Long> getUsers(int i) {

    	ArrayList<Long> arr = new ArrayList<Long>(1000000);;
		try
		{
			IDs ids;
	    	long cursor = -1;
			
			RateLimitStatus status_followers;
			int count = 0;
			
	    	HashSet<Long> filter = new HashSet<Long>();

	    	Twitter twitter = var.getTweetConnector("/followers/ids");
	    	ids = twitter.getFollowersIDs(46086849,cursor);

	    	int count_ids = 0;

	        do {
	            for (long id : ids.getIDs()) {
	            	count_ids++;
	            	filter.add(id);
	            	
	            if(filter.size()%1000==0)
	            {
	            	System.out.println(filter.size());
	            }
	             if(filter.size()==5000)
	             {
	            	 break;
	             }}
	             if(filter.size()==5000)
	             {
	            	 break;
	             }
	            
	         } while ((cursor = ids.getNextCursor()) != 0);

	        Long temp;
	        Iterator it = filter.iterator();
	        count = 0;
	        while(it.hasNext())
	        {
	        	temp = (Long) it.next();
	        	count++;
	    		arr.add(temp);

	        }

			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
        return arr;		
	}
}
