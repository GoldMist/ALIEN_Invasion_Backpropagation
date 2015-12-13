package utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;

import alien.Animal;
import alien.Heuristic;

/**
 * Max Animal PQ.
 */
public class AnimalPQAVL {
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
    private AVLTree<AnimalPQAVL.Entry> avl;
    private int maxAnimals;
    private boolean hasMax;
   // private HashMap<Animal, Double> animalToKey;
    
    public AnimalPQAVL(int maxAnimals, Heuristic heur) {
        this.avl = new AVLTree();
        this.heur = heur;
        this.hasMax = true;
        this.maxAnimals = maxAnimals;
     //   this.animalToKey = new HashMap<Animal, Double>();
    }
    
    /** Constructor with no maximum #animals */
    public AnimalPQAVL(Heuristic heur) {
        this.avl = new AVLTree();
        this.heur = heur;
        this.hasMax = false;
        this.maxAnimals = 0;
    //    this.animalToKey = new HashMap<Animal, Double>();
    }

    /**
     * Retrieves root but does not remove it.
     * @return the root
     */
    public Animal getRoot() {
        return this.avl.getMax()._animal;
    }

    /** update the key of the item currently at the root */
    public void updateRootKey(double key) {
        // remove from avl tree
        Entry temp = this.avl.getMax();
        this.avl.remove(temp);
        // update key
        temp._val = key;
        // put back in
        this.avl.add(temp);
       // this.animalToKey.replace(temp._animal, temp._val);
        
    }

    public int size() {
        return this.avl.size();
    }
    
    public void add(Animal calf) {
        
        if (this.hasMax && (this.avl.size() >= this.maxAnimals)) {
            this.avl.remove(this.avl.getMin());
        }
        
        Entry temp = new Entry((this.heur.getHeuristic(calf)), calf);
        this.avl.add(temp);
       // this.animalToKey.put(temp._animal, temp._val);
    }
    
    /** return all Animals with heuristic value greater or equal to 'd' */
    public ArrayList<AnimalPQAVL.Entry> getBest(double d) {
        // TODO Auto-generated method stub
        Iterable<AnimalPQAVL.Entry> it = this.avl.postOrder();
        Iterator<AnimalPQAVL.Entry> iter = it.iterator();
        ArrayList<AnimalPQAVL.Entry> result = new ArrayList<AnimalPQAVL.Entry>();
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
        return this.avl.getMax()._val;
    }

    public Animal removeRoot() {
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
        // double key = this.animalToKey.get(carcass);
        Iterable<AnimalPQAVL.Entry> it = this.avl.postOrder();
        Iterator<AnimalPQAVL.Entry> iter = it.iterator();
        ArrayList<AnimalPQAVL.Entry> result = new ArrayList<AnimalPQAVL.Entry>();
        this.avl = new AVLTree();
        while (iter.hasNext()) {
            Entry temp = iter.next();
            if (temp._animal != carcass) {
                this.avl.add(temp);
            }
        }
        
    }

    /**
     * Find an animal and update its value.
     * @param carcass
     */
    public void update(Animal carcass) {
        Iterable<AnimalPQAVL.Entry> it = this.avl.postOrder();
        Iterator<AnimalPQAVL.Entry> iter = it.iterator();
        ArrayList<AnimalPQAVL.Entry> result = new ArrayList<AnimalPQAVL.Entry>();
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
