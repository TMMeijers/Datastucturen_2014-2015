import java.util.*;

/*
 * Class
 */
public class Assignment1 {

	/*
	 * List of instances of datastructures that implement the List interface that are to be benchmarked.
	 */
	private ArrayList<List<Integer>> lists;

	/*
	 * List of instances of datastructures that implement the Queue interface that are to be benchmarked.
	 */
	private ArrayList<Queue<Integer>> queues;

	/*
	 * Default constructor. Creates an empty instance of each of the data structures that are to be benchmarked.
	 */ 
	public Assignment1() {

	}

	/*
	 * Performs benchmark using the given seed to populate the data structures and with mutations as in CollectionTimer.DEFAULT_MUTATIONS. The result is printed to stdout.
	 * 
	 * @see 		CollectionTimer.DEFAULT_MUTATIONS
	 */
	public void benchmark() {

	}

	/*
	 * Performs benchmark with random seed 0 to populate the data structures and with mutations as in CollectionTimer.DEFAULT_MUTATIONS. The result is printed to stdout.
	 * 
	 * @param 	elemGenSeed 	seed for the random object generator
	 * @see 					CollectionTimer.DEFAULT_MUTATIONS
	 */
	public void benchmark(long elemGenSeed) {

	}

	/*
	 * Performs benchmark with random seed 0 to populate the data structures and with mutations as in CollectionTimer.DEFAULT_MUTATIONS. The result is printed to stdout.
	 * 
	 * @param 	elemGenSeed 	seed for the random object generator
	 * @param	mutations 		integer array defining which successive permutations should be performed
	 */
	public void benchmark(long elemGenSeed, int[] mutations) {

	}

	/*
	 * Main method of the program. Parses the command line options and initiates the benchmarking process according to the provided arguments. See the class description for an overview of the accepted paramers.
	 * 
	 * @param 	args 			the command line arguments passed to the program
	 */
	public static void main(String[] args) {

	}

	/*
	 * Print a message to stderr and exit with value 1.
	 *
	 * @param 	msg 			the error message
	 */
	private static void errorExit(String msg) {
		System.out.println(msg);
		System.exit(1);
	}
}