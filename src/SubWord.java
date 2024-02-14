/**
 * Creates the direct comparable for the SubWord
 */
public class SubWord implements Comparable<SubWord> {
    private String rootWord, sub1, sub2;

    /**
     * Constructor for class SubWord
     * @param root the root word
     * @param s1 first half of word
     * @param s2 second half of word
     */
    public SubWord(String root, String s1, String s2) {
        this.rootWord = root;
        this.sub1 = s1;
        this.sub2 = s2;
    }

    /**
     * Returns the word as a string
     * @return the word but split up
     */
    public String toString() {
        return rootWord + " = " + sub1 + " + " + sub2;
    }

    /**
     * Compares one subword object to another based on the value of rootword
     * @param other the object to be compared.
     * @return compares the rootword to the subword
     */
    public int compareTo(SubWord other) {
        return this.rootWord.compareTo(other.rootWord);
    }
}
