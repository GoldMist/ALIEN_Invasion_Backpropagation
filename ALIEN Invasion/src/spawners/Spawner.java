package spawners;

import alien.Model;

public abstract class Spawner {
	protected int _hitCount = 0;

	public abstract Model spawn();
	
	public int getHits() { return _hitCount; }
	
	/** Unscaled probability of selecting this spawner */
	public abstract double getWeight();
	
}
