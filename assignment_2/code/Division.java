/**
 * 
 */
public class Division implements Compressable {
    /**
     * 
     */
    int table_length;

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
    Division(int length) {
        table_length = length;
    }

    /**
     * [calcIndex description]
     * @param  key [description]
     * @return     [description]
     */
    int calcIndex(String key) {
        int index;

        index = Math.abs(hashCode(key)) % table_length;
        return index;
    }

    /**
     * [hashCode description]
     * @param  key [description]
     * @return     [description]
     */
    int hashCode(String key) {
        int h = initial;
        char[] val = key.toCharArray();
        int len = key.length();
   
        for (int i = 0; i < len; i++) {
            h = multiplier * h + val[i];
        }
        return h;
    }

}