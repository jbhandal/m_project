import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.User;

public class Parse_Tweet_For_Information {
	static String file_info = "Info.txt";	
	static TweetUserInfo var = new TweetUserInfo();
	public static void main(String args[])
	{
		var.initTweetConnectors("Profiles.txt");
		String filename = "Tweets.txt";

		split_tweets(filename);
	}
	
	public static void split_tweets(String filename)
	{
		int line_no=1;
		try
		{
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			while((line  = br.readLine())!=null)
			{
				String tokens[] = line.split("\t");
				parse_for_info(tokens);
				line_no++;
			}
		}
		catch(Exception e)
		{
			System.out.println(line_no);
		}
		
		
	}

	private static void parse_for_info(String[] tokens) {
		// TODO Auto-generated method stub
		String tweet = tokens[3];
		Pattern pattern = Pattern.compile("^.*(birthday|b'day|bday|Birthday).*$");
		Matcher matcher = pattern.matcher(tweet);
		boolean first_time = false;
		if(matcher.find())
		{

			File file =new File(file_info);
			try
			{
				if(!file.exists()){
					file.createNewFile();
					FileWriter filewriter = new FileWriter(file_info);
					BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
					PrintWriter pw = new PrintWriter(bufferedwriter);
					pw.println("Userid"+"\t"+"UserName"+"\t"+"BirthDate");
					pw.close();
					
				}
				
			}
			catch (Exception e)
			{
				System.out.println(e.getMessage());
			}

//			if(!first_time)
//			{
//				try
//				{
//					FileWriter filewriter = new FileWriter(file_info);
//					BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
//					PrintWriter pw = new PrintWriter(bufferedwriter);
//					pw.println("Userid"+"\t"+"UserName"+"\t"+"BirthDate");
//					pw.close();
//				}
//				catch(Exception e)
//				{
//					System.out.println(e.getMessage());
//				}
//				first_time=true;
//			}
			
			build_info(tokens);
		}
//		else
//		return false;

	}

	private static void build_info(String[] tokens) {
		// TODO Auto-generated method stub
		Long userid  = Long.parseLong(tokens[0]);
		Long tweetid = Long.parseLong(tokens[4]);
		try
		{
			File file =new File(file_info);
//			if(!file.exists()){
//				file.createNewFile();
//			}

			FileWriter fw = new FileWriter(file,true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			Twitter twitter = var.getTweetConnector("/users/lookup");
			User user = twitter.showUser(userid);
			Status st = twitter.showStatus(tweetid);
			System.out.println(user.getId()+"\t"+user.getName()+"\t"+(new SimpleDateFormat("MM-dd-yyyy").format(st.getCreatedAt())));
			pw.println(user.getId()+"\t"+user.getName()+"\t"+(new SimpleDateFormat("MM-dd-yyyy").format(st.getCreatedAt())));
			pw.close();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
