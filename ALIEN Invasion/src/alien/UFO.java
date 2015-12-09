package alien;

import java.util.ArrayList;

import alien.Model.ModelGenerator;
import animals.Cow;
import utilities.Instance;
import utilities.AnimalPQ;

/**
 * Algorithm.
 * @author christy, derek
 *
 */
public class UFO {
	private ArrayList<Instance> _data;
	private AnimalPQ _animals;
	private Heuristic _heuristic;
	private ModelGenerator _generator;

	public UFO(ArrayList<Instance> data, AnimalPQ animals, Heuristic heur, ModelGenerator generator) {
		_data = data;
		_animals = animals;
		_heuristic = heur;
		_generator = generator;
	}

	/** Depending on current state, add some animals to _animals */
	public void insertAnimals() {
		// TODO Auto-generated method stub
		if (_animals.size() == 0) {
			Animal calf = new Cow(_data, _generator.genNRandom(1));
			_animals.add(calf);
		}
	}
	
	
}
