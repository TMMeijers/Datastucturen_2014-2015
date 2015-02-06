import java.util.*;
import java.lang.String;

/**
 * Example solution for the first assignment of the course Data Structures 2009/2010 at the University of Amsterdam. 
 * Times the mutations (population and depopulation) of various data structures provided by the Java standard library 
 * that either implement the List or Queue interface.
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
	 * Performs benchmark with random seed 0 to populate the data structures and with mutations as in 
	 * CollectionTimer.DEFAULT_MUTATIONS. The result is printed to stdout.
	 * 
	 * @see CollectionTimer.DEFAULT_MUTATIONS
	 */
	public void benchmark() {
		for (List<Integer> list : lists) {
			ListTimer listTimer = new ListTimer(list);
			System.out.println(list.getClass().getSimpleName() + ": " + listTimer.time() + "ms");
		}
		for (Queue<Integer> queue : queues) {
			QueueTimer queueTimer = new QueueTimer(queue);
			System.out.println(queue.getClass().getSimpleName() + ": " + queueTimer.time() + "ms");
		}
	}

	/**
	 * Performs benchmark using the given seed to populate the data structures and with mutations as in 
	 * CollectionTimer.DEFAULT_MUTATIONS. The result is printed to stdout.
	 * 
	 * @param elemGenSeed 	seed for the random object generator
	 * @see 				CollectionTimer.DEFAULT_MUTATIONS
	 */
	public void benchmark(long elemGenSeed) {
		for (List<Integer> list : lists) {
			ListTimer listTimer = new ListTimer(list, elemGenSeed);
			System.out.println(list.getClass().getSimpleName() + ": " + listTimer.time() + "ms");
		}
		for (Queue<Integer> queue : queues) {
			QueueTimer queueTimer = new QueueTimer(queue, elemGenSeed);
			System.out.println(queue.getClass().getSimpleName() + ": " + queueTimer.time() + "ms");
		}
	}

	/**
	 * Performs benchmark with random seed 0 to populate the data structures and with mutations as in 
	 * CollectionTimer.DEFAULT_MUTATIONS. The result is printed to stdout.
	 * 
	 * @param elemGenSeed 	seed for the random object generator
	 * @param mutations 	integer array defining which successive permutations should be performed
	 */
	public void benchmark(long elemGenSeed, int[] mutations) {
		for (List<Integer> list : lists) {
			ListTimer listTimer = new ListTimer(list, elemGenSeed);
			System.out.println(list.getClass().getSimpleName() + ": " + listTimer.time(mutations) + "ms");
		}
		for (Queue<Integer> queue : queues) {
			QueueTimer queueTimer = new QueueTimer(queue, elemGenSeed);
			System.out.println(queue.getClass().getSimpleName() + ": " + queueTimer.time(mutations) + "ms");
		}
	}

	/**
	 * Main method of the program. Parses the command line options and initiates the benchmarking process according to 
	 * the provided arguments. See the class description for an overview of the accepted paramers.
	 * 
	 * @param args 	the command line arguments passed to the program
	 */
	public static void main(String[] args) {
		Assignment1 assignment1 = new Assignment1();

		if (args.length > 0) {
			long elemGenSeed = 0;
			int mutationsIndex = 0;

			// If seed is specified
			if (args[0].equals("-s")) {
				try {
					elemGenSeed = Long.parseLong(args[1]);
					mutationsIndex = 2;
				} catch (NumberFormatException e) {
					errorExit("Please specify the seed correctly");
				}
			}

			// Get mutations
			int[] mutations = new int[args.length - mutationsIndex];
			for (int i = 0; i < args.length - mutationsIndex; i++) {
				try {
					mutations[i] = Integer.parseInt(args[i + mutationsIndex]);
				} catch (NumberFormatException e) {
					errorExit("Please specify the mutations correctly");
				}
			}

			// Call benchmark method according to specified arguments
			if (mutations.length > 0) {
				assignment1.benchmark(elemGenSeed, mutations);
			} else {
				assignment1.benchmark(elemGenSeed);
			}
		} else {
			assignment1.benchmark();
		}
		

	}

	/**
	 * Print a message to stderr and exit with value 1.
	 *
	 * @param msg 	the error message
	 */
	private static void errorExit(String msg) {
		System.out.println(msg);
		System.exit(1);
	}
}