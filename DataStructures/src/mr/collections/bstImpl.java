package mr.collections;

import java.util.Optional;

public class bstImpl<K extends Comparable<? super K>, E> {

	private static class Node<K, E> {
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
	
	private Node<K, E> root;
	private int size;

	public void insert(K key, E item) {
		if(key == null || item == null) {
			throw new RuntimeException("Illegal argument.");
		}
		Node<K, E> temporary = this.root;
		while(true) {
			if(key.compareTo(temporary.key) < 0) {
				if(temporary.left == null) {
					temporary.left = new Node<K, E>(key, item);
					this.size++;
					return;
				} 
				temporary = temporary.left;
			} else if(key.compareTo(temporary.key) > 0) {
				if(temporary.right == null) {
					temporary.right = new Node<K, E>(key, item);
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

	public E remove(K key) {
		if(key == null) {
			throw new RuntimeException("Illegal argument.");
		}
		
		Node<K, E> removed = this.nodeSearch(key).orElse(null);
		if (removed == null) {
			return null;
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

	public E search(K key) {
		if(key == null) {
			throw new RuntimeException("Illegal argument.");
		}
		
		return this.nodeSearch(key).map(e -> e.item).orElse(null);
	}

	/*---------------------------------------------------------------------------*/
	/*---------------------------------------------------------------------------*/

	private Optional<Node<K, E>> nodeSearch(K key) {
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
