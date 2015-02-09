import java.util.*;

/**
 * Abstract class that is to be extended by classes that wish to time the performance of some data structure. Contrary 
 * to what the name implies, it is not enforced that the data structures that are timed implement the Collection 
 * interface. The data structure will need to support insertion of Integer objects.
 *
 * @author Markus Pfundstein and Thomas Meijers
 */
public abstract class CollectionTimer {
	
	/**  
	 * Default sequence of inserts and deletions that is used to time the data structure: 10000 insertions followed by 
	 * their removal.
	 */
	static final int[] DEFAULT_MUTATIONS = new int[] {10000, -10000};

	/**  
	 * Generator of objects that are inserted into the data structure.
	 */
	private java.util.Random elemGen;

	/**  
	 * Default constructor. Creates a CollectionTimer instance in which the random object generator is instantiated 
	 * with seed 0.
	 */
	public CollectionTimer() {
		elemGen = new Random(0);
	}	

	/**  
	 * Constructor that creates a CollectionTimer instance with a random object generator with the specified seed.
	 *
	 * @param elemGenSeed 	seed for the random object generator
	 */
	public CollectionTimer(long elemGenSeed) {
		elemGen = new Random(elemGenSeed);
	}

	/**
	 * Inserts a specified number of Integer objects into the data structure. This method assumes that the underlying 
	 * data structure supports the insertion of the required number of objects. If such is not the case, then 
	 * (depending on the implementation), an exception may be thrown.
	 *
	 * @param 	amount 		number of objects to insert
	 */
	public void insert(int amount) {
		for (int i = 0; i < amount; i++) {
			addElement(elemGen.nextInt());
		}
	}

	/**
	 * Removes a specified number of objects from the data structure.
	 *
	 * @param amount 	number of objects to remove
	 * @return 			true if sufficient elements were present, false otherwise.
	 */
	public boolean extract(int amount) {
		if (amount <= getSize()) {
			for (int i = 0; i < amount; i++) {
				try {
					removeElement();
				} catch (RuntimeException e) {
					System.out.println(e);
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Times a sequence of operations on the underlying data structure. This method performs the mutations defined by 
	 * DEFAULT_MUTATIONS. Timing takes place by subtracting the number of milliseconds since the UNIX epoch before and 
	 * after the required operations have taken place. Note that the actual accuracy is platform dependant and may be 
	 * influenced by other processes running on the host machine.
	 *
	 * @return 	elapsed time in milliseconds
	 * @see 	DEFAULT_MUTATIONS
	 */
	public long time() {
		return time(DEFAULT_MUTATIONS);
	}

	/**
	 * Times a specified sequence of operations on the underlying data structure. This method performs the given 
	 * operations in successive order, as they occur in the given array. Insertions are specified by a positive 
	 * number, removal of objects are denoted by a negative value. If mutations equals null, then the default 
	 * mutations as defined by DEFAULT_MUTATIONS are performed. Timing takes place by subtracting the number of 
	 * milliseconds since the UNIX epoch before and after the required operations have taken place. Note that the 
	 * actual accuracy is platform dependant and may be influenced by other processes running on the host machine.
	 *
	 * @param mutations 	integer array defining which successive permutations should be performed
	 * @return 				elapsed time in milliseconds
	 * @see 				DEFAULT_MUTATIONS
	 */
	public long time(int[] mutations) {
		long start = System.currentTimeMillis();
		for (int mutation : mutations) {
			if (mutation < 0) {
				extract(-mutation);
			} else {
				insert(mutation);
			}
		}
		long stop = System.currentTimeMillis();
		return stop - start;
	}

	/**
	 * Adds the given object to the data structure.
	 *
	 * @param elem 	object to add to the data structure
	 */
	public abstract void addElement(Integer elem);

	/**
	 * Removes some object from the data structure.
	 * <p>
	 * <b>QUESTION</b>: "How will the implementation of removeElement() affect the performance of the different ADTs?"
	 * <p>
	 * <b>ANSWER</b>: For ADTs based on the List class the method used is removing elements at the end. This has the 
	 * best performance since if one would remove an element at any other index all the elements coming after the
	 * removed one would have to be moved one index to the front.
	 * <p>
	 * For ADTs based on the Queue class this is the other way around. The method used to remove elements is
	 * poll(), which returns the element at the start. If one would opt to remove any other element, just as
	 * with the List class, the elements coming before that element have to be moved one index.
	 * <p>
	 * When removeElement is implemented this way, both classes have "optimal" performance and can be comapred
	 * in a realistic way, which is why we chose to implement it in this way.
	 * 
	 * @throws RuntimeException 	If no element can be removed from the underlying data structure
	 */
	public abstract void removeElement() throws RuntimeException;
	/*

	 */

	/**
	 * Determines the size of the data structure that is being timed.
	 *
	 * @return the size of the data structure
	 */
	public abstract int getSize();

	/**
	 * Tests wheter the data structure that is being timed is empty.
	 *
	 * @return true if the data structure is empty, false otherwise
	 */
	public abstract boolean isEmpty();
}