package application;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HuffmanCompress {

	private TNode<Character> root;
	private String pathOfOriginalFile;
	private int[] frequencies;
	private int numberOfChars;
	private int[] lengths;
	private String[] huff;
	private byte[] buffer; // data is stored here before being written to the compressed file
	private File originalFile;
	private int originalFileSize;
	private String originalFileExtension;

	public HuffmanCompress(String pathOfOriginalFile) {

		this.pathOfOriginalFile = pathOfOriginalFile;
		originalFile = new File(pathOfOriginalFile);
		frequencies = new int[256];
		lengths = new int[256];
		huff = new String[256];
		if (originalFile.exists() && !getFileExtension(originalFile).equals(".huf"))
			compress();
	}

	private void compress() {
		getFrequenciesFromFile();
		buildHuffmanTree();

		encode();
		for (int i = 0; i <= 255; i++) {
			System.out.println(
					(char) i + ": freq=" + frequencies[i] + " huffman code=" + huff[i] + " length=" + lengths[i]);
		}
		createCompressedFile();
		System.out.println("numberOfChars: " + numberOfChars);
	}

	// to read the original file
	private void getFrequenciesFromFile() {
		try {
			BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(originalFile));
			int val;
			while ((val = inStream.read()) != -1) {
				frequencies[val]++;
			}
			inStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// build Huffman tree
	private void buildHuffmanTree() {
		getNumberOfCharacters();
		System.out.println("number of characters: " + numberOfChars);
		MinHeap<TNode<Character>> heap = new MinHeap<>(numberOfChars);

		for (int i = 0; i < frequencies.length; i++) { // fill the heap with characters with their frequencies
			if (frequencies[i] != 0) {
				TNode<Character> node = new TNode<>((char) i, frequencies[i]);
				heap.add(node);
			}
		}

		while (heap.getSize() > 1) {
			TNode<Character> x = heap.deleteMin();
			TNode<Character> y = heap.deleteMin();
			TNode<Character> newNode = new TNode<>(null, (x.getFreq() + y.getFreq()));
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

	// to encode characters
	private void encode() {
		if (root != null)
			encode(root, "", 0);

		this.originalFileSize = root.getFreq();
	}

	// helper recursive method
	private void encode(TNode<Character> node, String encoding, int depth) {
		if (node.isLeaf()) {
			huff[node.getData()] = encoding;
			lengths[node.getData()] = (depth);
			return;
		}

		if (node.hasLeft()) {
			encode(node.getLeft(), encoding + 0, (depth + 1));
		}

		if (node.hasRight()) {
			encode(node.getRight(), encoding + 1, (depth + 1));
		}
	}

	// create compressed file from original file
	private void createCompressedFile() {
		try {
			BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(
					originalFile.getName().replaceAll(getFileExtension(originalFile), ".huf"), false));

			writeHeaderToCompressedFile(outStream);
			writeDataToCompressedFile(outStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// write header to compressed file
	private void writeHeaderToCompressedFile(BufferedOutputStream outputStream) {
		try {
			buffer = new byte[getHeaderSize()];
			System.out.println("header size = " + getHeaderSize());
			refillBufferWithZeros();
			DataOutputStream outStream = new DataOutputStream(outputStream);
			outStream.writeUTF(getFileExtension(originalFile)); // write original file extension
			outStream.writeInt(getSizeOfOriginalFile()); // write size of original file
			outStream.writeInt(getHeaderSize()); // write size of the header
			String huffmanCodingTree = CreateHuffmanCodingTree(); // get huffman coding tree
			System.out.println(huffmanCodingTree);
			int pointer = 0;
			int byteNumber;
			int bitNumber;
			for (int i = 0; i < huffmanCodingTree.length(); i++) {
				String nextChar = huffmanCodingTree.substring(i, i + 1); // get character at index i
				if (Integer.parseInt(nextChar) == 0) {
					pointer++; // buffer initially stores 0 values, so go to next position
				} else if (Integer.parseInt(nextChar) == 1) {
					byteNumber = pointer / 8;
					bitNumber = pointer % 8;
					buffer[byteNumber] = (byte) (buffer[byteNumber] | (1 << (7 - bitNumber)));
					pointer++;
				}
			}
			outStream.write(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// read the file to be compressed for the second time, this time for writing on compressed file
	private void writeDataToCompressedFile(BufferedOutputStream outStream) {
		buffer = new byte[8];
		refillBufferWithZeros();
		int pointer = 0;
		try {
			BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(originalFile));
			System.out.println("file extension: " + getFileExtension(originalFile));
			int val;
			while ((val = inStream.read()) != -1) {
				int byteNumber = pointer / 8;
				int bitNumber = pointer % 8;
				String huffCode = huff[val];
				int length = this.lengths[val];

				for (int i = 0; i < length; i++) {
					byte bitValue = (byte) Integer.parseInt(huffCode.substring(i, i + 1));
					if (pointer == buffer.length * 8) {
						outStream.write(buffer);
						refillBufferWithZeros();
						pointer = 0;
						byteNumber = 0;
						bitNumber = 0;
					}
					bitValue = (byte) (bitValue << (7 - bitNumber));
					buffer[byteNumber] = (byte) (buffer[byteNumber] | bitValue);
					pointer++;
					byteNumber = pointer / 8;
					bitNumber = pointer % 8;
				}

				if (pointer == buffer.length * 8) {
					outStream.write(buffer);
					refillBufferWithZeros();
					pointer = 0;
				}

			}
			// store into the compressed file
			if (pointer != 0)
				outStream.write(buffer, 0, ((pointer / 8) + 1));
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

	// count the leaves of Huffman tree
	private int countLeaves() {
		return countLeaves(root);
	}

	// helper recursive method for counting the leaves of Huffman tree
	private int countLeaves(TNode<Character> curr) {
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
	private int countParents(TNode<Character> curr) {
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
		this.originalFileExtension = name.substring(index);
		return name.substring(index);
	}

	// this method creates the huffman coding tree to be written in the header in the compressed file
	public String CreateHuffmanCodingTree() {
		return traversePostOrder(root) + 0;
	}

	// traverse Huffman tree to get the header
	private String traversePostOrder(TNode<Character> curr) {
		if (curr == null)
			return "";
		String header = traversePostOrder(curr.getLeft()) + traversePostOrder(curr.getRight());
		if (curr.isLeaf()) {
			int data = curr.getData();
			header += 1 + "" + getBinaryRepresnetation(data);
		} else
			header += 0;
		return header;
	}

	// this method gets the size of original file that will be compDressed
	private int getSizeOfOriginalFile() {
		return root.getFreq();
	}

	// this method returns the size of header in bytes
	public int getHeaderSize() {
		return (int) (countLeaves() + Math.ceil((countLeaves() + countParents() + 1) / 8.0)); // leaves will be written
	}

	private String getBinaryRepresnetation(int val) {
		String s = "00000000";
		StringBuilder b = new StringBuilder(s);
		if (val >= 128) {
			b.replace(0, 1, "1");
			val -= 128;
		}
		if (val >= 64) {
			b.replace(1, 2, "1");
			val -= 64;
		}

		if (val >= 32) {
			b.replace(2, 3, "1");
			val -= 32;
		}

		if (val >= 16) {
			b.replace(3, 4, "1");
			val -= 16;
		}

		if (val >= 8) {
			b.replace(4, 5, "1");
			val -= 8;
		}

		if (val >= 4) {
			b.replace(5, 6, "1");
			val -= 4;
		}

		if (val >= 2) {
			b.replace(6, 7, "1");
			val -= 2;
		}

		if (val == 1) {
			b.replace(7, 8, "1");
			val -= 1;
		}

		System.out.println(b.toString());
		return b.toString();
	}

	public int getOriginalFileSize() {
		return originalFileSize;
	}

	public void setOriginalFileSize(int originalFileSize) {
		this.originalFileSize = originalFileSize;
	}

	public String getOriginalFileExtension() {
		return originalFileExtension;
	}

	public void setOriginalFileExtension(String originalFileExtension) {
		this.originalFileExtension = originalFileExtension;
	}

	public byte[] getBuffer() {
		return buffer;
	}

	public TNode<Character> getRoot() {
		return root;
	}

	public int[] getFrequencies() {
		return frequencies;
	}

	public int getNumberOfChars() {
		return numberOfChars;
	}

	public int[] getLengths() {
		return lengths;
	}

	public String[] getHuff() {
		return huff;
	}
}
