package ca.mcmaster.se2aa4.island.team01.Actions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team01.Direction;

public class Heading {
    private final Logger logger = LogManager.getLogger();

    public String changeHeading(Direction currentDirection){
        JSONObject request = new JSONObject();
        request.put("action", "heading"); // do heading action

        JSONObject parameters = new JSONObject();
        parameters.put("direction", currentDirection.getValue());

        request.put("parameters", parameters); 
        logger.info("Heading request:" + request.toString()); 

        return request.toString();
    }
}
