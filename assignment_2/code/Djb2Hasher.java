/**
 * Hashing function utilizing the DJB2 algorithm.
 * @author Markus Pfundstein and Thomas Meijers
 */
public class Djb2Hasher implements Hasher {

    /**
     * The length of the hash table associated with the hashing function.
     */
    private int table_length;

    /**
     * Constructor for the hashing function (currently updated though setLength)
     * @param  length the initial length of the hash table
     */
    public Djb2Hasher(int length) {
    	table_length = length;
    }

    /**
     * Calculates the new index through hashing the key with the DJB2 algorithm.
     * @param  key the String to be hashed
     * @return     the index obtained through hashing
     */
    public int calcIndex(String key) {
        return hashCode(key);
    }

    /**
     * Updates the length of the hash table (not used for this algorithm)
     * @param length the new length of the hash table
     */
    public void setLength(int length) {
        table_length = length;
    }

    /**
     * Converts a String to an index using the DJB2 algorithm.
     * @param  key the String to be hashed
     * @return     the index obtained through hashing
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
