package functions;

import java.util.function.Function;

public class _DLogisticFunction implements Function<Double, Double> {

	@Override
	public Double apply(Double x) {
		return 1.0/(2.0 + Math.exp(x) + Math.exp(-x));
	}

}
