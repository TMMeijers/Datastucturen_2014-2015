import java.lang.IllegalArgumentException;

/**
 * Collision resolution strategy for open adressing.
 */
public class QuadraticProbing extends Strategy {
    
    /**
     * Iteration count of finding a new index.
     */
    private int j;

    /**
     * Constant for multiplying step size (c1 * j)
     */
    private int c1;

    /**
     * Constant for multiplying step size (c2 * j^2). 
     * Note that if this is zero quadratic probing degrades to linear probing.
     */
    private int c2;

    /**
     * Resets the iteration count.
     */
    public void init() {
        j = 0;
    }

    /**
     * Constructor with default values for constants (H(k, i) = index + c2 * j^2).
     */
    public QuadraticProbing() {
        super(0);
        this.c1 = 0;
        this.c2 = 1;
    }


    /**
     * Constructor with user specified values for the constants c1 and c2.
     * @param  c1                       Constant for multiplying step size
     * @param  c2                       Constant for multiplying squared step size
     * @throws IllegalArgumentException when specified step sizes are invalid.
     */
    public QuadraticProbing(int c1, int c2) throws IllegalArgumentException {
        super(0);
        if (!(c1 >= 0 && c2 > 0)) {
            throw new IllegalArgumentException("invalid stepsizes given");
        }
        this.c1 = c1;
        this.c2 = c2;
    }

    /**
     * Executes the quadratic probing strategy for finding the next index
     * @param  i Object used for finding the next index, Integer in this case.
     * @return   the new index in the hash table
     */
    public int execute(Object i) {
        int index = (Integer) i;
        j++;
        int idx = (index + c1 * j + c2 * (j * j)) % this.table_length;
        //System.out.println(idx);
        //System.out.println(this.table_length);
        return idx;
    }
}
