package utilities;

/**
 * 600.226, Fall 2015
 * Starter code for AVLtree implementation
 * As edited by Alex Eusman (Aeusman1)
 * @author Alex
 */
import java.util.Collection;
import java.util.LinkedList;

/**
 * AVL Tree - based on Weiss.
 * @param <T> the base type of data in a node
 *
 */
public class AVLTree<T extends Comparable<? super T>> {

    /**
     * Inner node class.  Do not make this static because you want the T to be
     * the same T as in the BST header.
     */
    public class BNode {

        /** Variable data of type T. */
        protected T data;
        /** Variable left of type BNode. */
        protected BNode left;
        /** Variable right of type BNode. */
        protected BNode right;
        /** Variable height of the node. */
        private int height;

        /**
         * Constructor for BNode.
         * @param val to insert the given node.
         */
        public BNode(T val) {
            this.data = val;
            this.height = 0;
            this.left = null;
            this.right = null;
        }

        /**
         * Returns whether node is a leaf or not.
         * @return true is node is leaf, false if not
         */
        public boolean isLeaf() {
            return (this.left == null) && (this.right == null);
        }
    }

    /** The root of the tree. */
    private BNode root;
    /** The size of the tree. */
    private int size;

    /**
     * Constructs a Binary Search Tree.
     */
    public AVLTree() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Find out how many elements are in the Tree.
     * @return the number
     */
    public int size() {
        return this.size;
    }

    /**
     * See if the Tree is empty.
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (this.size() == 0);
    }

    /**
     * Get the value of the root.
     * @return value of the root
     */
    public T root() {
        if (this.root == null) {
            return null;
        }
        return this.root.data;
    }

    /**
     * Search for an item in the tree.
     * @param val the item to search for
     * @return true if found, false otherwise
     */
    public boolean contains(T val) {
        return (this.contains(val, this.root) != null);
    }

    /**
     * Checks if a tree contains a certain value.
     * @param val the value you're looking for
     * @param curr the root of the tree you're searching
     * @return the node that contains that value
     */
    private BNode contains(T val, BNode curr) {
        if (curr == null || this.isEmpty()) {
            return null;
        }
        if (val.compareTo(curr.data) == 0) {
            return curr;
        }
        if (val.compareTo(curr.data) < 0) {
            return this.contains(val, curr.left);
        }
        return this.contains(val, curr.right);
    }
    

    /**
     * Floor.
     *
     * @param val the val
     * @return the t
     */
    public T floor(T val) {
        if (this.size == 0) {
            return null;
        }
        if (this.size == 1) {
            if (this.root.data.compareTo(val) > -1) {
                return this.root.data;
            } else {
                return null;
            }
        }
        BNode temp = this.floor(val, this.root);
        if ((temp == null) || (temp.data.compareTo(val) < 0)) {
            return null;
        }
        return temp.data;
        
    }
    

    
    /**
     * Floor.
     *
     * @param val the val
     * @param curr the curr
     * @return the b node
     */
    private BNode floor(T val, BNode curr) {
        if (curr == null || this.isEmpty()) {
            return null;
        }
        if (val.compareTo(curr.data) == 0) {
            return curr;
        }
        if (curr.isLeaf()) {
            return null;
        }
        
        if (val.compareTo(curr.data) < 0) {
            return this.floorRight(val, curr);
        } else {
            BNode max = this.findMax(curr.right);
            if ((max != null) && (val.compareTo(max.data) > 0)) {
                return curr;
            }
            BNode temp = this.floor(val, curr.right);
            if (temp == null) {
                return curr.right;
            }
            return temp;
        }
    }
    
    /**
     * Floor right (For avoiding cyclo-complex error).
     *
     * @param val the val
     * @param curr the curr
     * @return the b node
     */
    private BNode floorRight(T val, BNode curr) {
        BNode max = this.findMax(curr.left);
        if ((max != null) && (val.compareTo(max.data) > 0)) {
            return curr;
        }
        BNode temp = this.floor(val, curr.left);
        if (temp == null) {
            return curr.left;
        }
        return temp;
    }

    /**
     * Add an item to the Tree.
     * @param val the item to add
     * @return true if added, false if val is null
     */
    public boolean add(T val) {
        if (val != null) {
            this.root = this.insert(val, this.root);
            this.size++;
            //how you can check whether the root is balanced:
            //System.out.println(Math.abs(balanceFactor(this.root)));
            return true;
        }
        return false;
    }

