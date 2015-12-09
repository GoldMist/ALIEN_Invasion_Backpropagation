package spawners;

import alien.Model;

public class GaussianSpawner extends Spawner {
	private Model.ModelGenerator _gen;
	private double _std;
	
	public GaussianSpawner(Model.ModelGenerator gen, double std) {
		_gen = gen;
		_std = std;
	}

	@Override
	public Model spawn() {
		_hitCount++;
		return _gen.genNRandom(_std);
	}

	@Override
	public double getWeight() {
		return 100.0 + 30*Math.sqrt((double) _hitCount);
	}

}
