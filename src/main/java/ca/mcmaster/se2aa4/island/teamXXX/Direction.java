package ca.mcmaster.se2aa4.island.teamXXX;

public enum Direction {
    NORTH("N"), SOUTH("S"), EAST("E"), WEST("W");

    private final String val;
    private Direction left; 
    private Direction right; 

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
        return val;  // ✅ Return short-form (N, E, S, W) instead of full name
    }
}