package ca.mcmaster.se2aa4.island.team01.Actions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class Scan {
    private final Logger logger = LogManager.getLogger();

    public String scanArea(){
        JSONObject request = new JSONObject();
        request.put("action", "scan"); // do scan action

        logger.info("Scan request:" + request.toString()); 
        return request.toString();
    }
}
