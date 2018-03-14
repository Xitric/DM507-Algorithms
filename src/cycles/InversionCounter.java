package cycles;

import java.util.Arrays;

/**
 * @author Kasper
 */
public class InversionCounter {

	/**
	 * Count the number of inversions in the specified array of integers.
	 *
	 * @param input the array of integers in which to count inversions
	 * @return the number of inversions
	 */
	public static int countInversions(int[] input) {
		return countInversions(input, 0, input.length);
	}

	public static int countInversions(int[] input, int p, int r) {
		int inv = 0;

		if (p < r - 1) {
			int q = (p + r) / 2;
			inv += countInversions(input, p, q);
			inv += countInversions(input, q, r);
			inv += mergeCountInversions(input, p, q, r);
		}

		return inv;
	}

	public static int mergeCountInversions(int[] input, int p, int q, int r) {
		int[] left = Arrays.copyOfRange(input, p, q);
		int[] right = Arrays.copyOfRange(input, q, r);

		int i = 0;
		int j = 0;
		int inv = 0;

		for (int k = p; k < r; k++) {
			if (j == right.length || (i < left.length && left[i] <= right[j])) {
				input[k] = left[i];
				i++;
			} else {
				input[k] = right[j];
				j++;
				inv += left.length - i;
			}
		}

		return inv;
	}

	public static void main(String[] args) {
		//Example run code
		int[] permutation = new int[]{2, 3, 8, 6, 1};
		System.out.println(countInversions(permutation));
	}
}
