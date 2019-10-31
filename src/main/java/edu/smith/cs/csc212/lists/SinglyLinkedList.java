package edu.smith.cs.csc212.lists;

import me.jjfoley.adt.ListADT;
import me.jjfoley.adt.errors.BadIndexError;
import me.jjfoley.adt.errors.TODOErr;

/**
 * A Singly-Linked List is a list that has only knowledge of its very first
 * element. Elements after that are chained, ending with a null node.
 * 
 * @author jfoley
 *
 * @param <T> - the type of the item stored in this list.
 */
public class SinglyLinkedList<T> extends ListADT<T> {
	/**
	 * The start of this list. Node is defined at the bottom of this file.
	 */
	Node<T> start;

	@Override
	public T removeFront() {
		checkNotEmpty();
		T firstValue = this.start.value;
		this.start = this.start.next;
		return firstValue;
	}

	@Override
	public T removeBack() {
		checkNotEmpty();
		Node<T> remove;
		
		
		if (size() == 1) {
			T firstValue = this.start.value;
			this.start = null;
			return firstValue;
		}
		else {
			Node<T> last = null;
			Node<T> lastNode = null;	
			for (Node<T> current = this.start; current.next != null; current = current.next) {
				lastNode = current;
				last = lastNode.next;
			}
			remove = last;
			lastNode.next = null;
			return remove.value;
		}	
	}
		
	@Override
	public T removeIndex(int index) {
		checkNotEmpty();
		Node<T> delete = null;
		if(index < 0 || index>size()) {
			throw new BadIndexError(index); 
		}
		if (index==0) {
			return removeFront();
		}
		
		else {
			int count = 0;
			for (Node<T> current = this.start; current != null; current = current.next) {
				if (count==index-1) {
					delete = current.next;
					current.next = delete.next;
					return delete.value;
				}
				count++;
			}
			return delete.value;
		}
	}

	@Override
	public void addFront(T item) {
		this.start = new Node<T>(item, start);
	}

	@Override
	public void addBack(T item) {
		//from PPT 9, slide 25
		if (this.start == null) {
			addFront(item);
			return;
		}
		
		Node<T> last = null;
		for (Node<T> current = this.start; current != null; current = current.next) {
			last = current;
		}
		
		assert(last.next == null);
		last.next = new Node<T> (item, null);
	}

	@Override
	public void addIndex(int index, T item) {
		if (index>size() || index<0) {
			throw new BadIndexError(index);
		}
		
		//add to the front
		if (index == 0) {
			this.start = new Node<T> (item, this.start);
		}
		
		//add to the back
		if (index == size()) {
			addBack(item);
			return;
		}
		
		//add index at a specific location 
		if (index<size() && index>0) {
			int count = 0;
			for (Node <T> current = this.start; current != null; current = current.next) {
				if (count == index-1) {
					Node <T> insert = new Node<T> (item, current.next);
					current.next = insert;
					return;
				}
				count++;
			}
		}
	}

	@Override
	public T getFront() {
		checkNotEmpty();
		T firstValue = this.start.value;
		return firstValue;	
	}

	@Override
	public T getBack() {
		checkNotEmpty();
		Node<T> last = null;
		for (Node<T> current = this.start; current != null; current = current.next) {
			last = current;
		}
		return last.value;
	}

	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		int at = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			if (at++ == index) {
				return n.value;
			}
		}
		throw new BadIndexError(index);
	}

	@Override
	public void setIndex(int index, T value) {
		checkNotEmpty();
		
		//if there is no node
		if (this.start == null) {
			this.start = new Node<T> (value, this.start);
		}
		
		if (this.start != null) {
			T replace; //this value will be set at the index, and replacing the old value
			replace = this.getIndex(index); 
			replace = value;
			
			int count = 0;
			for (Node<T> current = this.start; current != null; current = current.next) {
				if (count++ == index) {
					current.value = value;
				}
			}
			
		}
	}

	@Override
	public int size() {
		int count = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			count++;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		return this.start == null;
	}

	/**
	 * The node on any linked list should not be exposed. Static means we don't need
	 * a "this" of SinglyLinkedList to make a node.
	 * 
	 * @param <T> the type of the values stored.
	 */
	private static class Node<T> {
		/**
		 * What node comes after me?
		 */
		public Node<T> next;
		/**
		 * What value is stored in this node?
		 */
		public T value;

		/**
		 * Create a node with no friends.
		 * 
		 * @param value - the value to put in it.
		 * @param next - the successor to this node.
		 */
		public Node(T value, Node<T> next) {
			this.value = value;
			this.next = next;
		}
	}

}
