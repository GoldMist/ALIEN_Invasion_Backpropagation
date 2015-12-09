package utilities;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Iterator;

public class FeatureVector implements Iterable<Entry<Integer, Double>> {
	private HashMap<Integer, Double> myFeatures;

	public FeatureVector() {
		myFeatures = new HashMap<Integer, Double>();
	}
	public FeatureVector(double[] values) {
		myFeatures = new HashMap<Integer, Double>();
		for (int i=0; i<values.length; i++) {
			myFeatures.put(i, values[i]);
		}
	}
	
	public void add(int idx, double value) {
		myFeatures.put(idx, value);
	}
	
	public double get(int idx) {
		if (!myFeatures.containsKey(idx))
			return 0;
		
		return myFeatures.get(idx);
	}
	
	@Override
	public Iterator<Entry<Integer, Double>> iterator() {
		return myFeatures.entrySet().iterator();
	}
	
}
