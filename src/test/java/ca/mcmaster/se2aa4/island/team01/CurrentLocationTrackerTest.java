package ca.mcmaster.se2aa4.island.team01;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class CurrentLocationTrackerTest {

    @Test
    public void moveTest() {
        Direction direction = Direction.SOUTH;
        CurrentLocation currLocation = CurrentLocation.get();
        CurrentLocationTracker tracker = new CurrentLocationTracker();
        tracker.move(direction);
        assertEquals(currLocation.getXCoordinate(), 2);
    }

}
