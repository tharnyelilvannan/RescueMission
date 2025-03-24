package ca.mcmaster.se2aa4.island.team01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team01.Actions.Echo;
import ca.mcmaster.se2aa4.island.team01.Actions.Fly;
import ca.mcmaster.se2aa4.island.team01.Actions.Heading;
import ca.mcmaster.se2aa4.island.team01.Actions.Scan;
import ca.mcmaster.se2aa4.island.team01.Actions.Stop;

public class IslandLength {
    private final Logger logger = LogManager.getLogger();
    private Echo echo;
    private Fly fly;
    private Heading heading;
    private Scan scan;
    private Stop stop;
    private ExtraInfo information;
    private Direction currentDirection;
    private boolean leftIsGround = false;
    private boolean rightIsGround = false;
    private FindIsland findIsland;
    private Direction prevDirection;
    private String rightType;
    private String leftType;
    private String forwardType;
    private boolean hasFoundLength = false;
    private int totalLength;
    private CurrentLocation currentLocation = CurrentLocation.get();

    private enum State {
        ECHO_FORWARD, STOP, TURN_SOUTH, PROCESS_RIGHT_ECHO, PROCESS_LEFT_ECHO, PROCESS_FORWARD_ECHO
    }

    private State state = State.TURN_SOUTH;

    public IslandLength() {
        this.echo = new Echo();
        this.fly = new Fly();
        this.heading = new Heading();
        this.scan = new Scan();
        this.stop = new Stop();
        this.findIsland = new FindIsland();
        this.currentDirection = findIsland.getCurrentDirection();
    }

    public void updateInfo(ExtraInfo info) {
        if (info == null) {
            logger.error("Received null ExtraInfo. Skipping update.");
            return;
        }
        this.information = info;    
    }

    public String measureIsland() {
        switch (state) {
            case TURN_SOUTH:
                state = State.ECHO_FORWARD;
                prevDirection = currentDirection;
                currentDirection = currentDirection.turnRight();
                logger.info("Current Direction: {} and New Direction: {}", prevDirection, currentDirection);
                return heading.changeHeading(currentDirection, prevDirection); // turn south (initially east)
                
            case ECHO_FORWARD:
                state = State.PROCESS_FORWARD_ECHO;
                return echo.echoStraight(currentDirection); // check front for ground

            case PROCESS_FORWARD_ECHO:
                JSONObject extras = information.getExtras();
                if (extras.has("found") && extras.has("range")) {
                    forwardType = extras.getString("found");

                    if (!"GROUND".equals(forwardType)) {
                        state = State.PROCESS_RIGHT_ECHO;
                        return echo.echoRightWing(currentDirection);    // echo right if no ground
                    } else {
                        state = State.ECHO_FORWARD;
                        return fly.flyOneUnit(currentDirection);    // fly forward if ground
                    }
                }

            case PROCESS_RIGHT_ECHO:
                extras = information.getExtras();
                if (extras.has("found") && extras.has("range")) {
                    rightType = extras.getString("found");
                    rightIsGround = "GROUND".equals(rightType);
                }
                state = State.PROCESS_LEFT_ECHO;
                return echo.echoLeftWing(currentDirection);

            case PROCESS_LEFT_ECHO: 
                extras = information.getExtras();
                if (extras.has("found") && extras.has("range")) {
                    leftType = extras.getString("found");
                    leftIsGround = "GROUND".equals(leftType);
                }
        
                if (leftIsGround || rightIsGround) {
                    state = State.ECHO_FORWARD;
                    logger.info("IS THIS EVEN BEING PRINTED OUTSDFS FSFSFSF");
                    return fly.flyOneUnit(currentDirection);
                } else {
                    prevDirection = currentDirection;
                    currentDirection = currentDirection.turnLeft();
                    hasFoundLength = true;
                    totalLength = currentLocation.getXCoordinate();
                    logger.info("Total length is {}", totalLength);
                    return heading.changeHeading(currentDirection, prevDirection);
                }

            default:
                logger.error("Invalid state");
                return fly.flyOneUnit(currentDirection);
        }
    }

    public int getIslandLength() {
        return totalLength;
    }
        
    public boolean hasFoundLength() {
        return hasFoundLength;
    }
}

