/**
 * Implementation of a linked list to be used for a hash table that uses collision chaining as it's collision 
 * resolution technique.
 * @author Markus Pfundstein and Thomas Meijers
 */
public class LinkedHashList {

    /**
     * The first HashNode in the LinkedHashList
     */
    private HashNode head;

    /**
     * The last HashNode in the LinkedHashList
     */
    private HashNode tail;

    /**
     * The current length of the LinkedHashList
     */
    private int length;

    /**
     * Constructor for a list initialized with it's first word
     * @param  word the String to be stored in the first HashNode
     */
    public LinkedHashList(String word) {
        head = new HashNode(word);
        tail = head;
        length = 1;
    }

    /**
     * Adds a new HashNode to the list, updates tail and length.
     * @param word the String to be stored in the HashNode
     */
    public void addHashNode(String word) {
        HashNode newNode = new HashNode(word);
        tail.next = newNode;
        tail = newNode;
        length++;
    }

    /**
     * Returns the HashNode at the start of the list
     * @return the HashNode at the start of the list
     */
    public HashNode getHead() {
        return head;
    }

    /**
     * Returns the size of the LinkedHashList
     * @return size of the LinkedHashList
     */
    public int size() {
        return length;
    }
}