public class Polynomial {
	private int n; // no. of variables
	private int degree; // degree of polynomial
	private double[][] coefs; // coefficients
	private boolean setStatus = false; // whether the polynomial is set

	// constructors
	public Polynomial() {

	}

	public Polynomial(int n, int degree, double[][] coefs) {

		this.n = n;
		this.degree = degree;
		this.coefs = coefs;

	}

	// getters
	public int getN() {
		return this.n;
	}

	public int getDegree() {
		return this.degree;
	}

	public double[][] getCoefs() {
		return this.coefs;
	}

	// setters
	public void setN(int a) {
		this.n = a;
	}

	public void setDegree(int a) {
		this.degree = a;
	}

	public void setCoef(int j, int d, double a) {
		this.coefs[j][d] = a;
	}

	// other methods
	public void init() {

		// init coefficient array based on # of variables and degree

		coefs = new double[n][this.degree + 1];
		this.setStatus = true;

	}

	public double f(double[] x) {

		// calculate the value of a function given a vector

		double value = 0;
		for (int i = 0; i < n; i++) {

			double cur_x = x[i];

			for (int j = 0; j <= degree; j++) {

				value = value + coefs[i][j] * Math.pow(cur_x, (degree - j));

			}
		}

		return value;

	}

	public double[] gradient(double[] x) {

		// calculate the gradient of a simplePolynomialSum
		double[] gradientVec = new double[n];

		for (int i = 0; i < n; i++) {

			double[] cur_coefs = coefs[i];
			double cur_x = x[i];
			double cur_grad = 0;

			for (int j = 0; j < degree; j++) {

				cur_grad = cur_grad + ((degree - j) * cur_coefs[j] * Math.pow(cur_x, degree - 1 - j));

			}

			gradientVec[i] = cur_grad;

		}

		return gradientVec;
	}

	public double gradientNorm(double[] x) {

		// calculate the normal of a gradient
		double norm = 0;
		double[] gradient = this.gradient(x);

		for (int i = 0; i < gradient.length; i++) {

			norm = norm + Math.pow(gradient[i], 2);

		}

		norm = Math.sqrt(norm);
		return norm;
	}

	public boolean isSet() {

		// return the set status

		return this.setStatus;

	}

	public void print() {

		// prints out the polynomial

		System.out.print("f(x) = ");

		// loop through each variable
		for (int i = 0; i < n; i++) {

			System.out.print("( ");

			// loop through each coefficient in each variable
			for (int j = 0; j <= degree; j++) {

				if (j < degree) {
					System.out.format("%.2fx%d^%d", coefs[i][j], (i + 1), (degree - j));
					System.out.print(" + ");
				}

				else {
					System.out.format("%.2f", coefs[i][j]);
				}
			}

			System.out.print(" )");

			// if not the final variable, print + between each variable
			if (i < (n - 1)) {

				System.out.print(" + ");

			}

		}

		System.out.print("\n");
	}
}
