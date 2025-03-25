package ca.mcmaster.se2aa4.island.team01;
/**
 * The Site class represents a creek on the island with a unique identifier and coordinates (x, y).
 * It implements the Coordinates interface to provide methods for setting and getting the ID coordinates.
 * It also implements the ID interface to manage the emergency site ID.
 */
public class Site implements Coordinates, ID {
    private static Site instance = null;
    private String id;
    private int x;
    private int y;

    private Site() {
    }

    public static synchronized Site get() {
        if (instance == null) {
            instance = new Site();
        }

        return instance;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getID() {
        return this.id;
    }

    public void setXCoordinate(int x) {
        this.x = x;
    }

    public void setYCoordinate(int y) {
        this.y = y;
    }

    public int getXCoordinate() {
        return this.x;
    }

    public int getYCoordinate() {
        return this.y;
    }

}
