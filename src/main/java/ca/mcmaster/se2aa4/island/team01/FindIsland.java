package ca.mcmaster.se2aa4.island.team01;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.mcmaster.se2aa4.island.team01.Actions.Echo;
import ca.mcmaster.se2aa4.island.team01.Actions.Fly;
import ca.mcmaster.se2aa4.island.team01.Actions.Heading;
import ca.mcmaster.se2aa4.island.team01.Actions.Scan;
import ca.mcmaster.se2aa4.island.team01.Actions.Stop;

public class FindIsland {
    private final Logger logger = LogManager.getLogger();
    private GroundDetector groundDetector;
    private Echo echo;
    private Heading heading;
    private Fly fly;
    private Scan scan;
    private Stop stop;
    private Direction currentDirection = Direction.EAST;
    private Direction lastDirection;
    
    private boolean flyForward;
    private boolean initialSearch;
    private boolean flyPhase;
    private boolean landingPhase;

    public FindIsland() {
        groundDetector = new GroundDetector();
        echo = new Echo();
        heading = new Heading();
        fly = new Fly();
        scan = new Scan();
        stop = new Stop();
        flyForward = false;
        initialSearch = true;
        flyPhase = true;
        landingPhase = false;
    }

    public void updateGroundDetector(ExtraInfo info) {
        if (info != null) {
            groundDetector.updateInfo(info);
        }
    }

    public String searchForGround() {
        logger.info("** Making decision");

         // Fly phase: must fly to the island from starting position
        if (flyPhase == true) {
            if (initialSearch == true) {
                initialSearch = false;
                lastDirection = currentDirection;
                currentDirection = currentDirection.turnRight(); // flying south to start off
                return heading.changeHeading(currentDirection, lastDirection);
            }

            // To detect the island: the calls alternate between making an echo from the left wing and making flying forward one step
            if (groundDetector.isGroundFound() == false && flyForward == false) {       // make an echo
                flyForward = true;
                return echo.echoLeftWing(currentDirection);
            } else if (groundDetector.isGroundFound() == false && flyForward == true) { // fly one step
                flyForward = false;
                return fly.flyOneUnit(currentDirection);
            } else if (groundDetector.isGroundFound() == true && currentDirection == Direction.SOUTH) {
                lastDirection = currentDirection;
                currentDirection = currentDirection.turnLeft(); // face east
                return heading.changeHeading(currentDirection, lastDirection);
            }

            // Once ground is detected: the calls alternate between making an echo from the nose and making flying forward one step
            else if (groundDetector.isGroundFound() == true && groundDetector.getRange() > 0 && flyForward == true) { // fly one step                                                                 
                flyForward = false;
                return fly.flyOneUnit(currentDirection);
            } else if (groundDetector.isGroundFound() == true && groundDetector.getRange() > 0 && flyForward == false) { // echo  straight                                                                                    // straight
                flyForward = true;
                return echo.echoStraight(currentDirection);
            } else { // Ground is reached
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
