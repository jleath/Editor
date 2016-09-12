package editor;

import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Created by jaleath on 9/6/16.
 */
public class TextRenderEngine {
    private static final int LEFT_MARGIN = 5;
    private static final int RIGHT_MARGIN = 5;
    private static final int TOP_MARGIN = 0;
    private static final int BOTTOM_MARGIN = 0;

    private int groupWidth;
    private int groupHeight;
    private int fontSize;
    private int lineHeight;
    private String fontName;
    private TextStore textStore;
    private Point nextPosition;

    public TextRenderEngine(int width, int height, int defaultFontSize, String defaultFontName) {
        groupWidth = width;
        groupHeight = height;
        fontSize = defaultFontSize;
        fontName = defaultFontName;
        textStore = new TextStore();
        nextPosition = new Point(LEFT_MARGIN, TOP_MARGIN);
    }

    public Cursor getCursor(int xPos, int yPos) {
        // TODO
        return null;
    }

    public Cursor moveCursorDown() {
        // TODO
        return null;
    }

    public Cursor moveCursorUp() {
        // TODO
        return null;
    }

    public Cursor moveCursorLeft() {
        // TODO
        return null;
    }

    public Cursor moveCursorRight() {
        // TODO
        return null;
    }

    public Group build(FastLinkedList<String> buffer) {
        textStore = new TextStore();
        lineHeight = getLineHeight();
        for (int i = 0; i < buffer.size(); ++i) {
            String textFromBuffer = buffer.getValueFromNode(i);
            Text toInsert = buildTextBox(textFromBuffer);
            int lineNumber = calculateLineNumber(toInsert.getY());
            textStore.addToLine(lineNumber, new TextInfo(toInsert, buffer.getNodeAt(i)));
        }
        return textStore.getTextGroup();
    }

    private int calculateLineNumber(double yPos) {
        return (int) (yPos / lineHeight);
    }

    private Text buildTextBox(String text) {
        Text result = new Text(text);
        result.setTextOrigin(VPos.TOP);
        result.setFont(new Font(fontName, (double) fontSize));
        positionTextBox(result);
        return result;
    }

    private void positionTextBox(Text t) {
        if (t.getText().equals("\n")) {
            setToStartOfNextLine(t);
            return;
        }
        if (textOverflowsWindow(t)) {
            int newX = LEFT_MARGIN;
            int newY = nextPosition.getY() + lineHeight;
            FastLinkedList<TextInfo> poppedFromTextStore = new FastLinkedList<>();
            while (!textStore.peekAtEnd().equals(" ")) {
                TextInfo popped = textStore.popFromEnd();
                if (popped == null) {
                    while (poppedFromTextStore.size() > 0) {
                        int lineToAddBack = textStore.numLines() - 1;
                        textStore.addToLine(lineToAddBack, poppedFromTextStore.delete());
                    }
                    t.setX(LEFT_MARGIN);
                    t.setY(nextPosition.getY() + lineHeight);
                    nextPosition = new Point(LEFT_MARGIN + getTextWidth(t), nextPosition.getY() + lineHeight);
                    return;
                }
                poppedFromTextStore.insert(popped);
            }
            nextPosition = new Point(LEFT_MARGIN, nextPosition.getY() + lineHeight);
            while (poppedFromTextStore.size() > 0) {
                FastLinkedList.Node nodeFromTextStore = poppedFromTextStore.delete().getNodeInBuffer();
                String textFromPopped = (String) nodeFromTextStore.getValue();
                Text toInsert = buildTextBox(textFromPopped);
                int lineNumber = calculateLineNumber(toInsert.getY());
                textStore.addToLine(lineNumber, new TextInfo(toInsert, nodeFromTextStore));
            }
            setToNextPosition(t);
            return;
        } else {
            setToNextPosition(t);
            return;
        }
    }

    private boolean textOverflowsWindow(Text t) {
        return nextPosition.getX() + getTextWidth(t) > groupWidth - RIGHT_MARGIN;
    }

    private void setToStartOfNextLine(Text t) {
        int newX = LEFT_MARGIN;
        int newY = nextPosition.getY() + lineHeight;
        t.setX(newX);
        t.setY(newY);
        nextPosition = new Point(newX + getTextWidth(t), newY);
    }

    private void setToNextPosition(Text t) {
        t.setX(nextPosition.getX());
        t.setY(nextPosition.getY());
        nextPosition = new Point(nextPosition.getX() + getTextWidth(t), nextPosition.getY());
    }

    private int getLineHeight() {
        Text heightTest = new Text("T");
        heightTest.setFont(new Font(fontName, (double) fontSize));
        return getTextHeight(heightTest);
    }

    private int getTextHeight(Text t) {
        return (int) (t.getLayoutBounds().getHeight() + 1);
    }

    private int getTextWidth(Text t) {
        return (int) (t.getLayoutBounds().getWidth() + 1);
    }

    public void setgroupWidth(int newWidth) {
        groupWidth = newWidth;
    }

    public void setgroupHeight(int newHeight) {
        groupHeight = newHeight;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int newSize) {
        fontSize = newSize;
    }
}
