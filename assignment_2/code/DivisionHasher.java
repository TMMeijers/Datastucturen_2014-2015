/**
 * 
 */
public class DivisionHasher implements Hasher {
    /**
     * 
     */
    private int table_length;

    /**
     * 
     */
    int initial = 11;

    /**
     * m
     */
    int multiplier = 31;
    
    /**
     * Constructor
     * @param  length length of the table
     */
    public DivisionHasher() {
        table_length = 0;
    }

    /**
     * [calcIndex description]
     * @param  key [description]
     * @return     [description]
     */
    public int calcIndex(String key) {
        return Math.abs(hashCode(key)) % table_length;
    }

    public void setLength(int length) {
        table_length = length;
    }

    /**
     * [hashCode description]
     * @param  key [description]
     * @return     [description]
     */
    private int hashCode(String key) {
        int h = initial;
        char[] val = key.toCharArray();
        int len = key.length();
   
        for (int i = 0; i < len; i++) {
            h = multiplier * h + val[i];
        }
        return h;
    }

}
