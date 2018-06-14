package sorting;

import util.PermutationGenerator;

import java.util.Arrays;

/**
 * @author Kasper
 */
public class QuickSort {

	/**
	 * Perform quick sort of the specified array of integers.
	 *
	 * @param input the array of integers to sort
	 */
	public static void sort(int[] input) {
		sort(input, 0, input.length - 1);
	}

	/**
	 * Perform quick sort of the portion of the specified array from index p (inclusive) to index r (inclusive).
	 *
	 * @param input the array of integers to sort
	 * @param p     the index to sort from (inclusive)
	 * @param r     the index to sort to (inclusive)
	 */
	public static void sort(int[] input, int p, int r) {
		if (p < r) {
			int q = partition(input, p, r);
			sort(input, p, q - 1);
			sort(input, q + 1, r);
		}
	}

	/**
	 * Partition the sequence of integers from index p (inclusive) to index r (inclusive), using the integer at index r
	 * as a pivot.
	 *
	 * @param input the array of integers to sort
	 * @param p     the lower index of the partition (inclusive)
	 * @param r     the upper index of the partition and the index of the pivot (inclusive)
	 * @return the index of the pivot after it has been rearranged in the partition
	 */
	public static int partition(int[] input, int p, int r) {
		int x = input[r];
		int i = p - 1;

		System.out.println("\nPartition:");
		System.out.println("Input: " + Arrays.toString(Arrays.copyOfRange(input, p, r + 1)));

		for (int j = p; j < r; j++) {
			if (input[j] <= x) {
				i++;
				exchange(input, i, j);
			}
		}

		exchange(input, i + 1, r);

		System.out.println("Pivot: " + x);
		System.out.println("Pivot placement: " + (i + 1));
		System.out.println("Result: " + Arrays.toString(Arrays.copyOfRange(input, p, r + 1)));

		return i + 1;
	}

	private static void exchange(int[] input, int a, int b) {
		int temp = input[a];
		input[a] = input[b];
		input[b] = temp;
	}

	public static void main(String[] args) {
		sort(PermutationGenerator.getRandomPermutation(8), 0, 7);
	}
}
