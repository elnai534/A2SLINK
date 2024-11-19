package utilities;

import java.util.NoSuchElementException;

public class MyDLL<E> implements ListADT<E> {
	private MyDLLNode<E> head;
	private MyDLLNode<E> tail;
	private int size;
	
	public MyDLL() {
		head = null;
		tail = null;
		size = 0;
	}
	
	@Override
	public int size() {
		return size;
	}
	
	@Override
	public void clear() {
		head = null;
		tail = null;
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
		
		MyDLLNode<E> newNode = new MyDLLNode<>(toAdd);
		
		if (index == 0) {
			if (head == null) {
				head = tail = newNode;
			} else {
				newNode.setNext(head);
				head.setPrev(newNode);
				head = newNode;
			}
		} else if (index == size) {
			tail.setNext(newNode);
			newNode.setPrev(tail);
			tail = newNode;
		} else {
			MyDLLNode<E> current = getNodeAt(index);
			newNode.setNext(current);
            newNode.setPrev(current.getPrev());
            current.getPrev().setNext(newNode);
            current.setPrev(newNode);
		}
		
		size++;
		return true;
	}
	
	@Override
	public boolean add(E toAdd) throws NullPointerException {
		return add(size, toAdd);
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
		return getNodeAt(index).getElement();
	}
	
	@Override
	public E remove(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index is out of bounds!");
		}
		
		MyDLLNode<E> toRemove = getNodeAt(index);
		
		E removedElement = toRemove.getElement();
			
		if (toRemove == head) {
			head = head.getNext();
			if (head != null) {
				head.setPrev(null);
			}
		} else if (toRemove == tail) {
			tail = tail.getPrev();
			if(tail != null) {
				tail.setNext(null);
			}
		} else {
			toRemove.getPrev().setNext(toRemove.getNext());
			toRemove.getNext().setPrev(toRemove.getPrev());
		}
		
		if (size == 1) {
			head = null;
			tail = null;
		}
		
		size--;
		return removedElement;
	}
	
	@Override
	public E remove (E toRemove) throws NullPointerException {
		if (toRemove == null) {
			throw new NullPointerException("Element to be removed cannot be null!");
		}
		
		MyDLLNode<E> current = head;
		while (current != null) {
			if (current.getElement().equals(toRemove)) {
				return remove(getIndexOf(current));
			}
			current = current.getNext();
		}
		
		return null;
	}
	
	@Override
	public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
		if (toChange == null) {
			throw new NullPointerException("Cannot set null as an element!");
		}
		
		MyDLLNode<E> node = getNodeAt(index);
		E oldElement = node.getElement();
		node.setElement(toChange);
		return oldElement;
	}
	
	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	
	@Override
	public boolean contains(E toFind) throws NullPointerException {
		if (toFind == null) {
			throw new NullPointerException("Cannot search for null elements!")
		}
		
		MyDLLNode<E> current = head;
		while (current != null) {
			if (current.getElement().equals(toFind)) {
				return true;
			}
			current = current.getNext();
		}
		
		return false;
	}
	
	@Override
	public E[] toArray(E[] toHold) throws NullPointerException {
		if (toHold == null) {
			throw new NullPointerException("Array cannot be null!");
		}
		if (toHold.length < size) {
			toHold = (E[]) new Object[size];
		}
		
		MyDLLNode<E> current = head;
		for (int i = 0; i < size; i++) {
			toHold[i] = current.getElement();
			current = current.getNext();
		}
		
		return toHold;
	}
	
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		MyDLLNode<E> current = head;
		
		for(int i = 0; i < size; i++) {
			array[i] = current.getElement();
			current = current.getNext();
		}
		
		return array;
	}
	
	@Override
	public Iterator<E> iterator() {
		return new MyDLLIterator();
	}
	
	private MyDLLNode<E> getNodeAt(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index is out of bounds!");
		}
		
		MyDLLNode<E> current = (index < size / 2) ? head : tail;
		
		if (index < size/2) {
			for (int i = 0; i < index; i++) {
				current = current.getNext();
			}
		} else {
			for (int i = size - 1; i > index; i--) {
				current = current.getPrev();
			}
		}
		
		return current;
	}
	
	private int getIndexOf(MyDLLNode<E> node) {
		MyDLLNode<E> current = head;
		int index = 0;
		while (current != null) {
			if (current == node) return index;
			current = current.getNext();
			index++;
		}
		return -1;
	}
	
	private class MyDLLIterator implements Iterator<E> {
		private MyDLLNode<E> current = head;
		
		@Override
		public boolean hasNext() {
			return current != null;
		}
		
		@Override
		public E next() throws NoSuchElementException{
			if (!hasNext()) {
				throw new NoSuchElementException("No more elements!");
			}
				E element = current.getElement();
				current = current.getNext();
				return element;
		}
	}
}
