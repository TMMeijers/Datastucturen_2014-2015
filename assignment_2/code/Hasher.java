/**
 * Interface for hashing functions.
 * @author Markus Pfundstein and Thomas Meijers
 */
public interface Hasher {

    /**
     * Function that hashes the key to an index.
     * @param  key The String containing the key to be hashed
     * @return     The index obtained through hashing
     */
    public int calcIndex(String key);

    /**
     * Updates the length of the hash table for the hashing algorithm.
     * @param length The new length of the table
     */
    public void setLength(int length);
}
