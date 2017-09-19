package mr.collections;

import java.util.Iterator;

public class LinkedList<E> implements MyCollection<E> {

	/*---------------------------------------------------------------------------*/
	/*---------------------------------------------------------------------------*/

	private static class Node<E> {
		public E item;
		public Node<E> prev;
		public Node<E> next;
	}

	private static class LinkedListIterator<E> implements Iterator<E> {
		private Node<E> currentNode;

		public LinkedListIterator(Node<E> head) {
			this.currentNode = head;
		}

		@Override
		public boolean hasNext() {
			return this.currentNode != null ? true : false;
		}

		@Override
		public E next() {
			//TODO: ConcurrentModificationException
			if (this.currentNode == null) {
				throw new RuntimeException("No such element");
			}
			E result = this.currentNode.item;
			this.currentNode = this.currentNode.next;
			return result;
		}
	}

	private Node<E> head;
	private Node<E> tail;
	private int size;

	/*---------------------------------------------------------------------------*/
	/*---------------------------------------------------------------------------*/

	public void addFront(E item) {
		if (this.head != null) {
			Node<E> temporary = new Node<>();
			temporary.item = item;
			temporary.next = this.head;
			this.head.prev = temporary;
			this.head = temporary;
		} else {
			this.head = new Node<>();
			this.head.item = item;
			this.tail = this.head;
		}
		this.size++;
	}

	public void addBack(E item) {
		if (this.tail != null) {
			Node<E> temporary = new Node<>();
			temporary.item = item;
			temporary.prev = this.tail;
			this.tail.next = temporary;
			this.tail = temporary;
		} else {
			this.tail = new Node<>();
			this.tail.item = item;
			this.head = this.tail;
		}
		this.size++;
	}

	public E removeFront() {
		E result = null;
		if (this.head != null) {
			result = this.head.item;
			this.head = this.head.next;
			if (this.head == null) {
				this.tail = null;
			} else {
				this.head.prev = null;
			}
			this.size--;
		} else {
			throw new RuntimeException("List is empty.");
		}
		return result;
	}

	public E removeBack() {
		E result = null;
		if (this.tail != null) {
			result = this.tail.item;
			this.tail = this.tail.prev;
			if (this.tail == null) {
				this.head = null;
			} else {
				this.tail.next = null;
			}
			this.size--;
		} else {
			throw new RuntimeException("List is empty.");
		}
		return result;
	}

	public E get(int index) {
		if(index < 0 || index > this.size - 1) {
			throw new RuntimeException("Index out of bounds.");
		}
		
		Node<E> temporary = null;
		if(index <= (this.size - 1)/ 2) {
			temporary = this.head;
			for(int i = 0; i != index; i++) {
				temporary = temporary.next;
			}
		} else {
			temporary = this.tail;
			for(int i = (this.size - 1); i != index; i--) {
				temporary = temporary.prev;
			}
		}
		return temporary.item;
	}

	/*---------------------------------------------------------------------------*/
	/*---------------------------------------------------------------------------*/

	@Override
	public Iterator<E> iterator() {
		return new LinkedListIterator<>(this.head);
	}

	@Override
	public boolean contains(E item) {
		boolean result = false;
		for(E e : this) {
			if(e.equals(item)) {
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

	@Override
	public void clear() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}

	/*---------------------------------------------------------------------------*/
	/*---------------------------------------------------------------------------*/
}
