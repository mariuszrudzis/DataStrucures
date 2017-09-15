package mr.collections;

import java.util.Iterator;

public class Stack<E> implements MyCollection<E> {
	private LinkedList<E> list;
	
	/*---------------------------------------------------------------------------*/
	/*---------------------------------------------------------------------------*/
	
	public void push(E item) {
		this.list.addFront(item);
	}
	
	public E pop() {
		return this.list.removeFront();
	}
	
	/*---------------------------------------------------------------------------*/
	/*---------------------------------------------------------------------------*/
	
	@Override
	public Iterator<E> iterator() {
		return this.list.iterator();
	}

	@Override
	public boolean contains(E item) {
		return this.list.contains(item);
	}

	@Override
	public long size() {
		return this.list.size();
	}

	@Override
	public void clear() {
		this.list.clear();
	}

	/*---------------------------------------------------------------------------*/
	/*---------------------------------------------------------------------------*/
}
