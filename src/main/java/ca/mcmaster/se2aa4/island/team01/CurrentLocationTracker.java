package ca.mcmaster.se2aa4.island.team01;

import ca.mcmaster.se2aa4.island.team01.Direction;
import ca.mcmaster.se2aa4.island.team01.CurrentLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
 
public class CurrentLocationTracker {
    private int x;
    private int y;
    static CurrentLocationTracker instance = null;
    Logger logger = LogManager.getLogger();

 
    private  CurrentLocationTracker() {
        this.x = 0; // Start point
        this.y = 0;
    }

    public static synchronized CurrentLocationTracker get() {
        if (instance == null) {
            instance = new CurrentLocationTracker();
        }
        return instance;
    }
    
 
    public void move(Direction direction) {
        switch (direction) {
            case NORTH:
                this.y -= 1;
                break;
            case SOUTH:
                this.y += 1;
                break;
            case EAST:
                this.x += 1;
                break;
            case WEST:
                this.x -= 1;
                break;
        }
    }
 
    public int getXCoordinate() {
        return this.x;
    }
 
    public int getYCoordinate() {
        return this.y;
    }
}

/* 
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

        Logger logger = LogManager.getLogger();
        logger.error(direction);
        logger.error(currLocation.getXCoordinate());
        logger.error(currLocation.getYCoordinate());
        if (direction == Direction.NORTH) {
            currLocation.setYCoordinate(currLocation.getYCoordinate() + 1);
        }
        else if (direction == Direction.SOUTH) {
            currLocation.setYCoordinate(currLocation.getYCoordinate() - 1);
        }
        else if (direction == Direction.EAST) {
            currLocation.setXCoordinate(currLocation.getXCoordinate() - 1);
        }
        else if (direction == Direction.WEST) {
            currLocation.setXCoordinate(currLocation.getXCoordinate() + 1);
        }
        logger.error(currLocation.getXCoordinate());
        logger.error(currLocation.getYCoordinate());
    }
    */
