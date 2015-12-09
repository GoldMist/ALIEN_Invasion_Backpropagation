package functions;

import java.util.function.Function;

public class ArctanFunction extends Derivable implements Function<Double, Double> {

	@Override
	public Double apply(Double x) {
		return Math.atan(x)*2.0/Math.PI;
	}

	@Override
	public Function<Double, Double> derivative() {
		return new _DArctanFunction();
	}

}
