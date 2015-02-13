import java.lang.String;

/**
 * 
 */
public class OpenHashtable extends AbstractHashtable {

    /**
     * 
     */
    String table[];

    /**
     * [OpenHashTable description]
     * @param  hash_size the size that table should have.
     * @param  function  the hashing technique used.
     * @param  strategy  the strategy used to solve collisions.
     * @return           [description]
     */
    public OpenHashtable(int hash_size, Compressable function, Strategy strategy) {
        super(function, strategy);
        table = new String[hash_size];
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
            size++;
            table[index] = word;
        // Else get next index based on chosen strategy
        } else {
            int j = 0;
            index = strategy.execute(index, j);
            // Keep looking for empty index
            while (table[index] != null) {
                index = strategy.execute(index, j);
                j++;
            }
            size++; // temp
            table[index] = word;
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
        int j = 0;
        // While word doesn't match and we don't arrive at an empty element get new index
        while ((!word.equals(table[index])) && (table[index] != null)) {
            index = strategy.execute(index, j);
            j++;
        }
        return table[index];
    }
} 