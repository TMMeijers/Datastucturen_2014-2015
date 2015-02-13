public class BitShiftHasher implements Compressable {
    /**
     * 
     */
    private int table_length;

    public BitShiftHasher(int length) {
    	table_length = length;
    }

    /**
     * [calcIndexdescription]
     * @param  key [description]
     * @return     [description]
     * @see        <a href="http://stackoverflow.com/a/12996028/4286852">Source</a>
     */
    public int calcIndex(Object k) {
        int key = (int) k;
        key = ((key>>> 16) ^ key) * 0x45d9f3b;
        key = ((key>>> 16) ^ key) * 0x45d9f3b;
        return ((key>>> 16) ^ key);
    }
}