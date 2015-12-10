package ufos;

import java.util.ArrayList;
import java.util.Random;

import utilities.AnimalPQ;
import utilities.Instance;
import alien.Heuristic;
import alien.Model.ModelGenerator;
import alien.UFO;

public class UFO1 extends UFO {
	private double _spawn_rate;
	private boolean _firstSpawn;
	private Random _rand;
	
	/** probability of drawing from certain distributions
	 * does not have to sum to 1
	 * current distributions: quasi-uniform guassian, guassian mixture, cauchy mixture
	 */
	private ArrayList<Double> _rawDistPs;

	public UFO1(ArrayList<Instance> data, AnimalPQ animals, Heuristic heur,
			ModelGenerator generator) {
		super(data, animals, heur, generator);
		init();
	}
	private void init() {
		_spawn_rate = 0.2;
		_firstSpawn = true;
		_rand = new Random();
	}
	
	public void setSpawnRate(double rate) {
		_spawn_rate = rate;
	}
	public void setSeed(long seed) {
		_rand = new Random(seed);
	}
	
	@Override
	public void insertAnimals() {
		if (_firstSpawn) {
			_firstSpawn = false;
		} else {
			double r = _rand.nextDouble();
			if (r >= _spawn_rate)
				return;
		}
		
		double Z = 0.0;
		for (double p : _rawDistPs) {
			Z += p;
		}
		
		double r = Z*_rand.nextDouble();
		int dist=0;
		for (; dist<_rawDistPs.size()-1; dist++) {
			r -= _rawDistPs.get(dist);
			if (r <= 0) 
				break;
		}
	}

}
