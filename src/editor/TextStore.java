package editor;

import javafx.scene.Group;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * A container for TextInfo objects, organized by line. This data structure is the source of positions and
 * values for the renderingEngine.
 */
public class TextStore {
    private ArrayList<Line> lines;

    public TextStore() {
        lines = new ArrayList<>();
        addNewLine();
    }

    public int numLines() {
        return lines.size();
    }

    public void addToLine(int lineNumber, TextInfo text) {
        if (lines.size() < lineNumber) {
            throw new RuntimeException("Attempted to insert on a line that can't exist");
        }
        if (lines.size() == lineNumber) {
            addNewLine();
        }
        lines.get(lineNumber).addToEnd(text);
    }

    public void addNewLine() {
        lines.add(new Line());
    }

    public TextInfo removeFromLine(int lineNumber) {
        Line lineToRemoveFrom = lines.get(lineNumber);
        return lineToRemoveFrom.deleteFromEnd();
    }

    /**
     * Returns a FastLinkedList containing the textInfo objects for the last full word
     * entered into the textStore. If the line consists only of one full word, will return
     * an empty FastLinkedList.
     */
    public FastLinkedList<TextInfo> popLastWord() {
        FastLinkedList<TextInfo> result = new FastLinkedList<>();
        Line lastLine = getLastLine();
        while (!lastLine.isEmpty() && !peekAtEnd().isSpace()) {
            result.insert(popFromEnd());
        }
        if (lastLine.isEmpty()) {
            pushInReverse(result);
            return new FastLinkedList<>();
        }
        return result;
    }

    private void pushInReverse(FastLinkedList<TextInfo> toPush) {
        Line lastLine = getLastLine();
        while (!toPush.isEmpty()) {
            lastLine.addToEnd(toPush.delete());
        }
    }

    /**
     * Returns the last TextInfo object of the last line in the textStore.
     * Returns null if the last line is empty.
     */
    public TextInfo popFromEnd() {
        Line lastLine = getLastLine();
        if (lastLine.isEmpty()) {
            return null;
        }
        return lastLine.deleteFromEnd();
    }

    public TextInfo peekAtEnd() {
        Line lastLine = getLastLine();
        if (lastLine.isEmpty()) {
            return null;
        }
        return lastLine.getFromEnd();
    }

    private Line getLastLine() {
        return lines.get(lines.size() - 1);
    }

    /**
     * Returns a group of all the text objects stored in the TextStore.
     */
    public Group getTextGroup() {
        Group result = new Group();
        for (Line line : lines) {
            for (int i = 0; i < line.size(); ++i) {
                result.getChildren().add(line.getText(i));
            }
        }
        return result;
    }

    public int getNumCharactersInLine(int lineNum) {
        return lines.get(lineNum).size();
    }

    /**
     * A line is simply a FastLinkedList that stores TextInfo objects.
     */
    private class Line {
        private FastLinkedList<TextInfo> characters;

        private Line() {
            characters = new FastLinkedList<>();
        }

        private void addToEnd(TextInfo text) {
            characters.insert(text);
        }

        private TextInfo deleteFromEnd() {
            return characters.delete();
        }

        private TextInfo getFromEnd() {
            return characters.getNodeAt(size() - 1).getValue();
        }

        private int size() {
            return characters.size();
        }

        private Text getText(int i) {
            return characters.getNodeAt(i).getValue().getTextBox();
        }

        private String getStringAt(int i) {
            return getText(i).getText();
        }

        private String getLastString() {
            return getStringAt(size() - 1);
        }

        private boolean isEmpty() {
            return size() == 0;
        }
    }
}
