/**
 * Hashing function utilizing character values.
 * @author Markus Pfundstein and Thomas Meijers
 */
public class DivisionHasher implements Hasher {

    /**
     * The length of the associated hash table.
     */
    private int table_length;

    /**
     * The initial value for the hashing algorithm.
     */
    private int initial = 11;

    /**
     * The multiplier for the hashing algorithm.
     */
    private int multiplier = 31;
    
    /**
     * Constructor for the hashing function (table_length currently updated though setLength)
     */
    public DivisionHasher() {
        table_length = 0;
    }

    /**
     * Calculates the new index through hashing the key with an algorithm utilizing char values
     * @param  key the String to be hashed
     * @return     the index obtained through hashing
     */
    public int calcIndex(String key) {
        return Math.abs(hashCode(key)) % table_length;
    }

    /**
     * Updates the length of the associated hash table.
     * @param length the new length of the associated hash table.
     */
    public void setLength(int length) {
        table_length = length;
    }

    /**
     * Calculates the new index through hashing the key with an algorithm utilizing char values
     * @param  key the String to be hashed
     * @return     the index obtained through hashing
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
