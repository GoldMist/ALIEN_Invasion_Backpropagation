package functions;

import java.util.function.Function;

public class SquareFunction extends Derivable implements Function<Double, Double> {

	@Override
	public Double apply(Double x) {
		return x*x;
	}

	@Override
	public Function<Double, Double> derivative() {
		return new _DSquareFunction();
	}

}
