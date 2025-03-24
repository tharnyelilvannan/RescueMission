package ca.mcmaster.se2aa4.island.team01;

import ca.mcmaster.se2aa4.island.team01.Direction;
import ca.mcmaster.se2aa4.island.team01.CurrentLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
 
public class CurrentLocationTracker {

    static CurrentLocationTracker instance = null;

    private CurrentLocationTracker() {

    }

    public static synchronized CurrentLocationTracker get() {
        if (instance == null) {
            instance = new CurrentLocationTracker();
        }
        return instance;
    }
    
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
