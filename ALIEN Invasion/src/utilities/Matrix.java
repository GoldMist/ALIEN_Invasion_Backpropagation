package utilities;

import java.util.function.Function;

public class Matrix {
	private double[][] _data;
	private int _height, _width;
	
	public Matrix(int height, int width) {
		_data = new double[height][width];
		_height = height;
		_width = width;
	}
	public Matrix(double[] vector) {
		_data = new double[vector.length][1];
		_height = vector.length;
		_width = 1;
		
		for (int row=0; row<_height; row++) {
			_data[row][0] = vector[row];
		}
	}
	
	public int[] dim() {
		int[] ret = new int[2];
		ret[0] = _height;
		ret[1] = _width;
		return ret;
	}
	public void set(int row, int col, double val) {
		_data[row][col] = val;
	}
	public void set(int el, double val) {
		if (_width == 1) {
			_data[el][0] = val;
		} else if (_height == 1) {
			_data[0][el] = val;
		} else {
			System.err.println("(ERROR) Matrix.set: TWO-DIMENSIONAL ARRAY REQUIRES TWO INDICES");
			//assert(false);
		}
	}
	
	public double get(int row, int col) {
		return _data[row][col];
	}
	public double get(int el) {
		if (_width == 1) {
			return _data[el][0];
		} else if (_height == 1) {
			return _data[0][el];
		} else {
			System.err.println("(ERROR) Matrix.get: TWO-DIMENSIONAL MATRIX REQUIRES TWO INDICES");
			assert(false);
		}
		return 0; //_data[el][0];
	}
	
	public void apply(int row, int col, Function<Double, Double> f) {
		_data[row][col] = f.apply(_data[row][col]);
	}
	public Matrix applyAll(Function<Double, Double> f) {
		Matrix ans = new Matrix(_height, _width);
		
		for (int row=0 ;row<_data.length; row++) {
			for (int col=0; col<_data[0].length; col++) {
				ans._data[row][col] = f.apply(_data[row][col]);
			}
		}
		
		return ans;
	}
	public double sum() {
		double ans=0.0; 
		
		for (int row=0; row<_height; row++) {
			for (int col=0; col<_width; col++) {
				ans += _data[row][col];
			}
		}
		
		return ans;
	}
	
	public Matrix times(double scale) {
		Matrix ans = new Matrix(_height, _width);
		
		for (int row=0; row<_height; row++) {
			for (int col=0; col<_width; col++) {
				ans._data[row][col] = scale*this._data[row][col];
			}
		}
		
		return ans;
	}
	public Matrix times(Matrix other) {
		assert(this._width == other._height);
		
		int height = this._height;
		int width = other._width;
		int dot = this._width;
		
		Matrix ans = new Matrix(height, width);
		
		for (int i=0; i<height; i++) {
			for (int j=0; j<width; j++) {
				for (int k=0; k<dot; k++) {
					ans._data[i][j] += _data[i][k]*other._data[k][j];
				}
			}
		}
		
		return ans;
	}
	public Matrix elTimes(Matrix other) {
		assert (this._height == other._height && this._width == other._width);
		
		Matrix ans = new Matrix(_height, _width);
		
		for (int row=0; row<_height; row++) {
			for (int col=0; col<_width; col++) {
				ans._data[row][col] = this._data[row][col]*other._data[row][col];
			}
		}
		
		return ans;
	}
	
	public Matrix plus(Matrix other) {
		return plus(other, 1);
	}
	public Matrix plus(Matrix other, double scale) {
		assert (this._width == other._width && this._height == other._height);
		
		Matrix ans = new Matrix(_height, _width);
		
		for (int i=0; i<_height; i++) {
			for (int j=0; j<_width; j++) {
				ans._data[i][j] = this._data[i][j]+scale*other._data[i][j];
			}
		}
		
		return ans;
	}

	public Matrix transpose() {
		Matrix ans = new Matrix(_width, _height);
		
		for (int row=0; row<_height; row++) {
			for (int col=0; col<_width; col++) {
				ans._data[col][row] = this._data[row][col];
			}
		}
		
		return ans;
	}
	
	public double getEuclideanSquare() {
		double d2 = 0.0;
		
		for (int row=0; row<_height; row++) {
			for (int col=0; col<_width; col++) {
				d2 += _data[row][col]*_data[row][col];
			}
		}
		
		return d2;
	}
	
	public double getManhattanNorm() {
		double d = 0.0;
		
		for (int row=0; row<_height; row++) {
			for (int col=0; col<_width; col++) {
				d += _data[row][col];
			}
		}
		
		return d;
	}
	
	
	
	@Override
	public Matrix clone() {
		Matrix ret = new Matrix(this._height, this._width);
		
		for (int row=0; row<_height; row++) {
			for (int col=0; col<_width; col++) {
				ret._data[row][col] = this._data[row][col];
			}
		}
		
		return ret;
	}
	
	@Override
	public String toString() {
		String ans = "";
		
		for (int row=0; row<_height; row++) {
			
			for (int col=0; col<_width; col++) {
				ans += _data[row][col] + "\t";
			}
			ans += "\n";
		}
		
		return ans;
	}
}
