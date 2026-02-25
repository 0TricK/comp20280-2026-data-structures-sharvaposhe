package project20280.list;

import project20280.interfaces.List;

import java.util.Comparator;
import java.util.Iterator;

public class SinglyLinkedList<E> implements List<E> {

    private static class Node<E> {

        private final E element;            // reference to the element stored at this node

        /**
         * A reference to the subsequent node in the list
         */
        private Node<E> next;         // reference to the subsequent node in the list

        /**
         * Creates a node with the given element and next node.
         *
         * @param e the element to be stored
         * @param n reference to a node that should follow the new node
         */
        public Node(E e, Node<E> n) {
            element = e;
            next = n;
        }

        // Accessor methods

        /**
         * Returns the element stored at the node.
         *
         * @return the element stored at the node
         */
        public E getElement() {
            return element;
        }

        /**
         * Returns the node that follows this one (or null if no such node).
         *
         * @return the following node
         */
        public Node<E> getNext() {return next;}

        // Modifier methods

        /**
         * Sets the node's next reference to point to Node n.
         *
         * @param n the node that should follow this one
         */
        public void setNext(Node<E> n) {next = n;}
    } //----------- end of nested Node class -----------

    /**
     * The head node of the list
     */
    private Node<E> head = null;               // head node of the list (or null if empty)


    /**
     * Number of nodes in the list
     */
    private int size = 0;                      // number of nodes in the list

    public SinglyLinkedList() {
    }              // constructs an initially empty list

    //@Override
    public int size() {
        return size;
    }

    //@Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int position) {
        Node<E> current = head;
        for (int i = 0; i < position; i++) {
            current = current.getNext();
        }
        return current.getElement();
    }

    @Override
    public void add(int position, E e) {
        Node<E> current = head;
        for (int i = 1; i < position; i++) {
            current = current.getNext();
        }
        current.next = new Node<E>(e, current.next);
    }


    @Override
    public void addFirst(E e) {
        head  = new Node<>(e, head);
        size++;
    }

    @Override
    public void addLast(E e) {
        Node<E> current = new Node<>(e, null);
        Node<E> last = head;
        if (head == null) {
            head = current;
        }
        else {
            while (last.getNext() != null) {
                last = last.getNext();
            }
            last.setNext(current);
        }
        size++;
    }

    @Override
    public E remove(int position) {
        Node<E> current = head;
        Node<E> previous = null;
        for (int i = 0; i < position; i++) {
            previous = current;
            current = current.getNext();
        }
        E element = current.getElement();
        previous.next = current.next;
        size--;
        return element;
    }

    @Override
    public E removeFirst() {
        if (head == null) {
            return null;
        }
        E first = head.getElement();
        head = head.getNext();
        size--;
        return first;
    }

    @Override
    public E removeLast() {
        Node<E> current = head;
        Node<E> previous = null;
        if (size == 1){
            E element = head.getElement();
            head = null;
            size--;
            return element;
        }
        while (current.getNext() != null) {
            previous = current;
            current = current.getNext();
        }
        previous.next = null;
        size--;
        return current.getElement();
    }

    public Node sortedMerge(Node l1, Node l2) {
        Comparator c = Comparator.naturalOrder();
        SinglyLinkedList<E> newList = new SinglyLinkedList<E>();
        Node<E> curr;
        while (l1 != null && l2 != null) {
            if (c.compare(l1.getElement(), l2.getElement()) <= 0) {
                curr = l1;
                l1 = l1.getNext();
                newList.addLast(curr.getElement());
            }
            else {
                curr = l2;
                l2 = l2.getNext();
                newList.addLast(curr.getElement());
            }
        }
        if (l1 != null) {
            while (l1 != null) {
                curr = l1;
                l1 = l1.getNext();
                newList.addLast(curr.getElement());
            }
        }
        if (l2 != null) {
            while (l2 != null) {
                curr = l2;
                l2 = l2.getNext();
                newList.addLast(curr.getElement());
            }
        }
        return newList.head;
    }

    public SinglyLinkedList<E> copy() {
        SinglyLinkedList<E> twin = new SinglyLinkedList<E>();
        Node<E> tmp = head;
        while (tmp != null) {
            twin.addLast(tmp.getElement());
            tmp = tmp.next;
        }
        return twin;
    }

    public SinglyLinkedList<E> recursiveCopy() {
        SinglyLinkedList<E> copy = new SinglyLinkedList<>();
        copy.head = recursiveCopyHelper(this.head);
        copy.size = this.size;
        return copy;
    }

    private Node<E> recursiveCopyHelper(Node<E> node) {
        if (node == null)
            return null;

        Node<E> newNode = new Node<>(node.getElement(), null);
        newNode.next = recursiveCopyHelper(node.getNext());
        return newNode;
    }

    public void reverse() {
        Node<E> prev = null;
        Node<E> curr = head;
        Node<E> next;
        while(curr != null) {
            next = curr.getNext();
            curr.setNext(prev);
            prev = curr;
            curr = next;
        }
        head = prev;
    }

    //@Override
    public Iterator<E> iterator() {
        return new SinglyLinkedListIterator<E>();
    }

    private class SinglyLinkedListIterator<E> implements Iterator<E> {
        Node<E> curr = (Node<E>) head;

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public E next() {
            E res = curr.getElement();
            curr = curr.next;
            return res;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = head;
        while (curr != null) {
            sb.append(curr.getElement());
            if (curr.getNext() != null)
                sb.append(", ");
            curr = curr.getNext();
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        SinglyLinkedList<Integer> ll = new SinglyLinkedList<Integer>();
        System.out.println("ll " + ll + " isEmpty: " + ll.isEmpty());
        //LinkedList<Integer> ll = new LinkedList<Integer>();

        ll.addFirst(0);
        ll.addFirst(1);
        ll.addFirst(2);
        ll.addFirst(3);
        ll.addFirst(4);
        ll.addLast(-1);
        //ll.removeLast();
        //ll.removeFirst();
        //System.out.println("I accept your apology");
        //ll.add(3, 2);
        System.out.println(ll);
        ll.remove(5);
        System.out.println(ll);

    }
}