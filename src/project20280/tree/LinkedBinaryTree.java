package project20280.tree;

import project20280.interfaces.Position;

import java.util.ArrayList;
import java.util.List;
//import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

/**
 * Concrete implementation of a binary tree using a node-based, linked
 * structure.
 */
public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {

    static java.util.Random rnd = new java.util.Random();
    /**
     * The root of the binary tree
     */
    protected Node<E> root = null; // root of the tree

    // LinkedBinaryTree instance variables
    /**
     * The number of nodes in the binary tree
     */
    private int size = 0; // number of nodes in the tree

    /**
     * Constructs an empty binary tree.
     */
    public LinkedBinaryTree() {
    } // constructs an empty binary tree

    // constructor

    public static LinkedBinaryTree<Integer> makeRandom(int n) {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<>();
        bt.root = randomTree(null, 1, n);
        return bt;
    }

    // nonpublic utility

    public static <T extends Integer> Node<T> randomTree(Node<T> parent, Integer first, Integer last) {
        if (first > last) return null;
        else {
            Integer treeSize = last - first + 1;
            Integer leftCount = rnd.nextInt(treeSize);
            Integer rightCount = treeSize - leftCount - 1;
            Node<T> root = new Node<T>((T) ((Integer) (first + leftCount)), parent, null, null);
            root.setLeft(randomTree(root, first, first + leftCount - 1));
            root.setRight(randomTree(root, first + leftCount + 1, last));
            return root;
        }
    }

    // accessor methods (not already implemented in AbstractBinaryTree)

    public static void main(String [] args) {
        LinkedBinaryTree<String> bt = new LinkedBinaryTree<>();
        String[] arr = { "A", "B", "C", "D", "E", null, "F", null, null, "G", "H", null, null, null, null };
        bt.createLevelOrder(arr);
        System.out.println(bt.diameter());
        bt.printLeaves();
        Integer [] inorder= {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
        Integer [] preorder= {18, 2, 1, 14, 13, 12, 4, 3, 9, 6, 5, 8, 7, 10, 11, 15, 16, 17, 28, 23, 19, 22, 20, 21, 24, 27, 26, 25, 29, 30};
        LinkedBinaryTree<Integer> intTree = new LinkedBinaryTree<>();
        intTree.construct(inorder, preorder);
        System.out.println(bt.toBinaryTreeString());
        System.out.println(intTree.toBinaryTreeString());

    }


    /**
     * Factory function to create a new node storing element e.
     */
    protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
        return new Node<E>(e, parent, left, right);
    }

    /**
     * Verifies that a Position belongs to the appropriate class, and is not one
     * that has been previously removed. Note that our current implementation does
     * not actually verify that the position belongs to this particular list
     * instance.
     *
     * @param p a Position (that should belong to this tree)
     * @return the underlying Node instance for the position
     * @throws IllegalArgumentException if an invalid position is detected
     */
    protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node)) throw new IllegalArgumentException("Not valid position type");
        Node<E> node = (Node<E>) p; // safe cast
        if (node.getParent() == node) // our convention for defunct node
            throw new IllegalArgumentException("p is no longer in the tree");
        return node;
    }

    /**
     * Returns the number of nodes in the tree.
     *
     * @return number of nodes in the tree
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the root Position of the tree (or null if tree is empty).
     *
     * @return root Position of the tree (or null if tree is empty)
     */
    @Override
    public Position<E> root() {
        return root;
    }

    // update methods supported by this class

    /**
     * Returns the Position of p's parent (or null if p is root).
     *
     * @param p A valid Position within the tree
     * @return Position of p's parent (or null if p is root)
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    @Override
    public Position<E> parent(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getParent();
    }

    /**
     * Returns the Position of p's left child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the left child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    @Override
    public Position<E> left(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getLeft();
    }

    /**
     * Returns the Position of p's right child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the right child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    @Override
    public Position<E> right(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getRight();
    }

    /**
     * Places element e at the root of an empty tree and returns its new Position.
     *
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalStateException if the tree is not empty
     */
    public Position<E> addRoot(E e) throws IllegalStateException {
        if (root != null) {
            throw new IllegalArgumentException("Tree is not empty");
        }
        else {
            root = createNode(e, null, null, null);
        }
        size++;
        return (Position<E>) root;
    }

    public void insert(E e) {
        root = addRecursive(root, e);
    }

    // recursively add Nodes to binary tree in proper position
    private Node<E> addRecursive(Node<E> p, E e) {
        if (p == null) {
            size++;
            return createNode(e, null, null, null);
        }

        // Note: This requires E to be Comparable.
        // If not using BST logic, you would choose a side based on other criteria.
        Comparable<? super E> k = (Comparable<? super E>) e;
        if (k.compareTo(p.getElement()) < 0) {
            Node<E> leftChild = addRecursive(p.getLeft(), e);
            p.setLeft(leftChild);
            leftChild.setParent(p);
        } else if (k.compareTo(p.getElement()) > 0) {
            Node<E> rightChild = addRecursive(p.getRight(), e);
            p.setRight(rightChild);
            rightChild.setParent(p);
        }
        size++;
        return p;
    }

    /**
     * Creates a new left child of Position p storing element e and returns its
     * Position.
     *
     * @param p the Position to the left of which the new element is inserted
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p already has a left child
     */
    public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> parent = validate(p);
        if (parent.getLeft() != null) {
            throw new IllegalArgumentException("p already has a left child");
        } else {
            parent.setLeft(createNode(e, parent, null, null));
        }
        size++;
        return parent.getLeft();
    }

    /**
     * Creates a new right child of Position p storing element e and returns its
     * Position.
     *
     * @param p the Position to the right of which the new element is inserted
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p already has a right child
     */
    public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> parent = validate(p);
        if (parent.getRight() != null) {
            throw new IllegalArgumentException("p already has a right child");
        } else {
            parent.setRight(createNode(e, parent, null, null));
        }
        size++;
        return parent.getRight();
    }

    /**
     * Replaces the element at Position p with element e and returns the replaced
     * element.
     *
     * @param p the relevant Position
     * @param e the new element
     * @return the replaced element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public E set(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        E temp = node.getElement();
        node.setElement(e);
        return temp;
    }

    /**
     * Attaches trees t1 and t2, respectively, as the left and right subtree of the
     * leaf Position p. As a side effect, t1 and t2 are set to empty trees.
     *
     * @param p  a leaf of the tree
     * @param t1 an independent tree whose structure becomes the left child of p
     * @param t2 an independent tree whose structure becomes the right child of p
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p is not a leaf
     */
    public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2) throws IllegalArgumentException {
        Node<E> node = validate(p);
        if (numChildren(p) > 0) throw new IllegalArgumentException("p must be a leaf");

        size += t1.size() + t2.size();

        if (!t1.isEmpty()) {
            t1.root.setParent(node);
            node.setLeft(t1.root);
            t1.root = null;
            t1.size = 0;
        }
        if (!t2.isEmpty()) {
            t2.root.setParent(node);
            node.setRight(t2.root);
            t2.root = null;
            t2.size = 0;
        }
    }

    /**
     * Removes the node at Position p and replaces it with its child, if any.
     *
     * @param p the relevant Position
     * @return element that was removed
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p has two children.
     */
    public E remove(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);

        // Node cannot have two children
        if (node.getLeft() != null && node.getRight() != null)
            throw new IllegalArgumentException("p has two children");

        // Get the single child (or null)
        Node<E> child = (node.getLeft() != null ? node.getLeft() : node.getRight());

        // If there is a child, connect it to node’s parent
        if (child != null)
            child.setParent(node.getParent());

        // If node is root, update root pointer
        if (node == root) {
            root = child;
        } else {
            Node<E> parent = node.getParent();
            if (parent.getLeft() == node)
                parent.setLeft(child);
            else
                parent.setRight(child);
        }

        size--;
        E removed = node.getElement();

        // Mark node as defunct
        node.setLeft(null);
        node.setRight(null);
        node.setParent(node);

        return removed;
    }

    public String toString() {
        return positions().toString();
    }

    public void createLevelOrder(ArrayList<E> l) {
        size = 0;
        root = createLevelOrderHelper(l, null, 0);
    }

    private Node<E> createLevelOrderHelper(java.util.ArrayList<E> l, Node<E> p, int i) {
        Node<E> curr = null;
        if (i < l.size() && l.get(i) != null) {
            curr = createNode(l.get(i), p, null, null);
            size++;
            curr.setLeft(createLevelOrderHelper(l, curr, 2 * i + 1));
            curr.setRight(createLevelOrderHelper(l, curr, 2 * i + 2));
        }
        return curr;
    }

    public void createLevelOrder(E[] arr) {
        root = createLevelOrderHelper(arr, root, 0);
    }

    private Node<E> createLevelOrderHelper(E[] arr, Node<E> p, int i) {
        Node<E> curr = null;
        if (i < arr.length && arr[i] != null) {
            curr = createNode(arr[i], p, null, null);
            size++;
            curr.setLeft(createLevelOrderHelper(arr, curr, 2 * i + 1));
            curr.setRight(createLevelOrderHelper(arr, curr, 2 * i + 2));
        }
        return curr;
    }

    public String toBinaryTreeString() {
        BinaryTreePrinter<E> btp = new BinaryTreePrinter<>(this);
        return btp.print();
    }

    /**
     * Nested static class for a binary tree node.
     */
    public static class Node<E> implements Position<E> {
        private E element;
        private Node<E> left, right, parent;

        public Node(E e, Node<E> p, Node<E> l, Node<E> r) {
            element = e;
            left = l;
            right = r;
            parent = p;
        }

        // accessor
        public E getElement() {
            return element;
        }

        // modifiers
        public void setElement(E e) {
            element = e;
        }

        public Node<E> getLeft() {
            return left;
        }

        public void setLeft(Node<E> n) {
            left = n;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setRight(Node<E> n) {
            right = n;
        }

        public Node<E> getParent() {
            return parent;
        }

        public void setParent(Node<E> n) {
            parent = n;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (element == null) {
                sb.append("\u29B0");
            } else {
                sb.append(element);
            }
            return sb.toString();
        }
    }

    public void construct(E[] inorder, E[] preorder) {
        size = 0;
        root = constructHelper(inorder, 0, inorder.length - 1, preorder, 0, preorder.length - 1, null);
    }

    private Node<E> constructHelper(E[] inorder, int inStart, int inEnd,
                                    E[] preorder, int preStart, int preEnd,
                                    Node<E> parent) {
        if (inStart > inEnd || preStart > preEnd) {
            return null;
        }

        E rootVal = preorder[preStart];
        Node<E> node = createNode(rootVal, parent, null, null);
        size++;

        int index = inStart;
        while (index <= inEnd && !inorder[index].equals(rootVal)) {
            index++;
        }

        // Number of nodes in the left subtree
        int leftSize = index - inStart;

        node.setLeft(constructHelper(inorder, inStart, index - 1,
                preorder, preStart + 1, preStart + leftSize, node));
        node.setRight(constructHelper(inorder, index + 1, inEnd,
                preorder, preStart + leftSize + 1, preEnd, node));

        return node;
    }

    public ArrayList<ArrayList<E>> rootToPaths() {
        ArrayList<ArrayList<E>> paths = new ArrayList<>();
        if (root() != null) {
            ArrayList<E> currentPath = new ArrayList<>();
            rootToPathsHelper(root(), currentPath, paths);
        }
        return paths;
    }

    private void rootToPathsHelper(Position<E> node,
                                   ArrayList<E> currentPath,
                                   ArrayList<ArrayList<E>> paths) {
        currentPath.add(node.getElement());

        if (isExternal(node)) {
            paths.add(new ArrayList<>(currentPath));
        } else {
            if (left(node) != null)
                rootToPathsHelper(left(node), currentPath, paths);
            if (right(node) != null)
                rootToPathsHelper(right(node), currentPath, paths);
        }

        currentPath.remove(currentPath.size() - 1);
    }

    public int diameter() {
        return diameterHelper((Node<Integer>)root).diameter;
    }

    private static class Pair {
        int height;
        int diameter;
        Pair(int h, int d) { height = h; diameter = d; }
    }

    private Pair diameterHelper(Node<Integer> node) {
        if (node == null)
            return new Pair(-1, 0);

        Pair left  = diameterHelper(node.getLeft());
        Pair right = diameterHelper(node.getRight());

        int height = Math.max(left.height, right.height) + 1;

        int throughRoot = left.height + right.height + 2;

        int diameter = Math.max(throughRoot,
                Math.max(left.diameter, right.diameter));

        return new Pair(height, diameter);
    }

    public void printLeaves() {
        List<E> leaves = new ArrayList<>();
        printLeavesHelper(root, leaves);
        System.out.println(leaves);
    }

    private void printLeavesHelper(Node<E> node, List<E> leaves) {
        if (node == null)
            return;

        printLeavesHelper(node.getLeft(), leaves);

        // leaf check
        if (node.getLeft() == null && node.getRight() == null)
            leaves.add(node.getElement());

        printLeavesHelper(node.getRight(), leaves);
    }

}
