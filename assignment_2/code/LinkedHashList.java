/**
 * 
 */
public class LinkedHashList {

    /**
     * The first HashNode in the LinkedHashList
     */
    private HashNode head;

    /**
     * 
     */
    private HashNode tail;

    /**
     * 
     */
    private int length;

    /**
     * [LinkedHashList description]
     * @param  word [description]
     * @return      [description]
     */
    public LinkedHashList(String word) {
        head = new HashNode(word);
        tail = head;
        length = 1;
    }

    /**
     * [addHashNode description]
     * @param word [description]
     */
    public void addHashNode(String word) {
        HashNode newNode = new HashNode(word);
        tail.next = newNode;
        tail = newNode;
        length++;
    }

    /**
     * gets the HashNode at the start of the list
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