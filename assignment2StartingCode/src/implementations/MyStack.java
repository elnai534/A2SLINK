package implementations;

import utilities.MyArrayList;
import utilities.StackADT;
import java.util.NoSuchElementException;

/**
 * Implementation of a Stack using MyArrayList as the underlying data structure.
 * 
 * @param <T> the type of elements held in this stack
 */
public class MyStack<T> implements StackADT<T> {
    private MyArrayList<T> arrayList;

    /**
     * Default constructor initializing the stack.
     */
    public MyStack() {
        arrayList = new MyArrayList<>();
    }

    /**
     * Pushes an item onto the top of this stack.
     * 
     * @param element the item to be pushed onto this stack
     * @throws NullPointerException if the element is null
     */
    @Override
    public void push(T element) {
        if (element == null) {
            throw new NullPointerException("Cannot add null element to the stack.");
        }
        arrayList.add(element);
    }

    /**
     * Removes the object at the top of this stack and returns that object as the value.
     * 
     * @return the object at the top of this stack
     * @throws NoSuchElementException if the stack is empty
     */
    @Override
    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty. Cannot pop element.");
        }
        return arrayList.remove(arrayList.size() - 1);
    }

    /**
     * Looks at the object at the top of this stack without removing it.
     * 
     * @return the object at the top of this stack
     * @throws NoSuchElementException if the stack is empty
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty. Cannot peek element.");
        }
        return arrayList.get(arrayList.size() - 1);
    }

    /**
     * Tests if this stack is empty.
     * 
     * @return true if this stack is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return arrayList.isEmpty();
    }

    /**
     * Returns the number of elements in this stack.
     * 
     * @return the number of elements in this stack
     */
    @Override
    public int size() {
        return arrayList.size();
    }

}
