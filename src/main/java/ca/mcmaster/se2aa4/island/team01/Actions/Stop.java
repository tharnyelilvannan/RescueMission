package ca.mcmaster.se2aa4.island.team01.Actions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

/**
 * The Stop class handles stopping the drone and returning it to headquarters.
 */
public class Stop {
    private final Logger logger = LogManager.getLogger();
    
    /**
     * Sends a stop request, signaling the drone to return to headquarters.
     * @return JSON string representing the stop request.
     */
    public String returnToHeadquarters(){
        JSONObject request = new JSONObject();
        request.put("action", "stop");
        
        logger.trace("Stop request: " + request.toString());
        return request.toString();
    }
}