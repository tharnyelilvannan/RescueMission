package ca.mcmaster.se2aa4.island.team01.Actions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team01.CurrentLocationTracker;
import ca.mcmaster.se2aa4.island.team01.Direction;

/**
 * The Fly class handles movement actions for the drone.
 */
public class Fly {
    private final Logger logger = LogManager.getLogger(); // Logger for debugging
    private CurrentLocationTracker tracker = CurrentLocationTracker.get(); // Tracker for updating location

    /**
     * Sends a fly request to move the drone one unit in the specified direction.
     * Updates the current location tracker accordingly.
     * @param direction The direction in which the drone should move.
     * @return JSON string representing the fly request.
     */
    public String flyOneUnit(Direction direction){
        JSONObject request = new JSONObject();
        request.put("action", "fly");

        logger.trace("Fly request:" + request.toString());

        tracker.move(direction); // Update the drone's current location

        return request.toString();
    }
}