    /**
     * Helper insert method.
     * @param val the value to insert
     * @param curr the root of the tree
     * @return the node that is inserted
     */
    private BNode insert(T val, BNode curr) {
        BNode temp = curr;
        if (temp == null) { // leaf, make new node
            return new BNode(val);
        }
        if (val.compareTo(temp.data) < 0) {
            temp.left = this.insert(val, temp.left);
            temp.height = this.manHeight(temp, -1);
            temp = this.balance(temp);

        } else {  // val >= temp
            temp.right = this.insert(val, temp.right);
            temp.height = this.manHeight(temp, -1);
            temp = this.balance(temp);

        }
        return temp;
    }

    /**
     * Remove an item from the Tree.
     * @param val the item to remove
     * @return true if removed, false if not found
     */
    public boolean remove(T val) {
        if (this.contains(val)) {
            this.root = this.delete(this.root, val);
            this.size--;
            //how you can check whether the root was balanced:
            //System.out.println(Math.abs(balanceFactor(this.root)))
            return true;
        }
        return false;
    }

    /**
     * Delete root node.
     *
     * @param value the value to be deleted
     * @return the b node value of the new root
     */
    private BNode deleteTop(T value) {
        if (this.root.isLeaf()) {
            return null;
        }
        T newRoot = null;
        if (this.root.right != null) {
            if (this.root.right.isLeaf()) {
                newRoot = this.deleteRoot(this.root.right);
                this.root.right = null;
            } else {
                newRoot = this.deleteRoot(this.root.right);
            }
            
        } else {
            if (this.root.left.isLeaf()) {
                newRoot = this.deleteRoot(this.root.left);
                this.root.left = null;
            } else {
                newRoot = this.deleteRoot(this.root.left);
            }
            
        }
        
        if (this.root.data == newRoot) {
            return null;
        } else {
            this.root.data = newRoot;
            return this.root;
        }
    }

    /**
     * Helper delete method - does the real work.
     *
     * @param curr the root of the subtree to look in
     * @param value the value to delete
     * @return the new subtree after rebalancing
     */
    private BNode delete(BNode curr, T value) {
        
        if (value.compareTo(curr.data) < 0) {
            return this.deleteLeft(curr, value);
        } else if (value.compareTo(curr.data) > 0) {
            return this.deleteRight(curr, value);
        } else if (value.compareTo(curr.data) == 0) {
            return this.deleteTop(value);
        }
        return null;
    }
    
    /**
     * Delete in left subtree.
     *
     * @param curr the curr
     * @param value the value
     * @return the b node
     */
    private BNode deleteLeft(BNode curr, T value) {
        if (curr.left.data == value) {
            //check if subtree
            if (curr.left.isLeaf()) {
                curr.left = null;
                curr = this.balance(curr);
                return curr;
            } else { 
                //assume its a balanced subtree
                //Grab next in order successor, transfer to deletable 
                //root node, delete next order succession
                if (curr.left.right == null) {
                    //if entered this case it means right side 
                    //doesnt exist, insert left value
                    curr.left = curr.left.left;
                    return curr;
                    //stays balanced after this so no need to rebalance
                } else if (curr.left.right.isLeaf()) {
                    curr.left.data = this.deleteRoot(curr.left.right);
                    curr.left.right = null;
                    curr.left = this.balance(curr.left);
                    return curr;
                } else {
                    //If entered this case means can pull a next in line
                    curr.left.data = this.deleteRoot(curr.left.right);
                    return curr;
                }
            }
        } else {
            this.delete(curr.left, value);
            return curr;
        }
    }
    
    /**
     * Delete in right subtree.
     *
     * @param curr the curr
     * @param value the value
     * @return the b node
     */
    private BNode deleteRight(BNode curr, T value) {
        if (curr.right.data == value) {
            //check if subtree
            if (curr.right.isLeaf()) {
                curr.right = null;
                curr = this.balance(curr);
                return curr;
            } else {
                if (curr.right.right == null) {
                    //if entered this case it means right side 
                    //doesnt exist, insert left value
                    curr.right = curr.right.left;
                    return curr;
                    //stays balanced after this so no need to rebalance
                } else if (curr.right.right.isLeaf()) {
                    curr.right.data = this.deleteRoot(curr.right.right);
                    curr.right.right = null;
                    curr.right = this.balance(curr.right);
                    return curr;
                } else {
                    //If entered this case means can pull a next in line
                    curr.right.data = this.deleteRoot(curr.right.right);
                    return curr;
                }
            }
        } else {
            this.delete(curr.right, value);
            return curr;
        }
    }
    
