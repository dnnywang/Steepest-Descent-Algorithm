import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Pro4_wangda93 {
	
	static BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
	static ArrayList<Polynomial> polyFuncs = new ArrayList<Polynomial>();
	static SteepestDescent algorithm = new SteepestDescent();
	
	public static void main(String[] args) throws IOException {
		
		boolean flag = true; 
		
		do { //keep looping menu as long as flag is true
			displayMenu();
			String selection = cin.readLine();
			
			if (selection.equals("L") || selection.equals("l")) {
				
				// get the polynomial parameters from the user
				
				loadPolynomialFile(polyFuncs);
				
			}
			
			else if (selection.equals("F") || selection.equals("f")) {
				
				// check if polynomial is entered, then print the polynomials
				if (polyFuncs.size() == 0) {
					
					System.out.println("\nERROR: No polynomial functions are loaded!\n");
					
				}
				
				else {
					
					printPolynomials(polyFuncs);
					
				}
			}
			
			else if (selection.equals("C") || selection.equals("c")) {
				
				//clear polynomials
				
				polyFuncs.clear();
				algorithm.setHasResults(false);
				System.out.println("\nAll polynomials cleared.\n");
				
			}
			
			else if (selection.equals("S") || selection.equals("s")) {
				
				// set algorithm parameters 
				
				getAlgorithmParams(algorithm, 1);
				algorithm.setHasResults(false);
				
			}
			
			else if (selection.equals("P") || selection.equals("p")) {
				
				// print algorithm parameters
				
				algorithm.print();
				
			}
			
			else if (selection.equals("R") || selection.equals("r")) {
				
				//run each polynomial
				
				if (polyFuncs.size() == 0) {
					
					System.out.println("\nERROR: No polynomial functions are loaded!\n");

				}
				
				else {
					System.out.println();
		
					for (int i = 0; i < polyFuncs.size(); i++) {
						
						algorithm.run(i, polyFuncs.get(i));
						
					}
					
					System.out.println("\nAll polynomials done.\n");
				}
			}
			
			else if (selection.equals("D") || selection.equals("d")) {
				
				// display algorithm results
				
				if (algorithm.hasResults() == false) {
					
					System.out.println("\nERROR: No results exist!\n");
					
				}
				
				else {
					System.out.print("\n");
					algorithm.printAll();
					System.out.println();
					algorithm.printStats();
					System.out.print("\n");
				}
			}
			
			else if (selection.equals("Q") || selection.equals("q")) {
				
				// quit the program
				
				flag = false; 
				System.out.println("\nFin.");
				
			}
			
			else {
				
				System.out.println("\nERROR: Invalid menu choice!\n");
				
			}
					
		} while(flag);
		
		
		
	}
	
	public static void displayMenu() {
		
		// print the menu
		
		System.out.println("   JAVA POLYNOMIAL MINIMIZER (STEEPEST DESCENT)");
		System.out.println("L - Load polynomials from file");
		System.out.println("F - View polynomial functions");
		System.out.println("C - Clear polynomial functions");
		System.out.println("S - Set steepest descent parameters");
		System.out.println("P - View steepest descent parameters");
		System.out.println("R - Run steepest descent algorithm");
		System.out.println("D - Display algorithm performance");
		System.out.println("Q - Quit\n");
		System.out.print("Enter choice: ");
	
	}
	
	public static boolean loadPolynomialFile(ArrayList<Polynomial> P) throws IOException {
		
		//load polynomials from a text file format 
		
		System.out.print("\nEnter file name (0 to cancel): ");
		String filename = cin.readLine();
		
		if (filename.equals("0")) {
			
			System.out.println("\nFile loading process canceled.\n");
			return false;
			
		}
		
		else {
			try (BufferedReader fin = new BufferedReader(new FileReader(filename))) {
				String curLine;
				ArrayList<String[]> curCoefs = new ArrayList<String[]>();
				String[] splitCoefs;
				int numPoly = 1;
				int successPoly = 0;
				
				do {
					
					curLine = fin.readLine();
					
					if(curLine != null) {
						
						if(curLine.contains("*") && curLine.charAt(0) == '*') {
							
							if (checkPolynomialEven(curCoefs)) {
								
								polyFuncs.add(processPolynomial(curCoefs));
								successPoly++;
								
							}
							
							else {
								
								System.out.println("\nERROR: Inconsistent dimensions in polynomial " + numPoly + "!");
								
							}
							
							curCoefs.clear(); // reset coefficients
							numPoly++;
							
						}
						
						else {
							
							splitCoefs = curLine.split(","); // we have now split the string into different coefficients
							curCoefs.add(splitCoefs);
							
						}
					}
					
					else {
						
						if (checkPolynomialEven(curCoefs)) {
							
							polyFuncs.add(processPolynomial(curCoefs));
							successPoly++;
							
						}
						
						else {
							
							System.out.println("\nERROR: Inconsistent dimensions in polynomial " + numPoly + "!");
							
						}
						
						curCoefs.clear(); // reset coefficients
						numPoly++;
						
					}
					
				} while (curLine != null);
				
				System.out.println("\n" + successPoly + " polynomials loaded!\n");
				algorithm.init(polyFuncs);
				
			}
			
			catch (FileNotFoundException e){
				
				System.out.println("\nERROR: File not found!\n");
				return false;
				
			}
			
			return true;
		}
	}
	
	public static Polynomial processPolynomial(ArrayList<String[]> coefs) {
		
		// create a Polynomial object given an ArrayList of coefficients (of type String)
		
		ArrayList<double[]> coefsDouble = new ArrayList<double[]>();
		double[] curCoefs;
		
		// convert ArrayList of Strings to ArrayList of doubles
		for(int i = 0; i < coefs.size(); i++) {
			
			curCoefs = new double[coefs.get(i).length];
			
			for (int j = 0; j < curCoefs.length; j++) {
				
				curCoefs[j] = Double.valueOf(coefs.get(i)[j]);
				
			}
			
			coefsDouble.add(curCoefs);
			
		}
		
		// place each double[] in the ArrayList into the double[][]
		double[][] coefsArray = new double[coefsDouble.size()][coefsDouble.get(0).length];
		
		for (int i = 0; i < coefsArray.length; i++) {
			
			coefsArray[i] = coefsDouble.get(i);
			
		}
		
		// initialize the polynomial
		Polynomial poly = new Polynomial(coefs.size(), coefs.get(0).length - 1, coefsArray);
		
		return poly;
		
	}
	
	public static boolean checkPolynomialEven(ArrayList<String[]> coefs) {
		
		// check if a polynomial has the same coefficients 
		
		boolean flag = true;
		int num = coefs.get(0).length;
		
		for (int i = 0; i < coefs.size(); i++) {
			
			int cur_num = coefs.get(i).length;
			
			if(cur_num != num) {
				
				flag = false;
				
			}
			
			num = cur_num;
			
		}
		
		return flag;
	}
	
	public static void printPolynomials(ArrayList<Polynomial> P) {
		
		// print out the parameters of each polynomial
	
		System.out.println("\n---------------------------------------------------------");
		System.out.println("Poly No.  Degree   # vars   Function");
		System.out.println("---------------------------------------------------------");
		
		for (int i = 0; i < P.size(); i++) {
			
			int degree = P.get(i).getDegree();
			int vars = P.get(i).getN();
			
			System.out.format("%8d%8d%9d   ", (i+1), degree, vars);
			P.get(i).print();
			
		}
		
		System.out.println();
		
	}
	
	public static boolean getAlgorithmParams(SteepestDescent SD, int n) throws IOException {
		
		// get parameters for the steepest descent algorithm	
		
		System.out.print("\n");
		
		double temp_eps = Pro4_wangda93.getDouble("Enter tolerance epsilon (0 to cancel): ", 0, Double.MAX_VALUE);
		
		//if cancelled return false
		if (temp_eps == 0) { 
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false; 
		}
		
		int temp_maxIter = Pro4_wangda93.getInteger("Enter maximum number of iterations (0 to cancel): ", 0, 10000);
		
		//if cancelled return false
		if (temp_maxIter == 0) {
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false; 
		}
		
		double temp_stepSize = Pro4_wangda93.getDouble("Enter step size alpha (0 to cancel): ", 0, Double.MAX_VALUE);
		
		//if cancelled return false
		if (temp_stepSize == 0) { 
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false; 
		}
		
		double temp_x0 = Pro4_wangda93.getDouble("Enter value for starting point (0 to cancel): ", -1 * Double.MAX_VALUE, Double.MAX_VALUE);
		
		//if cancelled return false
		if (temp_x0 == 0) { 
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false; 
		}
				
		SD.setEps(temp_eps);
		SD.setMaxIter(temp_maxIter);
		SD.setStepSize(temp_stepSize);
		SD.setX0(temp_x0);
		
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
