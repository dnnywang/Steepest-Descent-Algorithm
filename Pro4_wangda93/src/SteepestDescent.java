import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SteepestDescent {

	private double eps; 
	private int maxIter;
	private double stepSize; 
	private double x0; 
	private ArrayList<double[]> bestPoint = new ArrayList<double[]>(); 
	private double[] bestObjVal; 
	private double[] bestGradNorm; 
	private long[] compTime; 
	private int[] nIter; 
	private boolean resultsExist;
	
	// constructors
	
	public SteepestDescent() {
		
		this.eps = 0.001;
		this.maxIter = 100;
		this.stepSize = 0.05;
		this.x0 = 1;
		
	}
	
	public SteepestDescent(double eps, int maxIter, double stepSize, double x0) {
		
		this.eps = eps;
		this.maxIter = maxIter;
		this.stepSize = stepSize;
		this.x0 = x0;
		
	}

	//getters
	public double getEps() { return this.eps; }
	public int getMaxIter() { return this.maxIter; }
	public double getStepSize() { return this.stepSize; }
	public double getX0() { return this.x0; }
	public double[] getBestObjVal() { return this.bestObjVal; }
	public double[] getBestGradNorm() { return this.bestGradNorm; }
	public double[] getBestPoint(int i) { return this.bestPoint.get(i); }
	public int[] getNIter() { return this.nIter; }
	public long[] getCompTime() { return this.compTime; }
	public boolean hasResults() { return this.resultsExist; }
	
	//setters
	
	public void setEps(double a) { this.eps = a; }
	public void setMaxIter(int a) { this.maxIter = a; }
	public void setStepSize(double a) { this.stepSize = a; }
	public void setX0(double a) { this.x0 = a; }
	public void setBestObjVal(int i, double a) { this.bestObjVal[i] = a; }
	public void setBestGradNorm(int i, double a) { this.bestGradNorm[i] = a; }
	public void setBestPoint(int i, double[] a) { this.bestPoint.set(i, a); }
	public void setCompTime(int i, long a) { this.compTime[i] = a; }
	public void setNIter(int i, int a) { this.nIter[i] = a; }
	public void setHasResults(boolean a) { this.resultsExist = a; } 
	
	// other methods
	
	public void init(ArrayList<Polynomial> P) {
		// initialize array dimensions
		int n = P.size();
		
		this.bestPoint.clear();
		
		for (int i = 0; i < n; i++) {
			
			double[] emptyArray = new double[P.get(i).getN()];
			
			this.bestPoint.add(emptyArray);
			
		}
		
		this.bestObjVal = new double[n];
		this.bestGradNorm = new double[n];
		this.compTime = new long[n];
		this.nIter = new int[n];
		
	}
	
	public void run(int i, Polynomial P) {
		
		//run the algorithm with one Polynomial
		
		//fill bestPoint with x0
		double[] tempFirstPoint = new double[this.bestPoint.get(i).length];
		Arrays.fill(tempFirstPoint, this.x0); 
		this.bestPoint.set(i, tempFirstPoint);
		//System.out.println(Arrays.toString(this.bestPoint.get(i)));
		
		this.nIter[i] = 0; //initialize the number of iterations to 0
		long start = System.currentTimeMillis(); // start timer 
		
		
		for (int c = 0; c <= this.maxIter; c++) {
			
			
			// set values of current iteration that will be printed
			
			this.bestObjVal[i] = P.f(this.bestPoint.get(i));
			this.bestGradNorm[i] = P.gradientNorm(this.bestPoint.get(i));
			this.compTime[i] = System.currentTimeMillis()-start;
			
			// if the gradientNormal is smaller than epsilon or undefined, finish algorithm
			if (this.bestGradNorm[i] <= this.eps) {
				
				break;
				
			}
			
			if (Double.isNaN(this.bestGradNorm[i])) {
				
				break;
				
			}
			
			//set new values for following iteration
			
			if (c != this.maxIter) {
				this.nIter[i]++;// update number of iterations 
				
				double[] cur_direction = this.direction(P, this.bestPoint.get(i)); //set the new direction
				
				double[] tempBestPoint = new double[this.bestPoint.get(i).length];
				
				for (int j = 0; j < this.bestPoint.get(i).length; j++) { //update x for the next iteration
					
					tempBestPoint[j] = this.bestPoint.get(i)[j] + (this.lineSearch() * cur_direction[j]);
					
				}
				
				this.bestPoint.set(i, tempBestPoint);
			}
			
		}
		
		this.resultsExist = true;
		
		System.out.println("Polynomial " + (i+1) + " done in " + this.compTime[i] + "ms.");
	}
		
	
	public double lineSearch() {
		
		// return the stepSize
		
		return this.stepSize;
		
	}
	
	public double[] direction(Polynomial P, double[] x) {
		
		// return the direction
		
		double[] directionVec = P.gradient(x);
		
		for (int i = 0; i < directionVec.length; i++) {
			
			directionVec[i] = directionVec[i] * -1;
			
		}
		
		return directionVec;
		
	}
	
	public void getParamsUser() {
		
		//set user params
		
		this.setEps(this.eps);
		this.setMaxIter(this.maxIter);
		this.setStepSize(this.stepSize);
		this.setX0(this.x0);
		
	}
	
	public void print() {
		
		// print out algorithm parameters
		
		System.out.println("\nTolerance (epsilon): " + this.eps);
		System.out.println("Maximum iterations: " + this.maxIter);
		System.out.println("Step size (alpha): " + this.stepSize);
		System.out.println("Starting point (x0): " + this.x0);
		System.out.println();
		
	}
	
	public void printStats() {
		
		// print out results from running the algorithm
		
		System.out.println("Statistical summary:");
		System.out.println("---------------------------------------------------");
		System.out.println("          norm(grad)       # iter    Comp time (ms)");
		System.out.println("---------------------------------------------------");
		
		System.out.format("Average%13.3f%13.3f%18.3f\n", Stats.calcAverage(this.bestGradNorm), Stats.calcAverage(this.nIter), Stats.calcAverage(this.compTime));
		System.out.format("St Dev%14.3f%13.3f%18.3f\n", Stats.calcSD(this.bestGradNorm), Stats.calcSD(this.nIter), Stats.calcSD(this.compTime));
		System.out.format("Min%17.3f%13.0f%18.0f\n", Stats.calcMin(this.bestGradNorm), Stats.calcMin(this.nIter), Stats.calcMin(this.compTime));
		System.out.format("Max%17.3f%13.0f%18.0f\n", Stats.calcMax(this.bestGradNorm), Stats.calcMax(this.nIter), Stats.calcMax(this.compTime));
	}
	
	public void printAll() {
		
		// print out all the polynomial results
		
		System.out.println("Detailed results:");
		for (int i = 0; i < this.bestGradNorm.length; i++) {
			
			if (i == 0) {
				printSingleResult(i, false);
			}
			
			else {
				printSingleResult(i, true);
			}
		}
		
	}
	
	public void printSingleResult(int i, boolean rowOnly) {
		
		// print out the result of one polynomial
		
		if (rowOnly == false) {
			
			//rowOnly determines whether to print the header
			System.out.println("-------------------------------------------------------------------------");
			System.out.println("Poly no.         f(x)   norm(grad)   # iter   Comp time (ms)   Best point   ");
			System.out.println("-------------------------------------------------------------------------");
			
		}
		
		System.out.format("%8d%13.6f%13.6f%9d%17d", i + 1, this.bestObjVal[i], this.bestGradNorm[i], this.nIter[i], this.compTime[i]);
		System.out.print("   ");
		
		for (int c = 0; c < this.bestPoint.get(i).length; c++) {
			
			System.out.format("%.4f", this.bestPoint.get(i)[c]);
			
			//print comma and space if not final number
			if (c != (this.bestPoint.get(i).length - 1)) {
				System.out.print(", ");
			}
		}
		
		System.out.print("\n");
		
	}
	
}
