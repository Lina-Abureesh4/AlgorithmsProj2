package application;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class HuffmanDecompress {

	private TNode<Integer> root;
	private String pathOfCompressedFile;
	private byte[] buffer; // data is stored here before being written to the compressed file
	private int sizeOfOriginalFile;
	private int sizeOfHeader;
	private File compressedFile;
	private String originalFileExtension;

	public HuffmanDecompress(String pathOfCompressedFile) {
		super();
		this.pathOfCompressedFile = pathOfCompressedFile;
		this.compressedFile = new File(pathOfCompressedFile);
		if (compressedFile.exists() && getFileExtension(compressedFile).equals(".huf"))
			decompress();
	}

	private void decompress() {
		readCompressedFile();
	}

	private void readCompressedFile() {
		try {
			DataInputStream inStream = new DataInputStream(
					new BufferedInputStream(new FileInputStream(compressedFile)));
			originalFileExtension = inStream.readUTF(); // read original File extension
			sizeOfOriginalFile = inStream.readInt(); // read size of original file
			sizeOfHeader = inStream.readInt(); // read size of the header
			System.out.println("originalFileExtension: " + originalFileExtension);
			System.out.println("sizeOfOriginalFile: " + sizeOfOriginalFile);
			System.out.println("sizeOfHeader: " + sizeOfHeader);
			buildHuffmanTree(inStream); // build huffman tree by reading the header information
			System.out.println("wwwwwwwww");
			traversePostOrder();
			readCompressedData(inStream); // read data for decompression
			inStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void traversePostOrder() {
		traversePostOrder(root);
	}

	private void traversePostOrder(TNode<Integer> curr) {
		if (curr == null)
			return;
		traversePostOrder(curr.getLeft());
		traversePostOrder(curr.getRight());
		System.out.print(curr + " ");
	}

	// read the header information to build huffman tree
	private void buildHuffmanTree(DataInputStream inStream) {
		ArrayStack<TNode<Integer>> stack = new ArrayStack<TNode<Integer>>(256);
		buffer = new byte[sizeOfHeader];
		try {
			inStream.read(buffer);
			int pointer = 0;
			int count = 0;
			while (pointer < (((sizeOfHeader * 8) / 10) * 10) - 1) {
				System.out.println("pointer: " + pointer);
				int byteNumber = pointer / 8;
				int bitNumber = pointer % 8;
				byte temp = (byte) (buffer[byteNumber] << bitNumber);
				temp = (byte) (temp >> 7); // we need only the bit to which the pointer points (0 or 1)
				temp = (byte) (temp & 1);
				System.out.println("temp = " + temp);
				if (temp == 0) { // non-leaf node
					TNode<Integer> right = stack.pop();
					TNode<Integer> left = stack.pop();
					TNode<Integer> newNode = new TNode<>(null, 0);
					newNode.setRight(right);
					newNode.setLeft(left);
					stack.push(newNode);
					pointer++;
					count++;
				}

				else if (temp == 1) { // leaf node
					pointer++; // to start reading the next character
					byteNumber = pointer / 8;
					bitNumber = pointer % 8;
					int firstVal = (buffer[byteNumber] << bitNumber);
					int numberOfRemainingBits = bitNumber;
					if (numberOfRemainingBits > 0) {
						byteNumber += 1;
						pointer = byteNumber * 8; 
						bitNumber = 0; // will be zero
						int secondVal = (buffer[byteNumber] >> (8 - numberOfRemainingBits)); // shift right with sign
																								// extension
						byte val2 = 0;
						for (int j = 0; j < numberOfRemainingBits; j++) {
							val2 = (byte) (val2 << 1); // shift left
							val2 = (byte) (val2 | 1); // make first digit = 1
						}
						secondVal = (byte) (secondVal & val2);
						byte asciiCode = (byte) (firstVal | secondVal); // get ASCII code for the character
						TNode<Integer> newNode = new TNode<>((int) asciiCode, 0);
						int data = newNode.getData();
						System.out.println("data: " + (char) data);
						stack.push(newNode);
						pointer += numberOfRemainingBits;
					} else {
						byte asciiCode = (byte) firstVal;
						TNode<Integer> newNode = new TNode<>((int) asciiCode, 0);
						int data = newNode.getData();
						System.out.println("data: " + (char) data);
						stack.push(newNode);
						pointer += 8;
					}
				}
			}
			root = stack.pop(); // tree is created
			System.out.println("root = " + root);
			System.out.println(stack.pop());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// read data for decompression from the compressed file
	private void readCompressedData(DataInputStream inStream) {
		TNode<Integer> currNode = root;
		try (
				BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(new File(
						compressedFile.getName().replace(getFileExtension(compressedFile), originalFileExtension))));) {
			buffer = new byte[8];
			refillBufferWithZeros();
			inStream.read(buffer);

			System.out.println("sizeOfOriginalFile: " + sizeOfOriginalFile);

			int charNo = 0;
			while (charNo < sizeOfOriginalFile) {
				int pointer = -1;
				while (pointer < (buffer.length * 8) - 1) {

					pointer++;

					if (currNode.isLeaf()) {
						charNo++;
						outStream.write(currNode.getData()); // write the character into the uncompressed file
						currNode = root;
						if (charNo == sizeOfOriginalFile)
							break;
					}

					int byteNumber = pointer / 8;
					int bitNumber = pointer % 8;
					// get the bit into which the pointer points
					byte newVal = (byte) (buffer[byteNumber] << bitNumber);
					newVal = (byte) ((newVal >> 7) & 1);
					if (newVal == 0) { // go left
						currNode = currNode.getLeft();
					} else if (newVal == 1) { // go right
						currNode = currNode.getRight();
					}

				}
				buffer = new byte[8];
				refillBufferWithZeros();
				inStream.read(buffer);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// refill the buffer array with zeros for reusing
	private void refillBufferWithZeros() {
		for (int i = 0; i < buffer.length; i++)
			buffer[i] = 0;
	}

	// get file extension
	private String getFileExtension(File file) {
		String name = file.getName();
		int index = name.lastIndexOf(".");
		if (index == -1) {
			return ""; // empty extension
		}
		return name.substring(index);
	}

	public byte[] getBuffer() {
		return buffer;
	}

}
