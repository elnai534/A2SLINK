package implementations;

import java.util.NoSuchElementException;

/**
 * MyQueue.java
 *
 * Implementation of a Queue using MyDLL as the underlying data structure.
 *
 * @param <T> the type of elements held in this queue
 */
public class MyQueue<T> implements QueueADT<T> {
    private MyDLL<T> dll; // Underlying doubly linked list to store queue elements

    /**
     * Default constructor initializing the queue.
     */
    public MyQueue() {
        dll = new MyDLL<>();
    }

    /**
     * Adds an element to the end of the queue.
     *
     * @param element the element to be added to the queue
     * @throws IllegalArgumentException if the element is null
     */
    @Override
    public void enqueue(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot enqueue null element.");
        }
        dll.add(element);
    }

    /**
     * Removes and returns the element at the front of the queue.
     *
     * @return the element removed from the front of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    @Override
    public T dequeue() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty. Cannot dequeue element.");
        }
        return dll.remove(0);
    }

    /**
     * Returns the element at the front of the queue without removing it.
     *
     * @return the element at the front of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    @Override
    public T peek() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty. Cannot peek element.");
        }
        return dll.get(0);
    }

    /**
     * Checks if the queue is empty.
     *
     * @return true if the queue is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return dll.isEmpty();
    }

    /**
     * Returns the number of elements in the queue.
     *
     * @return the number of elements in the queue
     */
    @Override
    public int size() {
        return dll.size();
    }

    /**
     * Removes all elements from the queue.
     */
    public void clear() {
        dll.clear();
    }

    /**
     * Returns an iterator for this queue.
     *
     * @return an iterator to traverse the queue
     */
    public Iterator<T> iterator() {
        return dll.iterator();
    }

    /**
     * Returns a string representation of the queue.
     *
     * @return a string representation of the queue
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("MyQueue: [");
        Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
