public class QueueTimer extends CollectionTimer {

	private Queue<Integer> queue;

	/*
	 * Constructors
	 */ 
	public QueueTimer(List<Integer> queue) {
		super();
		this.queue = queue;
	}

	/*
	 * Constructors
	 */ 
	public QueueTimer(List<Integer> queue, long elemGenSeed) {
		super(elemGenSeed);
		this.queue = queue;
	}

	/*
	 * Constructors
	 */ 
	public QueueTimer(List<Integer> queue, Long elemGenSeed) {
		super(elemGenSeed);
		this.queue = queue;
	}

	/*
	 * Methods
	 */
	public void addElement(Integer elem) {

	}

	/*
	 * Methods
	 */
	public void removeELement() {

	}

	/*
	 * Methods
	 */
	public int getSize() {
		return queue.size();
	}

	/*
	 * Methods
	 */
	public boolean isEmpty() {

	}
}