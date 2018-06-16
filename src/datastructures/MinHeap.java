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

	public void buildMinHeap(int[] data) {
		System.arraycopy(data, 0, heap, 0, data.length);
		heapSize = data.length;

		for (int i = data.length / 2 - 1; i >= 0; i--) {
			minHeapify(i);
		}
	}

	public int minimum() {
		return heap[0];
	}

	public int extractMin() {
		int min = heap[0];

		heap[0] = heap[heapSize - 1];
		heapSize--;

		System.out.println(this);
		minHeapify(0);

		return min;
	}

	public void decreaseKey(int i, int key) {
		heap[i] = key;
		System.out.println(this);

		while (i > 0 && heap[parent(i)] > heap[i]) {
			exchange(i, parent(i));
			i = parent(i);

			System.out.println(this);
		}
	}

	public void insert(int number) {
		heapSize++;
		heap[heapSize - 1] = Integer.MAX_VALUE;
		decreaseKey(heapSize - 1, number);
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
			exchange(i, smallest);
			System.out.println(this);

			minHeapify(smallest);
		}
	}

	public void exchange(int i, int j) {
		int temp = heap[i];
		heap[i] = heap[j];
		heap[j] = temp;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");

		for (int i = 0; i < heapSize; i++) {
			sb.append(heap[i]).append(", ");
		}

		sb.setLength(sb.length() - 2);
		sb.append("]");

		return sb.toString();
	}
}
