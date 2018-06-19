package graphs.algorithms;

import graphs.AdjacencyList;
import graphs.Vertex;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

/**
 * @author Kasper
 */
public class Prim {

	private static final boolean DIRECTED = true;
	protected AdjacencyList adj;
	private Vertex r;

	public Prim() {
		adj = new AdjacencyList();
		Scanner in = new Scanner(System.in);

		System.out.println("Enter edges in the format:");
		System.out.println("\t\"u v weight\"");
		System.out.println("Terminate with an empty line");

		String input = null;
		while (!(input = in.nextLine()).isEmpty()) {
			String[] elements = input.split(" ");

			adj.addEdge(elements[0], elements[1], Integer.parseInt(elements[2]));
			if (!DIRECTED) adj.addEdge(elements[1], elements[0], Integer.parseInt(elements[2]));
		}

		System.out.print("Root vertex: ");
		r = adj.toVertex(in.nextLine());
	}

	public void prim() {
		for (Vertex vertex : adj.getVertices()) {
			vertex.key = Integer.MAX_VALUE;
		}

		r.key = 0;
		Queue<Vertex> q = new PriorityQueue<>(new KeyVertexComparator());
		q.addAll(adj.getVertices());

		while (! q.isEmpty()) {
			Vertex u = q.poll();

			for (Vertex v : adj.getVertices(u)) {
				if (q.contains(v) && adj.getWeight(u, v) < v.key) {
					v.p = u;
					v.key = adj.getWeight(u, v);
					q = updateQueue(q);
				}
			}
		}

		//Print result
		int weight = 0;

		System.out.println("The MST consists of the following edges:");
		for (Vertex v : adj.getVertices()) {
			if (v == r) continue;
			System.out.println("(" + v.identifier + ", " + v.p.identifier + ")");
			weight += adj.getWeight(v, v.p);
		}

		System.out.println("Total price: " + weight);
	}

	private Queue<Vertex> updateQueue(Queue<Vertex> original) {
		Queue<Vertex> q = new PriorityQueue<>(new KeyVertexComparator());

		for (Vertex vertex : adj.getVertices()) {
			if (original.contains(vertex)) {
				q.add(vertex);
			}
		}

		return q;
	}

	private static class KeyVertexComparator implements Comparator<Vertex> {

		@Override
		public int compare(Vertex a, Vertex b) {
			int result = Integer.compare(a.key, b.key);

			if (result == 0) {
				result = a.identifier.compareTo(b.identifier);
			}

			return result;
		}
	}

	public static void main(String[] args) {
		Prim prim = new Prim();
		prim.prim();
	}
}
