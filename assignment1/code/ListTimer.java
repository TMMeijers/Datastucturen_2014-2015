public class ListTimer extends CollectionTimer {
	
	private List<Integer> list;

	/*
	 * Constructors
	 */ 
	public ListTimer(List<Integer> list) {
		super();
		this.list = list;
	}

	/*
	 * Constructors
	 */ 
	public ListTimer(List<Integer> list, long elemGenSeed) {
		super(elemGenSeed);
		this.list = list;
	}

	/*
	 * Constructors
	 */ 
	public ListTimer(List<Integer> list, Long elemGenSeed) {
		super(elemGenSeed);
		this.list = list;
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
		return list.size();
	}

	/*
	 * Methods
	 */
	public boolean isEmpty() {

	}
}