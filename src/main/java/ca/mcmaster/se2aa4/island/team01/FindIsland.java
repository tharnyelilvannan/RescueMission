package ca.mcmaster.se2aa4.island.team01;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.mcmaster.se2aa4.island.team01.Actions.Echo;
import ca.mcmaster.se2aa4.island.team01.Actions.Fly;
import ca.mcmaster.se2aa4.island.team01.Actions.Heading;
import ca.mcmaster.se2aa4.island.team01.Actions.Scan;
import ca.mcmaster.se2aa4.island.team01.Actions.Stop;

/* *****************************************************************************
         * Strategy
         * 1. Face south
         * 2. Make an echo to check if the ground is found.
         * 3. If ground is out of range, then fly down one step
         * 3. Keep repeating step 2-3 until ground is found
         * 4. Once ground is found, face east again
         * 5. Make an echo to check how close the drone is to ground (range)
         * 6. Fly forward one step
         * 7. Keep repeating step 5-6 until drone reaches ground (range == 0)
         * 8. Once land is reached, drone makes a scan
         * **************************************************************************** */

public class FindIsland {
    private final Logger logger = LogManager.getLogger();
    private GroundDetector groundDetector;
    private Echo echo;
    private Heading heading;
    private Fly fly;
    private Scan scan;
    private Stop stop;
    private Direction currentDirection = Direction.EAST;
    
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
                currentDirection = currentDirection.turnRight(); // flying south to start off
                return heading.changeHeading(currentDirection);
            }

            // To detect the island: the calls alternate between making an echo from the left wing and making flying forward one step
            if (groundDetector.isGroundFound() == false && flyForward == false) {       // make an echo
                flyForward = true;
                return echo.echoLeftWing(currentDirection);
            } else if (groundDetector.isGroundFound() == false && flyForward == true) { // fly one step
                flyForward = false;
                return fly.flyOneUnit();
            } else if (groundDetector.isGroundFound() == true && currentDirection == Direction.SOUTH) {
                currentDirection = currentDirection.turnLeft(); // face east
                return heading.changeHeading(currentDirection);
            }

            // Once ground is detected: the calls alternate between making an echo from the nose and making flying forward one step
            else if (groundDetector.isGroundFound() == true && groundDetector.getRange() > 0 && flyForward == true) { // fly one step                                                                 
                flyForward = false;
                return fly.flyOneUnit();
            } else if (groundDetector.isGroundFound() == true && groundDetector.getRange() > 0 && flyForward == false) { // echo  straight                                                                                    // straight
                flyForward = true;
                return echo.echoStraight(currentDirection);
            } else { // Ground is reached
                flyPhase = false;
                landingPhase = true;
                return scan.scanArea();
            }
        }
        return fly.flyOneUnit();
    }

    public boolean isLandingPhase() {
        return landingPhase;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }
}
