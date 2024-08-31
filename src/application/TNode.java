package application;

public class TNode<T> implements Comparable<TNode<T>> {

	private int freq;
	private TNode<T> left;
	private TNode<T> right;
	private T data;

	public TNode(T data, int freq) {
		super();
		this.freq = freq;
		this.data = data;
	}

	public int getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public TNode<T> getLeft() {
		return left;
	}

	public void setLeft(TNode<T> left) {
		this.left = left;
	}

	public TNode<T> getRight() {
		return right;
	}

	public void setRight(TNode<T> right) {
		this.right = right;
	}

	public boolean hasRight() {
		return this.right != null;
	}

	public boolean hasLeft() {
		return this.left != null;
	}

	public boolean isLeaf() {
		return this.right == null && this.left == null;
	}

	@Override
	public int compareTo(TNode<T> o) {
		if (this.freq > o.getFreq())
			return 1;
		else if (this.freq < o.getFreq())
			return -1;
		return 0;
	}

	@Override
	public String toString() {
		return "TNode [freq=" + freq + ", data=" + data + "]";
	}
}
