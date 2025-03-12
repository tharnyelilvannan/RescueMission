package ca.mcmaster.se2aa4.island.teamXXX;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import netscape.javascript.JSObject;

public class GroundDetector {
    private final Logger logger = LogManager.getLogger();

    private boolean groundFound;
    private boolean mustTurn; 
    private boolean canLand; 
    private Direction currentDirection; // hardcoded
    private int range;




    boolean echoLeft; 
    boolean echoRight; 

    public GroundDetector(Direction direction){
        groundFound = false;
        mustTurn = false; 
        canLand = false; 

        echoLeft = false; 
        echoRight = false; 

        currentDirection = direction; 
    }

    public String checkGround() {
        JSONObject request = new JSONObject();
        request.put("action", "echo"); // do echo action

        JSONObject parameters = new JSONObject();
        parameters.put("direction", currentDirection.getValue()); 

        request.put("parameters", parameters);
        logger.info("Echo request: " + request.toString());

        return request.toString();
    }

    public String changeHeading(){
        logger.debug("Setting must turn to false"); 
        mustTurn = false; 

        JSONObject request = new JSONObject();
        request.put("action", "heading"); // do heading action

        JSONObject parameters = new JSONObject();
        parameters.put("direction", currentDirection.getValue());

        request.put("parameters", parameters); 
        logger.info("Heading request:" + request.toString()); 

        return request.toString();
    }


    public String fly(){
        JSONObject request = new JSONObject();
        request.put("action", "fly"); // do fly action

        logger.info("Fly request:" + request.toString()); 
        return request.toString();
    }
    public String scan(){
        JSONObject request = new JSONObject();
        request.put("action", "scan"); // do scan action

        logger.info("Scan request:" + request.toString()); 
        return request.toString();
    }




    public String echoDifferentDirection(){
        JSONObject request = new JSONObject();
        request.put("action", "echo"); // do echo action


        // if both directions have been checked already
        if (echoLeft == true && echoRight == true && mustTurn == false){
            logger.debug("Flying one step"); 
            mustTurn = true;
            // fly forward one step
            return fly();
        }
        else if (echoLeft == true && echoRight == false){ // to echo left
            echoLeft = false; 
            echoRight = true;
            // echo left
            JSONObject parameters = new JSONObject();
            parameters.put("direction", currentDirection.turnLeft().getValue()); 
            request.put("parameters", parameters);
            logger.info("Echo request: " + request.toString());
            return request.toString();
        }
        else if (echoLeft == false && echoRight == true){ // to echo right
            echoLeft = true; 
            // echo right
            JSONObject parameters = new JSONObject();
            parameters.put("direction", currentDirection.turnRight().getValue());
            request.put("parameters", parameters);
            logger.info("Echo request: " + request.toString());
            return request.toString(); 
        }
        else{ 
            // if nothing has been checked
            echoLeft = true; 
            JSONObject parameters = new JSONObject();
            parameters.put("direction", currentDirection.getValue());
            request.put("parameters", parameters);
            logger.info("Echo request: " + request.toString());
            return request.toString(); 

        }
    }

    public String returnToHeadquarters(){
        JSONObject request = new JSONObject();
        request.put("action", "stop");
        logger.info("Stop request: " + request.toString());
        return request.toString();

    }


    public String targetFly(){
        return null; 
    }

   
    public void processResponse(JSONObject extraInfo) {
        String found = extraInfo.optString("found", "UNKNOWN");
        range = extraInfo.optInt("range", -1);  

        if ("GROUND".equals(found)){
            groundFound = true; // found ground

            if (range == 0){
                canLand = true;// can stop
                logger.info("Ground underneath. Landing: ");
            }
            else{
                // must fly to land
                canLand = false; 
                logger.info("Must fly to land in " + range);  
            }

            //return true; // ground found so stop checking 
        } 
        else if ("OUT_OF_RANGE".equals(found)) { // If OUT_OF_RANGE and need to change diretions
            System.out.println("No ground detected, area is out of range in direction: " + currentDirection);

            // must change directions
            if (mustTurn == true){
                logger.debug("must change current Direction turning right");
                currentDirection = currentDirection.turnRight(); 
                logger.debug("setting must turn to true");
                groundFound = false; 
                canLand = false;


                echoLeft = false; 
                echoRight = false;

            }
        }
    }

    public void turnRight(){
        currentDirection = currentDirection.turnRight(); 
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }
    public int getRange(){
        return range; 
    }

    public boolean isGroundFound() {
        return groundFound;
    }
    public boolean mustDroneTurn(){
        return mustTurn;
    }
    public boolean canDroneLand() {
        return canLand;
    }
}