package ca.mcmaster.se2aa4.island.team01;

/**
 * Singleton class that tracks and updates the current location based on movement in a given direction.
 */
public class CurrentLocationTracker {

    private static CurrentLocationTracker instance = null;

    private CurrentLocationTracker() {
        // Private constructor to prevent instantiation
    }

    /**
     * Returns the singleton instance of CurrentLocationTracker.
     * @return The instance of CurrentLocationTracker.
     */
    public static synchronized CurrentLocationTracker get() {
        if (instance == null) {
            instance = new CurrentLocationTracker();
        }
        return instance;
    }
    
    /**
     * Updates the coordinate system based on the performed action.
     * @param direction The direction to move (NORTH, SOUTH, EAST, WEST).
     */
    public void move(Direction direction) {
        CurrentLocation currLocation = CurrentLocation.get();

        if (direction == Direction.NORTH) {
            currLocation.setYCoordinate(currLocation.getYCoordinate() - 1);
        }
        else if (direction == Direction.SOUTH) {
            currLocation.setYCoordinate(currLocation.getYCoordinate() + 1);
        }
        else if (direction == Direction.EAST) {
            currLocation.setXCoordinate(currLocation.getXCoordinate() + 1);
        }
        else if (direction == Direction.WEST) {
            currLocation.setXCoordinate(currLocation.getXCoordinate() - 1);
        }
    }
}
