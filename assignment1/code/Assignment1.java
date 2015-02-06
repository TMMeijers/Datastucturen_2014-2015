import java.util.*;
import java.lang.String;
/**
 * Class
 */
public class Assignment1 {

	/**
	 * List of instances of datastructures that implement the List interface that are to be benchmarked.
	 */
	private ArrayList<List<Integer>> lists;

	/**
	 * List of instances of datastructures that implement the Queue interface that are to be benchmarked.
	 */
	private ArrayList<Queue<Integer>> queues;

	/**
	 * Default constructor. Creates an empty instance of each of the data structures that are to be benchmarked.
	 */ 
	public Assignment1() {
		lists = new ArrayList<>();
		lists.add(new ArrayList<Integer>());
		lists.add(new LinkedList<Integer>());
		lists.add(new Stack<Integer>());
		lists.add(new Vector<Integer>());
		queues = new ArrayList<>();
		queues.add(new LinkedList<Integer>());
		queues.add(new PriorityQueue<Integer>());
	}

	/**
	 * Performs benchmark with random seed 0 to populate the data structures and with mutations as in CollectionTimer.DEFAULT_MUTATIONS. The result is printed to stdout.
	 * 
	 * @see 		CollectionTimer.DEFAULT_MUTATIONS
	 */
	public void benchmark() {
		for (List<Integer> list : lists) {
			ListTimer listTimer = new ListTimer(list);
			System.out.println(list.getClass().getSimpleName() + ": " + listTimer.time());
		}
		for (Queue<Integer> queue : queues) {
			QueueTimer queueTimer = new QueueTimer(queue);
			System.out.println(queue.getClass().getSimpleName() + ": " + queueTimer.time());
		}
	}

	/**
	 * Performs benchmark using the given seed to populate the data structures and with mutations as in CollectionTimer.DEFAULT_MUTATIONS. The result is printed to stdout.
	 * 
	 * @param 	elemGenSeed 	seed for the random object generator
	 * @see 					CollectionTimer.DEFAULT_MUTATIONS
	 */
	public void benchmark(long elemGenSeed) {
		for (List<Integer> list : lists) {
			ListTimer listTimer = new ListTimer(list, elemGenSeed);
			System.out.println(list.getClass().getSimpleName() + ": " + listTimer.time());
		}
		for (Queue<Integer> queue : queues) {
			QueueTimer queueTimer = new QueueTimer(queue, elemGenSeed);
			System.out.println(queue.getClass().getSimpleName() + ": " + queueTimer.time());
		}
	}

	/**
	 * Performs benchmark with random seed 0 to populate the data structures and with mutations as in CollectionTimer.DEFAULT_MUTATIONS. The result is printed to stdout.
	 * 
	 * @param 	elemGenSeed 	seed for the random object generator
	 * @param	mutations 		integer array defining which successive permutations should be performed
	 */
	public void benchmark(long elemGenSeed, int[] mutations) {
		for (List<Integer> list : lists) {
			ListTimer listTimer = new ListTimer(list, elemGenSeed);
			System.out.println(list.getClass().getSimpleName() + ": " + listTimer.time(mutations));
		}
		for (Queue<Integer> queue : queues) {
			QueueTimer queueTimer = new QueueTimer(queue, elemGenSeed);
			System.out.println(queue.getClass().getSimpleName() + ": " + queueTimer.time(mutations));
		}
	}

	/**
	 * Main method of the program. Parses the command line options and initiates the benchmarking process according to the provided arguments. See the class description for an overview of the accepted paramers.
	 * 
	 * @param 	args 			the command line arguments passed to the program
	 */
	public static void main(String[] args) {
		long elemGenSeed;
		int mutations[];
		if (args.length > 0) {
			elemGenSeed = Long.parseLong(args[0]);
		}
		if (args.length > 1) {
			mutations = new int[args.length-1];
			for (int i = 1; i < args.length; i++) {
				mutations[i-1] = Integer.parseInt(args[i]);
			}
		}
		benchmark();
	}

	/**
	 * Print a message to stderr and exit with value 1.
	 *
	 * @param 	msg 			the error message
	 */
	private static void errorExit(String msg) {
		System.out.println(msg);
		System.exit(1);
	}
}