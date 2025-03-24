package ca.mcmaster.se2aa4.island.team01.Actions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team01.Direction;
import ca.mcmaster.se2aa4.island.team01.CurrentLocationTracker;

public class Heading {
    private final Logger logger = LogManager.getLogger();

    public String changeHeading(Direction currentDirection, Direction lastDirection){
        JSONObject request = new JSONObject();
        request.put("action", "heading"); // do heading action

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
