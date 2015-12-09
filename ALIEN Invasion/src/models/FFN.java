package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.function.Function;

import functions.ArctanFunction;
import functions.Derivable;
import functions.LogisticFunction;
import functions.SquareFunction;
import utilities.Instance;
import utilities.Matrix;
import alien.Model;

public class FFN extends Model {
	public static final String ERROR_FUNCTION = "*FFN:ERROR FUNCTION*";
	public static final String ERROR_FUNCTION_SS = "ss";
	public static final String ERROR_FUNCTION_CE = "ce";
	
	public static final String AFUNCT = "*FFN:AFUNCT*";
	public static final String AFUNCT_LOGISTIC = "logistic";
	public static final String AFUNCT_ARCTAN   = "arctan";
	
	private FFNGenerator _generator;
	
	private HashMap<String, Object> mode;
	private Derivable _ErrorFunction;
	private Derivable _afunct;
	private ArrayList<Integer> _num_units;
	
	private ArrayList<Matrix> _weights;
	private ArrayList<Matrix> _biases;
	private int _paramLayers;
	
	private double _prevError = 0.0;

	public static class FFNGenerator extends ModelGenerator {
		private FFN _parent;
		private Random _rand;
		private ArrayList<Integer> _num_units;
		private Derivable _ErrorFunction;
		private Derivable _afunct;
		private HashMap<String, Object> _mode;

		public FFNGenerator(ArrayList<Integer> num_units, HashMap<String, Object> mode) {
			_num_units = num_units;
			_ErrorFunction = (Derivable)mode.get(FFN.ERROR_FUNCTION);
			_afunct = (Derivable)mode.get(FFN.AFUNCT);
			_parent = null;
			_rand = new Random();
		}
		public FFNGenerator(FFN parent) {
			_parent = parent;
			_num_units = _parent._num_units;
			_rand = new Random();
		}
		public FFNGenerator(FFN parent, int rng) {
			_parent = parent;
			_rand = new Random(rng);
		}
		
		@Override
		public FFN genNRandom(double sigma) {
			FFN next = new FFN(_num_units);
			
			//TODO: can optimize here
			for (int layer=0; layer<_num_units.size()-1; layer++) {
				for (int row=0; row<_num_units.get(layer+1); row++) {
					
					for (int col=0; col<_num_units.get(layer); col++) {
						next._weights.get(layer).set(row, col, sigma*_rand.nextGaussian());
					}
					
					next._biases.get(layer).set(row, sigma*_rand.nextGaussian());
				}
			}
			
			next._ErrorFunction = this._ErrorFunction;
			next._afunct = this._afunct;
			
			return next;
		}

		@Override
		public FFN genRandom(double sigma) {
			FFN next = new FFN(_num_units);
			
			//TODO: can optimize here
			for (int layer=0; layer<_num_units.size()-1; layer++) {
				for (int row=0; row<_num_units.get(layer+1); row++) {
					
					for (int col=0; col<_num_units.get(layer); col++) {
						next._weights.get(layer).set(row, col, sigma*_rand.nextDouble());
					}
					
					next._biases.get(layer).set(row, sigma*_rand.nextDouble());
				}
			}
			
			next._ErrorFunction = this._ErrorFunction;
			next._afunct = this._afunct;
			
			return next;
		}

		@Override
		public FFN genZero() {
			FFN next = new FFN(_num_units);

			next._ErrorFunction = this._ErrorFunction;
			next._afunct = this._afunct;
			
			return next;
		}
		
	}

	
	public FFN(ArrayList<Integer> num_units) {
		_num_units = num_units;
		init(new HashMap<String, Object>());
	}
	public FFN(ArrayList<Integer> num_units, HashMap<String, Object> mode) {
		_num_units = num_units;
		init(mode);
	}
	private void init(HashMap<String, Object> mode) {
		_ErrorFunction = new SquareFunction();
		if (mode.containsKey(FFN.ERROR_FUNCTION)) {
			if (mode.get(FFN.ERROR_FUNCTION).equals(FFN.ERROR_FUNCTION_CE)) {
				System.err.println("(ERROR) FFN: CROSS ENTROPY UNIMPLEMENTED");
				System.exit(1);
			}
		}
		
		_afunct = new LogisticFunction();
		if (mode.containsKey(FFN.AFUNCT)) {
			if (mode.get(FFN.AFUNCT).equals(FFN.AFUNCT_ARCTAN)) {
				_afunct = new ArctanFunction();
			}
		}
		
		_generator = new FFNGenerator(this);
		
		_paramLayers = _num_units.size()-1;
		
		_weights = new ArrayList<Matrix>();
		_biases  = new ArrayList<Matrix>();
		for (int layer=0; layer<_paramLayers; layer++) {
			_weights.add(new Matrix(_num_units.get(layer+1), _num_units.get(layer)));
			_biases.add( new Matrix(_num_units.get(layer+1), 1));
		}
		
		validate();
	}
	private void validate() {
	}

