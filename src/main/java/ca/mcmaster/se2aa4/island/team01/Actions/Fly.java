package ca.mcmaster.se2aa4.island.team01.Actions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class Fly {
    private final Logger logger = LogManager.getLogger();

    public String flyOneUnit(){
        JSONObject request = new JSONObject();
        request.put("action", "fly"); // do fly action

        logger.info("Fly request:" + request.toString()); 
        return request.toString();
    }
}
