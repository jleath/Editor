package editor;

import javafx.scene.text.Text;

/** A TextInfo object stores a Text object and the corresponding node from the editor's main FastLinkedList. */
public class TextInfo {
    private Text text;
    private FastLinkedList.Node nodeInBuffer;

    public TextInfo(Text t, FastLinkedList.Node n) {
        text = t;
        nodeInBuffer = n;
    }

    public FastLinkedList.Node getNodeInBuffer() {
        return nodeInBuffer;
    }

    public Text getTextBox() {
        return text;
    }

    public Point getPosition() {
        return new Point((int) text.getX(), (int) text.getY());
    }

    public String toString() {
        return text.getText() + " @ " + text.getX() + ", " + text.getY();
    }

    public boolean isSpace() {
        return getText().equals(" ");
    }

    public String getText() {
        return text.getText();
    }
}