	@Override
	public Model getGradient(ArrayList<Instance> pat) {
		// TODO Auto-generated method stub
		int npat = pat.size();
		
		FFN grad = _generator.genZero();
		
		for (int patno = 0; patno<npat; patno++) {
			
			Matrix ipat = new Matrix(_num_units.get(0), 1);
			Matrix tpat = new Matrix(_num_units.get(_paramLayers), 1);
			for (Entry<Integer, Double> feature : pat.get(patno)._features) {
				ipat.set(feature.getKey(), feature.getValue());
			}
			for (Entry<Integer, Double> classification : pat.get(patno)._classification) {
				tpat.set(classification.getKey(), classification.getValue());
			}
			System.out.println("ipat: " + ipat.transpose());
			System.out.println("tpat: " + tpat.transpose());
			
			ArrayList<Matrix> netinput = new ArrayList<Matrix>(_paramLayers+1);
			ArrayList<Matrix> act = getActivation(ipat, netinput);
			/*for (int i=1; i<_paramLayers+1; i++) {
				System.out.println("netinput.get(" + i + ")=...");
				System.out.print(netinput.get(i));
				System.out.println("____________________");
			}*/
			for (int i=1; i<_paramLayers+1; i++) {
				System.out.println("act.get(" + i + ")=...");
				System.out.print(act.get(i));
				System.out.println("____________________");
			}
			
			Matrix err = tpat.plus(act.get(_paramLayers), -1);
			_prevError = tpat.applyAll(_ErrorFunction).sum();
			
			ArrayList<Matrix> delta = getDeltas(netinput, act, err);
			/*for (int i=1; i<_paramLayers+1; i++) {
				System.out.println("delta.get(" + i + ")=...");
				System.out.print(delta.get(i));
				System.out.println("____________________");
			}*/
			
			for (int pLayer=0; pLayer<_paramLayers; pLayer++) {
				grad._weights.set(pLayer, delta.get(pLayer+1).times(act.get(pLayer).transpose()));
				grad._biases.set( pLayer, delta.get(pLayer+1));
			}
		}
		
		return grad;
	}

	@Override
	public double getPrevError() {
		return _prevError;
	}
	
	@Override
	public double getEpochError(ArrayList<Instance> pat) {
		double ans = 0.0;
		
		int npat = pat.size();
		
		for (int patno = 0; patno<npat; patno++) {
			
			Matrix ipat = new Matrix(_num_units.get(0), 1);
			Matrix tpat = new Matrix(_num_units.get(_paramLayers), 1);
			for (Entry<Integer, Double> feature : pat.get(patno)._features) {
				ipat.set(feature.getKey(), feature.getValue());
			}
			for (Entry<Integer, Double> classification : pat.get(patno)._classification) {
				tpat.set(classification.getKey(), classification.getValue());
			}
			
			ArrayList<Matrix> netinput = new ArrayList<Matrix>(_paramLayers+1);
			ArrayList<Matrix> act = getActivation(ipat, netinput);
			
			Matrix err = tpat.plus(act.get(_paramLayers), -1);
			ans += err.applyAll(_ErrorFunction).sum();
		}
		
		return ans;
	}

	@Override
	public FFN add(Model grad, double eta) {
		FFN grad2 = (FFN)grad;
		
		for (int layer=0; layer<_paramLayers; layer++) {
			
			_weights.set(layer, _weights.get(layer).plus(grad2._weights.get(layer), eta));
			
			_biases.set(layer, _biases.get(layer).plus(grad2._biases.get(layer), eta));
		}
		
		return this;
	}
	
	private ArrayList<Matrix> getActivation(Matrix ipat, ArrayList<Matrix> netinput) {
		ArrayList<Matrix> act = new ArrayList<Matrix>(_paramLayers+1);
		
		act.add(0, new Matrix(_num_units.get(0), 1));
		for (int unit=0; unit<_num_units.get(0); unit++) {
			act.get(0).set(unit, ipat.get(unit));
		}
		
		netinput.add(null);
		for (int layer=1; layer<=_paramLayers; layer++) {
			netinput.add(layer, _weights.get(layer-1).times(act.get(layer-1)).plus(
					_biases.get(layer-1)));
			
			act.add(netinput.get(layer).applyAll(_afunct));
		}
		
		return act;
	}
	
	/** get the deltas given a certain error.
	 * err.get(0) corresponds to the input layer, and is null
	 * @param err
	 * @return
	 */
	private ArrayList<Matrix> getDeltas(ArrayList<Matrix> netinput, ArrayList<Matrix> act, Matrix err) {
		//TODO check math
		ArrayList<Matrix> delta = new ArrayList<Matrix>(_paramLayers+1);
		for (int layer=0; layer<_paramLayers+1; layer++) {
			delta.add(null);
		}
		
		delta.set(_paramLayers, err.applyAll(_ErrorFunction.derivative())
				.elTimes(netinput.get(_paramLayers).applyAll(_afunct.derivative())));
		
		for (int layer=_paramLayers-1; layer > 0; layer--) {
			delta.set(layer, _weights.get(layer).transpose().times(delta.get(layer+1))
					.elTimes(netinput.get(layer).applyAll(_afunct.derivative())));
		}
		
		return delta;
	}
	
	public double getEuclideanNorm() {
		double d2 = 0.0;
		
		for (int layer=0; layer<_paramLayers; layer++) {
			d2 += _weights.get(layer).getEuclideanSquare();
			d2 += _biases.get(layer).getEuclideanSquare();
		}
		
		return Math.sqrt(d2);
	}
	
	public double getManhattanNorm() {
		double d = 0.0;
		
		for (int layer=0; layer<_paramLayers; layer++) {
			d += _weights.get(layer).getManhattanNorm();
			d += _biases.get(layer).getManhattanNorm();
		}
		
		return d;
	}
	
	@Override
	public String toString() {
		String ans = "";
		for (int layer=0; layer<_paramLayers; layer++) {
			ans += _weights.get(layer).toString();
			ans += _biases.get(layer).transpose().toString();
			ans += "-----------------------------------\n";
		}
		
		return ans;
	}

}
