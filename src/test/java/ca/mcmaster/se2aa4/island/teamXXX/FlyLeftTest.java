package ca.mcmaster.se2aa4.island.teamXXX;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FlyLeftTest {
    @Test
    public void testFlyLeftNorth() {
        LocationCoordinates location = new LocationCoordinates(5, 5);
        Fly fly = new Fly();
        fly.flyLeft(Direction.NORTH, location);
        assertEquals(4, location.getX());
        assertEquals(5, location.getY());
    }

    @Test
    public void testFlyLeftSouth() {
        LocationCoordinates location = new LocationCoordinates(5, 5);
        Fly fly = new Fly();
        fly.flyLeft(Direction.SOUTH, location);
        assertEquals(6, location.getX());
        assertEquals(5, location.getY());
    }

    @Test
    public void testFlyLeftEast() {
        LocationCoordinates location = new LocationCoordinates(5, 5);
        Fly fly = new Fly();
        fly.flyLeft(Direction.EAST, location);
        assertEquals(5, location.getX());
        assertEquals(4, location.getY());
    }

    @Test
    public void testFlyLeftWest() {
        LocationCoordinates location = new LocationCoordinates(5, 5);
        Fly fly = new Fly();
        fly.flyLeft(Direction.WEST, location);
        assertEquals(5, location.getX());
        assertEquals(6, location.getY());
    }
}
