package sorting;

import java.util.Arrays;

/**
 * @author Kasper
 */
public class MergeSort {

	/**
	 * Perform merge sort of the specified array of integers.
	 *
	 * @param input the array of integers to sort
	 */
	public static void mergeSort(int[] input) {
		mergeSort(input, 0, input.length);
	}

	/**
	 * Perform merge sort of the portion of the specified array from index p (inclusive) to index r (exclusive).
	 *
	 * @param input the array of integers to sort
	 * @param p     the index to sort from (inclusive)
	 * @param r     the index to sort to (exclusive)
	 */
	public static void mergeSort(int[] input, int p, int r) {
		if (p < r - 1) {
			int q = (p + r) / 2;
			mergeSort(input, p, q); //Sort the range [p,q-1]
			mergeSort(input, q, r); //Sort the range [q,r-1]
			merge(input, p, q, r);
		}
	}

	/**
	 * Merge two sorted sequences of integers. The first sequence is the indexes [p,q-1] and the second sequence is the
	 * indexes [q,r-1].
	 *
	 * @param input the array containing the integers to merge
	 * @param p     the lower index of the first sequence (inclusive)
	 * @param q     the lower index of the second sequence (inclusive)
	 * @param r     the upper index of the second sequence (exclusive)
	 */
	public static void merge(int[] input, int p, int q, int r) {
		int[] left = Arrays.copyOfRange(input, p, q); //The numbers in the range [p,q-1]
		int[] right = Arrays.copyOfRange(input, q, r); //The numbers in the range [q,r-1]

		int i = 0;
		int j = 0;

		for (int k = p; k < r; k++) {
			if (j == right.length || (i < left.length && left[i] <= right[j])) {
				input[k] = left[i];
				i++;
			} else {
				input[k] = right[j];
				j++;
			}
		}
	}
}
