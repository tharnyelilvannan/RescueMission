package ca.mcmaster.se2aa4.island.team01;

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

    // boolean echoLeft; 
    // boolean echoRight; 

    public GroundDetector(Direction direction){
        groundFound = false;
        canLand = false; 

        // echoLeft = false; 
        // echoRight = false; 
        currentDirection = direction; 
    }

    // public String checkGround() {
    //     JSONObject request = new JSONObject();
    //     request.put("action", "echo"); // do echo action

    //     JSONObject parameters = new JSONObject();
    //     parameters.put("direction", currentDirection.getValue()); 

    //     request.put("parameters", parameters);
    //     logger.info("Echo request: " + request.toString());

    //     return request.toString();
    // }
    
    public String echoStraight() {
        JSONObject request = new JSONObject();
        request.put("action", "echo"); // do echo action

        JSONObject parameters = new JSONObject();
        parameters.put("direction", currentDirection.getValue()); 

        request.put("parameters", parameters);
        logger.info("Echo request: " + request.toString());

        return request.toString();
    }

    public String echoLeftWing() {
        JSONObject request = new JSONObject();
        request.put("action", "echo"); // do echo action

        JSONObject parameters = new JSONObject();
        parameters.put("direction", currentDirection.turnLeft().getValue()); 

        request.put("parameters", parameters);
        logger.info("Echo request: " + request.toString());

        return request.toString();
    }

    public String echoRightWing() {
        JSONObject request = new JSONObject();
        request.put("action", "echo"); // do echo action

        JSONObject parameters = new JSONObject();
        parameters.put("direction", currentDirection.turnRight().getValue()); 

        request.put("parameters", parameters);
        logger.info("Echo request: " + request.toString());

        return request.toString();
    }

    // change heading given a direction
    public String changeHeading(Direction direction){
        currentDirection = direction; 

        JSONObject request = new JSONObject();
        request.put("action", "heading"); // do heading action

        JSONObject parameters = new JSONObject();
        parameters.put("direction", currentDirection.getValue());

        request.put("parameters", parameters); 
        logger.info("Heading request:" + request.toString()); 

        return request.toString();
    }

    // fly
    public String fly(){
        JSONObject request = new JSONObject();
        request.put("action", "fly"); // do fly action

        logger.info("Fly request:" + request.toString()); 
        return request.toString();
    }

    // scan
    public String scan(){
        JSONObject request = new JSONObject();
        request.put("action", "scan"); // do scan action

        logger.info("Scan request:" + request.toString()); 
        return request.toString();
    }
    // stop 
    public String returnToHeadquarters(){
        JSONObject request = new JSONObject();
        request.put("action", "stop");
        logger.info("Stop request: " + request.toString());
        return request.toString();

    }
   
    public void processResponse(JSONObject extraInfo) {
        String found = extraInfo.optString("found", "UNKNOWN");

        if ("GROUND".equals(found)){
            groundFound = true; // found ground

            if (extraInfo.has("range")){
                range = extraInfo.getInt("range");  
                logger.info("the range is " + range);
            }

        } 
        else if ("OUT_OF_RANGE".equals(found)) { // If OUT_OF_RANGE
            logger.info("Ground out of range"); 
            
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
    public boolean canDroneLand() {
        return canLand;
    }
}