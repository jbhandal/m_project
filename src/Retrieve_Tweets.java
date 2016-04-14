

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import twitter4j.IDs;
import twitter4j.Paging;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Retrieve_Tweets {
public static void main(String[] args) throws IllegalStateException, TwitterException
{
//Get the all users by opening and reading files text1,text2,text3 and text4
	ArrayList<String> users = new ArrayList<String>();
	String filename = "test1.txt";
	
    try{

       //Create object of FileReader

       FileReader inputFile1 = new FileReader(filename);
       FileReader inputFile2 = new FileReader("test2.txt");
       FileReader inputFile3 = new FileReader("test3.txt");
       FileReader inputFile4 = new FileReader("test4.txt");
       //Instantiate the BufferedReader Class

       BufferedReader bufferReader1 = new BufferedReader(inputFile1);
       BufferedReader bufferReader2 = new BufferedReader(inputFile2);
       BufferedReader bufferReader3 = new BufferedReader(inputFile3);
       BufferedReader bufferReader4 = new BufferedReader(inputFile4);
       //Variable to hold the one line data
       String line = null;

       // Read file line by line and print on the console
       while ((line = bufferReader1.readLine()) != null)   {
    	   users.add(line);
       }
       while ((line = bufferReader2.readLine()) != null)   {
    	   users.add(line);
       }
           while ((line = bufferReader3.readLine()) != null)   {
        	   users.add(line);
           }        	   
               while ((line = bufferReader4.readLine()) != null)   {
            	   users.add(line);
               }
        	   
    	   
       
       //Close the buffer reader
       bufferReader1.close();
       bufferReader2.close();
       bufferReader3.close();
       bufferReader4.close();
    	
    	
       
    }catch(Exception e){
       System.out.println("Error while reading file line by line:" + e.getMessage());                      
    }	
//    System.out.println(users.size());
	
//We are trying to create a snippet that gets the friend list and follower list for the user
//tries to get an intersection of the two.	
	ArrayList<Long> array1 = new ArrayList<Long>();
	ArrayList<Long> array2 = new ArrayList<Long>();
	ArrayList<Long> array3 = new ArrayList<Long>();	
	ArrayList<Long> array4 = new ArrayList<Long>();	
	
	int count = 0;
	for(String user:users)
	{
		count ++;
		if(count < (users.size()/4))
		{
			array1.add(Long.parseLong(user));
		}
		else if (count >=users.size()/4 && count < users.size()/2)
		{
			array2.add(Long.parseLong(user));
		}
		else if (count >=users.size()/2 && count < (3*users.size()/4))
		{
			array3.add(Long.parseLong(user));
		}
		else if (count >=(3*users.size()/4) && count <= users.size())
		{
			array4.add(Long.parseLong(user));			
		}
	}
	
	
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
	    		TwitterFactory tf1 = new TwitterFactory(cb.build());		    		
	        	Twitter twitter1 = tf1.getInstance();
	//get the instance of a twitter handle for zeeshan
	    		TwitterFactory tf2 = new TwitterFactory(cb1.build());		    		
	        	Twitter twitter2 = tf2.getInstance();
	    		//get the instance of a twitter handle for zeeshan
	    		TwitterFactory tf3 = new TwitterFactory(cb2.build());		    		
	        	Twitter twitter3 = tf3.getInstance();

	    		TwitterFactory tf4 = new TwitterFactory(cb3.build());		    		
	        	Twitter twitter4 = tf4.getInstance();

	        	tweet_getter t1 = new tweet_getter(twitter1,array1,"tweets_01.txt");
	        	tweet_getter t2 = new tweet_getter(twitter2,array2,"tweets_02.txt");
	        	tweet_getter t3 = new tweet_getter(twitter3,array3,"tweets_03.txt");
	        	tweet_getter t4 = new tweet_getter(twitter4,array4,"tweets_04.txt");	   
	        	t1.start();
	        	t2.start();
	        	t3.start();
	        	t4.start();
}



//}
}
