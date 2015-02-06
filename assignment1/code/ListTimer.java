import java.util.List;

/**
 * Class that times the performance of a data structure that implements the List interface.
 *
 * @author Markus Pfundstein and Thomas Meijers
 */
public class ListTimer extends CollectionTimer {

	/**
	 * The list that will be benchmarked.
	 */
	private List<Integer> list;

	/**
	 * Constructor that creates a ListTimer instance for the given list.
	 *
	 * @param list 	instance of the data structure that is to be benchmarked.
	 */ 
	public ListTimer(List<Integer> list) {
		super();
		this.list = list;
	}

	/**
	 * Constructor that creates a ListTimer instance for the given list.
	 *
	 * @param list 			instance of the data structure that is to be benchmarked.
	 * @param elemGenSeed 	seed for the generator of random elements
	 */ 
	public ListTimer(List<Integer> list, long elemGenSeed) {
		super(elemGenSeed);
		this.list = list;
	}

	/**
	 * Constructor that creates a ListTimer instance for the given list.
	 *
	 * @param list 			instance of the data structure that is to be benchmarked.
	 * @param elemGenSeed	seed for the generator of random elements
	 */ 
	public ListTimer(List<Integer> list, Long elemGenSeed) {
		super(elemGenSeed);
		this.list = list;
	}

	/**
	 * Adds an Integer object to the list.
	 *
	 * @param elem 	the object that is to be inserted
	 */ 
	public void addElement(Integer elem) {
		list.add(elem);
	}

	/**
	 * Removes an object from the list.
	 *
	 * @throws IndexOutOfBoundsException 		if the list is empty
	 * @throws UnsupportedOperationException 	if the list does not support removal of elements
	 */ 
	public void removeElement() throws IndexOutOfBoundsException, UnsupportedOperationException {
		list.remove(list.size() - 1);
	}

	/**
	 * Returns the size of the list.
	 *
	 * @return the size of the list
	 */
	public int getSize() {
		return list.size();
	}

	/**
	 * Tells whether the list is empty.
	 *
	 * @return true if the list is empty, false otherwise
	 */
	public boolean isEmpty() {
		if (list.size() > 0) {
			return true;
		}
		return false;
	}
}