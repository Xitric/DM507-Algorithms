package datastructures;

/**
 * @author Kasper
 */
public class BinarySearchTree {

	public Node root;

	private int size;

	private int counter;

	public void insert(int k) {
		Node y = null;

		Node z = new Node();
		z.key = k;

		Node x = root;
		while (x != null) {
			y = x;

			if (z.key < x.key) {
				x = x.left;
			} else {
				x = x.right;
			}
		}

		z.parent = y;
		if (y == null) {
			root = z;
		} else if (z.key < y.key) {
			y.left = z;
		} else {
			y.right = z;
		}

		size++;
	}

	public void transplant(Node u, Node v) {
		if (u.parent == null) {
			root = v;
		} else if (u == u.parent.left) {
			u.parent.left = v;
		} else {
			u.parent.right = v;
		}

		if (v != null) {
			v.parent = u.parent;
		}
	}

	public void delete(Node z) {
		if (z.left == null) {
			transplant(z, z.right);
		} else if (z.right == null) {
			transplant(z, z.left);
		} else {
			Node y = treeMinimum(z.right);
			if (y.parent != z) {
				transplant(y, y.right);
				y.right = z.right;
				y.right.parent = y;
			}

			transplant(z, y);
			y.left = z.left;
			y.left.parent = y;
		}

		size--;
	}

	public Node treeMinimum(Node x) {
		while (x.left != null) {
			x = x.left;
		}

		return x;
	}

	public Node treeMaximum(Node x) {
		while (x.right != null) {
			x = x.right;
		}

		return x;
	}

	public Node treeSuccessor(Node x) {
		if (x.right != null) {
			return treeMinimum(x.right);
		}

		Node y = x.parent;
		while (y != null && x == y.right) {
			x = y;
			y = y.parent;
		}

		return y;
	}

	public Node treePredecessor(Node x) {
		if (x.left != null) {
			return treeMaximum(x.left);
		}

		Node y = x.parent;
		while (y != null && x == y.left) {
			x = y;
			y = y.parent;
		}

		return y;
	}

	public int[] orderedTraversal() {
		int[] insert = new int[size];
		counter = 0;
		orderedTraversal(root, insert);
		return insert;
	}

	private void orderedTraversal(Node x, int[] insert) {
		if (x != null) {
			orderedTraversal(x.left, insert);

			insert[counter] = x.key;
			counter++;

			orderedTraversal(x.right, insert);
		}
	}

	public Node treeSearch(Node x, int k) {
		while (x != null && k != x.key) {
			if (k < x.key) {
				x = x.left;
			} else {
				x = x.right;
			}
		}

		return x;
	}

	public int getMaxHeight(Node x) {
		if (x != null) {
			return Math.max(getMaxHeight(x.left), getMaxHeight(x.right)) + 1;
		}

		return 0;
	}

	public static final class Node {
		public Node parent;
		public Node left;
		public Node right;
		public int key;
	}
}
