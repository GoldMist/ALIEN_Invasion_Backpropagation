package utilities;

/**
* An inner class to hold object data and to tag along a priority
* number.  Not nested inside PQ because checkstyle kept yelling
* at me.
* @author christy
* @param <T>    the data type
*
*/
public class PQInnerClass<T> implements Comparable<PQInnerClass<T>> {
    
    /** The object. */
    T item;
    /** The priority number. */
    double pNum;
    
    /**
     * Class constructor.
     * @param thing the item
     * @param priority the priority number
     */
    public PQInnerClass(T thing, double priority) {
        
        this.item = thing;
        this.pNum = priority;
        
    }
    
    /**
     * Gets the item.
     * @return the item.
     */
    public T getItem() {
        return this.item;
    }
    
    /**
     * Sets the item.
     * @param thing the new item
     * @return the old item
     */
    public T setItem(T thing) {
        T temp = this.item;
        this.item = thing;
        return temp;
    }
    
    /**
     * Gets the priority number.
     * @return priority number
     */
    public double getPNum() {
        return this.pNum;
    }
    
    /**
     * Sets the priority number.
     * @param theNum the new priority number
     * @return the old priority number
     */
    public double setPNum(double theNum) {
        double temp = this.pNum;
        this.pNum = theNum;
        return temp;
    }


    @Override
    public int compareTo(PQInnerClass<T> o) {
        
        if (this.getPNum() > o.getPNum()) {
            return 1;
        } else if (this.getPNum() < o.getPNum()) {
            return -1;
        } else {
            return 0;
        }
    
    }
    
    /**
     * Returns string representation for testing purpose.
     * @return the string representation
     */
    public String toString() {
        return "(" + String.format("%.2f", this.pNum) + ", "
                + this.item.toString() + ")";
    }
    
    @Override
    public int hashCode() {
        return ((Double) this.pNum).hashCode();
    }


}
