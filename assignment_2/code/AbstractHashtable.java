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
    protected int table_size;

    /**
     * 
     */
    protected int table_length;

    /**
     * [AbstractHashTable description]
     * @param  function [description]
     * @param  strategy [description]
     * @return          [description]
     */
    protected AbstractHashtable(Compressable function) {
        this.function = function;
    }

    /**
     * [size description]
     * @return [description]
     */
    public int size() {
        return table_size;
    }

    public int length() {
        return table_length;
    }

    public int hashSize() {
        return table_length;
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

    public abstract String printStrategy();
}
