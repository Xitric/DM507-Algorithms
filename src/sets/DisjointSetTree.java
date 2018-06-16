package sets;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kasper
 */
public class DisjointSetTree {

	private static boolean pathCompression = true;

	public static Node makeSet(Object x) {
		Node node = new Node(x);
		node.p = node;
		node.rank = 0;
		return node;
	}

	public static Node union(Node x, Node y) {
		return link(findSet(x), findSet(y));
	}

	public static Node link(Node x, Node y) {
		if (x.rank > y.rank) {
			y.p = x;
			return x;
		} else {
			x.p = y;
			if (x.rank == y.rank) {
				y.rank++;
			}
			return y;
		}
	}

	public static Node findSet(Node x) {
		if (x != x.p) {
			Node parent = findSet(x.p);

			if (pathCompression) {
				x.p = parent;
			} else {
				return parent;
			}
		}
		return x.p;
	}

	public static class Node {

		public Object content;
		public Node p;
		public int rank;

		public Node(Object content) {
			this.content = content;
		}
	}

	public static void main(String[] args) {
		//Make the nodes
		List<Node> nodes = new ArrayList<>();
		nodes.add(makeSet("A")); int a = 0;
		nodes.add(makeSet("B")); int b = 1;
		nodes.add(makeSet("C")); int c = 2;
		nodes.add(makeSet("D")); int d = 3;
		nodes.add(makeSet("E")); int e = 4;
		nodes.add(makeSet("F")); int f = 5;
		nodes.add(makeSet("G")); int g = 6;

		//Perform union as specified in the task description.
		//REMEMBER TO TOGGLE WHETHER TO USE PATH COMPRESSION ON LINE 11
		union(nodes.get(b), nodes.get(a));
		union(nodes.get(b), nodes.get(c));
		union(nodes.get(e), nodes.get(d));
		union(nodes.get(e), nodes.get(c));
		union(nodes.get(g), nodes.get(f));
		union(nodes.get(e), nodes.get(g));

		//Print parent-child relationships
		for (Node node : nodes) {
			System.out.println(node.content + " parent: " + node.p.content);
		}
	}
}
