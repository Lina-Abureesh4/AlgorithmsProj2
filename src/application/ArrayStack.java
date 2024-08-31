package application;

import java.lang.reflect.Array;

public class ArrayStack<T extends Comparable<T>> implements Stackable<T> {

	private T[] arr;
	private int top = -1;

	public ArrayStack(int capacity) {
		arr = (T[]) new Comparable[capacity];
	}

	public boolean isEmpty() {
		return top == -1;
	}

	public void push(T data) {
		if (top + 1 >= arr.length)
			resize();

		arr[++top] = data;
	}

	public T pop() {
		if (!isEmpty()) {
			T peek = arr[top];
			top--;
//			if (top < arr.length / 4)
//				shrink();
			return peek;
		}
		return null;
	}

	@Override
	public T peek() {
		if (isEmpty())
			return null;
		return arr[top];
	}

	@Override
	public void clear() {
		top = -1;
	}

	public String toString() {
		String res = "Top--> ";
		for (int i = top; i >= 0; i--)
			res += "[" + arr[i] + "]--> ";
		return res + "Null";
	}

	private void resize() {
		T[] newArr = (T[]) new Comparable[arr.length * 2];
		for (int i = 0; i < arr.length; i++)
			newArr[i] = arr[i];
		this.arr = newArr;
	}

	private void shrink() {
		T[] newArr = (T[]) new Comparable[arr.length / 2];
		for (int i = 0; i < newArr.length; i++)
			newArr[i] = arr[i];
		this.arr = newArr;
	}
}
