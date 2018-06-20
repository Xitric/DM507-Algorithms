package sets;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kasper
 */
public class DisjointSetList {

	private int length;
	private Node head;
	private Node tail;

	public DisjointSetList(Object element) {
		Node node = new Node(element);
		node.representative = this;

		head = node;
		tail = node;
		length = 1;
	}

	public static Node makeSet(Object x) {
		return new DisjointSetList(x).head;
	}

	public static Node union(Node x, Node y) {
		Node left = y;
		Node right = x;

		if (x.representative.length > y.representative.length) {
			left = x;
			right = y;
		}

		System.out.println("Appended " + right.representative.head.content + " after " + left.representative.tail.content);

		left.representative.tail.next = right.representative.head;
		left.representative.tail = right.representative.tail;
		left.representative.length += right.representative.length;

		Node next = right;
		while (next != null) {
			next.representative = left.representative;
			next = next.next;
		}

		return left;
	}

	public static DisjointSetList findSet(Node x) {
		return x.representative;
	}

	public static class Node {

		public Object content;
		public DisjointSetList representative;
		public Node next;

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
		union(nodes.get(b), nodes.get(a));
		union(nodes.get(b), nodes.get(c));
		union(nodes.get(e), nodes.get(d));
		union(nodes.get(e), nodes.get(c));
		union(nodes.get(g), nodes.get(f));
		union(nodes.get(e), nodes.get(g));

		//Print parent-child relationships
		Node next = nodes.get(a).representative.head;
		while (next != null) {
			System.out.print(next.content);
			next = next.next;

			if (next != null) {
				System.out.print(" -> ");
			}
		}
	}
}
