package datastructures;

/**
 * @author Kasper
 */
public class MinHeap {

	public final int[] heap;
	public int heapSize;

	/**
	 * Constructs a new min-heap with a pre-set maximum capacity. Just set this much higher than what you need.
	 *
	 * @param maxElements the maximum number of elements this min-heap should ever contain.
	 */
	public MinHeap(int maxElements) {
		heap = new int[maxElements];
	}

	public int extractMin() {
		int min = heap[0];

		heap[0] = heap[heapSize - 1];
		heapSize--;

		minHeapify(0);

		return min;
	}

	public void insert(int number) {
		heapSize++;

		int i = heapSize - 1;
		heap[i] = number;

		while (i > 0 && heap[parent(i)] > heap[i]) {
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
	 * Ensure that the min-heap property is satisfied.
	 *
	 * @param i the index of the node from which to perform this operation. Only the node at this index and its children
	 *          will be affected.
	 */
	public void minHeapify(int i) {
		int left = leftChild(i);
		int right = rightChild(i);

		int smallest = i;

		if (left < heapSize && heap[left] < heap[smallest]) {
			smallest = left;
		}

		if (right < heapSize && heap[right] < heap[smallest]) {
			smallest = right;
		}

		if (smallest != i) {
			int temp = heap[i];
			heap[i] = heap[smallest];
			heap[smallest] = temp;
			minHeapify(smallest);
		}
	}
}