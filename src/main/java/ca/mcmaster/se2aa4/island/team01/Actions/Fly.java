package ca.mcmaster.se2aa4.island.team01.Actions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team01.Direction;
import ca.mcmaster.se2aa4.island.team01.CurrentLocationTracker;

public class Fly {
    private final Logger logger = LogManager.getLogger();
    CurrentLocationTracker tracker = new CurrentLocationTracker();

    public String flyOneUnit(Direction direction){
        JSONObject request = new JSONObject();
        request.put("action", "fly"); // do fly action

        logger.info("Fly request:" + request.toString()); 

        tracker.move(direction);
        return request.toString();
    }
}
