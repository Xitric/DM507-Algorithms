package util;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * @author Kasper
 */
public class PermutationGenerator {

	public static int[] getRandomPermutation(int count) {
		return new Random().ints(0, count).distinct().limit(count).boxed().mapToInt(Integer::intValue).toArray();
	}

	public static int[] getSortedPermutation(int count) {
		return IntStream.range(0, count).toArray();
	}

	public static int[] getInverseSortedPermutation(int count) {
		return IntStream.range(0, count).map(i -> count - i - 1).toArray();
	}
}
