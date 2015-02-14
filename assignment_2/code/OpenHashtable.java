import java.lang.String;
import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;
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
    private Strategy strategy;

    private double resizeFactor;

    private double resizeThreshold;

    /**
     * [OpenHashTable description]
     * @param  hash_size the size that table should have.
     * @param  function  the hashing technique used.
     * @param  strategy  the strategy used to solve collisions.
     * @return           [description]
     */
    public OpenHashtable(int hash_size, Strategy strategy) {
        super(new DivisionHasher());
        this.strategy = strategy;
        this.strategy.setLength(hash_size);
        this.function.setLength(hash_size);
        this.table_length = hash_size;
        this.table = new String[hash_size];
        this.resizeFactor = 1.75;
        this.resizeThreshold = 0.75;
    }

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
        this.strategy.setLength(hash_size);
        this.function.setLength(hash_size);
        this.table_length = hash_size;
        this.table = new String[hash_size];
        this.resizeFactor = 1.75;
        this.resizeThreshold = 0.75;
    }

    /**
     * [OpenHashTable description]
     * @param  hash_size the size that table should have.
     * @param  function  the hashing technique used.
     * @param  strategy  the strategy used to solve collisions.
     * @return           [description]
     */
    public OpenHashtable(int hash_size, 
                         double resizeFactor,
                         double resizeThreshold,
                         Compressable function, 
                         Strategy strategy) {
        super(function);
        if (hash_size <= 0) {
            throw new IllegalArgumentException("hash_size must be positive integer");
        }
        if (resizeFactor <= 1) {
            throw new IllegalArgumentException("resizeFactor must be bigger than 1");
        }
        if (resizeThreshold <= 0 || resizeThreshold >= 1.0) {
            throw new IllegalArgumentException("resizeThreshold must be between 0 and 1");
        }

        this.strategy = strategy;
        this.strategy.setLength(hash_size);
        this.function.setLength(hash_size);
        this.table_length = hash_size;
        this.table = new String[hash_size];
        this.resizeFactor = resizeFactor;
        this.resizeThreshold = resizeThreshold;
    }

    /**
     * [put description]
     * @param word        [description]
     * @param placeholder [description]
     */
    public void put(String word) throws IllegalStateException {
        // If load factor > 0.75 increase size of table
        if (((double) table_size / table_length) > resizeThreshold) {
            resize();
        }
   
        int index = function.calcIndex(word);
        //System.out.println(index);
        // If empty fill index
        
        if (table[index] == null) {
            table_size++;
            table[index] = word;
        // Else get next index based on chosen strategy
        } else {
            strategy.init();
            index = strategy.execute(index);

            int ct = 0; // counter to keep track of how often we executed strategy
            while (table[index] != null) {
                index = strategy.execute(index);
                ct++;
                // strategy executed too often, this shouldn't happen. throw exception
                if (ct > table_length) {
                    // shouldnt happen
                    throw new IllegalStateException("The hash table grew full. This shouldn't happen. Hash function seems to end in an infinite loop");
                }
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
        //        System.out.println("resize");
        // Double size and update objects with new size
        
        String[] storedWords = new String[table_size];
        for (int i = 0, j = 0; i < table_length; i++) {
            String word = table[i];
            if (word != null) {
                storedWords[j] = word;
                j++;
            }
        }

        table_length *= resizeFactor;
        table = new String[table_length];
        strategy.setLength(table_length);
        function.setLength(table_length);

        // Now rehash all the words and fill
        for (String copy : storedWords) {
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
