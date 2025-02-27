package ca.mcmaster.se2aa4.island.teamXXX;

public enum Direction {
    NORTH, SOUTH, EAST, WEST; 

    private Direction left; 
    private Direction right; 

    static {
        NORTH.left = WEST;
        NORTH.right = EAST;
        WEST.left = SOUTH;
        WEST.right = NORTH;
        EAST.left = NORTH;
        EAST.right = SOUTH;
        SOUTH.left = EAST;
        SOUTH.right = WEST;
    }

    public Direction turnLeft(){
        return left; 
    }
    public Direction turnRight(){
        return right;
    }

}
