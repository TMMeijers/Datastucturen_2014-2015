import java.util.*;

/*
 * Class
 */
public abstract class CollectionTimer {
	
	static final int[] DEFAULT_MUTATIONS;
	private java.util.Random elemGen;

	/*  
	 * Constructor
	 */
	public CollectionTimer() {
		elemGen = new Random();
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

	}

	/*
	 * Methods
	 */
	public long time() {

	}

	/*
	 * Methods
	 */
	public long time(int[] mutations) {

	}

	public abstract void addElement(Integer elem);
	public abstract void removeElement();
	public abstract int getSize();
	public abstract boolean isEmpty();
}