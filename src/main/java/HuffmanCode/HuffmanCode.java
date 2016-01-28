/** 
 * Main class for the Huffman coding program. Handles the input and output, as well as 
 * the control flow of the program.
 *
 * @author Stuart Dilts
 * Time-stamp: <2016-01-27 19:14:02 stuart>
 * */

package HuffmanCode;
import java.util.PriorityQueue;

public class HuffmanCode {

    private String message;
    private int[] freqTable;
    private String[] codeTable;
    private Tree t;

    public HuffmanCode() throws Exception {
	System.out.println("This behavior is undefined with this constructor");
	System.exit(1);
    }

    public HuffmanCode(String message) {
	this.message = formatInput(message);
	//System.out.println(this.message);
	System.out.println("Building Frequency Table..");
	buildFreqTable();
	System.out.println("Building Huffman Tree...");
	buildHuffmanTree();
	System.out.println("Code Table...");
	buildCodeTable();
	System.out.println("Encoding message...");
	encodeMessage();
	System.out.println("Done");
    }

    private String formatInput(String input) {
	input = input.toUpperCase();
	input = input.replaceAll("\\n", "\\\\");
	input = input.replaceAll("\\s", "["); //replace problem chars
	return input;
    }


    private void buildCodeTable() {
	codeTable = new String[28];
	StringBuilder stackie = new StringBuilder();
	buildCodeTableHelper(t.root, stackie);

    }

    private void buildCodeTableHelper(Node current, StringBuilder
				      code) {
	//assume that the tree is built correctly:
	if(current.isLeaf()) {
	    if(code.length() > 0) {
		codeTable[toIndex((char)current.data)] = code.toString();
	    } else { //only one entry in the tree:
		codeTable[toIndex((char)current.data)] = "0";
	    }
	} else { //not a leaf; continue:
	    code.append('0');
	    buildCodeTableHelper(current.rightChild, code);
	    code.deleteCharAt(code.length()-1);
	    code.append('1');
	    buildCodeTableHelper(current.leftChild, code);
	    code.deleteCharAt(code.length()-1);
	}

    }

    private int toIndex(char c) {
	return ((int) c) - 65;
    }

    private char fromIndex(int index) {
	return ((char) (index + 65));
    }

    private void buildFreqTable() {
	freqTable = new int[28];
	for(int i = 0; i < message.length(); i++) {
	    int index = toIndex(message.charAt(i));
	    freqTable[index]++;
	}
	// for(int i = 0; i < freqTable.length; i++) {
	//     System.out.printf("%s = %s\n", fromIndex(i), freqTable[i]);

	// }
    }

    private void buildHuffmanTree() {
	PriorityQueue<Node> q = new PriorityQueue<Node>();
	for(int i = 0; i < freqTable.length; i++) {
	    if(freqTable[i] != 0) {
		q.add(new Node<Character>(freqTable[i], fromIndex(i)));
	    }
	}
	//Node<Integer> tree = q.pull();
	while(q.size() > 1) {
	    Node<Character> left = q.poll();
	    Node<Character> right = q.poll();
	    Node parent = new Node<Character>(left.key + right.key,
					    null);
	    parent.leftChild = left;
	    parent.rightChild = right;
	    q.add(parent);

	}
	t = new Tree(q.poll());
	//t.displayTree();
	
    }

    public void displayTree() {
	if(t == null) {
	    System.out.println("You need to enter a message first");
	} else {
	    t.displayTree();
	}
    }

    public void printCodeTable() {
	for(int i = 0; i < codeTable.length; i++) {
	    if(codeTable[i] != null) {
		System.out.printf("%s  %s\n", fromIndex(i),
				  codeTable[i]);
	    }
	}
    }
}
