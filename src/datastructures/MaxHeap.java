package datastructures;

/**
 * @author Kasper
 */
public class MaxHeap {

	public final int[] heap;
	public int heapSize;

	/**
	 * Constructs a new max-heap with a pre-set maximum capacity. Just set this much higher than what you need.
	 *
	 * @param maxElements the maximum number of elements this max-heap should ever contain.
	 */
	public MaxHeap(int maxElements) {
		heap = new int[maxElements];
	}

	public int extractMax() {
		int max = heap[0];

		heap[0] = heap[heapSize - 1];
		heapSize--;

		maxHeapify(0);

		return max;
	}

	public void insert(int number) {
		heapSize++;

		int i = heapSize - 1;
		heap[i] = number;

		while (i > 0 && heap[parent(i)] < heap[i]) {
			heap[i] = heap[parent(i)];
			heap[parent(i)] = number;
			i = parent(i);
		}
	}

	/**
	 * Get the index of the parent of the specified index.
	 *
	 * @param i the index whose parent to get
	 * @return the index of the parent
	 */
	public int parent(int i) {
		return (i - 1) / 2;
	}

	/**
	 * Get the index of the left child of the specified index.
	 *
	 * @param i the index whose left child to get
	 * @return the index of the left child
	 */
	public int leftChild(int i) {
		return i * 2 + 1;
	}

	/**
	 * Get the index of the right child of the specified index.
	 *
	 * @param i the index whose right child to get
	 * @return the index of the right child
	 */
	public int rightChild(int i) {
		return i * 2 + 2;
	}

	/**
	 * Ensure that the max-heap property is satisfied.
	 *
	 * @param i the index of the node from which to perform this operation. Only the node at this index and its children
	 *          will be affected.
	 */
	public void maxHeapify(int i) {
		int left = leftChild(i);
		int right = rightChild(i);

		int largest = i;

		if (left < heapSize && heap[left] > heap[largest]) {
			largest = left;
		}

		if (right < heapSize && heap[right] > heap[largest]) {
			largest = right;
		}

		if (largest != i) {
			int temp = heap[i];
			heap[i] = heap[largest];
			heap[largest] = temp;
			maxHeapify(largest);
		}
	}
}
