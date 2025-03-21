package ca.mcmaster.se2aa4.island.team01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import netscape.javascript.JSObject;

public class EmergencySiteDetector extends GroundDetector {

    private final Logger logger = LogManager.getLogger();
    public boolean ESFound = false;

    public EmergencySiteDetector(Direction direction) {

        super(direction); 

    }
/*
    public String scan() {
        JSONObject request = new JSONObject();
        request.put("action", "scan"); // do scan action

        logger.info("Scan request:" + request.toString()); 
        processScan(request);
        return request.toString();
    }
*/
    public void processScan(JSONObject request) {
        String scan = request.optString("sites", "UNKNOWN");
        System.out.print(scan);

        if (!scan.equals("[]")) {
            ESFound = true;
            logger.info(scan);
            logger.info("Hellooooooooooooooooooooooooooooooooooo");
        }
    }

    public boolean isESFound() {
        return ESFound;
    }

}