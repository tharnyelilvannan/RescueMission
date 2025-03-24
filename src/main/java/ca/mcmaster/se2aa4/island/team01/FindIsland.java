package ca.mcmaster.se2aa4.island.team01;

public class FindIsland extends ExploreInterface {
    private GroundDetector groundDetector;
    private Direction lastDirection;
    
    private boolean flyForward;
    private boolean initialSearch;
    private boolean flyPhase;
    private boolean landingPhase;
    private boolean scanPhase = true;
    int countX = 0;
    int countY = 0;

    public FindIsland() {
        super();
        groundDetector = new GroundDetector();
        flyForward = false;
        initialSearch = true;
        flyPhase = true;
        landingPhase = false;
    }

    @Override
    public void updateInfo(ExtraInfo info) {
        updateGroundDetector(info);
    }

    public void updateGroundDetector(ExtraInfo info) {
        if (info != null) {
            groundDetector.updateInfo(info);
        } else {
            logger.error("Received null ExtraInfo. Skipping ground detector update.");
        }
    }

    @Override
    public String explore() {
        logger.info("** Making decision");
        CurrentLocationTracker currentLocation = CurrentLocationTracker.get();

        if (scanPhase) {
            scanPhase = false;
            return scan.scanArea();
        }

        if (flyPhase) {
            scanPhase = true;
            if (initialSearch) {
                initialSearch = false;
                lastDirection = currentDirection;
                currentDirection = currentDirection.turnRight(); // Fly south to start
                logger.error("Original X: " + currentLocation.getXCoordinate());
                logger.error("Original Y: " + currentLocation.getYCoordinate());
                return heading.changeHeading(currentDirection, lastDirection);
            }
            if (!groundDetector.isGroundFound() && !flyForward) { // Echo left
                flyForward = true;
                return echo.echoLeftWing(currentDirection);
            } else if (!groundDetector.isGroundFound() && flyForward) { // Fly forward
                flyForward = false;
                logger.error("Original X: " + currentLocation.getXCoordinate());
                logger.error("Original Y: " + currentLocation.getYCoordinate());
                logger.error(currentDirection);
                logger.error("COUNTX " + countX);
                logger.error("COUNTY " + countY);
                countY++;
                return fly.flyOneUnit(currentDirection);
            } else if (groundDetector.isGroundFound() && currentDirection == Direction.SOUTH) {
                lastDirection = currentDirection;
                currentDirection = currentDirection.turnLeft(); // Face east
                return heading.changeHeading(currentDirection, lastDirection);
            } else if (groundDetector.isGroundFound() && groundDetector.getRange() > 0 && flyForward) { // Fly forward
                flyForward = false;
                logger.error("Original X: " + currentLocation.getXCoordinate());
                logger.error("Original Y: " + currentLocation.getYCoordinate());
                logger.error(currentDirection);
                logger.error("COUNTX " + countX);
                logger.error("COUNTY " + countY);
                countX++;
                return fly.flyOneUnit(currentDirection);
            } else if (groundDetector.isGroundFound() && groundDetector.getRange() > 0 && !flyForward) { // Echo straight
                flyForward = true;
                return echo.echoStraight(currentDirection);
            } else { // Ground reached
                flyPhase = false;
                landingPhase = true;
                logger.error(currentLocation.getXCoordinate());
                logger.error(currentLocation.getYCoordinate());
                logger.error("COUNTX " + countX);
                logger.error("COUNTY " + countY);
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