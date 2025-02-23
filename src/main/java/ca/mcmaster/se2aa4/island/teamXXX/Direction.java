// Direction.java
package ca.mcmaster.se2aa4.island.teamXXX;

public enum Direction {
    NORTH("N"),
    EAST("E"),
    SOUTH("S");

    private final String value;

    Direction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}