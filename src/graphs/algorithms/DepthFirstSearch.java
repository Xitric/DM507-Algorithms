package graphs.algorithms;

import graphs.AdjacencyList;
import graphs.Vertex;

import java.util.Scanner;

/**
 * @author Kasper
 */
public class DepthFirstSearch {

	private AdjacencyList adj;
	private int time;
	private String startNode;

	public DepthFirstSearch() {
		adj = new AdjacencyList();
		Scanner in = new Scanner(System.in);

		System.out.println("Enter edges in the format:");
		System.out.println("\t\"u v\"");
		System.out.println("Terminate with an empty line");

		String input = null;
		while (!(input = in.nextLine()).isEmpty()) {
			String[] elements = input.split(" ");

			adj.addEdge(elements[0], elements[1], 1);
		}

		System.out.print("Start node: ");
		startNode = in.nextLine();
	}

	public void dfs() {
		//Begin with start node
		for (Vertex u : adj.getVertices()) {
			if (u.identifier.equals(startNode)) {
				dfsVisit(u);
				break;
			}
		}

		//Then continue alphabetically
		for (Vertex u : adj.getVertices()) {
			//If u.color == WHITE
			if (u.d == 0) {
				dfsVisit(u);
			}
		}

		System.out.println(toString());
	}

	public void dfsVisit(Vertex u) {
		u.d = ++time;

		for (Vertex v : adj.getVertices(u)) {
			if (v.d == 0) { //v.color == WHITE
				v.p = u;
				System.out.println("(" + u.identifier + ", " + v.identifier + "): Tree edge");
				dfsVisit(v);
			} else {
				if (v.f == 0) { //v.color == GRAY
					System.out.println("(" + u.identifier + ", " + v.identifier + "): Back edge");
				} else { //v.color == BLACK
					if (u.d < v.d) {
						System.out.println("(" + u.identifier + ", " + v.identifier + "): Forward edge");
					} else {
						System.out.println("(" + u.identifier + ", " + v.identifier + "): Cross edge");
					}
				}
			}
		}

		u.f = ++time;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (Vertex vertex : adj.getVertices()) {
			sb.append(vertex.identifier)
					.append("\td: ").append(vertex.d == 0 ? "NIL" : vertex.d)
					.append("\tf: ").append(vertex.f == 0 ? "NIL" : vertex.f)
					.append("\n");
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		DepthFirstSearch dfs = new DepthFirstSearch();
		dfs.dfs();
	}
}
