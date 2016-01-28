/** 
 * Main class for the Huffman coding program. Handles the input and output, as well as 
 * the control flow of the program.
 *
 * @author Stuart Dilts
 * Time-stamp: <2016-01-27 23:04:48 stuart>
 * */

package HuffmanCode;
import java.util.PriorityQueue;

public class HuffmanCode {

    private String message;
    private String encodedMessage;
    private int[] freqTable;
    private String[] codeTable;
    private Tree t;

    public HuffmanCode() throws Exception {
	System.out.println("This behavior is undefined with this constructor");
	System.exit(1);
    }

    public HuffmanCode(String message) {
	this.message = formatInput(message);
	System.out.println("Encoding: " + this.message);
	System.out.println("Building Frequency Table...");
	buildFreqTable();
	System.out.println("Building Huffman Tree...");
	buildHuffmanTree();
	System.out.println("Building Code Table...");
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

    private void encodeMessage() {
	StringBuilder code = new StringBuilder();
	for(int i = 0; i < message.length(); i++) {
	    code.append(codeTable[toIndex(message.charAt(i))]);
	}
	encodedMessage = code.toString();
    }

    public String decodeMessage(String message) {
	StringBuilder code = new StringBuilder();
	Node cur = t.root;
	for(int i = 0; i < message.length(); i++) {
	    //System.out.println(cur.getValue());
	    if(message.charAt(i) == '0') {
		cur = cur.rightChild;
	    } else if(message.charAt(i) == '1') {
		cur = cur.leftChild;
	    } else {
		System.out.println("Text must be either a 1 or a zero");
		System.exit(1);
	    }
	    if(cur.isLeaf()) {
		//System.out.println((char) cur.data);
		code.append((char)cur.data);
		//i--;
		cur = t.root;
	    }
	}
	if(!cur.equals(t.root)) { //work on the logic here: what will happen
	                          // if input is invalid?
	    System.out.println("Error: could not read the last digits of the code.");
	    System.exit(1);
	}
	return code.toString();
    }

    private void buildCodeTable() {
	codeTable = new String[28];
	StringBuilder stackie = new StringBuilder();
	buildCodeTableHelper(t.root, stackie);
    }


    private void buildCodeTableHelper(Node current, StringBuilder
				      code) {
	//account for special case where only one char is represented:
	if(current != null) {
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

    }

    private void buildFreqTable() {
	freqTable = new int[28];
	for(int i = 0; i < message.length(); i++) {
	    int index = toIndex(message.charAt(i));
	    freqTable[index]++;
	}
    }

    private void buildHuffmanTree() {
	PriorityQueue<Node> q = new PriorityQueue<Node>();
	for(int i = 0; i < freqTable.length; i++) {
	    if(freqTable[i] != 0) { //exclude chars that aren't in the message
		q.add(new Node<Character>(freqTable[i], fromIndex(i)));
	    }
	}
	if(q.size() == 1) { //special case: only one code:
	    Node<Character> left = q.poll();
	    Node parent = new Node(left.key, null);
	    parent.rightChild = left;
	    t = new Tree(parent);
	} else {
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
	
    }

    public void displayTree() {
	if(t == null) {
	    System.out.println("You need to enter a message first");
	} else {
	    PrintTree.printTree(t);
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

    private int toIndex(char c) {
	return ((int) c) - 65;
    }

    private char fromIndex(int index) {
	return ((char) (index + 65));
    }

    public String getMessage() {
	return message;
    }

    public String getEncodedMessage() {
	return encodedMessage;
    }

}
