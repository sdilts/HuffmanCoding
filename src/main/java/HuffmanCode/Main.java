/** 
 * Main class for the Huffman coding program. Handles the input and output, as well as 
 * the control flow of the program.
 *
 * @author Stuart Dilts
 * Time-stamp: <2016-01-27 18:55:00 stuart>
 * */

package HuffmanCode;

import java.util.Scanner;

public class Main {

    private static HuffmanCode file;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	Scanner s = new Scanner(System.in);
	while(true) {
	    System.out.print("Enter first letter of enter, show, code ,or decode: ");
	    //try {
		switch(s.nextLine()) {
		case "e":
		    System.out.println("Enter text lines, terminate with $");
		    file = new HuffmanCode(enterMessage("[$]"));
		    break;
		case "s":
		    file.displayTree();
		    System.out.println("\n\n\n");
		    break;
		case "c":
		    System.out.println("Code stuff");
		    file.printCodeTable();
		    break;
		case "d":
		    System.out.println("Decode Stuff");
		    break;
		case "exit":
		    System.out.println("Exiting...");
		    s.close();
		    System.exit(0);
		default:
		    System.out.println("Input error:\nPlease enter either 'e', 's', 'c', or *'d'");
		    break;
		}
		// }  catch(java.util.NoSuchElementException e) {
	    // 	e.printStackTrace();
	    // 	System.exit(1);
	    // }
	}
    }

    /**
     * Recieves the next input from System.in that ends with the
     * pattern matched by the given regular expression.
     *
     * @param regex regluar expression that matches the end of input.
     */
    private static String enterMessage(String regex) {
	Scanner s = new Scanner(System.in).useDelimiter(regex);
	return s.next();
    }

}
