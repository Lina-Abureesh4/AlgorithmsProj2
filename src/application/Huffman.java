package application;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;

public class Huffman {

	private TNode<Integer> root;
	private String path;
	private int[] frequencies;
	private int numberOfChars;
	private byte[] length;
	private byte[] huff;
	private byte[] buffer; // data is stored here before being written to the compressed file

	public Huffman(String path, boolean compress) {

		this.path = path;
		frequencies = new int[256];
		length = new byte[256];
		huff = new byte[256];

		if (compress) {
			compress();
		} else
			decompress();
	}

	// build Huffman tree
	private void buildHuffmanTree() {
		getNumberOfCharacters(); 
		System.out.println("number of characters: " + numberOfChars);
		MinHeap<TNode<Integer>> heap = new MinHeap<>(numberOfChars);

		for (int i = 0; i < frequencies.length; i++) { // fill the heap with characters with their frequencies
			if (frequencies[i] != 0) {
				TNode<Integer> node = new TNode<>(i, frequencies[i]);
				heap.add(node);
			}
		}

		while (heap.getSize() > 1) {
			TNode<Integer> x = heap.deleteMin();
			TNode<Integer> y = heap.deleteMin();
			TNode<Integer> newNode = new TNode<>(null, (x.getFreq() + y.getFreq()));
			newNode.setLeft(x);
			newNode.setRight(y);
			heap.add(newNode);
		}

		root = heap.deleteMin(); // after exiting the while loop, the heap has only 1 node, which is the root
		System.out.println(root);
	}

	// to find number of different characters in the file
	private void getNumberOfCharacters() {
		numberOfChars = 0;
		for (int i = 0; i < 256; i++)
			if (frequencies[i] != 0)
				numberOfChars += 1;
	}

	// to read the original file
	private void getFrequenciesFromFile() {
		try {
			File file = new File(path); 
			BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(file));
			BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(file.getPath().replaceAll(getFileExtension(file), "2"+getFileExtension(file))));
//			System.out.println(new File(path).getParent() + "\\answers2.dat");
			int val;
			while ((val = inStream.read()) != -1) {
				frequencies[val]++;
//				outStream.write(val);
			}
			inStream.close();
//			outStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void compress() {
		getFrequenciesFromFile();
		buildHuffmanTree();
		encode();
		for (int i = 0; i <= 255; i++) {
			System.out.println(
					(char) i + ": freq=" + frequencies[i] + " huffman code=" + huff[i] + " length=" + length[i]);
		}
//		System.out.println("# of leaves= " + countLeaves());
//		System.out.println("# of parents= " + countParents());

