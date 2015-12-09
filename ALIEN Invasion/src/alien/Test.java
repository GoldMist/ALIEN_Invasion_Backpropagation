package alien;

import functions.*;
import utilities.*;

public class Test {

	public static void main(String[] args) {
		Matrix a = new Matrix(2, 1);
		Matrix b = new Matrix(1, 2);
		
		a.set(0, 2.0);
		a.set(1, 3.0);
		b.set(0, 5.0);
		b.set(1, 7.0);
		
		System.out.println(a.times(b));
		System.out.println(b.times(a));
		//System.out.println(new LogisticFunction().derivative().apply(-1.0));
		System.out.println(System.getProperty("java.version"));
	}
}
