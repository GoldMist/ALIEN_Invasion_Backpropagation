package heuristics;

import java.util.HashMap;

import alien.Animal;
import alien.Heuristic;

public class LinearHeuristic extends Heuristic {
	public static final String PARAM_RAW_ERROR = "*LinearHeuristic:RAW ERROR*";
	public static final String PARAM_AGE = "*LinearHeuristic:AGE*";
	public static final String PARAM_INVERSE_AGE = "*LinearHeuristic:INVERSE AGE*";
	public static final String PARAM_EUCLIDEAN_GRAD_NORM = "*LinearHeuristic:EUCLIDEAN NORM*";
	public static final String PARAM_MANHATTAN_GRAD_NORM = "*LinearHeuristic:MANHATTAN NORM*";
	public static final String PARAM_UNCERTAINTY = "*LinearHeuristic:UNCERTAINTY*";

	public LinearHeuristic(HashMap<String, Double> params) {
		super(params);
	}

	@Override
	public double getHeuristic(Animal entity) {
		double heurVal = 0.0;
		
		heurVal += entity.getPrevError()*getParam(
				LinearHeuristic.PARAM_RAW_ERROR, 0);
		heurVal += entity.getAge()*getParam(
				LinearHeuristic.PARAM_AGE, 0);
		heurVal += (1.0/entity.getAge())*getParam(
				LinearHeuristic.PARAM_INVERSE_AGE, 0);
		heurVal += entity.getGradEuclidean()*getParam(
				LinearHeuristic.PARAM_EUCLIDEAN_GRAD_NORM, 0);
		heurVal += entity.getGradManhattan()*getParam(
				LinearHeuristic.PARAM_MANHATTAN_GRAD_NORM, 0);
		heurVal += entity.getUncertainty()*getParam(
				LinearHeuristic.PARAM_UNCERTAINTY, 0);
		
		heurVal /= entity.getStepTime();
		heurVal *= getParam(Heuristic.PARAM_SCALE, 1);
		
		return heurVal;
	}

}
