/** 
 * Main class for the Huffman coding program. Handles the input and output, as well as 
 * the control flow of the program.
 *
 * @author Stuart Dilts
 * Time-stamp: <2016-02-08 15:59:03 stuart>
 * */

package HuffmanCode;
import java.util.PriorityQueue;
import java.util.BitSet;

public class HuffmanCode {

    private String message;
    private String encodedMessage;
    private int[] freqTable;
    private String[] codeTable;
    private Tree t;

    public HuffmanCode(String message) {
	this.message = formatInput(message);
	System.out.println("Encoding: " + this.message);
	System.out.println("Building Frequency Table...");
	buildFreqTable();
	printFreqTable();
	System.out.println("Building Huffman Tree...");
	buildHuffmanTree();
	System.out.println("Building Code Table...");
	buildCodeTable();
	System.out.println("Encoding message...");
	encodeMessage();
	//encodeBitMessage();
	System.out.println("Done");
    }

    
    /**
     * Converts message into the correct, all uppercase format with "]" for
     * spaces and "/" for newlines:
     * @param input Message to be converted
     * @return converted message
     */
    private String formatInput(String input) {
	input = input.toUpperCase();
	input = input.replaceAll("\\n", "\\\\");
	input = input.replaceAll("\\s", "["); //replace problem chars
	return input;
    }

    /** Creates the encoded message using the current code table. Will throw a null
     * pointer exception if the code table is not initialized.  */
    private void encodeMessage() {
	StringBuilder code = new StringBuilder();
	for(int i = 0; i < message.length(); i++) {
	    code.append(codeTable[toIndex(message.charAt(i))]);
	}
	encodedMessage = code.toString();
    }

    /** Encodes the message in the same way as encodeMessage(), but stores the
     * message in a byte array instead of a string. */
    private void encodeBitMessage() {
	BitSet code = new BitSet();
	int bitIndex = 0;
	for(int i = 0; i < message.length(); i++) {
	    //get the character encoding we need:
	    int curChar = toIndex(message.charAt(i));
	    //run over every char in the encoding string and set the appropriate bits:
	    for(int j = 0; j < codeTable[curChar].length(); j++) {
		if(codeTable[curChar].charAt(j) == '1') {
		    code.set(bitIndex);
		} else {
		    code.clear(bitIndex);
		}
		bitIndex++;
	    }
	} 
	//byte[] bitMessage = code.toByteArray();
	for(int i = 0; i < bitIndex; i++) {
	    boolean data = code.get(i);
	    if(data) {
		System.out.print('1');
	    } else System.out.print(0);
	}
	System.out.print('\n');
    }

    /**
     * Decodes the given message string and returns the result.
     * @param message String to be converted
     * @return decoded message
     */
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
		System.out.println("Error: Text must be either a 1 or a zero");
		System.exit(1);  //easier to exit than handle exception or null case
	    }
	    if(cur == null) { //special case of there only being one item code
		System.out.println("Error: An invalid code was entered");
		System.exit(1);
	    } else if(cur.isLeaf()) {
		//System.out.println((char) cur.data);
		code.append((char)cur.data);
		//i--;
		cur = t.root;
	    }
	}
	if(!cur.equals(t.root)) { //work on the logic here: what will happen
	                          // if input is invalid?
	    System.out.println("Error: could not read the last digits of the code.");
	    System.exit(1); //easier to exit than handle exception or null case
	}
	return code.toString();
    }

    /** Outside method for building the code table from the Huffman tree. The
     * code table is saved to the global variable <i>codeTable</i> */
    private void buildCodeTable() {
	codeTable = new String[28];
	buildCodeTableHelper(t.root, "");
    }


    /**
     * Recursive function for building the code table from the Huffman
     * tree. Uses a StringBuilder object as a stack in order to record
     * the recursive steps through the tree.
     * @param current the current node that the method is
     *          analyzing. Should start as the root of the tree.
     * @param code String used to store the current code
     */
    private void buildCodeTableHelper(Node current, String code) {
	//account for special case where only one char is represented:
	if(current != null) {
	    if(current.isLeaf()) {
		if(code.length() > 0) {
		    codeTable[toIndex((char)current.data)] = code.toString();
		} else { //only one entry in the tree:
		    codeTable[toIndex((char)current.data)] = "0";
		}
	    } else { //not a leaf; continue:
		buildCodeTableHelper(current.rightChild, code + "0");
		code.deleteCharAt(code.length()-1);
		buildCodeTableHelper(current.leftChild, code + "1");
		code.deleteCharAt(code.length()-1);
	    }
	}

    }

    /** Counts the frequency of the characters in the messages string. */
    private void buildFreqTable() {
	freqTable = new int[28];
	for(int i = 0; i < message.length(); i++) {
	    int index = toIndex(message.charAt(i));
	    freqTable[index]++;
	}
    }

    /** Builds the Huffman tree from the frequency table. */
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
	    t = new Tree<Character>(q.poll());
	}
	
    }

    // private void rebuildTree(String[] codes) {
    // 	Node root = new Node<Character>(null, null);
    // 	for(int i = 0; i < codes.length; i++) {
    // 	    for(int j = 0; j < codes[i].length(); j++) {
    // 		//determine if you ne

    // 	    }
    // 	}

    // }

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

    public void printFreqTable() {
	//assumes fixed-width font:
	System.out.println(" Frequency Table:");
	System.out.println("+----------------+");
	for(int i = 0; i < freqTable.length; i++) {
	    //determine the length of the number held in the data:
	    int length = getDigits(freqTable[i]);
	    for(int j = 0; j < length; j++) {
		System.out.print(" ");
	    }
	    System.out.print(" " + fromIndex(i));
	}
	System.out.print("\n");
	for(int i = 0; i < freqTable.length; i++) {
	    System.out.printf("  %s", freqTable[i]);
	}
	System.out.print("\n\n");

    }

    private int getDigits(int number) {
	if(number == 0) {
	    return 1;
	} else return  1 + (int)Math.floor(Math.log10(number));

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