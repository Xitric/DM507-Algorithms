package strassen;

import java.util.Arrays;

/**
 * Implementation of Strassen's algorithm, that assumes matrix sizes of n^2.
 *
 * @author Kasper
 */
public class StrassenMatrixMult {

	public static void main(String[] args) {
		Matrix a = new Matrix(new double[][] {{1, 3, 5, 6},
												{7, 5, 8, 9},
												{1, 6, 8, 5},
												{5, 4, 3, 5}});
		Matrix b = new Matrix(new double[][] {{1, 3, 5, 6},
												{7, 5, 8, 9},
												{1, 6, 8, 5},
												{5, 4, 3, 5}});

		System.out.println("Strassen:");
		System.out.println(strassenMult(a, b));

		System.out.println("\nNormal multiplication:");
		System.out.println("If this does not match with the Strassen result, don't trust this code!");
		System.out.println(mult(a, b));
	}

	public static Matrix strassenMult(Matrix a, Matrix b) {
		//Base case
		if (a.getN() == 1) {
			Matrix c = new Matrix(1);
			c.set(0, 0, a.get(0, 0) * b.get(0, 0));
			return c;
		}

		//Step 1
		Matrix a11 = a.getQuadrant(0, 0);
		Matrix a12 = a.getQuadrant(0, a.getN() / 2);
		Matrix a21 = a.getQuadrant(a.getN() / 2, 0);
		Matrix a22 = a.getQuadrant(a.getN() / 2, a.getN() / 2);

		Matrix b11 = b.getQuadrant(0, 0);
		Matrix b12 = b.getQuadrant(0, b.getN() / 2);
		Matrix b21 = b.getQuadrant(b.getN() / 2, 0);
		Matrix b22 = b.getQuadrant(b.getN() / 2, b.getN() / 2);

		//Step 2
		Matrix s1 = sub(b12, b22);
		Matrix s2 = add(a11, a12);
		Matrix s3 = add(a21, a22);
		Matrix s4 = sub(b21, b11);
		Matrix s5 = add(a11, a22);
		Matrix s6 = add(b11, b22);
		Matrix s7 = sub(a12, a22);
		Matrix s8 = add(b21, b22);
		Matrix s9 = sub(a11, a21);
		Matrix s10 = add(b11, b12);

		//Step 3
		Matrix p1 = strassenMult(a11, s1);
		Matrix p2 = strassenMult(s2, b22);
		Matrix p3 = strassenMult(s3, b11);
		Matrix p4 = strassenMult(a22, s4);
		Matrix p5 = strassenMult(s5, s6);
		Matrix p6 = strassenMult(s7, s8);
		Matrix p7 = strassenMult(s9, s10);

		System.out.println("S matrices, in order of number:");
		System.out.println(s1);
		System.out.println(s2);
		System.out.println(s3);
		System.out.println(s4);
		System.out.println(s5);
		System.out.println(s6);
		System.out.println(s7);
		System.out.println(s8);
		System.out.println(s9);
		System.out.println(s10);

		System.out.println("P matrices, in order of number:");
		System.out.println(p1);
		System.out.println(p2);
		System.out.println(p3);
		System.out.println(p4);
		System.out.println(p5);
		System.out.println(p6);
		System.out.println(p7);

		//Step 4
		Matrix c11 = add(sub(add(p5, p4), p2), p6);
		Matrix c12 = add(p1, p2);
		Matrix c21 = add(p3, p4);
		Matrix c22 = sub(sub(add(p5, p1), p3), p7);

		//Combine
		Matrix c = new Matrix(a.getN());
		c.set(0, 0, c11);
		c.set(0, c.getN() / 2, c12);
		c.set(c.getN() / 2, 0, c21);
		c.set(c.getN() / 2, c.getN() / 2, c22);

		return c;
	}

	public static Matrix mult(Matrix a, Matrix b) {
		int n = a.getN();
		Matrix c = new Matrix(n);

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				double cij = 0;
				for (int k = 0; k < n; k++) {
					cij += a.get(i, k) * b.get(k, j);
				}
				c.set(i, j, cij);
			}
		}

		return c;
	}

	public static Matrix add(Matrix a, Matrix b) {
		int n = a.getN();
		Matrix c = new Matrix(n);

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				c.set(i, j, a.get(i, j) + b.get(i, j));
			}
		}

		return c;
	}

	public static Matrix sub(Matrix a, Matrix b) {
		int n = a.getN();
		Matrix c = new Matrix(n);

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				c.set(i, j, a.get(i, j) - b.get(i, j));
			}
		}

		return c;
	}

	private static final class Matrix {

		private double[][] data;
		private int n;
		private int ro;
		private int co;

		public Matrix(int n) {
			this.data = new double[n][n];
			this.n = n;
		}

		public Matrix(double[][] data) {
			this.data = data;
			this.n = data.length;
		}

		public Matrix(double[][] data, int n, int rowOffset, int colOffset) {
			this.data = data;
			this.n = n;
			this.ro = rowOffset;
			this.co = colOffset;
		}

		public Matrix getQuadrant(int row, int col) {
			int newN = n / 2;
			return new Matrix(data, newN, ro + row, co + col);
		}

		public double get(int row, int col) {
			return data[ro + row][co + col];
		}

		public void set(int row, int col, double value) {
			data[ro + row][co + col] = value;
		}

		public void set(int row, int col, Matrix c) {
			for (int i = 0; i < c.getN(); i++) {
				for (int j = 0; j < c.getN(); j++) {
					data[ro + row + i][co + col + j] = c.get(i, j);
				}
			}
		}

		public int getN() {
			return n;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();

			for (int i = 0; i < n; i++) {
				builder.append(Arrays.toString(Arrays.copyOfRange(data[ro + i], co, co + n)));
				builder.append("\n");
			}

			return builder.toString();
		}
	}
}
