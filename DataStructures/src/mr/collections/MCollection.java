package mr.collections;

public interface MCollection<E> extends Iterable<E>{
	public int size();
	public boolean isEmpty();
	public void clear();
	public boolean containsValue(E element);
}
