package application;

public class Data {

	private char character;
	private int frequency;
	private String code;
	private int length;

	public Data(char character, int frequency, String code, int length) {
		super();
		this.character = character;
		this.frequency = frequency;
		this.code = code;
		this.length = length;
	}

	public char getCharacter() {
		return character;
	}

	public void setCharacter(char character) {
		this.character = character;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

}
