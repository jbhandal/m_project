

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import twitter4j.IDs;
import twitter4j.Paging;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.Paging;

public class Friend_List extends Thread{


//We need to create a bucket where we keep getting users and test them for our criteria
//Our criteria is 1500 followers and 3000 tweets
	
	
	HashMap<User,List<Status>> hmap;
	ArrayList<Long> list;
	Twitter twitter;
	String filename;
	long user_id=0;
	PrintWriter w; 
	public Friend_List(Twitter twitter_arg,ArrayList<Long> friend_list,String file) throws TwitterException, FileNotFoundException, UnsupportedEncodingException {
		// TODO Auto-generated constructor stub
        list = friend_list;
        twitter = twitter_arg;
        filename = file;
        hmap = new HashMap<User,List<Status>>();
    	Random random = new Random();
    	w = new PrintWriter("file_"+random.nextInt(100)+".txt","UTF-8");
	}

	
     @SuppressWarnings("resource")
	public void run()
     {
    	 long id;
    	 int number_of_users=0;

        //this will set the number of entries retrieved from the API to 2200
    	try{
		Paging page = new Paging();
		page.setCount(2200);
    	List<Status> statuses = null;
    	IDs friend_ids,followers_id;
//List of friends
    	ArrayList<Long> friends = null;
//cursor - Causes the list of connections to be broken into pages of no more than 5000 IDs    	
//    	at a time. The number of IDs returned is not guaranteed to be 5000 as suspended 
//    	users are filtered out after connections are queried. 
//    	To begin paging provide a value of -1 as the cursor. The response from the API 
//    	will include a previous_cursor and next_cursor to allow paging back and forth.
    	PrintWriter writer,writer_all;
    	Date d = new Date();
    	writer = new PrintWriter(filename, "UTF-8");

    	w.println("Welcome All");
    	long cursor = -1;
        IDs ids;
        ArrayList<Status> list_status;
//we are using the RateLimitStatus to keep track of the number of API calls
        RateLimitStatus status ;
        Map<String, RateLimitStatus> rateLimitStatus;

//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        Date date = new Date();
        Status st;
//        System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48
        Iterator it = list.iterator();
        int counter = 0;
        User user = null;
    	System.out.println("Thread has been created for: "+twitter.showUser(twitter.getId()).getName()+ "Size of list: "+list.size());		    				
while(it.hasNext()) {
		id = (long) it.next();
		try {

			counter ++;
//get the statuses of the users 
			
			int count=0;
			list_status = new ArrayList<Status>();
            for(int i=1;i<20;i++)
            {
            	page = new Paging(i,200);
    			rateLimitStatus = twitter.getRateLimitStatus();
    			status = rateLimitStatus.get("/statuses/user_timeline");
    		    if(status.getRemaining() == 0)
    		    {
    		    	System.out.println(twitter.showUser(twitter.getId()).getName());
    				Thread.sleep(900000);
    		    }

            	statuses = twitter.getUserTimeline(id,page);
            	for(Status x:statuses)
            	{
            		list_status.add(x);
            	}
            	count = count + statuses.size();
            }
//			statuses = twitter.getUserTimeline(id,page);
//get the followers of the userid
			rateLimitStatus = twitter.getRateLimitStatus();
			status = rateLimitStatus.get("/followers/ids");
		    if(status.getRemaining() == 0)
		    {
		    	System.out.println(twitter.showUser(twitter.getId()).getName());
				Thread.sleep(900000);
		    }
//		    System.out.println(status.getRemaining());
		    int number_of_followers =0;
		    followers_id = twitter.getFollowersIDs(id,cursor);
//            do {
                for (long id1 : followers_id.getIDs()) 
                	number_of_followers++;
//                	
//                 if(number_of_followers>1000)
//                 {
//                	 break;
//                 }
//                
//             } while ((cursor = followers_id.getNextCursor()) != 0);
		    
		    


			int number_of_status = count;
			if(counter%10==0)
			{
				System.out.println("Number of users serviced: "+counter+" User:"+twitter.showUser(twitter.getId()).getName());
			}
//			if (number_of_status > 1000 && number_of_followers > 500)
//			{
//	            	user = twitter.showUser(id);
//					System.out.println("Probable user " +user.getName() + " ID: " +user.getId());
//
//			}
//			System.out.println("Info:"+id +" "+number_of_status+" "+number_of_followers);
//			w.println("Info:"+id +" "+number_of_status+" "+number_of_followers);
			if (number_of_status > 3000 && number_of_followers >= 300)
			{
				File file =new File(filename);
		    	  if(!file.exists()){
		    	 	file.createNewFile();
		    	  }
		    	  FileWriter fw = new FileWriter(file,true);
		    	  BufferedWriter bw = new BufferedWriter(fw);
		    	  PrintWriter pw = new PrintWriter(bw);
					number_of_users++;
					hmap.put(user, list_status);
		        	user = twitter.showUser(id);
					pw.println(id);
		        	//					pw.println(user.getName());
//					for(Status y:list_status)
//					{
//						writer.println(y.getText());
//					}
//					writer.println("---------------------------");
//					writer.println("---------------------------");
					pw.close();
		        	System.out.println("Following user added to the list " +user.getName() );
		        	System.out.println("Number of users selected :"+number_of_users+" by "+twitter.showUser(twitter.getId()).getName());
			}
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			if(e.getErrorCode()==88)
			{
				Thread.sleep(900000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        	
		}        

//for (Map.Entry<User,List<Status>> entry : hmap.entrySet())
//{
//		User u = entry.getKey();
//		List<Status> s = entry.getValue();
//		System.out.println("Name of user:"+u.getName());
//		Iterator it1 = s.iterator();
//		writer.println(u.getName());
//		while(it1.hasNext())
//		{
//
//			writer.println(it1.next());
//		}
//		writer.println("---------------------------");
//		writer.println("---------------------------");
//}				
//writer.close();
    	}
    	catch (Throwable e2)
    	{
			e2.printStackTrace();
    	}
	}

}
