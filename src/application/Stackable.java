package application;

public interface Stackable<T> {

	public void push(T data);

	public T pop();

	public T peek();

	public boolean isEmpty();

	public void clear();
}
