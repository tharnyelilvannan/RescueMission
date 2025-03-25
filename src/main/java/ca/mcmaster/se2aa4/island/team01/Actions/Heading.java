package ca.mcmaster.se2aa4.island.team01.Actions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team01.CurrentLocationTracker;
import ca.mcmaster.se2aa4.island.team01.Direction;

/**
 * The Heading class handles changing the drone's heading direction.
 */
public class Heading {
    private final Logger logger = LogManager.getLogger();

    /**
     * Sends a heading change request to adjust the drone's direction.
     * Updates the location tracker to reflect the change in direction.
     * @param currentDirection The new direction the drone should face.
     * @param lastDirection The previous direction before the change.
     * @return JSON string representing the heading change request.
     */
    public String changeHeading(Direction currentDirection, Direction lastDirection){
        JSONObject request = new JSONObject();
        request.put("action", "heading");

        JSONObject parameters = new JSONObject();
        parameters.put("direction", currentDirection.getValue());

        request.put("parameters", parameters); 
        logger.trace("Heading request:" + request.toString());
        
        CurrentLocationTracker tracker = CurrentLocationTracker.get();
        
        tracker.move(lastDirection);
        tracker.move(currentDirection);
        
        return request.toString();
    }
}
