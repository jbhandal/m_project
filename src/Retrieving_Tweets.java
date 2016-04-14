import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Date;
import java.util.ArrayList;
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
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class Retrieving_Tweets {
	
	static TweetUserInfo var = new TweetUserInfo();
	static String filename = "tweets_10160050";

	public static void main(String[] args) throws IllegalStateException, TwitterException
	{

/*
 * Prepare the tweet userinfo object
 */
		String filename = "Profiles.txt";

		var.initTweetConnectors(filename);
		
		
		//Get the users by opening and reading files text1,text2,text3 and text4
		ArrayList<Long> users = new ArrayList<Long>();
		ArrayList<String> fileList = new ArrayList<String>();
		fileList.add("test1.txt");
		fileList.add("test2.txt");
		fileList.add("test3.txt");
		fileList.add("test4.txt");
		addUserstolist(fileList,users);
		
/**
 * We have all the users. Iterate over them to get the intersection of their 
 * friends and followers list
 */

		RateLimitStatus status;
		IDs list_of_friends = null,list_of_followers = null;
		long cursor = -1;
		Twitter t;
		
		Iterator userlist = users.iterator();
		while(userlist.hasNext())
		{
			Long userid = (Long) userlist.next();
			try
			{
				t = var.getTweetConnector("/friends/ids");
				if(t==null)
				{
					Date date = new Date();
					System.out.println(date);

					TimeUnit.MINUTES.sleep(15);										
					t = var.getTweetConnector("/friends/ids");					
				}
				list_of_friends = t.getFriendsIDs(userid,cursor);
				HashSet<Long> set_of_friends = new HashSet<Long>();
				int count_friends=0;
				do{
					for (long id : list_of_friends.getIDs()){
						if(count_friends>2000000)
							break;
						count_friends++;
						set_of_friends.add(id);
						}					
					if(count_friends>2000000)
						break;
					
				}while((cursor = list_of_friends.getNextCursor()) != 0);
				
				if(count_friends>2000000)
					continue;
				cursor = -1;
				t = var.getTweetConnector("/followers/ids");
				if(t==null)
				{
					Date date = new Date();
					System.out.println(date);
					
					TimeUnit.MINUTES.sleep(15);
					t = var.getTweetConnector("/followers/ids");					
				}
				list_of_followers = t.getFollowersIDs(userid,cursor);
				HashSet<Long> set_of_followers = new HashSet<Long>();
				int count_followers=0;
				do{
					for (long id : list_of_followers.getIDs()){
						if(count_followers>200000)
							break;
						count_followers++;
						set_of_followers.add(id);
						}					
					if(count_followers>200000)
						break;
					
				}while((cursor = list_of_followers.getNextCursor()) != 0);
				
				if(count_followers>200000)
					continue;
				set_of_friends.retainAll(set_of_followers);	
				
				t = var.getTweetConnector("/users/show/:id");
				if(t==null)
				{
					Date date = new Date();
					System.out.println(date);

					TimeUnit.MINUTES.sleep(15);	
					t = var.getTweetConnector("/users/show/:id");					
				}
				twitter4j.User u = t.showUser(userid);

				iterateListForTweets(set_of_friends,u);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

			
		}
//		RateLimitStatus status ;
//		Map<String, RateLimitStatus> rateLimitStatus;
//		Status st;
//		List<Status> statuses = null;
//
//		IDs friends_list,followers_list;
//		long cursor = -1;
//		Paging page;
//
//		Iterator it = user_list.iterator();
//		while(it.hasNext())
//		{
//			Long val = (Long) it.next();
//			long id = val.longValue();
//			try {
//				rateLimitStatus = twitter.getRateLimitStatus();
//				status = rateLimitStatus.get("/friends/ids");
//				if(status.getRemaining() == 0)
//				{
//					System.out.println(twitter.showUser(twitter.getId()).getName());
//					Thread.sleep(900000);
//				}
//				friends_list  = twitter.getFriendsIDs(id, cursor);
//				HashSet<Long> friends_set = new HashSet<Long>();
//				do {
//					for (long id1 : friends_list.getIDs()){
//						friends_set.add(id1);
//						//                	System.out.println("Friends:"+id1);
//					}
//				} while ((cursor = friends_list.getNextCursor()) != 0);
//
//
//				rateLimitStatus = twitter.getRateLimitStatus();
//				status = rateLimitStatus.get("/followers/ids");
//				if(status.getRemaining() == 0)
//				{
//					System.out.println(twitter.showUser(twitter.getId()).getName());
//					Thread.sleep(900000);
//				}
//				HashSet<Long> followers_set = new HashSet<Long>();
//				cursor = -1;
//				followers_list = twitter.getFollowersIDs(id,cursor);
//				System.out.println("Followers present:"+followers_list.hasNext());
//				do {
//					for (long id1 : followers_list.getIDs()){ 
//						followers_set.add(id1);
//						//                	System.out.println("Follower:"+id1);
//					}
//				} while ((cursor = followers_list.getNextCursor()) != 0);
//
//				friends_set.retainAll(followers_set);
//				Iterator iter = friends_set.iterator();
//				while(iter.hasNext())
//				{
//					Long friend = (Long) iter.next();
//					for(int i=1;i<20;i++)
//					{
//						page = new Paging(i,200);
//						rateLimitStatus = twitter.getRateLimitStatus();
//						status = rateLimitStatus.get("/statuses/user_timeline");
//						if(status.getRemaining() == 0)
//						{
//							System.out.println(twitter.showUser(twitter.getId()).getName());
//							Thread.sleep(900000);
//						}
//
//						statuses = twitter.getUserTimeline(friend.longValue(),page);
//						File file =new File(filename);
//						if(!file.exists()){
//							file.createNewFile();
//						}
//						FileWriter fw = new FileWriter(file,true);
//						BufferedWriter bw = new BufferedWriter(fw);
//						PrintWriter pw = new PrintWriter(bw);
//						for(Status x:statuses)
//						{
//							String text = x.getText();
//							status = null;
//							status = rateLimitStatus.get("/users/show/:id");
//							if(status.getRemaining() == 0)
//							{
//								System.out.println(twitter.showUser(twitter.getId()).getName());
//								Thread.sleep(900000);
//							}
//							twitter4j.User u = twitter.showUser(id);
//							if(Utility.checktweetforuserhandle(text,u))
//							//Once the text is retrieved check for the user's handle in the friend's text 
//							pw.println(id+"\t"+friend.longValue()+"\t"+x.getText()+"\t"+x.getId());
//						}
//						pw.close();
//
//					}
//
//
//				}
//
//
//			} catch (TwitterException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				try {
//					System.out.println("Sleeping:"+twitter.showUser(twitter.getId()).getName());
//				} catch (IllegalStateException | TwitterException e2) {
//					// TODO Auto-generated catch block
//					e2.printStackTrace();
//				}
//				try {
//					Thread.sleep(900000);
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}
//	    
	    
	    
	}

	private static void iterateListForTweets(HashSet<Long> set_of_friends, User u) {
		// TODO Auto-generated method stub
		Twitter t;
		Paging page = null;
		List<Status> statuses = null;
		Iterator iter = set_of_friends.iterator();
		try
		{
			while(iter.hasNext())
			{
				Long friendid = (long)iter.next();
				t = var.getTweetConnector("/users/lookup");
				if(t==null)
				{
					Date date = new Date();
					System.out.println(date);

					TimeUnit.MINUTES.sleep(15);	
					t = var.getTweetConnector("/users/lookup");					
				}
				int statusCount = t.showUser(friendid.longValue()).getStatusesCount();
				if(statusCount>3200)
				{
					statusCount = 3200;
				}
				int q = statusCount/200;
				int r = statusCount%200;
//				long maxid = -1;
				for(int i=1;i<=q;i++)
				{
//					if(i==1)
						page = new Paging(i,200);
//					else
//						page = new Paging(i,200).maxId(maxid);						
					t = var.getTweetConnector("/statuses/user_timeline");
					if(t==null)
					{
						Date date = new Date();
						System.out.println(date);

						TimeUnit.MINUTES.sleep(15);	
						t = var.getTweetConnector("/statuses/user_timeline");					
					}
				
			
				else
				{
//					if(i==1)
//						statuses = t.getUserTimeline(friendid.longValue(),page);						
//					else
						statuses = t.getUserTimeline(friendid.longValue(),page);												

//					maxid = iterateTweetsForInfo(statuses,u,friendid);
						iterateTweetsForInfo(statuses,u,friendid);
						
				}
				}
			}
			}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		
	}

	private static void iterateTweetsForInfo(List<Status> statuses, User u, Long friendid) {
		// TODO Auto-generated method stub
		Twitter t;
		RateLimitStatus status;
//		long lowest_id = 0;
		try
		{
			File file =new File(filename);
			if(!file.exists()){
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file,true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
//			t = var.getTweetConnector("/users/show/:id");
//			if(t==null)
//			{
//				Date date = new Date();
//				System.out.println(date);
//
//				TimeUnit.MINUTES.sleep(15);	
//				t = var.getTweetConnector("/users/show/:id");					
//			}
//			twitter4j.User u = t.showUser(u2);

			for(Status x:statuses)
			{				
//				if(x.getId()<lowest_id)
//				{
//					lowest_id = x.getId();
//				}

				String text = x.getText();
				if(Utility.checktweetforuserhandle(text,u))
				//Once the text is retrieved check for the user's handle in the friend's text 
				pw.println(u.getId()+"\t"+u.getScreenName()+"\t"+friendid.longValue()+"\t"+x.getText()+"\t"+x.getId());
			}
			pw.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
//		for(Status x:statuses)
//		{
//			String text = x.getText();
//			status = null;
//			status = rateLimitStatus.get("/users/show/:id");
//			if(status.getRemaining() == 0)
//			{
//				System.out.println(twitter.showUser(twitter.getId()).getName());
//				Thread.sleep(900000);
//			}
//			twitter4j.User u = twitter.showUser(id);
//			if(Utility.checktweetforuserhandle(text,u))
//			//Once the text is retrieved check for the user's handle in the friend's text 
//			pw.println(id+"\t"+friend.longValue()+"\t"+x.getText()+"\t"+x.getId());
//		}
//		pw.close();
//		return lowest_id;
		
	}

	public static void addUserstolist(List<String>files, ArrayList<Long> list)
	{
	    try{

		       //Create object of FileReader

	    	for(String filename:files)
	    	{
			       FileReader inputFile = new FileReader(filename);	    		
			       BufferedReader bufferReader = new BufferedReader(inputFile);
			       String line = null;

			       // Read file line by line and print on the console
			       while ((line = bufferReader.readLine()) != null)   {
			    	   list.add(Long.parseLong(line));
			       }
			       bufferReader.close();
	    	}
	    }
	    catch(Exception e){
		       System.out.println("Error while reading file line by line:" + e.getMessage());                      
		    }
	    
//	    	
//		       FileReader inputFile1 = new FileReader("test1.txt");
//		       FileReader inputFile2 = new FileReader("test2.txt");
//		       FileReader inputFile3 = new FileReader("test3.txt");
//		       FileReader inputFile4 = new FileReader("test4.txt");
//		       //Instantiate the BufferedReader Class
//
//		       BufferedReader bufferReader1 = new BufferedReader(inputFile1);
//		       BufferedReader bufferReader2 = new BufferedReader(inputFile2);
//		       BufferedReader bufferReader3 = new BufferedReader(inputFile3);
//		       BufferedReader bufferReader4 = new BufferedReader(inputFile4);
//		       //Variable to hold the one line data
//		       String line = null;
//
//		       // Read file line by line and print on the console
//		       while ((line = bufferReader1.readLine()) != null)   {
//		    	   list.add(Long.parseLong(line));
//		       }
//		       while ((line = bufferReader2.readLine()) != null)   {
//		    	   list.add(Long.parseLong(line));
//		       }
//		           while ((line = bufferReader3.readLine()) != null)   {
//			    	   list.add(Long.parseLong(line));
//		           }        	   
//		               while ((line = bufferReader4.readLine()) != null)   {
//		    	    	   list.add(Long.parseLong(line));
//		               }
//		        	   
//		    	   
//		       
//		       //Close the buffer reader
//		       bufferReader1.close();
//		       bufferReader2.close();
//		       bufferReader3.close();
//		       bufferReader4.close();
		    	
		    	
		       
//		    }
//	catch(Exception e){
//		       System.out.println("Error while reading file line by line:" + e.getMessage());                      
//		    }			
	}

}
