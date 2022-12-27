public class Stats {

	public static double calcAverage(double[] array) {

		// get the average from an array of numbers
		double average = 0;

		for (int i = 0; i < array.length; i++) {

			average = average + array[i];

		}

		average = average / array.length;

		return average;
	}

	public static double calcSD(double[] array) {
		// get the standard deviation from an array of numbers
		double sD = 0;

		for (int i = 0; i < array.length; i++) {

			sD = sD + Math.pow((array[i] - Stats.calcAverage(array)), 2);

		}

		sD = Math.sqrt(sD / (array.length - 1));

		return sD;
	}

	public static double calcMax(double[] array) {

		// get the max of an array of numbers
		double max = array[0];
		for (int i = 0; i < array.length; i++) {

			if (max < array[i]) {
				max = array[i];
			}
		}
		return max;
	}

	public static double calcMin(double[] array) {

		// get the min of an array of numbers
		double min = array[0];
		for (int i = 0; i < array.length; i++) {

			if (min > array[i]) {
				min = array[i];
			}
		}
		return min;

	}

	public static double calcAverage(int[] array) {

		// get the average from an array of numbers
		double average = 0;

		for (int i = 0; i < array.length; i++) {

			average = average + array[i];

		}

		average = average / array.length;

		return average;
	}

	public static double calcSD(int[] array) {
		// get the standard deviation from an array of numbers
		double sD = 0;

		for (int i = 0; i < array.length; i++) {

			sD = sD + Math.pow((array[i] - Stats.calcAverage(array)), 2);

		}

		sD = Math.sqrt(sD / (array.length - 1));

		return sD;
	}

	public static double calcMax(int[] array) {

		// get the max of an array of numbers
		double max = array[0];
		for (int i = 0; i < array.length; i++) {

			if (max < array[i]) {
				max = array[i];
			}
		}
		return max;
	}

	public static double calcMin(int[] array) {

		// get the min of an array of numbers
		double min = array[0];
		for (int i = 0; i < array.length; i++) {

			if (min > array[i]) {
				min = array[i];
			}
		}
		return min;
	}

	public static double calcAverage(long[] array) {
		
		// get the average from an array of numbers
		double average = 0;

		for (int i = 0; i < array.length; i++) {

			average = average + array[i];

		}

		average = average / array.length;

		return average;
	}

	public static double calcSD(long[] array) {
		
		// get the standard deviation from an array of numbers
		double sD = 0;

		for (int i = 0; i < array.length; i++) {

			sD = sD + Math.pow((array[i] - Stats.calcAverage(array)), 2);

		}

		sD = Math.sqrt(sD / (array.length - 1));

		return sD;
	}

	public static double calcMax(long[] array) {
		
		// get the max of an array of numbers
		double max = array[0];
		for (int i = 0; i < array.length; i++) {

			if (max < array[i]) {
				max = array[i];
			}
		}
		return max;
	}

	public static double calcMin(long[] array) {

		// get the min of an array of numbers
		double min = array[0];
		for (int i = 0; i < array.length; i++) {

			if (min > array[i]) {
				min = array[i];
			}
		}
		return min;

	}
}
