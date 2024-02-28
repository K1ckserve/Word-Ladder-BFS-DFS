package assignment3;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

public class MainTest {
    private static Set<String> dict;
    private static ByteArrayOutputStream outContent;

    private static final int SHORT_TIMEOUT = 300; // ms
    private static final int SEARCH_TIMEOUT = 30000; // ms

    private SecurityManager initialSecurityManager;

    @Rule // Comment this rule and the next line out when debugging to remove timeouts
    public Timeout globalTimeout = new Timeout(SEARCH_TIMEOUT);

    @Before // this method is run before each test
    public void setUp() {
        Main.initialize();
        dict = Main.makeDictionary();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        initialSecurityManager = System.getSecurityManager();
//        System.setSecurityManager(new NoExitSecurityManager());
    }

    @After
    public void cleanup() {
        System.setSecurityManager(initialSecurityManager);
    }

    @Test
//    @Score(1)
    public void testDFSDoesNotExist() {
        ArrayList<String> res = Main.getWordLadderDFS("think", "zebra");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertNotNull(res);
        assertNotEquals(0, res.size());
        assertEquals("think", res.get(0));
        assertEquals("zebra", res.get(1));
        assertFalse(verifyLadder(res, "think", "zebra"));
    }

    @Test
    public void BFS_Functionality(){
        ArrayList<String> tester = new ArrayList<>();
        tester.add("smart");
        tester.add("scart");
        tester.add("scatt");
        tester.add("scats");
        tester.add("seats");
        tester.add("beats");
        tester.add("bents");
        tester.add("benes");
        tester.add("bones");
        tester.add("boney");
        tester.add("money");
        tester.equals(Main.getWordLadderBFS("smart","money"));
    }

    @Test
    public void BFS_Longest(){
        //ArrayList<String> tester = new ArrayList<>();
        Main.printLadder(Main.getWordLadderBFS("iller","nylon"));
    }
    @Test
    public void BFS_No_Ladder(){
        ArrayList<String> tester = new ArrayList<>();
        tester.add("boney");
        tester.add("zebra");
        tester.equals(Main.getWordLadderBFS("boney","zebra"));
    }
    @Test
    public void BFS_Quit(){
        Main.printLadder(Main.getWordLadderBFS("/quit","/quit"));
    }
    @Test
    public void BFS_Repeat(){
        ArrayList<String> tester = new ArrayList<>();
        tester.add("capes");
        tester.add("cares");
        tester.add("lares");
        tester.add("laree");
        tester.add("saree");
        tester.add("spree");
        tester.add("sprue");
        tester.add("sprug");
        tester.add("sprit");
        tester.add("split");
        tester.add("uplit");
        tester.add("unlit");
        tester.add("unlet");
        tester.add("inlet");
        tester.add("islet");
        tester.add("isled");
        tester.add("idled");
        tester.add("idler");
        tester.add("iller");
        tester.equals(Main.getWordLadderBFS("capes","iller"));
    }

    private boolean verifyLadder(ArrayList<String> ladder, String start, String end) {
        String prev = null;
        if (ladder == null) {
            return true;
        }
        for (String word : ladder) {
            if (!dict.contains(word.toUpperCase()) && !dict.contains(word.toLowerCase())) {
                return false;
            }
            if (prev != null && !differByOne(prev, word)) {
                return false;
            }
            prev = word;
        }
        return ladder.size() > 0
                && ladder.get(0).toLowerCase().equals(start)
                && ladder.get(ladder.size() - 1).toLowerCase().equals(end);
    }

    private static boolean differByOne(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }

        int diff = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i) && ++diff > 1) {
                return false;
            }
        }

        return true;
    }

    @Test
    public void testDFSLongest() {
        ArrayList<String> res = Main.getWordLadderDFS("iller", "nylon");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertNotNull(res);
        assertNotEquals(0, res.size());
        assertNotEquals(2, res.size());
        assertEquals("iller", res.get(0));
        assertEquals("nylon", res.get(res.size()-1));
        assertTrue(verifyLadder(res, "iller", "nylon"));
    }

    @Test
//    @Score(1)
    public void testDFSFirst2Last() {
        ArrayList<String> res = Main.getWordLadderDFS("aahed", "zymes");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertNotNull(res);
        assertNotEquals(0, res.size());
        assertNotEquals(2, res.size());
        assertEquals("aahed", res.get(0));
        assertEquals("zymes", res.get(res.size()-1));
        assertTrue(verifyLadder(res, "aahed", "zymes"));
    }

    @Test
//    @Score(1)
    public void testDFSLongPath() {
        ArrayList<String> res = Main.getWordLadderDFS("think", "steer");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertNotNull(res);
        assertNotEquals(0, res.size());
        assertNotEquals(2, res.size());
        assertEquals("think", res.get(0));
        assertEquals("steer", res.get(res.size()-1));
        assertTrue(verifyLadder(res, "think", "steer"));
    }

    @Test
//    @Score(1)
    public void testDFSShortPath() {
        ArrayList<String> res = Main.getWordLadderDFS("start", "sward");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertNotNull(res);
        assertNotEquals(0, res.size());
        assertNotEquals(2, res.size());
        assertEquals("start", res.get(0));
        assertEquals("sward", res.get(res.size()-1));
        assertTrue(verifyLadder(res, "start", "sward"));
    }
}