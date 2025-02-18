package ca.mcmaster.se2aa4.island.teamXXX;

public class LocationCoordinates {



    private String currentCoordinates;
    private int xCoordinate; 
    private int yCoordinate; 

    public LocationCoordinates(String coordinates){
        currentCoordinates = coordinates;
        String[] xAndY = currentCoordinates.split(","); 

        xCoordinate = Integer.parseInt(xAndY[0]); 
        yCoordinate = Integer.parseInt(xAndY[1]);
    }
    

    public void setNewCoordinates(String newCoordinates){
        currentCoordinates = newCoordinates;
        String[] xAndY = currentCoordinates.split(","); 

        xCoordinate = Integer.parseInt(xAndY[0]); 
        yCoordinate = Integer.parseInt(xAndY[1]);
        
    }
    public int getCurrentXCoordinate(){
        return xCoordinate; 
    }
    public int getCurrentYCoordinate(){

        return yCoordinate; 
    }




}
