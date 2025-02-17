package test.java.ca.mcmaster.se2aa4.island.team01;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import main.java.ca.mcmaster.se2aa4.island.teamXXX.Fly;
import main.java.ca.mcmaster.se2aa4.island.teamXXX.Direction;
import main.java.ca.mcmaster.se2aa4.island.teamXXX.LocationCoordinates;


public class FlyForwardTest {
    Direction direction = Direction.SOUTH;
    LocationCoordinates location = new LocationCoordinates();
    Fly fly = new Fly();
    @Test
    public void flyForward() {
        assertTrue(0 == fly.flyForward(direction, location));
    }

}