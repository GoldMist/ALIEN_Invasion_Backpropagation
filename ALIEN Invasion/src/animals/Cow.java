package animals;

import java.util.ArrayList;
import java.util.Random;

import utilities.Instance;
import alien.Animal;
import alien.Model;

public class Cow extends Animal {
	public static final double STEP_TIME = 1.0;
	public static final double ETA_DEFAULT = 0.05;

	private int NPAT;
	private int _sgd_examples;
	
	private int patno;
	private int[] order;
	
	private double _eta;
	
	private double _prevEta = 0.0;
	private Model  _prevGrad = null;
	private double _prevError = 0.0;
	private double _age = 0.0;
	
	public Cow(ArrayList<Instance> data, Model params_init) {
		super(data, params_init);
		Cow_init(ETA_DEFAULT);
	}
	public Cow(ArrayList<Instance> data, Model params_init, double eta) {
		super(data, params_init);
		Cow_init(eta);
	}
	public Cow(ArrayList<Instance> data, Model params_init, double eta, int sgd_examples) {
		super(data, params_init);
		Cow_init(eta);
	}
	private void Cow_init(double eta) {
		NPAT = _data.size();
		_sgd_examples = 1;
		patno = 0;
		_eta = eta;
		
		reorder();
	}
	private void reorder() {
		Random rand = new Random();
		ArrayList<Integer> torder = new ArrayList<Integer>(NPAT);
		for (int i=0; i<NPAT; i++) {
			torder.add(i);
		}
		
		order = new int[NPAT];
		for (int i=0; i<NPAT; i++) {
			int idx = rand.nextInt(NPAT-i);
			order[i] = torder.get(idx);
			torder.remove(idx);
		}
	}

	@Override
	public void step() {
		ArrayList<Instance> gradInstances = new ArrayList<Instance>(1);
		gradInstances.add(_data.get(order[patno]));
		patno++;
		if (patno >= NPAT) {
			patno=0;
			reorder();
		}
		
		Model grad = _params.getGradient(gradInstances);
		
		_prevError = grad.getPrevError();
		
		_prevEta = _eta;
		_prevGrad = grad;
		_params.add(grad, _eta);
		
		_age += Cow.STEP_TIME;
	}
	
	@Override
	public double getPrevError() {
		return _prevError;
	}
	
	@Override
	public double getEpochError() {
		return _params.getEpochError(_data);
	}

	@Override
	public double getUncertainty() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public double getAge() {
		return _age;
	}

	@Override
	public double getGradEuclidean() {
		return _prevEta*_prevGrad.getEuclideanNorm();
	}
	@Override
	public double getGradManhattan() {
		return _prevEta*_prevGrad.getManhattanNorm();
	}
	
	@Override
	public double getStepTime() {
		return Cow.STEP_TIME;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "COW:\n" + _params.toString();
	}
    @Override
    public String getType() {
        return "Cow";
    }

}
