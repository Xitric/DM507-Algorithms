package datastructures;

import util.HashFunctionFactory.HashFunction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Kasper
 */
public class ChainedHashTable {

	private final List<LinkedList<Integer>> table;
	private final HashFunction h;

	public ChainedHashTable(int m, HashFunction h) {
		table = new ArrayList<>();
		for (int i = 0; i < m; i++) table.add(new LinkedList<>());
		this.h = h;
	}

	public void insert(int k) {
		int position = h.hash(k);
		table.get(position).addFirst(k);
	}

	public void insertAll(int... kt) {
		for (int k : kt) {
			insert(k);
		}
	}

	public void insert(int index, int k) {
		table.get(index).addFirst(k);
	}

	public void delete(int k) {
		int position = h.hash(k);
		table.get(position).removeIf(integer -> integer == k);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < table.size(); i++) {
			sb.append(i).append(" [ ] -> ");

			for (int k : table.get(i)) {
				sb.append(" ").append(k).append(",");
			}

			sb.setLength(sb.length() - 1);
			sb.append("\n");
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		int m = 9;

		//Hash function:
		//             h(k) = k mod m
		HashFunction h = k -> k % m;

		//Universal hash:
		//             h(k) = ((a * k + b) mod p) mod m
//		HashFunction h = k -> ((a * k + b) % p) % m;

		ChainedHashTable cht = new ChainedHashTable(m, h);
		cht.insertAll(5, 28, 19, 15, 20, 33, 12, 17, 10);
		System.out.println(cht);

//		cht.delete(19);
//		System.out.println(cht);
	}
}
