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
    public LinearProbing(int length) {
        super(length);
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
    public int execute(int index) {
        return (index + 1) % table_length;
    }
}
