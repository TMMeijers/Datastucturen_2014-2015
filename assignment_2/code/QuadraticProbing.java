import java.lang.IllegalArgumentException;

/**
 * 
 */
public class QuadraticProbing extends Strategy {

    /**
     * Stepsizes for quadratic probing. 
     * H(k, i) = (h(k) + c1*i + c2*i^2) mod table_size
     * Note that if c2 = 0 than we get LinearProbing
     */
    private int c1;
    private int c2;

    /**
     * [QuadraticProbing description]
     * @return [description]
     */
    public QuadraticProbing(int length) {
        super(length);
        this.c1 = 0;
        this.c2 = 1;
    }

    /**
     * [QuadraticProbing description]
     * @return [description]
     */
    public QuadraticProbing(int length, int c1, int c2) throws IllegalArgumentException {
        super(length);
        if (!(c1 >= 0 && c2 > 0)) {
            throw new IllegalArgumentException("invalid stepsizes given");
        }
        this.c1 = c1;
        this.c2 = c2;
    }

    /**
     * [execute description]
     */
    public int execute(int index, int j) {
        return (index + c1 * j + c2 * (j * j)) % table_length;
    }
}