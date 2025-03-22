package ca.mcmaster.se2aa4.island.team01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team01.Actions.Echo;
import ca.mcmaster.se2aa4.island.team01.Actions.Fly;
import ca.mcmaster.se2aa4.island.team01.Actions.Heading;
import ca.mcmaster.se2aa4.island.team01.Actions.Stop;

public class SearchIsland {
    private final Logger logger = LogManager.getLogger();
    private ExtraInfo information;
    private final Fly fly;
    private final Heading heading;
    private final Echo echo;
    private final Stop stop;
    private final CreekFinder creekFinder;
    private Direction currentDirection;
    private boolean echoedForward = false;

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
        this.stop = new Stop();
        this.creekFinder = new CreekFinder();
        this.currentDirection = Direction.EAST;
    }

    public void updateInfo(ExtraInfo info) {
        if (info == null) {
            logger.error("Received null ExtraInfo. Skipping update.");
            return;
        }
        this.information = info;
        creekFinder.updateInfo(info);
    }

    public String exploreIsland() {
        logger.info("** Searching the island **");

        if (creekFinder.isScanning()) return creekFinder.findCreeks();

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

        return creekFinder.startScanning();
    }

    private String handleStateTransition() {
        switch (state) {
            case MOVE_EAST:
                state = State.TURN_SOUTH_FROM_EAST;
                currentDirection = currentDirection.turnRight();
                return heading.changeHeading(currentDirection);

            case TURN_SOUTH_FROM_EAST:
                state = State.MOVE_SOUTH_FROM_EAST;
                return fly.flyOneUnit();

            case MOVE_SOUTH_FROM_EAST:
                state = State.TURN_WEST;
                currentDirection = currentDirection.turnRight();
                return heading.changeHeading(currentDirection);

            case TURN_WEST:
                state = State.TURN_SOUTH_FROM_WEST;
                currentDirection = currentDirection.turnRight();
                return heading.changeHeading(currentDirection);

            case TURN_SOUTH_FROM_WEST:
                state = State.MOVE_SOUTH_FROM_WEST;
                return fly.flyOneUnit();

            case MOVE_SOUTH_FROM_WEST:
                state = State.TURN_SOUTH_FROM_EAST;
                currentDirection = currentDirection.turnRight();
                return heading.changeHeading(currentDirection);

            default:
                logger.error("Invalid state. Stopping search.");
                return stop.returnToHeadquarters();
        }
    }
}
