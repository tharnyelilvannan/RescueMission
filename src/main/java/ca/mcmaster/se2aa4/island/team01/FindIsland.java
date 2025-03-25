package ca.mcmaster.se2aa4.island.team01;

/**
 * The FindIsland class finds the island from the start posiiton.
 * It controls the drone's actions for locating the island. 
 * The class interacts with the GroundDetector to detect the presence of ground and make decisions based on that.
 */
public class FindIsland extends ExploreAbstract {
    private GroundDetector groundDetector;
    private Direction lastDirection;
    private boolean flyForward;
    private boolean initialSearch;
    private boolean flyPhase;
    private boolean landingPhase;

    public FindIsland() {
        super();
        groundDetector = new GroundDetector();
        flyForward = false;
        initialSearch = true;
        flyPhase = true;
        landingPhase = false;
    }

    /**
     * Updates the GroundDetector with response information provided in ExtraInfo.
     * @param info The ExtraInfo containing the latest drone status and other parameters.
     */
    @Override
    public void updateInfo(ExtraInfo info) {
        if (info != null) {
            groundDetector.updateInfo(info);
        } else {
            logger.error("Received null ExtraInfo. Skipping ground detector update.");
        }    
    }


    /**
     * Controls the exploration process. The drone starts in the fly phase, searching for the island.
     * It alternates between flying and scanning based on ground detection. 
     * If the island is found, it transitions to the landing phase.
     * @return A command string for the drone to execute (e.g., fly, echo, scan).
     */
    @Override
    public String explore() {
        logger.trace("** Making decision");
        CurrentLocationTracker currentLocation = CurrentLocationTracker.get();

        if (flyPhase) {
            if (initialSearch) {
                initialSearch = false;
                lastDirection = currentDirection;
                currentDirection = currentDirection.turnRight(); // Fly south to start

                return heading.changeHeading(currentDirection, lastDirection);
            }
            if (!groundDetector.isGroundFound() && !flyForward) { // Echo left
                flyForward = true;
                return echo.echoLeftWing(currentDirection);
            } else if (!groundDetector.isGroundFound() && flyForward) { // Fly forward
                flyForward = false;
                return fly.flyOneUnit(currentDirection);
            } else if (groundDetector.isGroundFound() && currentDirection == Direction.SOUTH) {
                lastDirection = currentDirection;
                currentDirection = currentDirection.turnLeft(); // Face east
                return heading.changeHeading(currentDirection, lastDirection);
            } else if (groundDetector.isGroundFound() && groundDetector.getRange() > 0 && flyForward) { // Fly forward
                flyForward = false;
                return fly.flyOneUnit(currentDirection);
            } else if (groundDetector.isGroundFound() && groundDetector.getRange() > 0 && !flyForward) { // Echo straight
                flyForward = true;
                return echo.echoStraight(currentDirection);
            } else { // Ground reached
                flyPhase = false;
                landingPhase = true;
                return scan.scanArea();
            }
        }
        
        return fly.flyOneUnit(currentDirection);
    }

    public boolean isLandingPhase() {
        return landingPhase;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }
}