    /**
     * Delete root of a subtree and return new root value.
     *
     * @param curr the root of the old tree
     * @return the new root of the tree value
     */
    private T deleteRoot(BNode curr) {
        if (curr.left == null) {
            if (curr.right != null) {
                T rootOut = curr.data;
                BNode temp = curr.right;
                curr.left = temp.left;
                curr.right = temp.right;
                curr.data = temp.data;
                return rootOut;
            }
            return curr.data;
        }        
        
        if (curr.left.isLeaf()) {
            T temp = curr.left.data;
            curr.left = null;
            return temp;
        }
        
        if (curr.left.left == null) {
            BNode temp = curr.left.right;
            T output = curr.left.data;
            curr.left = temp;
            return output;
        }
        
        T min = this.deleteRoot(curr.left);
        curr.left = this.balance(curr.left);
        
        return min;
    }


    /**
     * Performs balancing of the nodes if necessary.  IMPLEMENT!
     * Only children and grandchildren
     * @param curr the root of the subtree to balance
     * @return the root node of the newly balanced subtree
     */
    private BNode balance(BNode curr) {
        //use balance factor to determine  if balance, on child how to
        curr.height = this.manHeight(curr, -1);
        if (this.isBalanced(curr)) {
            return curr;
        }
        
        if (this.balanceFactor(curr) < 0) {
            if (this.balanceFactor(curr.right) > 0) {
                //LL right left
                BNode temp = this.doubleWithRightChild(curr);
                return temp;
            } else if (this.balanceFactor(curr.right) < 0) {
                //L
                BNode temp = this.rotateWithRightChild(curr);
                return temp;
            }
        } else if (this.balanceFactor(curr) > 0)  {
            if (this.balanceFactor(curr.left) < 0) {
                //RR
                BNode temp = this.doubleWithLeftChild(curr);
                return temp;
            } else if (this.balanceFactor(curr.left) > 0) {
                //R
                BNode temp = this.rotateWithLeftChild(curr);
                return temp;
            }
        }
        return curr;
    }
    
    /**
     * Checks balance of nodes.
     * @param b node to check balance at
     * @return integer that is balance factor
     */
    private int balanceFactor(BNode b) {
        if (b == null) {
            return -1;
        }

        if (b.isLeaf()) {
            return 0;
        }

        return this.height(b.left) - this.height(b.right);
    }

    /**
     * Search from curr (as root of subtree) and find maximum value.
     * @param curr the root of the tree
     * @return the min
     */
    private BNode findMax(BNode curr) {
        BNode temp = curr;
        if (temp == null) {
            return temp;
        }
        while (temp.right != null) {
            temp = temp.right;
        }
        return temp;
    }
    
    private BNode findMin(BNode curr) {
        BNode temp = curr;
        if (temp == null) {
            return temp;
        }
        while (temp.left != null) {
            temp = temp.left;
        }
        return temp;
    }
    
    public T getMin() {
        return this.findMin(this.root).data;
    }
    
    /**
     * Search from root and find max value.
     * @return the max value
     */
    public T getMax() {
 
        BNode max = this.findMax(this.root);
        if (max == null) {
            return null;
        }
        return this.findMax(this.root).data;
    }


    /**
     * Return the height of node t, or -1, if null.
     * @param t the node to find the height of
     * @return int height to be returned
     */
    private int height(BNode t) {
        if (t == null) {
            return -1;
        }
        return t.height;
    }
    
    /**
     * Getting the height manually when height isn't incremented.
     *
     * @param curr the current node
     * @param level the level value 
     * @return the int that is the level at that point
     */
    private int manHeight(BNode curr, int level) {
        if (curr == null) {
            return level;
        }
        
        int right = this.manHeight(curr.right, level);
        int left = this.manHeight(curr.left, level);
        
        if (right > left) {
            return (right + 1);
        } else {
            return (left + 1);
        }
    }

