import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Pro3_wangda93 {
	
	static BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
	static Polynomial polyFunc = new Polynomial();
	static SteepestDescent algorithm = new SteepestDescent();
	
	public static void main(String[] args) throws IOException {
		
		boolean flag = true; 
		
		do { //keep looping menu as long as flag is true
			displayMenu();
			String selection = cin.readLine();
			
			if (selection.equals("E") || selection.equals("e")) {
				
				// get the polynomial parameters from the user
				
				getPolynomialDetails(polyFunc);
				
			}
			
			else if (selection.equals("F") || selection.equals("f")) {
				
				// check if polynomial is entered, then print the parameters
				
				if (polyFunc.isSet() == true) {
					
					polyFunc.print();
					
				}
					
				else {
					
					System.out.println("\nERROR: Polynomial function has not been entered!\n");
					
				}
				
			}
			
			else if (selection.equals("S") || selection.equals("s")) {
				
				// set algorithm parameters 
				
				if (polyFunc.isSet() == true ) {
					
					getAlgorithmParams(algorithm, polyFunc.getN());
					
				}
				
				else {
					
					System.out.println("\nERROR: Polynomial function has not been entered!\n");
					
				}
				
			}
			
			else if (selection.equals("P") || selection.equals("p")) {
				
				// print algorithm parameters
				
				algorithm.print();
				
			}
			
			else if (selection.equals("R") || selection.equals("r")) {
				
				// run algorithm
				
				if (polyFunc.isSet() == true ) {
					
					algorithm.run(polyFunc);
					
				}
				
				else {
					
					System.out.println("\nERROR: Polynomial function has not been entered!\n");
					
				}
			}
			
			else if (selection.equals("D") || selection.equals("d")) {
				
				// display algorithm results
				
				if (algorithm.getResultsStatus() == false) {
					
					System.out.println("\nERROR: No results exist!\n");
					
				}
				
				else {
					System.out.print("\n");
					algorithm.printResults(false);
					System.out.print("\n");
				}
			}
			
			else if (selection.equals("Q") || selection.equals("q")) {
				
				// quit the program
				
				flag = false; 
				System.out.println("\nThe end.");
				
			}
			
			else {
				
				System.out.println("\nERROR: Invalid menu choice!\n");
				
			}
					
		} while(flag);
		
		
		
	}
	
	public static void displayMenu() {
		
		// print the menu
		
		System.out.println("   JAVA POLYNOMIAL MINIMIZER (STEEPEST DESCENT)");
		System.out.println("E - Enter polynomial function");
		System.out.println("F - View polynomial function");
		System.out.println("S - Set steepest descent parameters");
		System.out.println("P - View steepest descent parameters");
		System.out.println("R - Run steepest descent algorithm");
		System.out.println("D - Display algorithm performance");
		System.out.println("Q - Quit\n");
		System.out.print("Enter choice: ");
	
	}
	
	public static boolean getPolynomialDetails(Polynomial P) throws IOException {
		
		// get polynomial parameters from the user
		
		System.out.print("\n");
		
		int numVariables = getInteger("Enter number of variables (0 to cancel): ", 0, Integer.MAX_VALUE);
		
		// if cancelled, return false
		if (numVariables == 0) { 
			System.out.println("\nProcess canceled. No changes made to polynomial function.\n");
			return false; 
		}
		
		int pDegree = getInteger("Enter polynomial degree (0 to cancel): ", 0, Integer.MAX_VALUE);
		
		// if cancelled return false
		if (pDegree == 0) { 
			System.out.println("\nProcess canceled. No changes made to polynomial function.\n");
			return false; 
		}
		
		P.setN(numVariables);
		P.setDegree(pDegree);
		P.init();
		
		// loop through coefficient array, prompt user 1 index at a time
		
		for (int var_i = 0; var_i < numVariables; var_i++) {
			
			System.out.println("Enter coefficients for variable x" + (var_i + 1) + ": "); // add one to account for 0th index
			for (int coefs_i = 0; coefs_i < pDegree + 1; coefs_i++ ) {
				
				double tempValue = getDouble("   Coefficient " + (coefs_i + 1) + ": ", -1 * Double.MAX_VALUE, Double.MAX_VALUE); // add one to account for 0th index
				P.setCoef(var_i, coefs_i, tempValue);
			}	
		}
		
		System.out.println("\nPolynomial complete!\n");
		
		return true;
	}
	
	public static boolean getAlgorithmParams(SteepestDescent SD, int n) throws IOException {
		
		// get parameters for the steepest descent algorithm	
		
		System.out.print("\n");
		
		double temp_eps = Pro3_wangda93.getDouble("Enter tolerance epsilon (0 to cancel): ", 0, Double.MAX_VALUE);
		
		//if cancelled return false
		if (temp_eps == 0) { 
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false; 
		}
		
		int temp_maxIter = Pro3_wangda93.getInteger("Enter maximum number of iterations (0 to cancel): ", 0, 10000);
		
		//if cancelled return false
		if (temp_maxIter == 0) {
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false; 
		}
		
		double temp_stepSize = Pro3_wangda93.getDouble("Enter step size alpha (0 to cancel): ", 0, Double.MAX_VALUE);
		
		//if cancelled return false
		if (temp_stepSize == 0) { 
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false; 
		}
		
		SD.setEps(temp_eps);
		SD.setMaxIter(temp_maxIter);
		SD.setStepSize(temp_stepSize);
		SD.init(n);
		
		System.out.println("Enter values for starting point: ");
		
		SD.getParamsUser(n);
		//for (int i = 0; i < n; i++) {
			
			//SD.setX0(i, Pro3_wangda93.getDouble("   x" + (i+1) + ": ", -1 * Double.MAX_VALUE, Double.MAX_VALUE));
			
		//}
		
		System.out.println("\nAlgorithm parameters set!\n");
		return true;
	
		
	}
	
	public static int getInteger(String prompt, int LB, int UB) throws IOException {
		
		// get valid integer from user
		
		int number = 0;
		boolean flag = true; // set flag that makes the loop run when true
		String lowerBound = Integer.toString(LB);
		String upperBound = Integer.toString(UB);
		
		if (LB == (-1 * Integer.MAX_VALUE)) {
			
			lowerBound = "-infinity";
			
		}
		
		if (UB == Integer.MAX_VALUE) {
			
			upperBound = "infinity";
			
		}
		
		do {
			
			flag = false; // if no errors are found, the number is valid, and the loop will end
			System.out.print(prompt);
			
			try { 
				
				number = Integer.parseInt(cin.readLine());
				
			} 
			
			catch (NumberFormatException e){
				
				System.out.println("ERROR: Input must be an integer in [" + lowerBound + ", " + upperBound + "]!\n");
				flag = true;
			}
			
			if (!flag && (number > UB || number < LB)) { // if an earlier error was found, don't process the new error
				
				System.out.println("ERROR: Input must be an integer in [" + lowerBound + ", " + upperBound + "]!\n");
				flag = true;
				
			}
			
		} while (flag);
		
		return number;
	}
	
	public static double getDouble(String prompt, double LB, double UB) throws IOException {
		
		// get valid integer from user
		
		double number = 0;
		boolean flag = true; // set flag that makes the loop run when true
		String lowerBound = String.format("%.2f",LB);
		String upperBound = String.format("%.2f",UB);
		
		if (LB == (-1 * Double.MAX_VALUE)) {
			
			lowerBound = "-infinity";
			
		}
		
		if (UB == Double.MAX_VALUE) {
			
			upperBound = "infinity";
			
		}
		
		do {
			
			flag = false; // if no errors are found, the number is valid, and the loop will end
			System.out.print(prompt);
			
			try { 
				
				number = Double.parseDouble(cin.readLine());
				
			} 
			
			catch (NumberFormatException e){
				
				System.out.println("ERROR: Input must be a real number in [" + lowerBound + ", " + upperBound + "]!\n");
				flag = true;
			}
			
			if (!flag && (number > UB || number < LB)) { // if an earlier error was found, don't process the new error
				
				System.out.println("ERROR: Input must be a real number in [" + lowerBound + ", " + upperBound + "]!\n");
				flag = true;
				
			}
			
		} while (flag);
		
		return number;	
	}
	


}
