/**
 * 
 */
public class DoubleHashProbing extends Strategy {

    /**
     * 
     */
    Compressable hasher;

    int last_index;

    /**
     * [DoubleHashProbing description]
     * @param  length [description]
     * @return        [description]
     */
    public DoubleHashProbing() {
        super(0);
        hasher = new BitShiftHasher(0);
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
    public int execute(int index) {
        return hasher.calcIndex(index);
    }
}
