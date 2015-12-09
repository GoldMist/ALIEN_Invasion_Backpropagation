package utilities;

import java.util.LinkedList;

/**
 * A min heap.  For the PriorityQueue implementation.  For our purpose, we do
 * not need to resize the Heap.
 * @author christy
 * @param <T> the data type
 *
 */
public class MinHeap<T extends Comparable<? super T>> {

    /** The start size. */
    private final int startSize = 5;
    
    /** Heap array contianing the data.*/
    private T[] heap;
    /** Capacity of the heap. */
    private int capacity;
    /** Number of items in the array. */
    private int size;
    
    /**
     * Default constructor.
     */
    public MinHeap() {
        
        this.capacity = this.startSize;
        this.size = 0;
        // the heap start at index of 1, not 0
        this.heap = (T[]) new Comparable[this.capacity + 1];
    }
    
    /**
     * Class constructor.
     * @param theCapacity   the capacity that we want
     */
    public MinHeap(int theCapacity) {
        this.capacity = theCapacity;
        this.size = 0;
        // the heap start at index of 1, not 0
        this.heap = (T[]) new Comparable[this.capacity + 1];
    }
    
    
    /**
     * Gets the current number of items in the heap.
     * @return the number of items in the heap
     */
    public int size() {
        return this.size;
    }

    /**
     * Gets the capacity of the heap.
     * @return capacity of the heap
     */
    public int capacity() {
        return this.capacity;
    }
    
    /**
     * Returns whether this is a leaf.
     * @param nodePos   the position of the node
     * @return true if this is a leaf.
     */
    public boolean isLeaf(int nodePos) {
        // return (position is valid) AND (position is in the second half
        //                                  half of the heap array)
        return ((nodePos - 1 < this.size()
                && (nodePos - 1 >= this.size() / 2)));
    }
    
    /**
     * Returns the position of the left child.
     * @param nodePos   the position of the current node
     * @return  the position of the left child, -1 if no left child found.
     */
    public int leftChildPos(int nodePos) {
        
        int index = nodePos * 2;
        
        if (index > this.size) {
            return -1;
        }
        
        return index;
    }
    
    /**
     * Returns the position of the right child.
     * @param nodePos   the position of the current node
     * @return  the position of the right child, -1 if no right child found.
     */
    public int rightChildPos(int nodePos) {
        
        int index = nodePos * 2 + 1;
        
        if (index > this.size) {
            return -1;
        }
        
        return index;
    }
    
    /**
     * Returns the position of the parent.
     * @param nodePos   the position of the current node
     * @return  the position of the parent, -1 if nodPos is not valid,
     *          0 if at the head
     */
    public int parentPos(int nodePos) {
        
        if (nodePos > this.size) {
            return -1;
        }
        
        return nodePos / 2;
    }
    
    /**
     * Inserts an item.
     * @param item the item
     */
    public void insert(T item) {
        
        // resize if necessary
        if (this.size() + 1 > this.capacity()) {
            this.capacity *= 2;
            T[] temp = (T[]) new Comparable[this.capacity + 1];
            
            for (int i = 1; i <= this.size(); i++) {
                temp[i] = this.heap[i];
            }
            
            this.heap = temp;
            
        }
        
        // insert to end
        int currIndex = this.size() + 1;
        this.heap[currIndex] = item;
        this.size++;
        
        while (currIndex > 1) {
            
            // if the item is smaller than its parent, bubble it up
            if (item.compareTo(this.heap[this.parentPos(currIndex)]) < 0) {
            
                T temp = this.heap[this.parentPos(currIndex)];
                this.heap[this.parentPos(currIndex)] = item;
                this.heap[currIndex] = temp;
                currIndex = this.parentPos(currIndex);
            } else {
                // found the location
                return;
            }
        }
        
    }
    
    /**
     * Gets the min item.
     * @return the min item
     */
    public T min() {
        // return the top item in the heap
        // first item is at 1
        
        if (this.size == 0) {
            return null;
        }
        return this.heap[1];
    }
    
    /**
     * Deletes the min item.
     * @return the old min item
     */
    public T deleteMin() {
        
        T min = this.min();        
        
        if (this.size() == 0) {
            return null;
        }

        // replace minItem with last item
        this.heap[1] = this.heap[this.size()];
        this.size--;
        
        if (this.size() == 0) {
            return min;
        }
        
        int currIndex = 1;
        boolean atPos = false;
        
        
        
        while (!this.isLeaf(currIndex) && !atPos) {
            
            int posToCompare = 0;  
            
            if (this.rightChildPos(currIndex) == -1) {
                posToCompare = this.leftChildPos(currIndex);
            } else if (this.heap[this.leftChildPos(currIndex)].compareTo(
                    this.heap[this.rightChildPos(currIndex)]) < 1) {
                posToCompare = this.leftChildPos(currIndex);
                
            } else {
                posToCompare = this.rightChildPos(currIndex);
            }
            
            if (this.heap[currIndex].compareTo(this.heap[posToCompare]) >= 1) {
                T temp = this.heap[currIndex];
                this.heap[currIndex] = this.heap[posToCompare];
                this.heap[posToCompare] = temp;
                currIndex = posToCompare;
            } else {
                atPos = true;
            }
            
        }
           
        return min;
    }
    
    /**
     * To string method for the heap for testing purpose.
     * @return  the string representation of the heap
     */
    public String toString() {
        
        String result = "";
        
        for (int i = 1; i <= this.size; i++) {
            
            result += this.heap[i].toString();
            
            if (i < this.size) {
                result += ", ";
            }
            
        }
        
        return "[" + result + "]";
        
        
    }
    
    /**
     * Gets the heap as a LinkedList.
     * @return the heap
     */
    public LinkedList<T> getHeap() {

        /* T[] result = (T[]) new Comparable[this.size];
        System.arraycopy(this.heap, 1, result, 0, this.size);
        return Arrays.asList(array); */
        
        LinkedList<T> everything = new LinkedList<T>();
        
        for (int i = 1; i <= this.size; i++) {
            everything.addLast(this.heap[i]);
        }
        
        return everything;
    }

    /**
     * Clears the heap.
     */
    public void clear() {
        this.size = 0;
        this.heap = (T[]) new Comparable[this.capacity + 1];
    }
    
    
}
