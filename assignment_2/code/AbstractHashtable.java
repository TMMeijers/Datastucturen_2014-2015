/**
 * 
 */
public abstract class AbstractHashtable {

    /**
     * 
     */
    protected Compressable function;

    /**
     * 
     */
    protected Strategy strategy;

    /**
     * 
     */
    protected int size;

    /**
     * [AbstractHashTable description]
     * @param  function [description]
     * @param  strategy [description]
     * @return          [description]
     */
    public AbstractHashtable(Compressable function, Strategy strategy) {
        this.function = function;
        this.strategy = strategy;
    }

    /**
     * [size description]
     * @return [description]
     */
    public int size() {
        return size;
    }

    /**
     * [put description]
     * @param word        [description]
     * @param placeholder [description]
     */
    public abstract void put(String word);

    /**
     * [get description]
     * @param word [description]
     */
    public abstract String get(String word);
}