package application;

public class TestStack {

	public static void main(String[] args) {
		
		ArrayStack<Integer> stack = new ArrayStack<>(10);
		for(int i = 0; i < 100; i++) {
			stack.push(i);
		}
		
		System.out.println(stack);
	}
}
