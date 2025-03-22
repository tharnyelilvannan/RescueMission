package ca.mcmaster.se2aa4.island.team01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team01.Actions.Echo;
import ca.mcmaster.se2aa4.island.team01.Actions.Fly;
import ca.mcmaster.se2aa4.island.team01.Actions.Heading;

public class FollowCoastline {
    private ExtraInfo information;
    private Fly fly;
    private Heading heading;
    private Echo echo;
    private boolean echoedLeft = false;
    private boolean echoedRight = false;
    private String lastEchoDirection;
    private boolean groundOnRight = false;
    private boolean groundOnLeft = false;
    private final Logger logger = LogManager.getLogger();
    private FindIsland findIsland;
    private Direction currentDirection;
    private int stepsSinceLastScan = 0; 
    private boolean scannedOnLastAction = false;
    private final CreekFinder creekFinder;

    public FollowCoastline() {
        findIsland = new FindIsland();
        fly = new Fly();
        heading = new Heading();
        echo = new Echo();
        creekFinder = new CreekFinder();
        this.currentDirection = findIsland.getCurrentDirection();  // Now it's safe to access
    }
    

    public void updateInfo(ExtraInfo info) {
        if (info == null) {
            logger.error("Received null ExtraInfo in FollowCoastline. Skipping update.");
            return;
        }
        this.information = info;
        creekFinder.updateInfo(info);  // Pass ExtraInfo to CreekFinder
    }
    

    public String traverse() {
        logger.info("** Following the coastline");

        if (scannedOnLastAction) {
            scannedOnLastAction = false;
            return creekFinder.searchForCreek();
        }
        
        if (!echoedRight) {
            echoedRight = true;
            lastEchoDirection = "RIGHT";
            return echo.echoRightWing(currentDirection);
        } 

        if (!echoedLeft) {
            echoedLeft = true;
            lastEchoDirection = "LEFT";
            return echo.echoLeftWing(currentDirection);
        }
        
        if (information != null) {
            JSONObject extras = information.getExtras();
            if (extras.has("found")) {
                String found = extras.getString("found");
                if ("GROUND".equals(found)) {
                    if ("RIGHT".equals(lastEchoDirection)) {
                        groundOnRight = true;
                    } else if ("LEFT".equals(lastEchoDirection)) {
                        groundOnLeft = true;
                    }
                }
            }
        }
        echoedRight = false;
        echoedLeft = false;

        stepsSinceLastScan++;

        if (stepsSinceLastScan >= 3) {
            stepsSinceLastScan = 0;
            scannedOnLastAction = true;
            return creekFinder.searchForCreek();
        }
    
        if (groundOnRight && !groundOnLeft) {
            return fly.flyOneUnit();
        } else if (!groundOnRight && groundOnLeft) {
            return fly.flyOneUnit();
        } else if (!groundOnRight && !groundOnLeft) {
            currentDirection = currentDirection.turnRight();
            return heading.changeHeading(currentDirection);
        } else {
            currentDirection = currentDirection.turnLeft();
            return heading.changeHeading(currentDirection);
        }
    }
}