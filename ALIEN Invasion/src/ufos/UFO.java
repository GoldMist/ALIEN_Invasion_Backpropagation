package ufos;

import heuristics.GradientScaledHeuristic;
import heuristics.LinearHeuristic;

import java.util.ArrayList;
import java.util.Random;

import alien.Animal;
import alien.Heuristic;
import alien.Invasion;
import alien.Model;
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
	protected ArrayList<Instance> _data;
	protected AnimalPQ _animalSelector;
	protected AnimalPQ _animalDeleter;
	protected Heuristic _selectorHeuristic;
	protected Heuristic _deleterHeuristic;
	protected ModelGenerator _generator;
	
	protected int _nextId;

	/*public UFO(ArrayList<Instance> data, AnimalPQ animalSelector, AnimalPQ animalDeleter, Heuristic heur, ModelGenerator generator) {
		_data = data;
		_animalSelector = animalSelector;
		_animalDeleter  = animalDeleter;
		_heuristic = heur;
		_generator = generator;
	}*/

	public UFO(ArrayList<Instance> data,
			Heuristic selectorHeuristic,
			Heuristic deleterHeuristic, ModelGenerator generator,
			int max_animals) {
		// TODO Auto-generated constructor stub
		
		_data = data;
		_selectorHeuristic = selectorHeuristic;
		_deleterHeuristic  = deleterHeuristic;
		_generator = generator;
		_nextId = 0;
		
		_animalSelector = new AnimalPQ(_selectorHeuristic);
		_animalDeleter = new AnimalPQ(_deleterHeuristic);
	}

	/** Depending on current state, add some animals to _animals */
	public void insertAnimals() {
		// TODO Auto-generated method stub
		if (_animalSelector.size() == 0) {
			Animal calf = new Cow(_data, _generator.genNRandom(1));
			calf.step();
			this.addAnimal(calf);
		}
	}
	
	public void step() {
		if (Invasion.VERBOSITY >= 2 && _animalSelector.size() <= 0) {
			System.err.println("UFO.step(): stepping with no animals");
			return;
		}
		
		Invasion.println(3, "step-start");
		Animal mover = _animalSelector.getRoot();
		Invasion.println(3, "found mover:"+ mover);
		
		mover.step();
		Invasion.println(3, "stepped mover:" + mover);
		_animalSelector.updateRootKey(_selectorHeuristic.getHeuristic(mover));
		Invasion.println(3, "updated root key");
		_animalDeleter.update(mover);
		Invasion.println(3, "step-end");
	}
	
	protected void addAnimal(Animal entity) {
		//Invasion.println(1, _animalSelector + ".size: " + _animalSelector.size());
		_animalSelector.add(entity, _nextId);
		//Invasion.println(1, _animalSelector + ".size: " + _animalSelector.size());
		_animalDeleter.add(entity, _nextId);
		
		_nextId++;
	}
	
	public void deleteAnimals() {
	}

	public double getEpochError() {
		return this._animalSelector.getRoot().getEpochError();
	}
}
