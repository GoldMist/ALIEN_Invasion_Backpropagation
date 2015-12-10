package utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

import alien.Animal;
import alien.Heuristic;

/**
 * Max Animal PQ.
 */
public class AnimalPQ {
	public class Entry {
		public double _val;
		public Animal _animal;
	}
	private Animal _root;
	
	private PriorityQueue<PQInnerClass<Animal>> maxHeap;

	public AnimalPQ(int maxAnimals, Heuristic heur) {
		//TODO: added signature
	    this.maxHeap = new PriorityQueue<PQInnerClass<Animal>>(Collections.reverseOrder());
	}
	
	/** Constructor with no maximum #animals */
	public AnimalPQ(Heuristic heur) {
		//TODO: added signature
	    this.maxHeap = new PriorityQueue<PQInnerClass<Animal>>(Collections.reverseOrder());
	}

	/**
	 * Retrieves root but does not remove it.
	 * @return the root
	 */
	public Animal getRoot() {
		return this.maxHeap.peek().getItem();
	}

	/** update the key of the item currently at the root */
	public void updateRootKey(double key) {
	    PQInnerClass<Animal> temp = this.maxHeap.poll();
	    temp.setPNum(key);
	    this.maxHeap.add(temp);
		
	}

	public int size() {
		return this.maxHeap.size();
	}
	
	public void add(Animal calf) {
	    
	    double key = 0;
	    if (this.maxHeap.size() != 0) {
	        key = this.maxHeap.peek().getPNum() + 1;

	    }
	    this.maxHeap.add(new PQInnerClass<Animal>(calf, key));
	}
	
	/** return all Animals with heuristic value greater or equal to 'd' */
	public ArrayList<AnimalPQ.Entry> getBest(double d) {
		// TODO Auto-generated method stub
		return null;
	}

	/** return heuristic value of the root */
	public double bestVal() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Animal removeRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	/** find the carcass and remove it. 
	 * should require a HashMap to find where the animal is
	 * @param carcass
	 */
	public void removeAnimal(Animal carcass) {
		// TODO Auto-generated method stub
		
	}

	
}
