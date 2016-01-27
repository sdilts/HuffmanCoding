/** 
 * Main class for the Huffman coding program. Handles the input and output, as well as 
 * the control flow of the program.
 *
 * @author Stuart Dilts
 * Time-stamp: <2016-01-26 20:25:54 stuart>
 * */

package HuffmanCode;

import java.util.Scanner;

public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	Scanner s = new Scanner(System.in);
	while(true) {
	    System.out.print("Enter first letter of enter, show, code ,or decode: ");
	    try {
		switch(s.nextLine()) {
		case "e":
		    System.out.println("Enter stuff now");
		    System.out.println(enterMessage("[$]"));
		    break;
		case "s":
		    System.out.println("Show stuff");
		    break;
		case "c":
		    System.out.println("Code stuff");
		    break;
		case "d":
		    System.out.println("Decode Stuff");
		    break;
		case "exit":
		    System.exit(0);
		default:
		    System.out.println("Input error:\nPlease enter either 'e', 's', 'c', or *'d'");
		    break;
		}
	    } catch(Exception e) {
		e.printStackTrace();
	    }
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