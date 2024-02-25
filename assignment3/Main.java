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
    public static char[] alphabet;
    public static Set<String> dictionary;
    public static boolean DFSfoundword;

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
        if (keyWords != null) {
            printLadder(getWordLadderDFS(keyWords.get(0), keyWords.get(1)));
        }
    }

    public static void initialize() {
        // initialize your static variables or constants here.
        // We will call this method before running our JUnit tests.  So call it
        // only once at the start of main.
        nodes = new ArrayList<String>();
        path = new ArrayList<String>();
        dictionary = makeDictionary();
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
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
                keyWords = null;
                return null;
            }
            keyWords.add(tmp);
            i--;
        }
        return keyWords;
    }

    public static ArrayList<String> getWordLadderDFS(String start, String end) {
        // Returned list should be ordered start to end.  Include start and end.
        // If ladder is empty, return list with just start and end.
        Deque<String> undiscoveredNodes = new ArrayDeque<>();
        ArrayList<String> Discovered = new ArrayList<String>();
        start = start.toUpperCase();
        end = end.toUpperCase();
        undiscoveredNodes.add(start);
        Discovered.add(start);
        DFSfoundword = false;
        ArrayList<String> wordLadder = new ArrayList<String>();
        recursiveDFS(start, end, undiscoveredNodes, Discovered, wordLadder);
        for (int h = 0; h < wordLadder.size(); h++) {
            wordLadder.set(h, wordLadder.get(h).toLowerCase());
        }
        ArrayList<String> wordLadderTmp = new ArrayList<String>(wordLadder);
        int p = 0;
        int q = wordLadder.size()-1;
        while (q >= 0) {
            wordLadder.set(p, wordLadderTmp.get(q));
            q--;
            p++;
        }
        wordLadderTmp.clear();

        ArrayList<String> dummy = new ArrayList<String>(DFSsort(wordLadder, wordLadderTmp));
        wordLadderTmp.clear();
        wordLadder.clear();
        return dummy;
    }

    public static ArrayList<String> getWordLadderBFS(String start, String end) {
        Queue<String> undiscoveredNodes = new LinkedList<String>();
        Map<String, String> Pairs = new HashMap<>();
        ArrayList<String> Discovered = new ArrayList<String>();
        ArrayList<String> wordLadder = new ArrayList<String>();
        start = start.toUpperCase();
        end = end.toUpperCase();
        undiscoveredNodes.add(start);
        Discovered.add(start);
        while (!undiscoveredNodes.isEmpty()) {
            String tmp = undiscoveredNodes.poll();
            for (int i = 0; i < start.length(); i++) {
                char[] word = tmp.toCharArray();
                for (int j = 0; j < alphabet.length; j++) {
                    word[i] = alphabet[j];
                    String tmp2 = new String(word);
                    if (dictionary.contains(tmp2) && !Discovered.contains(tmp2)) {
                        undiscoveredNodes.add(tmp2);
                        Discovered.add(tmp2);
                        Pairs.put(tmp2,tmp);
                        if (tmp2.equals(end)) {
                            wordLadder.add(tmp2);
                            while (Pairs.containsKey(tmp2)) {
                                wordLadder.add(Pairs.get(tmp2));
                                tmp2 = Pairs.get(tmp2);
                            }
                            for (int h = 0; h < wordLadder.size(); h++) {
                                wordLadder.set(h, wordLadder.get(h).toLowerCase());
                            }
                            ArrayList<String> wordLadderTmp = new ArrayList<String>(wordLadder);
                            int p = 0;
                            int q = wordLadder.size()-1;
                            while (q >= 0) {
                                wordLadder.set(p, wordLadderTmp.get(q));
                                q--;
                                p++;
                            }
                            wordLadderTmp.clear();
                            return wordLadder;
                        }
                    }
                }
            }
        }
        wordLadder.add(start);
        wordLadder.add(end);
        for (int k = 0; k < wordLadder.size(); k++) {
            wordLadder.set(k, wordLadder.get(k).toLowerCase());
        }
        ArrayList<String> wordLadderTmp = new ArrayList<String>(wordLadder);
        int n = 0;
        int m = wordLadder.size()-1;
        while (m > 0) {
            wordLadder.set(n, wordLadderTmp.get(m));
            m--;
            n++;
        }
        wordLadderTmp.clear();
        return wordLadder;
    }


    public static void recursiveDFS(String start, String end, Deque<String> undiscoveredNodes, ArrayList<String> Discovered, ArrayList<String> wordLadder) {
        //Checks flag that is true when the word is discovered in that case adds current word to the ladder and returns
        if (DFSfoundword) {
            wordLadder.add(start);
            return;
        }

        //Add all nodes that are incident to the current node to the front of the queue
        //i.e. All the words that are one letter different from current word AND not a word already in list AND not already in discovered.
        while (!undiscoveredNodes.isEmpty()) {
            start = start.toUpperCase();
            String curr = undiscoveredNodes.poll();
            if (Objects.equals(start, end)) {
                DFSfoundword = true;
                wordLadder.add(start);
                return;
            }
            for (int i = 0; i < start.length(); i++) {
                char[] word = curr.toCharArray();
                for (int j = 0; j < alphabet.length; j++) {
                    word[i] = alphabet[j];
                    String tmp2 = new String(word);
                    tmp2 = tmp2.toUpperCase();
                    if (dictionary.contains(tmp2) && !Discovered.contains(tmp2)) {
                        undiscoveredNodes.addFirst(tmp2);
                        Discovered.add(tmp2);
                        recursiveDFS(tmp2, end, undiscoveredNodes, Discovered, wordLadder);
                    }
                    if (DFSfoundword) {
                        wordLadder.add(start);
                        return;
                    }
                }
            }
        }
    }

    public static void printLadder(ArrayList<String> ladder) {
        if (ladder.size() == 2) {
            System.out.println("no word ladder can be found between " + ladder.get(0) + " and " + ladder.get(1) + ".");
        } else {
            System.out.println("a " + (ladder.size() - 2) + "-rung word ladder exists between " + ladder.get(0) + " and " + ladder.get(ladder.size() - 1) + ".");
            for (int i = 0; i <= ladder.size() - 1; i++) {
                System.out.println(ladder.get(i));
            }
        }
    }

    public static boolean isNode(String word1, String word2) {
        int count = 0;
        for(int i =0; i<word1.length(); i++){
            if(word1.charAt(i) == word2.charAt(i)){
                count++;
            }
        }
        return count >= 4;
    }


    //Start from the end of the wordLadder-1 and look for words that are one letter away then create new word ladder
    //Going through each of the word in the ArrayList
    public static ArrayList<String> DFSsort(ArrayList<String> wordLadder, ArrayList<String> wordLadderTmp) {
        int size2 = wordLadder.size() - 1;
        for (int i = 0; i < size2; i++) {
            for (int j = size2; j >= 0; j--) {
                //Call method that checks if the words are one letter away
                if (isNode(wordLadder.get(i), wordLadder.get(j))) {
                    //Creates new array that has all previous nodes up to current node and node it connected too
                    //and all nodes after that.
                    // i = 1
                    // j = 4
                    // ab be ce de ac ae ae - wL
                    // k = 1
                    // l = 6
                    // a b e f
                    int removeVal = j-i-1;
                    int a = i+1;
                    int counter = 0;
                    while (counter < removeVal) {
                        wordLadder.remove(a);
                        counter++;
                    }
                    size2 = size2 - counter;
                    break;
                }
            }
        }
        return wordLadder;
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
