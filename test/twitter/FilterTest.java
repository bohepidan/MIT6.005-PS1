/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
	/*
	 * writtenBy: 
	 * 	tweets: empty, noContains, contains
	 * 	
	 */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }    

    
    private static final Tweet myTweet1 = new Tweet(1, "laolai", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet myTweet2 = new Tweet(2, "songyu", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet myTweet3 = new Tweet(3, "laolai", "lao zi zui qiang @songyu", d2);

    @Test
    public void myTestWrittenBy(){
        List<Tweet> writtenBy1 = Filter.writtenBy(Arrays.asList(myTweet1), "songyu");
        List<Tweet> writtenBy2 = Filter.writtenBy(Arrays.asList(myTweet1, myTweet2), "songyu");
        List<Tweet> writtenBy3 = Filter.writtenBy(Arrays.asList(myTweet1, myTweet2, myTweet3), "laolai");
    	
        assertEquals("expected singleton list", 0, writtenBy1.size());
        assertEquals("expected singleton list", 1, writtenBy2.size());
        assertEquals("expected singleton list", 2, writtenBy3.size());
        assertTrue("expected list to contain tweet", writtenBy1.isEmpty());
        assertTrue("expected list to contain tweet", writtenBy2.contains(myTweet2));
        assertTrue("expected list to contain tweet", writtenBy3.contains(myTweet1) &&  writtenBy3.contains(myTweet3));
    }
    
    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }
    /*
     * inTimespan:
     * 	timespan:
     * 	tweets:	nobetween, at two sides, between
     */
    @Test
    public void myTestInTimespan(){
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant test1 = Instant.parse("2016-02-17T10:00:00Z");
        Instant test2 = Instant.parse("2016-02-17T11:00:00Z");
        Instant test3 = Instant.parse("2016-02-17T10:15:00Z");
        Instant test4 = Instant.parse("2016-02-17T10:30:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");

        List<Tweet> inTimespan1 = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(test3, test4));
        List<Tweet> inTimespan2 = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(test1, test2));
        List<Tweet> inTimespan3 = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertTrue("expected empty", inTimespan1.isEmpty());
        assertTrue("expected contain", inTimespan2.contains(tweet1) && inTimespan2.contains(tweet2));
        assertTrue("expected contain", inTimespan3.contains(tweet1) && inTimespan3.contains(tweet2));
    }
    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }
    /*
     * containing:
     * 	input:
     * 		words:
     * 		tweets: noContains, contains, different case
     * 	output:
     * 		sameOrder as input
     * 
     */
    //private static final Tweet myTweet1 = new Tweet(1, "laolai", "is it reasonable to talk about rivest so much?", d1);
    //private static final Tweet myTweet2 = new Tweet(2, "songyu", "rivest talk in 30 minutes #hype", d2);
    //private static final Tweet myTweet3 = new Tweet(3, "laolai", "lao zi zui qiang @songyu", d2);
    @Test
    public void myTestContaning(){
        List<Tweet> containing1 = Filter.containing(Arrays.asList(myTweet1, myTweet2), Arrays.asList("qiang"));
        List<Tweet> containing2 = Filter.containing(Arrays.asList(myTweet1, myTweet2, myTweet3), Arrays.asList("qiang"));
        List<Tweet> containing3 = Filter.containing(Arrays.asList(myTweet1, myTweet2, myTweet3), Arrays.asList("talk"));

        assertTrue("expected empty list", containing1.isEmpty());
        assertTrue("expected list to contain tweets", containing2.containsAll(Arrays.asList(myTweet3)));
        assertTrue("expected list to contain tweets", containing3.containsAll(Arrays.asList(myTweet1, myTweet2)));
        assertEquals("expected same order", 0, containing3.indexOf(myTweet1));
        assertEquals("expected same order", 1, containing3.indexOf(myTweet2));
    }
    @Test
    public void testContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }

    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */

}
