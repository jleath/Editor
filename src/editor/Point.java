package editor;

/**
 * A simple immutable point class to store and access x and y coordinates of objects.
 * This class is immutable because I want to easily share position information across
 * objects without worrying about accidentally changing the position of the wrong item.
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

    public int getY() {
        return yPos;
    }

    public String toString() {
        return xPos + " " + yPos;
    }
}
