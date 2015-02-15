/** 
 * Interface Strategy can be implemented for hashing strategies, has the
 * method execute which executes the given strategy.
 */
public abstract class Strategy {

    protected int table_length;

    public Strategy(int length) {
        table_length = length;
    }

    /**
     * Placeholder function, only really used by QuadraticProbing
     */
    public void init() {
    }

    public void setLength(int length) {
        table_length = length;
    }

    /** 
     * Executes the strategy for the given class implementing this method.
     */
    public abstract int execute(Object index);
}
