package sorting;

import java.util.Arrays;

/**
 * @author Kasper
 */
public class CountingSort {

	private int[] cInitial;
	private int[] cAccumulated;
	private int[] cFinal;
	private int[] input;
	private int[] output;

	/**
	 * Sort the specified array of integers.
	 *
	 * @param input the array of integers to sort
	 * @param k     the maximum value in the input array (inclusive)
	 */
	public void sort(int[] input, int k) {
		this.input = input;
		cInitial = new int[k + 1];

		//Perform initial counting of elements
		for (int i = 0; i < input.length; i++) {
			cInitial[input[i]]++;
		}

		//Accumulate element counts
		cAccumulated = Arrays.copyOf(cInitial, cInitial.length);
		for (int i = 1; i < cAccumulated.length; i++) {
			cAccumulated[i] = cAccumulated[i] + cAccumulated[i - 1];
		}

		//Sort output array
		cFinal = Arrays.copyOf(cAccumulated, cAccumulated.length);
		output = new int[input.length];

		for (int i = input.length - 1; i >= 0; i--) {
			output[cFinal[input[i]] - 1] = input[i];
			cFinal[input[i]]--;
		}
	}

	public int[] getCountingInitial() {
		return cInitial;
	}

	public int[] getCountingAccumulated() {
		return cAccumulated;
	}

	public int[] getCountingFinal() {
		return cFinal;
	}

	public int[] getSortedArray() {
		return output;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Counting sort:\n");
		sb.append("Input array: ").append(Arrays.toString(input)).append("\n");
		sb.append("Output array: ").append(Arrays.toString(output)).append("\n");
		sb.append("Initial counting array: ").append(Arrays.toString(cInitial)).append("\n");
		sb.append("Accumulated counting array: ").append(Arrays.toString(cAccumulated)).append("\n");
		sb.append("Final counting array: ").append(Arrays.toString(cFinal));
		return sb.toString();
	}

	public static void main(String[] args) {
		//Example run code
		CountingSort cs = new CountingSort();

		cs.sort(new int[]{7, 4, 1, 2, 6, 4, 0, 4, 4, 4, 7, 2}, 7);
		System.out.println(cs);
	}
}
