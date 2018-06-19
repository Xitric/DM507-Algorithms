package graphs.algorithms;

import graphs.Edge;
import graphs.SingleSourceAlgorithm;
import graphs.Vertex;

import java.util.Scanner;

/**
 * @author Kasper
 */
public class BellmanFord extends SingleSourceAlgorithm {

	private static final boolean DIRECTED = true;
	private Vertex s;

	public BellmanFord() {
		super();
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

		System.out.print("Source vertex: ");
		s = adj.toVertex(in.nextLine());
	}

	public boolean bellmanFord() {
		initializeSingleSource(s);

		System.out.println("Initialize");
		System.out.println(toString());

		//Make |G.V| - 1 passes
		for (int i = 1; i < adj.getVertexCount(); i++) {
			for (Edge edge : adj.getEdges()) {
				relax(edge.u, edge.v, edge.w);
			}

			System.out.println("Pass " + i);
			System.out.println(toString());
		}

		for (Edge edge : adj.getEdges()) {
			if (edge.u.d != Integer.MAX_VALUE && edge.v.d > edge.u.d + edge.w) {
				return false;
			}
		}

		return true;
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
		BellmanFord bf = new BellmanFord();

		System.out.println("Success: " + bf.bellmanFord());
	}
}
