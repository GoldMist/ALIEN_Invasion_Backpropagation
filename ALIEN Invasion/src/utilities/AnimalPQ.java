package utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.TreeSet;

import alien.Animal;
import alien.Heuristic;
import alien.Invasion;

/**
 * Max Animal PQ.
 */
public class AnimalPQ {
    public class Entry implements Comparable<Entry>{
        public double _val;
        public Animal _animal;
        public int _id;
        
        public Entry(double val, Animal animal, int id) {
            _val = val;
            _animal = animal;
            _id = id;
        }

        @Override
        public int compareTo(Entry o) {
            // TODO Auto-generated method stub
            
            return ((Double) this._val).compareTo((Double) o._val);
        }
        
    }
    
    private Animal _root;
    private Heuristic heur;
    private ArrayList<AnimalPQ.Entry> tree;
    private int maxAnimals;
    private boolean hasMax;

    
    public AnimalPQ(int maxAnimals, Heuristic heur) {
        this.tree = new ArrayList<AnimalPQ.Entry>();
        this.heur = heur;
        this.hasMax = true;
        this.maxAnimals = maxAnimals;

    }
    
    /** Constructor with no maximum #animals */
    public AnimalPQ(Heuristic heur) {
        this.tree = new ArrayList<AnimalPQ.Entry>();
        this.heur = heur;
        this.hasMax = false;
        this.maxAnimals = 0;
    }

    public int getRootID() {
        if (this.tree.size() == 0) {
            System.err.println("AnimalPQ.getRoot(): ROOT IS NULL");
            return -1;
        }
        return this.tree.get(this.tree.size() - 1)._id;
    }
    
    /**
     * Retrieves root but does not remove it.
     * @return the root
     */
    public Animal getRoot() {
        if (this.tree.size() == 0) {
            System.err.println("AnimalPQ.getRoot(): ROOT IS NULL");
            return null;
        }
        return this.tree.get(this.tree.size() - 1)._animal;
    }

    /** update the key of the item currently at the root */
    public void updateRootKey(double key) {
        if (this.tree.size() == 0) {
            return;
        }

        Entry temp = this.tree.remove(this.tree.size() - 1);
        temp._val = key;
        this.tree.add(temp);
        Collections.sort(this.tree);
        
    }

    public int size() {
        return this.tree.size();
    }
    
    public void add(Animal calf, int id) {
        
        Entry temp = new Entry((this.heur.getHeuristic(calf)), calf, id);
        
        int t = this.size();
        this.tree.add(temp);
        Collections.sort(this.tree);
        if (this.size() != t+1) {
        	System.err.println("AnimalPQ.add(Animal,int): ADD DOES NOT INCREASE SIZE");
        }
       
    }
    
    /** return all Animals with heuristic value greater or equal to 'd' */
    public ArrayList<AnimalPQ.Entry> getBest(double d) {
        
        ArrayList<AnimalPQ.Entry> smaller = new ArrayList<AnimalPQ.Entry>();
        Iterator<AnimalPQ.Entry> it = this.tree.iterator();
        
                
        while (it.hasNext()) {
            AnimalPQ.Entry temp = it.next();
            if (temp._val >= d) {
                smaller.add(temp);
            }
            
        }
        
        return smaller;
        
    }

    /** return heuristic value of the root */
    public double bestVal() {
        
        if (this.tree.size() == 0) {
            System.err.println("AnimalPQ.bestVal(): ROOT IS NULL");
            return 0;
        }
        
        return this.tree.get(this.tree.size() - 1)._val;
    }

    /**
     * Remove max.
     * @return
     */
    public Animal removeRoot() {
        
        if (this.tree.size() == 0) {
            System.err.println("AnimalPQ.removeRoot(): ROOT IS NULL");
            return null;
        }
        
        return this.tree.remove(this.tree.size() - 1)._animal;

    }

    /** find the carcass and remove it. 
     * should require a HashMap to find where the animal is
     * @param carcass
     */
    public void removeAnimal(Animal carcass) {
             
        Iterator<AnimalPQ.Entry> iter = this.tree.iterator();
        
        while (iter.hasNext()) {
            Entry curr = iter.next();
            if (curr._animal == carcass) {
                iter.remove();
                return;
            }
            
        }
        
        
    }

    /**
     * Find an animal and update its value.
     * @param carcass
     */
    public void update(Animal carcass) {
        
        Iterator<AnimalPQ.Entry> iter = this.tree.iterator();
        
        while (iter.hasNext()) {
            Entry curr = iter.next();
            if (curr._animal == carcass) {
                iter.remove();
                curr._val = this.heur.getHeuristic(carcass);
                this.tree.add(curr);
                Collections.sort(this.tree);
                return;
            }
            
        }
        
        
    }
    
    public String idToString() {
        String result = "";
        Iterator<AnimalPQ.Entry> iter = this.tree.iterator();
        
        while (iter.hasNext()) {
            result += iter.next()._id + ", ";
        }
        return result;
    }
    
    public void printString() {
        String result = "";
        Iterator<AnimalPQ.Entry> iter = this.tree.iterator();
        
        System.out.println("STARTING PRINT");
        while (iter.hasNext()) {
            AnimalPQ.Entry temp = iter.next();
            System.out.println(temp._id);
            System.out.println(this.heur.getHeuristic(temp._animal));
        }
        System.out.println("END PRINT");
       
        
    }
    
    
}
