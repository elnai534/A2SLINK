package utilities;

import java.util.NoSuchElementException;

/**
 * QueueADT.java
 *
 * @author Sila, Anjhel, Sumaiya
 * @version 1.1
 *
 * Class Definition: This interface represents the public contract for the
 * implementation of a Queue. This data type allows for elements to be 
 * added at the end and removed from the front, following a First-In-First-Out (FIFO) principle.
 */
public interface QueueADT<T> {

    /**
     * Mutator method to add an element to the end of the queue.
     *
     * Precondition: A valid queue object exists, and a non-null value is passed as an argument.
     *
     * Postcondition: The element is added to the end of the queue.
     *
     * @param element the element to be added to the queue
     * @throws IllegalArgumentException if the element is null
     */
    void enqueue(T element);

    /**
     * Mutator method to remove and return the element at the front of the queue.
     *
     * Precondition: A valid queue object exists and is not empty.
     *
     * Postcondition: The front element is removed from the queue.
     *
     * @return the element removed from the front of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    T dequeue() throws NoSuchElementException;

    /**
     * Accessor method to return the element at the front of the queue without removing it.
     *
     * Precondition: A valid queue object exists and is not empty.
     *
     * Postcondition: The front element is returned without modifying the queue.
     *
     * @return the element at the front of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    T peek() throws NoSuchElementException;

    /**
     * Accessor method to check if the queue is empty.
     *
     * Precondition: A valid queue object exists.
     *
     * Postcondition: The method returns whether the queue is empty.
     *
     * @return true if the queue is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Accessor method to return the number of elements in the queue.
     *
     * Precondition: A valid queue object exists.
     *
     * Postcondition: The size of the queue is returned.
     *
     * @return the number of elements in the queue
     */
    int size();
}
