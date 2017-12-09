import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private int size;
    private Item[] container;
    private int max;
    
    
    public RandomizedQueue() {
        size = 0;
        max = 1;
        container = (Item[]) new Object[max];
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int size() {
        return size;
    }
    
    private void resizeArray(int newSize) {
        Item[] newArray = (Item[]) new Object[newSize];
        int j = 0; 
        for(int i = 0; i < container.length; i++) {
            if(j == newSize) {
                break;
            }
            if(container[i] != null) {
                newArray[j++] = container[i];
            }
        }
        container = newArray;
    }
    
    public void enqueue(Item item){
        if(item == null) {
            throw new java.lang.IllegalArgumentException("null argument");
        }
        if(size == max) {
            max *= 2;
            resizeArray(max);            
        }
        //System.out.println(size);
        container[size++] = item;
    }
    
    public Item dequeue() {
        if(size == 0) {
            throw new java.util.NoSuchElementException("Queue is empty");
        }
        int idx = StdRandom.uniform(max);
        while(container[idx] == null) {
            idx = StdRandom.uniform(max);
        }
        Item item = container[idx];
        container[idx] = null;
        size--;
        if(size <= (int) (0.25 * max) && (int) (max * 0.5) > 0) {
            max *= 0.5;
            resizeArray(max);
        }
        return item;
    }
    
    public Item sample(){
        if(size == 0) {
            throw new java.util.NoSuchElementException("Queue is empty");
        }
        int idx = StdRandom.uniform(max);
        while(container[idx] == null) {
            idx = StdRandom.uniform(max);
        }
        return container[idx];
    }
    
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }
    
    private class RandomIterator implements Iterator<Item> {
        private Item[] itItems;
        private int left;
        
        public RandomIterator() {
            itItems = (Item[]) new Object[size];
            left = size;
            int j = 0;
            for(int i = 0; i < max; i++) {
                if(j == size) {
                    break;
                }
                if(container[i] != null) {
                    itItems[j++] = container[i];
                }
            }
        }
        
        public boolean hasNext() {
            return left != 0;
        }
        public Item next() {
            if(!hasNext()) {
                throw new java.util.NoSuchElementException("no items left");
            }
            int rand = StdRandom.uniform(size);
            while(itItems[rand] == null) {
                rand = StdRandom.uniform(size);
            }
            Item elem = itItems[rand];
            itItems[rand] = null;
            left--;
            return elem;
        }
        public void remove() {
            throw new java.lang.UnsupportedOperationException("Not supported");
        }
    }
    
//    public static void main(String[] args) {
//         RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
//         System.out.println(rq.size());
//         rq.enqueue(81);
//         System.out.println(rq.size());
//         rq.dequeue();
//         System.out.println(rq.size());
//         rq.isEmpty();
//         System.out.println(rq.size());
//         rq.enqueue(550);
//    }
    
}
    