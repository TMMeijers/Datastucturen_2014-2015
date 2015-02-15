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
    private String[] table;

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
        if (strategy.getClass().getSimpleName().equals("DoubleHashProbing") && hash_size < 64) {
            System.out.println("[WARNING] Double hashing function can't handle too small sizes, set to minimum: 64.");
            hash_size = 64;
        }
        this.strategy.setLength(hash_size);
        this.function.setLength(hash_size);
        this.table_length = hash_size;
        this.table = new String[hash_size];
        this.resizeFactor = 2;
        this.resizeThreshold = 0.75;
        // Quadratic probing and double hashing can fail finding empty index with load factor >= 0.5
        if (strategy.getClass().getSimpleName().matches("QuadraticProbing|DoubleHashProbing")) {
            this.resizeThreshold = 0.5;
        }
    }

    /**
     * [OpenHashTable description]
     * @param  hash_size the size that table should have.
     * @param  function  the hashing technique used.
     * @param  strategy  the strategy used to solve collisions.
     * @return           [description]
     */
    public OpenHashtable(int hash_size, Hasher function, Strategy strategy) {
        super(function);
        this.strategy = strategy;
        if (strategy.getClass().getSimpleName().equals("DoubleHashProbing") && hash_size < 64) {
            System.out.println("[WARNING] Double hashing function can't handle sizes below min, set to minimum: 64.");
            hash_size = 64;
        }
        this.strategy.setLength(hash_size);
        this.function.setLength(hash_size);
        this.table_length = hash_size;
        this.table = new String[hash_size];
        this.resizeFactor = 2;
        this.resizeThreshold = 0.75;
        // Quadratic probing and double hashing can fail finding empty index with load factor >= 0.5
        if (strategy.getClass().getSimpleName().matches("QuadraticProbing|DoubleHashProbing")) {
            this.resizeThreshold = 0.5;
        }
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
                         Hasher function, 
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
        // Quadratic probing and double hashing can fail finding empty index with load factor >= 0.5
        if (strategy.getClass().getSimpleName().matches("QuadraticProbing|DoubleHashProbing") && resizeThreshold > 0.5) {
            System.out.println("[WARNING] Double hashing or quadratic probing resize factor set to max: 0.5.");
            this.resizeThreshold = 0.5;
        }
    }

    /**
     * [put description]
     * @param word        [description]
     * @param placeholder [description]
     */
    public void put(String word) throws IllegalStateException {
        // If load factor > 0.75 increase size of table
        if (((double) table_size / table_length) >= resizeThreshold) {
            resize();
        }
        int index = function.calcIndex(word);
        // If empty fill index
        if (table[index] == null) {
            table_size++;
            table[index] = word;
        // Else get new index based on chosen strategy
        } else {
            int newIndex = index;
            strategy.init();
            // Double Hash probing requires key to be passed
            if (!printStrategy().equals("DoubleHashProbing")) {
                newIndex = strategy.execute(newIndex);
            } else {
                newIndex = (index + strategy.execute(word)) % table_length;
            }

            int ct = 0; // counter to keep track of how often we executed strategy
            while (table[newIndex] != null) {
                if (!printStrategy().equals("DoubleHashProbing")) {
                    newIndex = strategy.execute(newIndex);
                } else {
                    newIndex = (index + strategy.execute(word)) % table_length;
                }
                ct++;
                // strategy executed too often, this shouldn't happen. throw exception
                if (ct > table_length) {
                    // shouldnt happen
                    throw new IllegalStateException("The hash table grew full. This shouldn't happen. Hash function seems to end in an infinite loop");
                }
            }
            table_size++;
            table[newIndex] = word;
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
        int newIndex = index;
        // While word doesn't match and we don't arrive at an empty element get new index
        while ((!word.equals(table[newIndex])) && (table[newIndex] != null)) {
            if (!printStrategy().equals("DoubleHashProbing")) {
                newIndex = strategy.execute(newIndex);
            } else {
                newIndex = (index + strategy.execute(word)) % table_length;
            }
        }
        return table[newIndex];
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
                int newIndex = index;
                if (!printStrategy().equals("DoubleHashProbing")) {
                    newIndex = strategy.execute(newIndex);
                } else {
                    newIndex = (index + strategy.execute(copy)) % table_length;
                }
                // Keep looking for empty index
                while (table[newIndex] != null) {
                    if (!printStrategy().equals("DoubleHashProbing")) {
                        newIndex = strategy.execute(newIndex);
                    } else {
                        newIndex = (index + strategy.execute(copy)) % table_length;
                    }
                }
                table[newIndex] = copy;
            }
        }
    }
} 
