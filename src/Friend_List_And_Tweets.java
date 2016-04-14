

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.IDs;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;


/**
 * Gets account settings.
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public final class Friend_List_And_Tweets {
    /**
     * Usage: java twitter4j.examples.account.GetAccountSettings
     *
     * @param args arguments doesn't take effect with this example
     * @throws TwitterException 
     * @throws UnsupportedEncodingException 
     * @throws FileNotFoundException 
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws TwitterException, FileNotFoundException, UnsupportedEncodingException, InterruptedException {
        //build the configuration object for zia's ID
    	ConfigurationBuilder cb = new ConfigurationBuilder();
	       cb.setDebugEnabled(true)
     	 .setOAuthConsumerKey("IjMX7qDgN1jLNwZygrXe2SEkk")
     	 .setOAuthConsumerSecret("EeuwKTsEXU4nKqf8IjnXna5f5lXuxQMMvViLdDx70EZv86NrSH")
     	 .setOAuthAccessToken("121380800-xTA6c6ufmsB77VGObz6HJWJeh1q3BDg0SCbjyfMy")
     	 .setOAuthAccessTokenSecret("l3pBTwTCPBh1j10GQm1CklrCDdzyZXvj7Q7h3pptmq7z0")    		;
  	ConfigurationBuilder cb1 = new ConfigurationBuilder();
	       cb1.setDebugEnabled(true)
         	 .setOAuthConsumerKey("AZPNtw4BliYGMWQQjk65cUaG3")
         	 .setOAuthConsumerSecret("xtSOs7PwvqMCnVhSAsCkCroApUYLsGyLPlV4DI5FklOj1dGBxU")
         	 .setOAuthAccessToken("96932313-3imgpSZK4DbT5VpVG0VJwrWrHqoDfnVlAUlIj1hAU")
         	 .setOAuthAccessTokenSecret("PmEProT1vVWWjfcc9pMwAl02y5XYjvQww28ZWDfzMqYsp")    		;		    	       
  	ConfigurationBuilder cb2 = new ConfigurationBuilder();
	       cb2.setDebugEnabled(true)
         	 .setOAuthConsumerKey("KjIu9zlXVWfSGboostST8GzD0")
         	 .setOAuthConsumerSecret("rhjqFZPnKPxzgMrn4z1QcGbMgJ9rCdeCSQ8Ugd7vwZtuJ9w5NQ")
         	 .setOAuthAccessToken("3116051712-me9f5zfhAGxgsuwTYrA4BgF8csXwtYHPxAKyML4")
         	 .setOAuthAccessTokenSecret("S128lylvQ97kvTrig63ZKZlKf79YF29TEgRIkAQgWul84")    		;		    	       
	        	ConfigurationBuilder cb3 = new ConfigurationBuilder();
	    	       cb3.setDebugEnabled(true)
	               	 .setOAuthConsumerKey("BaOihxTHVpbKGj1ss1LZXVUnY")
	               	 .setOAuthConsumerSecret("kRlVC1b848BjNpr7DTCEF5dLk3fQFQXLw6ehcyD061PeH6O4zx")
	               	 .setOAuthAccessToken("540502045-2D0yzyPIju9I3aSm6nMXBDOUbMDiBPAx5EU08yyI")
	               	 .setOAuthAccessTokenSecret("6FuX65e8lLzVTIBQYiFLuOAqZpPUaPsXV0256XCJvKAuj")    		;		    	       

	    		    //get the instance of a twitter handle for zia
		    		TwitterFactory tf = new TwitterFactory(cb.build());		    		
		        	Twitter twitter = tf.getInstance();
		//get the instance of a twitter handle for zeeshan
		    		TwitterFactory tf1 = new TwitterFactory(cb1.build());		    		
		        	Twitter twitter1 = tf1.getInstance();
		    		//get the instance of a twitter handle for zeeshan
		    		TwitterFactory tf2 = new TwitterFactory(cb2.build());		    		
		        	Twitter twitter2 = tf2.getInstance();

		    		TwitterFactory tf3 = new TwitterFactory(cb3.build());		    		
		        	Twitter twitter3 = tf3.getInstance();

		        	
		        	
		        	//create a map of friends and their tweets for zia
		        	HashMap<User,List<Status>> friend_list = new HashMap<User,List<Status>>();
		//create a map of friends and their tweets for zeeshan
		        	HashMap<User,List<Status>> friend_list1 = new HashMap<User,List<Status>>();
		//create a map of friends and their tweets for mithun
		        	HashMap<User,List<Status>> friend_list2 = new HashMap<User,List<Status>>();
		    		//create a map of friends and their tweets for mithun
		        	HashMap<User,List<Status>> friend_list3 = new HashMap<User,List<Status>>();
		        	
//		        	31239408		        	
		        	IDs ids;
		        	long cursor = -1;

		        	HashSet<Long> filter = new HashSet<Long>();
		        	ArrayList<Long> arr1 = new ArrayList<Long>(1000000);;
		        	ArrayList<Long> arr2= new ArrayList<Long>(1000000);
		        	ArrayList<Long> arr3 = new ArrayList<Long>(1000000);
		        	ArrayList<Long> arr4 = new ArrayList<Long>(1000000);		        	
//		        	long cursor = -1;
		            Map<String, RateLimitStatus> rateLimitStatus;
		            try{
		    		RateLimitStatus status_followers;
		        	int count = 0;
			    		rateLimitStatus = twitter.getRateLimitStatus();
		        		status_followers = rateLimitStatus.get("/followers/ids");
		        		System.out.println(status_followers.getRemaining() +" "+status_followers.getSecondsUntilReset());
		    		    if(status_followers.getRemaining()== 0)
		    		    {
		    		    	System.out.println(twitter.showUser(twitter.getId()).getName() +" sleeping in main thread");
		    		    	TimeUnit.MINUTES.sleep(15);
		    		    }
			        	ids = twitter.getFollowersIDs(46086849,cursor);
//		        		ids = twitter2.getFrieIDs(cursor);

			        	
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

	    	        	if(count>0 && count<=500)
	    	        	{
	    	        		System.out.println("Array1 "+temp.longValue());
	    	        		arr1.add(temp);
	    	        	}
	    	        	if(count>500 && count<=1000)
	    	        	{
	    	        		System.out.println("Array2 "+temp.longValue());
	    	        		arr2.add(temp);	    	        		
	    	        	}
	    	        	if(count>1000 && count<=1500)
	    	        	{
	    	        		System.out.println("Array3 "+temp.longValue());
	    	        		arr3.add(temp);
	    	        	}
	    	        	if(count>1500 && count<=2500)
	    	        	{
	    	        		System.out.println("Array4 "+temp.longValue());
	    	        		arr4.add(temp);
	    	        	}
	    	        }
	    	        System.out.println(arr1.size()+" "+arr2.size()+" "+arr3.size()+" "+arr4.size());    
			        for(int j=0;j<499;j++)
			        {
			        	System.out.println(arr1.get(j)+" "+arr2.get(j)+" "+arr3.get(j)+" "+arr4.get(j)+" ");
			        }
			        	
//		        	System.exit(0);
		            Friend_List fl = new Friend_List(twitter,arr1,"test1.txt");
		            fl.start();
		            Friend_List fl1 = new Friend_List(twitter1,arr2,"test2.txt");		            
		            fl1.start();
		            Friend_List fl2 = new Friend_List(twitter2,arr3,"test3.txt");		            
		            fl2.start();
		            Friend_List fl3 = new Friend_List(twitter3,arr4,"test4.txt");		            
		            fl3.start();

		            
		            
		            }
		            catch(TwitterException e)
		            {
		            	Thread.sleep(9000000);
		            	System.out.println(e.getErrorCode());
		            }
    }
}
