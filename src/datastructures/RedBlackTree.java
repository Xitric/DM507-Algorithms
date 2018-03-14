package datastructures;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kasper
 */
public class RedBlackTree {

	public static final Node NIL = new Node(Color.Black);
	public Node root = NIL;

	public void leftRotate(Node x) {
		Node y = x.right;
		x.right = y.left;

		if (y.left != NIL) {
			y.left.parent = x;
		}

		y.parent = x.parent;

		if (x.parent == NIL) {
			root = y;
		} else if (x == x.parent.left) {
			x.parent.left = y;
		} else {
			x.parent.right = y;
		}

		y.left = x;
		x.parent = y;
	}

	//TODO: Correct?
	public void rightRotate(Node y) {
		Node x = y.left;
		y.left = x.right;

		if (x.right != NIL) {
			x.right.parent = y;
		}

		x.parent = y.parent;

		if (y.parent == NIL) {
			root = x;
		} else if (y == y.parent.right) {
			y.parent.right = x;
		} else {
			y.parent.left = x;
		}

		x.right = y;
		y.parent = x;
	}

	public void insert(int key) {
		Node z = new Node(key);

		Node y = NIL;
		Node x = root;

		while (x != NIL) {
			y = x;

			if (z.key < x.key) {
				x = x.left;
			} else {
				x = x.right;
			}
		}

		z.parent = y;

		if (y == NIL) {
			root = z;
		} else if (z.key < y.key) {
			y.left = z;
		} else {
			y.right = z;
		}

		z.left = NIL;
		z.right = NIL;
		z.color = Color.Red;

		insertFixup(z);
	}

	public void insertFixup(Node z) {
		while (z.parent.color == Color.Red) {
			if (z.parent == z.parent.parent.left) {
				Node y = z.parent.parent.right;

				if (y.color == Color.Red) {
					z.parent.color = Color.Black;
					y.color = Color.Black;
					z.parent.parent.color = Color.Red;
					z = z.parent.parent;
				} else {
					if (z == z.parent.right) {
						z = z.parent;
						leftRotate(z);
					}

					z.parent.color = Color.Black;
					z.parent.parent.color = Color.Red;
					rightRotate(z.parent.parent);
				}
			} else {
				Node y = z.parent.parent.left;

				if (y.color == Color.Red) {
					z.parent.color = Color.Black;
					y.color = Color.Black;
					z.parent.parent.color = Color.Red;
					z = z.parent.parent;
				} else {
					if (z == z.parent.left) {
						z = z.parent;
						rightRotate(z);
					}

					z.parent.color = Color.Black;
					z.parent.parent.color = Color.Red;
					leftRotate(z.parent.parent);
				}
			}
		}

		root.color = Color.Black;
	}

	public void delete(Node z) {
		Node y = z;
		Node x;
		Color yOriginalColor = y.color;

		if (z.left == NIL) {
			x = z.right;
			transplant(z, z.right);
		} else if (z.right == NIL) {
			x = z.left;
			transplant(z, z.left);
		} else {
			y = treeMinimum(z.right);
			yOriginalColor = y.color;
			x = y.right;

			if (y.parent == z) {
				x.parent = y;
			} else {
				transplant(y, y.right);
				y.right = z.right;
				y.right.parent = y;
			}

			transplant(z, y);
			y.left = z.left;
			y.left.parent = y;
			y.color = z.color;
		}

		if (yOriginalColor == Color.Black) {
			deleteFixup(x);
		}
	}

	public void transplant(Node u, Node v) {
		if (u.parent == NIL) {
			root = v;
		} else if (u == u.parent.left) {
			u.parent.left = v;
		} else {
			u.parent.right = v;
		}

		v.parent = u.parent;
	}

	public void deleteFixup(Node x) {
		while (x != root && x.color == Color.Black) {
			if (x == x.parent.left) {
				Node w = x.parent.right;

				if (w.color == Color.Red) {
					w.color = Color.Black;
					x.parent.color = Color.Red;
					leftRotate(x.parent);
					w = x.parent.right;
				}

				if (w.left.color == Color.Black && w.right.color == Color.Black) {
					w.color = Color.Red;
					x = x.parent;
				} else {
					if (w.right.color == Color.Black) {
						w.left.color = Color.Black;
						w.color = Color.Red;
						rightRotate(w);
						w = x.parent.right;
					}

					w.color = x.parent.color;
					x.parent.color = Color.Black;
					w.right.color = Color.Black;
					leftRotate(x.parent);
					x = root;
				}
			} else {
				Node w = x.parent.left;

				if (w.color == Color.Red) {
					w.color = Color.Black;
					x.parent.color = Color.Red;
					rightRotate(x.parent);
					w = x.parent.left;
				}

				if (w.right.color == Color.Black && w.left.color == Color.Black) {
					w.color = Color.Red;
					x = x.parent;
				} else {
					if (w.left.color == Color.Black) {
						w.right.color = Color.Black;
						w.color = Color.Red;
						leftRotate(w);
						w = x.parent.left;
					}

					w.color = x.parent.color;
					x.parent.color = Color.Black;
					w.left.color = Color.Black;
					rightRotate(x.parent);
					x = root;
				}
			}
		}

		x.color = Color.Black;
	}

	public Node treeMinimum(Node x) {
		while (x.left != NIL) {
			x = x.left;
		}

		return x;
	}

	public Node treeMaximum(Node x) {
		while (x.right != NIL) {
			x = x.right;
		}

		return x;
	}

	public Node treeSearch(Node x, int k) {
		while (x != NIL && k != x.key) {
			if (k < x.key) {
				x = x.left;
			} else {
				x = x.right;
			}
		}

		return x;
	}

	public Node treeSuccessor(Node x) {
		if (x.right != NIL) {
			return treeMinimum(x.right);
		}

		Node y = x.parent;
		while (y != NIL && x == y.right) {
			x = y;
			y = y.parent;
		}

		return y;
	}

	public Node treePredecessor(Node x) {
		if (x.left != NIL) {
			return treeMaximum(x.left);
		}

		Node y = x.parent;
		while (y != NIL && x == y.left) {
			x = y;
			y = y.parent;
		}

		return y;
	}

	public int[] inorderTreeWalk(Node x) {
		List<Integer> output = new ArrayList<>();
		inorderTreeWalk(x, output);
		return output.stream().mapToInt(Integer::intValue).toArray();
	}

	private void inorderTreeWalk(Node x, List<Integer> output) {
		if (x != NIL) {
			inorderTreeWalk(x.left, output);
			output.add(x.key);
			inorderTreeWalk(x.right, output);
		}
	}

	/*
	Utility types
	 */
	private static final class Node {
		public Color color;
		public Node parent;
		public Node left;
		public Node right;
		public int key;

		public Node(Color color) {
			this.color = color;
		}

		public Node(int key) {
			this.key = key;
		}
	}

	private enum Color {
		Black, Red
	}
}
