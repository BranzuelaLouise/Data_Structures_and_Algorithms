import java.util.Arrays;
import java.util.Random;

public class Main {

	public static void selection_sort(int[] array) {

		int n = array.length;
		for (int pos = 0; pos < n - 1; pos++) {
			int smallestIndex = pos;
			int smallest = array[pos];

			for (int i = pos + 1; i < n; i++) {
				if (array[i] < smallest) {
					smallestIndex = i;
					smallest = array[i];
				}
			}

			array[smallestIndex] = array[pos];
			array[pos] = smallest;

		}
	}

	// This algorithm is making (n-1) + (n-2) + ... + 1
	// comparisons, therefore the growth function is
	// (1/2)n^2 - (1/2)n which means the order of the algorithm is
	// O(n^2) since the biggest power of the growth function is n^2

	public static void main(String[] args) {

		int MAX = 5;
		int[] numbers = new int[MAX];

		Random rand = new Random();
		for (int i = 0; i < numbers.length; i++)
			numbers[i] = rand.nextInt(100);

		System.out.println("Random numbers were ");
		System.out.println(Arrays.toString(numbers));

		selection_sort(numbers);

		System.out.println("Sorted numbers are ");
		System.out.println(Arrays.toString(numbers));

	}
}
