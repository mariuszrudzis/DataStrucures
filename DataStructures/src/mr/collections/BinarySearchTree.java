package mr.collections;

import java.util.Iterator;

public class BinarySearchTree<K extends Comparable<? super K>, E> implements MyCollection<E> {

	private class Node<E> {
		public K key;
		public E item;
		public Node<E> leftChild;
		public Node<E> rightChild;

		public Node(K key, E item) {
			this.key = key;
			this.item = item;
		}
	}

	private class PairOfNodes<E> {
		public Node<E> parent;
		public Node<E> child;
	}

	private class PostorderTraversalIterator implements Iterator<E> {
		private Node<E> currentNode = BinarySearchTree.this.root;
		private Stack<Node<E>> parentNodes = new Stack<>();
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

			E result = this.currentNode.item;
			if (this.currentNode.leftChild != null) {
				if (this.currentNode.rightChild != null) {
					this.parentNodes.push(this.currentNode);
				}
				this.currentNode = this.currentNode.leftChild;
			} else if (this.currentNode.rightChild != null) {
				this.currentNode = this.currentNode.rightChild;
			} else {
				if (this.parentNodes.size() != 0) {
					this.currentNode = this.parentNodes.pop().rightChild;
				}
			}
			return result;
		}

		private void notifyIterator() {
			modified = true;
		}

	}

	private Node<E> root;
	private int size;
	private LinkedList<PostorderTraversalIterator> iterators = new LinkedList<>();

	/*---------------------------------------------------------------------------*/
	/*---------------------------------------------------------------------------*/
	public void insert(K key, E item) {
	}

	public E search(K key) {
		return null;
	}

	public E remove(K key) {
		return null;
	}
	/*---------------------------------------------------------------------------*/
	/*---------------------------------------------------------------------------*/

	@Override
	public Iterator<E> iterator() {
		this.iterators.addBack(new PostorderTraversalIterator());
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

	@Override
	public void clear() {
		this.root = null;
		this.size = 0;
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

	private PairOfNodes<E> getNode(K key) {
		PairOfNodes<E> nodes = new PairOfNodes<>();
		nodes.child = this.root;

		while (nodes.child != null) {
			if (nodes.child.key.compareTo(key) < 0) {
				nodes.parent = nodes.child;
				nodes.child = nodes.child.leftChild;
			} else if (nodes.child.key.compareTo(key) > 0) {
				nodes.parent = nodes.child;
				nodes.child = nodes.child.rightChild;
			} else {
				break;
			}
		}
		return nodes;
	}
	
	private PairOfNodes<E> getMinNode(Node<E> subtreeRoot) {
		PairOfNodes<E> nodes = new PairOfNodes<>();
		nodes.child = subtreeRoot;
		while(nodes.child.leftChild != null) {
			nodes.parent = nodes.child;
			nodes.child = nodes.child.leftChild;
		}
		return nodes;
	}

	private void insertTest(K key, E item) {
		if (key == null || item == null) {
			throw new RuntimeException("Illegal argument exception");
		}
		
		PairOfNodes<E> nodes = this.getNode(key);

		if (nodes.child == null & nodes.parent == null) {
			this.root = new Node<E>(key, item);
		} else if (nodes.child != null) {
			nodes.child.item = item;
		} else if (nodes.parent != null) {
			if (nodes.parent.key.compareTo(key) < 0) {
				nodes.parent.leftChild = new Node<>(key, item);
			} else {
				nodes.parent.rightChild = new Node<>(key, item);
			}
		}
		this.size++;
	}
	
	private E removeTest(K key) {
		if(key == null) {
			throw new RuntimeException("Illegal argument exception.");
		}
		
		PairOfNodes<E> nodes = this.getNode(key);
		if(nodes.child == null) {
			throw new RuntimeException("No such element.");
		}
		
		E result = nodes.child.item;
		if(nodes.child.leftChild == null && nodes.child.rightChild == null) {
			if(nodes.parent == null) {
				this.root = null;
			} else {
				if(nodes.parent.leftChild == nodes.child) {
					nodes.parent.leftChild = null;
				} else if (nodes.parent.rightChild == nodes.child) {
					nodes.parent.rightChild = null;
				}
			}
		} else if(nodes.child.leftChild != null && nodes.child.rightChild == null) {
			if(nodes.parent == null) {
				this.root = nodes.child.leftChild;
			} else {
				if(nodes.parent.leftChild == nodes.child) {
					nodes.parent.leftChild = nodes.child.leftChild;
				} else if(nodes.parent.rightChild == nodes.child) {
					nodes.parent.rightChild = nodes.child.leftChild;
				}
			}
		} else if(nodes.child.leftChild == null && nodes.child.rightChild != null) {
			if(nodes.parent == null) {
				this.root = nodes.child.rightChild;
			} else {
				if(nodes.parent.leftChild == nodes.child) {
					nodes.parent.leftChild = nodes.child.rightChild;
				} else if(nodes.parent.rightChild == nodes.child) {
					nodes.parent.rightChild = nodes.child.rightChild;
				}
			}
		} else {
			PairOfNodes<E> subNodes = this.getMinNode(nodes.child.rightChild);
			nodes.child.item = subNodes.child.item;
			//TODO: case with 2 children
		}
		
		this.size--;
		return result;
	}
}
