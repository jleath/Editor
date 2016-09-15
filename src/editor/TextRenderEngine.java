package editor;

import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * A utility class for position text and generating a Group of Text nodes for display by the Editor application.
 */
public class TextRenderEngine {
    private static final int LEFT_MARGIN = 5;
    private static final int RIGHT_MARGIN = 5;
    private static final int TOP_MARGIN = 0;

    /**
     * The maximum width of the text box built by the engine.
     */
    private int groupWidth;

    /**
     * The maximum height of the text box build by the engine.
     */
    private int groupHeight;

    /**
     * The font size of the text to be built.
     */
    private int fontSize;

    /**
     * The line height of the text to be built. Will be determined by the engine as it works.
     */
    private int lineHeight;

    /**
     * The name of the font to be used by the engine.
     */
    private String fontName;

    /**
     * A container to store positioning information as well as information for write access via a cursor.
     */
    private TextStore textStore;

    /**
     * A coordinate position that indicates where the next textbox should be positioned.
     */
    private Point nextPosition;

    public TextRenderEngine(int width, int height, int defaultFontSize, String defaultFontName) {
        groupWidth = width;
        groupHeight = height;
        fontSize = defaultFontSize;
        fontName = defaultFontName;
        textStore = new TextStore();
        nextPosition = new Point(LEFT_MARGIN, TOP_MARGIN);
    }

    public static Cursor defaultCursor() {
        return new Cursor(null, new Point(LEFT_MARGIN, TOP_MARGIN));
    }

    /**
     * Cursor functionality has not yet been implemented, these are just here as a reminder for now.
     */
    public Cursor getCursor(double xPos, double yPos) {
        int line = calculateLineNumber(yPos);
        TextInfo ti = textStore.getTextAt(line, xPos);
        return new Cursor(ti.getNodeInBuffer(), ti.getPosition());
    }

    /**
     * Processes each String in BUFFER, positions it, adds it into the textStore for write access
     * and returns a Group holding Text boxes.
     */
    public Group build(FastLinkedList<String> buffer) {
        nextPosition = new Point(LEFT_MARGIN, TOP_MARGIN);
        textStore = new TextStore();
        lineHeight = getLineHeight();
        for (int i = 0; i < buffer.size(); ++i) {
            FastLinkedList.Node node = buffer.getNodeAt(i);
            String textFromBuffer = (String) node.getValue();
            Text toInsert = buildTextBox(textFromBuffer);
            int lineNumber = calculateLineNumber(toInsert.getY());
            TextInfo newInfo = new TextInfo(toInsert, node);
            node.setPosition(newInfo.getPosition());
            textStore.addToLine(lineNumber, newInfo);
        }
        return textStore.getTextGroup();
    }

    /**
     * Calculates and returns the number of the line that a given Y-coordinate position corresponds to.
     */
    private int calculateLineNumber(double yPos) {
        return (int) (yPos / lineHeight);
    }

    /**
     * Constructs and positions a Text box containing TEXT.
     */
    private Text buildTextBox(String text) {
        Text result = new Text(text);
        result.setTextOrigin(VPos.TOP);
        result.setFont(new Font(fontName, (double) fontSize));
        positionTextBox(result);
        return result;
    }

    /**
     * Calculates the position of a text box and handles word wrapping. If T represents a newline character, T will be inserted
     * into the textStore on a new line. If T will cause the display to overrun the boundaries of the application
     * window, it will move the last full word to the next line. If the last word is long enough that it cannot fit on one line
     * no text will be moved and T will simply be placed on a newline. Otherwise, T will be positioned according to the coordinates
     * of nextPosition.
     */
    private void positionTextBox(Text t) {
        if (t.getText().equals("\n")) {
            setPositionToStartOfNextLine();
        } else if (textOverflowsWindow(t)) {
            setPositionToStartOfNextLine();
            FastLinkedList<TextInfo> lastWord = textStore.popLastWord();
            while (!lastWord.isEmpty()) {
                TextInfo textInfo = lastWord.delete();
                Text toInsert = buildTextBox(textInfo.getText());
                int lineNumber = calculateLineNumber(toInsert.getY());
                textStore.addToLine(lineNumber, new TextInfo(toInsert, textInfo.getNodeInBuffer()));
            }
        }
        setToNextPosition(t);
    }

    /**
     * Returns true if insertion of T into the textStore will result in a Text Group with characters
     * that will overflow the bounds of the application window.
     */
    private boolean textOverflowsWindow(Text t) {
        return nextPosition.getX() + getTextWidth(t) > groupWidth - RIGHT_MARGIN;
    }

    /**
     * Sets nextPosition to point at the start of the next line.
     */
    private void setPositionToStartOfNextLine() {
        int newY = nextPosition.getY() + lineHeight;
        nextPosition = new Point(LEFT_MARGIN, newY);
    }

    /**
     * Sets nextPosition to point at the next space for a textBox on the current line.
     */
    private void setToNextPosition(Text t) {
        t.setX(nextPosition.getX());
        t.setY(nextPosition.getY());
        nextPosition = new Point(nextPosition.getX() + getTextWidth(t), nextPosition.getY());
    }

    /**
     * Returns the height that each line will have based on the height of a Text box.
     * This Text box's height will vary based on font name and size.
     */
    private int getLineHeight() {
        Text heightTest = new Text("T");
        heightTest.setFont(new Font(fontName, (double) fontSize));
        return getTextHeight(heightTest);
    }

    /**
     * Returns the height of a textBox.
     */
    private int getTextHeight(Text t) {
        return (int) (t.getLayoutBounds().getHeight() + 1);
    }

    /**
     * Returns the width of a textbox.
     */
    private int getTextWidth(Text t) {
        return (int) (t.getLayoutBounds().getWidth() + 1);
    }

    /**
     * Set the width of the Group returned by the render engine's build method.
     */
    public void setGroupWidth(int newWidth) {
        groupWidth = newWidth;
    }

    /**
     * Set the height of the Group returned by the render engine's build method.
     */
    public void setGroupHeight(int newHeight) {
        groupHeight = newHeight;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int newSize) {
        fontSize = newSize;
    }
}
