package ca.mcmaster.se2aa4.island.team01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team01.Actions.Echo;
import ca.mcmaster.se2aa4.island.team01.Actions.Fly;
import ca.mcmaster.se2aa4.island.team01.Actions.Heading;
import ca.mcmaster.se2aa4.island.team01.Actions.Scan;
import ca.mcmaster.se2aa4.island.team01.Actions.Stop;
//import ca.mcmaster.se2aa4.island.teamXXX.CreekList;

public class SearchIsland {
    private final Logger logger = LogManager.getLogger();
    private ExtraInfo information;
    private Fly fly;
    private Heading heading;
    private Echo echo;
    private Scan scan;
    private Stop stop;
    private Direction currentDirection;
    private boolean echoedForward = false;
    private boolean scanningForCreek = false;
    private boolean keepTurning = false; 
    private boolean flyUp = false; 
    private boolean adjustPosition = false;

    int count = 0; 

    private enum State {
        MOVE_EAST, TURN_SOUTH_FROM_EAST,
        TURN_WEST, TURN_NORTH_FROM_WEST,
         TURN_SOUTH_FROM_WEST,TURN_NORTH_FROM_EAST;
    }

    private State state = State.MOVE_EAST;

    public SearchIsland() {
        this.fly = new Fly();
        this.heading = new Heading();
        this.echo = new Echo();
        this.scan = new Scan();
        this.stop = new Stop();
        this.currentDirection = Direction.EAST;
    }

    public void updateInfo(ExtraInfo info) {
        if (info == null) {
            logger.error("Received null ExtraInfo. Skipping update.");
            return;
        }
        this.information = info;
    }

    public String exploreIsland() {
        logger.info("** Searching the island **");
        
        if (scanningForCreek) return processScanResults();
        if (keepTurning) return handleStateTransition();

        if (!echoedForward) {
            echoedForward = true;
            return echo.echoStraight(currentDirection);
        }

        JSONObject extras = information.getExtras();


        if (extras.has("found") && extras.has("range")) {
            String found = extras.getString("found");
            int range = extras.getInt("range");

            if (!"GROUND".equals(found)) { // reached the end of the island 


                if (count >= 15){ // 15-20 for maps 03, 10, 20 , 10 for map 06
                    flyUp = true; // start searching in the north direction instead
                    adjustPosition = true;
                    count = 0; 

                }

                if (range > 9){
                    echoedForward = false;
                    return fly.flyOneUnit(); 
                }
                else{// if near the edge of map
                    count++; 
                    echoedForward = false;
                    return handleStateTransition();
                }
                
            }
        }

        echoedForward = false;

        // Scan every 3 steps
        scanningForCreek = true;
        return scan.scanArea();
    }

    // refactor later; probably
    private String handleStateTransition() {
        switch (state) {

            // currently facing east 
            case MOVE_EAST:

                if (flyUp){ 
                    state = State.TURN_NORTH_FROM_EAST;
                    currentDirection = currentDirection.turnLeft(); 
                    keepTurning = true; 
                    return heading.changeHeading(currentDirection); // turn north
                }
                
                state = State.TURN_SOUTH_FROM_EAST; 
                currentDirection = currentDirection.turnRight();
                keepTurning = true;
                return heading.changeHeading(currentDirection);// turn south 

            // currently facing north
            case TURN_NORTH_FROM_EAST: 
                if (adjustPosition) { // adjusts the drone's position so that it's skips a line
                    adjustPosition = false; 
                    return fly.flyOneUnit();
                }

                state = State.TURN_WEST; 
                currentDirection = currentDirection.turnLeft(); 
                keepTurning = false; 
                return heading.changeHeading(currentDirection); // turn west
            
            // currently facing south
            case TURN_SOUTH_FROM_EAST:
                state = State.TURN_WEST; 
                currentDirection = currentDirection.turnRight();
                keepTurning = false; 
                return heading.changeHeading(currentDirection);// turn west

            // facing west
            case TURN_WEST:
                if (flyUp){
                    state = State.TURN_NORTH_FROM_WEST; 
                    currentDirection = currentDirection.turnRight(); 
                    keepTurning = true; 
                    return heading.changeHeading(currentDirection); // facing north
                }

                state = State.TURN_SOUTH_FROM_WEST;
                currentDirection = currentDirection.turnLeft();
                keepTurning = true; 
                return heading.changeHeading(currentDirection);// facing south

            // facing north
            case TURN_NORTH_FROM_WEST:
                if (adjustPosition) {
                    adjustPosition = false; 
                    return fly.flyOneUnit();
                }
                state = State.MOVE_EAST; 
                currentDirection = currentDirection.turnRight();
                keepTurning = false;
                return heading.changeHeading(currentDirection); // facing east
            
            
            // facing south south 
            case TURN_SOUTH_FROM_WEST:
                state = State.MOVE_EAST;
                currentDirection = currentDirection.turnLeft();
                keepTurning = false; 
                return heading.changeHeading(currentDirection);// facing east

            default:
                logger.error("Invalid state. Stopping search.");
                return stop.returnToHeadquarters();
        }
    }


    private String processScanResults() {
        scanningForCreek = false;
        JSONObject extras = information.getExtras();
        logger.info(extras);
        CreekList creekList = CreekList.get();
        CurrentLocation current = CurrentLocation.get();
    

        if (!extras.has("creeks")) {
            logger.info("No creeks found.");
            return fly.flyOneUnit();
        }

        JSONArray creeks = extras.getJSONArray("creeks");
        JSONArray sites = extras.getJSONArray("sites");
        if (creeks.length() > 0) {
            creekList.addCreek(creeks.getString(0), current);
            logger.info("Creek found! ID: " + creeks.getString(0));
        }

        if (sites.length() > 0) {
            Site ESite = Site.get();
            ESite.setID(sites.getString(0));
            ESite.setLocation(current);
            logger.info("Emergency site found! ID: " + sites.getString(0));
            return stop.returnToHeadquarters();
        }

        return fly.flyOneUnit();
    }
}