		createCompressedFile();
	}

	private void decompress() {

	}

	// to encode characters
	private void encode() {
		if (root != null)
			encode(root, (byte) 0, (byte) 0);
	}

	// helper recursive method
	private void encode(TNode<Integer> node, byte encoding, byte depth) {
		if (node.isLeaf()) {
			encoding = (byte) (encoding >> 1); // shift right by one digit
			huff[node.getData()] = encoding;
			length[node.getData()] = (byte) (depth);
			return;
		}

		if (node.hasLeft()) {
			byte new_encoding = (byte) (encoding << 1); // shift left by 1 digit
			encode(node.getLeft(), new_encoding, (byte) (depth + 1));
		}

		if (node.hasRight()) {
			encoding = (byte) (encoding | 1); // the least significant bit is 1
			byte new_encoding = (byte) (encoding << 1); // shift left by 1 digit
			encode(node.getRight(), new_encoding, (byte) (depth + 1));
		}
	}

	// read the file to be compressed for the second time, this time for writing on
	// compressed file
	private void writeDataToCompressedFile() {
		buffer = new byte[8];
		int pointer = 0;
		try {
			BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(path));
//			System.out.println(new File(path).getPath().replaceAll("[.]\\S*", ".huf"));
			System.out.println("file extension: " + getFileExtension(new File(path)));
			BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(
					new File(path).getPath().replaceAll(getFileExtension(new File(path)), ".huf"), true));
			int val;
			int count = 0;
			while ((val = inStream.read()) != -1) {
				int byteNumber = pointer / 8;
				int bitNumber = pointer % 8;
				int huffCode = huff[val];
				int length = this.length[val];

//				System.out.print(count + "--> " + 
//						(char) val + ": freq=" + frequencies[val] + " huffman code=" + huffCode + " length=" + length);

				byte newVal = (byte) (huffCode << (8 - length)); // shift left
//				System.out.println(" new val=" + newVal);
				if (bitNumber + length <= 8) { // to ensure that no bits are lost in the
					// right shifting operation
					newVal = (byte) (newVal >>> bitNumber); // shift right with zero extension
					buffer[byteNumber] = (byte) (buffer[byteNumber] | newVal);
					if ((pointer + length) < buffer.length * 8) // the huffCode as a whole can be stored in the buffer
						pointer += length;
					else if ((pointer + length) == buffer.length * 8) {
						// store into the compressed file
						outStream.write(buffer);
						refillBufferWithZeros();
						pointer = 0;
					} else {
						// store into the compressed file
						outStream.write(buffer);
						refillBufferWithZeros();
						byte lengthOfRemainingBits = (byte) (pointer + length - buffer.length);
						pointer = 0;
						newVal = (byte) (huffCode << (8 - lengthOfRemainingBits));
						buffer[0] = newVal;
						pointer += lengthOfRemainingBits;
					}
				} else {
					newVal = (byte) (newVal >>> bitNumber); // shift right with zero extension
					buffer[byteNumber] = (byte) (buffer[byteNumber] | newVal);
					byte lengthOfRemainigBits = (byte) (bitNumber + length - 8);
					newVal = (byte) (huffCode << (8 - lengthOfRemainigBits));
					if ((pointer + length) < buffer.length * 8) { // the huffCode as a whole can be stored in the buffer
						pointer += (length - lengthOfRemainigBits);
						byteNumber = pointer / 8;
						buffer[byteNumber] = newVal;
						pointer += lengthOfRemainigBits;
					} else if ((pointer + length) > buffer.length * 8) { // the huffCode can't be stored in the buffer
						// store into the compressed file
						outStream.write(buffer);
						refillBufferWithZeros();
						pointer = 0;
						buffer[0] = newVal;
						pointer += lengthOfRemainigBits;
					}
				}
				count++;
			}

			// store into the compressed file
			if (pointer != 0)
				outStream.write(buffer, 0, ((pointer / 8) + 1));
			System.out.println("count = " + count);

			inStream.close();
			outStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// refill the buffer array with zeros for reusing
	private void refillBufferWithZeros() {
		for (int i = 0; i < buffer.length; i++)
			buffer[i] = 0;
	}

	// create compressed file from original file
	private void createCompressedFile() {
		writeHeaderToCompressedFile();
		writeDataToCompressedFile();
	}

	// write header to compressed file
	private void writeHeaderToCompressedFile() {
		try {
			buffer = new byte[getHeaderSize()];
			File file = new File(path);
			DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(
					new FileOutputStream(new File(path).getPath().replaceAll(getFileExtension(file), ".huf"))));
			outStream.writeUTF(getFileExtension(file)); // write original file extension
			outStream.writeInt(getSizeOfOriginalFile()); // write size of original file
			outStream.write(getHeaderSize()); // write size of the header
			String huffmanCodingTree = CreateHuffmanCodingTree(); // get huffman coding tree
			int pointer = 0;
			int byteNumber;
			int bitNumber;
			for (int i = 0; i < huffmanCodingTree.length(); i++) {
				String nextChar = huffmanCodingTree.substring(i, i + 1); // get character at index i
				if (nextChar.matches("[\\D]")) { // if the char is a non digit character, store its ASCII code
					byteNumber = pointer / 8;
					bitNumber = pointer % 8;
					byte asciiCode = (byte) huffmanCodingTree.charAt(i);
					byte newVal = (byte) (asciiCode >>> bitNumber); // shift right with zero extension
					buffer[byteNumber] = (byte) (buffer[byteNumber] | newVal);
					byte lengthOfRemainigBits = (byte) bitNumber;
					if (lengthOfRemainigBits > 0) {
						newVal = (byte) (asciiCode << (8 - lengthOfRemainigBits));
						pointer += (8 - lengthOfRemainigBits);
						byteNumber = pointer / 8;
						buffer[byteNumber] = newVal;
						pointer += lengthOfRemainigBits;
					} else 
						pointer += 8; 
				}

				else if (nextChar.matches("[\\d]")) { // if the char is a number, 1 or 0, store it as a bit
					if (Integer.parseInt(nextChar) == 0) {
						pointer++; // buffer initially stores 0 values, so go to next position
					} else if (Integer.parseInt(nextChar) == 1) {
						byteNumber = pointer / 8;
						bitNumber = pointer % 8;
						buffer[byteNumber] = (byte) (buffer[byteNumber] | (1 << (7 - bitNumber)));
						pointer++;
					}
				}
			}
			outStream.write(buffer);
			outStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// count the leaves of Huffman tree
	private int countLeaves() {
		return countLeaves(root);
	}

	// helper recursive method for counting the leaves of Huffman tree
	private int countLeaves(TNode<Integer> curr) {
		if (curr == null)
			return 0;
		if (curr.isLeaf())
			return 1;
		return countLeaves(curr.getLeft()) + countLeaves(curr.getRight());
	}

	// count the parents (non-leafs) in Huffman tree
	private int countParents() {
		return countParents(root);
	}

	// helper recursive method for counting the parents in Huffman tree
	private int countParents(TNode<Integer> curr) {
		if (curr == null)
			return 0;
		if (curr.isLeaf())
			return 0;
		return 1 + countParents(curr.getLeft()) + countParents(curr.getRight());
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

	// this method creates the huffman coding tree to be written in the header in
	// the compressed file
	private String CreateHuffmanCodingTree() {
		return traversePostOrder(root) + 0;
	}

	// traverse Huffman tree to get the header
	private String traversePostOrder(TNode<Integer> curr) {
		if (curr == null)
			return "";
		String header = traversePostOrder(curr.getLeft()) + traversePostOrder(curr.getRight());
		if (curr.isLeaf()) {
			int data = curr.getData();
			header += 1 + "" + (char) data;
		} else
			header += 0;
		return header;
	}

	// this method gets the size of original file that will be compressed
	private int getSizeOfOriginalFile() {
		return root.getFreq();
	}

	// this method returns the size of header in bytes
	private int getHeaderSize() {
		return (int) (countLeaves() + Math.ceil((countLeaves() + countParents() + 1) / 8.0)); // leaves will be written
																								// as bytes, 1's and 0's
																								// as bits such that the
																								// number of 1's = the
																								// number of leaves, and
																								// the number of 0's =
																								// the number of parents
																								// + 1 (the end of
																								// huffman coding tree)
	}

	public TNode<Integer> getRoot() {
		return root;
	}

	public int[] getFrequencies() {
		return frequencies;
	}

	public int getNumberOfChars() {
		return numberOfChars;
	}

	public byte[] getLength() {
		return length;
	}

	public byte[] getHuff() {
		return huff;
	}
}
