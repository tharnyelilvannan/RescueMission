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
import ca.mcmaster.se2aa4.island.team01.CreekList;

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
    private Direction lastDirection;

    private enum State {
        MOVE_EAST, TURN_SOUTH_FROM_EAST, MOVE_SOUTH_FROM_EAST,
        TURN_WEST, MOVE_WEST, TURN_SOUTH_FROM_WEST, MOVE_SOUTH_FROM_WEST,
        TURN_EAST
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

        if (!echoedForward) {
            echoedForward = true;
            return echo.echoStraight(currentDirection);
        }

        JSONObject extras = information.getExtras();
        if (extras.has("found") && extras.has("range")) {
            String found = extras.getString("found");

            if (!"GROUND".equals(found)) {
                echoedForward = false;
                return handleStateTransition();
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
            case MOVE_EAST:
                state = State.TURN_SOUTH_FROM_EAST;
                lastDirection = currentDirection;
                currentDirection = currentDirection.turnRight();
                return heading.changeHeading(currentDirection, lastDirection);

            case TURN_SOUTH_FROM_EAST:
                state = State.MOVE_SOUTH_FROM_EAST;
                return fly.flyOneUnit(currentDirection);

            case MOVE_SOUTH_FROM_EAST:
                state = State.TURN_WEST;
                currentDirection = currentDirection.turnRight();
                return heading.changeHeading(currentDirection, lastDirection);

            case TURN_WEST:
                state = State.TURN_SOUTH_FROM_WEST;
                lastDirection = currentDirection;
                currentDirection = currentDirection.turnRight();
                return heading.changeHeading(currentDirection, lastDirection);

            case TURN_SOUTH_FROM_WEST:
                state = State.MOVE_SOUTH_FROM_WEST;
                return fly.flyOneUnit(currentDirection);

            case MOVE_SOUTH_FROM_WEST:
                state = State.TURN_SOUTH_FROM_EAST;
                lastDirection = currentDirection;
                currentDirection = currentDirection.turnRight();
                return heading.changeHeading(currentDirection, lastDirection);

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
            return fly.flyOneUnit(currentDirection);
        }

        JSONArray creeks = extras.getJSONArray("creeks");
        JSONArray sites = extras.getJSONArray("sites");
        if (creeks.length() > 0) {
            creekList.addCreek(creeks.getString(0), current.getXCoordinate(), current.getYCoordinate());
            logger.info("Creek found! ID: " + creeks.getString(0));
        }

        if (sites.length() > 0) {
            Site ESite = Site.get();
            ESite.setID(sites.getString(0));
            ESite.setLocation(current.getXCoordinate(), current.getYCoordinate());
            logger.info("Emergency site found! ID: " + sites.getString(0));
            return stop.returnToHeadquarters();
        }

        return fly.flyOneUnit(currentDirection);
    }
}
