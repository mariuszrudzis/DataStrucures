package mr.collections;

import static org.junit.Assert.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class LinkedListTest {
	private LinkedList<String> list;
	
	@Before
	public void init() {
		this.list = new LinkedList<>();
	}
	@Test
	public void generalTest() {
		this.list = new LinkedList<String>() {
			{
				add(0, "foo");
				add(1, "bar");
				add(2, "baz");
				add(3, "qux");
			}
		};
		
		String[] testData = { "foo", "bar", "baz", "qux" };
		assertTrue(this.list.size() == 4);
		assertFalse(this.list.isEmpty());

		String[] result = new String[this.list.size()];
		for (int i = 0; i < list.size(); i++) {
			result[i] = list.get(i);
		}
		assertArrayEquals(testData, result);

		result = new String[this.list.size()];
		int size = list.size();
		for (int i = 0; i < size; i++) {
			result[i] = this.list.remove(0);
		}
		assertTrue(this.list.size() == 0);
		assertTrue(this.list.isEmpty());
		assertArrayEquals(testData, result);

		for (int i = 0; i < 4; i++) {
			this.list.add(i, testData[3 - i]);
		}
		assertTrue(this.list.size() == 4);
		assertFalse(this.list.isEmpty());

		int index = 0;
		result = new String[this.list.size()];
		for (String s : this.list) {
			result[index++] = s;
		}
		assertArrayEquals(new String[] { "qux", "baz", "bar", "foo" }, result);

		this.list.add(2, "test");
		assertTrue(this.list.size() == 5);

		result = new String[this.list.size()];
		index = 0;
		for (String s : this.list) {
			result[index++] = s;
		}
		assertArrayEquals(new String[] { "qux", "baz", "test", "bar", "foo" }, result);
		
		this.list.add(0, "test");
		assertTrue(this.list.size() == 6);

		result = new String[this.list.size()];
		index = 0;
		for (String s : this.list) {
			result[index++] = s;
		}
		assertArrayEquals(new String[] { "test", "qux", "baz", "test", "bar", "foo"}, result);
		assertTrue(this.list.containsValue("baz"));
		this.list.clear();
		assertFalse(this.list.containsValue("baz"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAdd_shouldThrowIllegalArgument() {
		this.list.add(0, null);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testAdd_shouldThrowIndexOutOfBounds() {
		this.list.add(10, "test");
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testRemove_shouldThrowIndexOutOfBounds() {
		this.list.remove(0);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testGet_shouldThrowIndexOutOfBounds_When_indexLessThanZero() {
		this.list.add(0, "test");
		this.list.get(-1);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testGet_shouldThrowIndexOutOfBounds_When_indexGreaterThanMaxIndex() {
		this.list.add(0, "test");
		this.list.get(this.list.size());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testIterator_shouldThrowNoSuchElement() {
		Iterator<String> it = this.list.iterator();
		assertFalse(it.hasNext());
		it.next();
	}
	
	@Test(expected = ConcurrentModificationException.class)
	public void testIterator_shouldThrowConcurrentModification() {
		Iterator<String> it = this.list.iterator();
		assertFalse(it.hasNext());
		this.list.add(0, "test");
		it.next();
	}
}
