package cycles;

/**
 * @author Kasper
 */
public class CycleCounter {

	public static void main(String[] args) {
        //System.out.println(count(new int[]{1,3,4,3,2,5}));

	}

	/**
	 * Count the number of cycles in the specified array of integers.
	 *
	 * @param input the array of integers in which to count cycles
	 * @return the number of cycles
	 */
	public static int count(int[] input) {
		Integer[] checked = new Integer[input.length];
		int cycles = 0;

		for (int i = 0; i < input.length; i++) {
			if (checked[i] != null) continue;

			int current = input[i];
			int next = current;
			checked[i] = current;

			while (checked[next] == null) {
				checked[next] = input[next];
				next = checked[next];
			}

			cycles++;
		}

		return cycles;
	}
}
