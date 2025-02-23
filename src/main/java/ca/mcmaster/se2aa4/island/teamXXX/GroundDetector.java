package ca.mcmaster.se2aa4.island.teamXXX;

import org.json.JSONObject;

public class GroundDetector {

    private final Direction[] directions = Direction.values();
    private int directionIndex = 0;
    private boolean groundFound = false;

    public String checkGround() {
        JSONObject request = new JSONObject();
        request.put("action", "echo"); // do echo action

        JSONObject parameters = new JSONObject();
        parameters.put("direction", directions[directionIndex].getValue()); // test each direction (N, E, S not W because heading not changed)

        request.put("parameters", parameters);
        System.out.println("Echo request: " + request.toString());

        return request.toString();
    }

    public boolean processResponse(JSONObject extraInfo) {
        String found = extraInfo.optString("found", "UNKNOWN");

        if ("GROUND".equals(found)) {
            System.out.println("Ground detected: " + directions[directionIndex].getValue());
            groundFound = true;
            return true; // ground found so stop checking
        } else {
            System.out.println("No ground detected, area is out of range in direction: " + directions[directionIndex].getValue());
            directionIndex = (directionIndex + 1) % directions.length; // move to next direction
            return false; // if ground not found continue checking
        }
    }

    public Direction getCurrentDirection() {
        return directions[directionIndex];
    }

    public boolean isGroundFound() {
        return groundFound;
    }
}