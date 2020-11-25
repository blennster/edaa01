package bst;

import java.util.ArrayList;
import java.util.Comparator;


public class BinarySearchTree<E> {
    BinaryNode<E> root;  // Används också i BSTVisaulizer
    int size;            // Används också i BSTVisaulizer
    private Comparator<E> comparator = null;

    public static void main(String[] args) {
        BSTVisualizer bstv = new BSTVisualizer("My tree", 300, 300);
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.add(5);
        tree.add(13);
        tree.add(9);
        tree.add(11);
        tree.add(1);
        tree.add(3);
        tree.add(7);
        //tree.rebuild();
        bstv.drawTree(tree);
    }

    /**
     * Constructs an empty binary search tree.
     */
    public BinarySearchTree() {
    }

    /**
     * Constructs an empty binary search tree, sorted according to the specified comparator.
     */
    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    /**
     * Inserts the specified element in the tree if no duplicate exists.
     * @param x element to be inserted
     * @return true if the the element was inserted
     */
    public boolean add(E x) {
        if (root == null) {
            root = new BinaryNode<>(x);
            size++;
            return true;
        }

        boolean res = add(root, x);
        if (res) size++;
        return res;
    }

    private boolean add(BinaryNode<E> node, E x) {
        if (cmp(x, node.element) == 0) return false;

        if (cmp(x, node.element) > 0) {
            if (node.right == null) {
                node.right = new BinaryNode<>(x);
                return true;
            } else {
                return add(node.right, x);
            }
        } else {
            if (node.left == null) {
                node.left = new BinaryNode<>(x);
                return true;
            } else {
                return add(node.left, x);
            }
        }
    }

    private int cmp(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        return ((Comparable<E>)e1).compareTo(e2);
    }

    /**
     * Computes the height of tree.
     * @return the height of the tree
     */
    public int height() {
        //return getHeight(root, 0);
        return height(root);
    }

    private int height(BinaryNode<E> n) {
        if (n == null) return 0;
        return 1 + Math.max(height(n.left), height(n.right));
    }

    private int getHeight(BinaryNode<E> root, int depth) {
        int leftDepth = 0;
        if (root.left != null) {
            leftDepth = getHeight(root.left, depth + 1);
        }
        int rightDepth = 0;
        if (root.right != null) {
            rightDepth = getHeight(root.right, depth + 1);
        }
        if (leftDepth + rightDepth == 0) {
            return depth;
        }
        return Math.max(leftDepth, rightDepth);
    }

    /**
     * Returns the number of elements in this tree.
     * @return the number of elements in this tree
     */
    public int size() {
        return size;
    }

    /**
     * Removes all of the elements from this list.
     */
    public void clear() {
        root = null;
    }

    /**
     * Print tree contents in inorder.
     */
    public void printTree() {
        traverse(root);
    }

    private void traverse(BinaryNode<E> node) {
        if (node != null) {
            traverse(node.left);
            System.out.println(node.element);
            traverse(node.right);
        }
    }

    /**
     * Builds a complete tree from the elements in the tree.
     */
    public void rebuild() {
        ArrayList<E> sorted = new ArrayList<>(size);
        toArray(root, sorted);
        root = buildTree(sorted, 0, sorted.size() - 1);
    }

    /*
     * Adds all elements from the tree rooted at n in inorder to the list sorted.
     */
    private void toArray(BinaryNode<E> n, ArrayList<E> sorted) {
        if (n != null) {
            toArray(n.left, sorted);
            sorted.add(n.element);
            toArray(n.right, sorted);
        }
    }

    /*
     * Builds a complete tree from the elements from position first to
     * last in the list sorted.
     * Elements in the list a are assumed to be in ascending order.
     * Returns the root of tree.
     */
    private BinaryNode<E> buildTree(ArrayList<E> sorted, int first, int last) {
        if (first == last) {
            return new BinaryNode<>(sorted.get(first));
        }

        int middle = first + ((last - first) / 2);
        BinaryNode<E> n = new BinaryNode<>(sorted.get(middle));
        if (middle != first) {
            n.left = buildTree(sorted, first, middle - 1);
        }
        n.right = buildTree(sorted, middle + 1, last);
        return n;
    }



    static class BinaryNode<E> {
        E element;
        BinaryNode<E> left;
        BinaryNode<E> right;

        private BinaryNode(E element) {
            this.element = element;
        }
    }

}
