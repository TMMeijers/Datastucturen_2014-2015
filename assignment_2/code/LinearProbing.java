/**
 * 
 */
public class LinearProbing extends Strategy {


    /**
     * stepSize for linear probing. Defaults to 1
     */
    private int stepSize;
    
    /**
     * [LinearProbing description]
     */
    public LinearProbing() {
        super(0);
        stepSize = 1;
    }

    /**
     * [LinearProbing description]
     */
    public LinearProbing(int length, int stepSize) {
        super(length);
        this.stepSize = stepSize;
    }

    /**
     * [execute description]
     * @param  index [description]
     * @return       [description]
     */
    public int execute(Object i) {
        int index = (Integer) i;
        return (index + stepSize) % table_length;
    }
}
