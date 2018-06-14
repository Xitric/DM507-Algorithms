package graphs;

/**
 * @author Kasper
 */
public class Edge implements Comparable<Edge> {

	public Vertex u;
	public Vertex v;
	public int w;

	public Edge(Vertex u, Vertex v, int w) {
		this.u = u;
		this.v = v;
		this.w = w;
	}

	@Override
	public int compareTo(Edge other) {
		int result = u.compareTo(other.u);
		if (result == 0) {
			result = v.compareTo(other.v);
		}

		return result;
	}
}
