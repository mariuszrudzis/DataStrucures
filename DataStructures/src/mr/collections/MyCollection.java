package mr.collections;

public interface MyCollection<E> extends Iterable<E>{
	public boolean contains(E item);
	public int size();
	public void clear();
}
