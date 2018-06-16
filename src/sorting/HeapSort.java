package sorting;

import datastructures.MaxHeap;
import util.PermutationGenerator;

/**
 * @author Kasper
 */
public class HeapSort {

	public static void heapSort(int[] data) {
		MaxHeap maxHeap = new MaxHeap(data.length);
		System.out.println("Build max heap:");
		maxHeap.buildMaxHeap(data);

		System.out.println("\nHeap sort:");
		for (int i = maxHeap.heapSize - 1; i > 0; i--) {
			maxHeap.exchange(0, i);
			maxHeap.heapSize--;
			maxHeap.maxHeapify(0);
			System.out.println();
		}

		System.out.println("Result:");
		maxHeap.heapSize = data.length;
		System.out.println(maxHeap);
	}

	public static void main(String[] args) {
		int[] data = PermutationGenerator.getRandomPermutation(10);
		heapSort(data);
	}
}
