package graphs;

/**
 * @author Kasper
 */
public abstract class SingleSourceAlgorithm {

	protected AdjacencyList adj;

	public SingleSourceAlgorithm() {
		adj = new AdjacencyList();
	}

	public void initializeSingleSource(Vertex s) {
		for (Vertex vertex : adj.getVertices()) {
			vertex.d = Integer.MAX_VALUE;
			vertex.p = null;
		}

		s.d = 0;
	}

	public void relax(Vertex u, Vertex v, int w) {
		//Hack to support positive "infinity"
		if (u.d == Integer.MAX_VALUE) {
			return;
		}

		if (v.d > u.d + w) {
			v.d = u.d + w;
			v.p = u;
		}
	}
}
