package ufos;

import java.util.ArrayList;
import java.util.Random;

import spawners.GaussianMixtureSpawner;
import spawners.GaussianSpawner;
import spawners.Spawner;
import utilities.AnimalPQ;
import utilities.Instance;
import alien.Animal;
import alien.Heuristic;
import alien.Invasion;
import alien.Model.ModelGenerator;
import animals.Cow;

public class UFO1 extends UFO {
	private double _spawn_rate;
	private boolean _firstSpawn;
	private Random _rand;
	
	/** probability of drawing from certain distributions
	 * does not have to sum to 1
	 * current distributions: quasi-uniform guassian, guassian mixture, cauchy mixture
	 */
	private ArrayList<Spawner> _distributions;

	public UFO1(ArrayList<Instance> data,
			Heuristic selectorHeuristic,
			Heuristic deleterHeuristic, ModelGenerator generator,
			int max_animals) {
		super(data, selectorHeuristic, deleterHeuristic, generator, max_animals);
		init();
	}
	private void init() {
		_spawn_rate = 0.2;
		_firstSpawn = true;
		_rand = new Random();
		
		_distributions = new ArrayList<Spawner>();
		_distributions.add(new GaussianSpawner(this._generator, 1.0));
		_distributions.add(new GaussianMixtureSpawner(this._generator, this._animalSelector, 0.2, 0.08));
	}
	
	public void setSpawnRate(double rate) {
		_spawn_rate = rate;
	}
	public void setSeed(long seed) {
		_rand = new Random(seed);
	}
	
	@Override
	public void insertAnimals() {
	    
		ArrayList<Double> rawDistPs = new ArrayList<Double>();
		for (Spawner spawner : _distributions) {
			rawDistPs.add(spawner.getWeight());
		}
		
		int dist = 0;
		
		if (_firstSpawn || _animalSelector.size() <= 0) {
			_firstSpawn = false;
			dist = 0;
		} else {
			double r = _rand.nextDouble();
			//if (r >= 0)//_spawn_rate)
			if (_animalSelector.size() >= 2)
				return;
			double Z = 0.0;
			for (double p : rawDistPs) {
				Z += p;
			}
			
			r = Z*_rand.nextDouble();
			dist=0;
			for (; dist<rawDistPs.size()-1; dist++) {
				r -= rawDistPs.get(dist);
				if (r <= 0) 
					break;
			}
		}
		
		Animal calf = new Cow(_data, _distributions.get(dist).spawn());
		calf.step();
		System.err.println("pre add new animal");
		this.addAnimal(calf);
		System.err.println("post add new animal");
		Invasion.println(1, "added animal " + this._nextId);
	}
	
	@Override
	public void deleteAnimals() {
		if (true) return;
		if (_animalDeleter.size() <= 1) 
			return;

		double pDelete = Math.atan(_animalDeleter.size()/100.0);
		Random rand = new Random();
		if (rand.nextDouble() > pDelete)
			return;
		
		Animal carcass = _animalDeleter.removeRoot();
		_animalSelector.removeAnimal(carcass);
		
	}

}
