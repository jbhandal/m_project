

//import User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import twitter4j.IDs;
import twitter4j.Paging;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class tweet_getter extends Thread {
	Twitter twitter;
	ArrayList<Long> user_list;
	String filename;
	public tweet_getter (Twitter t, ArrayList<Long> list,String file)
	{
		twitter = t;
		user_list = list;
		filename  =file;
	}

	public void run()
	{
		RateLimitStatus status ;
		Map<String, RateLimitStatus> rateLimitStatus;
		Status st;
		List<Status> statuses = null;

		IDs friends_list,followers_list;
		long cursor = -1;
		Paging page;

		Iterator it = user_list.iterator();
		while(it.hasNext())
		{
			Long val = (Long) it.next();
			long id = val.longValue();
			try {
				rateLimitStatus = twitter.getRateLimitStatus();
				status = rateLimitStatus.get("/friends/ids");
				if(status.getRemaining() == 0)
				{
					System.out.println(twitter.showUser(twitter.getId()).getName());
					Thread.sleep(900000);
				}
				friends_list  = twitter.getFriendsIDs(id, cursor);
				HashSet<Long> friends_set = new HashSet<Long>();
				do {
					for (long id1 : friends_list.getIDs()){
						friends_set.add(id1);
						//                	System.out.println("Friends:"+id1);
					}
				} while ((cursor = friends_list.getNextCursor()) != 0);


				rateLimitStatus = twitter.getRateLimitStatus();
				status = rateLimitStatus.get("/followers/ids");
				if(status.getRemaining() == 0)
				{
					System.out.println(twitter.showUser(twitter.getId()).getName());
					Thread.sleep(900000);
				}
				HashSet<Long> followers_set = new HashSet<Long>();
				cursor = -1;
				followers_list = twitter.getFollowersIDs(id,cursor);
				System.out.println("Followers present:"+followers_list.hasNext());
				do {
					for (long id1 : followers_list.getIDs()){ 
						followers_set.add(id1);
						//                	System.out.println("Follower:"+id1);
					}
				} while ((cursor = followers_list.getNextCursor()) != 0);

				friends_set.retainAll(followers_set);
				Iterator iter = friends_set.iterator();
				while(iter.hasNext())
				{
					Long friend = (Long) iter.next();
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

						statuses = twitter.getUserTimeline(friend.longValue(),page);
						File file =new File(filename);
						if(!file.exists()){
							file.createNewFile();
						}
						FileWriter fw = new FileWriter(file,true);
						BufferedWriter bw = new BufferedWriter(fw);
						PrintWriter pw = new PrintWriter(bw);
						for(Status x:statuses)
						{
							String text = x.getText();
							status = null;
							status = rateLimitStatus.get("/users/show/:id");
							if(status.getRemaining() == 0)
							{
								System.out.println(twitter.showUser(twitter.getId()).getName());
								Thread.sleep(900000);
							}
							twitter4j.User u = twitter.showUser(id);
							if(Utility.checktweetforuserhandle(text,u))
							//Once the text is retrieved check for the user's handle in the friend's text 
							pw.println(id+"\t"+friend.longValue()+"\t"+x.getText()+"\t"+x.getId());
						}
						pw.close();

					}


				}


			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					System.out.println("Sleeping:"+twitter.showUser(twitter.getId()).getName());
				} catch (IllegalStateException | TwitterException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					Thread.sleep(900000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
