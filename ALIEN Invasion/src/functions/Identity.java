package functions;

import java.util.function.Function;

public class Identity implements Function<Double, Double> {
	
	private double _scale;
	
	public Identity() {
		_scale = 1.0;
	}
	
	public Identity(double scale) {
		_scale = scale;
	}

	@Override
	public Double apply(Double t) {
		return _scale*t;
	}

}
