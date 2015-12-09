package utilities;

public class Instance {
	
	public FeatureVector _features;
	public ClassificationVector _classification;
	
	public Instance(FeatureVector features, ClassificationVector classification) {
		_features = features;
		_classification = classification;
	}

}
