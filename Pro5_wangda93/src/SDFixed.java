import java.io.IOException;

public class SDFixed extends SteepestDescent {

	private double alpha; // fixed step size

	// constructors
	public SDFixed() {
		super();
		this.alpha = 0.01;
	}

	public SDFixed(double alpha) {
		super();
		this.alpha = alpha;

	}

	// getters
	public double getAlpha() {
		return this.alpha;
	}

	// setters
	public void setAlpha(double a) {
		this.alpha = a;
	}

	// other methods
	@Override
	public double lineSearch(Polynomial P, double[] x) {
		
		//return alpha value
		
		return this.alpha;

	}

	@Override
	public boolean getParamsUser() throws IOException {

		System.out.println("Set parameters for SD with a fixed line search:");

		double temp_alpha = Pro5_wangda93.getDouble("Enter fixed step size (0 to cancel): ", 0, Double.MAX_VALUE);

		if (temp_alpha == 0) {
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		}

		double temp_eps = Pro5_wangda93.getDouble("Enter tolerance epsilon (0 to cancel): ", 0, Double.MAX_VALUE);

		if (temp_eps == 0) {
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		}

		int temp_maxIter = Pro5_wangda93.getInteger("Enter maximum number of iterations (0 to cancel): ", 0, 10000);

		if (temp_maxIter == 0) {
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		}

		double temp_x0 = Pro5_wangda93.getDouble("Enter value for starting point (0 to cancel): ",
				-1 * Double.MAX_VALUE, Double.MAX_VALUE);

		// if cancelled return false
		if (temp_x0 == 0) {
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		}

		this.alpha = temp_alpha;
		super.setEps(temp_eps);
		super.setMaxIter(temp_maxIter);
		super.setX0(temp_x0);

		System.out.println("\nAlgorithm parameters set!\n");
		return true;
	}

	@Override
	public void print() {

		System.out.println("\nSD with a fixed line search:");
		System.out.println("Tolerance (epsilon): " + super.getEps());
		System.out.println("Maximum iterations: " + super.getMaxIter());
		System.out.println("Starting point (x0): " + super.getX0());
		System.out.println("Fixed step size (alpha): " + this.alpha);

	}

	@Override
	public void printStats() {

		// print out results from running the algorithm

		System.out.println("Statistical summary for SD with a fixed line search:");
		System.out.println("---------------------------------------------------");
		System.out.println("          norm(grad)       # iter    Comp time (ms)");
		System.out.println("---------------------------------------------------");

		System.out.format("Average%13.3f%13.3f%18.3f\n", Stats.calcAverage(super.getBestGradNorm()),
				Stats.calcAverage(super.getNIter()), Stats.calcAverage(super.getCompTime()));
		System.out.format("St Dev%14.3f%13.3f%18.3f\n", Stats.calcSD(super.getBestGradNorm()),
				Stats.calcSD(super.getNIter()), Stats.calcSD(super.getCompTime()));
		System.out.format("Min%17.3f%13.0f%18.0f\n", Stats.calcMin(super.getBestGradNorm()),
				Stats.calcMin(super.getNIter()), Stats.calcMin(super.getCompTime()));
		System.out.format("Max%17.3f%13.0f%18.0f\n", Stats.calcMax(super.getBestGradNorm()),
				Stats.calcMax(super.getNIter()), Stats.calcMax(super.getCompTime()));
	}

	@Override
	public void printAll() {

		// print out all the polynomial results

		System.out.println("Detailed results for SD with a fixed line search:");
		for (int i = 0; i < super.getBestGradNorm().length; i++) {

			if (i == 0) {
				printSingleResult(i, false);
			}

			else {
				printSingleResult(i, true);
			}
		}

	}

}
