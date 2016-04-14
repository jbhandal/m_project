

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import twitter4j.Twitter;
import twitter4j.User;

public class Utility {

	public static boolean checktweetforuserhandle(String text,User u)
	{
		Pattern pattern = Pattern.compile(".*"+u.getScreenName()+".*");
		Matcher matcher = pattern.matcher(text);
		if(matcher.find())
		return true;
		else
		return false;
		
	}
}
