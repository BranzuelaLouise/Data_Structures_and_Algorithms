import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SelfOrganizingArrayList<E> implements List<E> {
	private Object[] list;
	private int[] accessCount;
	private int size;

	public SelfOrganizingArrayList(int listSize) {
		this.list = new Object[listSize];
		this.accessCount = new int[listSize];
		this.size = 0;
	}

	public int getIndexOfObject(Object o) {
		int index = -1;

		// Search for the Index of the Element
		for (int i = 0; i < size; i++) {
			// Found a match, save the index position
			if (this.list[i].equals(o)) {
				index = i;
				break;
			}
		}

		return index;
	}

	public void incrementAccessCount(int index, Object accessedObject) {
		// Match found, increment count for the related index
		if (index != -1) {
			this.accessCount[index]++;
			// Save original count of the found index
			int c = this.accessCount[index];

			// Check if count array has any significant changes
			for (int i = 0; i < index; i++) {
				// Recently incremented count is greater
				if (accessCount[index] > accessCount[i]) {
					for (int j = index; j > i; j--) {
						this.list[j] = list[j - 1];
						this.accessCount[j] = accessCount[j - 1];
					}
					this.list[i] = accessedObject;
					this.accessCount[i] = c;
					break;
				}
			}
		}
	}

	@Override
	public int size() {
		return this.list.length;
	}

	@Override
	public boolean isEmpty() {
		return this.list.length == 0;
	}

	@Override
	public boolean contains(Object o) {
		int index = this.getIndexOfObject(o);
		boolean containsSearch = index != -1;

		if (containsSearch) {
			this.incrementAccessCount(index, o);
		}

		return containsSearch;
	}

	@Override
	public Iterator<E> iterator() {
		Iterator<E> iterate = new Iterator<E>() {
			private int currentIndex = 0;

			@Override
			public boolean hasNext() {
				return currentIndex < list.length && list[this.currentIndex] != null;
			}

			@Override
			@SuppressWarnings("unchecked")
			public E next() {
				return (E) list[this.currentIndex++];
			}
		};
		return iterate;
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		ListIterator<E> listIterate = new ListIterator<E>() {
			private int currentIndex = index;

			@Override
			public boolean hasNext() {
				return currentIndex < list.length && list[currentIndex] != null;
			}

			@Override
			@SuppressWarnings("unchecked")
			public E next() {
				return (E) list[this.currentIndex++];
			}

			@Override
			public boolean hasPrevious() {
				return currentIndex-- >= 0;
			}

			@Override
			@SuppressWarnings("unchecked")
			public E previous() {
				// TODO Auto-generated method stub
				return (E) list[this.currentIndex--];
			}

			@Override
			public int nextIndex() {
				return currentIndex++;
			}

			@Override
			public int previousIndex() {
				return currentIndex--;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("Invalid operation");
			}

			@Override
			public void set(E e) {
				throw new UnsupportedOperationException("Invalid operation");
			}

			@Override
			public void add(E e) {
				throw new UnsupportedOperationException("Invalid operation");
			}
		};
		return listIterate;
	}

	@Override
	public ListIterator<E> listIterator() {
		return this.listIterator(0);
	}

	@Override
	public boolean add(E e) {
		if (this.size == this.list.length) {
			// Expand the capacity of the list
			this.size++;

			// Update Appended List and Appended Access Count
			Object[] appendedList = new Object[this.size];
			int[] appendedAccessCount = new int[this.size];
			for (int counter = 0; counter < size; counter++) {
				appendedList[counter] = (counter == size - 1) ? e : this.list[counter];
				appendedAccessCount[counter] = (counter == size - 1) ? 0 : this.accessCount[counter];
			}

			// Update all fields
			this.list = appendedList;
			this.accessCount = appendedAccessCount;
		} else {
			this.list[size] = e;
			this.accessCount[size] = 0;
			this.size++;
		}
		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public E remove(int index) {
		if (index < 0 || index >= size) {
			System.out.println("Invalid position.");
			return null;
		}

		// Object to be removed
		E objectRemoved = (E) this.list[index];

		// Update Reduced List and Reduced Access Count
		Object[] reducedList = new Object[this.size];
		int[] reducedAccessCount = new int[this.size];

		// Shuffle all to the left if applicable
		for (int counter = index; counter < size - 1; counter++) {
			reducedList[counter] = this.list[counter + 1];
			reducedAccessCount[counter] = this.accessCount[counter + 1];
		}

		// Update all fields
		this.list = reducedList;
		this.accessCount = reducedAccessCount;
		this.size--;
		return objectRemoved;
	}

	@Override
	public boolean remove(Object o) {
		int index = this.getIndexOfObject(o);
		boolean removeSearch = index != -1;
		if (removeSearch) {
			this.remove(index);
		}
		return removeSearch;
	}

	@Override
	public int indexOf(Object o) {
		int index = this.getIndexOfObject(o);

		// Match found, increment count for the related index
		if (index != -1) {
			this.incrementAccessCount(index, o);
		}

		return index;
	}

	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException("Invalid operation");
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException("Invalid operation");
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException("Invalid operation");
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException("Invalid operation");
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException("Invalid operation");
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException("Invalid operation");
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("Invalid operation");
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException("Invalid operation");
	}

	@Override
	public E get(int index) {
		throw new UnsupportedOperationException("Invalid operation");
	}

	@Override
	public E set(int index, E element) {
		throw new UnsupportedOperationException("Invalid operation");
	}

	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException("Invalid operation");
	}

	@Override
	public int lastIndexOf(Object o) {
		throw new UnsupportedOperationException("Invalid operation");
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException("Invalid operation");
	}

	@Override
	// Taken from the String documentation and repurposed for my own program.
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append('[');
		for (int i = 0; i < this.size; i++) {
			b.append(this.list[i]);
			b.append("(");
			b.append(this.accessCount[i]);
			b.append(")");
			if (i == this.size - 1)
				return b.append(']').toString();
			b.append(", ");
		}
		return null;
	}
}