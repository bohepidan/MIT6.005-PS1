/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
    	Timespan timespan = new Timespan(tweets.get(0).getTimestamp(), tweets.get(0).getTimestamp());
    	for(Tweet tweet : tweets){
    		if(tweet.getTimestamp().isBefore(timespan.getStart())){
    			timespan = new Timespan(tweet.getTimestamp(), timespan.getEnd());
    		}
    		if(tweet.getTimestamp().isAfter(timespan.getEnd())){
    			timespan = new Timespan(timespan.getStart(), tweet.getTimestamp());
    		}
    	}
    	return timespan;
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    
    private static boolean charIsValidUsername(char ch){
    	return Character.isLetter(ch) || ch == '-' || ch == '_' || Character.isDigit(ch);
    }
    
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
    	Set<String> mentionedUsers = new HashSet<String>();
    	for(Tweet tweet : tweets){
    		String text = tweet.getText();
    		for(int index = 0; index < text.length(); ){
    			int startIndex = text.indexOf("@", index);
    			if(startIndex == -1)
    				break;
    			if(startIndex == 0 || !charIsValidUsername(text.charAt(startIndex - 1))){
    				int finishIndex = startIndex + 1;
    				for(; finishIndex < text.length() && charIsValidUsername(text.charAt(finishIndex)) ; finishIndex++) ;
    				String substr = text.substring(startIndex+1, finishIndex).toLowerCase();
    				if(!mentionedUsers.contains(substr));
						mentionedUsers.add(substr);
    			}
    			index = startIndex + 1;
    		}
    	}
    	return mentionedUsers;
    }

}
