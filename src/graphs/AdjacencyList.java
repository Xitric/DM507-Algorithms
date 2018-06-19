package graphs;

import java.util.*;

/**
 * @author Kasper
 */
public class AdjacencyList {

	private Map<Vertex, Map<Vertex, Integer>> adj;
	private Map<String, Vertex> vertices;

	public AdjacencyList() {
		adj = new HashMap<>();
		vertices = new HashMap<>();
	}

	public void addEdge(String u, String v, int w) {
		Vertex uVert = toVertexInternal(u);
		Vertex vVert = toVertexInternal(v);

		Map<Vertex, Integer> uMap = adj.computeIfAbsent(uVert, k -> new HashMap<>());
		uMap.put(vVert, w);
	}

	public int getVertexCount() {
		return vertices.size();
	}

	public Vertex toVertex(String identifier) {
		return vertices.get(identifier);
	}

	private Vertex toVertexInternal(String identifier) {
		Vertex vert = toVertex(identifier);
		if (vert == null) {
			vert = new Vertex(identifier);
			vertices.put(identifier, vert);
		}

		return vert;
	}

	public List<Vertex> getVertices() {
		List<Vertex> result = new ArrayList<>(vertices.values());
		Collections.sort(result);
		return result;
	}

	public List<Vertex> getVertices(Vertex u) {
		Map<Vertex, Integer> adjacent = adj.get(u);
		if (adjacent == null) {
			return new ArrayList<>();
		}

		List<Vertex> result = new ArrayList<>(adjacent.keySet());
		Collections.sort(result);
		return result;
	}

	public List<Edge> getEdges() {
		List<Edge> result = new ArrayList<>();

		for (Vertex u : adj.keySet()) {
			for (Vertex v : adj.get(u).keySet()) {
				result.add(new Edge(u, v, adj.get(u).get(v)));
			}
		}

		Collections.sort(result);
		return result;
	}

	public int getWeight(Vertex u, Vertex v) {
		return adj.get(u).get(v);
	}

	public AdjacencyList transpose() {
		AdjacencyList adjT = new AdjacencyList();

		for (Vertex v : getVertices()) {
			for (Vertex u : getVertices(v)) {
				adjT.addEdge(u.identifier, v.identifier, getWeight(v, u));
			}
		}

		return adjT;
	}
}
