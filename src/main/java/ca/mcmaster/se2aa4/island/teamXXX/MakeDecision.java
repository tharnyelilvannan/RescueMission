package ca.mcmaster.se2aa4.island.teamXXX;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class MakeDecision {

    private final Logger logger = LogManager.getLogger();
    GroundDetector groundDetector; 
    Direction currDirection = Direction.EAST; 

    public MakeDecision(){
        groundDetector = new GroundDetector(currDirection);
    }


    public String makeDecision() {
        logger.info("** Taking a decision"); 

        if (groundDetector.mustDroneTurn() == true){
            groundDetector.changeHeading(); 
        }
        // if ground found, range > 0
        if (groundDetector.isGroundFound() == true && groundDetector.canDroneLand() == false){
            //return groundDetector.targetFly(); 
            return groundDetector.returnToHeadquarters();
        }

        // if ground found, range = 0
        else if (groundDetector.isGroundFound() == true && groundDetector.canDroneLand() == true){
            return groundDetector.returnToHeadquarters(); 
        }
        // out of range
        else if(groundDetector.isGroundFound() == false && groundDetector.canDroneLand() == false){
            return groundDetector.echoDifferentDirection(); 
        }

        else { // if ground found and range == 0
            return "{\"action\":\"stop\"}"; 
        }
    }


    public void sendResponse(JSONObject extraInfo){
        groundDetector.processResponse(extraInfo);
    }
    
}
