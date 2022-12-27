import java.io.IOException;

public class SDArmijo extends SteepestDescent {

	private double maxStep;
	private double beta;
	private double tau;
	private int K;

	// constructors
	public SDArmijo() {
		super();
		this.maxStep = 1;
		this.beta = 0.0001;
		this.tau = 0.5;
		this.K = 10;
	}

	public SDArmijo(double maxStep, double beta, double tau, int K) {
		super();
		this.maxStep = maxStep;
		this.beta = beta;
		this.tau = tau;
		this.K = K;

	}

	// getters
	public double getMaxStep() {
		return this.maxStep;
	}

	public double getBeta() {
		return this.beta;
	}

	public double getTau() {
		return this.tau;
	}

	public int getK() {
		return this.K;
	}

	// setters
	public void setMaxStep(double a) {
		this.maxStep = a;
	}

	public void setBeta(double a) {
		this.beta = a;
	}

	public void setTau(double a) {
		this.tau = a;
	}

	public void setK(int a) {
		this.K = a;
	}

	// other methods

	public double lineSearch(Polynomial P, double[] x) {

		// return the step size
		
		double alpha = this.maxStep;
		int kIter = 0;

		double[] leftSide = new double[x.length];

		for (int i = 0; i < x.length; i++) {

			leftSide[i] = x[i] + alpha * direction(P, x)[i];

		}

		double rightSide = P.f(x) - alpha * this.beta * Math.pow(P.gradientNorm(x), 2);

		for (int j = 0; j < this.K && P.f(leftSide) > rightSide; j++) {

			alpha = alpha * this.tau;
			for (int k = 0; k < x.length; k++) {

				leftSide[k] = x[k] + alpha * direction(P, x)[k];

			}

			rightSide = P.f(x) - alpha * this.beta * Math.pow(P.gradientNorm(x), 2);
			kIter++;

		}

		if (kIter == this.K) {

			alpha = -1;

		}

		return alpha;

	}

	@Override
	public boolean getParamsUser() throws IOException {
		
		// get parameters from the user

		System.out.println("Set parameters for SD with an Armijo line search:");

		double temp_maxStep = Pro5_wangda93.getDouble("Enter Armijo max step size (0 to cancel): ", 0,
				Double.MAX_VALUE);

		if (temp_maxStep == 0) {
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		}

		double temp_beta = Pro5_wangda93.getDouble("Enter Armijo beta (0 to cancel): ", 0, 1);

		if (temp_beta == 0) {
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		}

		double temp_tau = Pro5_wangda93.getDouble("Enter Armijo tau (0 to cancel): ", 0, 1);

		if (temp_tau == 0) {
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		}

		int temp_K = Pro5_wangda93.getInteger("Enter Armijo K (0 to cancel): ", 0, Integer.MAX_VALUE);

		if (temp_K == 0) {
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

		this.maxStep = temp_maxStep;
		this.beta = temp_beta;
		this.tau = temp_tau;
		this.K = temp_K;
		super.setEps(temp_eps);
		super.setMaxIter(temp_maxIter);
		super.setX0(temp_x0);

		System.out.println("\nAlgorithm parameters set!\n");
		return true;

	}

	@Override
	public void print() {

		// print out all parameters
		
		System.out.println("\nSD with an Armijo line search:");
		System.out.println("Tolerance (epsilon): " + super.getEps());
		System.out.println("Maximum iterations: " + super.getMaxIter());
		System.out.println("Starting point (x0): " + super.getX0());
		System.out.println("Armijo maximum step size: " + this.maxStep);
		System.out.println("Armijo beta: " + this.beta);
		System.out.println("Armijo tau: " + this.tau);
		System.out.println("Armijo maximum iterations: " + this.K);

	}

	@Override
	public void printStats() {

		// print out results from running the algorithm

		System.out.println("Statistical summary for SD with an Armijo line search:");
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

		System.out.println("Detailed results for SD with an Armijo line search:");
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
