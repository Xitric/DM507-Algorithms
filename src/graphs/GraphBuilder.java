package graphs;

/**
 * @author Kasper
 */
public interface GraphBuilder {

	/**
	 * Set the number of nodes that this graph builder should consider. This will reset the builder.
	 *
	 * @param nodes the number of nodes in the graph
	 */
	void setNodeCount(int nodes);

	/**
	 * Add an edge from node u to node v with the weight w.
	 *
	 * @param u the node that the edge is incident from
	 * @param v the node that the edge is incident to
	 * @param w the weight of the edge
	 */
	void addEdge(String u, String v, int w);
}
