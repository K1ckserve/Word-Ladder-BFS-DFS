package assignment3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

public class Main {

    // static variables and constants only here.
    public static ArrayList<String> nodes;
    public static ArrayList<String> path;
    public static ArrayList<String> keyWords;
    public static Queue<Character> alphabet;
    public static Set<String> dictionary;

    public static void main(String[] args) throws Exception {
        Scanner kb;     // input Scanner for commands
        PrintStream ps; // output file, for student testing and grading only
        // If arguments are specified, read/write from/to files instead of Std IO.
        if (args.length != 0) {
            kb = new Scanner(new File(args[0]));
            ps = new PrintStream(new File(args[1]));
            System.setOut(ps);              // redirect output to ps
        } else {
            kb = new Scanner(System.in);    // default input from Stdin
            ps = System.out;                // default output to Stdout
        }
        initialize();
        parse(kb);
        getWordLadderBFS(keyWords.get(0), keyWords.get(1));
        printLadder(keyWords);

        // TODO methods to read in words, output ladder
    }

    public static void initialize() {
        // initialize your static variables or constants here.
        // We will call this method before running our JUnit tests.  So call it
        // only once at the start of main.
        nodes = new ArrayList<String>();
        path = new ArrayList<String>();
        dictionary = makeDictionary();
        alphabet = new LinkedList<Character>();
        char[] tmp = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        for (int i = 0; i < tmp.length; i++) {
            alphabet.add(tmp[i]);
        }
    }

    /**
     * @param keyboard Scanner connected to System.in
     * @return ArrayList of Strings containing start word and end word.
     * If command is /quit, return empty ArrayList.
     */
    public static ArrayList<String> parse(Scanner keyboard) {
        int i = 2;
        keyWords = new ArrayList<String>();
        while (i > 0) {
            String tmp = keyboard.next();
            if (Objects.equals(tmp, "/quit")) {
                return keyWords;
            }
            keyWords.add(tmp);
            i--;
        }
        return keyWords;
    }

    public static ArrayList<String> getWordLadderDFS(String start, String end) {
        // Returned list should be ordered start to end.  Include start and end.
        // If ladder is empty, return list with just start and end.
        // TODO some code

        return null; // replace this line later with real return
    }

    public static ArrayList<String> getWordLadderBFS(String start, String end) {
        Queue<String> undiscoveredNodes = new LinkedList<String>();
        undiscoveredNodes.add(start);
        ArrayList<LinkedList<String>> adj_list = new ArrayList<LinkedList<String>>();
        adj_list.add(new LinkedList<String>());

        while (!undiscoveredNodes.isEmpty()) {
            String tmp = undiscoveredNodes.poll();
            if (wordIsValidNode() && !(nodes.contains(tmp)) && dictionary.contains(tmp)) {
                adj_list.get(0).add(tmp);
                undiscoveredNodes.offer();
                //Set this node as discovered
                //Add all of its neighbors to the queue.
            }
        }
        return null; // replace this line later with real return
    }

    public static void printLadder(ArrayList<String> ladder) {
        for(int i = 0; i < ladder.size(); i++) {
            System.out.println(ladder.get(i));
        }
    }

    public static boolean wordIsValidNode(String one, String two){
        int count = 0;
        for(int i =0; i<one.length(); i++){
            if(one.charAt(i) == two.charAt(i)){
                count++;
            }
        }
        return count >= 4;
    }

    /* Do not modify makeDictionary */
	public static Set<String> makeDictionary() {
        Set<String> words = new HashSet<String>();
        Scanner infile = null;
        try {
            infile = new Scanner(new File("five_letter_words.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Dictionary File not Found!");
            e.printStackTrace();
            System.exit(1);
        }
        while (infile.hasNext()) {
            words.add(infile.next().toUpperCase());
        }
        return words;
    }
}
