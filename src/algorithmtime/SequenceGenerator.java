package algorithmtime;

/**
 * @author Kasper
 */
public class SequenceGenerator {

	//O(n^2)
	public static String getLinearSequence(int n) {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < n; i++) {
			builder.append(i).append(" + ");
		}

		builder.setLength(builder.length() - 3);
		return builder.toString();
	}

	//O(n)
	public static String getExpDecreasingSequence(int n) {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; n > 0; i++) {
			n = (int) (n / Math.pow(2, i));
			builder.append(n).append(" + ");
		}

		builder.setLength(builder.length() - 3);
		return builder.toString();
	}

	public static String getExpIncreasingSequence(int n) {
		StringBuilder builder = new StringBuilder();

		for (int i = 1; i <= n; i *= 2) {
			builder.append(i).append(" + ");
		}

		builder.setLength(builder.length() - 3);
		return builder.toString();
	}

	public static String getLogSequence(int n) {
		StringBuilder builder = new StringBuilder();

		for (int i = 1; i <= n; i++) {
			builder.append((int) Math.ceil(Math.log(i)/Math.log(2))).append(" + ");
		}

		builder.setLength(builder.length() - 3);
		return builder.toString();
	}
}
