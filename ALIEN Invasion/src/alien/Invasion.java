package alien;

import heuristics.GradientScaledHeuristic;
import heuristics.LinearHeuristic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;

import alien.Model.ModelGenerator;
import models.FFN;
import ufos.UFO;
import ufos.UFO1;
import utilities.AnimalPQ;
import utilities.ClassificationVector;
import utilities.FeatureVector;
import utilities.Instance;
import functions.*;

/**
 * Driver.
 * @author Derek, christy
 *
 */
public class Invasion {
	private static final String DATA_FILE = "xor.data";
	private static final int MAX_ANIMALS = 1;
	private static final long RUNTIME_MILLIS = 1000;
	private static final int[] RAW_FFN_LAYERS = {2, 2, 2};
	private static ArrayList<Integer> numUnits;
	
	//private static ArrayList<Instance> _data;

	public static void main(String[] args) {
		Invasion_init();
		//_data = importData(DATA_FILE);
		
		//Heuristic myHeuristic = new LinearHeuristic(new HashMap<String, Double>());
		
		UFO master = defineUFO(); //new UFO(data, myAnimals, myHeuristic, generator);
		
		ArrayList<Long> x_time = new ArrayList<Long>();
		ArrayList<Double> y_epochError = new ArrayList<Double>();
		
		long t_start = System.currentTimeMillis();
		int ep = 0;
		while (System.currentTimeMillis() - t_start < RUNTIME_MILLIS) {
			System.out.println("inserting");
		    master.insertAnimals();
		    
			//System.out.println("\n ___--RESULT(" + ep + ")--___\n");
			// x_time.add(System.currentTimeMillis() - t_start);
		    
		    if (ep%100 == 0) {
		    	x_time.add(System.currentTimeMillis() - t_start);
		    	y_epochError.add(master.getEpochError());
		    }
		    
		    master.step();
			System.out.println("stepping");
		    
		    master.deleteAnimals();
			System.out.println("deleting");
		    
			//System.out.println(myAnimals.getRoot());
			
			//Animal activeAnimal = myAnimals.getRoot();
			//activeAnimal.step();
			//myAnimals.updateRootKey(myHeuristic.getHeuristic(activeAnimal));
			
			ep++;
		}
		

		
		/*System.out.println("\n ___--RESULT(" + ep + ")--___\n");
		System.out.println(myAnimals.getRoot());
		System.out.println(myAnimals.getRoot().getEpochError());
        x_time.add(System.currentTimeMillis() - t_start);
        y_epochError.add(myAnimals.getRoot().getEpochError());*/
        
        PrettyGraph graph = new PrettyGraph(x_time, y_epochError, "UFO"); //myAnimals.getRoot().getType());
        graph.createGraph();
        
	}
	private static void Invasion_init() {
		numUnits = new ArrayList<Integer>( RAW_FFN_LAYERS.length );
		for (int i=0; i<RAW_FFN_LAYERS.length; i++) {
			numUnits.add(RAW_FFN_LAYERS[i]);
		}
	}

	/** import the data from 'filename' */
	private static ArrayList<Instance> importData(String filename) {
		
	    // highly inefficient and a generally dumb way to do this,
	    // but I'm tired and can't think straight right now.  It works
	    
	    Scanner fromFile = null;
        
        try {
            fromFile = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
            System.exit(1);
        }
        
        int numInput = 0;
        
        if (fromFile.hasNextLine()) {
            String temp = fromFile.nextLine().replaceAll(",", " ");
            
            Scanner readLine = new Scanner(temp);
            
            while (readLine.hasNext()) {
                readLine.next();
                numInput++;
            }
            numInput--;
            readLine.close();
            
            
        }
               
        ArrayList<double[]> ipatArrList = new ArrayList<double[]>();
        ArrayList<Integer> tpatArrList = new ArrayList<Integer>();
        int numEntries = 0;
        
        try {
            fromFile = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
            System.exit(1);
        }
        
        
        while (fromFile.hasNextLine()) {

            String temp = fromFile.nextLine().replaceAll(",", " ");
            System.out.println(temp);
            
            Scanner readLine = new Scanner(temp);
            
            double[] currIpat = new double[numInput];
            
            for (int i = 0; i < numInput; i++) {
                currIpat[i] = readLine.nextDouble();
            }
            
           
            tpatArrList.add(readLine.nextInt());
            ipatArrList.add(currIpat);
            
            readLine.close();
            numEntries++;
        }
        
        double[][] ipat = new double[numEntries][numInput];
        int[] tpat = new int[numEntries];
        
        for (int i = 0; i < numEntries; i++) {
            ipat[i] = ipatArrList.get(i);
            tpat[i] = tpatArrList.get(i);
        }
        
        ArrayList<Instance> instances = new ArrayList<Instance>(4);
        
        for (int i = 0; i < numEntries; i++) {
            instances.add(new Instance(new FeatureVector(ipat[i]), 
                    new ClassificationVector(tpat[i])));
        }
        
        fromFile.close();
        
        return instances;
	    
	    
	    
	}
	
	private static UFO defineUFO() {
		
		HashMap<String, Double> selectorParams = new HashMap<String, Double>();
		selectorParams.put(Heuristic.PARAM_SCALE, -1.0);
		GradientScaledHeuristic selectorHeuristic = new GradientScaledHeuristic(selectorParams);
		
		HashMap<String, Double> deleterParams = new HashMap<String, Double>();
		deleterParams.put(Heuristic.PARAM_SCALE, -1.0);
		deleterParams.put(LinearHeuristic.PARAM_RAW_ERROR, -100.0);
		deleterParams.put(LinearHeuristic.PARAM_INVERSE_AGE, 10000.0);
		deleterParams.put(LinearHeuristic.PARAM_EUCLIDEAN_GRAD_NORM, 50.0);
		LinearHeuristic deleterHeuristic = new LinearHeuristic(deleterParams);

		HashMap<String, Object> mode = new HashMap<String, Object>();
		mode.put(FFN.ERROR_FUNCTION, new SquareFunction());
		mode.put(FFN.AFUNCT, new LogisticFunction());
		ModelGenerator generator = new FFN.FFNGenerator(numUnits, mode);		
		
		return new UFO1(importData(DATA_FILE), selectorHeuristic, deleterHeuristic, generator, MAX_ANIMALS);
	}
}
