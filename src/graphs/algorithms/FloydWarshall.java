package graphs.algorithms;

import graphs.GraphBuilder;

import java.util.Scanner;

/**
 * @author Kasper
 */
public class FloydWarshall {

	public void floydWarshall(int[][] w) {
		int n = w.length;
		int[][][] d = new int[n + 1][][];
		d[0] = w;

		for (int k = 1; k <= n; k++) {
			d[k] = new int[n][n];

			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					d[k][i][j] = Math.min(d[k - 1][i][j], sum(d[k - 1][i][k - 1], d[k - 1][k - 1][j]));
				}
			}
		}

		//Print resulting matrices
		for (int num = 0; num <= n; num++) {
			System.out.println("D^" + num);
			print(d[num]);
			System.out.println();
		}
	}

	private int sum(int a, int b) {
		if (a == Integer.MAX_VALUE || b == Integer.MAX_VALUE) {
			return Integer.MAX_VALUE;
		}

		return a + b;
	}

	private void print(int[][] matrix) {
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
		}

		FloydWarshall fw = new FloydWarshall();
		fw.floydWarshall(builder.getMatrix());
	}

	private static class FloydWarshallBuilder implements GraphBuilder {

		private int[][] matrix;

		public int[][] getMatrix() {
			return matrix;
		}

		@Override
		public void setNodeCount(int nodes) {
			matrix = new int[nodes][nodes];

			//The diagonal should be zeros, the rest is MAX_VALUE to indicate infinity
			for (int i = 0; i < nodes; i++) {
				for (int j = 0; j < nodes; j++) {
					if (i != j) {
						matrix[i][j] = Integer.MAX_VALUE;
					}
				}
			}
		}

		@Override
		public void addEdge(String u, String v, int w) {
			int i = 0;
			int j = 0;

			try {
				i = Integer.parseInt(u) - 1;
				j = Integer.parseInt(v) - 1;
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("For the Floyd-Warshall algorithm node names must be integers");
			}

			matrix[i][j] = w;
		}
	}
}
