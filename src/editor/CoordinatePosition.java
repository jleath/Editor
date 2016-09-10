package editor;

/**
 * Created by jaleath on 9/9/16.
 */
public class CoordinatePosition {
    private int xPos;
    private int yPos;

    public CoordinatePosition(int x, int y) {
        xPos = x;
        yPos = y;
    }

    public int getX() {
        return xPos;
    }

    public void setX(int x) {
        xPos = x;
    }

    public int getY() {
        return yPos;
    }

    public void setY(int y) {
        yPos = y;
    }
}
