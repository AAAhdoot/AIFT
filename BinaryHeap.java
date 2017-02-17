import java.util.Arrays;
import Square.java;
import GridWorld.java;
public class BinaryHeap<Square> implements PriorityQueue<Square> {
    private static final int DEFAULT_CAPACITY = 50;
     Square[] array;
     int size;
    
    /**
     * Constructs a new BinaryHeap.
     */
    //@SuppressWarnings("unchecked")
	public BinaryHeap () {
        // Java doesn't allow construction of arrays of placeholder data types 
        array = new Square[DEFAULT_CAPACITY];  
        size = 0;
    }
    
    
    /**
     * Adds a value to the min-heap.
     */
    public void add(Square square) {
        // grow array if needed
        if (size >= array.length - 1) {
            array = this.resize();
        }        
        
        // place element into heap at bottom
        size++;
        int index = size;
        array[index] = square;
        
        bubbleUp();
    }
    
// //NOTE THAT THE START STATE SHOULD INITIALLY BE IN THE HEAP

public void addFour(Square current){

//assume there is a main heap called heap

    heap.add()


}


// public Square find 



    /**
     * Returns true if the heap has no elements; false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    
    /**
     * Returns (but does not remove) the minimum element in the heap.
     */
    public Square peek() {
        if (this.isEmpty()) {
            throw new IllegalStateException();
        }
        
        return array[1];
    }

    
    /**
     * Removes and returns the minimum element in the heap.
     */
    public Square remove() {
    	// what do want return?
    	Square result = peek();
    	
    	// get rid of the last leaf/decrement
    	array[1] = array[size];
    	array[size] = null;
    	size--;
    	
    	bubbleDown();
    	
    	return result;
    }
    
    
    /**
     * Returns a String representation of BinaryHeap with values stored with 
     * heap structure and order properties.
     */
    public String toString() {
        return Arrays.toString(array);
    }

    
    /**
     * Performs the "bubble down" operation to place the element that is at the 
     * root of the heap in its correct place so that the heap maintains the 
     * min-heap order property.
     */
    public void bubbleDown() {
        int index = 1;
        
        // bubble down
        while (hasLeftChild(index)) {
            // which of my children is smaller?
            int smallerChild = leftIndex(index);
            
            // bubble with the smaller child, if I have a smaller child
            if (hasRightChild(index)
                && array[leftIndex(index)].f_value > array[rightIndex(index)].f_value) {
                smallerChild = rightIndex(index);
            } 
            
            if (array[index].f_value > array[smallerChild].f_value) {
                swap(index, smallerChild);
            } else {
                // otherwise, get outta here!
                break;
            }
            
            // make sure to update loop counter/index of where last el is put
            index = smallerChild;
        }        
    }
    
    
    /**
     * Performs the "bubble up" operation to place a newly inserted element 
     * (i.e. the element that is at the size index) in its correct place so 
     * that the heap maintains the min-heap order property.
     */
    public void bubbleUp() {
        int index = this.size;
        
        while (hasParent(index)
                && (parent(index).f_value > array[index].f_value)) {
            // parent/child are out of order; swap them
            swap(index, parentIndex(index));
            index = parentIndex(index);
        }        
    }
    
    
    public boolean hasParent(int i) {
        return i > 1;
    }
    
    
    public int leftIndex(int i) {
        return i * 2;
    }
    
    
    public int rightIndex(int i) {
        return i * 2 + 1;
    }
    
    
    public boolean hasLeftChild(int i) {
        return leftIndex(i) <= size;
    }
    
    
    public boolean hasRightChild(int i) {
        return rightIndex(i) <= size;
    }
    
    
    public Square parent(int i) {
        return array[parentIndex(i)];
    }
    
    
    public int parentIndex(int i) {
        return i / 2;
    }
    
    
    public Square[] resize() {
        return Arrays.copyOf(array, array.length * 2);
    }
    
    
    public void swap(int index1, int index2) {
        Square tmp = array[index1];
        array[index1] = array[index2];
        array[index2] = tmp;        
    }
}
