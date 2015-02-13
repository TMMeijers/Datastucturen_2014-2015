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
        hasher = new BitShiftHasher(length);
    }

    /**
     * [execute description]
     * @param  index [description]
     * @param  j     [description]
     * @return       [description]
     */
    public int execute(int index) {
        return hasher.calcIndex(index);
    }
}