import java.io.IOException;
import java.util.Arrays;

public class SteepestDescent {

	private double eps; 
	private int maxIter;
	private double stepSize; 
	private double[] x0; 
	private double[] bestPoint; 
	private double bestObjVal; 
	private double bestGradNorm; 
	private long compTime; 
	private int nIter; 
	private boolean resultsStatus = false;
	
	// constructors
	
	public SteepestDescent() {
		
		this.eps = 0.001;
		this.maxIter = 100;
		this.stepSize = 0.05;
		this.x0 = new double[] {1};
		
	}
	
	public SteepestDescent(double eps, int maxIter, double stepSize, double[] x0) {
		
		this.eps = eps;
		this.maxIter = maxIter;
		this.stepSize = stepSize;
		this.x0 = x0;
		
	}

	//getters
	public double getEps() { return this.eps; }
	public int getMaxIter() { return this.maxIter; }
	public double getStepSize() { return this.stepSize; }
	public double[] getX0() { return this.x0; }
	public double getBestObjVal() { return this.bestObjVal; }
	public double getBestGradNorm() { return this.bestGradNorm; }
	public double[] getBestPoint() { return this.bestPoint; }
	public int getNIter() { return this.nIter; }
	public long getCompTime() { return this.compTime; }
	public boolean getResultsStatus() { return this.resultsStatus; }
	
	//setters
	
	public void setEps(double a) { this.eps = a; }
	public void setMaxIter(int a) { this.maxIter = a; }
	public void setStepSize(double a) { this.stepSize = a; }
	public void setX0(int j, double a) { this.x0[j] = a; }
	public void setBestObjVal(double a) { this.bestObjVal = a; }
	public void setBestGradNorm(double a) { this.bestGradNorm = a; }
	public void setBestPoint(double[] a) { this.bestPoint = a; }
	public void setCompTime(long a) { this.compTime = a; }
	public void setNIter(int a) { this.nIter = a; }
	public void setHasResults(boolean a) { this.resultsStatus = a; } 
	
	// other methods
	
	public void init(int n) {
		
		// initialize array dimensions
		
		this.x0 = new double[n];
		this.bestPoint = new double[n];
		
	}
	
	public void run(Polynomial P) {
		
		// run the steepest descent algorithm
		
		//warning for in x0 does not match polynomial dimensions
		if (this.x0.length != P.getN()) {
			
			System.out.println("\nWARNING: Dimensions of polynomial and x0 do not match! Using x0 = 1-vector of appropriate dimension.");
			this.x0 = new double[P.getN()];
			for (int i = 0; i < this.x0.length; i++) {
				
				this.x0[i] = 1;
				
			}
		}
		
		//running algorithm
		
		System.out.print("\n");
		this.bestPoint = Arrays.copyOf(this.x0, this.x0.length); //initialize the first x to x0
		this.nIter = 0; //initialize the number of iterations to 0
		long start = System.currentTimeMillis(); // start timer 
		
		
		for (int i = 0; i <= this.maxIter; i++) {
			
			
			// set values of current iteration that will be printed
			
			this.bestObjVal = P.f(this.bestPoint);
			this.bestGradNorm = P.gradientNorm(this.bestPoint);
			this.compTime = System.currentTimeMillis()-start;
			
			if (i == 0) {
				printResults(false);
			}
			
			else {
				printResults(true);
			}
			
			// if the gradientNormal is smaller than epsilon or undefined, finish algorithm
			if (this.bestGradNorm <= this.eps) {
				
				break;
				
			}
			
			if (Double.isNaN(this.bestGradNorm)) {
				
				break;
				
			}
			
			//set new values for following iteration
			
			if (i != this.maxIter) {
				this.nIter++;// update number of iterations 
				
				double[] cur_direction = this.direction(P, this.bestPoint); //set the new direction
				
				for (int j = 0; j < this.bestPoint.length; j++) { //update x for the next iteration
					
					this.bestPoint[j] = this.bestPoint[j] + (this.lineSearch() * cur_direction[j]);
					
				}
			
			}
		}
		
		this.resultsStatus = true;
		
		System.out.print("\n");
	}
	
	public double lineSearch() {
		
		// next step is constant, so just use stepSize
		
		return this.stepSize;
		
	}
	
	public double[] direction(Polynomial P, double[] x) {
		
		// set the direction based on the current point
		
		double[] directionVec = P.gradient(x);
		
		for (int i = 0; i < directionVec.length; i++) {
			
			directionVec[i] = directionVec[i] * -1;
			
		}
		
		return directionVec;
		
	}
	
	public void getParamsUser(int n) throws IOException {
		
		// get parameters from the user
		
		for (int i = 0; i < n; i++) {
			
			this.setX0(i, Pro3_wangda93.getDouble("   x" + (i+1) + ": ", -1 * Double.MAX_VALUE, Double.MAX_VALUE));
			
		}
	}
	
	public void print() {
		
		// print out algorithm parameters
		
		System.out.println("\nTolerance (epsilon): " + this.eps);
		System.out.println("Maximum iterations: " + this.maxIter);
		System.out.println("Step size (alpha): " + this.stepSize);
		System.out.print("Starting point (x0): ( ");
		
		for (int i = 0; i < x0.length; i++) {
			
			System.out.format("%.2f",x0[i]);
			
			if (i != (x0.length - 1)) {
				
				// if the final number, don't print commas
				System.out.print(", ");
				
			}
		
		}
		
		System.out.println(" )\n");
		
	}
	
	public void printResults(boolean rowOnly) {
		
		//print results of an iteration of the algorithm
		
		if (rowOnly == false) {
			
			//rowOnly determines whether to print the header
			System.out.println("--------------------------------------------------------------");
			System.out.println("      f(x)   norm(grad)   # iter   Comp time (ms)   Best point   ");
			System.out.println("--------------------------------------------------------------");
			
		}
		
		System.out.format("%10.6f%13.6f%9d%17d", this.bestObjVal, this.bestGradNorm, this.nIter, this.compTime);
		System.out.print("   ");
		
		for (int i = 0; i < this.bestPoint.length; i++) {
			
			System.out.format("%.4f", this.bestPoint[i]);
			
			//print comma and space if not final number
			if (i != (this.bestPoint.length - 1)) {
				System.out.print(", ");
			}
		}
		
		System.out.print("\n");
		
	}
	
	
}
