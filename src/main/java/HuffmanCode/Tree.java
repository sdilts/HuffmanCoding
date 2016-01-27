package HuffmanCode;

import java.util.Stack;

public class Tree<T extends Comparable> {
    
    private Node<T> root;

    public Tree(Node root) {
	this.root = root;
    }
    
    public void displayTree() {
	Stack globalStack = new Stack();
	globalStack.push(root);
	int nBlanks = 32;
	boolean isRowEmpty = false;
	System.out.println(
			   "......................................................");
	while(isRowEmpty==false)
	    {
		Stack localStack = new Stack();
		isRowEmpty = true;
		for(int j=0; j<nBlanks; j++)
		    System.out.print(' ');
		while(globalStack.isEmpty()==false)
		    {
			Node temp = (Node)globalStack.pop();
			if(temp != null)
			    {
				System.out.print((temp.data == null) ? "+" : temp.data);
				System.out.print(temp.key);
				localStack.push(temp.leftChild);
				localStack.push(temp.rightChild);
				if(temp.leftChild != null ||
				   temp.rightChild != null)
				    isRowEmpty = false;
			    }
			else
			    {
				System.out.print("--");
				localStack.push(null);
				localStack.push(null);
			    }
			for(int j=0; j<nBlanks*2-2; j++)
			    System.out.print(' ');
		    } // end while globalStack not empty
		System.out.println();
		nBlanks /= 2;
		while(localStack.isEmpty()==false)
		    globalStack.push( localStack.pop() );
	    } // end while isRowEmpty is false
	System.out.println(
			   "......................................................");
    } // end displayTree()
}
