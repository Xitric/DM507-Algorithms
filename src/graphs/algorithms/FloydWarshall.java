package graphs.algorithms;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Kasper
 */
public class FloydWarshall {

	private static final boolean DIRECTED = true;

	public void floydWarshall(int[][] d0, int[][] p0) {
		int n = d0.length;
		int[][][] d = new int[n + 1][][];
		d[0] = d0;

		int[][][] p = new int[n + 1][][];
		p[0] = p0;

		for (int k = 1; k <= n; k++) {
			d[k] = new int[n][n];
			p[k] = new int[n][n];

			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					//Update distances
					d[k][i][j] = Math.min(d[k - 1][i][j], sum(d[k - 1][i][k - 1], d[k - 1][k - 1][j]));

					//Update predecessors
					if (d[k - 1][i][j] <= sum(d[k - 1][i][k - 1], d[k - 1][k - 1][j])) {
						p[k][i][j] = p[k - 1][i][j];
					} else {
						p[k][i][j] = p[k - 1][k - 1][j];
					}
				}
			}
		}

		//Print resulting matrices
		for (int num = 0; num <= n; num++) {
			System.out.println("D^" + num);
			System.out.println("Distances");
			printD(d[num]);
			System.out.println("Predecessors");
			printP(p[num]);
			System.out.println();
		}
	}

	private int sum(int a, int b) {
		if (a == Integer.MAX_VALUE || b == Integer.MAX_VALUE) {
			return Integer.MAX_VALUE;
		}

		return a + b;
	}

	private void printD(int[][] matrix) {
		for (int[] row : matrix) {
			for (int col : row) {
				if (col == Integer.MAX_VALUE) {
					System.out.print("∞");
				} else {
					System.out.print(col);
				}

				System.out.print(", ");
			}
			System.out.println();
		}
	}

	private void printP(int[][] matrix) {
		for (int[] row : matrix) {
			for (int col : row) {
				if (col == -1) {
					System.out.print("NIL");
				} else {
					System.out.print(col);
				}

				System.out.print(", ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		FloydWarshallBuilder builder = new FloydWarshallBuilder();

		System.out.print("Vertex count: ");
		builder.setNodeCount(Integer.parseInt(in.nextLine()));

		System.out.println("Enter edges in the format:");
		System.out.println("\t\"u v weight\"");
		System.out.println("Terminate with an empty line");

		String input = null;
		while (!(input = in.nextLine()).isEmpty()) {
			String[] elements = input.split(" ");

			builder.addEdge(elements[0], elements[1], Integer.parseInt(elements[2]));
			if (! DIRECTED) builder.addEdge(elements[1], elements[0], Integer.parseInt(elements[2]));
		}

		FloydWarshall fw = new FloydWarshall();
		fw.floydWarshall(builder.getDMatrix(), builder.getPMatrix());
	}

	private static class FloydWarshallBuilder {

		private int[][] dMatrix;
		private int[][] pMatrix;

		public int[][] getDMatrix() {
			return dMatrix;
		}

		public int[][] getPMatrix() {
			return pMatrix;
		}

		public void setNodeCount(int nodes) {
			pMatrix = new int[nodes][nodes];
			dMatrix = new int[nodes][nodes];

			//Distance matrix
			//The diagonal should be zeros, the rest is MAX_VALUE to indicate infinity
			for (int i = 0; i < nodes; i++) {
				for (int j = 0; j < nodes; j++) {
					if (i != j) {
						dMatrix[i][j] = Integer.MAX_VALUE;
					}
				}
			}

			//Predecessor matrix
			//Initially all cells are NIL, here identified using the value -1
			for (int i = 0; i < nodes; i++) {
				Arrays.fill(pMatrix[i], -1);
			}
		}

		public void addEdge(String u, String v, int w) {
			int i = 0;
			int j = 0;

			try {
				i = Integer.parseInt(u) - 1;
				j = Integer.parseInt(v) - 1;
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("For the Floyd-Warshall algorithm node names must be integers");
			}

			dMatrix[i][j] = w;
			pMatrix[i][j] = i + 1;
		}
	}
}
