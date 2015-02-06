import java.util.Queue;
import java.util.NoSuchElementException;

/**
 * Class that times the performance of a data structure that implements the Queue interface.
 *
 * @author Markus Pfundstein and Thomas Meijers
 */
public class QueueTimer extends CollectionTimer {

	/**
	 * The queue that will be benchmarked.
	 */
	private Queue<Integer> queue;

	/**
	 * Constructor that creates a QueueTimer instance for the given queue.
	 *
	 * @param queue 	instance of the data structure that is to be benchmarked.
	 */ 
	public QueueTimer(Queue<Integer> queue) {
		super();
		this.queue = queue;
	}

	/**
	 * Constructor that creates a QueueTimer instance for the given queue.
	 *
	 * @param queue 		instance of the data structure that is to be benchmarked.
	 * @param elemGenSeed 	seed for the generator of random elements
	 */ 
	public QueueTimer(Queue<Integer> queue, long elemGenSeed) {
		super(elemGenSeed);
		this.queue = queue;
	}

	/**
	 * Constructor that creates a QueueTimer instance for the given queue.
	 *
	 * @param queue 		instance of the data structure that is to be benchmarked.
	 * @param elemGenSeed 	seed for the generator of random elements
	 */ 
	public QueueTimer(Queue<Integer> queue, Long elemGenSeed) {
		super(elemGenSeed);
		this.queue = queue;
	}

	/**
	 * Adds an Integer object to the queue.
	 *
	 * @param elem 	the object that is to be inserted
	 */ 
	public void addElement(Integer elem) {
		queue.add(elem);
	}

	/**
	 * Removes an object from the queue.
	 *
	 * @throws NoSuchElementException 	if the queue is empty
	 */ 
	public void removeElement() throws NoSuchElementException {
		queue.poll();
	}

	/**
	 * Returns the size of the queue.
	 *
	 * @return the size of the queue
	 */
	public int getSize() {
		return queue.size();
	}

	/**
	 * Tells whether the queue is empty.
	 *
	 * @return true if the queue is empty, false otherwise
	 */
	public boolean isEmpty() {
		if (queue.size() > 0) {
			return true;
		}
		return false;
	}
}