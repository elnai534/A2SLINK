package utilities;

import java.util.NoSuchElementException;

public class MyArrayList<E> implements ListADT<E> {
	private static final int DEFAULT_CAPACITY = 10;
	private E[] data;
	private int size;
	
	@SuppressWarnings("unchecked")
	public MyArrayList() {
		this.data = (E[]) new Object[DEFAULT_CAPACITY];
		this.size = 0;
	}
	
	@Override
	public int size() {
		return size;
	}
	
	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			data[i] = null;
		}
		size = 0;
	}
	
	@Override
	public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
        if (toAdd == null) {
        	throw new NullPointerException("Cannot add null as an element!");
        }
        if (index < 0 || index > size) {
        	throw new IndexOutOfBoundsException("Index is out of bounds!");
        }
        
        ensureCapacity();
        
        for (int i = size; i > index; i--) {
        	data[i] = data[i-1];
        }
        data[index] = toAdd;
        size++;
        return true;
    };
    
    @Override
    public boolean add(E toAdd) throws NullPointerException {
    	if (toAdd == null) {
    		throw new NullPointerException("Cannot add null as an element!");
    	}
    	
    	ensureCapacity();
    	data[size++] = toAdd;
    	return true;
    }
    
    @Override
    public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
    	if (toAdd == null) {
    		throw new NullPointerException("List to add cannot be null!");
    	}
    	
    	for (int i = 0; i < toAdd.size(); i++) {
    		add(toAdd.get(i));
    	}
    	
    	return true;
    }
    
    @Override
    public E get(int index) throws IndexOutOfBoundsException {
    	if (index < 0 || index >= size) {
    		throw new IndexOutOfBoundsException("Index is out of bounds!");
    	}
    	E removedElement = data[index];
    	
    	for (int i = index; i < size - 1; i++) {
    		data[i] = data[i+1];
    	};
    	
    	data[--size] = null;
    	return removedElement;
    }
    
    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index out of bounds!");
		}
		
		E removedElement = data[index];
		
		for (int i = index; i < size - 1; i++) {
			data[i] = data[i+1];
		}
		
		data[--size] = null;
		
		return removedElement;
	}
    
    @Override
    public E remove(E toRemove) throws NullPointerException {
        if (toRemove == null) {
            throw new NullPointerException("Element to remove cannot be null.");
        }
        
        for (int i = 0; i < size; i++) {
            if (data[i].equals(toRemove)) {
                return remove(i);
            }
        }
        
        return null;
    }
    
    @Override
    public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
    	if (toChange == null) {
    		throw new NullPointerException("Cannot add null as an element!");
    	}
    	if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds!"); 
        }
    	
    	E oldElement = data[index];
    	data[index] = toChange;
    	return oldElement;
    }
    
    @Override
    public boolean isEmpty() {
    	return size == 0;
    }
    
    @Override
    public boolean contains(E toFind) throws NullPointerException {
    	if (toFind == null) {
    		throw new NullPointerException("Cannot search for null elements!");
    	}
    	
    	for (int i = 0; i < size; i++) {
    		if (data[i].equals(toFind)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public E[] toArray(E[] toHold) throws NullPointerException {
    	if (toHold == null) {
    		throw new NullPointerException("Array cannot be null!");
    	}
    	if (toHold.length < size) {
    		E[] newArray = (E[]) new Object[size];
    		
    		for (int i = 0; i < size; i++) {
    			newArray[i] = data[i];
    		}
    		return newArray;
    	}
    	
    	for (int i = 0; i < size; i++) {
    		toHold[i] = data[i];
    	}
    	
    	if (toHold.length > size) {
    		toHold[size] = null;
    	}
    	
    	return toHold;
    }
    
    @Override
    public Object[] toArray() {
    	Object[] newArray = new Object[size];
    	for (int i = 0; i < size; i++) {
    		newArray[i] = data[i];
    	}
    	return newArray;
    }
    
    @Override
    public Iterator<E> iterator() {
    	return new MyArrayListIterator();
    }
    
    @SuppressWarnings("unchecked")
    private void ensureCapacity() {
    	if (size == data.length) {
    		E[] newArray = (E[]) new Object[data.length * 2];
    		for (int i = 0; i < data.length; i++)  {
    			newArray[i] = data[i];
    		}
    		data = newArray;
    	}
    }
    
    private class MyArrayListIterator implements Iterator<E> {
    	private int currentIndex = 0;
    	
    	@Override
    	public boolean hasNext() {
    		return currentIndex < size;
    	}
    	
    	@Override
    	public E next() throws NoSuchElementException {
    		if (!hasNext()) {
    			throw new NoSuchElementException("No more elements left to iterate!");
    		}
    		return data[currentIndex++];
    	}
    }
}