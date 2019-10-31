package edu.smith.cs.csc212.lists;

import me.jjfoley.adt.ListADT;
import me.jjfoley.adt.errors.BadIndexError;
import me.jjfoley.adt.errors.TODOErr;

/**
 * A Doubly-Linked List is a list based on nodes that know of their successor and predecessor.
 * @author jfoley
 *
 * @param <T>
 */
public class DoublyLinkedList<T> extends ListADT<T> {
	/**
	 * This is a reference to the first node in this list.
	 */
	private Node<T> start;
	/**
	 * This is a reference to the last node in this list.
	 */
	private Node<T> end;
	
	/**
	 * A doubly-linked list starts empty.
	 */
	public DoublyLinkedList() {
		this.start = null;
		this.end = null;
	}
	
	
	@Override
	public T removeFront() {
		checkNotEmpty();
		T firstValue = this.start.value;
		//T endValue = this.end.value;
		this.start = this.start.after;
		return firstValue;
	}

	@Override
	public T removeBack() {
		checkNotEmpty();
		
		//if size is 1
		if (size() == 1) {
			T firstValue = this.start.value;
			this.start = null;
			return firstValue;
		} else {
			T endValue = this.end.value;
			this.end = this.end.before;
			this.end.after = null;	
			return endValue;
		}
	}

	@Override
	public T removeIndex(int index) {
		//throw new TODOErr();
		checkNotEmpty();
		T remove = null;
		
		//if index is outside of range, throw error
		if (index>size() || index < 0) {
			throw new BadIndexError(index);
		}
		
		//remove at front
		if (index == 0) {
			return removeFront();
		}
		
		//remove from back of list
		if (index == size()) {
			return removeBack();
		}
		
		//remove from the middle
		else {
			int count = 0;
			for (Node<T> current = this.start; current != null; current = current.after) {
				if (count == index - 1) {
					Node<T> delete = current.after;
					current = current.before;	
				}	
			} count++;
		} return remove;
	}

	@Override
	public void addFront(T item) {
		//PPT 9, slide 35
		
		//if there is nothing at start 
		if (start == null) {
			start = end = new Node<T> (item);
		} else {
			Node<T> second = start;
			start = new Node<T> (item);
			start.after = second;
			second.before = start;
		}
	}

	@Override
	public void addBack(T item) {
		//PPT 9, slide 35
		if (end == null) {
			start = end = new Node<T>(item);
		} else {
			Node<T> secondLast = end;
			end = new Node<T>(item);
			end.before = secondLast;
			secondLast.after = end;
		}
	}

	@Override
	public void addIndex(int index, T item) {
		
		//if index is outside the range 
		if (index>size() || index<0) {
			throw new BadIndexError(index);
		}
		
		//empty list 
		if (start == null) {
			start = end = new Node<T> (item);
		}
		
		//front of the list
		if (index == 0) {
			addFront(item);
		}
		
		//back of the list
		if (index == size()) {
			addBack(item);
			return;
		}
		
		//middle of the list 
			// update left and right nodes
		if (index < size() && index > 0) {
			int count = 0;
			for (Node<T> current = this.start; current != null; current = current.after) {
				if(count == index - 1) {
					Node<T> add = new Node<T>(item);
					current.before = add;
					current = current.after;
				}
			} count++;
		}
		
	}

	@Override
	public T getFront() {
		checkNotEmpty();
		T first = this.start.value;
		return first;
	}

	@Override
	public T getBack() {
		checkNotEmpty(); 
		T last = this.end.value;
		return last;
	}
	
	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		int count = 0;
		for (Node<T> n = this.start; n != null; n = n.after) {
			if(count++ == index) {
				return n.value;
			}
		}
		throw new BadIndexError(index);
	}
	
	public void setIndex(int index, T value) {
		//throw new TODOErr();
		checkNotEmpty();
		
		//there is no node, empty list
		if (this.start == null) {
			this.start = new Node<T> (value);
		}
		
		if (this.start != null) {
			T replace = this.getIndex(index);
			replace = value;
			
			int at = 0;
			for (Node<T> n = this.start; n != null; n = n.after) {
				if(at++ == index) {
					n.value = value;
				}
			}
			
		}
	}

	@Override
	public int size() {
		//PPT 10, slide 5
		int count = 0;
		for (Node<T> n = this.start; n != null; n = n.after) {
			count++;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		return this.start == null;
	}
	
	/**
	 * The node on any linked list should not be exposed.
	 * Static means we don't need a "this" of DoublyLinkedList to make a node.
	 * @param <T> the type of the values stored.
	 */
	private static class Node<T> {
		/**
		 * What node comes before me?
		 */
		public Node<T> before;
		/**
		 * What node comes after me?
		 */
		public Node<T> after;
		/**
		 * What value is stored in this node?
		 */
		public T value;
		/**
		 * Create a node with no friends.
		 * @param value - the value to put in it.
		 */
		public Node(T value) {
			this.value = value;
			this.before = null;
			this.after = null;
		}
	}
}
