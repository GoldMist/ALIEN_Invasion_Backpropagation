package heuristics;

import java.util.HashMap;

import alien.Animal;
import alien.Heuristic;

public class GradientScaledHeuristic extends Heuristic {

	public GradientScaledHeuristic(HashMap<String, Double> params) {
		super(params);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getHeuristic(Animal entity) {
		// TODO Auto-generated method stub

	    
		double h = this.getParam(Heuristic.PARAM_SCALE, -1.0);
		// System.out.println("entity.getGradEuclidean() " + entity.getGradEuclidean());
		h *= entity.getGradEuclidean();
		// System.out.println("8 - entity.getEpochError()" + (8 - entity.getEpochError()));
		h *= 8 - entity.getEpochError();
		
		return h;
	}

}
