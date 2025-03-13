package ca.mcmaster.se2aa4.island.team01;

public class BatteryLevel {

    private int currentBatteryLevel; 

    public BatteryLevel(Integer batteryLevel){
        currentBatteryLevel = batteryLevel; 
    }

    public int getCurrentBattery(){
        return currentBatteryLevel; 
    }
    public void setCurrentBattery(int newBatteryLevel){
        currentBatteryLevel = newBatteryLevel; 
    }
    
}
