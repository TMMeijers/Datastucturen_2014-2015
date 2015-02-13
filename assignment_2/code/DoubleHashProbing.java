/**
 * 
 */
public class DoubleHashProbing extends Strategy {

    /**
     * 
     */
    Compressable hasher;

    /**
     * [DoubleHashProbing description]
     * @param  length [description]
     * @return        [description]
     */
    public DoubleHashProbing(int length) {
        super(length);
        hasher = new Hasher(length);
    }

    /**
     * [execute description]
     * @param  index [description]
     * @param  j     [description]
     * @return       [description]
     */
    public int execute(int index, int j) {
        // return hasher.calcIndex(index);
        return 0;
    }
}