import java.util.*;
import java.io.*;
class OwnSpellChecker {
    public static void main(String[] args) {
        int hash_size;
        int count = 0, typo = 0;
        long start = 0, end = 0;
        String wordfile, textfile;
        Compressable function;
        Strategy strategy;

        /* Shared token to store for every word in the hash table. */

        if (!(args.length == 3) ) {
            System.out.println("Usage: java SpellChecker <wordfile> <text> <size>");
            System.exit(0);
        }
        wordfile = args[0];
        textfile = args[1];
        hash_size = Integer.parseInt(args[2]);
        System.out.printf("Selected table size: %d\n", hash_size);
       
        // Make list to test all implementations
        function = new DivisionHasher(hash_size);
        ArrayList<AbstractHashtable> tables = new ArrayList<AbstractHashtable>(4);
        // Hash table with Linear probing
        strategy = new LinearProbing(hash_size);
        tables.add(new OpenHashtable(hash_size, function, strategy));
        // Table with quadratic probing
        strategy = new QuadraticProbing(hash_size);
        tables.add(new OpenHashtable(hash_size, function, strategy));
        // Table with double hashing
        // strategy = new DoubleHashProbing(hash_size);
        // tables.add(new OpenHashtable(hash_size, function, strategy));
        // Table with collision chaining
        tables.add(new ChainHashtable(hash_size, function));

        for (AbstractHashtable table : tables) {
            System.out.println("\nTesting Hashtable with \"" + table.printStrategy() + "\" collision prevention");
            /* Read wordfile, and insert every word into the hash table. */
            try {
                BufferedReader in = new BufferedReader(new FileReader(wordfile));
                start = System.currentTimeMillis();
                String str, copy;
                while ((str = in.readLine()) != null) {
                    copy = str.toLowerCase();
                    table.put(copy);
                }
                end = System.currentTimeMillis();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Hash table built in " + (end - start) + " ms");

            // Read text file, and lookup every word in the hash table.
            try {
                BufferedReader src = new BufferedReader(new FileReader(textfile));
                start = System.currentTimeMillis();
                String str = src.readLine();
                while (str != null) {
                    String copy = str.toLowerCase();

                    StringTokenizer st = new StringTokenizer(copy, " ,.:;\"-_(){}[]?!*^&'\n\t");
                    while(st.hasMoreTokens()) {
                        String word = st.nextToken();
                        if (!contains_numbers(word) && table.get(word) == null) {
                            typo++;
                        }
                        count++;
                    }
                    str = src.readLine();
                }
                end = System.currentTimeMillis();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.printf("Hash table contains %d words\n", table.size());
            System.out.printf("Hash table load factor %f\n",
                   (double)table.size()/hash_size);

            System.out.printf("Text contains %d words\n", count);
            System.out.printf("typo's %d\n", typo);

            System.out.println("word search in " + (end - start) + " ms");
        }
    }

    /* Checks is word contains digits. So it can be ignored for spell
     * checking. */
    static boolean contains_numbers(String str) {
        for (int i = 0 ; i < str.length() ; i++) 
            if (str.charAt(i) >= '0' && str.charAt(i) <= '9') 
                return true;

        return false;
    }
}
