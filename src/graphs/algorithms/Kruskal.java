package graphs.algorithms;

import graphs.AdjacencyList;
import graphs.Edge;
import graphs.Vertex;
import sets.DisjointSetTree;

import java.util.*;

/**
 * @author Kasper
 */
public class Kruskal {

	private static final boolean DIRECTED = true;
	protected AdjacencyList adj;

	public Kruskal() {
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
	}

	public void kruskal() {
		Map<Vertex, DisjointSetTree.Node> nodes = new HashMap<>();
		for (Vertex v : adj.getVertices()) {
			nodes.put(v, DisjointSetTree.makeSet(v));
		}

		List<Edge> edges = adj.getEdges();
		edges.sort(Comparator.comparingInt(Edge::getWeight));

		int weight = 0;
		boolean skipFlip = true;

		System.out.println("The MST consists of the following edges:");
		for (Edge edge : edges) {
			if (DisjointSetTree.findSet(nodes.get(edge.u)) != DisjointSetTree.findSet(nodes.get(edge.v))) {
				edge.v.p = edge.u;
				System.out.println("(" + edge.u.identifier + ", " + edge.v.identifier + ")");
				weight += adj.getWeight(edge.v, edge.u);
				DisjointSetTree.union(nodes.get(edge.u), nodes.get(edge.v));
			} else {
				if (edge.u.p != edge.v) {
					if (skipFlip) System.out.println("Skipped edge + (" + edge.u.identifier + ", " + edge.v.identifier + ")");
					skipFlip = !skipFlip;
				}
			}
		}

		System.out.println("Total price: " + weight);
	}

	public static void main(String[] args) {
		Kruskal kruskal = new Kruskal();
		kruskal.kruskal();
	}
}
