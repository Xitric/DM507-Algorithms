package graphs.algorithms;

import graphs.SingleSourceAlgorithm;
import graphs.Vertex;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

/**
 * @author Kasper
 */
public class Dijkstra extends SingleSourceAlgorithm {

	private static final boolean DIRECTED = true;
	private Vertex s;

	public Dijkstra() {
		super();
		Scanner in = new Scanner(System.in);

		System.out.println("Enter edges in the format:");
		System.out.println("\t\"u v weight\"");
		System.out.println("Terminate with an empty line");

		String input = null;
		while (!(input = in.nextLine()).isEmpty()) {
			String[] elements = input.split(" ");

			adj.addEdge(elements[0], elements[1], Integer.parseInt(elements[2]));
			if (! DIRECTED) adj.addEdge(elements[1], elements[0], Integer.parseInt(elements[2]));
		}

		System.out.print("Source vertex: ");
		s = adj.toVertex(in.nextLine());
	}

	public void dijkstra() {
		initializeSingleSource(s);
		Queue<Vertex> q = new PriorityQueue<>(new DijkstraVertexComparator());
		q.addAll(adj.getVertices());

		while (!q.isEmpty()) {
			Vertex u = q.poll();
			System.out.println("Pulled " + u.identifier + " from queue.");

			for (Vertex v : adj.getVertices(u)) {
				relax(u, v, adj.getWeight(u, v));
			}

			q = updateQueue(q);
		}

		System.out.println(toString());
	}

	private Queue<Vertex> updateQueue(Queue<Vertex> original) {
		Queue<Vertex> q = new PriorityQueue<>(new DijkstraVertexComparator());

		for (Vertex vertex : adj.getVertices()) {
			if (original.contains(vertex)) {
				q.add(vertex);
			}
		}

		return q;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (Vertex vertex : adj.getVertices()) {
			sb.append(vertex.identifier)
					.append("\td: ").append(vertex.d == Integer.MAX_VALUE ? "∞" : vertex.d)
					.append("\tp: ").append(vertex.p == null ? "NIL" : vertex.p.identifier)
					.append("\n");
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		Dijkstra d = new Dijkstra();
		d.dijkstra();
	}

	private static class DijkstraVertexComparator implements Comparator<Vertex> {

		@Override
		public int compare(Vertex a, Vertex b) {
			return Integer.compare(a.d, b.d);
		}
	}
}
