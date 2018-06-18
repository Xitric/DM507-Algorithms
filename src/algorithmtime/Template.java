package algorithmtime;

/**
 * @author Kasper
 */
public class Template {

	public static void main(String[] args) {
		Counter counter = new Counter();
		int n = 64;

		//Algorithm begins here
		//Use Counter.count() to count an iteration of an internal loop
		//Use Counter.reset() after each iteration, if the next iteration will reset the value
		//Eg.:
		//int j = 1;
		//while (j <= i) {
		//	j = j * 2;
		//	counter.count();
		//}
		//counter.reset();

		System.out.println("Algorithm:");
		System.out.println(counter);

		System.out.println("\nSequences:");
		System.out.print("n^2: ");
		System.out.println(SequenceGenerator.getLinearSequence(n));
		System.out.print("First term: ");
		System.out.println(SequenceGenerator.getExpDecreasingSequence(n));
		System.out.print("Last term: ");
		System.out.println(SequenceGenerator.getExpIncreasingSequence(n));
		System.out.print("n * log(n): ");
		System.out.println(SequenceGenerator.getLogSequence(n));
	}
}
