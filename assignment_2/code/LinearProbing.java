/**
 * Collision resolution strategy for open adressing, utilizes linear probing.
 * @author Markus Pfundstein and Thomas Meijers
 */
public class LinearProbing extends Strategy {


    /**
     * Step size for linear probing. Defaults to 1
     */
    private int stepSize;
    
    /**
     * Constructor with default stepSize.
     */
    public LinearProbing() {
        super(0);
        stepSize = 1;
    }

    /**
     * Constructor with user specified stepSize.
     * @param  stepSize the step size for generating the next index
     */
    public LinearProbing(int stepSize) {
        super(0);
        this.stepSize = stepSize;
    }

    /**
     * Executes the linear probing strategy for finding the next index
     * @param  i Object used for finding the next index, Integer in this case.
     * @return   the new index in the hash table
     */
    public int execute(Object i) {
        int index = (Integer) i;
        return (index + stepSize) % table_length;
    }
}
