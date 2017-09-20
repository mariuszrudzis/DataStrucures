package mr.collections;

import java.util.Iterator;

public class LinkedList<E> implements MyCollection<E> {

	public LinkedList() {
		this.iterators = new LinkedList<>(true);
	}

	private LinkedList(boolean test) {
	}

	/*---------------------------------------------------------------------------*/
	/*---------------------------------------------------------------------------*/

	private static class Node<E> {
		public E item;
		public Node<E> prev;
		public Node<E> next;
	}

	private class LinkedListIterator implements Iterator<E> {

		private Node<E> currentNode = LinkedList.this.head;
		private boolean modified = false;

		@Override
		public boolean hasNext() {
			return this.currentNode != null;
		}

		@Override
		public E next() {
			if (this.modified) {
				throw new RuntimeException("Concurrent modification exception.");
			}

			if (this.currentNode == null) {
				throw new RuntimeException("No such element.");
			}

			Node<E> result = this.currentNode;
			this.currentNode = this.currentNode.next;
			return result.item;
		}

		private void notifyIterator() {
			this.modified = true;
		}
	}

	private Node<E> head;
	private Node<E> tail;
	private int size;
	private LinkedList<LinkedListIterator> iterators;

	/*---------------------------------------------------------------------------*/
	/*---------------------------------------------------------------------------*/
	public void addFront(E item) {
		this.add(0, item);
	}

	public void addBack(E item) {
		this.add(this.size, item);
	}

	public E removeFront() {
		return this.remove(0);
	}

	public E removeBack() {
		return this.remove(this.size - 1);
	}

	public void add(int index, E item) {
		this.notifyAllIterators();
		if (this.size == 0 && index == 0) {
			this.head = new Node<E>();
			this.tail = this.head;
			this.head.item = item;
		} else if (this.size != 0 && this.size - index > 0) {
			Node<E> temporary = this.getNode(index);
			Node<E> prev = temporary.prev;
			Node<E> newNode = new Node<E>();

			newNode.item = item;
			newNode.next = temporary;
			temporary.prev = newNode;
			if (prev == null) {
				this.head = newNode;
			} else {
				newNode.prev = prev;
				prev.next = newNode;
			}
		} else if (this.size != 0 && this.size - index == 0) {
			this.tail.next = new Node<E>();
			this.tail.next.prev = this.tail;
			this.tail = this.tail.next;
			this.tail.item = item;
		} else {
			throw new RuntimeException("Index out of bounds.");
		}
		this.size++;
	}

	public E remove(int index) {
		this.notifyAllIterators();
		Node<E> temporary = this.getNode(index);
		Node<E> prev = temporary.prev;
		Node<E> next = temporary.next;
		E result = temporary.item;

		if (prev == null && next == null) {
			this.head = null;
			this.tail = null;
		} else if (prev == null && next != null) {
			next.prev = null;
			this.head = next;
		} else if (prev != null && next != null) {
			prev.next = next;
			next.prev = prev;
		} else {
			prev.next = null;
			this.tail = prev;
		}
		this.size--;
		return result;
	}

	public E get(int index) {
		return this.getNode(index).item;
	}

	/*---------------------------------------------------------------------------*/
	/*---------------------------------------------------------------------------*/

	@Override
	public Iterator<E> iterator() {
		this.iterators.addBack(new LinkedListIterator());
		return this.iterators.get(this.iterators.size() - 1);
	}

	@Override
	public boolean contains(E item) {
		boolean result = false;
		for (E e : this) {
			if (e.equals(item)) {
				result = true;
				break;
			}
		}
		return result;
	}

	@Override
	public int size() {
		return this.size;
	}
	
	public void testSize() {
		System.out.println(this.iterators.size());
	}

	@Override
	public void clear() {
		this.notifyAllIterators();
		this.head = null;
		this.tail = null;
		this.size = 0;
	}

	/*---------------------------------------------------------------------------*/
	/*---------------------------------------------------------------------------*/

	private Node<E> getNode(int index) {
		if (index < 0 || index > this.size - 1) {
			throw new RuntimeException("Index out of bounds.");
		}

		int middleIndex = this.size / 2;
		Node<E> temporary = null;
		if (index <= middleIndex) {
			temporary = this.head;
			for (int i = 0; i < index; i++) {
				temporary = temporary.next;
			}
		} else {
			temporary = this.tail;
			for (int i = this.size - 1; i > index; i--) {
				temporary = temporary.prev;
			}
		}
		return temporary;
	}

	/*---------------------------------------------------------------------------*/
	/*---------------------------------------------------------------------------*/

	private void notifyAllIterators() {
		if (this.iterators != null) {
			for (int i = 0; i < this.iterators.size(); i++) {
				this.iterators.get(i).notifyIterator();
			}
			this.iterators.clear();
		}
	}
}
