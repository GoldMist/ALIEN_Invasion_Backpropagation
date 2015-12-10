package functions;

import java.util.function.Function;

public class OffsetInverseFunction implements Function<Double, Double> {
	private double _scale;
	private double _offset;
	
	public OffsetInverseFunction(double scale, double offset) {
		_scale = scale;
		_offset = offset;
	}

	@Override
	public Double apply(Double arg0) {
		return _scale/(_offset+arg0);
	}

}
