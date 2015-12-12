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
		
		public Entry(double val, Animal animal) {
		    _val = val;
		    _animal = animal;
		}
		
	}
	private Animal _root;
	
	private PriorityQueue<PQInnerClass<Animal>> maxHeap;
	
	Heuristic heur;

	public AnimalPQ(int maxAnimals, Heuristic heur) {
		//TODO: added signature
	    this.maxHeap = new PriorityQueue<PQInnerClass<Animal>>(Collections.reverseOrder());
	    this.heur = heur;
	}
	
	/** Constructor with no maximum #animals */
	public AnimalPQ(Heuristic heur) {
		//TODO: added signature
	    this.maxHeap = new PriorityQueue<PQInnerClass<Animal>>(Collections.reverseOrder());
	    this.heur = heur;
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
	    
	    double key = this.heur.getHeuristic(calf);
	    this.maxHeap.add(new PQInnerClass<Animal>(calf, key));
	}
	
	/** return all Animals with heuristic value greater or equal to 'd' */
	public ArrayList<AnimalPQ.Entry> getBest(double d) {
		// TODO Auto-generated method stub
	    Object[] thing = this.maxHeap.toArray();
	    PQInnerClass<Animal>[] PQthing = (PQInnerClass<Animal>[]) thing;
	    
	    ArrayList<AnimalPQ.Entry> result = new ArrayList<AnimalPQ.Entry>();
	    
	    for (int i = 0; i < PQthing.length; i++) {
	        
	        PQInnerClass<Animal> currAnimal = PQthing[i];
	        
	        if (currAnimal.getPNum() >= d) {
	            
	            result.add(new Entry(currAnimal.getPNum(), currAnimal.getItem()));
	            
	            
	        }
	        
	        
	    }
	    
	    
		return result;
	}

	/** return heuristic value of the root */
	public double bestVal() {
	    return this.maxHeap.peek().getPNum();
	}

	public Animal removeRoot() {
		return this.maxHeap.poll().getItem();
	}

	/** find the carcass and remove it. 
	 * should require a HashMap to find where the animal is
	 * @param carcass
	 */
	public void removeAnimal(Animal carcass) {
		// TODO Auto-generated method stub
	    
	    Object[] thing = this.maxHeap.toArray();
        PQInnerClass<Animal>[] PQthing = (PQInnerClass<Animal>[]) thing;
        
        this.maxHeap.clear();
        
        for (int i = 0; i < PQthing.length; i++) {
            if (PQthing[i].getItem() != carcass) {
                
                this.maxHeap.add(PQthing[i]);
                
            }
        }

	}

	/**
	 * Find an animal and update its value.
	 * @param carcass
	 */
	public void update(Animal carcass) {
	    this.removeAnimal(carcass);
	    this.add(carcass);
	    
	}
	
	
}
