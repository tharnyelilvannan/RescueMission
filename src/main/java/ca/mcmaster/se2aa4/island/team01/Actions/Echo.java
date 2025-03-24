package ca.mcmaster.se2aa4.island.team01.Actions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team01.Direction;

public class Echo {
    private final Logger logger = LogManager.getLogger();

    public String echoStraight(Direction currentDirection) {
            JSONObject request = new JSONObject();
            request.put("action", "echo"); // do echo action

            JSONObject parameters = new JSONObject();
            parameters.put("direction", currentDirection.getValue()); 

            request.put("parameters", parameters);
            logger.trace("Echo request: " + request.toString());

            return request.toString();
        }

    public String echoLeftWing(Direction currentDirection) {
        JSONObject request = new JSONObject();
        request.put("action", "echo"); // do echo action

        JSONObject parameters = new JSONObject();
        parameters.put("direction", currentDirection.turnLeft().getValue()); 

        request.put("parameters", parameters);
        logger.trace("Echo request: " + request.toString());

        return request.toString();
    }

    public String echoRightWing(Direction currentDirection) {
        JSONObject request = new JSONObject();
        request.put("action", "echo"); // do echo action

        JSONObject parameters = new JSONObject();
        parameters.put("direction", currentDirection.turnRight().getValue()); 

        request.put("parameters", parameters);
        logger.trace("Echo request: " + request.toString());

        return request.toString();
    }
}
