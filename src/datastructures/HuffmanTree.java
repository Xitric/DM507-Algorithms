package datastructures;

import java.util.Arrays;
import java.util.List;

/**
 * A representation of a Huffman tree. This class is used to generate Huffman codes based on an array of byte
 * frequencies.
 */
public class HuffmanTree {

	//The highest element in the tree
	public Node root;

	//Pointer used when traversing the Huffman tree to recreate bytes from Huffman codes
	private Node currentNode;

	public List<String> elements;

	/**
	 * Constructs a new Huffman tree with the specified value in its root node.
	 *
	 * @param rootValue the value to place in the root
	 */
	private HuffmanTree(String rootValue) {
		root = new Node();
		root.value = rootValue;

		currentNode = root;
	}

	/**
	 * Constructor for the Huffman tree. This is private since huffman trees should be created using the static factory
	 * method. This constructor helps in the Huffman algorithm, where a binary tree must be constructed by continuously
	 * combining smaller Huffman trees.
	 *
	 * @param left  the huffman tree to insert to the left of the root node in the new tree
	 * @param right the huffman tree to insert to the right of the root node in the new tree
	 */
	private HuffmanTree(HuffmanTree left, HuffmanTree right) {
		this(null);
		root.left = left.root;
		root.right = right.root;
	}

	/**
	 * Constructs a Huffman tree from the specified array of byte frequencies.
	 *
	 * @param frequencies the frequencies of the bytes to generate a tree for
	 * @param elements    the string represented by this Huffman tree
	 * @return the resulting Huffman tree
	 */
	public static HuffmanTree createHuffmanTree(Integer[] frequencies, String[] elements) {
		//Initialize a new priority queue to sort subtrees by frequency
		PQHeap queue = new PQHeap(frequencies.length);

		//Construct a new tree for each element. This is the initialization step. The trees are stored with the
		//frequency of their byte in the queue
		for (int i = 0; i < frequencies.length; i++) {
			HuffmanTree tree = new HuffmanTree(elements[i]);
			Element element = new Element(frequencies[i], tree);
			queue.insert(element);
		}

		//Combine all subtrees to form a single tree. This is the Huffman tree
		for (int i = 0; i < frequencies.length - 1; i++) {
			//Combine the two least frequent trees from the queue into a new tree
			Element left = queue.extractMin();
			Element right = queue.extractMin();

			HuffmanTree combined = new HuffmanTree((HuffmanTree) left.data, (HuffmanTree) right.data);

			//Store this new subtree by the combined frequencies
			int combinedFrequency = left.key + right.key;
			queue.insert(new Element(combinedFrequency, combined));
		}

		//The last tree in the queue is now the Huffman tree
		HuffmanTree ht = (HuffmanTree) queue.extractMin().data;
		ht.elements = Arrays.asList(elements);
		return ht;
	}

	/**
	 * Get the array of Huffman codes that this Huffman tree represents. Each string is the compressed code of a byte.
	 *
	 * @return an array of Huffman codes
	 */
	public String[] orderedTraversal() {
		String[] huffmanCodes = new String[elements.size()];

		//Generate codes recursively. Initially the code is empty
		orderedTraversalInternal(root, huffmanCodes, "");
		return huffmanCodes;
	}

	/**
	 * Recursive method for creating an array of Huffman codes from a Huffman tree. This method will be called for each
	 * node in the tree to recursively generate the Huffman codes for all the bytes that it represents.
	 *
	 * @param subRoot       the root of the subtree that is currently traversed
	 * @param huffmanCodes  the array used to store all Huffman codes
	 * @param recursiveCode the code that has been generated so far at this recursive step
	 */
	private void orderedTraversalInternal(Node subRoot, String[] huffmanCodes, String recursiveCode) {
		if (subRoot.left == null || subRoot.right == null) {
			//We have reached a leaf node, so we insert the code that was generated for this node's byte
			huffmanCodes[elements.indexOf(subRoot.value)] = recursiveCode;
			return;
		}

		//The root has children, so we call recursively on them
		//When moving left in the Huffman tree we append '0' to the code
		orderedTraversalInternal(subRoot.left, huffmanCodes, recursiveCode + "0");

		//When moving right in the Huffman tree we append '1' to the code
		orderedTraversalInternal(subRoot.right, huffmanCodes, recursiveCode + "1");
	}

	/**
	 * Make a step down the Huffman tree based on the next bit read from an encoded file.
	 *
	 * @param bit the next bit that is read from the file
	 * @return true if the step ended in a leaf Node and thus finished reading a Huffman code, false otherwise
	 */
	public boolean step(int bit) {
		//0 means move left
		if (bit == 0) {
			currentNode = currentNode.left;
		}
		//1 means move right
		else if (bit == 1) {
			currentNode = currentNode.right;
		}

		//Signal whether we arrived in a leaf Node
		return currentNode.left == null || currentNode.right == null;
	}

