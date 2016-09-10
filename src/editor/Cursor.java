package editor;

/**
 * Created by jaleath on 9/6/16.
 */
public class Cursor {
    private int position;
    private int line;

    public Cursor(int l, int pos) {
        if (l > 0) {
            line = l;
        } else {
            line = 0;
        }
        if (pos > 0) {
            position = pos;
        } else {
            position = 0;
        }
    }

    public int getPosition() {
        return position;
    }

    public int getLine() {
        return line;
    }
}
