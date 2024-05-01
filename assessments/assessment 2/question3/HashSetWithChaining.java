package question3;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class HashSetWithChaining<E> implements Set<E> {

	private int numElements;
	private Node<E>[] buckets;
	private double loadFactor = 0.75;

	@SuppressWarnings("unchecked")
	public HashSetWithChaining(int capacity) {
		this.buckets = new Node[capacity];
		this.numElements = 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean add(E o) {
		int index = hashCode(o.hashCode());
		Node<E> current = buckets[index];

		while (current != null) {
			if (current.element.equals(o)) {
				return false;
			}
			current = current.next;
		}
		Node<E> newNode = new Node<E>(o);
		newNode.element = o;
		newNode.next = buckets[index];
		buckets[index] = newNode;
		numElements++;
		double currentLoadFactor = numElements / buckets.length;

		if (currentLoadFactor > loadFactor) {
			System.out.println("LOAD FACTOR EXCEEDED, EXPANDING CAPACITY");
			Node<E>[] newBuckets = (Node[]) new HashSetWithChaining.Node[2 * buckets.length];
			Node<E>[] old = buckets;
			numElements = 0;
			buckets = newBuckets;
			for (Node<E> node : old) {
				while (node != null) {
					add((E) node.element);
					node = node.next;
				}
			}
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		if (c == null) {
			return false;
		}
		Iterator<E> iterator = (Iterator<E>) c.iterator();
		while (iterator.hasNext()) {
			this.add(iterator.next());
			numElements++;
		}
		return true;
	}

	@Override
	public void clear() {
		numElements = 0;
		buckets = null;
	}

	@Override
	public boolean contains(Object o) {
		int index = hashCode(o.hashCode());
		Node<E> current = buckets[index];

		while (current != null) {
			if (current.element.equals(o)) {
				return true;
			}
			current = current.next;
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o == null) {
			return false;
		}
		return (this == o);
	}

	public int hashCode(Object index) {
		return (int) index % buckets.length;
	}

	@Override
	public boolean isEmpty() {
		return numElements == 0;
	}

	@Override
	public Iterator<E> iterator() {
		return new HashSetIterator<E>();
	}

	@Override
	public boolean remove(Object o) {
		int index = hashCode(o.hashCode());
		Node<E> current = buckets[index];
		Node<E> previous = null;
		boolean found = false;

		while (current != null) {
			if (current.element.equals(o)) {
				if (previous == null) {
					buckets[index] = current.next;
				} else {
					previous.next = current.next;
				}
				numElements--;
				found = true;
				return found;
			}
			previous = current;
			current = current.next;
		}
		return found;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		if (c == null) {
			return false;
		}
		Iterator<E> iterator = (Iterator<E>) c.iterator();
		E currentElement = null;
		while (iterator.hasNext()) {
			currentElement = iterator.next();
			if (this.contains(currentElement)) {
				this.remove(currentElement);
				numElements--;
			}
		}
		return true;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		if (c == null) {
			return false;
		}
		Iterator<E> iterator = (Iterator<E>) c.iterator();
		E currentElement = null;
		while (iterator.hasNext()) {
			currentElement = iterator.next();
			if (!c.contains(currentElement)) {
				this.remove(currentElement);
				numElements--;
			}
		}
		return true;
	}

	@Override
	public int size() {
		return numElements;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	public String toString() {
		Node<E> currentNode = null;
		StringBuffer sb = new StringBuffer();

		for (int index = 0; index < buckets.length; index++) {
			sb.append("Row " + index + ":");
			if (buckets[index] != null) {
				currentNode = buckets[index];
				sb.append(" " + currentNode.element.toString());
				while (currentNode.next != null) {
					currentNode = currentNode.next;
					sb.append(" -> " + currentNode.element.toString());
				}
			}
			sb.append("\n");
		}

		return sb.toString();
	}

	@SuppressWarnings("hiding")
	class Node<E> {
		public E element;
		public Node<E> next;

		public Node(E element) {
			this.element = element;
			next = null;
		}
	}

	@SuppressWarnings("hiding")
	class HashSetIterator<E> implements Iterator<E> {

		private int currentBucket;
		private HashSetWithChaining<E>.Node<E> currentNode;

		public HashSetIterator() {
			currentNode = null;
			currentBucket = -1;
		}

		@Override
		public boolean hasNext() {
			if (currentNode != null && currentNode.next != null) {
				return true;
			}
			for (int index = currentBucket + 1; index < buckets.length; index++) {
				if (buckets[index] != null) {
					return true;
				}
			}
			return false;
		}

		@SuppressWarnings("unchecked")
		@Override
		public E next() throws NoSuchElementException {
			if (currentNode == null || currentNode.next == null) {
				currentBucket++;
				while (currentBucket < buckets.length && buckets[currentBucket] == null) {
					currentBucket++;
				}
				if (currentBucket < buckets.length) {
					currentNode = (HashSetWithChaining<E>.Node<E>) buckets[currentBucket];
				} else {
					throw new NoSuchElementException();
				}
			} else {
				currentNode = currentNode.next;
			}
			return currentNode.element;
		}
	}

	public static void main(String[] args) {
		HashSetWithChaining<String> names = new HashSetWithChaining<>(6);
		System.out.println("Creating Set, initial capacity = 6");

		names.add("Seth");
		names.add("Bob");
		names.add("Adam");
		names.add("Ian");
		System.out.println("Adding Seth, Bob, Adam, and Ian");
		System.out.println(names.toString());
		System.out.println("Size is: " + names.size());

		System.out.println("Adding Jill, Amy, Nat, Seth, Bob, and Simon");
		names.add("Jill");
		names.add("Amy");
		names.add("Nat");
		names.add("Seth");
		names.add("Bob");
		names.add("Simon");
		System.out.println(names.toString());
		System.out.println("Size is: " + names.size());

		System.out.println("Contains Seth? " + names.contains("Seth"));
		System.out.println("Contains Nat? " + names.contains("Nat"));
		System.out.println("Contains Gary? " + names.contains("Gary"));
	}

}
