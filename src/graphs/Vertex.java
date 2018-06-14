package graphs;

/**
 * @author Kasper
 */
public class Vertex implements Comparable<Vertex> {

	public String identifier;
	public int d;
	public int f;
	public Vertex p;

	public Vertex(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public int compareTo(Vertex other) {
		return identifier.compareTo(other.identifier);
	}
}
