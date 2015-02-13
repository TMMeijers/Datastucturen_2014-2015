public class Hasher implements Compressable {
    /**
     * 
     */
    private int table_length;

    public Hasher(int length) {
    	table_length = length;
    }

    /**
     * [calcIndex description]
     * @param  key [description]
     * @return     [description]
     */
    public int calcIndex(String key) {
    	return 0;
    }
}