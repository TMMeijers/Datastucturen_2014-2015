/**
 * 
 */
public class QuadraticProbing extends Strategy {

	/**
	 * [QuadraticProbing description]
	 * @return [description]
	 */
	public QuadraticProbing(int length) {
		super(length);
	}

	/**
	 * [execute description]
	 */
	public int execute(int index, int j) {
		return (index + j * j) % table_length;
	}
}