package functions;

import java.util.function.Function;

public abstract class Derivable implements Function<Double, Double> {

	@Override
	public abstract Double apply(Double x);
	
	public abstract Function<Double,Double> derivative();

}
