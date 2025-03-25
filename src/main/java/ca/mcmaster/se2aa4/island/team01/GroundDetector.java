package ca.mcmaster.se2aa4.island.team01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

/**
 * The GroundDetector class is responsible for detecting whether the ground is found based on information provided
 * through ExtraInfo. It uses the information to determine if the drone echoes ground or out of range.
 */
public class GroundDetector {
    private final Logger logger = LogManager.getLogger();
    private boolean groundFound;
    private ExtraInfo information;
    private int range;

    public GroundDetector(){
        groundFound = false;
    }

    /**
     * Updates the information used by the GroundDetector. If the provided ExtraInfo is valid,
     * it attempts to detect whether the ground is found and saves the range.
     * @param info The ExtraInfo object containing the data to update the detector.
     */
    public void updateInfo(ExtraInfo info) {
        if (info == null) {
            logger.error("Received null ExtraInfo in GroundDetector. Skipping update.");
            return;
        }
        this.information = info;
        detectGround();
    }

    /**
     * Detects whether the ground is found by parsing the provided ExtraInfo's extras data.
     * If ground is found, the range is also extracted.
     * @return true if ground is found, false otherwise.
     */
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
