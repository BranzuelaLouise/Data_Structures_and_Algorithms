import java.util.Collection;

public class LinkRetainRemoveSet<E extends Comparable<E>> extends LinkedSet<E> {
	public LinkRetainRemoveSet() {
		super();
	}

	public LinkRetainRemoveSet(Collection<? extends E> c) {
		super(c);
	}

	@Override
	public boolean add(E o) { // Overriding the add method to put elements in a natural order.
		Node<E> newNode = new Node<E>(o);

		Node<E> temp = firstNode;
		Node<E> prev = null;

		while (temp != null && temp.element.compareTo(o) < 0) { // Checks if the current element is less than another
																// element in each of the nodes.
			prev = temp;
			temp = temp.next;
		}

		if (temp != null && temp.element.compareTo(o) == 0) // Check for already existing elements.
			return false;

		if (prev == null) { // If the node is to be added before the head node.
			newNode.next = firstNode;
			firstNode = newNode;
		} else {
			newNode.next = prev.next;
			prev.next = newNode;
		}

		return true;
	}

	public LinkRetainRemoveSet<E> retain(E start, E end) {
		LinkRetainRemoveSet<E> set = new LinkRetainRemoveSet<E>();

		Node<E> temp = firstNode;

		if (start != null) { // Checking if the start parameter is null.
			// Navigate through each node to find the start parameter.
			while (temp != null && temp.element.compareTo(start) != 0) {
				set.add(temp.element); // Add each node to the set that's before the start parameter.
				temp = temp.next;
				firstNode = firstNode.next; // Shifting the firstNode.
			}
		}

		// There's no need to do anything special if the start parameter as null, since
		// the first node is basically the start.

		Node<E> prev = temp;

		if (end != null) { // Checking if the end parameter is null.
			while (temp != null && temp.element.compareTo(end) != 0) {
				prev = temp;
				temp = temp.next;
			}
		} else {
			// If the end parameter is null, then just iterate through every node after
			// start until there are no more nodes to iterate.
			while (temp != null) {
				prev = temp;
				temp = temp.next;
			}
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

		if (start != null) { // Checking if the start parameter is null.
			// Navigate through each node to find the start parameter.
			while (temp != null && temp.element.compareTo(start) != 0) {
				prev = temp;
				temp = temp.next;
			}
		}

		if (end != null) {
			while (temp != null && temp.element.compareTo(end) != 0) {
				set.add(temp.element);
				temp = temp.next;

				if (prev != null)
					prev.next = temp;

				else {
					firstNode = firstNode.next;
				}
			}
		} else {
			while (temp != null) {
				set.add(temp.element);
				temp = temp.next;

				if (prev != null)
					prev.next = temp;

				else {
					firstNode = firstNode.next;
				}
			}
		}

		return set;
	}
}
