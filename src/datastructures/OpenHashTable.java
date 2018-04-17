package datastructures;

import util.HashFunctionFactory;
import util.HashFunctionFactory.HashFunction;
import util.HashFunctionFactory.ProbeHashFunction;

/**
 * @author Kasper
 */
public class OpenHashTable {

	private final Integer[] table;
	private final boolean[] deleted;
	private final ProbeHashFunction h;

	public OpenHashTable(int m, ProbeHashFunction h) {
		table = new Integer[m];
		deleted = new boolean[m];

		this.h = h;
	}

	public void insert(int k) {
		for (int i = 0; i < table.length; i++) {
			int position = h.hash(k, i);

			if (table[position] == null || (table[position] != null && deleted[position])) {
				deleted[position] = false;
				table[position] = k;
				System.out.println("Inserted " + k + " into position " + position);
				return;
			}

			System.out.println("Attempted to insert " + k + " into position " + position + ", but it is occupied");
		}
	}

	public void insertAll(int... kt) {
		for (int k : kt) {
			insert(k);
		}
	}

	public void insert(int index, int k) {
		deleted[index] = false;
		table[index] = k;
	}

	public void delete(int k) {
		for (int i = 0; i < table.length; i++) {
			int position = h.hash(k, i);

			if (table[position] == k && !deleted[position]) {
				deleted[position] = true;
				System.out.println("Deleted " + k + " from position " + position);
				return;
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");

		for (int i = 0; i < table.length; i++) {
			if (deleted[i] || table[i] == null) {
				sb.append("_");
			} else {
				sb.append(table[i]);
			}

			sb.append(",");
		}

		sb.setLength(sb.length() - 1);
		sb.append("]");
		return sb.toString();
	}

	public static void main(String[] args) {
//		buildTable();
//		insertIntoExisting();
//		insertIntoExisting2();
	}

	/**
	 * Build an open-addressed hash table from a set of integers.
	 */
	public static void buildTable() {
		int m = 11;
		int c1 = 1;
		int c2 = 3;

		//Linear probing with the auxiliary hash function:
		//                     h(k) = k
		HashFunction auxiliary = k -> k;
		ProbeHashFunction hLinear = HashFunctionFactory.getLinearProbe(auxiliary, m);

		//Quadratic probing with the auxiliary hash function:
		//  h(k) = k
		ProbeHashFunction hQuadratic = HashFunctionFactory.getQuadraticProbe(auxiliary, c1, c2, m);

		//Double hashing with the auxiliary hash functions:
		//  h1(k) = k
		//  h2(k) = 1 + (k mod (m - 1))
		HashFunction h1 = k -> k;
		HashFunction h2 = k -> 1 + (k % (m - 1));
		ProbeHashFunction hDouble = HashFunctionFactory.getDoubleHash(h1, h2, m);

		//Run hash insertions (quadratic hashing)
		OpenHashTable oht = new OpenHashTable(m, hQuadratic);
		oht.insertAll(10, 22, 31, 4, 15, 28, 17, 88, 59);
		System.out.println();
		System.out.println(oht);
	}

	/**
	 * Insert a new key into an open-addressed hash table with initial elements.
	 */
	public static void insertIntoExisting() {
		int m = 10;
		HashFunction h = k -> k % m;
		ProbeHashFunction linearProbe = HashFunctionFactory.getLinearProbe(h, m);

		//Build hash table from task description
		OpenHashTable oht = new OpenHashTable(m, linearProbe);
		oht.insert(1, 11);
		oht.insert(2, 31);
		oht.insert(4, 24);
		oht.insert(5, 15);
		oht.insert(8, 48);

		oht.insert(4);
	}

	/**
	 * Insert a new key into an open-addressed hash table with initial elements.
	 */
	public static void insertIntoExisting2() {
		int m = 11;
		HashFunction h1 = k -> k % m;
		HashFunction h2 = k -> 1 + (k % (m - 1));
		ProbeHashFunction hDouble = HashFunctionFactory.getDoubleHash(h1, h2, m);

		//Build hash table from task description
		OpenHashTable oht = new OpenHashTable(m, hDouble);
		oht.insert(3, 14);
		oht.insert(7, 3);
		oht.insert(9, 41);

		oht.insert(18);
	}
}
