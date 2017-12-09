import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    
    private class Node {
        Item item;
        Node prev;
        Node next;
        
        public Node (Item i) {
            this.item = i;
            this.prev = null;
            this.next = null;
        }
    }
    
    private int size;
    private Node head, tail;
    
    public Deque() {
        size = 0;
        head = null;
        tail = null;
    }
    
    public boolean isEmpty() {
        return size() == 0;
    }
    
    public int size() {
        return size;
    }
    
    public void addFirst(Item item) {
        if(item == null) {
            throw new java.lang.IllegalArgumentException("null argument");
        }
        if(size == 0) {
            head = new Node(item);
            tail = head;
        }
        else {
            Node temp = new Node(item);
            temp.next = head;
            head.prev = temp;
            head = temp;
        }
        size++;
    }
    
    public void addLast(Item item) {
        if(item == null) {
            throw new java.lang.IllegalArgumentException("null argument");
        }
        if(size == 0) {
            Node temp = new Node(item);
            head = temp;
            tail = temp;
        }
        else {
            Node temp = new Node(item);
            temp.prev = tail;
            tail.next = temp;
            tail = temp;
        }
        size++;
    }
    
    public Item removeFirst() {
        if(size == 0) {
            throw new java.util.NoSuchElementException("Deque is empty");
        }
        if(size == 1) {
            Node temp = head;
            head = null;
            tail = null;            
            size = 0;
            return temp.item;
        }
        Node temp = head;
        head = head.next;
        size--;
        return temp.item;
    }
    
    public Item removeLast() {
        if(size == 0) {
            throw new java.util.NoSuchElementException("Deque is empty");
        }
        if(size == 1) {
            Node temp = head;
            head = null;
            tail = null;
            size = 0;
            return temp.item;
        }
        Node temp = tail;
        tail = tail.prev; 
        size--;
        return temp.item;
    }
    
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    
    private class DequeIterator implements Iterator<Item> {
        private Node current;
        
        public DequeIterator() {
            this.current = head;
        }        
        
        public boolean hasNext() {
            return current != null;
        }
        public Item next() {
            if(!this.hasNext()) {
                throw new java.util.NoSuchElementException("End of dequeu");
            }
            Node node = current;
            current = current.next;
            return node.item;
        }
        public void remove() {
            throw new java.lang.UnsupportedOperationException("Not supported");
        }
    }
}
    
        