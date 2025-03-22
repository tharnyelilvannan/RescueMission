package ca.mcmaster.se2aa4.island.team01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class GroundDetector {
    private static final Logger logger = LogManager.getLogger(GroundDetector.class);
    
    private boolean groundFound = false;
    private int range;

    public void updateInfo(ExtraInfo info) {
        if (info == null) {
            logger.error("Received null ExtraInfo. Skipping update.");
            return;
        }
        detectGround(info.getExtras());
    }

    private void detectGround(JSONObject extras) {
        String found = extras.optString("found", "");
        
        EchoStatus status = EchoStatus.fromString(found);
        switch (status) {
            case GROUND:
                groundFound = true;
                updateRange(extras);
                break;
            case OUT_OF_RANGE:
                logger.info("Ground out of range.");
                groundFound = false;
                break;
            default:
                logger.warn("Unexpected 'found' value: " + found);
                groundFound = false;
        }
    }

    private void updateRange(JSONObject extras) {
        if (extras.has("range")) {
            range = extras.getInt("range");
            logger.info("Ground detected at range: " + range);
        }
    }

    public boolean isGroundFound() {
        return groundFound;
    }

    public int getRange() {
        return range;
    }
}
