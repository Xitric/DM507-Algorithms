package algorithmtime;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

/**
 * @author Kasper
 */
public class Invariants {

	//Run with vm argument -ea
	public static void main(String[] args) {
		ensureArguments();
		int n = 64;

		//Algorithm start
		int x = n;
		int r = 0;

		assert 7 * x + r == 7 * n; //Edit this

		while (x > 0) {
			x = x - 1;
			r = r + 7;

			assert 7 * x + r == 7 * n; //Edit this
		}
		//Algorithm end
	}

	private static void ensureArguments() {
		RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
		List<String> arguments = runtimeMxBean.getInputArguments();

		if (! arguments.contains("-ea")) throw new IllegalStateException("You must run this with the vm argument -ea");
	}
}
