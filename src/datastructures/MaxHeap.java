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

	public void buildMaxHeap(int[] data) {
		System.arraycopy(data, 0, heap, 0, data.length);
		heapSize = data.length;

		for (int i = data.length / 2 - 1; i >= 0; i--) {
			maxHeapify(i);
		}
	}

	public int maximum() {
		return heap[0];
	}

	public int extractMax() {
		int max = heap[0];

		heap[0] = heap[heapSize - 1];
		heapSize--;

		System.out.println(this);
		maxHeapify(0);

		return max;
	}

	public void increaseKey(int i, int key) {
		heap[i] = key;
		System.out.println(this);

		while (i > 0 && heap[parent(i)] < heap[i]) {
			exchange(i, parent(i));
			i = parent(i);

			System.out.println(this);
		}
	}

	public void insert(int number) {
		heapSize++;
		heap[heapSize - 1] = Integer.MIN_VALUE;
		increaseKey(heapSize - 1, number);
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
			exchange(i, largest);
			System.out.println(this);

			maxHeapify(largest);
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

	public static void main(String[] args) {
		MaxHeap max = new MaxHeap(1000);
		max.heap[0] = 10;
		max.heap[1] = 7;
		max.heap[2] = 6;
		max.heap[3] = 5;
		max.heap[4] = 4;
		max.heap[5] = 2;
		max.heap[6] = 3;
		max.heap[7] = 1;
		max.heap[8] = 2;
		max.heap[9] = 3;
		max.heap[10] = 1;
		max.heap[11] = 1;
		max.heapSize = 12;

		System.out.println(max);
		max.extractMax();
//		max.insert(8);

//		max.insert(5);
//		max.insert(4);
//		max.insert(3);
//		max.insert(2);
//		max.insert(1);
//		max.insert(10);
//		max.insert(9);
//		max.insert(8);
//		max.insert(7);
//		max.insert(6);
//		System.out.println(max);
	}
}
