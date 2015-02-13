import java.lang.String;
import java.util.ArrayList;

/**
 * 
 */
public class OpenHashtable extends AbstractHashtable {

    /**
     * 
     */
    private String table[];

    /**
     * 
     */
    private ArrayList<String> wordsCopy;

    /**
     * 
     */
    private Strategy strategy;

    /**
     * [OpenHashTable description]
     * @param  hash_size the size that table should have.
     * @param  function  the hashing technique used.
     * @param  strategy  the strategy used to solve collisions.
     * @return           [description]
     */
    public OpenHashtable(int hash_size, Compressable function, Strategy strategy) {
        super(function);
        this.strategy = strategy;
        table_length = hash_size;
        table = new String[hash_size];
        wordsCopy = new ArrayList<String>();
    }

    /**
     * [put description]
     * @param word        [description]
     * @param placeholder [description]
     */
    public void put(String word) {
        // If load factor > 0.75 increase size of table
        if (((double) table_size / table_length) > 0.75) {
            resize();
        }
        // Copy word for size 
        wordsCopy.add(word);

        int index = function.calcIndex(word);
        // If empty fill index
        if (table[index] == null) {
            table_size++;
            table[index] = word;
        // Else get next index based on chosen strategy
        } else {
            strategy.init();
            index = strategy.execute(index);
            // Keep looking for empty index
            while (table[index] != null) {
                index = strategy.execute(index);
            }
            table_size++;
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
        strategy.init();
        // While word doesn't match and we don't arrive at an empty element get new index
        while ((!word.equals(table[index])) && (table[index] != null)) {
            index = strategy.execute(index);
        }
        return table[index];
    }

    public String printStrategy() {
        return strategy.getClass().getSimpleName();
    }

    /**
     * [increaseLength description]
     */
    private void resize() {
        // Double size and update objects with new size
        table_length *= 2;
        table = new String[table_length];
        strategy.setLength(table_length);
        function.setLength(table_length);

        // Now rehash all the words and fill
        for (String copy : wordsCopy) {
            int index = function.calcIndex(copy);
            // If empty fill index
            if (table[index] == null) {
                table[index] = copy;
            // Else get next index based on chosen strategy
            } else {
                strategy.init();
                index = strategy.execute(index);
                // Keep looking for empty index
                while (table[index] != null) {
                    index = strategy.execute(index);
                }
                table[index] = copy;
            }
        }
    }
} 