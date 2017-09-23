package mr.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
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
	
	protected class PreorderTraversalIteratorWrapper implements Iterator<E> {
		private PreorderTraversalIterator it = new PreorderTraversalIterator();
		private boolean modified = false;
		@Override
		public boolean hasNext() {
			return it.hasNext();
		}

		@Override
		public E next() {
			if(this.modified) {
				throw new ConcurrentModificationException();
			}
			
			if(!this.hasNext()) {
				throw new NoSuchElementException();
			}
			
			return it.next().item;
		}
		
		private void detach() {
			this.modified = true;
		}
		
	}
	
	protected class PreorderTraversalIterator implements Iterator<Node<K, E>> {
		private Node<K, E> currentNode = BinaryTree.this.root;
		private Stack<Node<K, E>> nodes = new Stack<>();

		@Override
		public boolean hasNext() {
			return this.currentNode != null;
		}

		@Override
		public Node<K, E> next() {
			Node<K, E> result = this.currentNode;
			if (this.currentNode.right != null) {
				this.nodes.push(this.currentNode.right);
			}

			if (this.currentNode.left != null) {
				this.currentNode = this.currentNode.left;
			} else {
				if (!this.nodes.isEmpty()) {
					this.currentNode = this.nodes.pop();
				} else {
					this.currentNode = null;
				}
			}
			return result;
		}
	}

	protected int size;
	protected Node<K, E> root;
	protected LinkedList<PreorderTraversalIteratorWrapper> iterators = new LinkedList<>();

	@Override
	public Iterator<E> iterator() {
		this.iterators.add(0, new PreorderTraversalIteratorWrapper());
		return this.iterators.get(0);
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
			throw new IllegalArgumentException();
		}
		
		if (this.root == null) {
			throw new IllegalStateException();
		}

		return this.nodeSearch(key).map(e -> e.item).orElse(null);
	}

	public abstract void insert(K key, E item);

	public abstract E remove(K key);

	public abstract void addAll(BinaryTree<K, ? extends E> tree);

	protected void notifyIterators() {
		int size = this.iterators.size();
		for (int i = 0; i < size; i++) {
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
