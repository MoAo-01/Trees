package trees.objects;

import java.util.LinkedList;
import java.util.Queue;


public class AVLTree<Key extends Comparable<Key>, Value> {
    private AVLNode root; // 二叉查找树的根结点
    private final int balancedFactorMaxValue = 2;

    public class AVLNode {
        private Key key; // 键
        private Value val; // 值
        private AVLNode left, right; // 指向子树的链接
        private int N; // 以该结点为根的子树中的结点总数
        private int height;
        private int balancedFactor;

        public AVLNode(Key key, Value val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
            this.height = 1;
        }

        public Key getKey() {
            return key;
        }

        public Value getVal() {
            return val;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", val=" + val +
                    '}';
        }
    }

    // ----------------------------------------------------------------
    // Binary Search Tree Part
    public int size() {
        return size(root);
    }

    public int height() {
        return height(root);
    }

    private int size(AVLNode x) {
        if (x == null) return 0;
        else return x.N;
    }

    private int height(AVLNode x) {
        if (x == null) return 0;
        else return x.height;
    }

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(AVLNode x, Key key) { // 在以x为根结点的子树中查找并返回key所对应的值；
        // 如果找不到则返回null
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.val;
    }

    public void put(Key key, Value val) { // 查找key，找到则更新它的值，否则为它创建一个新的结点
        root = put(root, key, val);
    }

    private AVLNode put(AVLNode x, Key key, Value val) {
        // 如果key存在于以x为根结点的子树中则更新它的值；
        // 否则将以key和val为键值对的新结点插入到该子树中
        if (x == null) return new AVLNode(key, val, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else x.val = val;
        x.N = size(x.left) + size(x.right) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;//rebalanced(x, key); // testing here
    }

    // min()、floor()方法
    public AVLNode min() {
        return min(root);
    }

    private AVLNode min(AVLNode x) {
        if (x.left == null) return x;
        return min(x.left);
    }

    public AVLNode floor(Key key) {
        AVLNode x = floor(root, key);
        if (x == null) return null;
        return x;
    }

    private AVLNode floor(AVLNode x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.left, key);
        AVLNode t = floor(x.right, key);
        if (t != null) return t;
        else return x;
    }

    // max()、ceiling()方法
    public AVLNode max() {
        return max(root);
    }

    private AVLNode max(AVLNode x) {
        if (x.right == null) return x;
        return max(x.right);
    }

    public AVLNode ceiling(Key key) {
        AVLNode x = ceiling(root, key);
        if (x == null) return null;
        return x;
    }

    private AVLNode ceiling(AVLNode x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp > 0) return ceiling(x.right, key);
        AVLNode t = ceiling(x.left, key);
        if (t != null) return t;
        else return x;
    }

    // select()、rank()方法
    public AVLNode select(int k) {
        return select(root, k);
    }

    private AVLNode select(AVLNode x, int k) { // 返回排名为k的结点
        if (x == null) return null;
        int t = size(x.left);
        if (t > k) return select(x.left, k);
        else if (t < k) return select(x.right, k - t - 1);
        else return x;
    }

    public int rank(Key key) {
        return rank(key, root);
    }

    private int rank(Key key, AVLNode x) { // 返回以x为根结点的子树中小于x.key的键的数量
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(key, x.left);
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
        else return size(x.left);
    }

    // delete()、deleteMin()、deleteMax()方法请见算法3.3（续4）
    public void deleteMin() {
        root = deleteMin(root);
    }

    private AVLNode deleteMin(AVLNode x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    public void deleteMax() {
        root = deleteMax(root);
    }

    private AVLNode deleteMax(AVLNode x) {
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        x.N = size(x.right) + size(x.left) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }


    public void delete(Key key) {
        root = delete(root, key);
    }

    private AVLNode delete(AVLNode x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            AVLNode t = x;
            x = min(t.right); // 请见算法3.3（续2）
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.N = size(x.left) + size(x.right) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    // keys()方法
    public Iterable<Key> keys() {
        return keys(min().key, max().key);
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new LinkedList<>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(AVLNode x, Queue<Key> queue, Key lo, Key hi) {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.add(x.key);
        if (cmphi > 0) keys(x.right, queue, lo, hi);
    }

    // --------------------------- AVL part ---------------------------

    public int getBalancedFactor(AVLNode node) {
        if (node != null)
            return height(node.left) - height(node.right);
        return -1;
    }

    // not testing yet
    AVLNode rebalanced(AVLNode node, Key insertedKey) {
        int balancedFactor = getBalancedFactor(node);
        int cmpNodeKey = insertedKey.compareTo(node.key);
        int cmpNodeLeftKey = insertedKey.compareTo(node.left.key);
        if (balancedFactor > 1 && cmpNodeKey < 0)
            return treeRightRotate(node);     //case 1
        if (balancedFactor < -1 && cmpNodeKey > 0)
            return treeLeftRotate(node);      //case 4
        if (balancedFactor > 1 && cmpNodeLeftKey > 0) {
            // case 2
            node.left = treeLeftRotate(node.left);
            return treeRightRotate(node);
        }
        if (balancedFactor < -1 && cmpNodeLeftKey < 0) {
            // case 3
            node.right = treeRightRotate(node.right);
            return treeLeftRotate(node);
        }
        // if do not need rebalanced (all conditions cannot satisfied)
        // then return the current node
        return node;
    }

    // for testing left and right rotation
    public void R() {
        root = treeRightRotate(root);
    }

    public void L() {
        root = treeLeftRotate(root);
    }

    private AVLNode treeLeftRotate(AVLNode x) {
        AVLNode y = x.left;
        x.left = y.right;
        y.right = x;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        x.N = size(x.left) + size(x.right) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        y.N = size(y.left) + size(y.right) + 1;
        return y;
    }

    private AVLNode treeRightRotate(AVLNode x) {
        AVLNode y = x.right;
        x.right = y.left;
        y.left = x;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        x.N = size(x.left) + size(x.right) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        y.N = size(y.left) + size(y.right) + 1;
        return y;
    }


    // Debug and Display Part
    // --------------------------- In-order ---------------------------

    public void print() {
        print(root);
    }

    private void print(AVLNode x) {
        if (x == null) return;
        print(x.left);
        System.out.println(x);
        print(x.right);
    }

    // -------------------------- Print Tree --------------------------

    private void printTree(AVLNode node, String prefix, boolean isLeft) {
        if (node == null) {
            System.out.println("Empty tree");
            return;
        }

        if (node.right != null) {
            printTree(node.right, prefix + (isLeft ? "│   " : "    "), false);
        }

        System.out.println(prefix + (isLeft ? "└── " : "┌── ") + node.val);

        if (node.left != null) {
            printTree(node.left, prefix + (isLeft ? "    " : "│   "), true);
        }
    }

    private void printTree(AVLNode node) {
        printTree(node, "", true);
    }

    public void printTree() {
        System.out.println(" >> START TO PRINT THE TREE:");
        printTree(root);
        System.out.println(" << END TO PRINT THE TREE");
    }

}