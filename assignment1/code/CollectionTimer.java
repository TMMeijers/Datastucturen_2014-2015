import java.util.*;

/*
 * Class
 */
public abstract class CollectionTimer {
	
	static final int[] DEFAULT_MUTATIONS = new int[] {10000, 10000};
	private java.util.Random elemGen;

	/*  
	 * Constructor
	 */
	public CollectionTimer() {
		elemGen = new Random();
		Random temp = new Random(0);
	}	

	/*  
	 * Constructor
	 */
	public CollectionTimer(long elemGenSeed) {
		elemGen = new Random(elemGenSeed);
	}

	/*
	 * Methods
	 */
	public void insert(int amount) {

	}

	/*
	 * Methods
	 */
	public boolean extract(int amount) {
		return true;
	}

	/*
	 * Methods
	 */
	public long time() {
		return (long)0.0;
	}

	/*
	 * Methods
	 */
	public long time(int[] mutations) {
		return (long)0.0;
	}

	public abstract void addElement(Integer elem);
	public abstract void removeElement();
	public abstract int getSize();
	public abstract boolean isEmpty();
}