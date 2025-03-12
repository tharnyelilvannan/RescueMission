package ca.mcmaster.se2aa4.island.teamXXX;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class MakeDecision {

    private final Logger logger = LogManager.getLogger();
    GroundDetector groundDetector; 
    Direction currDirection = Direction.EAST;
    
    int count; 

    public MakeDecision(){
        groundDetector = new GroundDetector(currDirection);
        count = 0;
    }


    public String makeDecision() {
        logger.info("** Making decision"); 

        // if (groundDetector.mustDroneTurn() == true){
        //     groundDetector.changeHeading(); 
        // }
        // // if ground found, range > 0
        // if (groundDetector.isGroundFound() == true && groundDetector.canDroneLand() == false){
        //     //return groundDetector.targetFly(); 
        //     return groundDetector.returnToHeadquarters();
        // }

        // // if ground found, range = 0
        // else if (groundDetector.isGroundFound() == true && groundDetector.canDroneLand() == true){
        //     return groundDetector.returnToHeadquarters(); 
        // }
        // // out of range
        // else if(groundDetector.isGroundFound() == false && groundDetector.canDroneLand() == false){
        //     return groundDetector.echoDifferentDirection(); 
        // }

        // else { // if ground found and range == 0
        //     return "{\"action\":\"stop\"}"; 
        // }


        
        if (count < 30){
            count ++; 
            return groundDetector.fly(); 
        }
        else if (count == 30){
            count++; 
            groundDetector.turnRight(); 
            return groundDetector.changeHeading();
        }
        else if (count == 31){
            count++; 
            return groundDetector.checkGround(); 
        }// 32
        else if (count < 39){ //33, 34, 35, 36, 37, 38, 39
            count++; 
            return groundDetector.fly(); 
        }
        else if (count == 39){
            count++; 
            return groundDetector.checkGround();
        }
        else if (count == 40){
            count++; 
             return groundDetector.scan(); 
        }
        else{
            return groundDetector.returnToHeadquarters(); 
        }
    }


    public void sendResponse(JSONObject extraInfo){
        groundDetector.processResponse(extraInfo);
    }
    
}
