/** 
 * Interface Strategy can be implemented for hashing strategies, has the
 * method execute which executes the given strategy.
 * @author Markus Pfundstein and Thomas Meijers
 */
public abstract class Strategy {

    /**
     * The length of the associated hash table.
     */
    protected int table_length;

    /**
     * Constructor for abstract class Strategy, set's the hash table's initial length.
     * @param  length the initial length of the associated hash table
     */
    public Strategy(int length) {
        table_length = length;
    }

    /**
     * Placeholder function, functionally used by QuadraticProbing and DoubleHashProbing.
     */
    public void init() {
    }

    /**
     * [setLength description]
     * @param length [description]
     */
    public void setLength(int length) {
        table_length = length;
    }

    /** 
     * Executes the strategy for the given class implementing this method.
     * @param  index  the index to be used for generating a new index
     * @return        the new index
     */
    public abstract int execute(Object index);
}
