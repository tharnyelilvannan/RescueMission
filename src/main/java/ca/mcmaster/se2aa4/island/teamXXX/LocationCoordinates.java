package ca.mcmaster.se2aa4.island.teamXXX;


public class LocationCoordinates {

    private String currentCoordinates;
    private int xCoordinate; 
    private int yCoordinate; 

    public LocationCoordinates(String coordinates){
        this.currentCoordinates = coordinates;
        setXAndYCoordinates(currentCoordinates);
    }

    private void setXAndYCoordinates(String coordinates){
        String[] xAndY = coordinates.split(","); 
        xCoordinate = Integer.parseInt(xAndY[0]); 
        yCoordinate = Integer.parseInt(xAndY[1]);

    }
    
    public void setNewCoordinates(String newCoordinates){
        currentCoordinates = newCoordinates;
        setXAndYCoordinates(currentCoordinates);
    }

    public int getCurrentXCoordinate(){
        return xCoordinate; 
    }
    public int getCurrentYCoordinate(){
        return yCoordinate; 
    }
    public String getCurrentCoordinates(){
        return currentCoordinates; 
    }




}
