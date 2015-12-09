package alien;

import java.util.ArrayList;

import utilities.Instance;

/**
 * Type of network. (Doesn't have to be a network).
 * @author christy
 *
 */
public abstract class Model {
	public static abstract class ModelGenerator {

		/** Create a model with normal random parameters with scale sigma */
		public abstract Model genNRandom(double sigma);
		
		/** Create a model with random parameters with scale sigma */
		public abstract Model genRandom(double sigma);
		
		/** Create a model with 0 parameters */
		public abstract Model genZero();
		
	}
	
	public abstract Model getGradient(ArrayList<Instance> pat);
	
	public abstract double getEpochError(ArrayList<Instance> pat);
	
	/** Add eta times the parameters from 'grad' to 'this'. */
	public abstract Model add(Model grad, double eta);

	/** NOT CURRENT - return error at time of last gradient */
	public abstract double getPrevError();
	
	public abstract double getEuclideanNorm();
	
	public abstract double getManhattanNorm();
}
