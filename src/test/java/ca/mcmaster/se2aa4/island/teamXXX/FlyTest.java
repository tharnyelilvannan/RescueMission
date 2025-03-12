package ca.mcmaster.se2aa4.island.teamXXX;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class FlyTest {

    @Test
    public void sampleTest() {
        assertTrue(1 == 1);
    }
    
    @Test
    public void TestFlyRight() {
        Direction dir = Direction.NORTH; 
        LocationCoordinates coordinates = new LocationCoordinates("5,5");
        Fly fly = new Fly(); 
        
        String result = fly.flyRight(dir, coordinates);

        String expectedResults = "6,6";

        assertEquals(expectedResults, result);
    }
    @Test
    public void TestFlyRight2() {
        Direction dir = Direction.SOUTH; 
        LocationCoordinates coordinates = new LocationCoordinates("3,2");

        Fly fly = new Fly(); 
        
        String result = fly.flyRight(dir, coordinates);

        String expectedResults = "2,1";

        assertEquals( expectedResults, result );
    }


}
