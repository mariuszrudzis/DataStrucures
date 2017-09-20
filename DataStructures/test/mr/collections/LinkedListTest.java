package mr.collections;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class LinkedListTest {
	private LinkedList<String> list;
	private String[] data = { "foo", "bar", "baz" };

	private void fillListFront() {
		for (String d : this.data) {
			this.list.addFront(d);
		}
	}

	private void fillListBack() {
		for (String d : this.data) {
			this.list.addBack(d);
		}
	}

	@Before
	public void init() {
		this.list = new LinkedList<>();
	}

	@Test
	public void shouldAddThreeElementsBackAndReturnItViaIterator() {
		this.fillListBack();
		String[] result = new String[this.list.size()];
		int i = 0;
		for (String l : this.list) {
			result[i++] = l;
		}
		
		assertArrayEquals(new String[] { "foo", "bar", "baz" }, result);
	}

	@Test
	public void shouldAddThreeElementsFrontAndReturnItViaIterator() {
		this.fillListFront();
		String[] result = new String[this.list.size()];
		int i = 0;
		for (String l : this.list) {
			result[i++] = l;
		}
		assertArrayEquals(new String[] { "baz", "bar", "foo" }, result);
	}

	@Test
	public void shouldAddThreeElementsBackAndReturnItViaGet() {
		this.fillListBack();
		String[] result = new String[this.list.size()];
		for (int i = 0; i < this.list.size(); i++) {
			result[i] = list.get(i);
		}
		assertArrayEquals(new String[] { "foo", "bar", "baz" }, result);
	}

	@Test
	public void shouldAddThreeElementsFronAndReturnItViaGet() {
		this.fillListFront();
		String[] result = new String[this.list.size()];
		for (int i = 0; i < this.list.size(); i++) {
			result[i] = list.get(i);
		}
		assertArrayEquals(new String[] { "baz", "bar", "foo" }, result);
	}
	
	@Test
	public void shouldAddThreeElementsFrontAndReturnItViaRemoveFront() {
		this.fillListFront();
		String[] result = new String[this.list.size()];
		int size = this.list.size();
		for(int i = 0; i < size; i++) {
			result[i] = this.list.removeFront();
		}
		assertArrayEquals(new String[] { "baz", "bar", "foo" }, result);
	}
	
	@Test
	public void shouldAddThreeElementsBackAndReturnItViaRemoveBack() {
		this.fillListBack();
		String[] result = new String[this.list.size()];
		int size = this.list.size();
		for(int i = 0; i < size; i++) {
			result[i] = this.list.removeBack();
		}
		assertArrayEquals(new String[] { "baz", "bar", "foo" }, result);
	}
	
	@Test
	public void shouldAddThreeElementsFrontAndReturnTrueAfterContains() {
		this.fillListFront();
		assertTrue(this.list.contains("baz"));
	}
	
	@Test
	public void shouldAddThreeElementsFrontAndReturnFalseAfterContains() {
		this.fillListFront();
		assertFalse(this.list.contains("test"));
	}
	
	@Test(expected = RuntimeException.class)
	public void shouldAddThreeElementsFrontAndThrowBecauseOfTooSmallIndex() {
		this.fillListFront();
		this.list.get(-1);
	}
	
	@Test(expected = RuntimeException.class)
	public void shouldAddThreeElementsFrontAndThrowBecauseOfTooLargeIndex() {
		this.fillListFront();
		this.list.get(3);
	}
	
	@Test(expected = RuntimeException.class)
	public void shouldAddThreeElementsFrontThenClearAndThrowBecauseOfRemoveFront() {
		this.fillListFront();
		this.list.clear();
		this.list.removeFront();
	}
	
	@Test(expected = RuntimeException.class)
	public void shouldAddThreeElementsFrontThenClearAndThrowBecauseOfRemoveBack() {
		this.fillListFront();
		this.list.clear();
		this.list.removeBack();
	}
	
	@Test(expected = RuntimeException.class)
	public void shouldAddThreeElementsFrontAndThrowBecauseOfRemove() {
		this.fillListFront();
		for(String s : this.list) {
			this.list.remove(0);
		}
	}

}
