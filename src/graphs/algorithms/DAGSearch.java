package graphs.algorithms;

import graphs.SingleSourceAlgorithm;
import graphs.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Kasper
 */
public class DAGSearch extends SingleSourceAlgorithm {

	private List<String> order;
	private String startNode;

	public DAGSearch() {
		super();
		Scanner in = new Scanner(System.in);

		System.out.println("Enter edges in the format:");
		System.out.println("\t\"u v weight\"");
		System.out.println("Terminate with an empty line");

		String input = null;
		while (!(input = in.nextLine()).isEmpty()) {
			String[] elements = input.split(" ");

			adj.addEdge(elements[0], elements[1], Integer.parseInt(elements[2]));
		}

		System.out.println("Vertex order, comma separated:");
		String stringOrder = in.nextLine();
		String[] orderElements = stringOrder.split(",");
		order = new ArrayList<>();
		for (String s: orderElements) {
			s = s.trim();
			order.add(s);
		}

		System.out.print("Start node: ");
		startNode = in.nextLine();
	}

	public void dag() {
		initializeSingleSource(adj.toVertex(startNode));

		for (String next : order) {
			Vertex u = adj.toVertex(next);

			for (Vertex v : adj.getVertices(u)) {
				relax(u, v, adj.getWeight(u, v));
			}

			System.out.println("\nRelax from " + next);
			System.out.println(toString());
		}
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
		DAGSearch dag = new DAGSearch();
		dag.dag();
	}
}
