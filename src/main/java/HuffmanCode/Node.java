package HuffmanCode;

class Node<E extends Comparable> implements Comparable<Comparable> {
	
    public Integer key;
    public E data;
    public Node<E> leftChild;
    public Node<E> rightChild;

    public Node(int key, E data) {
	this.key = key;
	this.data = data;
    }

    public int compareTo(Comparable compare) {
	// return ((Node) compare).key.compareTo(this.key);
	return key.compareTo(((Node) compare).key);
    }

    public boolean isLeaf() {
	return leftChild == null && rightChild == null;
    }

    //    public 

}
