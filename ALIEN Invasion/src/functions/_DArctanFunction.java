package functions;

import java.util.function.Function;

public class _DArctanFunction implements Function<Double, Double> {

	@Override
	public Double apply(Double x) {
		return (2.0/Math.PI)/(1.0+x*x);
	}

}
