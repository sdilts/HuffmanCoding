/** 
 * Main class for the Huffman coding program. Handles the input and output, as well as 
 * the control flow of the program.
 *
 * @author Stuart Dilts
 * Time-stamp: <2016-01-26 23:37:26 stuart>
 * */

package HuffmanCode;

public class HuffmanCode {

    private String message;
    private int[] freqTable;
    //private Tree t;

    public HuffmanCode() throws Exception {
	System.out.println("This behavior is undefined with this constructor");
	System.exit(1);

    }

    public HuffmanCode(String message) {
	this.message = formatInput(message);
	System.out.println(this.message);
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
	for(int i = 0; i < freqTable.length; i++) {
	    System.out.printf("%s = %s\n", fromIndex(i), freqTable[i]);

	}
    }

    private void buildHuffmanTree() {


    }



}