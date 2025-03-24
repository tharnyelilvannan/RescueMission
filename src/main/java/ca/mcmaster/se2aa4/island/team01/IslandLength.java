package ca.mcmaster.se2aa4.island.team01;

import org.json.JSONObject;

public class IslandLength extends ExploreInterface {
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
    private CurrentLocationTracker currentLocation = CurrentLocationTracker.get();
    private int initialLocation = currentLocation.getYCoordinate();

    private enum State {
        ECHO_FORWARD, TURN_SOUTH, PROCESS_RIGHT_ECHO, PROCESS_LEFT_ECHO, PROCESS_FORWARD_ECHO, TURN_NORTH, RETURN
    }

    private State state = State.TURN_SOUTH;

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

    @Override
    public String explore() {
        switch (state) {
            case TURN_SOUTH:
                state = State.ECHO_FORWARD;
                prevDirection = currentDirection;
                currentDirection = currentDirection.turnRight();
                setInitialDirection = true;
                logger.info("Current Direction: {} and New Direction: {}", prevDirection, currentDirection);
                return heading.changeHeading(currentDirection, prevDirection);

            case ECHO_FORWARD:
                if (setInitialDirection) {
                    setInitialDirection = false;
                    totalLength++;
                    return fly.flyOneUnit(currentDirection);
                } else {
                    state = State.PROCESS_FORWARD_ECHO;
                    return echo.echoStraight(currentDirection);
                }

            case PROCESS_FORWARD_ECHO:
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
                    totalLength++;
                    return fly.flyOneUnit(currentDirection);
                } else {
                    reachedEnd = true;
                    state = State.TURN_NORTH;
                    prevDirection = currentDirection;
                    currentDirection = currentDirection.turnRight();
                    logger.error("Total length: {}", totalLength);
                    logger.error(currentLocation.getXCoordinate());
                    logger.error(currentLocation.getYCoordinate());
                    logger.info("Current Direction: {} and New Direction: {}", prevDirection, currentDirection);
                    return heading.changeHeading(currentDirection, prevDirection);
                }

            case TURN_NORTH:
                state = State.RETURN;
                prevDirection = currentDirection;
                currentDirection = currentDirection.turnRight();
                logger.info("Current Direction: {} and New Direction: {}", prevDirection, currentDirection);
                return heading.changeHeading(currentDirection, prevDirection);

            case RETURN:
                if (currentPos != totalLength) {
                    currentPos++;
                    return fly.flyOneUnit(currentDirection);
                } else {
                    hasFoundLength = true;
                    prevDirection = currentDirection;
                    currentDirection = currentDirection.turnRight();
                    logger.info("Returning. Direction: {} to {}", prevDirection, currentDirection);
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
