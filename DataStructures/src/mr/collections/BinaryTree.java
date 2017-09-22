package mr.collections;

import java.util.Iterator;
import java.util.Optional;


public abstract class BinaryTree<K extends Comparable<? super K>, E> implements MCollection<E> {

	protected static class Node<K, E> {
		public K key;
		public E item;
		public Node<K, E> parent;
		public Node<K, E> left;
		public Node<K, E> right;

		public Node(K key, E item) {
			this.key = key;
			this.item = item;
		}
	}

	protected class PreorderTraversalIterator implements Iterator<E> {
		private boolean modified = false;
		private Node<K, E> currentNode = BinaryTree.this.root;

		@Override
		public boolean hasNext() {
			return this.currentNode != null;
		}

		@Override
		public E next() {
			if (this.modified) {
				throw new RuntimeException("Concurrent modification.");
			}

			if (!this.hasNext()) {
				throw new RuntimeException("No such element.");
			}

			return null;
		}
		
		private void detach() {
			this.modified = true;
		}

	}

	protected int size;
	protected Node<K, E> root;
	protected LinkedList<PreorderTraversalIterator> iterators = new LinkedList<>();

	@Override
	public Iterator<E> iterator() {
		return new PreorderTraversalIterator();
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public void clear() {
		this.root = null;
		this.size = 0;
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

	public boolean containsKey(K key) {
		return this.search(key) != null;
	}

	public E search(K key) {
		if (key == null) {
			throw new RuntimeException("Illegal argument.");
		}

		return this.nodeSearch(key).map(e -> e.item).orElse(null);
	}

	public abstract void insert(K key, E item);

	public abstract E remove(K key);

	public abstract void addAll(BinaryTree<K, ? extends E> tree);

	protected void notifyIterators() {
		int size = this.iterators.size();
		for(int i = 0; i < size; i++) {
			this.iterators.remove(0).detach();
		}
	}

	protected Optional<Node<K, E>> nodeSearch(K key) {
		Node<K, E> temporary = this.root;
		while (true) {
			if (key.compareTo(temporary.key) < 0) {
				if (temporary.left != null) {
					temporary = temporary.left;
				} else {
					return Optional.ofNullable(null);
				}
			} else if (key.compareTo(temporary.key) > 0) {
				if (temporary.right != null) {
					temporary = temporary.right;
				} else {
					return Optional.ofNullable(null);
				}
			} else {
				return Optional.of(temporary);
			}
		}
	}
}
