package ca.mcmaster.se2aa4.island.team01.Actions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team01.Direction;

/**
 * The Echo class is responsible for generating echo scan requests
 * in different directions based on the drone's current direction.
 */

public class Echo {
    private final Logger logger = LogManager.getLogger();

    /**
     * Sends an echo request in the current direction.
     * @param currentDirection The current facing direction of the drone.
     * @return JSON string representing the echo request.
     */

    public String echoStraight(Direction currentDirection) {
        JSONObject request = new JSONObject();
        request.put("action", "echo");

        JSONObject parameters = new JSONObject();
        parameters.put("direction", currentDirection.getValue());

        request.put("parameters", parameters);
        logger.trace("Echo request: " + request.toString());

        return request.toString();
    }

    /**
     * Sends an echo request to the left of the current direction.
     * @param currentDirection The current facing direction of the drone.
     * @return JSON string representing the echo request.
     */
    public String echoLeftWing(Direction currentDirection) {
        JSONObject request = new JSONObject();
        request.put("action", "echo");

        JSONObject parameters = new JSONObject();
        parameters.put("direction", currentDirection.turnLeft().getValue());

        request.put("parameters", parameters);
        logger.trace("Echo request: " + request.toString());

        return request.toString();
    }

    /**
     * Sends an echo request to the right of the current direction.
     * @param currentDirection The current facing direction of the drone.
     * @return JSON string representing the echo request.
     */

    public String echoRightWing(Direction currentDirection) {
        JSONObject request = new JSONObject();
        request.put("action", "echo"); 

        JSONObject parameters = new JSONObject();
        parameters.put("direction", currentDirection.turnRight().getValue());

        request.put("parameters", parameters);
        logger.trace("Echo request: " + request.toString());

        return request.toString();
    }
}
