package sorting;

/**
 * @author Kasper
 */
public class InsertionSort {

	//TODO: What would we want to output here?
	public void sort(int[] input) {
		for (int j = 1; j < input.length; j++) {
			int key = input[j];
			int i = j - 1;

			while (i >= 0 && input[i] > key) {
				input[i + 1] = input[i];
				i--;
			}

			input[i + 1] = key;
		}
	}
}
