package ca.mcmaster.se2aa4.island.team01;

import org.json.JSONObject;

/**
 * The IslandLength class is responsible for measuring the length of the island using a state machine.
 * The length is determined by how many fly forward actions are made during the exploration.
 */

public class IslandLength extends ExploreAbstract {
    private ExtraInfo information;
    private FindIsland findIsland;
    private Direction prevDirection;
    private String rightType;
    private String leftType;
    private String forwardType;
    private boolean leftIsGround = false;
    private boolean rightIsGround = false;
    private boolean hasFoundLength = false;
    private boolean setInitialDirection = false;
    private boolean reachedEnd = false;
    private int totalLength = 0;
    private int currentPos = 0;
    private CurrentLocation currentLocation = CurrentLocation.get();
    private int initialLocation = currentLocation.getYCoordinate();

    private enum State {
        ECHO_FORWARD, TURN_SOUTH, PROCESS_RIGHT_ECHO, PROCESS_LEFT_ECHO, PROCESS_FORWARD_ECHO, TURN_NORTH, RETURN
    }

    private State state = State.TURN_SOUTH;     // Initial state

    public IslandLength() {
        super();
        this.findIsland = new FindIsland();
        this.currentDirection = findIsland.getCurrentDirection();
    }

    @Override
    public void updateInfo(ExtraInfo info) {
        if (info == null) {
            logger.error("Received null ExtraInfo. Skipping update.");
            return;
        }
        this.information = info;
    }

    /**
     * The main exploration method that moves the drone along the island length and processes
     * ground detection data to determine the island's boundary length.
     * @return The next action for the drone to execute based on the current state.
     */
    @Override
    public String explore() {
        switch (state) {
            case TURN_SOUTH:
                // Turn the drone south to start the exploration
                state = State.ECHO_FORWARD;
                prevDirection = currentDirection;
                currentDirection = currentDirection.turnRight();
                setInitialDirection = true;
                logger.trace("Current Direction: {} and New Direction: {}", prevDirection, currentDirection);
                return heading.changeHeading(currentDirection, prevDirection);

            case ECHO_FORWARD:
                // Move forward and start the exploration by echoing the surroundings
                if (setInitialDirection) {
                    setInitialDirection = false;
                    totalLength++;
                    return fly.flyOneUnit(currentDirection);
                } else {
                    state = State.PROCESS_FORWARD_ECHO;
                    return echo.echoStraight(currentDirection);
                }

            case PROCESS_FORWARD_ECHO:
                // Process the echo result after moving forward
                JSONObject extras = information.getExtras();
                if (extras.has("found") && extras.has("range")) {
                    forwardType = extras.getString("found");
                    if (!"GROUND".equals(forwardType)) {
                        state = State.PROCESS_RIGHT_ECHO;
                        return echo.echoRightWing(currentDirection);
                    } else {
                        state = State.ECHO_FORWARD;
                        totalLength++;
                        return fly.flyOneUnit(currentDirection);
                    }
                }
                break;

            case PROCESS_RIGHT_ECHO:
                // Process the echo from the right wing to detect the ground
                extras = information.getExtras();
                if (extras.has("found") && extras.has("range")) {
                    rightType = extras.getString("found");
                    rightIsGround = "GROUND".equals(rightType);
                }
                state = State.PROCESS_LEFT_ECHO;
                return echo.echoLeftWing(currentDirection);

            case PROCESS_LEFT_ECHO:
                // Process the echo from the left wing to detect the ground
                extras = information.getExtras();
                if (extras.has("found") && extras.has("range")) {
                    leftType = extras.getString("found");
                    leftIsGround = "GROUND".equals(leftType);
                }

                // If ground is detected on either side, move forward
                if (leftIsGround || rightIsGround) {
                    state = State.ECHO_FORWARD;
                    totalLength++;
                    return fly.flyOneUnit(currentDirection);
                } else {
                    // If no ground is detected the boundary is reached
                    reachedEnd = true;
                    state = State.TURN_NORTH;
                    prevDirection = currentDirection;
                    currentDirection = currentDirection.turnRight();
                    logger.trace("Total length: {}", totalLength);
                    logger.trace("Current Direction: {} and New Direction: {}", prevDirection, currentDirection);
                    return heading.changeHeading(currentDirection, prevDirection);
                }

            case TURN_NORTH:
                // Turn the drone north to start returning
                state = State.RETURN;
                prevDirection = currentDirection;
                currentDirection = currentDirection.turnRight();
                logger.trace("Current Direction: {} and New Direction: {}", prevDirection, currentDirection);
                return heading.changeHeading(currentDirection, prevDirection);

            case RETURN:
                // Return to the starting point by retracing the steps
                if (currentPos != totalLength) {
                    currentPos++;
                    return fly.flyOneUnit(currentDirection);
                } else {
                    // Once back at the starting point, face east to start island search
                    hasFoundLength = true;
                    prevDirection = currentDirection;
                    currentDirection = currentDirection.turnRight();
                    logger.trace("Returning. Direction: {} to {}", prevDirection, currentDirection);
                    return heading.changeHeading(currentDirection, prevDirection);
                }

            default:
                logger.error("Invalid state encountered.");
                totalLength++;
                return fly.flyOneUnit(currentDirection);
        }
        return fly.flyOneUnit(currentDirection);
    }

    public int getIslandLength() {
        return totalLength;
    }

    public boolean hasFoundLength() {
        return hasFoundLength;
    }
}
