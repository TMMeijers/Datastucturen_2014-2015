import java.util.*;
import java.io.*;
class OwnSpellChecker {
    public static void main(String[] args) {
        int hash_size;
        String wordfile, textfile;
        Compressable function;
        Strategy strategy;

        /* Shared token to store for every word in the hash table. */

        if (args.length < 3) {
            System.err.println("Usage: java SpellChecker <wordfile> <textfile> <table_size>");
            System.exit(1); // on error
        }
        wordfile = args[0];
        textfile = args[1];
        hash_size = Integer.parseInt(args[2]);

        if (hash_size <= 0) {
            System.err.printf("<table_size> must be bigger than 0.\n");
            System.exit(1);
        }
        File wordFileHandle = new File(wordfile);
        File textFileHandle = new File(textfile);
        if (!wordFileHandle.exists() || wordFileHandle.isDirectory()) {
            System.err.println("<wordfile> doesn't exist or is a directory\n");
            System.exit(1);
        }
        if (!textFileHandle.exists() || textFileHandle.isDirectory()) {
            System.err.println("<textfile> doesn't exist or is a directory\n");
            System.exit(1);
        }
        
        System.out.printf("Selected table size: %d\n", hash_size);                
        // It is recommended to use a hash size which is a power of 2, so lets Warn if the user
        // doesn't provide it
        if (!((hash_size & -hash_size) == hash_size)) {
            System.out.println("[WARNING] hash_size should be a power of 2");
        }  

        try {
            // add all tables that we want to test
            ArrayList<AbstractHashtable> tables = new ArrayList<AbstractHashtable>();
            tables.add(new OpenHashtable(hash_size, new LinearProbing()));

            tables.add(new OpenHashtable(hash_size, new QuadraticProbing()));

            tables.add(new OpenHashtable(hash_size, new DoubleHashProbing()));

            tables.add(new ChainHashtable(hash_size));

            runExperiments(tables, wordfile, textfile);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /* Runs test on all hash tables in tables */
    static void runExperiments(ArrayList<AbstractHashtable> tables, String wordfile, String textfile) {
        for (AbstractHashtable table : tables) {
            try {
                long start = 0;
                long end = 0;
                int count = 0;
                int typo = 0;

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
                    System.exit(1);
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
                    System.exit(1);
                }

                System.out.printf("Hash table contains %d words\n", table.size());
                System.out.printf("Hash table load factor %f\n",
                                  (double)table.size()/table.hashSize());

                System.out.printf("Text contains %d words\n", count);
                System.out.printf("typo's %d\n", typo);

                System.out.println("word search in " + (end - start) + " ms");
            
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("error for table: " + table.printStrategy());
            }
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
