package algorithmtime;

/**
 * @author Kasper
 */
public class Counter {

	private int c;
	private StringBuilder builder = new StringBuilder();

	public void count() {
		c++;
	}

	public void reset() {
		builder.append(c).append(" + ");
		c = 0;
	}

	public int getCount() {
		return c;
	}

	@Override
	public String toString() {
		builder.setLength(builder.length() - 3);
		return builder.toString();
	}
}
