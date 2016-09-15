package editor;

/**
 * A custom circular linked list that keeps a reference to one of its own nodes for quick insertion and removal.
 */
public class FastLinkedList<T> {
    /**
     * The node where insertion or deletion will occur. Inserts will add a new node directly after this one.
     * Deletions will delete this node and move this to the previous node.
     */
    private Node insertionPoint;

    /**
     * The number of items in the linkedList.
     */
    private int size;

    /**
     * The root element of the linked list.
     */
    private Node sentinel;

    public FastLinkedList() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        insertionPoint = sentinel;
        size = 0;
    }

    /**
     * Insert into the textbuffer. VALUE will be inserted directly after insertionPoint, and insertionPoint
     * will be moved to point to the new Node.
     */
    public void insert(T value) {
        Node toInsert = new Node(value, insertionPoint.next, insertionPoint);
        insertionPoint.next.prev = toInsert;
        insertionPoint.next = toInsert;
        insertionPoint = toInsert;
        size += 1;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public Node getNodeAt(int i) {
        if (i >= size()) {
            throw new RuntimeException("Index out of bounds");
        }
        Node curr = sentinel.next;
        while (i > 0) {
            curr = curr.next;
            i -= 1;
        }
        return curr;
    }

    public T getValueFromNode(int i) {
        return getNodeAt(i).getValue();
    }

    /**
     * Remove insertionPoint from the textBuffer. Throws an exception if the textbuffer is empty or if
     * an attempt is made to remove the sentinel node. Insertionpoint will be moved to point at the node
     * before the removed node.
     */
    public T delete() {
        if (isEmpty()) {
            throw new RuntimeException("Attempt to delete from an empty TextBuffer");
        }
        if (insertionPoint == sentinel) {
            throw new RuntimeException("Attempt to delete sentinel node from TextBuffer");
        }
        T result = insertionPoint.value;
        insertionPoint.prev.next = insertionPoint.next;
        insertionPoint.next.prev = insertionPoint.prev;
        insertionPoint = insertionPoint.prev;
        size -= 1;
        return result;
    }

    public Node getInsertionPoint() {
        return insertionPoint;
    }

    public void setInsertionPoint(Node n) {
        insertionPoint = n;
    }

    public String toString() {
        String result = "";
        if (isEmpty()) {
            return result;
        }
        Node curr = sentinel.next;
        while (curr != sentinel) {
            result += curr.value.toString();
            curr = curr.next;
        }
        return result;
    }

    /**
     * A node in a FastLinkedList
     */
    public class Node {
        T value;
        Node next;
        Node prev;
        Point position;

        Node(T val, Node n, Node p) {
            value = val;
            next = n;
            prev = p;
        }

        public Point getPosition() {
            return position;
        }

        public void setPosition(Point p) {
            position = p;
        }

        public T getValue() {
            return value;
        }
    }

}
