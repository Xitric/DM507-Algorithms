package util;

/**
 * @author Kasper
 */
public class HashFunctionFactory {

	public static ProbeHashFunction getLinearProbe(HashFunction h, int m) {
		return (k, i) -> (h.hash(k) + i) % m;
	}

	public static ProbeHashFunction getQuadraticProbe(HashFunction h, int c1, int c2, int m) {
		return (k, i) -> (h.hash(k) + c1 * i + c2 * i * i) % m;
	}

	public static ProbeHashFunction getDoubleHash(HashFunction h1, HashFunction h2, int m) {
		return (k, i) -> (h1.hash(k) + i * h2.hash(k)) % m;
	}

	public interface HashFunction {

		int hash(int k);
	}

	public interface ProbeHashFunction {

		int hash(int k, int i);
	}
}
