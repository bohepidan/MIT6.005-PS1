/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     * 
     * getTimeSpan:
     * 	Partition inputs:
     * 	tweets.size : 1, 2, >2
     *	timeSpan: 0, !0
     */
    private static final Instant day1 = Instant.parse("2022-09-06T16:30:00Z");
    private static final Instant day2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant day3 = Instant.parse("2015-02-17T11:00:00Z");

    private static final Tweet myTweet1 = new Tweet(1, "laolai", "wo diao ni ma", day1);
    private static final Tweet myTweet2 = new Tweet(2, "songyu", "zen me shuo?@laolai", day2);
    private static final Tweet myTweet3 = new Tweet(2, "songyu", "@goushiguai jiang zi shuai? @goushi wo@jiangzi shi ni die", day3);
    
	@Test
	public void testGetTimeSpan(){
		Timespan timespan1 = Extract.getTimespan(Arrays.asList(myTweet1));
		Timespan timespan2 = Extract.getTimespan(Arrays.asList(myTweet1, myTweet2));
		Timespan timespan3 = Extract.getTimespan(Arrays.asList(myTweet1, myTweet2, myTweet3));

		assertEquals("expected start", day1, timespan1.getStart());
        assertEquals("expected end", day1, timespan1.getEnd());
        
		assertEquals("expected start", day2, timespan2.getStart());
        assertEquals("expected end", day1, timespan2.getEnd());  
        
		assertEquals("expected start", day3, timespan3.getStart());
        assertEquals("expected end", day1, timespan3.getEnd());
	}
	
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    /*
     * getMentionedUsers:
     * 	 mentioned usr: 0, 1, >1 
     * 	
     */
    @Test
    public void myTestGetMentionedUsersNoMention(){
    	Set<String> mentionedUsers1 = Extract.getMentionedUsers(Arrays.asList(myTweet1));
    	Set<String> mentionedUsers2 = Extract.getMentionedUsers(Arrays.asList(myTweet1,myTweet2));
    	Set<String> mentionedUsers3 = Extract.getMentionedUsers(Arrays.asList(myTweet1,myTweet2,myTweet3));
    	
    	assertTrue("expected empty", mentionedUsers1.isEmpty());

    	assertTrue("expected contain tweet laolai", mentionedUsers2.contains("laolai"));

    	assertTrue("expected contain tweet laolai", mentionedUsers3.contains("laolai"));
    	assertTrue("expected contain tweet laolai", mentionedUsers3.contains("goushiguai"));
    	assertFalse("expected contain tweet laolai", mentionedUsers3.contains("jiangzi"));
    	assertTrue("expected contain tweet goushi", mentionedUsers3.contains("goushi"));
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
