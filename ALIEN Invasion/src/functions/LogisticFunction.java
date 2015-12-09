package functions;

import java.util.function.Function;

public class LogisticFunction extends Derivable implements Function<Double, Double> {

	@Override
	public Double apply(Double x) {
		return 1.0/(1.0+Math.exp(0.0-x));
	}

	@Override
	public Function<Double, Double> derivative() {
		return new _DLogisticFunction();
	}

	
}
