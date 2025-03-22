package ca.mcmaster.se2aa4.island.team01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class MakeDecision {

    private final Logger logger = LogManager.getLogger();
    GroundDetector groundDetector;
    EmergencySiteDetector ESDetector;
    Direction currDirection = Direction.EAST;

    int count;
    private boolean flyForward;
    private boolean initialSearch; // for flying phase
    private boolean flyPhase;
    private boolean landingPhase;
    private boolean searchPhase;
    private boolean scan;
    private boolean firstSearchPhase = true;
    

    public MakeDecision() {
        groundDetector = new GroundDetector(currDirection);
        ESDetector = new EmergencySiteDetector(currDirection);
        count = 0;
        flyForward = false;
        initialSearch = true;
        flyPhase = true;
        landingPhase = false;
        searchPhase = false;
        scan = false;
    }

    public String makeDecision() {
        logger.info("** Making decision");

        // if (count < 30){
        // count ++;
        // return groundDetector.fly();
        // }
        // else if (count == 30){
        // count++;
        // groundDetector.turnRight();
        // return groundDetector.changeHeading();
        // }
        // else if (count == 31){
        // count++;
        // return groundDetector.checkGround();
        // }// 32
        // else if (count < 39){ //33, 34, 35, 36, 37, 38, 39
        // count++;
        // return groundDetector.fly();
        // }
        // else if (count == 39){
        // count++;
        // return groundDetector.checkGround();
        // }
        // else if (count == 40){
        // count++;
        // return groundDetector.scan();
        // }
        // else{
        // return groundDetector.returnToHeadquarters();
        // }

        
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

        
         // Fly phase: must fly to the island from starting position
        if (flyPhase == true) {

            if (initialSearch == true) {
                initialSearch = false;
                currDirection = currDirection.turnRight(); // flying south to start off
                return groundDetector.changeHeading(currDirection);
            }

            /* ******************************************************
             * to detect the island: the calls alternate between making an echo from the left wing and making flying forward one step
             * ***************************************************** */
            if (groundDetector.isGroundFound() == false && flyForward == false) { // make an echo
                flyForward = true;
                return groundDetector.echoLeftWing();
            }
             else if (groundDetector.isGroundFound() == false && flyForward == true) { // fly one step
                flyForward = false;
                return groundDetector.fly();
            }

            else if (groundDetector.isGroundFound() == true && currDirection == Direction.SOUTH) {
                currDirection = currDirection.turnLeft(); // face east
                return groundDetector.changeHeading(currDirection);
            }

            /* ******************************************************
             * Once ground is detected: the calls alternate between making an echo from the nose and making flying forward one step
             * ***************************************************** */

            else if (groundDetector.isGroundFound() == true && groundDetector.getRange() > 0 && flyForward == true) { // fly one step                                                                 
                flyForward = false;
                return groundDetector.fly();
            } 
            else if (groundDetector.isGroundFound() == true && groundDetector.getRange() > 0 && flyForward == false) { // echo  straight                                                                                    // straight
                flyForward = true;
                return groundDetector.echoStraight();
            }

            // Ground is reached
            else {
                flyPhase = false;
                searchPhase = true;
                // scan = true;
                currDirection = currDirection.turnLeft();
                currDirection = currDirection.turnLeft();
                currDirection = currDirection.turnLeft();
                return groundDetector.changeHeading(currDirection);
            }
        }

        if (searchPhase == true) {
            logger.info("In search phase");
            logger.info(currDirection);
        /*
            if (firstSearchPhase == true) {
                currDirection = currDirection.turnLeft();
                firstSearchPhase = false;
                return groundDetector.changeHeading(currDirection);
            }
        */
            if (ESDetector.isESFound()) {
                searchPhase = false;
                landingPhase = true;
                return ESDetector.returnToHeadquarters();
            }
            else if (flyForward == true) {
                flyForward = false;
                logger.info("Hello");
                return ESDetector.fly();
            }
            else {
                flyForward = true;
                scan = true;
                logger.info("HI");
                return ESDetector.scan();            
            }
        }

        if (landingPhase == true) {
            logger.info("In landing phase");

            return groundDetector.returnToHeadquarters();
        } 
        else {
            return groundDetector.returnToHeadquarters();
        }

    }

    public void sendResponse(JSONObject extraInfo) {
        if (scan == true) {
            scan = false;
            ESDetector.processScan(extraInfo);
        }
        groundDetector.processResponse(extraInfo);
    }

}
