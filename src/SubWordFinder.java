import java.io.File;
import java.util.*;

/**
 * SubWordFinder finds the subwords in the given text file
 * @version 2/13/2024
 * @author Kyngston Gaddy
 */
public class SubWordFinder implements WordFinder {
    private ArrayList<ArrayList<String>> dictionary;
    private String alpha = "abcdefghijklmnopqrstuvwxyz";

    /**
     * SubWordFinder creates a database of all the words in the file using an arraylist of buckets
     */
    public SubWordFinder() {
        dictionary = new ArrayList<>();
        for(int i = 0; i < 26; i++) {
            dictionary.add(new ArrayList<>());
        }
    }

    /**
     * Populates the dictionary from the text file contents
     */
    public void populateDictionary() {
        Scanner in = null;
        try {
            in = new Scanner(new File("words_all_os.txt"));
            String line;
            while(in.hasNext()) {
                line = in.nextLine();
                dictionary.get(alpha.indexOf(line.substring(0,1))).add(line);
            }
            in.close();
            for(int i = 0; i < dictionary.size(); i++) {
                Collections.sort(dictionary.get(i));
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve all SubWord objects from the dictionary.
     * @return An ArrayList containing the SubWord objects
     * pulled from the file words.txt
     */
    public ArrayList<SubWord> getSubWords() {
        ArrayList<SubWord> subwords = new ArrayList<>();
        ArrayList<String> frequencies = new ArrayList<>(); // extra - finding the frequency of all the words with the most possible combinations
        int temp = 0; // temp variable that stores the highest amount of subword combinations
        for(ArrayList<String> bucket : dictionary) {
            for(String word : bucket) {
                int count = 0; // the count variable counts how many subword combinations there are in a word
                for(int i = 2; i < word.length()-1; i++) {
                    String s1 = word.substring(0,i);
                    String s2 = word.substring(i);
                    if(inDictionary(s1) && inDictionary(s2)) {
                        subwords.add(new SubWord(word, s1, s2));
                        count++; // adds one for every valid subword combination
                    }
                }
                if(count > temp) {
                    temp = count; // replaces the temp # with new highest amount of subword combinations
                    frequencies.clear(); // this clears the list of all the previous highest number of subword combinations
                    frequencies.add(word); // replaces the words in the list will all the current highest subword combinations
                }
                else if (count == temp)
                    frequencies.add(word); // if the word has the same amount of combinations has the temp variable, then it will still add it to the frequencies arraylist
            }
        }
        System.out.println("There are " + frequencies.size() + " words that have " + temp + " subword combinations. Those words are: " + frequencies);
        return subwords;
    }

    /**
     * Look through the entire dictionary object to see if word exists in dictionary
     * @param word The item to be searched for in dictionary
     * @return true if word is in dictionary, false otherwise
     */
    public boolean inDictionary(String word) {
        return myBSearch(word);
    }

    private boolean myBSearch(String word) {
        ArrayList<String> bucket = dictionary.get(alpha.indexOf(word.substring(0,1)));
        int left = 0, right = bucket.size() - 1;
        while(left <= right) {
            int middle = (left + right) / 2;
            int diff = word.compareTo(bucket.get(middle));
            if(diff < 0)
                right = middle - 1;
            else if (diff > 0)
                left = middle + 1;
            else {
                return true;
            }
        }
        return false;
    }

    /**
     * Main method of the program
     * @param args, command line args if needed
     */
    public static void main(String[] args) {
        SubWordFinder app = new SubWordFinder();
        app.populateDictionary();
        ArrayList<SubWord> subs = app.getSubWords();
        for (SubWord sub : subs)
            System.out.println(sub);
        System.out.println(subs.size() + " SubWords are in words_all_os.txt");
    }
}

