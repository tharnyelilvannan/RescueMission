package ca.mcmaster.se2aa4.island.teamXXX;
import org.json.JSONObject;

public class Drone {

    private LocationCoordinates currentCoordinates; 
    private Direction currentDirection; 
    private BatteryLevel batteryLevel; 
    private String nextAction; 
    private String request;

    public Drone(Direction currentDirection, Integer currentbatteryLevel, int x, int y){
        this.currentDirection = currentDirection; 
        this.batteryLevel = new BatteryLevel(currentbatteryLevel); 
        currentCoordinates = new LocationCoordinates(x + ","+ y); 
        nextAction = "fly";
    }

    











    
}
