package mr.collections;

import static org.junit.Assert.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class BinarySerachTreeTest {

	private BinaryTree<Integer, String> tree;
	
	@Before
	public void init() {
		this.tree = new BinarySearchTree<>();
	}
	
	@Test
	public void generalTest() {
		int[] data = { 68, 42, 13, 58, 77, 41, 92, 60, 5, 75 };
		for (int i : data) {
			this.tree.insert(i, String.valueOf(i));
		}
		assertTrue(this.tree.size() == 10);

		String[] result = new String[this.tree.size()];
		int index = 0;
		for (String s : this.tree) {
			result[index++] = s;
		}
		assertArrayEquals(new String[] { "68", "42", "13", "5", "41", "58", "60", "77", "75", "92" }, result);

		assertTrue(this.tree.containsKey(68));
		assertTrue(this.tree.containsValue("68"));
		this.tree.remove(68);
		assertFalse(this.tree.containsKey(68));
		assertFalse(this.tree.containsValue("68"));
		assertTrue(this.tree.size() == 9);

		result = new String[this.tree.size()];
		index = 0;
		for (String s : this.tree) {
			result[index++] = s;
		}
		assertArrayEquals(new String[] { "75", "42", "13", "5", "41", "58", "60", "77", "92" }, result);

		this.tree.remove(42);
		assertTrue(this.tree.size() == 8);
		assertFalse(this.tree.containsKey(42));
		assertFalse(this.tree.containsValue("42"));

		result = new String[this.tree.size()];
		index = 0;
		for (String s : this.tree) {
			result[index++] = s;
		}
		assertArrayEquals(new String[] { "75", "58", "13", "5", "41", "60", "77", "92" }, result);

		this.tree.remove(5);
		assertTrue(this.tree.size() == 7);
		assertFalse(this.tree.containsKey(5));
		assertFalse(this.tree.containsValue("5"));

		result = new String[this.tree.size()];
		index = 0;
		for (String s : this.tree) {
			result[index++] = s;
		}
		assertArrayEquals(new String[] { "75", "58", "13", "41", "60", "77", "92" }, result);

		assertTrue(this.tree.search(41).equals("41"));
		assertTrue(this.tree.search(500) == null);

		BinaryTree<Integer, String> secondTree = new BinarySearchTree<>();
		for (int i : data) {
			secondTree.insert(i, String.valueOf(i));
		}

		this.tree.addAll(secondTree);
		assertTrue(this.tree.size == 10);
		result = new String[this.tree.size()];
		index = 0;
		for (String s : this.tree) {
			result[index++] = s;
		}
		assertArrayEquals(new String[] { "75", "58", "13", "5", "41", "42", "60", "68", "77", "92" }, result);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddAll_shouldThrowIllegalArgument_WhenArgumentNull() {
		this.tree.addAll(null);
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testRemove_shouldThrowNoSuchElement_WhenThereIsNoSuchKey() {
		this.tree.insert(10, "10");
		this.tree.remove(5);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testRemove_shouldThrowIllegalState_WhenTreeIsEmpty() {
		this.tree.remove(10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRemove_shoudlThrowIllegalArgument_WhenKeyNull() {
		this.tree.remove(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInsert_shouldThrowIllegalArgument_WhenValueNull() {
		this.tree.insert(10, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInsert_shouldThrowIllegalArgument_WhenKeyNull() {
		this.tree.insert(null, "10");
	}

	@Test(expected = IllegalStateException.class)
	public void testSearch_shouldThrowIllegalState_WhenTreeIsEmpty() {
		this.tree.search(10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSearch_shoudThrowIllegalArgument_WhenKeyNull() {
		this.tree.insert(10, "10");
		this.tree.search(null);
	}

	@Test(expected = NoSuchElementException.class)
	public void testIterator_shouldThrowNoSuchElement() {
		Iterator<String> it = this.tree.iterator();
		it.next();
	}

	@Test(expected = ConcurrentModificationException.class)
	public void testIterator_shouldThrowConcurrentModification() {
		this.tree.insert(10, "10");
		this.tree.insert(20, "20");
		for(String s : this.tree) {
			this.tree.remove(10);
		}
	}
}
