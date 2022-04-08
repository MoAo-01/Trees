package trees;


import trees.objects.AVLTree;
import trees.objects.BinaryTree;

import java.util.Arrays;
import java.util.List;


public class Tests {
    public static void main(String[] args) {
        AVLTree<Integer> avl=new AVLTree<>();
        BinaryTree<Integer,Integer> bst=new BinaryTree<>();
        List<Integer> keys = Arrays.asList(1, 19, 30, 36, 50, 89, 101, 40, 90, 105, 103);
        for (int key : keys) {
            bst.put(key,key);
            avl.insert(key);
            System.out.println("BST:");
            bst.printTree();
            System.out.println("AVL Tree:");
            avl.printTree();
            System.out.println("**************************************");
        }
    }
}