	/**
	 * This method should be called after {@link #step(int)} has returned true to get the value of the string decoded
	 * from that Huffman code. This resets the pointer in the tree so that {@link #step(int)} can be used again.
	 *
	 * @return the resulting string after having read a Huffman code
	 */
	public String getResultingValue() {
		String val = currentNode.value;
		currentNode = root;

		return val;
	}

	public int getHeight(Node x) {
		if (x == null) return 0;
		return 1 + Math.max(getHeight(x.left), getHeight(x.right));
	}

	public static class Node {

		public Node left;
		public Node right;

		/**
		 * This variable stores the byte value that this Node represents. If this is an internal Node, the contents of this
		 * variable are not interesting.
		 */
		public String value;
	}

	public static class Element {

		public int key;
		public Object data;

		public Element(int i, Object o) {
			this.key = i;
			this.data = o;
		}
	}

	public static class PQHeap {

		//Array for representing the min-heap internally
		private final Element[] heap;

		//The index of the first empty position in the min-heap array. Since indices start at 0, this is also the amount of
		//elements in this priority queue, which will be less than or equal to the amount of available space
		private int heapSize;

		/**
		 * Constructor for the min-heap based priority queue. Use this constructor to initialize a priority queue with an
		 * initial available capacity. This capacity cannot be changed after the priority queue has been initialized.
		 *
		 * @param maxElms the number of available space for inserting elements
		 */
		public PQHeap(int maxElms) {
			heap = new Element[maxElms];
		}

		/**
		 * Get the minimum element in this priority queue. This operation has a running time of O(log n), with n being the
		 * number of elements in the data structure.
		 *
		 * @return the minimum element in this priority queue
		 */
		public Element extractMin() {
			//Create a reference to the minimum element, which is at the first index of the array
			Element min = heap[0];

			//Replace the minimum element with the last element of the min-heap and decrease the size by one
			heap[0] = heap[heapSize - 1];
			heapSize--;

			//Ensure that the min-heap satisfied the min-heap property
			minHeapify(0);

			//Return the minimum element that we got a reference to earlier
			return min;
		}

		/**
		 * Insert a new element in this priority queue. This operation has a running time of O(log n), with n being the
		 * number of elements in this data structure.
		 *
		 * @param e the new element to insert
		 */
		public void insert(Element e) {
			//Increase the heap size by one to allow the new element
			heapSize++;

			//Point at the newly inserted element. The index is 'size - 1' because our indices start at 0
			int i = heapSize - 1;
			heap[i] = e;

			//As long as we have not yet reached the root, and the parent of the node we currently point at is larger, we
			//swap this node and its parent, and move one step up the min-heap
			while (i > 0 && heap[parent(i)].key > heap[i].key) {
				heap[i] = heap[parent(i)];
				heap[parent(i)] = e;
				i = parent(i);
			}
		}

		/**
		 * Get the index of the parent of the specified index.
		 *
		 * @param i the index whose parent to get
		 * @return the index of the parent node
		 */
		private int parent(int i) {
			//Because our indices start at 0, we had to subtract one from the index
			return (i - 1) / 2;
		}

		/**
		 * Get the index of the left child of the specified index.
		 *
		 * @param i the index whose left child to get
		 * @return the index of the left child
		 */
		private int leftChild(int i) {
			//We had to change this formula slightly because our indices start at 0
			return i * 2 + 1;
		}

		/**
		 * Get the index of the right child of the specified index.
		 *
		 * @param i the index whose right child to get
		 * @return the index of the right child
		 */
		private int rightChild(int i) {
			//We had to change this formula slightly because our indices start at 0
			return i * 2 + 2;
		}

		/**
		 * Ensure that the internal min-heap satisfied the min-heap property. This operation has a running time of O(log n),
		 * with n being the number of elements in this data structure.
		 *
		 * @param i the index from which to perform this operation. Only the node at this index and its children will be
		 *          affected.
		 */
		private void minHeapify(int i) {
			//Get the indices of the children of this node
			int left = leftChild(i);
			int right = rightChild(i);

			//We assume for now that the parent is the smallest element
			int smallest = i;

			//If the left child exists and it is smaller than the currently smallest element, then the left child must be
			//the smallest element
			if (left < heapSize && heap[left].key < heap[smallest].key) {
				smallest = left;
			}

			//If the right child exists and it is smaller than the currently smallest element, then the right child must be
			//the smallest element
			if (right < heapSize && heap[right].key < heap[smallest].key) {
				smallest = right;
			}

			//If the smallest element is not the parent, then we swap the parent with the smallest element and recursively
			//perform the min-heapify operation from the index of the child node we swap with
			if (smallest != i) {
				Element temp = heap[i];
				heap[i] = heap[smallest];
				heap[smallest] = temp;
				minHeapify(smallest);
			}
		}
	}

}
