import java.util.LinkedList;

public class ChainHashtable extends AbstractHashtable {

    /**
     * 
     */
    private LinkedHashList[] table;

    /**
     * [OpenHashTable description]
     * @param  hash_size the size that table should have.
     * @param  function  the hashing technique used.
     * @param  strategy  the strategy used to solve collisions.
     * @return           [description]
     */
    public ChainHashtable(int hash_size) {
        super(new DivisionHasher());
        function.setLength(hash_size);
        table_length = hash_size;
        table = new LinkedHashList[hash_size];
    }

    /**
     * [OpenHashTable description]
     * @param  hash_size the size that table should have.
     * @param  function  the hashing technique used.
     * @param  strategy  the strategy used to solve collisions.
     * @return           [description]
     */
    public ChainHashtable(int hash_size, Hasher function) {
        super(function);
        function.setLength(hash_size);
        table_length = hash_size;
        table = new LinkedHashList[hash_size];
    }

    /**
     * [put description]
     * @param word        [description]
     * @param placeholder [description]
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
     * [get description]
     * @param  word [description]
     * @return      [description]
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

    public String printStrategy() {
        return "CollisionChaining";
    }
} 
