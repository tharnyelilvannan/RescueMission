package ca.mcmaster.se2aa4.island.team01;

import ca.mcmaster.se2aa4.island.team01.Direction;
import ca.mcmaster.se2aa4.island.team01.CurrentLocation;

public class CurrentLocationTracker {
    CurrentLocation currLocation = CurrentLocation.get();
    
    public void move(Direction direction) {
        if (direction == Direction.NORTH) {
            currLocation.changeYCoordinate(currLocation.getYCoordinate() - 1);
        }
        else if (direction == Direction.SOUTH) {
            currLocation.changeYCoordinate(currLocation.getYCoordinate() + 1);
        }
        else if (direction == Direction.EAST) {
            currLocation.changeXCoordinate(currLocation.getXCoordinate() + 1);
        }
        else if (direction == Direction.WEST) {
            currLocation.changeXCoordinate(currLocation.getXCoordinate() - 1);
        }
    }
}