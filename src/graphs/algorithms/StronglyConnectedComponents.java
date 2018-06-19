package graphs.algorithms;

import graphs.AdjacencyList;
import graphs.Vertex;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * @author Kasper
 */
public class StronglyConnectedComponents {

	private AdjacencyList adj;
	private AdjacencyList adjT;
	private int time;

	public StronglyConnectedComponents() {
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

		adjT = adj.transpose();
	}

	public void dfs() {
		for (Vertex u : adj.getVertices()) {
			//If u.color == WHITE
			if (u.d == 0) {
				dfsVisit(u);
			}
		}
	}

	public void dfsVisit(Vertex u) {
		u.d = ++time;

		for (Vertex v : adj.getVertices(u)) {
			if (v.d == 0) { //v.color == WHITE
				v.p = u;
				dfsVisit(v);
			}
		}

		u.f = ++time;
	}

	public void scc() {
		List<Vertex> vertices = adjT.getVertices();
		vertices.sort(Comparator.comparingInt(v -> -getFinishingTime(v.identifier)));

		System.out.println("Strongly connected components on separate lines:");
		for (Vertex u : vertices) {
			if (u.d == 0) {
				System.out.print(u.identifier + ", ");
				sccVisit(u);
				System.out.println();
			}
		}
	}

	public void sccVisit(Vertex u) {
		u.d = ++time;

		for (Vertex v : adjT.getVertices(u)) {
			if (v.d == 0) { //v.color == WHITE
				System.out.print(v.identifier + ", ");
				v.p = u;
				sccVisit(v);
			}
		}

		u.f = ++time;
	}

	private int getFinishingTime(String v) {
		for (Vertex u : adj.getVertices()) {
			if (u.identifier.equals(v)) {
				return u.f;
			}
		}

		return 0;
	}

	public static void main(String[] args) {
		StronglyConnectedComponents scc = new StronglyConnectedComponents();
		scc.dfs();
		scc.scc();
	}
}
