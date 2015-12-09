package functions;

import java.util.function.Function;

public class _DSquareFunction implements Function<Double, Double> {

	@Override
	public Double apply(Double x) {
		return 2.0*x;
	}

}
