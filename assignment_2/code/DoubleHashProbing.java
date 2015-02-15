/**
 * 
 */
public class DoubleHashProbing extends Strategy {

    /**
     * 
     */
    private Hasher hasher;

    /**
     * Integer to keep track of iteration count
     */
    private int j;

    /**
     * Second hash index for keyword
     */
    private int secondHash;

    /**
     * Reset iteration count and hash key for new keyword
     */
    public void init() {
        j = 0;
        secondHash = -1;
    }

    /**
     * [DoubleHashProbing description]
     * @param  length [description]
     * @return        [description]
     */
    public DoubleHashProbing() {
        super(0);
        hasher = new Djb2Hasher(0);
    }

    public void setLength(int length) {
        this.table_length = length;
        hasher.setLength(length);
    }

    /**
     * [execute description]
     * @param  index [description]
     * @param  j     [description]
     * @return       [description]
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
