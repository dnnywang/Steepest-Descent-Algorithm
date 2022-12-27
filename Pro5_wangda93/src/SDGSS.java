import java.io.IOException;

public class SDGSS extends SteepestDescent {
	private final double PHI = (1. + Math.sqrt(5)) / 2.;
	private double maxStep; // b
	private double minStep; // a
	private double delta; //

	// constructors
	public SDGSS() {
		super();
		this.maxStep = 1;
		this.minStep = 0.001;
		this.delta = 0.001;
	}

	public SDGSS(double maxStep, double minStep, double delta) {
		super();
		this.maxStep = maxStep;
		this.minStep = minStep;
		this.delta = delta;

	}

	// getters
	public double getMaxStep() {
		return this.maxStep;
	}

	public double getMinStep() {
		return this.minStep;
	}

	public double getDelta() {
		return this.delta;
	}

	// setters
	public void setMaxStep(double a) {
		this.maxStep = a;
	}

	public void setMinStep(double a) {
		this.minStep = a;
	}

	public void setDelta(double a) {
		this.delta = a;
	}

	// other methods
	@Override
	public double lineSearch(Polynomial P, double[] x) { // step size from GSS

		// return the step size
		
		double c = this.minStep + ((this.maxStep - this.minStep) / this.PHI);

		double stepSize = GSS(this.minStep, this.maxStep, c, x, super.direction(P, x), P);

		return stepSize;

	}

	@Override
	public boolean getParamsUser() throws IOException { // get algorithm parameters from user

		// get algorithm parameters
		System.out.println("Set parameters for SD with a golden section line search:");

		double temp_maxStep = Pro5_wangda93.getDouble("Enter GSS maximum step size (0 to cancel): ", 0,
				Double.MAX_VALUE);

		if (temp_maxStep == 0) {
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		}

		double temp_minStep = Pro5_wangda93.getDouble("Enter GSS minimum step size (0 to cancel): ", 0, 2);

		if (temp_minStep == 0) {
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		}

		double temp_delta = Pro5_wangda93.getDouble("Enter GSS delta (0 to cancel): ", 0, Double.MAX_VALUE);

		if (temp_delta == 0) {
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
		this.minStep = temp_minStep;
		this.delta = temp_delta;
		super.setEps(temp_eps);
		super.setMaxIter(temp_maxIter);
		super.setX0(temp_x0);

		System.out.println("\nAlgorithm parameters set!\n");
		return true;
	}

	@Override
	public void print() { // print parameters

		System.out.println("\nSD with a golden section line search:");
		System.out.println("Tolerance (epsilon): " + super.getEps());
		System.out.println("Maximum iterations: " + super.getMaxIter());
		System.out.println("Starting point (x0): " + super.getX0());
		System.out.println("GSS maximum step size: " + this.maxStep);
		System.out.println("GSS minimum step size: " + this.minStep);
		System.out.println("GSS delta: " + this.delta);
		System.out.println();

	}

	private double GSS(double a, double b, double c, double[] x, double[] dir, Polynomial P) {

		// the golden section search algorithm

		double stepSize;

		if (P.f(vec(x, dir, c)) > P.f(vec(x, dir, a)) || P.f(vec(x, dir, c)) > P.f(vec(x, dir, b))) {

			if (P.f(vec(x, dir, a)) >= P.f(vec(x, dir, b))) {
				return b;
			}

			else {
				return a;
			}

		}

		if (Math.abs(b - a) < this.delta) {

			// if interval is too small

			stepSize = (b + a) / 2;

		}

		else {

			if ((Math.abs(c - a)) > Math.abs(b - c)) {

				// if [a,c] is the larger interval

				double y = a + ((c - a) / this.PHI);

				if (P.f(vec(x, dir, y)) < P.f(vec(x, dir, a)) && P.f(vec(x, dir, y)) < P.f(vec(x, dir, c))) {

					// if fy is less than both endpoints
					stepSize = this.GSS(a, c, y, x, dir, P);
				}

				else {

					// if fy is greater than one end point
					stepSize = this.GSS(y, b, c, x, dir, P);
				}
			}

			else {

				// if [c,b] is the larger interval
				double y = b - ((b - c) / this.PHI);
				// System.out.println("y_right: " + y);

				if (P.f(vec(x, dir, y)) < P.f(vec(x, dir, c)) && P.f(vec(x, dir, y)) < P.f(vec(x, dir, b))) {
					// if smaller than both end points
					stepSize = this.GSS(c, b, y, x, dir, P);

				}

				else {
					// if larger than one end point
					stepSize = this.GSS(a, y, c, x, dir, P);

				}
			}
		}

		return stepSize;
	}

	public double[] vec(double[] x, double[] dir, double stepSize) {

		// returns f(x + a * direction_x)

		double[] vec = new double[x.length];

		for (int i = 0; i < x.length; i++) {

			vec[i] = x[i] + (stepSize * dir[i]);

		}

		return vec;
	}

	@Override
	public void printStats() {

		// print out results from running the algorithm

		System.out.println("Statistical summary for SD with a golden section line search:");
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

		System.out.println("Detailed results for SD with a golden section line search:");
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
