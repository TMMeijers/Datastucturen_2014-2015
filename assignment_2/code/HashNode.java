/**
 * 
 */
public class HashNode {

    /**
     * 
     */
    protected HashNode next;

    /**
     * 
     */
    private String word; 

    /**
     * [HashNode description]
     * @param  word [description]
     * @return      [description]
     */
    public HashNode(String word) {
        this.word = word;
        next = null;
    }

    /**
     * [nextNode description]
     * @return [description]
     * @throws IndexOutOfBoundsException [description]
     */
    public HashNode nextNode() throws IndexOutOfBoundsException {
        if (next == null) {
            throw new IndexOutOfBoundsException();
        }
    	return next;
    }

    /**
     * [getWord description]
     * @return [description]
     */
    public String getWord() {
        return word;
    }
}