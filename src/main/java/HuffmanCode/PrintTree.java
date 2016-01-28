package HuffmanCode;
import java.util.HashMap;

public class PrintTree {

    // some static variables for printing:
    public static int curCol;
    public static char[][] board;
    public static HashMap<Node, Integer> colMap;
    public static HashMap<Node, Integer> centMap;
    public static HashMap<Node, Integer> rowMap;

    public static int getWidth(Node n, int depth, int colOffset) {
        // print nodes belonging to subtree of this node
        int lwidth = 0;
        int rwidth = 0;
        if (n.leftChild != null) {
            lwidth = getWidth(n.leftChild, depth + 1, colOffset);
        }
        if (n.rightChild != null) {
            rwidth = getWidth(n.rightChild, depth + 1, colOffset + lwidth + 2);
        }
        char[] data = ("" + n.getValue()).toCharArray();
        int width = Math.max(lwidth + rwidth + 2, data.length + 2);

        colMap.put(n, colOffset + width / 2 - data.length / 2 - 1);
        centMap.put(n, colMap.get(n) + 1 + data.length / 2);
        rowMap.put(n, 4 * depth);

        return width;
    }

    public static void recFillNodes(Node n) {
        char[] data = ("" + n.getValue()).toCharArray();
        for (int i = 0; i < data.length; i++) {
            board[rowMap.get(n)][colMap.get(n) + i + 1] = data[i];
        }
        board[rowMap.get(n)][colMap.get(n)] = '(';
        board[rowMap.get(n)][colMap.get(n) + data.length + 1] = ')';

        if (n.leftChild != null) {
            recFillNodes(n.leftChild);
        }
        if (n.rightChild != null) {
            recFillNodes(n.rightChild);
        }
    }

    public static void recFillEdges(Node n) {
        if (n.leftChild != null) {
            board[rowMap.get(n) + 1][centMap.get(n)] = '|';
            board[rowMap.get(n) + 2][centMap.get(n)] = '+';
            board[rowMap.get(n.leftChild) - 2][centMap.get(n.leftChild)] = '+';
            board[rowMap.get(n.leftChild) - 1][centMap.get(n.leftChild)] = '|';
            for (int j = centMap.get(n.leftChild) + 1; j < centMap.get(n); j++) {
                board[rowMap.get(n.leftChild) - 2][j] = '-';
            }
            recFillEdges(n.leftChild);
        }
        if (n.rightChild != null) {
            board[rowMap.get(n) + 1][centMap.get(n)] = '|';
            board[rowMap.get(n) + 2][centMap.get(n)] = '+';
            board[rowMap.get(n.rightChild) - 2][centMap.get(n.rightChild)] = '+';
            board[rowMap.get(n.rightChild) - 1][centMap.get(n.rightChild)] = '|';
            for (int j = centMap.get(n) + 1; j < centMap.get(n.rightChild); j++) {
                board[rowMap.get(n.rightChild) - 2][j] = '-';
            }
            recFillEdges(n.rightChild);
        }

    }

    public static void printTree(Tree tree) {
        colMap = new HashMap<>();
        centMap = new HashMap<>();
        rowMap = new HashMap<>();

        if (tree.root == null) {
            System.out.println("(null)");
            return;
        }
        int width = getWidth(tree.root, 0, 0);
        int depth = 4 * depth(tree) - 3;

        board = new char[depth][width];
        for (int i = 0; i < depth; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = ' ';
            }
        }
        if (tree.root != null) {
            recFillNodes(tree.root);
            recFillEdges(tree.root);

            for (int i = 0; i < depth; i++) {
                for (int j = 0; j < width; j++) {
                    System.out.print(board[i][j]);
                }
                if (i % 4 == 0) {
                    System.out.println(" <- depth " + i / 4);
                } else {
                    System.out.println();
                }
            }
        }
    }

    public static int depth(Tree tree) {
        return depth(tree.root);
    }
    
    public static int depth(Node n) {
        if (n == null) {
            return 0;
        }
        return 1 + Math.max(depth(n.leftChild), depth(n.rightChild));
    }
}
