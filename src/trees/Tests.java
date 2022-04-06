package trees;

import trees.objects.BinaryTree;

import java.util.Arrays;
import java.util.List;


public class Tests {
    public static void main(String[] args) {
        BinaryTree<Integer, String> BST = new BinaryTree<>();


        List<Integer> keys = Arrays.asList(1, 19, 30, 36, 50, 89, 101, 40, 90, 105, 103);
        for (int key : keys) {
            BST.put(key, Integer.toString(key));
        }

        BST.printTree();
        BST.deleteMax();
        BST.deleteMin();
        BST.delete(50);
        BST.printTree();
    }
}
