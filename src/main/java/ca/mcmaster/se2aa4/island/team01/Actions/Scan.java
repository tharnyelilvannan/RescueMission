package ca.mcmaster.se2aa4.island.team01.Actions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

/**
 * The Scan class handles scanning the surrounding area for biomes, creeks, and emergency sites.
 */
public class Scan {
    private final Logger logger = LogManager.getLogger();

    /**
     * Sends a scan request to analyze the current surroundings.
     * @return JSON string representing the scan request.
     */
    public String scanArea(){
        JSONObject request = new JSONObject();
        request.put("action", "scan"); 

        logger.trace("Scan request:" + request.toString());
        return request.toString();
    }
}
