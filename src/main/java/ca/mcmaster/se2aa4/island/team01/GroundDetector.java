package ca.mcmaster.se2aa4.island.team01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class GroundDetector {
    private final Logger logger = LogManager.getLogger();
    private boolean groundFound;
    private ExtraInfo information;
    private int range;

    public GroundDetector(){
        groundFound = false;
    }

    public void updateInfo(ExtraInfo info) {
        if (info == null) {
            logger.error("Received null ExtraInfo in GroundDetector. Skipping update.");
            return;
        }
        this.information = info;
        detectGround();
    }
    
   
    protected boolean detectGround() {
        JSONObject extras = information.getExtras();

        if (extras.has("found")) {
            String found = extras.getString("found");
            
            if ("GROUND".equals(found)){
                groundFound = true; // found ground

                if (extras.has("range")){
                    range = extras.getInt("range");  
                    logger.trace("the range is " + range);
                }
            } 
            else if ("OUT_OF_RANGE".equals(found)) { // If OUT_OF_RANGE
                logger.trace("Ground out of range"); 
                
            }
        }
        return groundFound;
    }

    protected int getRange(){
        return range; 
    }

    protected boolean isGroundFound() {
        return groundFound;
    }
}