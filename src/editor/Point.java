package editor;

/**
 * Created by jaleath on 9/9/16.
 */
public class Point {
    private int xPos;
    private int yPos;

    public Point(int x, int y) {
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

    public String toString() {
        return xPos + " " + yPos;
    }
}
