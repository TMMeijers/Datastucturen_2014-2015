/**
 * Collision resolution strategy for open adressing.
 */
public class DoubleHashProbing extends Strategy {

    /**
     * The second hashing algorithm used for this collision resolution strategy.
     */
    private Hasher hasher;

    /**
     * Integer to keep track of iteration count
     */
    private int j;

    /**
     * Second hash index for keyword, so it only needs to be computed once.
     */
    private int secondHash;

    /**
     * Reset iteration count and hashed value for new keyword
     */
    public void init() {
        j = 0;
        secondHash = -1;
    }

    /**
     * Constructor for double hashing strategy with the default second hash algorithm (DJB2)
     */
    public DoubleHashProbing() {
        super(0);
        hasher = new Djb2Hasher(0);
    }

    /**
     * Constructor fo rdouble hashing strategy with a user specified second hash algorithm.
     * @param  hasher the second hashing algorithm to be used
     */
    public DoubleHashProbing(Hasher hasher) {
        super(0);
        this.hasher = hasher;
    }

    /**
     * Updates the length of the associated hash table.
     * @param length the new length of the associated hash table.
     */
    public void setLength(int length) {
        this.table_length = length;
        hasher.setLength(length);
    }

    /**
     * Executes the double hashing strategy for finding the next index
     * @param  k Object used for finding the next index, a String in this case.
     * @return   the new index in the hash table
     */
    public int execute(Object k) {
        String key = (String) k;
        j++;
        // If not hashed keyword with second hash function do so
        if (secondHash < 0) {
            secondHash = hasher.calcIndex(key);
        }
        return Math.abs(j * secondHash % table_length);
    }
}
