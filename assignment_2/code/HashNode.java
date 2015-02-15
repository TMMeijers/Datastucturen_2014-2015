/**
 * A node for the LinkedHashList class.
 * @see  LinkedHashList
 * @author Markus Pfundstein and Thomas Meijers
 */
public class HashNode {

    /**
     * The HashNode next in the list.
     */
    protected HashNode next;

    /**
     * The value contained within the HashNode
     */
    private String word; 

    /**
     * Constructor for the node and stores the value.
     * @param  word the String to be stored
     */
    public HashNode(String word) {
        this.word = word;
        next = null;
    }

    /**
     * Returns the next HashNode in the list.
     * @return                           a HashNode object after the current one
     * @throws IndexOutOfBoundsException when there is no next HashNode
     */
    public HashNode nextNode() throws IndexOutOfBoundsException {
        if (next == null) {
            throw new IndexOutOfBoundsException();
        }
        return next;
    }

    /**
     * Returns the word stored in this HashNode.
     * @return the word stored in this HashNode
     */
    public String getWord() {
        return word;
    }
}