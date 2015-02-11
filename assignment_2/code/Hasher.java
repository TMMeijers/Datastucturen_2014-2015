public class Hasher implements Compressable {
    /**
     * 
     */
    private int table_length;

    public Hasher(int length) {
    	table_length = length;
    }

    // /**
    //  * [calcIndex description]
    //  * @param  key [description]
    //  * @return     [description]
    //  */
    // public int calcIndex(String key) {
    // 	return 0;
    // }

    // CRAP BELOW IS COPY FROM DIVISION
    int initial = 11;
    int multiplier = 31;
    public int calcIndex(String key) {
    	// NEED TO CHANGE, COPY FROM DIVISION
        int index;

        index = Math.abs(hashCode(key)) % table_length;
    }
    private int hashCode(String key) {
    	// NEED TO CHANGE, COPY FROM DIVISION
        int h = initial;
        char[] val = key.toCharArray();
        int len = key.length();
   
        for (int i = 0; i < len; i++) {
            h = multiplier * h + val[i];
        }
        return h;
    }
}