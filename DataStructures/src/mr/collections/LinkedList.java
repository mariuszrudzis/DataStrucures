package mr.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<E> implements MCollection<E> {

	protected static class Node<E> {
		public E item;
		public Node<E> next;
		public Node<E> prev;

		public Node(E item) {
			this.item = item;
		}
	}

	protected class LinkedListIterator implements Iterator<E> {
		Node<E> currentNode = LinkedList.this.head;
		private boolean modified = false;

		@Override
		public boolean hasNext() {
			return this.currentNode != null & !this.modified;
		}

		@Override
		public E next() {
			if (this.modified) {
				throw new ConcurrentModificationException();
			}

			if (!this.hasNext()) {
				throw new NoSuchElementException();
			}
			Node<E> result = this.currentNode;
			currentNode = currentNode.next;
			return result.item;
		}

		private void detach() {
			this.modified = true;
		}
	}

	private Node<E> head;
	private Node<E> tail;
	private int size;
	private LinkedList<LinkedListIterator> iterators;

	public LinkedList() {
		this.iterators = new LinkedList<>(true);
	}

	private LinkedList(boolean t) {
	}

	public void add(int index, E item) {
		if (item == null) {
			throw new IllegalArgumentException();
		}

		if (this.size == 0 && index == 0) {
			this.head = new Node<E>(item);
			this.tail = this.head;
		} else if (this.size != 0 && index == 0) {
			this.head.prev = new Node<E>(item);
			this.head.prev.next = this.head;
			this.head = this.head.prev;
		} else if (this.size != 0 && index == this.size) {
			this.tail.next = new Node<E>(item);
			this.tail.next.prev = this.tail;
			this.tail = this.tail.next;
		} else if (this.size != 0 && (index > 0 || index < this.size)) {
			Node<E> next = this.getNode(index);
			Node<E> prev = next.prev;
			Node<E> temporary = new Node<E>(item);
			temporary.prev = prev;
			prev.next = temporary;
			temporary.next = next;
			next.prev = temporary;

		} else {
			throw new IndexOutOfBoundsException();
		}
		this.detachIterators();
		this.size++;
	}

	public E remove(int index) {
		Node<E> result = null;
		if (this.size == 1 && index == 0) {
			result = this.head;
			this.head = null;
			this.tail = null;
		} else if (this.size > 0 && index == 0) {
			result = this.head;
			this.head = this.head.next;
			result.next = null;
		} else if (this.size > 0 && (index > 0 || index < this.size - 1)) {
			Node<E> temporary = this.getNode(index);
			temporary.prev.next = temporary.next;
			temporary.next.prev = temporary.prev;
			temporary.prev = null;
			temporary.next = null;
		} else if (this.size > 0 && index == this.size - 1) {
			result = this.tail;
			this.tail = this.tail.prev;
			result.prev = null;
		} else {
			throw new IndexOutOfBoundsException();
		}
		this.size--;
		this.detachIterators();
		return result.item;
	}

	public E get(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		return this.getNode(index).item;
	}

	@Override
	public Iterator<E> iterator() {
		this.iterators.add(0, new LinkedListIterator());
		return this.iterators.get(0);
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}

	@Override
	public void clear() {
		this.head = null;
		this.tail = null;
		this.size = 0;
		this.detachIterators();
	}

	@Override
	public boolean containsValue(E element) {
		for (E e : this) {
			if (e.equals(element)) {
				return true;
			}
		}
		return false;
	}

	private Node<E> getNode(int index) {
		int middle = this.size / 2;
		Node<E> temporary = null;
		if (index <= middle) {
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

	private void detachIterators() {
		if (this.iterators != null) {
			int size = this.iterators.size;
			for (int i = 0; i < size; i++) {
				this.iterators.remove(0).detach();
			}
		}
	}
}