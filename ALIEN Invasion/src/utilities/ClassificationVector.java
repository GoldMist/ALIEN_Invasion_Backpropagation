package utilities;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class ClassificationVector implements Iterable<Entry<Integer, Double>> {
	private HashMap<Integer, Double> myFeatures;

	public ClassificationVector() {
		myFeatures = new HashMap<Integer, Double>();
	}
	public ClassificationVector(int classification) {
		myFeatures = new HashMap<Integer, Double>();
		myFeatures.put(classification, 1.0);
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
