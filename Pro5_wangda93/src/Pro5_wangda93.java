import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Pro5_wangda93 {

	static BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
	static ArrayList<Polynomial> polyFuncs = new ArrayList<Polynomial>();
	static SDFixed algorithmF = new SDFixed();
	static SDArmijo algorithmA = new SDArmijo();
	static SDGSS algorithmG = new SDGSS();

	public static void main(String[] args) throws IOException {

		boolean flag = true;

		do { // keep looping menu as long as flag is true
			displayMenu();
			String selection = cin.readLine();

			if (selection.equals("L") || selection.equals("l")) {

				// get the polynomial parameters from the user

				loadPolynomialFile(polyFuncs);
				algorithmF.setHasResults(false);
				algorithmA.setHasResults(false);
				algorithmG.setHasResults(false);

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

				// clear polynomials

				polyFuncs.clear();
				algorithmF.setHasResults(false);
				algorithmA.setHasResults(false);
				algorithmG.setHasResults(false);
				System.out.println("\nAll polynomials cleared.\n");

			}

			else if (selection.equals("S") || selection.equals("s")) {

				// set algorithm parameters
				getAllParams(algorithmF, algorithmA, algorithmG);
				algorithmF.setHasResults(false);
				algorithmA.setHasResults(false);
				algorithmG.setHasResults(false);

			}

			else if (selection.equals("P") || selection.equals("p")) {

				// print algorithm parameters

				printAllParams(algorithmF, algorithmA, algorithmG);

			}

			else if (selection.equals("R") || selection.equals("r")) {

				// run each polynomial

				if (polyFuncs.size() == 0) {

					System.out.println("\nERROR: No polynomial functions are loaded!\n");

				}

				else {

					runAll(algorithmF, algorithmA, algorithmG, polyFuncs);

				}

			}

			else if (selection.equals("D") || selection.equals("d")) {

				// display algorithm results

				if (algorithmF.hasResults() == false || algorithmA.hasResults() == false
						|| algorithmG.hasResults() == false) {

					System.out.println("\nERROR: Results do not exist for all line searches!\n");

				}

				else {

					printAllResults(algorithmF, algorithmA, algorithmG, polyFuncs);

				}

			}

			else if (selection.equals("X") || selection.equals("x")) {

				// compare algorithm performances

				if (algorithmF.hasResults() == false || algorithmA.hasResults() == false
						|| algorithmG.hasResults() == false) {

					System.out.println("\nERROR: Results do not exist for all line searches!\n");

				}

				else {

					compare(algorithmF, algorithmA, algorithmG);

				}

			}

			else if (selection.equals("Q") || selection.equals("q")) {

				// quit the program

				flag = false;
				System.out.println("\nArrivederci.");

			}

			else {

				System.out.println("\nERROR: Invalid menu choice!\n");

			}

		} while (flag);

	}

	public static void displayMenu() {

		// print the menu

		System.out.println("   JAVA POLYNOMIAL MINIMIZER (STEEPEST DESCENT)");
		System.out.println("L - Load polynomials from file");
		System.out.println("F - View polynomial functions");
		System.out.println("C - Clear polynomial functions");
		System.out.println("S - Set steepest descent parameters");
		System.out.println("P - View steepest descent parameters");
		System.out.println("R - Run steepest descent algorithms");
		System.out.println("D - Display algorithm performance");
		System.out.println("X - Compare average algorithm performance");
		System.out.println("Q - Quit\n");
		System.out.print("Enter choice: ");

	}

	public static boolean loadPolynomialFile(ArrayList<Polynomial> P) throws IOException {

		// load polynomials from a text file format

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

					if (curLine != null) {

						if (curLine.contains("*") && curLine.charAt(0) == '*') {

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
				algorithmF.init(polyFuncs);
				algorithmA.init(polyFuncs);
				algorithmG.init(polyFuncs);

			}

			catch (FileNotFoundException e) {

				System.out.println("\nERROR: File not found!\n");
				return false;

			}

			return true;
		}
	}

	public static Polynomial processPolynomial(ArrayList<String[]> coefs) {

		// create a Polynomial object given an ArrayList of coefficients (of type
		// String)

		ArrayList<double[]> coefsDouble = new ArrayList<double[]>();
		double[] curCoefs;

		// convert ArrayList of Strings to ArrayList of doubles
		for (int i = 0; i < coefs.size(); i++) {

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

			if (cur_num != num) {

				flag = false;

			}

			num = cur_num;

		}

		return flag;
	}

	public static void getAllParams(SDFixed SDF, SDArmijo SDA, SDGSS SDG) throws IOException {

		// get all the parameters for each algorithm

		System.out.println();
		SDF.getParamsUser();
		SDA.getParamsUser();
		SDG.getParamsUser();

	}

	public static void printAllParams(SDFixed SDF, SDArmijo SDA, SDGSS SDG) {

		// print all the parameters for each algorithm

		SDF.print();
		SDA.print();
		SDG.print();

	}

	public static void runAll(SDFixed SDF, SDArmijo SDA, SDGSS SDG, ArrayList<Polynomial> P) {

		// run all algorithms

		System.out.println();

		System.out.println("Running SD with a fixed line search:");
		for (int i = 0; i < P.size(); i++) {

			SDF.run(i, P.get(i));

		}

		System.out.println("\nRunning SD with an Armijo line search:");
		for (int i = 0; i < P.size(); i++) {

			SDA.run(i, P.get(i));

		}

		System.out.println("\nRunning SD with a golden section line search:");
		for (int i = 0; i < P.size(); i++) {

			SDG.run(i, P.get(i));

		}

		System.out.println("\nAll polynomials done.\n");
	}

	public static void printPolynomials(ArrayList<Polynomial> P) {

		// print out the parameters of each polynomial

		System.out.println("\n---------------------------------------------------------");
		System.out.println("Poly No.  Degree   # vars   Function");
		System.out.println("---------------------------------------------------------");

		for (int i = 0; i < P.size(); i++) {

			int degree = P.get(i).getDegree();
			int vars = P.get(i).getN();

			System.out.format("%8d%8d%9d   ", (i + 1), degree, vars);
			P.get(i).print();

		}

		System.out.println();

	}

	public static void printAllResults(SDFixed SDF, SDArmijo SDA, SDGSS SDG, ArrayList<Polynomial> P) {

		// print all results for algorithms

		System.out.print("\n");
		SDF.printAll();
		System.out.println();
		SDF.printStats();
		System.out.print("\n");

		System.out.print("\n");
		SDA.printAll();
		System.out.println();
		SDA.printStats();
		System.out.print("\n");

		System.out.print("\n");
		SDG.printAll();
		System.out.println();
		SDG.printStats();
		System.out.print("\n");
	}

	public static void compare(SDFixed SDF, SDArmijo SDA, SDGSS SDG) {

		// compare different algorithms

		System.out.println();
		SDF.printAverage(false, "Fixed");
		SDA.printAverage(true, "Armijo");
		SDG.printAverage(true, "GSS");

		double[] averageNorms = { SDF.getAverage()[0], SDA.getAverage()[0], SDG.getAverage()[0] };
		double[] averageIters = { SDF.getAverage()[1], SDA.getAverage()[1], SDG.getAverage()[1] };
		double[] averageComps = { SDF.getAverage()[2], SDA.getAverage()[2], SDG.getAverage()[2] };

		String bestNorms = getBestAlgorithm(averageNorms);
		String bestIters = getBestAlgorithm(averageIters);
		String bestComps = getBestAlgorithm(averageComps);

		System.out.println("---------------------------------------------------");
		System.out.format("Winner%14s%13s%18s\n", bestNorms, bestIters, bestComps);
		System.out.println("---------------------------------------------------");

		String[] winners = { bestNorms, bestIters, bestComps };

		if (winners[0].equals(winners[1]) && winners[1].equals(winners[2])) {

			System.out.println("Overall winner: " + winners[0]);

		}

		else {

			System.out.println("Overall winner: Unclear");

		}

		System.out.println();

	}

	public static String getBestAlgorithm(double[] average) {

		// calculate the best algorithm, and return the right algorithm name

		String algo = null;
		double bestAlgo = Stats.calcMin(average);
		Double[] bestAlgoObj = { average[0], average[1], average[2] };

		int index = Arrays.asList(bestAlgoObj).indexOf(bestAlgo);

		if (index == 0) {

			algo = "Fixed";

		}

		else if (index == 1) {

			algo = "Armijo";

		}

		else if (index == 2) {

			algo = "GSS";

		}

		return algo;
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

			catch (NumberFormatException e) {

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
		String lowerBound = String.format("%.2f", LB);
		String upperBound = String.format("%.2f", UB);

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

			catch (NumberFormatException e) {

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
