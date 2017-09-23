package mr.collections;

import java.util.NoSuchElementException;

public class BinarySearchTree<K extends Comparable<? super K>, E> extends BinaryTree<K, E> {

	@Override
	public void insert(K key, E item) {
		if (key == null || item == null) {
			throw new IllegalArgumentException();
		}
		
		this.notifyIterators();
		
		if(this.root == null) {
			this.root = new Node<K, E>(key, item);
			this.size++;
			return;
		}
		
		Node<K, E> temporary = this.root;
		while (true) {
			if (key.compareTo(temporary.key) < 0) {
				if (temporary.left == null) {
					temporary.left = new Node<K, E>(key, item);
					temporary.left.parent = temporary;
					this.size++;
					return;
				}
				temporary = temporary.left;
			} else if (key.compareTo(temporary.key) > 0) {
				if (temporary.right == null) {
					temporary.right = new Node<K, E>(key, item);
					temporary.right.parent = temporary;
					this.size++;
					return;
				}
				temporary = temporary.right;
			} else {
				temporary.item = item;
				return;
			}
		}
	}

	@Override
	public E remove(K key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
		
		if (this.root == null) {
			throw new IllegalStateException();
		}
		
		this.notifyIterators();
		
		Node<K, E> removed = this.nodeSearch(key).orElse(null);
		if (removed == null) {
			throw new NoSuchElementException();
		}
		
		E result = removed.item;

		if (removed.left == null && removed.right == null) {
			if (removed.parent == null) {
				this.root = null;
			} else {
				if (removed.parent.left == removed) {
					removed.parent.left = null;
				} else {
					removed.parent.right = null;
				}
				removed.parent = null;
			}
		} else if (removed.left != null && removed.right == null) {
			if (removed.parent == null) {
				this.root = removed.left;
			} else {
				if (removed.parent.left == removed) {
					removed.parent.left = removed.left;
				} else {
					removed.parent.right = removed.left;
				}
				removed.left.parent = removed.parent;
				removed.left = null;
				removed.parent = null;
			}
		} else if (removed.left == null && removed.right != null) {
			if (removed.parent == null) {
				this.root = removed.right;
			} else {
				if (removed.parent.left == removed) {
					removed.parent.left = removed.right;
				} else {
					removed.parent.right = removed.right;
				}
			}
			removed.right.parent = removed.parent;
			removed.right = null;
			removed.parent = null;
		} else {
			Node<K, E> min = removed.right;
			while (min.left != null) {
				min = min.left;
			}

			removed.item = min.item;
			removed.key = min.key;
			if (min.right == null) {
				if (min.parent.left == min) {
					min.parent.left = null;
				} else {
					min.parent.right = null;
				}
				min.parent = null;
			} else {
				if (min.parent.left == min) {
					min.parent.left = min.right;
				} else {
					min.parent.right = min.right;
				}
				min.right.parent = min.parent;
				min.right = null;
				min.parent = null;
			}
		}
		this.size--;
		return result;
	}

	@Override
	public void addAll(BinaryTree<K, ? extends E> tree) {
		if(tree == null) {
			throw new IllegalArgumentException();
		}
		
		this.notifyIterators();
		
		BinaryTree<K, ? extends E>.PreorderTraversalIterator it = tree.new PreorderTraversalIterator();
		while (it.hasNext()) {
			Node<K, ? extends E> node = it.next();
			this.insert(node.key, node.item);
		}
	}
}
