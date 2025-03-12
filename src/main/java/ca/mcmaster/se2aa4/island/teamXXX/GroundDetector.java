package ca.mcmaster.se2aa4.island.teamXXX;

import org.json.JSONObject;

import netscape.javascript.JSObject;

public class GroundDetector {

    private boolean groundFound;
    private boolean mustTurn; 
    private Direction currentDirection; // hardcoded

    public GroundDetector(Direction direction){
        groundFound = false;
        mustTurn = false; 
        currentDirection = direction; 
    }

    public String checkGround() {
        JSONObject request = new JSONObject();
        request.put("action", "echo"); // do echo action

        JSONObject parameters = new JSONObject();
        parameters.put("direction", currentDirection.getValue()); 

        request.put("parameters", parameters);
        System.out.println("Echo request: " + request.toString());

        return request.toString();
    }

    public String changeHeading(){
        mustTurn = false; 
        JSONObject request = new JSONObject();
        request.put("action", "heading"); // do heading action

        JSONObject parameters = new JSONObject();
        parameters.put("direction", currentDirection.getValue());

        request.put("parameters", parameters); 
        System.out.println("Heading request:" + request.toString()); 

        return request.toString();
    }


   
    public Direction processResponse(JSONObject extraInfo) {
        String found = extraInfo.optString("found", "UNKNOWN");

        if ("GROUND".equals(found)){
            System.out.println("Ground detected: " + currentDirection.getValue());
            groundFound = true;
            //return true; // ground found so stop checking 
        } 
        else { // If OUT_OF_RANGE
            System.out.println("No ground detected, area is out of range in direction: " + currentDirection);
            // turn right
            currentDirection = currentDirection.turnRight(); 
            mustTurn = true; 
            //return false; // if ground not found continue checking
        }
        return currentDirection;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public boolean isGroundFound() {
        return groundFound;
    }
    public boolean mustDroneTurn(){
        return mustTurn;
    }
}