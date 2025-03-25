package ca.mcmaster.se2aa4.island.team01;

/**
 * Singleton class that represents the current location with X and Y coordinates.
 * It extends Location and implements the Coordinates interface.
 */
public class CurrentLocation extends Location implements Coordinates {

    private static CurrentLocation instance = null; // Singleton instance

    private CurrentLocation() {
        super(0, 0); // Initialize with default coordinates (0, 0)
    }

    /**
     * Returns the singleton instance of CurrentLocation.
     * @return The instance of CurrentLocation.
     */
    public static synchronized CurrentLocation get() {
        if (instance == null) {
            instance = new CurrentLocation();
        }
        return instance;
    }

    public void setXCoordinate(int x) {
        location.set(0, x);
    }

    public void setYCoordinate(int y) {
        location.set(1, y);
    }

    public int getXCoordinate() {
        return location.get(0);
    }

    public int getYCoordinate() {
        return location.get(1);
    }
}
