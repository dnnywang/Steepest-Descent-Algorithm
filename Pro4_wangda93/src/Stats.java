import java.util.Arrays;

public class Stats {
	
	public static double calcAverage(double[] array) {
		
		double average = 0;
		
		for(int i = 0; i < array.length; i++) {
			
			average = average + array[i];
			
		}
		
		average = average / array.length;
		
		return average;
	}
	
	public static double calcSD(double[] array) {
		
		double sD = 0;
		
		for(int i = 0; i < array.length; i++) {
			
			sD = sD + Math.pow((array[i] - Stats.calcAverage(array)), 2);
			
		}
		
		sD = Math.sqrt(sD / (array.length - 1));
		
		return sD;
	}
	
	public static double calcMax(double[] array) {
		
		double max = array[0];
        for(int i = 0; i < array.length; i++) {
        	
            if(max < array[i]) {
                max = array[i];
            }
        }
        return max;
	}
	
	public static double calcMin(double[] array) {
		
		double min = array[0];
        for(int i = 0; i < array.length; i++) {
        	
            if(min > array[i]) {
                min = array[i];
            }
        }
        return min;
		
	}
	
	public static double calcAverage(int[] array) {
		
		double average = 0;
		
		for(int i = 0; i < array.length; i++) {
			
			average = average + array[i];
			
		}
		
		average = average / array.length;
		
		return average;
	}
	
	public static double calcSD(int[] array) {
		
		double sD = 0;
		
		for(int i = 0; i < array.length; i++) {
			
			sD = sD + Math.pow((array[i] - Stats.calcAverage(array)), 2);
			
		}
		
		sD = Math.sqrt(sD / (array.length - 1));
		
		return sD;
	}
	
	public static double calcMax(int[] array) {
		
		double max = array[0];
        for(int i = 0; i < array.length; i++) {
        	
            if(max < array[i]) {
                max = array[i];
            }
        }
        return max;
	}
	
	public static double calcMin(int[] array) {
		
		double min = array[0];
        for(int i = 0; i < array.length; i++) {
        	
            if(min > array[i]) {
                min = array[i];
            }
        }
        return min;
	}
	
	public static double calcAverage(long[] array) {
		
		double average = 0;
		
		for(int i = 0; i < array.length; i++) {
			
			average = average + array[i];
			
		}
		
		average = average / array.length;
		
		return average;
	}
	
	public static double calcSD(long[] array) {
		
		double sD = 0;
		
		for(int i = 0; i < array.length; i++) {
			
			sD = sD + Math.pow((array[i] - Stats.calcAverage(array)), 2);
			
		}
		
		sD = Math.sqrt(sD / (array.length - 1));
		
		return sD;
	}
	
	public static double calcMax(long[] array) {
		
		double max = array[0];
        for(int i = 0; i < array.length; i++) {
        	
            if(max < array[i]) {
                max = array[i];
            }
        }
        return max;
	}
	
	public static double calcMin(long[] array) {
		
		double min = array[0];
        for(int i = 0; i < array.length; i++) {
        	
            if(min > array[i]) {
                min = array[i];
            }
        }
        return min;
		
	}
}
