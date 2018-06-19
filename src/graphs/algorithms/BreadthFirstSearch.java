package graphs.algorithms;

import graphs.AdjacencyList;
import graphs.Vertex;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * @author Kasper
 */
public class BreadthFirstSearch {

	private static final boolean DIRECTED = true;
	private AdjacencyList adj;
	private String startNode;

	public BreadthFirstSearch() {
		adj = new AdjacencyList();
		Scanner in = new Scanner(System.in);

		System.out.println("Enter edges in the format:");
		System.out.println("\t\"u v\"");
		System.out.println("Terminate with an empty line");

		String input = null;
		while (!(input = in.nextLine()).isEmpty()) {
			String[] elements = input.split(" ");

			adj.addEdge(elements[0], elements[1], 1);
			if (! DIRECTED) adj.addEdge(elements[1], elements[0], 1);
		}

		System.out.print("Start node: ");
		startNode = in.nextLine();
	}

	public void bfs() {
		for (Vertex u : adj.getVertices()) {
			u.d = Integer.MAX_VALUE;
		}

		Queue<Vertex> q = new LinkedList<>();

		//Begin with start node
		for (Vertex u : adj.getVertices()) {
			if (u.identifier.equals(startNode)) {
				u.d = 0;
				q.add(u);
				break;
			}
		}

		while (! q.isEmpty()) {
			Vertex u = q.poll();
			System.out.println("Pulled " + u.identifier + " from queue.");

			for (Vertex v : adj.getVertices(u)) {
				if (v.d == Integer.MAX_VALUE) {
					v.d = u.d + 1;
					v.p = u;
					q.add(v);
				}
			}
		}

		System.out.println(toString());
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
		BreadthFirstSearch bfs = new BreadthFirstSearch();
		bfs.bfs();
	}
}
