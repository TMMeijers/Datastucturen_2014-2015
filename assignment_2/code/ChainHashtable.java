import java.util.LinkedList;

/**
 * Implementation of a hash table using collision chaining as its collision resolution strategy.
 * @author Markus Pfundstein and Thomas Meijers
 */
public class ChainHashtable extends AbstractHashtable {

    /**
     * The array containing all the LinkedHashList objects which store the words.
     */
    private LinkedHashList[] table;

    /**
     * Constructor for the hash table which always uses the Division hashing algorithm.
     * @param  hash_size initial size of the hash table
     */
    public ChainHashtable(int hash_size) {
        super(new DivisionHasher());
        function.setLength(hash_size);
        table_length = hash_size;
        table = new LinkedHashList[hash_size];
    }

    /**
     * Constructor for the hash table where the hashing algorithm can be specified.
     * @param  hash_size the initial size of the hash table
     * @param  function  the hashing algorithm used.
     */
    public ChainHashtable(int hash_size, Hasher function) {
        super(function);
        function.setLength(hash_size);
        table_length = hash_size;
        table = new LinkedHashList[hash_size];
    }

    /**
     * Method for storing a word in the hash table.
     * @param  word  the String to be stored in the table
     */
    public void put(String word) {
        int index = function.calcIndex(word);
        // If empty fill index
        if (table[index] == null) {
            table_size++;
            table[index] = new LinkedHashList(word);
        // Else get next index based on chosen strategy
        } else {
            table_size++;
            table[index].addHashNode(word);
        }
    }

    /**
     * Method for retrieving a word from the hash table.
     * @param  word the String to be retrieved from the table
     * @return      the String retrieved, if the index was empty returns null
     */
    public String get(String word) {
        int index = function.calcIndex(word);
        // If element is null word doesn't exist
        if (table[index] == null) {
            return null;
        }
        // If word not found get next word in list
        HashNode node = table[index].getHead();
        while (!node.getWord().equals(word)) {
            try {
                node = node.nextNode();
            } catch (IndexOutOfBoundsException e) {
                // If index out of bounds word not found
                return null;
            }
        }
        return node.getWord();
    }

    /**
     * Prints the associated strategy's class name. Static due to ChainHashtable having no Strategy object.
     * @return the String containing the strategy's class name
     */
    public String printStrategy() {
        return "CollisionChaining";
    }
} 
