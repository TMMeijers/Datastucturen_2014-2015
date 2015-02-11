import java.util.LinkedList;

public class ChainHashtable extends AbstractHashtable {

	/**
	 * 
	 */
	private LinkedHashList table[];

	/**
	 * [OpenHashTable description]
	 * @param  hash_size the size that table should have.
	 * @param  function  the hashing technique used.
	 * @param  strategy  the strategy used to solve collisions.
	 * @return           [description]
	 */
	public ChainHashtable(int hash_size, Compressable function, Strategy strategy) {
		super(function, strategy);
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
			size++;
			table[index] = new LinkedHashList(word);
		// Else get next index based on chosen strategy
		} 
	}

	/**
	 * [get description]
	 * @param  word [description]
	 * @return      [description]
	 */
	public String get(String word) {
		return "test";
	}
} 