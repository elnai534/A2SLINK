/**
 * StackADT.java
 *
 * This interface defines the basic functionalities of a stack data structure.
 * A stack is a collection that supports LIFO (Last In, First Out) access pattern.
 * It allows pushing elements onto the stack, popping elements off the stack,
 * peeking at the top element, checking if the stack is empty, and retrieving the size of the stack.
 *
 * @param <E> The type of elements in this stack.
 */
public interface StackADT<E> {

    /**
     * Pushes an element onto the top of the stack.
     * 
     * Precondition: The element to be pushed is not null.
     * Postcondition: The element is added to the top of the stack.
     * 
     * @param element The element to be added to the stack.
     * @throws IllegalArgumentException if the element is null.
     */
    void push(E element) throws IllegalArgumentException;

    /**
     * Removes and returns the top element from the stack.
     * 
     * Precondition: The stack is not empty.
     * Postcondition: The top element is removed and returned.
     * 
     * @return The top element that was removed from the stack.
     * @throws java.util.NoSuchElementException if the stack is empty.
     */
    E pop() throws java.util.NoSuchElementException;

    /**
     * Returns (without removing) the top element of the stack.
     * 
     * Precondition: The stack is not empty.
     * Postcondition: The top element remains on the stack.
     * 
     * @return The top element of the stack.
     * @throws java.util.NoSuchElementException if the stack is empty.
     */
    E peek() throws java.util.NoSuchElementException;

    /**
     * Checks whether the stack is empty.
     * 
     * Precondition: None.
     * Postcondition: Returns true if the stack contains no elements, false otherwise.
     * 
     * @return true if the stack is empty, false otherwise.
     */
    boolean isEmpty();

    /**
     * Returns the number of elements in the stack.
     * 
     * Precondition: None.
     * Postcondition: The size of the stack is returned.
     * 
     * @return The number of elements in the stack.
     */
    int size();
}