package alien;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

public abstract class Heuristic implements Comparator<Animal> {
	protected HashMap<String, Double> _parameters;
	
	public Heuristic(HashMap<String, Double> params) {
		_parameters = params;
	}
	
	public void addParam(Entry<String, Double> new_param) {
		_parameters.put(new_param.getKey(), new_param.getValue());
	}
	
	public abstract double getHeuristic(Animal entity);
	
	public int compare(Animal o1, Animal o2) {
		return (int) Math.signum(this.getHeuristic(o1) - this.getHeuristic(o2));
	}
	
}
