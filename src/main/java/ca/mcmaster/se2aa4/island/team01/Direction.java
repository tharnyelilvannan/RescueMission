package ca.mcmaster.se2aa4.island.team01;

/**
 * Enum representing the four cardinal directions (NORTH, SOUTH, EAST, WEST).
 * Provides methods to turn left, turn right, and retrieve the direction's string value.
 */
public enum Direction {
    NORTH("N"), SOUTH("S"), EAST("E"), WEST("W");

    private final String val;
    private Direction left; 
    private Direction right;

    // Static block to define left and right turns for each direction
    static {
        NORTH.left = WEST;
        NORTH.right = EAST;
        SOUTH.left = EAST;
        SOUTH.right = WEST;
        EAST.left = NORTH;
        EAST.right = SOUTH;
        WEST.left = SOUTH;
        WEST.right = NORTH;
    }

    Direction(String value) {
        this.val = value;
    }

    public Direction turnLeft(){
        return left; 
    }

    public Direction turnRight(){
        return right;
    }

    public String getValue() {
        return val;  
    }
}
