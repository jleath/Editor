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
    }

    public int numLines() {
        return lines.size();
    }

    public void addToLine(int lineNumber, TextInfo text) {
        lines.get(lineNumber).addToEnd(text);
    }

    public void addNewLine(int lineNumber) {
        lines.add(lineNumber, new Line());
    }

    public TextInfo removeFromLine(int lineNumber) {
        Line lineToRemoveFrom = lines.get(lineNumber);
        return lineToRemoveFrom.deleteFromEnd();
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

        private int size() {
            return characters.size();
        }

        private Text getText(int i) {
            return characters.get(i).getValue().getTextBox();
        }
    }
}
