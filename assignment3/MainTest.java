package assignment3;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;

import java.util.ArrayList;

public class MainTest {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(30);

    @Before // This method gets run once before each test
    public void setup() {
        Main.initialize();

        // Any more initialization you want to do before each test
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

    // Write your JUnit tests here
}
