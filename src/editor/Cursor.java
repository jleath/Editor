package editor;

/**
 * Not yet fully implemented.
 */
public class Cursor {
    private FastLinkedList.Node node;
    private Point position;

    public Cursor(FastLinkedList.Node n, Point p) {
        node = n;
        position = p;
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public FastLinkedList.Node getNode() {
        return node;
    }
}
