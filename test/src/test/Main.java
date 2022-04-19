package test;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

class LinkedSet<E> extends AbstractSet<E> {
	protected int numElements;
	protected Node<E> firstNode;

// default constructor that creates a new set
// that is initially empty
	public LinkedSet() {
		super();
		numElements = 0;
		firstNode = null;
	}

// constructor for creating a new set which
// initially holds the elements in the collection c
	public LinkedSet(Collection<? extends E> c) {
		this();
		for (E element : c) {
			add(element);
		}
	}

// adds the element to the set provided that it
// is not already in the set, and returns
// true if the set did not already contain the element
	public boolean add(E o) {
		if (!(contains(o))) {
			Node<E> newNode = new Node<E>(o);
// add the new node to the front of the list
			newNode.next = firstNode;
			firstNode = newNode;
			numElements++;
			return true;
		} else
			return false;
	}

//remove the element from the set and returns true if the
//element was in the set
	public boolean remove(Object o) { // search for the node of the element o in the set
		boolean found = false;
		if (firstNode != null) { // check if the element is first in list
			if ((firstNode.element == null && o == null)
					|| (firstNode.element != null && firstNode.element.equals(o))) {
				found = true;
				firstNode = firstNode.next;
				numElements--;
			} else { // check the other nodes in the list
				Node<E> previous = firstNode;
				Node<E> current = firstNode.next;
				while (current != null && !found) {
					if ((current.element == null && o == null)
							|| (current.element != null && current.element.equals(o))) {
						found = true;
						previous.next = current.next;
						numElements--;
					} else {
						previous = current;
						current = current.next;
					}
				}
			}
		}
		return found;
	}

//returns an iterator for iterating through the elements in the set
	public Iterator<E> iterator() {
		return new LinkedIterator<E>(firstNode);
	}

//returns the number of elements in the set
	public int size() {
		return numElements;
	}

//removes all elements from the set
	public void clear() {
		firstNode = null;
		numElements = 0;
	}

//inner class which represents a node in a singly-linked list
	protected class Node<E> {
		public E element;
		public Node<E> next;

		public Node(E element) {
			this.element = element;
			next = null;
		}
	}

	// inner class which represents an iterator for a singly-linked list
	private class LinkedIterator<E> implements Iterator<E> {
		private Node<E> nextNode; // next node to use for the iterator
		// constructor which accepts a reference to first node in list
		// and prepares an iterator which will iterate through the
		// entire linked list

		public LinkedIterator(Node<E> firstNode) {
			nextNode = firstNode; // start with first node in list
		}

		// returns whether there is still another element
		public boolean hasNext() {
			return (nextNode != null);
		}

		// returns the next element or throws a NoSuchElementException
		// it there are no further elements
		public E next() throws NoSuchElementException {
			if (!hasNext())
				throw new NoSuchElementException();
			E element = nextNode.element;
			nextNode = nextNode.next;
			return element;
		}

		// remove method not supported by this iterator
		public void remove() throws UnsupportedOperationException {
			throw new UnsupportedOperationException();
		}
	}
}

class LinkRetainRemoveSet<E extends Comparable<E>> extends LinkedSet<E> {
	public LinkRetainRemoveSet() {
		super();
	}

	public LinkRetainRemoveSet(Collection<? extends E> c) {
		super(c);
	}

	@Override
	public boolean add(E o) {
		Node<E> newNode = new Node<E>(o);

		Node<E> temp = firstNode;
		Node<E> prev = null;

		while (temp != null && temp.element.compareTo(o) < 0) {
			prev = temp;
			temp = temp.next;
		}

// if other such element exists
		if (temp != null && temp.element.compareTo(o) == 0)
			return false;

// if to be added before head
		if (prev == null) {
			newNode.next = firstNode;
			firstNode = newNode;
		} else {
			newNode.next = prev.next;
			prev.next = newNode;
		}

		numElements++;
		return true;
	}

	public LinkRetainRemoveSet<E> retain(E start, E end) {
		LinkRetainRemoveSet<E> set = new LinkRetainRemoveSet<E>();

		Node<E> temp = firstNode;
		numElements = 0;

		if (start != null) {
			while (temp != null && temp.element.compareTo(start) != 0) {
				set.add(temp.element);
				temp = temp.next;
				firstNode = firstNode.next;
			}
		}

		Node<E> prev = temp;

		while (temp != null && temp.element.compareTo(end) != 0) {
			prev = temp;
			temp = temp.next;
			numElements++;
		}

		prev.next = null;

		while (temp != null) {
			set.add(temp.element);
			temp = temp.next;
		}

		return set;
	}

	public LinkRetainRemoveSet<E> remove(E start, E end) {
		LinkRetainRemoveSet<E> set = new LinkRetainRemoveSet<E>();

		Node<E> temp = firstNode;
		Node<E> prev = null;

		numElements = 0;

		if (start != null) {
			while (temp != null && temp.element.compareTo(start) != 0) {
				prev = temp;
				temp = temp.next;
				numElements++;
			}
		}

		while (temp != null && temp.element.compareTo(end) != 0) {
			set.add(temp.element);
			temp = temp.next;

			if (prev != null)
				prev.next = temp;

			else {
				firstNode = firstNode.next;
			}
		}

		while (temp != null) {
			temp = temp.next;
			numElements++;
		}

		return set;
	}
}

public class Main {

	public static void main(String args[]) {
		LinkRetainRemoveSet<Integer> set = new LinkRetainRemoveSet<>();

		set.add(1);
		set.add(2);
		set.add(3);
		set.add(4);
		set.add(5);
		set.add(6);
		set.add(7);

		System.out.println("Set = " + set);

		LinkRetainRemoveSet<Integer> retainSet = set.retain(2, 6);
		System.out.println("retain (2, 6)");

		System.out.println("Set = " + set);

		System.out.println("Returned Set = " + retainSet);

		set.add(1);
		set.add(6);
		set.add(7);

		System.out.println("Set = " + set);

		retainSet = set.remove(null, 4);
		System.out.println("Remove(null, 4)");
		System.out.println("Set = " + set);

		System.out.println("Returned Set = " + retainSet);
	}
}
