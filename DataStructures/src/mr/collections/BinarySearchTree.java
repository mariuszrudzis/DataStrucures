package mr.collections;

import java.util.Iterator;

public class BinarySearchTree<E extends Comparable<? super E>> implements MyCollection<E> {
	
	private static class Node<E extends Comparable<? super E>> {
		public E item;
		public Node<E> leftChild;
		public Node<E> rightChild;
	}
	
	private static class PostorderTraversalIterator<E extends Comparable<? super E>> implements Iterator<E> {
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public E next() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	/*---------------------------------------------------------------------------*/
	/*---------------------------------------------------------------------------*/
	
	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(E item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
	
	/*---------------------------------------------------------------------------*/
	/*---------------------------------------------------------------------------*/
}
