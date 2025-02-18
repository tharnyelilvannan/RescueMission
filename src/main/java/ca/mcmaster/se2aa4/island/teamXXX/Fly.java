package ca.mcmaster.se2aa4.island.teamXXX;


public class Fly {
    Direction direction; 

    public String flyRight(Direction dir, LocationCoordinates currentCoordinates){ // { "action": "heading", "parameters": { "direction": "N" } }
        // JSONObject command = new JSONObject(new JSONTokener(new StringReader(s)));
        // //String action = command.getString("action");
        // String parameterString = command.getString("parameters");

        // JSONObject parameter = new JSONObject(new JSONTokener(new StringReader(parameterString)));
        // String dir = parameter.getString("direction"); 



        // must do boundary checks
        int x = currentCoordinates.getCurrentXCoordinate(); 
        int y = currentCoordinates.getCurrentYCoordinate();
        
        if (dir == Direction.NORTH){
            x = x + 1; 
            y = y + 1; 
            direction = Direction.EAST;
        }
        else if (dir == Direction.EAST){
            x = x + 1; 
            y = y - 1; 
            direction = Direction.SOUTH;
        }
        else if (dir == Direction.SOUTH){
            x = x - 1; 
            y = y - 1;
            direction = Direction.WEST; 
        }
        else if (dir == Direction.WEST){
            x = x - 1; 
            y = y + 1; 
            direction = Direction.NORTH; 
        }
        
        // JSONObject result = new JSONObject(); 
        // result.put("currentHeading", direction); 

        // JSONObject newPos = new JSONObject(); 
        // newPos.put("x", x); 
        // newPos.put("y", y);

        // result.put("currentLocation", newPos); 

        // return result.toString(); 

        String newCoordinates = x + "," + y; 

        return newCoordinates;

    }

    
}
