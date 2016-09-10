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
    private String fontName;
    private TextStore textStore;
    private CoordinatePosition nextPosition;

    public TextRenderEngine(int width, int height, int defaultFontSize, String defaultFontName) {
        groupWidth = width;
        groupHeight = height;
        fontSize = defaultFontSize;
        fontName = defaultFontName;
        textStore = new TextStore();
        nextPosition = new CoordinatePosition(LEFT_MARGIN, TOP_MARGIN);
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
        Text heightTest = new Text("T");
        heightTest.setFont(new Font(fontName, (double) fontSize));
        int lineHeight = (int) (heightTest.getLayoutBounds().getHeight() + 1);
        textStore.addNewLine(0);
        for (int i = 0; i < buffer.size(); ++i) {
            String textFromBuffer = buffer.get(i).getValue();
            Text toInsert = new Text(textFromBuffer);
            toInsert.setTextOrigin(VPos.TOP);
            int currentX = nextPosition.getX();
            int currentY = nextPosition.getY();
            toInsert.setY(currentY);
            toInsert.setX(currentX);
            toInsert.setFont(new Font(fontName, (double) fontSize));
            nextPosition.setX(currentX + (int) Math.round(toInsert.getLayoutBounds().getWidth()));
            textStore.addToLine(0, new TextInfo(toInsert, buffer.get(i)));
        }
        return textStore.getTextGroup();
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
