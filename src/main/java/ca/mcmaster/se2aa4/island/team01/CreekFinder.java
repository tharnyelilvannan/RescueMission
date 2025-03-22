package ca.mcmaster.se2aa4.island.team01;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team01.Actions.Scan;
import ca.mcmaster.se2aa4.island.team01.Actions.Fly;
import ca.mcmaster.se2aa4.island.team01.Actions.Echo;

public class CreekFinder {
    private final Logger logger = LogManager.getLogger();
    private boolean creekFound = false;
    private String creekId = null;
    private final Scan scan;
    private boolean hasScanned = false;
    public ExtraInfo information;
    private final Fly fly;
    private final Echo echo;

    public CreekFinder() {
        scan = new Scan();
        fly = new Fly();
        echo = new Echo();
    }

    public void updateInfo(ExtraInfo info) {
        if (info == null) {
            logger.error("updateInfo(): Received null ExtraInfo");
        } else {
            logger.info("updateInfo(): Received valid ExtraInfo");
        }
        this.information = info;
    }
    

    public String searchForCreek() {
        logger.info(hasScanned);

        if (information == null) {
            logger.error("searchForCreek(): information is null");
            return null;
        }
        if (!creekFound && !hasScanned) {
            logger.info("searchForCreek(): Performing scan");
            hasScanned = true;
            return scan.scanArea(); // the issue is it doesnt search the scan for extras; it searches fly for extras
        } else if (hasScanned) {
            hasScanned = false;
            JSONObject extras = information.getExtras();
            logger.info(extras);
            if (extras == null) {
                logger.error("searchForCreek(): information.getExtras() is null");
                return null;
            }
            if (extras.has("creeks")) {
                JSONArray creeks = extras.getJSONArray("creeks");
                logger.info("searchForCreek(): Found creeks array with size " + creeks.length());
    
                if (creeks.length() < 1) {   // No creeks found
                    logger.info("searchForCreek(): No creeks found in scan");
                    return null;
                } else {
                    creekId = creeks.getString(0);
                    creekFound = true;
                    logger.info("searchForCreek(): Creek found with ID " + creekId);
                    return null;
                }
            } else {
                logger.info("searchForCreek(): 'creeks' key not found in extras");
            }
        }
        return null; // Continue normal operations if no creek is found
    }
    

    public boolean isCreekFound() {
        return creekFound;
    }

    public String getCreekId() {
        return creekId;
    }
}