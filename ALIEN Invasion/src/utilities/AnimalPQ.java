package utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.TreeSet;

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
            // TODO Auto-generated method stub
            return ((Double) this._val).compareTo((Double) o._val);
        }
        
    }
    
    private Animal _root;
    private Heuristic heur;
    private TreeSet<AnimalPQ.Entry> tree;
    private int maxAnimals;
    private boolean hasMax;

    
    public AnimalPQ(int maxAnimals, Heuristic heur) {
        this.tree = new TreeSet<AnimalPQ.Entry>();
        this.heur = heur;
        this.hasMax = true;
        this.maxAnimals = maxAnimals;

    }
    
    /** Constructor with no maximum #animals */
    public AnimalPQ(Heuristic heur) {
        this.tree = new TreeSet<AnimalPQ.Entry>();
        this.heur = heur;
        this.hasMax = false;
        this.maxAnimals = 0;
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
        return this.tree.last()._animal;
    }

    /** update the key of the item currently at the root */
    public void updateRootKey(double key) {
        
        if (this.tree.size() == 0) {
            return;
        }

        Entry temp = this.tree.pollLast();
        temp._val = key;
        this.tree.add(temp);
        
    }

    public int size() {
        return this.tree.size();
    }
    
    public void add(Animal calf) {
        
        if (this.hasMax && (this.tree.size() >= this.maxAnimals)) {
            this.tree.pollFirst();
        }
        
        Entry temp = new Entry((this.heur.getHeuristic(calf)), calf);
        this.tree.add(temp);
    }
    
    /** return all Animals with heuristic value greater or equal to 'd' */
    public ArrayList<AnimalPQ.Entry> getBest(double d) {
        
        Entry start = this.tree.ceiling(new Entry(d, null));
        if (start == null) {
            return new ArrayList<AnimalPQ.Entry>();
        }
        TreeSet<AnimalPQ.Entry> subSet = (TreeSet<Entry>) this.tree.tailSet(start);
        ArrayList<AnimalPQ.Entry> list = new ArrayList<AnimalPQ.Entry>();
        while (!subSet.isEmpty()) {
            list.add(subSet.pollFirst());
        }
        
        return list;
    }

    /** return heuristic value of the root */
    public double bestVal() {
        
        if (this.tree.size() == 0) {
            System.err.println("AnimalPQ.bestVal(): ROOT IS NULL");
            return 0;
        }
        
        return this.tree.pollLast()._val;
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
        
        return this.tree.pollLast()._animal;

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
                return;
            }
            
        }
        
        
    }
    
    
}
