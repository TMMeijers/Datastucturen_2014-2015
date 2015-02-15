/**
 * Abstract class for hash table implementations utilizing different strategies.
 * @author Thomas Meijers and Markus Pfundstein
 */
public abstract class AbstractHashtable {

    /**
     * The hashing function used.
     */
    protected Hasher function;

    /**
     * The current size of the table (filled elements).
     */
    protected int table_size;

    /**
     * The current total length of the table.
     */
    protected int table_length;

    /**
     * Constructor for creating an AbstractHashtable.
     * @param  function the hashing function used for this AbstractHashtable
     */
    protected AbstractHashtable(Hasher function) {
        this.function = function;
    }

    /**
     * Returns the number of filled elements in the table.
     * @return The number of filled elements in the table
     */
    public int size() {
        return table_size;
    }

    /**
     * Returns the current length of the table.
     * @return The total length of the table
     */
    public int length() {
        return table_length;
    }

    /**
     * Abstract method for putting a word in the table.
     * @param word  The String to be stored in the table
     */
    public abstract void put(String word);

    /**
     * Abstract method for retrieving a word out of the table.
     * @param word The String to be retrieved from the table
     * @return     the element stored at the index obtained through hashing word
     */
    public abstract String get(String word);

    /**
     * Abstract method for printing the corresponding collision resolution strategy.
     * @return The String containing the strategy's simple object name
     */
    public abstract String printStrategy();
}
