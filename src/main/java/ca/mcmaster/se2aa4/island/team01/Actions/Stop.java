package ca.mcmaster.se2aa4.island.team01.Actions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class Stop {
    private final Logger logger = LogManager.getLogger();
    
    public String returnToHeadquarters(){
        JSONObject request = new JSONObject();
        request.put("action", "stop");
        logger.info("Stop request: " + request.toString());
        return request.toString();

    }
}
