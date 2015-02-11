/**
 * 
 */
public class LinearProbing extends Strategy {
	
	/**
	 * [LinearProbing description]
	 * @return [description]
	 */
	public LinearProbing(int length) {
		super(length);
	}

	/**
	 * [execute description]
	 * @param  index [description]
	 * @return       [description]
	 */
	public int execute(int index, int j) {
		return (index + 1) % table_length;
	}
}