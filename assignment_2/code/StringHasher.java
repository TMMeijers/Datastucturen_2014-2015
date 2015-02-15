/**
 * 
 */
public class StringHasher implements Compressable {
    /**
     * 
     */
    private int table_length;

    public StringHasher(int length) {
    	table_length = length;
    }

    /**
     * [calcIndexdescription]
     * @param  key [description]
     * @return     [description]
     */
    public int calcIndex(String key) {
        return hashCode(key);
    }

    public void setLength(int length) {
        table_length = length;
    }

    /**
     * [hashCode description]
     * @param  key [description]
     * @return     [description]
     * @see        <a href="http://www.cse.yorku.ca/~oz/hash.html">Source</a>
     */
    private int hashCode(String key) {
        int h = 5381;
        
        for (int i = 0; i < key.length(); i++) {
            h = ((h << 5) + h) + key.charAt(i);
        }
        return h;
    }
}
