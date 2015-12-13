package utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;

import alien.Animal;
import alien.Heuristic;

/**
 * Max Animal PQ.
 */
public class AnimalPQ {
    public class Entry implements Comparable<Entry>{
        public double _val;
        public Animal _animal;
        
        public Entry(double val, Animal animal) {
            _val = val;
            _animal = animal;
        }

        @Override
        public int compareTo(Entry o) {
            return ((Double) this._val).compareTo((Double) o._val);
        }
        
    }
    
    private Animal _root;
    private Heuristic heur;
    private AVLTree<AnimalPQ.Entry> avl;
    private int maxAnimals;
    private boolean hasMax;
    
    public AnimalPQ(int maxAnimals, Heuristic heur) {
        this.avl = new AVLTree();
        this.heur = heur;
        this.hasMax = true;
        this.maxAnimals = maxAnimals;

    }
    
    /** Constructor with no maximum #animals */
    public AnimalPQ(Heuristic heur) {
        this.avl = new AVLTree();
        this.heur = heur;
        this.hasMax = false;
        this.maxAnimals = 0;

    }

    /**
     * Retrieves root but does not remove it.
     * @return the root
     */
    public Animal getRoot() {
        if (this.avl.size() == 0) {
            return null;
        }

        return this.avl.getMax()._animal;
    }

    /** update the key of the item currently at the root */
    public void updateRootKey(double key) {
        
        if (this.avl.size() == 0) {
            return;
        }
        
        // remove from avl tree
        Entry temp = this.avl.getMax();
        this.avl.remove(temp);
        // update key
        temp._val = key;
        // put back in
        this.avl.add(temp);

        
    }

    public int size() {
        return this.avl.size();
    }
    
    public void add(Animal calf) {
        
        if (calf == null) {
            System.out.println("WUTTTTTT");
        }
        
        if (this.hasMax && (this.avl.size() >= this.maxAnimals)) {
            this.avl.remove(this.avl.getMin());
        }
        
        Entry temp = new Entry((this.heur.getHeuristic(calf)), calf);
        this.avl.add(temp);
        
    }
    
    /** return all Animals with heuristic value greater or equal to 'd' */
    public ArrayList<AnimalPQ.Entry> getBest(double d) {
        
        Iterable<AnimalPQ.Entry> it = this.avl.postOrder();
        Iterator<AnimalPQ.Entry> iter = it.iterator();
        ArrayList<AnimalPQ.Entry> result = new ArrayList<AnimalPQ.Entry>();
        while (iter.hasNext()) {
            Entry temp = iter.next();
            if (temp._val >= d) {
                result.add(temp);
            }
        }
        
        return result;
    }

    /** return heuristic value of the root */
    public double bestVal() {
        
        if (this.avl.size() == 0) {
            return 0;
        }

        return this.avl.getMax()._val;
    }

    public Animal removeRoot() {
        
        if (this.avl.size() == 0) {
            return null;
        }
        
        Entry temp = this.avl.getMax();
        this.avl.remove(temp);
        // this.animalToKey.remove(temp._animal);
        return temp._animal;
        
    }

    /** find the carcass and remove it. 
     * should require a HashMap to find where the animal is
     * @param carcass
     */
    public void removeAnimal(Animal carcass) {

        Iterable<AnimalPQ.Entry> it = this.avl.postOrder();
        Iterator<AnimalPQ.Entry> iter = it.iterator();
        Entry toRemove = null;
        while (iter.hasNext()) {
            Entry temp = iter.next();
            
            if (temp._animal == carcass) {
                
                if ((toRemove == null) || (toRemove._val >= temp._val)) {
                    toRemove = temp;
                }
                
            }
        }
        
        if (toRemove != null) {
            this.avl.remove(toRemove);
        }


    }

    /**
     * Find an animal and update its value.
     * @param carcass
     */
    public void update(Animal carcass) {

        Iterable<AnimalPQ.Entry> it = this.avl.postOrder();
        Iterator<AnimalPQ.Entry> iter = it.iterator();
        ArrayList<AnimalPQ.Entry> result = new ArrayList<AnimalPQ.Entry>();
        this.avl = new AVLTree();
        while (iter.hasNext()) {
            Entry temp = iter.next();
            if (temp._animal == carcass) {
                temp._val = this.heur.getHeuristic(carcass);
            }
            this.avl.add(temp);
        }

        
    }
   
    
    
}