    /**
     * Check whether tree is balanced.
     * @param curr the root of the tree
     * @return true if balanced, false if not
     */
    public boolean isBalanced(BNode curr) {
        if (curr == null) {
            return true;
        }
        boolean left = this.isBalanced(curr.left);
        boolean right = this.isBalanced(curr.right);
        int leftHeight = this.height(curr.left);
        int rightHeight = this.height(curr.right);
        boolean heights = Math.abs(leftHeight - rightHeight) < 2;
        return left && right && heights;
    }

    /**
     * Return maximum of lhs and rhs.
     * @param lhs height of lhs
     * @param rhs height of rhs
     * @return the int that's larger
     */
    private static int max(int lhs, int rhs) {
        if (lhs > rhs) {
            return lhs;
        }
        return rhs;
    }

    /**
     * Rotate binary tree node with left child.
     * Update heights, then return new root.
     * @param k2 node to rotate
     * @return updated node
     */
    private BNode rotateWithLeftChild(BNode k2) {
        if (k2 == null) {
            return null;
        }
        BNode k1 = k2.left;
        if (k1 != null) {
            k2.left = k1.right;
            k1.right = k2;
            k2.height = max(this.height(k2.left), this.height(k2.right)) + 1;
            k1.height = max(this.height(k1.left), k2.height) + 1;
        }
        return k1;
    }

    /**
     * Rotate binary tree node with right child.
     * Update heights, then return new root.
     * @param k1 node to rotate
     * @return updated node
     */
    private BNode rotateWithRightChild(BNode k1) {
        if (k1 == null) {
            return null;
        }
        BNode k2 = k1.right;
        if (k2 != null) {
            k1.right = k2.left;
            k2.left = k1;
            k1.height = max(this.height(k1.left), this.height(k1.right)) + 1;
            k2.height = max(this.height(k2.right), k1.height) + 1;
        }
        return k2;
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child.
     * Update heights, then return new root.
     * @param k3 node to rotate
     * @return update node
     */
    private BNode doubleWithLeftChild(BNode k3) {
        if (k3 != null) {
            k3.left = this.rotateWithRightChild(k3.left);
            return this.rotateWithLeftChild(k3);
        }
        return k3;
    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child.
     * Update heights, then return new root.
     * @param k1 node to rotate
     * @return updated node
     */
    private BNode doubleWithRightChild(BNode k1) {
        if (k1 != null) {
            k1.right = this.rotateWithLeftChild(k1.right);
            return this.rotateWithRightChild(k1);
        }
        return k1;
    }

    /**
     * String representation of the Tree with elements in order.
     * @return a string containing the Tree contents in the format "[1, 5, 6]".
     */
    public String toString() {
        return this.inOrder().toString();
    }

    /**
     * Inorder traversal.
     * @return a Collection of the Tree elements in order
     */
    public Iterable<T> inOrder() {
        return this.inOrder(this.root);
    }

    /**
     * Preorder traversal.
     * @return a Collection of the Tree elements in preorder
     */
    public Iterable<T> preOrder() {
        return this.preOrder(this.root);
    }

    /**
     * Postorder traversal.
     * @return a Collection of the Tree elements in postorder
     */
    public Iterable<T> postOrder() {
        return this.postOrder(this.root);
    }

    /**
     * Generates an in-order list of items.
     * @param curr the root of the tree
     * @return collection of items in order
     */
    private Collection<T> inOrder(BNode curr) {
        LinkedList<T> iter = new LinkedList<T>();
        if (curr == null) {
            return iter;
        }
        iter.addAll(this.inOrder(curr.left));
        iter.addLast(curr.data);
        iter.addAll(this.inOrder(curr.right));
        return iter;
    }

    /**
     * Generates a pre-order list of items.
     * @param curr the root of the tree
     * @return collection of items in preorder
     */
    private Collection<T> preOrder(BNode curr) {
        LinkedList<T> iter = new LinkedList<T>();
        if (curr == null) {
            return iter;
        }
        iter.addLast(curr.data);
        iter.addAll(this.preOrder(curr.left));
        iter.addAll(this.preOrder(curr.right));
        return iter;
    }

    /**
     * Generates a post-order list of items.
     * @param curr the root of the tree
     * @return collection of items in postorder
     */
    private Collection<T> postOrder(BNode curr) {
        LinkedList<T> iter = new LinkedList<T>();
        if (curr == null) {
            return iter;
        }
        iter.addAll(this.postOrder(curr.left));
        iter.addAll(this.postOrder(curr.right));
        iter.addLast(curr.data);
        return iter;
    }
}