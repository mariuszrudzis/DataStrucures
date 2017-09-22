package mr.collections;

import java.util.Iterator;

public class Stack<E> implements MCollection<E> {
	
	private LinkedList<E> list = new LinkedList<>();
	
	public void push(E item) {
		this.list.add(0, item);
	}
	
	public E pop() {
		return this.list.remove(0);
	}
	
	@Override
	public Iterator<E> iterator() {
		return this.list.iterator();
	}

	@Override
	public int size() {
		return this.list.size();
	}

	@Override
	public boolean isEmpty() {
		return this.list.isEmpty();
	}

	@Override
	public void clear() {
		this.list.clear();
	}

	@Override
	public boolean containsValue(E element) {
		return this.list.containsValue(element);
	}
	
}
