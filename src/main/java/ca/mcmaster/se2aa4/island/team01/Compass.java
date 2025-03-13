package main.java.ca.mcmaster.se2aa4.island.teamXXX;

public class Compass {

    public static Direction turnLeft(Direction direction) {
        if (direction = Direction.EAST) {
            return NORTH;
        }
        else if (direction = Direction.NORTH) {
            return WEST;
        }
        else if (direction = Direction.WEST) {
            return SOUTH;
        }
        else {
            return EAST;
        }
    }

    public static Direction turnRight(Directiion direction) {
        if (direction = Direction.EAST) {
            return SOUTH;
        }
        else if (direction = Direction.SOUTH) {
            return WEST;
        }
        else if (direction = Direction.WEST) {
            return NORTH;
        }
        else {
            return EAST;
        }
    }
}
