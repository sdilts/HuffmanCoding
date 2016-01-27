/** 
 * Main class for the Huffman coding program. Handles the input and output, as well as 
 * the control flow of the program.
 *
 * @author Stuart Dilts
 * Time-stamp: <2016-01-27 16:08:41 stuart>
 * */

package HuffmanCode;
import java.util.PriorityQueue;

public class HuffmanCode {

    private String message;
    private int[] freqTable;
    private Tree t;

    public HuffmanCode() throws Exception {
	System.out.println("This behavior is undefined with this constructor");
	System.exit(1);
    }

    public HuffmanCode(String message) {
	this.message = formatInput(message);
	//System.out.println(this.message);
	buildFreqTable();
	buildHuffmanTree();
    }

    private String formatInput(String input) {
	input = input.toUpperCase();
	input = input.replaceAll("\\n", "\\\\");
	input = input.replaceAll("\\s", "["); //replace problem chars
	return input;
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
	    parent.leftChild = right;
	    parent.rightChild = left;
	    q.add(parent);

	}
	t = new Tree(q.poll());
	t.displayTree();
	
    }

    public void displayTree() {
	if(t == null) {
	    System.out.println("You need to enter a message first");
	} else {
	    t.displayTree();
	}
    }
}
