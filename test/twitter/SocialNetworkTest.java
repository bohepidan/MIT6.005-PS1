/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
	/*
	 * geussFollowGraph:
	 * 	input:
	 * 		tweets: noMentions, mention 1, mention >1
	 * 	output:
	 */
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about @rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "laolai", "@rivest talk in 30 minutes @hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "laolai", "@songyu lai he lao zi da jia @songyu @goushi", d2);
    private static final Tweet tweet4 = new Tweet(4, "songyu", "jiang zi shuai?", d2);
    @Test
	public void myTestGuessFollowGraph(){
		Map<String, Set<String>> followsGraph0 = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet4));
		Map<String, Set<String>> followsGraph1 = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1));
		Map<String, Set<String>> followsGraph2 = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet2));
		Map<String, Set<String>> followsGraph3 = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet3));
		Map<String, Set<String>> followsGraph4 = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet2,tweet3));
		
		assertTrue("expected empty", followsGraph0.isEmpty() || (followsGraph0.containsKey("songyu") &&followsGraph0.get("songyu").isEmpty()));

		assertTrue("expected keyContains", followsGraph1.containsKey("alyssa"));
		assertTrue("expected valueContains", followsGraph1.get("alyssa").contains("rivest"));

		assertTrue("expected keyContains", followsGraph2.containsKey("laolai"));
		assertTrue("expected valueContains", followsGraph2.get("laolai").contains("rivest"));
		assertTrue("expected valueContains", followsGraph2.get("laolai").contains("hype"));

		assertTrue("expected keyContains", followsGraph3.containsKey("laolai"));
		assertTrue("expected valueContains", followsGraph3.get("laolai").contains("songyu"));
		assertTrue("expected valueContains", followsGraph3.get("laolai").contains("goushi"));

		assertTrue("expected keyContains", followsGraph4.containsKey("laolai") && followsGraph4.containsKey("laolai"));
		assertTrue("expected valueContains", followsGraph4.get("laolai").contains("rivest"));
		assertTrue("expected valueContains", followsGraph4.get("laolai").contains("hype"));
		assertTrue("expected valueContains", followsGraph4.get("laolai").contains("songyu"));
		assertTrue("expected valueContains", followsGraph4.get("laolai").contains("goushi"));

	}
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }
    
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
